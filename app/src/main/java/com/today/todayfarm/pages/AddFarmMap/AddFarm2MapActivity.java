package com.today.todayfarm.pages.AddFarmMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.MapDrawActionInfo;
import com.today.todayfarm.pages.createFarm.CreateFarmActivity;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFarm2MapActivity extends Activity {

    private int shapetype = 0; // 0-circle 1-polygon
    private int drawbuttonstatus = 0; // 0-draw 1-next

    @BindView(R.id.addfarmmap)
    WebView map;

    @BindView(R.id.exit)
    Button exit;

    @BindView(R.id.changeshape)
    TextView tvchangeshape;

    @BindView(R.id.dodraw)
    Button btdraw;

    @BindView(R.id.back) Button drawback;

    @OnClick(R.id.back)
    public void back(){
        this.finish();

    }

    @OnClick(R.id.dodraw)
    public void dodraw(){

        if (drawbuttonstatus == 0){
            // 改变UI
            tvchangeshape.setVisibility(View.GONE);
            drawback.setVisibility(View.VISIBLE);
            btdraw.setText("下一步");
            drawbuttonstatus =1 ;
            // 操作地图
            MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
            if (shapetype==0){
                mapDrawActionInfo.setAction("drawcircle");
            }else{
                mapDrawActionInfo.setAction("drawpolygon");
            }

            JSParamInfo<MapDrawActionInfo> jsParamInfo = new JSParamInfo<>();
            jsParamInfo.setType("draw");
            jsParamInfo.setParams(mapDrawActionInfo);
            WebUtil.callJS(map,new Gson().toJson(jsParamInfo));

        } else if(drawbuttonstatus ==1){
            // next action 请求获取边界信息，等回调方法"returngeojson"返回
            MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
            mapDrawActionInfo.setAction("getgeojson");
            JSParamInfo<MapDrawActionInfo> jsParamInfo = new JSParamInfo<>();
            jsParamInfo.setType("draw");
            jsParamInfo.setParams(mapDrawActionInfo);
            WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
        }

    }

    @OnClick(R.id.changeshape)
    public void changeshape(){
        if(shapetype==0){
            // 切换为polygon
            tvchangeshape.setText(this.getResources().getString(R.string.icon_change_shape_polygon));
            shapetype = 1;
        }else{
            // 切换为circle
            tvchangeshape.setText(this.getResources().getString(R.string.icon_change_shape_circle));
            shapetype = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm2_map);
        ButterKnife.bind(this);

        exit.setTypeface(MyApplication.iconTypeFace);
        tvchangeshape.setTypeface(MyApplication.iconTypeFace);
        drawback.setTypeface(MyApplication.iconTypeFace);

        // 配置webview
        loadmap();






    }


    private void loadmap() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        map.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
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
                                intent.setClass(AddFarm2MapActivity.this, CreateFarmActivity.class);
                                AddFarm2MapActivity.this.startActivity(intent);


//                                new SweetAlertDialog(AddFarm2MapActivity.this)
//                                        .setTitleText(js2AndroidParam.getType())
//                                        .setContentText(js2AndroidParam.getValue())
//                                        .show();
                            }
                        }else if ("error".equals(js2AndroidParam.getType())){
                            Log.e("ERRORSSSS",js2AndroidParam.getValue());
                            new SweetAlertDialog(AddFarm2MapActivity.this)
                                    .setTitleText(js2AndroidParam.getType())
                                    .setContentText(js2AndroidParam.getValue())
                                    .show();

                        }
                    }
                }
            }
        }),"androidjs");


        map.loadUrl("file:///android_asset/index.html");
    }
}
