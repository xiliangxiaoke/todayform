package com.today.todayfarm.pages.tabs.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.HealthImgInfo;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.TimeAxisItemInfo;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
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


    private TextView btmenu;
    private WebView map;

    private ImageView geolocation;
    private NiceSpinner niceSpinner;

    private RecyclerView recyclerView;



    private RecyclerviewAdapter adapter;
    List<TimeAxisItemInfo> timeregion= new ArrayList<>();

    public AMapLocationClient mlocationClient = null;
    public AMapLocationClientOption option = new AMapLocationClientOption();


    int datatype = 3;//1- 健康监测  2-卫星影像  3-区划


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_map_fragment,container,false);

        btmenu = view.findViewById(R.id.menu);
        btmenu.setTypeface(MyApplication.iconTypeFace);
        map = view.findViewById(R.id.map);
        geolocation = view.findViewById(R.id.geolocation);

        adapter = new RecyclerviewAdapter(this.getContext(),timeregion);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);


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


        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent("openMenuActivity",""));
            }
        });

        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    //  获取健康监测数据
                    showHealthData();
                } else if (position == 1) {
                    // TODO 获取卫星影像数据
                    showStaliteData();
                } else if (position == 2) {
                    // TODO 获取作物区划图
                    showBlock();
                }
            }
        });
    }

    private void showBlock() {

    }

    private void showStaliteData() {

    }

    // 获取健康数据
    private void showHealthData() {
        API.findMyHealthImgsWeekdays(
                Hawk.get(HawkKey.TOKEN),
                new ApiCallBack<HealthImgInfo>() {
                    @Override
                    public void onResponse(ResultObj<HealthImgInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            // TODO 解析健康监测数据结果，展示到地图上
                            // 1 先计算出一个月的时间列表
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            // test 先减三个月 为了计算到有数据的时间段
                            calendar.add(Calendar.MONTH, -3);

                             timeregion = new ArrayList<>();

                            for (int i =0; i<30;i++){

                                calendar.add(Calendar.DATE,-1);
                                TimeAxisItemInfo info = new TimeAxisItemInfo();
                                info.setTimestamp(calendar.getTimeInMillis());
                                info.setDateText(calendar.get(Calendar.DATE));
                                timeregion.add(info);
                            }

                            // 往里插实际的影像数据
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                List<HealthImgInfo> healthImgInfos = resultObj.getList();
                                for (int i = 0; i < healthImgInfos.size(); i++) {
                                    insertHealthData2TimeRegion(timeregion,healthImgInfos.get(i));
                                }
                            }

                            // TODO 显示数据时间轴
                            adapter.notifyDataSetChanged();

                        }
                    }

                    private void insertHealthData2TimeRegion(List<TimeAxisItemInfo> timeregion, HealthImgInfo healthImgInfo) {
                        for (int i = 0; i < timeregion.size(); i++) {
                            //"2018-08-15"
                            Calendar caTimeRegion = Calendar.getInstance();
                            caTimeRegion.setTime(new Date(timeregion.get(i).getTimestamp()));
                            int TR_YEAR = caTimeRegion.get(Calendar.YEAR);
                            int TR_MONTH = caTimeRegion.get(Calendar.MONTH);
                            int TR_DATE = caTimeRegion.get(Calendar.DATE);
                            String[] ss = healthImgInfo.getHealthImgTime().split("-");
                            if (ss.length == 3) {
                                if (
                                        TR_YEAR == Integer.parseInt(ss[0])
                                        && TR_MONTH == Integer.parseInt(ss[1])
                                        && TR_DATE == Integer.parseInt(ss[2])
                                        ){
                                    if (timeregion.get(i).getHealthImgInfos() == null) {
                                        timeregion.get(i).setHealthImgInfos(new ArrayList<>());
                                    }
                                    timeregion.get(i).getHealthImgInfos().add(healthImgInfo);
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

        map.loadUrl("file:///android_asset/index.html");
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
        List<TimeAxisItemInfo> data;

        public RecyclerviewAdapter(Context context, List<TimeAxisItemInfo> data) {
            this.context = context;
            this.data = data;
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
            if (info.getHealthImgInfos() != null && info.getHealthImgInfos().size() > 0) {
                holder.datablock.setVisibility(View.VISIBLE);
            }else {
                holder.datablock.setVisibility(View.GONE);
            }

            holder.label.setText(info.getDateText());
            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 在地图上显示当前的影像数据，分是健康检测影像还是卫星影像，加个标志位
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;

            View datablock;
            TextView label;


            public Viewholder(View itemView) {
                super(itemView);

                panel = itemView.findViewById(R.id.panel);

                datablock = itemView.findViewById(R.id.datablock);
                label = itemView.findViewById(R.id.label);

            }
        }
    }

}
