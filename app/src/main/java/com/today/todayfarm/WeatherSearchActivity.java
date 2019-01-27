package com.today.todayfarm;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.facebook.drawee.view.SimpleDraweeView;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherSearchActivity extends BaseActivity implements OnWeatherSearchListener {


    @BindView(R.id.realTemp)
    TextView realTemp;
    @BindView(R.id.realicon)
    SimpleDraweeView realicon;
    @BindView(R.id.realweathername)
    TextView realweathername;
    @BindView(R.id.realLeftInfo)
    TextView realLeftInfo;
    @BindView(R.id.realRightInfo)
    TextView realRightInfo;

    @BindView(R.id.firstdate)
    TextView firstdate;
    @BindView(R.id.firsticon)
    SimpleDraweeView firsticon;
    @BindView(R.id.firstweather)
    TextView firstweather;
    @BindView(R.id.firsttemp)
    TextView firsttemp;

    @BindView(R.id.seconddate)
    TextView seconddate;
    @BindView(R.id.secondicon)
    SimpleDraweeView secondicon;
    @BindView(R.id.secondweather)
    TextView secondweather;
    @BindView(R.id.secondtemp)
    TextView secondtemp;

    @BindView(R.id.thirddate)
    TextView thirddate;
    @BindView(R.id.thirdicon)
    SimpleDraweeView thirdicon;
    @BindView(R.id.thirdweather)
    TextView thirdweather;
    @BindView(R.id.thirdtemp)
    TextView thirdtemp;

    @BindView(R.id.city)
    TextView city;


    @BindView(R.id.back)
    TextView back;
    @OnClick(R.id.back)
    public void setback() {
        this.finish();
    }


    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private String cityname="北京市";//天气搜索的城市，可以写名称或adcode；
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_search);
        ButterKnife.bind(this);
        back.setTypeface(MyApplication.iconTypeFace);


        Intent intent = getIntent();
        cityname = intent.getStringExtra("city");

        city.setText(cityname);

        searchliveweather();
        searchforcastsweather();
    }

    private void searchforcastsweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    private void searchliveweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult ,int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherlive = weatherLiveResult.getLiveResult();
