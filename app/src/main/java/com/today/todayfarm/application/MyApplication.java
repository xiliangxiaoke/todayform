package com.today.todayfarm.application;

import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {

    public static String token = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
