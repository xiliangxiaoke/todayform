package com.today.todayfarm.pages.AddFarmMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.MapDrawActionInfo;
import com.today.todayfarm.pages.createFarm.CreateFarmActivity;
import com.today.todayfarm.pages.createFarm.EditFarmActivity;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmMapActivity extends BaseActivity {


    public static final int RESULTCODE_EditFarmMapActivity = 234;
    FieldInfo fieldInfo = null;

    @BindView(R.id.webview)
    WebView webView;
    private boolean firstloaded=false;


    @OnClick(R.id.exit)
    public void exit() {
        this.finish();
    }


    @OnClick(R.id.save)
    public void save() {
        // 保存获取边界
        MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
        mapDrawActionInfo.setAction("getgeojson");
        JSParamInfo<MapDrawActionInfo> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("draw");
        jsParamInfo.setParams(mapDrawActionInfo);
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farm_map);
        ButterKnife.bind(this);

        String fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        try {
            fieldInfo = new Gson().fromJson(fieldinfo_json,FieldInfo.class);
        } catch (Exception e) {

        }

        initWebview();
    }

    private void initWebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        webView.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
            @Override
            public void callback(String json) {
                if (json!=null){
                    JS2AndroidParam js2AndroidParam = new Gson().fromJson(json,JS2AndroidParam.class);
                    if (js2AndroidParam!=null){
                        if ("returngeojson".equals(js2AndroidParam.getType())){
                            if (js2AndroidParam.getValue()!=null && js2AndroidParam.getValue().length()>0){
                                //成功拿到geojson边界  js2AndroidParam.getValue()

                                String geojson = js2AndroidParam.getValue();

                                Hawk.put(HawkKey.TEMP_GEOJSON,geojson);
                                Hawk.put(HawkKey.TEMP_GEOJSON_AREA, js2AndroidParam.getFarmarea());

                                Intent intent = new Intent();
                                intent.setClass(EditFarmMapActivity.this, CreateFarmActivity.class);
                                intent.putExtra("geojson",geojson);
                                intent.putExtra("area",js2AndroidParam.getFarmarea());
                                EditFarmMapActivity.this.setResult(RESULTCODE_EditFarmMapActivity,intent);
                                EditFarmMapActivity.this.finish();


                            }
                        }else if ("error".equals(js2AndroidParam.getType())){
                            Log.e("ERRORSSSS",js2AndroidParam.getValue());
                            new SweetAlertDialog(EditFarmMapActivity.this)
                                    .setTitleText(js2AndroidParam.getType())
                                    .setContentText(js2AndroidParam.getValue())
                                    .show();

                        }
                    }
                }
            }
        }),"androidjs");

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && firstloaded == false){
                    Log.v("webview","totalChartWebview 加载完成");
                    firstloaded = true;
                    if (fieldInfo != null) {
                        showAndEditFieldboundary(fieldInfo.getFieldBoundary());
                    }
                }
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    private void showAndEditFieldboundary(String boundary) {
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("editgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        Log.v("boundary:",boundary);

        try{
            boundaryInfo2Js = new Gson().fromJson(boundary,BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }
}
