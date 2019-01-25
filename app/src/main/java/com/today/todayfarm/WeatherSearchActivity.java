package com.today.todayfarm;

import android.content.Intent;
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
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
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


        Intent intent = getIntent();
        cityname = intent.getStringExtra("city");

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
            }else {
                ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(WeatherSearchActivity.this, rCode);
        }
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


            // 第二天
            seconddate.setText(getWeek(forecastlist.get(1)));
            secondweather.setText(forecastlist.get(1).getDayWeather());
            secondtemp.setText(getTemp(forecastlist.get(1)));

            // 第三天
            thirddate.setText(getWeek(forecastlist.get(2)));
            thirdweather.setText(forecastlist.get(2).getDayWeather());
            thirdtemp.setText(getTemp(forecastlist.get(2)));
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
