package com.today.todayfarm.pages.tabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.HealthImgInfo;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SatellateImgInfo;
import com.today.todayfarm.dom.TimeAxisItemInfo;
import com.today.todayfarm.pages.AddFarmMap.AddFarm2MapActivity;
import com.today.todayfarm.pages.createFarm.CreateFarmActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.ToastUtil;
import com.today.todayfarm.util.WebUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页地图模块
 */

public class MapFragment extends Fragment implements AMapLocationListener{


//    private TextView btmenu;
    private WebView map;

    private ImageView geolocation;
    private ImageView fullmap;
    private NiceSpinner niceSpinner;

    private RecyclerView recyclerView;

    //TextView datetv;

    LinearLayout maplegend;


    private RecyclerviewAdapter adapter;
    List<TimeAxisItemInfo> timeregion= new ArrayList<>();

    public AMapLocationClient mlocationClient = null;
    public AMapLocationClientOption option = new AMapLocationClientOption();


    int datatype = 1;//1- 健康监测  2-卫星影像  3-区划
    private boolean firstloaded = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_map_fragment,container,false);

//        btmenu = view.findViewById(R.id.menu);
//        btmenu.setTypeface(MyApplication.iconTypeFace);
        map = view.findViewById(R.id.map);
        geolocation = view.findViewById(R.id.geolocation);
        fullmap = view.findViewById(R.id.fullmap);
//        datetv = view.findViewById(R.id.date);
        maplegend = view.findViewById(R.id.maplegend);

        adapter = new RecyclerviewAdapter(this.getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //加载地图js
        loadmap();

        // spinner
        niceSpinner = (NiceSpinner) view.findViewById(R.id.niceSpinner);
        List<String> dataset = new LinkedList<>(Arrays.asList(
                "健康监测图",
                "卫星影像图",
                "作物区划图"
        ));
        niceSpinner.attachDataSource(dataset);

        mlocationClient = new AMapLocationClient(getContext());
        mlocationClient.setLocationListener(this);
        //定位参数
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);//定位场景 签到
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);// 定位模式 高精度
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true);

        initlistener();


        niceSpinner.setSelectedIndex(2);


        // 默认定位一次
        //location do once
//        if(null != mlocationClient){
//            mlocationClient.setLocationOption(option);
//            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            mlocationClient.stopLocation();
//            mlocationClient.startLocation();
//        }

        return view;
    }

    private void initlistener() {
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //location do once
                if(null != mlocationClient){
                    mlocationClient.setLocationOption(option);
                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                    mlocationClient.stopLocation();
                    mlocationClient.startLocation();
                }
            }
        });

        fullmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // todo: 地图内容全图显示
                fullextent();
            }
        });


//        btmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MessageEvent("openMenuActivity",""));
//            }
//        });

        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    //  获取健康监测数据
                    maplegend.setVisibility(View.VISIBLE);
                    datatype = 1;
                    showHealthData();
                    recyclerView.setVisibility(View.VISIBLE);
//                    datetv.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    // TODO 获取卫星影像数据
                    maplegend.setVisibility(View.INVISIBLE);
                    datatype = 2;
                    showStaliteData();
                    recyclerView.setVisibility(View.VISIBLE);
//                    datetv.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    // 获取作物区划图
                    maplegend.setVisibility(View.INVISIBLE);
                    datatype = 3;
                    timeregion.clear();
                    showBlock();
                    recyclerView.setVisibility(View.GONE);
