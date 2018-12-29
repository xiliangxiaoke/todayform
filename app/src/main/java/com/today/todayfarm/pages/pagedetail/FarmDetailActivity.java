package com.today.todayfarm.pages.pagedetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.AddNewCrop.AddNewCropActivity;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SoilInfo;
import com.today.todayfarm.dom.TotalRainAndTemp;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingBozhongActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingGuangaiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShifeiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShougeActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhengdiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhibaoActivity;
import com.today.todayfarm.pages.chart.TempDetailActivity;
import com.today.todayfarm.pages.createcrop.CreateCropActivity;
import com.today.todayfarm.pages.farmThingList.FarmThingListActivity;
import com.today.todayfarm.pages.chart.RainDetailActivity;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.pages.threeDaysWeather.ThreeDaysWeatherActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Common;
import com.today.todayfarm.util.WebUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 农田详情
 */
public class FarmDetailActivity extends Activity {

    String fieldid = null;
    FieldInfo fieldInfo = null;

    @BindView(R.id.map)
    WebView map;

    @BindView(R.id.farmname)
    TextView farmname;

    @BindView(R.id.farmarea)
    TextView farmarea;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.nocroptip)
    TextView nocroptip;

    @BindView(R.id.cropdatapanel)
    LinearLayout cropdatapanel;

    @BindView(R.id.showallfarmcrop)
    TextView showallfarmcrop;

    @BindView(R.id.farmthingpanel)
    CardView farmthingpanel;

    @BindView(R.id.farmthing)
    TextView farmthingname;

    @BindView(R.id.farmthingdate)
    TextView farmthingdate;

    @BindView(R.id.farmthingtab)
    RelativeLayout farmthingtab;

    @BindView(R.id.showallfarmthing)
    TextView showallfarmthing;

    @BindView(R.id.rainvalue) TextView rainvalue;
    @BindView(R.id.raintime) TextView raintime;
    @BindView(R.id.raindetail) TextView raindetail;

    @BindView(R.id.tempvalue) TextView tempvalue;
    @BindView(R.id.temptime) TextView temptime;
    @BindView(R.id.tempdetail) TextView tempdetail;

    @BindView(R.id.threedaysdata) TextView threedaysdata;

    @BindView(R.id.oiltype) TextView oiltype;
    @BindView(R.id.oilcfirst) TextView oilcfirst;
    @BindView(R.id.oilcsecond) TextView oilcsecond;
    @BindView(R.id.oilcthird) TextView oilcthird;
    @BindView(R.id.oilph) TextView oilph;




    @BindView(R.id.farmcroppanel)
    CardView farmcroppanel;

    @BindView(R.id.farmcropname)
    TextView farmcropname;

    @BindView(R.id.farmcropdate)
    TextView farmcropdate;

    @BindView(R.id.farmcropdatatab)
    RelativeLayout farmcropdatatab;

    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.edit)
    public void edit() {
        // TODO
    }

    @OnClick(R.id.raindetail)
    public void showRaindetail() {
        // TODO 显示降雨详情
        Intent intent = new Intent();
        intent.setClass(this,RainDetailActivity.class);
        intent.putExtra("fieldId",fieldid);
        intent.putExtra("fieldName",fieldInfo.getFieldName());
        this.startActivity(intent);
    }

    @OnClick(R.id.tempdetail)
    public void showTempdetail() {
        // TODO 显示温度详情
        Intent intent = new Intent();
        intent.setClass(this,TempDetailActivity.class);
        intent.putExtra("fieldId",fieldid);
        intent.putExtra("fieldName",fieldInfo.getFieldName());
        this.startActivity(intent);
    }

    @OnClick(R.id.threedaysdata)
    public void showThreedaysdata(){
        // TODO 显示三天预测结果

        Intent intent = new Intent();
        intent.setClass(this, ThreeDaysWeatherActivity.class);
        intent.putExtra("fieldid",fieldid);
        this.startActivity(intent);

    }



    public static final int REQUEST_CODE_CREATE_CROP_ACTIVITY = 1001;


    FieldThingInfo fieldThingInfo = null;
    CropInfo cropInfo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        fieldid = intent.getStringExtra("fieldid");

        Log.v("fieldid:",fieldid);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);


        initwebview();

        // 获取农田信息
        API.getFieldById(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                new ApiCallBack<FieldInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldInfo> resultObj) {
                        if (resultObj.getCode()==0){
                            fieldInfo = resultObj.getObject();
                            // 向地图 中加载农田边界

                            showfieldboundary(fieldInfo.getFieldBoundary());

                            // 显示名称
                            farmname.setText(fieldInfo.getFieldName());

                            farmarea.setText(Common.getAreaStr(fieldInfo.getFieldArea())+"亩");



                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );

        // 获取农事记录信息 获取总数all,及最新的一条
        updateFieldthing();

        // 获取积温和降水统计信息
        getTotalRainAndTempData();



        // 获取土壤信息
        getSoilinfo();

        // TODO 获取注记信息
        updateZhuji();

        // 获取作物信息
        updateCropInfo();

    }

    private void getSoilinfo() {
        API.getSoilsInfo(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                new ApiCallBack<SoilInfo>() {
                    @Override
                    public void onResponse(ResultObj<SoilInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            SoilInfo soilInfo = resultObj.getObject();

                            oiltype.setText("主要土壤类型："+soilInfo.getMajorName());
                            oilcfirst.setText("地表土有机碳含量："+soilInfo.getSl1()+"克/千克");
                            oilcsecond.setText("5cm土有机碳含量："+soilInfo.getSl2()+"克/千克");
                            oilcthird.setText("15cm土有机碳含量："+soilInfo.getSl3()+"克/千克");
                            oilph.setText("土壤PH值："+soilInfo.getPhValue());
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void getRainData() {

    }

    private void getTotalRainAndTempData() {

        API.findClimateDatas(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                new ApiCallBack<TotalRainAndTemp>() {
                    @Override
                    public void onResponse(ResultObj<TotalRainAndTemp> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getProp() != null) {
                                rainvalue.setText(resultObj.getProp().getRain()+"毫米");
                                tempvalue.setText(resultObj.getProp().getTemperature()+" GDD");
                            }
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void updateZhuji() {

    }

    private void updateFieldthing() {
        API.findFiledAllActivity(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                1, 1,
                new ApiCallBack<FieldThingInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldThingInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getAll() > 0) {

                                fieldThingInfo = resultObj.getList().get(0);
                                farmthingname.setText(fieldThingInfo.getType());
                                if (fieldThingInfo.getEndDate() != null && fieldThingInfo.getEndDate().length() > 0) {
                                    farmthingdate.setText(fieldThingInfo.getEndDate()+"完成");
                                } else {
                                    farmthingdate.setText(fieldThingInfo.getStartDate()+"开始");
                                }

                                farmthingtab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //TODO 点击打开当前的农事详情
                                        Intent intent = null;
                                        String type = fieldThingInfo.getType();
                                        if("播种".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingBozhongActivity.class);
                                        }else if ("灌溉".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingGuangaiActivity.class);
                                        }else if ("施肥".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingShifeiActivity.class);
                                        }else if ("收割".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingShougeActivity.class);
                                        }else if ("整地".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingZhengdiActivity.class);
                                        }else if ("植保".equals(type)){
                                            intent = new Intent(FarmDetailActivity.this, EditFarmthingZhibaoActivity.class);
                                        }


                                        intent.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
                                        intent.putExtra("id",fieldThingInfo.getId());
                                        intent.putExtra("fieldname",fieldThingInfo.getFieldName());
                                        FarmDetailActivity.this.startActivity(intent);
                                    }
                                });

                                showallfarmthing.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // TODO 打开农事列表页面
                                        Intent intent1 = new Intent();
                                        intent1.setClass(FarmDetailActivity.this, FarmThingListActivity.class);
                                        intent1.putExtra("fieldid",fieldid);
                                        intent1.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
                                        FarmDetailActivity.this.startActivity(intent1);
                                    }
                                });

                            } else {
                                // 提示当前无农事
                                farmthingname.setText("暂无相关农事记录");
                                showallfarmthing.setText("添加新农事信息");
                                showallfarmthing.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //TODO 跳转到添加新农事页面
                                        Intent intent = new Intent();
                                        intent.setClass(FarmDetailActivity.this, SelectFarmActivity.class);
                                        FarmDetailActivity.this.startActivity(intent);
                                    }
                                });

                            }
                        }else{
                            farmthingname.setText("暂无相关农事记录");
                            showallfarmthing.setText("添加新农事信息");
                            showallfarmthing.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO 跳转到添加新农事页面
                                    Intent intent = new Intent();
                                    intent.setClass(FarmDetailActivity.this, SelectFarmActivity.class);
                                    FarmDetailActivity.this.startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void showfieldboundary(String boundary) {
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        Log.v("boundary:",boundary);

        try{
            boundaryInfo2Js = new Gson().fromJson(boundary,BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);



        //Log.v("jsParamInfo:",new Gson().toJson(jsParamInfo));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
            }
        }, 1000);//3秒后执行Runnable中的run方法
    }

    private void shownocroptip() {
        nocroptip.setVisibility(View.VISIBLE);
        cropdatapanel.setVisibility(View.GONE);
        nocroptip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加新作物
                Intent intent = new Intent(FarmDetailActivity.this, AddNewCropActivity.class);
                intent.putExtra("fieldid",fieldid);
                FarmDetailActivity.this.startActivity(intent);
            }
        });
    }

    private void initwebview() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        map.loadUrl("file:///android_asset/index.html");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE_CROP_ACTIVITY && resultCode == CreateCropActivity.RESULT_CODE_CREATE_CROP) {
            //刚创建完新的作物，刷新列表显示
            updateCropInfo();
        }
    }

    private void updateCropInfo() {
        API.findCropInfosByFieldId(
                Hawk.get(HawkKey.TOKEN), fieldid,
                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                List<CropInfo> croplist = resultObj.getList();
                                if (croplist!=null && croplist.size()>0){

                                    cropInfo= croplist.get(0);
                                    nocroptip.setVisibility(View.INVISIBLE);
                                    cropdatapanel.setVisibility(View.VISIBLE);
                                    farmcropname.setText(cropInfo.getCropName());
                                    farmcropdate.setText(cropInfo.getPlantYear()+"年");
                                    showallfarmcrop.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // 显示作物列表
                                            Intent intent = new Intent(FarmDetailActivity.this, SelectCropActivity.class);
                                            intent.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
                                            FarmDetailActivity.this.startActivity(intent);
                                        }
                                    });


                                }else {
                                    // 提示暂无农作物
                                    nocroptip.setVisibility(View.VISIBLE);
                                    cropdatapanel.setVisibility(View.INVISIBLE);
                                    nocroptip.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // TODO 添加新作物
                                            Intent intent = new Intent(FarmDetailActivity.this, CreateCropActivity.class);
                                            intent.putExtra("fieldid", fieldid);
                                            FarmDetailActivity.this.startActivityForResult(intent, REQUEST_CODE_CREATE_CROP_ACTIVITY);
                                        }
                                    });

                                }

                            }


                        }


                    }

                    @Override
                    public void onError(int code) {

                    }
                }

        );
    }
}
