package com.today.todayfarm.pages.createFarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.MapDrawActionInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.AddFarmMap.AddFarm2MapActivity;
import com.today.todayfarm.pages.tabs.DrawerTabActivity;
import com.today.todayfarm.pages.tabs.TabActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateFarmActivity extends BaseActivity {

    String geojson;

    @BindView(R.id.map)
    WebView map;

    @BindView(R.id.close )
    TextView tvclose;

    @BindView(R.id.save)
    TextView tvsave;

    @BindView(R.id.editfarmname)
    EditText editfarmname;

    @BindView(R.id.editcropname)
    EditText editcropname;

    @OnClick(R.id.close)
    public void close(){
        this.finish();
    }

    @OnClick(R.id.save)
    public void save(){
        // 保存农田
        String farmname = editfarmname.getText().toString();
        String cropname = editcropname.getText().toString();
        if (farmname==null){
            new SweetAlertDialog(this)
                    .setTitleText("农田名称为空")
                    .show();
            return;
        }
        if (cropname == null) {
            new SweetAlertDialog(this)
                    .setTitleText("作物名称为空")
                    .show();
            return;
        }
        API.saveOrUpdate(
                Hawk.get(HawkKey.TOKEN),
                farmname,
                Hawk.get(HawkKey.TEMP_GEOJSON_AREA),
                geojson,
                cropname,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //保存成功
                            new SweetAlertDialog(CreateFarmActivity.this)
                                    .setTitleText("保存成功")
                                    .showConfirmButton(true)
                                    .setConfirmText("好的")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Hawk.put(HawkKey.MAIN_PAGE_INDEX_TO_SHOW,1);
                                            Intent intent = new Intent();
                                            intent.setClass(CreateFarmActivity.this, DrawerTabActivity.class);
                                            CreateFarmActivity.this.startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_farm);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        tvclose.setTypeface(MyApplication.iconTypeFace);
        tvsave.setTypeface(MyApplication.iconTypeFace);

        geojson = Hawk.get(HawkKey.TEMP_GEOJSON);

        loadmap();

        // 设置
//        MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
//        mapDrawActionInfo.setAction("getgeojson");
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        //Log.v("boundary:",geojson);

        try{
            boundaryInfo2Js = new Gson().fromJson(geojson,BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);




        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
            }
        }, 300);//3秒后执行Runnable中的run方法



    }


    private void loadmap() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);



        map.loadUrl("file:///android_asset/index.html");
    }
}
