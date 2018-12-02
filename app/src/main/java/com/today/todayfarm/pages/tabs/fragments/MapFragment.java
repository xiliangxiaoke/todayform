package com.today.todayfarm.pages.tabs.fragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.util.WebUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页地图模块
 */

public class MapFragment extends Fragment implements AMapLocationListener{


    private TextView btmenu;
    private WebView map;

    private ImageView geolocation;


    public AMapLocationClient mlocationClient = null;
    public AMapLocationClientOption option = new AMapLocationClientOption();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_map_fragment,container,false);

        btmenu = view.findViewById(R.id.menu);
        btmenu.setTypeface(MyApplication.iconTypeFace);
        map = view.findViewById(R.id.map);
        geolocation = view.findViewById(R.id.geolocation);

        //加载地图js
        loadmap();

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
}
