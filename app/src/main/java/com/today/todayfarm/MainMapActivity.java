package com.today.todayfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.GroundOverlay;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.today.todayfarm.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMapActivity extends AppCompatActivity implements WeatherSearch.OnWeatherSearchListener,LocationSource,
        AMapLocationListener {

    @BindView(R.id.weather_time)TextView weatherTime;

    @BindView(R.id.weather_info)TextView weatherInfo;
    @BindView(R.id.map)MapView mapView;
    @OnClick(R.id.back) void back(){
        this.finish();
    }

    @OnClick(R.id.weather_button)void showDetailWeather(){
        Intent intent = new Intent();
        intent.setClass(this,WeatherSearchActivity.class);
        intent.putExtra("city",cityname);
        startActivity(intent);
    }

    @OnClick(R.id.fieldlist) void showFieldlist(){
        Intent intent = new Intent();
        intent.setClass(this,FieldListActivity.class);
        startActivity(intent);
    }


    AMap aMap;

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RadioGroup mGPSModeGroup;

    private TextView mLocationErrText;

    String cityname = "青岛市";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_map);
        ButterKnife.bind(this);

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }


        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象




        setUpMap();

        getWeatherInfo("青岛市");


        testaddOverlayToMap();

    }



    /**
     * 往地图上添加一个groundoverlay覆盖物
     */
    private void testaddOverlayToMap() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.3383525435886,
                106.3838672323), 18));//
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(40.3333525435886, 106.3788672323))
                .include(new LatLng(40.3406619155568, 106.388435294325)).build();

       GroundOverlay groundoverlay = aMap.addGroundOverlay(new GroundOverlayOptions()
                .anchor(0.5f, 0.5f)
                .transparency(0.1f)
                .image(BitmapDescriptorFactory
                        .fromResource(R.drawable.testoverlayimg))
                .positionFromBounds(bounds));
    }



    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle(){
//        // 自定义定位蓝点图标系统定位蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
//                fromResource(R.drawable.gps_point));
//        // 自定义精度范围的圆形边框颜色
//        myLocationStyle.strokeColor(STROKE_COLOR);
//        //自定义精度范围的圆形边框宽度
//        myLocationStyle.strokeWidth(5);
//        // 设置圆形的填充颜色
//        myLocationStyle.radiusFillColor(FILL_COLOR);
//        // 将自定义的 myLocationStyle 对象添加到地图上
//        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult ,int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive;
                weatherlive = weatherLiveResult.getLiveResult();
                weatherTime.setText("实时天气 "+cityname+" "+weatherlive.getReportTime()+"发布");
                weatherInfo.setText(weatherlive.getWeather()+"  "+
                        weatherlive.getTemperature()+"°"+"  "+
                        weatherlive.getWindDirection()+"风"+weatherlive.getWindPower()+"级"+"  "+
                        "湿度"+weatherlive.getHumidity()+"%"
                );


            }else {
                ToastUtil.show(MainMapActivity.this, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(MainMapActivity.this, rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                cityname = amapLocation.getCity();
                getWeatherInfo(amapLocation.getCity());

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);

            }
        }


//        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//        amapLocation.getLatitude();//获取纬度
//        amapLocation.getLongitude();//获取经度
//        amapLocation.getAccuracy();//获取精度信息
//        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//        amapLocation.getCountry();//国家信息
//        amapLocation.getProvince();//省信息
//        amapLocation.getCity();//城市信息
//        amapLocation.getDistrict();//城区信息
//        amapLocation.getStreet();//街道信息
//        amapLocation.getStreetNum();//街道门牌号信息
//        amapLocation.getCityCode();//城市编码
//        amapLocation.getAdCode();//地区编码
//        amapLocation.getAoiName();//获取当前定位点的AOI信息
//        amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//        amapLocation.getFloor();//获取当前室内定位的楼层
//        amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        // 激活定位
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }

    }

    @Override
    public void deactivate() {
        // 关闭定位
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }



    void getWeatherInfo(String city){
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
}
