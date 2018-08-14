package com.today.todayfarm.restapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Doapi {

//    private static Doapi _doapi;
//
//    private Doapi(){
//
//    }
//
//    public static Doapi getinstance(){
//        if (_doapi==null){
//            _doapi = new Doapi();
//        }
//        return _doapi;
//    }

    private static MyApiService _service = null;

    public static MyApiService instance(){
        if(_service == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://47.92.108.255:8081/todayserver/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            _service = retrofit.create(MyApiService.class);
        }
        return _service;
    }
}
