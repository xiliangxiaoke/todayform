package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

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
        callenqueue(call,callBack);
    }


    /**
     * 登陆
     * @param phone
     * @param code
     * @param callBack
     */
    public static void login(String phone,String code,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance().login(phone,code);
        callenqueue(call,callBack);
    }

    /**
     * 注册
     * @param phone
     * @param code
     * @param callBack
     */
    public static void registerUser(String phone,String code,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance().registerUser(phone,code);
        callenqueue(call,callBack);
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
        callenqueue(call,callBack);
    }


    public static void findMyFields(String token,int currentpage,int pagesize,ApiCallBack<FieldInfo> callBack){
        Call<ResultObj<FieldInfo>> call = Doapi.instance().findMyFields(token,currentpage,pagesize);
        callenqueue(call,callBack);
    }

    public static void getFieldById(String token, String fieldId,ApiCallBack<FieldInfo> callBack) {
        Call<ResultObj<FieldInfo>> call = Doapi.instance().getFieldById(token, fieldId);
        callenqueue(call,callBack);

    }

    public static void findMyFieldsAllActivity(String token, int currentpage, int pagesize,ApiCallBack<FieldThingInfo> callBack) {
        Call<ResultObj<FieldThingInfo>> call = Doapi.instance().findMyFieldsAllActivity(token, currentpage,pagesize);
        callenqueue(call,callBack);
    }

    public static void findCropInfosByFieldId(String token, String fieldid, ApiCallBack<CropInfo> callBack){
        Call<ResultObj<CropInfo>> call = Doapi.instance().findCropInfosByFieldId(token, fieldid);
        callenqueue(call,callBack);
    }

    public static void saveOrUpdateCropInfo(String token, String fieldid, String cropname, String plantyear, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance().saveOrUpdateCropInfo(token,fieldid,cropname,plantyear);
        callenqueue(call,callBack);
    }

    public static void sowingSaveOrUpdate(
             String token,
             String fieldId,
             String sowingActivityId,
             String cropId,
             String seedName,
             String quantityPreAcer,
             String sowingType,
             String rowDistance,
             String columnDistance,
             String reservedSeedingQuantity,
             String sowingTractor,
             String sowingMechanical,
             String sowingStartTime,
             String sowingEndTime,
             String sowingDepth,
             String sowingPerAcre,
             String totalCost,
             String sowingNote,
             ApiCallBack<Object> callBack
    ){
        Call<ResultObj<Object>> call = Doapi.instance().sowingSaveOrUpdate(
                token,
                fieldId,
                sowingActivityId,
                cropId,
                seedName,
                quantityPreAcer,
                sowingType,
                rowDistance,
                columnDistance,
                reservedSeedingQuantity,
                sowingTractor,
                sowingMechanical,
                sowingStartTime,
                sowingEndTime,
                sowingDepth,
                sowingPerAcre,
                totalCost,
                sowingNote);
        callenqueue(call,callBack);
    }


    public static void irrigatingSaveOrUpdate(
             String token,
             String fieldId,
             String irrigatingId,
             String cropId,
             String irrigatingStartTime,
             String irrigatingEndTime,
             String waterPressure,
             String voltage,
             String equipmentSpeed,
             String totalCost,
             String irrigatingNote,
             ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance().irrigatingSaveOrUpdate(
                token,fieldId,irrigatingId,cropId,irrigatingStartTime,irrigatingEndTime,waterPressure,
                voltage,equipmentSpeed,totalCost,irrigatingNote
        );
        callenqueue(call,callBack);
    }


    public static void fertilizingSaveOrUpdate(
            String token,
            String fieldId,
            String cropId,
            String fertilizingActivityId,
            String fertilizingType,
            String fertilizingMethod,
            String fertilizingAcre,
            String fertilizingStartTime,
            String fertilizingEndTime,
            String totalQuantity,
            String totalCost,
            String fertilizingNote,
            ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance().fertilizingSaveOrUpdate(
                token,fieldId,cropId,fertilizingActivityId,fertilizingType,fertilizingMethod,fertilizingAcre,fertilizingStartTime,
                fertilizingEndTime,totalQuantity,totalCost,fertilizingNote
        );
        callenqueue(call,callBack);
    }

    //==========================================================


    /**
     * 通用回调代码
     * @param call
     * @param callBack
     * @param <E>
     */
    public  static <E>  void callenqueue (Call<ResultObj<E>> call,ApiCallBack<E> callBack){
        call.enqueue(new Callback<ResultObj<E>>() {
            @Override
            public void onResponse(Call<ResultObj<E>> call, Response<ResultObj<E>> response) {
                if (response.isSuccessful()) {
                    callBack.onResponse(response.body());
                } else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<E>> call, Throwable t) {
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }
}
