package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class API {
    public static int RESPONSE_NOT_SUCCESS = 88001;
    public static int RESPONSE_ON_FAILURE = 88002;

    /**
     * 发送手机验证码
     * @param phone
     * @param callBack
     */
    public static void sendRandomCode(String phone, ApiCallBack<PhoneCode> callBack){

        Call<ResultObj<PhoneCode>> call =  Doapi.instance().sendRandomCode(phone);
        call.enqueue(new Callback<ResultObj<PhoneCode>>() {
            @Override
            public void onResponse(Call<ResultObj<PhoneCode>> call, Response<ResultObj<PhoneCode>> response) {
                if(response.isSuccessful()){
                    callBack.onResponse(response.body());
                }else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<PhoneCode>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }


    /**
     * 登陆
     * @param phone
     * @param code
     * @param callBack
     */
    public static void login(String phone,String code,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance().login(phone,code);
        call.enqueue(new Callback<ResultObj<Object>>() {
            @Override
            public void onResponse(Call<ResultObj<Object>> call, Response<ResultObj<Object>> response) {
                if(response.isSuccessful()){
                    callBack.onResponse(response.body());
                }else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<Object>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }

    /**
     * 注册
     * @param phone
     * @param code
     * @param callBack
     */
    public static void registerUser(String phone,String code,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance().registerUser(phone,code);
        call.enqueue(new Callback<ResultObj<Object>>() {
            @Override
            public void onResponse(Call<ResultObj<Object>> call, Response<ResultObj<Object>> response) {
                if(response.isSuccessful()){
                    callBack.onResponse(response.body());
                }else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<Object>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }

    /**
     * 保存农田
     * @param token
     * @param fieldName
     * @param fieldArea
     * @param fieldBoundary
     * @param callBack
     */
    public static void saveOrUpdate(String token, String fieldName, Double fieldArea, String fieldBoundary,String cropName,ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance().saveOrUpdate(token, fieldName, fieldArea, fieldBoundary,cropName);
        call.enqueue(new Callback<ResultObj<Object>>() {
            @Override
            public void onResponse(Call<ResultObj<Object>> call, Response<ResultObj<Object>> response) {
                if (response.isSuccessful()) {
                    callBack.onResponse(response.body());
                } else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<Object>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }


    public static void findMyFields(String token,int currentpage,int pagesize,ApiCallBack<FieldInfo> callBack){
        Call<ResultObj<FieldInfo>> call = Doapi.instance().findMyFields(token,currentpage,pagesize);
        call.enqueue(new Callback<ResultObj<FieldInfo>>() {
            @Override
            public void onResponse(Call<ResultObj<FieldInfo>> call, Response<ResultObj<FieldInfo>> response) {
                if (response.isSuccessful()) {
                    callBack.onResponse(response.body());
                } else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<FieldInfo>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }
}
