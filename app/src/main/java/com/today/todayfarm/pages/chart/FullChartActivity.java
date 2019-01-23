package com.today.todayfarm.pages.chart;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.NameValuePair;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 全屏图表，用于雨量和积温的横屏图标页面
 * charttype: rain temp
 * datatype: total everyday
 */
public class FullChartActivity extends BaseActivity {
    String charttype = null;
    String datatype = null;
    int datarange = 0; // 1 自种植以来 0 全年数据
    String fieldId = null;
    String cropId = null;

    @BindView(R.id.fromCrop)
    TextView fromcrop;
    @BindView(R.id.allyear)TextView allyear;
    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.back)
    TextView back;

    private boolean firstloaded = false;

    @OnClick(R.id.back)
    public void back(){
        this.finish();
    }


    @OnClick(R.id.fromCrop)
    public void fromcropfun() {
        //  获取自种植以来
        datarange = 1;
        getData();
    }

    @OnClick(R.id.allyear)
    public void allyearfun() {
        // 全年数据
        datarange = 0;
        getData();
    }

    private void getData() {
        if ("rain".equals(charttype)) {

            getRainData();
        } else if ("temp".equals(charttype)) {
            getTempData();
        }
    }

    private void getTempData() {
        String _name = "";
        if ("total".equals(datatype)) {
            _name = "totalTemperature";
        } else if ("everyday".equals(datatype)) {
            _name = "temperature";
        }
        String final_name = _name;
        API.findTemperatureDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldId,
                cropId,
                datarange, _name,
                new ApiCallBack<NameValuePair>() {
                    @Override
                    public void onResponse(ResultObj<NameValuePair> resultObj) {
                        if (resultObj.getCode() == 0) {
                            String fun = "";
                            if (datarange == 0){
                                if ("temperature".equals(final_name)) {
                                    fun = "tempEveryDayYearByField";
                                } else if ("totalTemperature".equals(final_name)) {
                                    fun = "tempTotalYearByField";
                                }
                            } else if (datarange == 1) {
                                if ("temperature".equals(final_name)) {
                                    fun = "tempEveryDayByCrop";
                                } else if ("totalTemperature".equals(final_name)) {
                                    fun = "tempTotalByCrop";
                                }
                            }
                            showChart(fun,resultObj.getList());
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void getRainData() {
        String _name = "";
        if ("total".equals(datatype)) {
            _name = "totalRain";
        } else if ("everyday".equals(datatype)) {
            _name = "rain";
        }
        String final_name = _name;
        API.findRainDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldId,
               cropId,
                datarange, _name,
                new ApiCallBack<NameValuePair>() {
                    @Override
                    public void onResponse(ResultObj<NameValuePair> resultObj) {
                        if (resultObj.getCode() == 0) {
                            String fun = "";
                            if (datarange == 0){
                                if ("rain".equals(final_name)) {
                                    fun = "rainEveryDayYearByField";
                                } else if ("totalRain".equals(final_name)) {
                                    fun = "rainTotalYearByField";
                                }
                            } else if (datarange == 1) {
                                if ("rain".equals(final_name)) {
                                    fun = "rainEveryDayByCrop";
                                } else if ("totalRain".equals(final_name)) {
                                    fun = "rainTotalByCrop";
                                }
                            }
                            showChart(fun,resultObj.getList());
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
        setContentView(R.layout.activity_full_chart);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        back.setTypeface(MyApplication.iconTypeFace);

        charttype = getIntent().getStringExtra("charttype");
        datatype = getIntent().getStringExtra("datatype");
        fieldId = getIntent().getStringExtra("fieldId");
        cropId = getIntent().getStringExtra("cropId");

        initwebview();



    }


    public void showChart(String fun,List<NameValuePair> data){
        JSParamInfo<NameValuePair> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType(fun);
        jsParamInfo.setList(data);
        Log.v("jsParamInfo",new Gson().toJson(jsParamInfo));
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
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
                    getData();
                }
            }
        });

        webView.loadUrl("file:///android_asset/echart.html");
    }
}
