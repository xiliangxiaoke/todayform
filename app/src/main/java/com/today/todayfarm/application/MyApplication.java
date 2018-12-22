package com.today.todayfarm.application;

import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
//import com.mapbox.mapboxsdk.Mapbox;
//import com.tencent.smtt.sdk.QbSdk;
import com.orhanobut.hawk.Hawk;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class MyApplication extends MultiDexApplication {

    public static String token = null;
    public static Typeface iconTypeFace = null;


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        Hawk.init(this).build();

        iconTypeFace = Typeface.createFromAsset(getAssets(),"font/fontawesome-webfont.ttf");

        // youmeng
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,null);
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        MobclickAgent.setScenarioType(this,MobclickAgent.EScenarioType.E_UM_NORMAL);


        // mapbox access token
//        Mapbox.getInstance(getApplicationContext(),getString(R.string.mapbox_access_token));


    }
}
