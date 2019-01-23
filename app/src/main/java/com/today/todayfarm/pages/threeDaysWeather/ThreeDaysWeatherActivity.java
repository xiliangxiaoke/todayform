package com.today.todayfarm.pages.threeDaysWeather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.HealthImgInfo;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.AddFarmMap.AddFarm2MapActivity;
import com.today.todayfarm.pages.createFarm.CreateFarmActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreeDaysWeatherActivity extends BaseActivity {


    @BindView(R.id.rain)
    TextView rain;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.back)
            TextView back;

    String fieldid = null;
    private boolean firstloaded = false;


    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.rain)
    public void showRainData() {
        // todo: 获取降雨数据r(0:温度，1：湿度,2:降水)

        API.getThreeDayWeather(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                2,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            List<Object> list = resultObj.getList();
                            List<List<Object>> data = (List<List<Object>>) list.get(0);
                            // Object: ["20181223140000",0.0]
                            JSParamInfo<List<Object>> jsParamInfo = new JSParamInfo<>();
                            jsParamInfo.setType("threeDaysWeatherChart");
                            jsParamInfo.setList(data);
                            WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));

                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    @OnClick(R.id.humidity)
    public void showHumid(){
        // todo: 获取湿度(0:温度，1：湿度,2:降水)
        API.getThreeDayWeather(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                1,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            List<Object> list = resultObj.getList();
                            List<List<Object>> data = (List<List<Object>>) list.get(0);
                            // Object: ["20181223140000",0.0]
                            JSParamInfo<List<Object>> jsParamInfo = new JSParamInfo<>();
                            jsParamInfo.setType("threeDaysHumidChart");
                            jsParamInfo.setList(data);
                            WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));

                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    @OnClick(R.id.temp)
    public void showTemp(){
        // todo: 获取温度数据(0:温度，1：湿度,2:降水)
        API.getThreeDayWeather(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                0,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            List<Object> list = resultObj.getList();
                            List<List<Object>> data = (List<List<Object>>) list.get(0);
                            // Object: ["20181223140000",0.0]
                            JSParamInfo<List<Object>> jsParamInfo = new JSParamInfo<>();
                            jsParamInfo.setType("threeDaysTempChart");
                            jsParamInfo.setList(data);
                            WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));

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
        setContentView(R.layout.activity_three_days_weather);
        ButterKnife.bind(this);


        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        back.setTypeface(MyApplication.iconTypeFace);
        fieldid = getIntent().getStringExtra("fieldid");


        initwebview();

    }

    private void initwebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
            @Override
            public void callback(String json) {
                if (json!=null){
                    JS2AndroidParam js2AndroidParam = new Gson().fromJson(json,JS2AndroidParam.class);
                    if (js2AndroidParam!=null){
                        // do something
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
                    showTemp();
                }
            }
        });

        webView.loadUrl("file:///android_asset/echart.html");
    }
}
