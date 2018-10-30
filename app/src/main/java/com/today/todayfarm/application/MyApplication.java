package com.today.todayfarm.application;

import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
//import com.mapbox.mapboxsdk.Mapbox;
//import com.tencent.smtt.sdk.QbSdk;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;

public class MyApplication extends MultiDexApplication {

    public static String token = null;
    public static Typeface iconTypeFace = null;


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        Hawk.init(this).build();

        iconTypeFace = Typeface.createFromAsset(getAssets(),"font/fontawesome-webfont.ttf");

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(),  cb);


        // mapbox access token
//        Mapbox.getInstance(getApplicationContext(),getString(R.string.mapbox_access_token));


    }
}
