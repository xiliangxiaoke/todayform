package com.today.todayfarm.pages.chart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.NameValuePair;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.WebUtil;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RainDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.totalcharttitle)
    TextView totalcharttitle;

    @BindView(R.id.everydaycharttitle)
    TextView everydaycharttitle;
    @BindView(R.id.totalChartWebview)
    WebView totalChartWebview;
    @BindView(R.id.everydayChartWebview)
    WebView everydayChartWebview;

    @BindView(R.id.niceSpinner)
    NiceSpinner niceSpinner;
    private List<CropInfo> croplist = new ArrayList<>();
    private CropInfo cropInfo = null;


    JSParamInfo<NameValuePair> jsParamInfo_total = null;
    JSParamInfo<NameValuePair> jsParamInfo_everyDay = null;
    boolean webview_total_load_finished = false;
    boolean webview_everyday_load_finished = false;


    @OnClick(R.id.back)
    public void setBack(){
        this.finish();
    }

    @OnClick(R.id.showTotalDetail)
    public void showTotalDetail() {

        // TODO 打开累积图表 横屏

        Intent intent = new Intent();
        intent.setClass(this, FullChartActivity.class);
        intent.putExtra("charttype","rain");
        intent.putExtra("datatype","total");
        intent.putExtra("fieldId",fieldId);
        intent.putExtra("cropId",cropInfo!=null?cropInfo.getCropId():null);
        this.startActivity(intent);

    }

    @OnClick(R.id.showEverydayDetail)
            public void showEverydayDetail(){
        // TODO 打开每日图表 横屏
        Intent intent = new Intent();
        intent.setClass(this, FullChartActivity.class);
        intent.putExtra("charttype","rain");
        intent.putExtra("datatype","everyday");
        intent.putExtra("fieldId",fieldId);
        intent.putExtra("cropId",cropInfo!=null?cropInfo.getCropId():null);
        this.startActivity(intent);
    }

    String fieldId=null;
    String fieldName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        fieldId = getIntent().getStringExtra("fieldId");
        fieldName = getIntent().getStringExtra("fieldName");
        back.setTypeface(MyApplication.iconTypeFace);


        title.setText(fieldName);
        totalcharttitle.setText("累积降水量");
        everydaycharttitle.setText("每日降水量");

        initWebview();


        // add crop list
        initSpinnerData();



        initlistener();
    }

    private void initWebview() {
        WebSettings webSettings1 = totalChartWebview.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        webSettings1.setJavaScriptCanOpenWindowsAutomatically(true);
        totalChartWebview.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
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
        totalChartWebview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && webview_total_load_finished ==false){
                    Log.v("webview","totalChartWebview 加载完成");
                    webview_total_load_finished = true;
                    if (jsParamInfo_total != null) {
                        Log.v("加载图表 total: ",new Gson().toJson(jsParamInfo_total));
                        WebUtil.callJS(totalChartWebview, new Gson().toJson(jsParamInfo_total));
                    }
                }
            }
        });
        totalChartWebview.loadUrl("file:///android_asset/echart.html");


        WebSettings webSettings2 = everydayChartWebview.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webSettings2.setJavaScriptCanOpenWindowsAutomatically(true);
        everydayChartWebview.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
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
        everydayChartWebview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && webview_everyday_load_finished==false){
                    Log.v("webview","everydayChartWebview 加载完成");
                    webview_everyday_load_finished = true;
                    if (jsParamInfo_everyDay != null) {
                        Log.v("加载图表 everyday: ",new Gson().toJson(jsParamInfo_everyDay));
                        WebUtil.callJS(everydayChartWebview, new Gson().toJson(jsParamInfo_everyDay));
                    }
                }
            }
        });
        everydayChartWebview.loadUrl("file:///android_asset/echart.html");

    }

    private void initlistener() {
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 cropInfo = croplist.get(position);
                webview_total_load_finished = false;
                webview_everyday_load_finished = false;


                // TODO 根据选择的作物更新图表数据
                initchartdata(cropInfo,1);
            }
        });
    }

    private void initSpinnerData() {


        API.findCropInfosByFieldId(
                Hawk.get(HawkKey.TOKEN),
                fieldId,

                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
                                croplist = resultObj.getList();

                                List<String> spinnerdata = new ArrayList<>();
                                for (int i = 0; i < croplist.size(); i++) {
                                    spinnerdata.add(
                                            croplist.get(i).getCropName()+" "+croplist.get(i).getPlantYear()+"年"
                                    );
                                }

                                niceSpinner.attachDataSource(spinnerdata);

                                cropInfo = croplist.get(0);

                                // 初始化图表数据
                                initchartdata(cropInfo,1);

                            }else{
                                initchartdata(null,0);
                            }
                        }else{
                            initchartdata(null,0);
                        }
                    }

                    @Override
                    public void onError(int code) {
                        initchartdata(null,0);
                    }
                }
        );




    }



    private void initchartdata(CropInfo cropInfo,int type) {

        // 获取累积数据
        API.findRainDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldId,
                cropInfo!=null ? cropInfo.getCropId():null,
                type, "totalRain",
                new ApiCallBack<NameValuePair>() {
                    @Override
                    public void onResponse(ResultObj<NameValuePair> resultObj) {
                        JSParamInfo<NameValuePair> jsParamInfo = new JSParamInfo<>();
                        if (resultObj.getCode() == 0) {

                            if (type == 1) {
                                //自种植以来数据
                                jsParamInfo.setType("rainTotalByCrop");
                            } else {
                                // 全年数据
                                jsParamInfo.setType("rainTotalYearByField");
                            }
                            jsParamInfo.setList(resultObj.getList());
                            Log.v("webview","调用 callJS 1");



                        } else {
                            // 显示空数据

                            if (type == 1){
                                //自种植以来数据
                                jsParamInfo.setType("rainTotalByCrop");
                            }else{
                                // 全年数据
                                jsParamInfo.setType("rainTotalYearByField");
                            }
                            jsParamInfo.setList(getEmptylist());
                            Log.v("webview","调用 callJS 2");

                        }
                        jsParamInfo_total = jsParamInfo;
                        Log.v("jsParamInfo_total",new Gson().toJson(jsParamInfo_total));
                        if (webview_total_load_finished == true) {
                            WebUtil.callJS(totalChartWebview, new Gson().toJson(jsParamInfo));
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }

                    private List<NameValuePair> getEmptylist() {
                        List<NameValuePair> list = new ArrayList<NameValuePair>();
                        for (int i=1;i<10;i++) {
                            NameValuePair pair = new NameValuePair();
                            pair.setName(""+i);
                            pair.setValue(0);
                            list.add(pair);
                        }
                        return list;
                    }
                }
        );

        // 获取每日数据
        API.findRainDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldId,
                cropInfo!=null ? cropInfo.getCropId():null,
                type, "rain",
                new ApiCallBack<NameValuePair>() {
                    @Override
                    public void onResponse(ResultObj<NameValuePair> resultObj) {
                        JSParamInfo<NameValuePair> jsParamInfo = new JSParamInfo<>();
                        if (resultObj.getCode() == 0) {

                            if (type == 1) {
                                //自种植以来数据
                                jsParamInfo.setType("rainEveryDayByCrop");
                            } else {
                                // 全年数据
                                jsParamInfo.setType("rainEveryDayYearByField");
                            }
                            jsParamInfo.setList(resultObj.getList());
                            Log.v("webview","调用 callJS 3");

                        } else {
                            // 显示空数据

                            if (type == 1){
                                //自种植以来数据
                                jsParamInfo.setType("rainEveryDayByCrop");
                            }else{
                                // 全年数据
                                jsParamInfo.setType("rainEveryDayYearByField");
                            }
                            jsParamInfo.setList(getEmptylist());
                            Log.v("webview","调用 callJS 4");

                        }

                        jsParamInfo_everyDay = jsParamInfo;
                        Log.v("jsParamInfo_everyDay",new Gson().toJson(jsParamInfo_everyDay));
                        if (webview_everyday_load_finished == true) {
                            WebUtil.callJS(everydayChartWebview, new Gson().toJson(jsParamInfo));
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }

                    private List<NameValuePair> getEmptylist() {
                        List<NameValuePair> list = new ArrayList<NameValuePair>();
                        for (int i=1;i<10;i++) {
                            NameValuePair pair = new NameValuePair();
                            pair.setName(""+i);
                            pair.setValue(0);
                            list.add(pair);
                        }
                        return list;
                    }
                }
        );
    }
}