//                    datetv.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void showBlock() {

        clearmap();

        API.findMyFields(
                Hawk.get(HawkKey.TOKEN),
                1,
                1000,
                new ApiCallBack<FieldInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldInfo> resultObj) {
                        //Log.v("fieldinfolist:",new Gson().toJson(resultObj));
                        if (resultObj.getCode()==0){
                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
                                // 显示到地图上




                                JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
                                jsParamInfo.setType("showallboundary");

                                List<BoundaryInfo2Js> bs = new ArrayList<>();

                                for (int i=0;i<resultObj.getList().size();i++) {
                                    BoundaryInfo2Js boundaryInfo2Js =null;
                                    try{
                                        boundaryInfo2Js = new Gson().fromJson(resultObj.getList().get(i).getFieldBoundary(),BoundaryInfo2Js.class);
                                        boundaryInfo2Js.setMarkertitle(resultObj.getList().get(i).getCropName());
                                        boundaryInfo2Js.setColor(getCropColor(resultObj.getList().get(i).getCropName()));
                                    }catch (Exception e){
                                        Log.e("boundary err",e.getMessage());
                                    }
                                    bs.add(boundaryInfo2Js);
                                }






                                jsParamInfo.setList(bs);

                                WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
                            }

                        }

                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private String getCropColor(String cropName) {
        String color = "#ffffff";
        if ("小麦".equals(cropName)){
            color = "#6B8E23";
        }else if ("玉米".equals(cropName)){
            color = "#EEB422";
        }else if ("水稻".equals(cropName)){
            color = "#F8F8FF";
        }else if ("棉花".equals(cropName)){
            color = "#ffffff";
        }else if ("番茄".equals(cropName)){
            color = "#FC665C";
        }else if ("鹰嘴豆".equals(cropName)){
            color = "#E8A878";
        }else if ("洋葱".equals(cropName)){
            color = "#8C2079";
        }else if ("辣椒".equals(cropName)){
            color = "#E0010F";
        }else if ("菜花".equals(cropName)){
            color = "#F9F3B9";
        }else if ("卷心菜".equals(cropName)){
            color = "#6FC164";
        }else if ("土豆".equals(cropName)){
            color = "#BF9A50";
        }else if ("绿豆".equals(cropName)){
            color = "#517A50";
        }else if ("向日葵".equals(cropName)){
            color = "#FACE08";
        }else if ("大豆".equals(cropName)){
            color = "#FDE474";
        }else if ("甘蔗".equals(cropName)){
            color = "#529435";
        }else if ("花生".equals(cropName)){
            color = "#C1B296";
        }else if ("西瓜".equals(cropName)){
            color = "#F7A394";
        }
        return color;
    }

    private void showStaliteData() {
        clearmap();
        API.findSatellateImgsWeekdays(
                Hawk.get(HawkKey.TOKEN),
                new ApiCallBack<SatellateImgInfo>() {
                    @Override
                    public void onResponse(ResultObj<SatellateImgInfo> resultObj) {
                        // 解析卫星影像数据显示到地图上
                        if (resultObj.getCode() == 0) {
                            // 1 先计算出一个月的时间列表
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            //calendar.add(Calendar.MONTH, -1);

                            timeregion = new ArrayList<>();

                            calendar.add(Calendar.DATE,-30);

                            for (int i =0; i<30;i++){

                                calendar.add(Calendar.DATE,1);
                                TimeAxisItemInfo info = new TimeAxisItemInfo();
                                info.setTimestamp(calendar.getTimeInMillis());

                                info.setDateText(calendar.get(Calendar.DATE));
//                                info.setMonthText(calendar.get(Calendar.MONTH)+1);
//                                info.setYear(calendar.get(Calendar.YEAR));
                                info.setSelected(false);
                                timeregion.add(info);
                            }

                            for (int j = 0;j<timeregion.size();j++) {
                                if (timeregion.get(j).getDateText() == 1) {

                                    timeregion.get(j).setMonthText(calendar.get(Calendar.MONTH)+1);

                                    if (j > 0) {

                                        timeregion.get(j-1).setYear(calendar.get(Calendar.YEAR));
                                    }

                                }
                            }

                            // 显示日期描述
//                            datetv.setText(
//                                    calendar.get(Calendar.YEAR)+"年"+
//                                            (calendar.get(Calendar.MONTH)+1)+"月"
//                            );

                            // 往里插实际的影像数据
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                List<SatellateImgInfo> satellateImgInfos = resultObj.getList();
                                for (int i = 0; i < satellateImgInfos.size(); i++) {
                                    insertSatellateData2TimeRegion(timeregion,satellateImgInfos.get(i));
                                }
                            }

                            // 显示数据时间轴
                            adapter.notifyDataSetChanged();
                        }
                    }

                    private void insertSatellateData2TimeRegion(List<TimeAxisItemInfo> timeregion, SatellateImgInfo satellateImgInfo) {
                        String[] ss = satellateImgInfo.getSatellateImgTime().split("-");
                        int h_year = 0;
                        int h_month = 0;
                        int h_date= 0;
                        if (ss.length == 3) {
                            h_year = Integer.parseInt(ss[0]);
                            h_month = Integer.parseInt(ss[1]);
                            h_date = Integer.parseInt(ss[2]);
                        }
                        for (int i = 0; i < timeregion.size(); i++) {
                            //"2018-08-15"
                            Calendar caTimeRegion = Calendar.getInstance();
                            caTimeRegion.setTime(new Date(timeregion.get(i).getTimestamp()));
                            int TR_YEAR = caTimeRegion.get(Calendar.YEAR);
                            int TR_MONTH = caTimeRegion.get(Calendar.MONTH)+1;
                            int TR_DATE = caTimeRegion.get(Calendar.DATE);


                            if (
                                    TR_YEAR == h_year
                                            && TR_MONTH == h_month
                                            && TR_DATE == h_date
                                    ){
                                if (timeregion.get(i).getSatellateImgInfos() == null) {
                                    timeregion.get(i).setSatellateImgInfos(new ArrayList<>());
                                }
                                timeregion.get(i).getSatellateImgInfos().add(satellateImgInfo);
                            }

                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    // 获取健康数据
    private void showHealthData() {
        clearmap();
        API.findMyHealthImgsWeekdays(
                Hawk.get(HawkKey.TOKEN),
                new ApiCallBack<HealthImgInfo>() {
                    @Override
                    public void onResponse(ResultObj<HealthImgInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //  解析健康监测数据结果，展示到地图上
                            // 1 先计算出一个月的时间列表
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());


                            timeregion = new ArrayList<>();

                            calendar.add(Calendar.DATE,-30);

                            for (int i =0; i<30;i++){

                                calendar.add(Calendar.DATE,1);
                                TimeAxisItemInfo info = new TimeAxisItemInfo();
                                info.setTimestamp(calendar.getTimeInMillis());

                                info.setDateText(calendar.get(Calendar.DATE));


                                info.setSelected(false);
                                timeregion.add(info);
                            }

                            for (int j = 0;j<timeregion.size();j++) {
                                if (timeregion.get(j).getDateText() == 1) {

                                    timeregion.get(j).setMonthText(calendar.get(Calendar.MONTH)+1);

                                    if (j > 0) {

                                        timeregion.get(j-1).setYear(calendar.get(Calendar.YEAR));
                                    }

                                }
                            }

                            // 显示日期描述
//                            datetv.setText(
//                                    calendar.get(Calendar.YEAR)+"年"+
//                                            (calendar.get(Calendar.MONTH)+1)+"月"
//                            );

                            // 往里插实际的影像数据
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                List<HealthImgInfo> healthImgInfos = resultObj.getList();
                                for (int i = 0; i < healthImgInfos.size(); i++) {
                                    insertHealthData2TimeRegion(timeregion,healthImgInfos.get(i));
                                }
                            }

                            // 显示数据时间轴
                            adapter.notifyDataSetChanged();





                        }
                    }

                    private void insertHealthData2TimeRegion(List<TimeAxisItemInfo> timeregion, HealthImgInfo healthImgInfo) {
                        String[] ss = healthImgInfo.getHealthImgTime().split("-");
                        int h_year = 0;
                        int h_month = 0;
                        int h_date= 0;
                        if (ss.length == 3) {
                            h_year = Integer.parseInt(ss[0]);
                            h_month = Integer.parseInt(ss[1]);
                            h_date = Integer.parseInt(ss[2]);
                        }
                        for (int i = 0; i < timeregion.size(); i++) {
                            //"2018-08-15"
                            Calendar caTimeRegion = Calendar.getInstance();
                            caTimeRegion.setTime(new Date(timeregion.get(i).getTimestamp()));
                            int TR_YEAR = caTimeRegion.get(Calendar.YEAR);
                            int TR_MONTH = caTimeRegion.get(Calendar.MONTH)+1;
                            int TR_DATE = caTimeRegion.get(Calendar.DATE);


                                if (
                                        TR_YEAR == h_year
                                        && TR_MONTH == h_month
                                        && TR_DATE == h_date
                                        ){
                                    if (timeregion.get(i).getHealthImgInfos() == null) {
                                        timeregion.get(i).setHealthImgInfos(new ArrayList<>());
                                    }
                                    timeregion.get(i).getHealthImgInfos().add(healthImgInfo);
                                }

                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }


    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //EventBus.getDefault().unregister(this);
    }

    private void loadmap() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        map.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && firstloaded == false){
                    Log.v("webview","totalChartWebview 加载完成");
                    firstloaded = true;
                    showBlock();
                }
            }
        });
        map.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
            @Override
            public void callback(String json) {
                if (json!=null){
                    JS2AndroidParam js2AndroidParam = new Gson().fromJson(json,JS2AndroidParam.class);
                    if (js2AndroidParam!=null){
                        if ("error".equals(js2AndroidParam.getType())){
                            Log.e("ERRORSSSS",js2AndroidParam.getValue());
//                            Log.e("ERRORSSSS",js2AndroidParam.getValue());
//                            new SweetAlertDialog(MapFragment.this.getContext())
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


    public void clearmap(){
        JSParamInfo<Object> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("clearall");
        WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
    }


    public void fullextent(){
        JSParamInfo<Object> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("fullextent");
        WebUtil.callJS(map,new Gson().toJson(jsParamInfo));
    }

    @Override
    public void onDestroyView() {


        super.onDestroyView();
    }


    // AMaplocationListener
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

    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder>{

        Context context;


        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_time_axis,parent,false);
            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, int position) {

            TimeAxisItemInfo info = timeregion.get(position);
            if (datatype == 1){
                if (info.getHealthImgInfos() != null && info.getHealthImgInfos().size() > 0) {
                    holder.datablock.setVisibility(View.VISIBLE);
                }else {
                    holder.datablock.setVisibility(View.GONE);
                }
            }else if (datatype ==2){
                if (info.getSatellateImgInfos() != null && info.getSatellateImgInfos().size() > 0) {
                    holder.datablock.setVisibility(View.VISIBLE);
                }else {
                    holder.datablock.setVisibility(View.GONE);
                }
            }


            holder.label.setText(""+info.getDateText());
            // 如果是1号显示日期
            if (info.getDateText() == 1) {
                //holder.label.setText(info.getMonthText()+"-"+info.getDateText());
                holder.yeardevide.setVisibility(View.VISIBLE);

            }else{
                //holder.label.setText(""+info.getDateText());
                holder.yeardevide.setVisibility(View.GONE);

            }

            if (info.getMonthText() > 0) {
                holder.month.setText("年"+info.getMonthText()+"月");
            }else{
                holder.month.setText("");
            }

            if (info.getYear() > 0) {
                holder.year.setText(""+info.getYear());
            }else{
                holder.year.setText("");
            }

            if (info.isSelected()){
                holder.selectdatablock.setVisibility(View.VISIBLE);
            }else{
                holder.selectdatablock.setVisibility(View.GONE);
            }

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  在地图上显示当前的影像数据，分是健康检测影像还是卫星影像，加个标志位
                    if (datatype == 1){
                        //如果有健康监测的数据就显示到地图上，没有提示没有
                        if (info.getHealthImgInfos() != null && info.getHealthImgInfos().size() > 0) {
                            JSParamInfo<HealthImgInfo> jsParamInfo = new JSParamInfo<>();
                            jsParamInfo.setType("showhealthimg");
                            jsParamInfo.setList(info.getHealthImgInfos());
                            WebUtil.callJS(map,new Gson().toJson(jsParamInfo));

                            // 更新选中样式
                            for (int i=0;i<timeregion.size();i++) {
                                timeregion.get(i).setSelected(false);
                            }
                            info.setSelected(true);
                            notifyDataSetChanged();

                        } else {
                            ToastUtil.show(MapFragment.this.getContext(),"该日无影像数据！");
                        }
                    } else if (datatype == 2) {
                        // TODO: 如果有卫星影像的数据就显示卫星影像的数据，没有提示没有
                        if (info.getSatellateImgInfos() != null && info.getSatellateImgInfos().size() > 0) {
                            JSParamInfo<SatellateImgInfo> jsParamInfo = new JSParamInfo<>();
                            jsParamInfo.setType("showrsimg");
                            jsParamInfo.setList(info.getSatellateImgInfos());
                            WebUtil.callJS(map,new Gson().toJson(jsParamInfo));


                            // 更新选中样式
                            for (int i=0;i<timeregion.size();i++) {
                                timeregion.get(i).setSelected(false);
                            }
                            info.setSelected(true);
                            notifyDataSetChanged();


                        } else {
                            ToastUtil.show(MapFragment.this.getContext(),"该日无影像数据！");
                        }
                    } else {

                    }
                }
            });



        }

        @Override
        public int getItemCount() {
            return timeregion.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;

            View datablock;
            TextView label;

            RelativeLayout selectdatablock;
            View yeardevide;
            TextView year;
            TextView month;


            public Viewholder(View itemView) {
                super(itemView);

                panel = itemView.findViewById(R.id.panel);

                datablock = itemView.findViewById(R.id.datablock);
                label = itemView.findViewById(R.id.label);
                selectdatablock = itemView.findViewById(R.id.selectDataBlock);
                yeardevide = itemView.findViewById(R.id.yeardevide);
                year = itemView.findViewById(R.id.year);
                month = itemView.findViewById(R.id.month);

            }
        }
    }

}
