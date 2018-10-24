package com.today.todayfarm.restapi;

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
}
