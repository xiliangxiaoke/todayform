package com.today.todayfarm.pages.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
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

public class TempDetailActivity extends BaseActivity {

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
    CropInfo cropInfo = null;


    @OnClick(R.id.back)
    public void setBack(){
        this.finish();
    }

    @OnClick(R.id.showTotalDetail)
            public void showTotalDetail(){
        // TODO 打开累积图表 横屏
        Intent intent = new Intent();
        intent.setClass(this, FullChartActivity.class);
        intent.putExtra("charttype","temp");
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
        intent.putExtra("charttype","temp");
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
        back.setTypeface(MyApplication.iconTypeFace);

        fieldId = getIntent().getStringExtra("fieldId");
        fieldName = getIntent().getStringExtra("fieldName");


        title.setText(fieldName);
        totalcharttitle.setText("累积积温");
        everydaycharttitle.setText("每日积温");

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
        everydayChartWebview.loadUrl("file:///android_asset/echart.html");

    }

    private void initlistener() {
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cropInfo = croplist.get(position);

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
        API.findTemperatureDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldId,
                cropInfo!=null ? cropInfo.getCropId():null,
                type, "totalRain",
                new ApiCallBack<NameValuePair>() {
                    @Override
                    public void onResponse(ResultObj<NameValuePair> resultObj) {
                        if (resultObj.getCode() == 0) {
                            JSParamInfo<NameValuePair> jsParamInfo = new JSParamInfo<>();
                            if (type == 1){
                                //自种植以来数据
                                jsParamInfo.setType("tempTotalByCrop");
                            }else{
                                // 全年数据
                                jsParamInfo.setType("tempTotalYearByField");
                            }

                            jsParamInfo.setList(resultObj.getList());
                            WebUtil.callJS(totalChartWebview,new Gson().toJson(jsParamInfo));
                        }
                    }

                    @Override
                    public void onError(int code) {

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
                        if (resultObj.getCode() == 0) {
                            JSParamInfo<NameValuePair> jsParamInfo = new JSParamInfo<>();
                            if (type == 1){
                                //自种植以来数据
                                jsParamInfo.setType("tempEveryDayByCrop");
                            }else{
                                // 全年数据
                                jsParamInfo.setType("tempEveryDayYearByField");
                            }
                            jsParamInfo.setList(resultObj.getList());
                            WebUtil.callJS(everydayChartWebview,new Gson().toJson(jsParamInfo));
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }
}
