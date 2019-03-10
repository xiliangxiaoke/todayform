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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.MapDrawActionInfo;
import com.today.todayfarm.pages.createFarm.CreateFarmActivity;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFarm2MapActivity extends Activity implements AMapLocationListener {

    private int shapetype = 0; // 0-circle 1-polygon
    private int drawbuttonstatus = 0; // 0-draw 1-next


    public AMapLocationClient mlocationClient = null;
    public AMapLocationClientOption option = new AMapLocationClientOption();

    @BindView(R.id.addfarmmap)
    WebView map;

    @BindView(R.id.exit)
    Button exit;

    @BindView(R.id.changeshape)
    TextView tvchangeshape;

    @BindView(R.id.dodraw)
    Button btdraw;

    @BindView(R.id.back) Button drawback;

    @OnClick(R.id.exit)
    public void setExit(){
        this.finish();

    }

    @OnClick(R.id.back)
    public void setback(){
        MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
        mapDrawActionInfo.setAction("back");

        JSParamInfo<MapDrawActionInfo> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("draw");
        jsParamInfo.setParams(mapDrawActionInfo);
        WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
    }


    @OnClick(R.id.geolocation)
    public void setGeolocation(){
//location do once
        if(null != mlocationClient){
            mlocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mlocationClient.stopLocation();
            mlocationClient.startLocation();
        }
    }

    @OnClick(R.id.fullmap)
    public void setfullmap(){
        // todo: 地图内容全图显示
        fullextent();
    }


    @OnClick(R.id.dodraw)
    public void dodraw(){

        if (drawbuttonstatus == 0){
            // 改变UI
            tvchangeshape.setVisibility(View.GONE);

            btdraw.setText("下一步");
            drawbuttonstatus =1 ;
            // 操作地图
            MapDrawActionInfo mapDrawActionInfo = new MapDrawActionInfo();
            if (shapetype==0){
                mapDrawActionInfo.setAction("drawcircle");
            }else{
                mapDrawActionInfo.setAction("drawpolygon");
                drawback.setVisibility(View.VISIBLE);
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
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        exit.setTypeface(MyApplication.iconTypeFace);
        tvchangeshape.setTypeface(MyApplication.iconTypeFace);
        drawback.setTypeface(MyApplication.iconTypeFace);

        // 配置webview
        loadmap();



        mlocationClient = new AMapLocationClient(this);
        mlocationClient.setLocationListener(this);
        //定位参数
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);//定位场景 签到
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);// 定位模式 高精度
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true);


        if(null != mlocationClient){
            mlocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mlocationClient.stopLocation();
            mlocationClient.startLocation();
        }



    }

    public void fullextent(){
        JSParamInfo<Object> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("fullextent");
        WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
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
//                            new SweetAlertDialog(AddFarm2MapActivity.this)
//                                    .setTitleText(js2AndroidParam.getType())
//                                    .setContentText(js2AndroidParam.getValue())
//                                    .show();

                        }
                    }
                }
            }
        }),"androidjs");


        map.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double longitude = aMapLocation.getLongitude();
                double latitude = aMapLocation.getLatitude();

                String city = aMapLocation.getCity();
                if (city != null) {
                    Hawk.put(HawkKey.CITY,city);
                }


                Gson gson = new Gson();
                GeoPoint geoPoint = new GeoPoint();
                geoPoint.setLon(longitude);
                geoPoint.setLat(latitude);
                JSParamInfo<GeoPoint> jsParamInfo = new JSParamInfo<>();
                jsParamInfo.setType("location");
                jsParamInfo.setParams(geoPoint);
                WebUtil.callJS(map,gson.toJson(jsParamInfo));

//                callJS("loc:"+longitude+latitude);

//                new SweetAlertDialog(MapFragment.this.getContext())
//                        .setTitleText("定位成功")
//                        .setConfirmText(""+longitude+latitude)
//                        .show();

            } else {
                // 定位失败
            }
        }
    }
}