//                reporttime1.setText(weatherlive.getReportTime()+"发布");
                realweathername.setText(weatherlive.getWeather());
                realTemp.setText(weatherlive.getTemperature()+"°");
                realLeftInfo.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                realRightInfo.setText("湿度         "+weatherlive.getHumidity()+"%");
                realicon.setImageURI(Uri.parse("res://a/"+getWeatherDrawable(weatherlive.getWeather())));
            }else {
                ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(WeatherSearchActivity.this, rCode);
        }
    }

    private int getWeatherDrawable(String weather) {
        int drawable = R.mipmap.w99;
        if ("晴".equals(weather)){
            drawable = R.mipmap.w0;
        }else if ("多云".equals(weather)){
            drawable = R.mipmap.w4;
        }else if ("阴".equals(weather)){
            drawable = R.mipmap.w9;
        }else if ("阵雨".equals(weather)){
            drawable = R.mipmap.w10;
        }else if ("雷阵雨".equals(weather)){
            drawable = R.mipmap.w11;
        }else if ("雷阵雨并伴有冰雹".equals(weather)){
            drawable = R.mipmap.w12;
        }else if ("雨夹雪".equals(weather)){
            drawable = R.mipmap.w20;
        }else if ("小雨".equals(weather)){
            drawable = R.mipmap.w13;
        }else if ("中雨".equals(weather)){
            drawable = R.mipmap.w14;
        }else if ("大雨".equals(weather)){
            drawable = R.mipmap.w15;
        }else if ("暴雨".equals(weather)){
            drawable = R.mipmap.w16;
        }else if ("大暴雨".equals(weather)){
            drawable = R.mipmap.w17;
        }else if ("特大暴雨".equals(weather)){
            drawable = R.mipmap.w18;
        }else if ("阵雪".equals(weather)){
            drawable = R.mipmap.w21;
        }else if ("小雪".equals(weather)){
            drawable = R.mipmap.w22;
        }else if ("中雪".equals(weather)){
            drawable = R.mipmap.w23;
        }else if ("大雪".equals(weather)){
            drawable = R.mipmap.w24;
        }else if ("暴雪".equals(weather)){
            drawable = R.mipmap.w25;
        }else if ("雾".equals(weather)){
            drawable = R.mipmap.w30;
        }else if ("冻雨".equals(weather)){
            drawable = R.mipmap.w19;
        }else if ("沙尘暴".equals(weather)){
            drawable = R.mipmap.w28;
        }else if ("小雨-中雨".equals(weather)){
            drawable = R.mipmap.w14;
        }else if ("中雨-大雨".equals(weather)){
            drawable = R.mipmap.w15;
        }else if ("大雨-暴雨".equals(weather)){
            drawable = R.mipmap.w16;
        }else if ("暴雨-大暴雨".equals(weather)){
            drawable = R.mipmap.w17;
        }else if ("大暴雨-特大暴雨".equals(weather)){
            drawable = R.mipmap.w18;
        }else if ("小雪-中雪".equals(weather)){
            drawable = R.mipmap.w23;
        }else if ("中雪-大雪".equals(weather)){
            drawable = R.mipmap.w24;
        }else if ("大雪-暴雪".equals(weather)){
            drawable = R.mipmap.w25;
        }else if ("浮尘".equals(weather)){
            drawable = R.mipmap.w26;
        }else if ("扬沙".equals(weather)){
            drawable = R.mipmap.w27;
        }else if ("强沙尘暴".equals(weather)){
            drawable = R.mipmap.w29;
        }else if ("飑".equals(weather)){
            drawable = R.mipmap.w34;
        }else if ("龙卷风".equals(weather)){
            drawable = R.mipmap.w36;
        }else if ("弱高吹雪".equals(weather)){
            drawable = R.mipmap.w32;
        }else if ("轻霾".equals(weather)){
            drawable = R.mipmap.w31;
        }else if ("霾".equals(weather)){
            drawable = R.mipmap.w31;
        }
        return drawable;
    }

    /**
     * 天气预报查询结果回调
     * */
    @Override
    public void onWeatherForecastSearched(
            LocalWeatherForecastResult weatherForecastResult,int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (weatherForecastResult!=null && weatherForecastResult.getForecastResult()!=null
                    && weatherForecastResult.getForecastResult().getWeatherForecast()!=null
                    && weatherForecastResult.getForecastResult().getWeatherForecast().size()>0) {
                weatherforecast = weatherForecastResult.getForecastResult();
                forecastlist= weatherforecast.getWeatherForecast();
                fillforecast();

            }else {
                ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(WeatherSearchActivity.this, rCode);
        }
    }
    private void fillforecast() {

        if (forecastlist.size() >= 3) {
            // 明天
            firstdate.setText(getWeek(forecastlist.get(0)));
            firstweather.setText(forecastlist.get(0).getDayWeather());
            firsttemp.setText(getTemp(forecastlist.get(0)));
            firsticon.setImageURI(Uri.parse("res://a/"+getWeatherDrawable(forecastlist.get(0).getDayWeather())));


            // 第二天
            seconddate.setText(getWeek(forecastlist.get(1)));
            secondweather.setText(forecastlist.get(1).getDayWeather());
            secondtemp.setText(getTemp(forecastlist.get(1)));
            secondicon.setImageURI(Uri.parse("res://a/"+getWeatherDrawable(forecastlist.get(1).getDayWeather())));

            // 第三天
            thirddate.setText(getWeek(forecastlist.get(2)));
            thirdweather.setText(forecastlist.get(2).getDayWeather());
            thirdtemp.setText(getTemp(forecastlist.get(2)));
            thirdicon.setImageURI(Uri.parse("res://a/"+getWeatherDrawable(forecastlist.get(2).getDayWeather())));
        }

//        String forecast="";
//        for (int i = 0; i < forecastlist.size(); i++) {
//            LocalDayWeatherForecast localdayweatherforecast=forecastlist.get(i);
//            String week = null;
//
//            String temp =String.format("%-3s/%3s",
//                    localdayweatherforecast.getDayTemp()+"°",
//                    localdayweatherforecast.getNightTemp()+"°");
//            String date = localdayweatherforecast.getDate();
//            forecast+=date+"  "+week+"                       "+temp+"\n\n";
//        }

    }

    private String getTemp(LocalDayWeatherForecast localDayWeatherForecast) {
        String temp =String.format("%-3s/%3s",
                localDayWeatherForecast.getDayTemp()+"°",
                localDayWeatherForecast.getNightTemp()+"°");
        return temp;
    }


    public String getWeek(LocalDayWeatherForecast localdayweatherforecast){
        String week = "";
        switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
            case 7:
                week = "周日";
                break;
            default:
                break;
        }
        return week;
    }
}
