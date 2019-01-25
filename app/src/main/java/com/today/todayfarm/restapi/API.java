package com.today.todayfarm.restapi;

import android.util.Log;

import com.google.gson.Gson;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FertilizingInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.HarvestingInfo;
import com.today.todayfarm.dom.HealthImgInfo;
import com.today.todayfarm.dom.IrrigatingInfo;
import com.today.todayfarm.dom.NameValuePair;
import com.today.todayfarm.dom.NoteInfo;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SatellateImgInfo;
import com.today.todayfarm.dom.SoilInfo;
import com.today.todayfarm.dom.SowingInfo;
import com.today.todayfarm.dom.SprayingInfo;
import com.today.todayfarm.dom.StageInfo;
import com.today.todayfarm.dom.SubStageInfo;
import com.today.todayfarm.dom.TillingInfo;
import com.today.todayfarm.dom.TotalRainAndTemp;
import com.today.todayfarm.dom.User;

import org.json.JSONArray;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public static void updateField(String token, String fieldid,String fieldName, Double fieldArea, String fieldBoundary,String cropName,ApiCallBack<Object> callBack) {

        Call<ResultObj<Object>> call = Doapi.instance().updateField(token,fieldid, fieldName, fieldArea, fieldBoundary,cropName);
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
             String sowingNote,String imgUrl,
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
                sowingNote,imgUrl);
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
             String irrigatingNote,String imgUrl,
             ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance().irrigatingSaveOrUpdate(
                token,fieldId,irrigatingId,cropId,irrigatingStartTime,irrigatingEndTime,waterPressure,
                voltage,equipmentSpeed,totalCost,irrigatingNote,imgUrl
        );
        callenqueue(call,callBack);
    }


    public static void fertilizingSaveOrUpdate(
            String token,
            String fieldId,
            String cropId,
            String fertilizingActivityId,
            String fertilizingName,
            String fertilizingType,
            String fertilizingMethod,
            String fertilizingAcre,
            String fertilizingStartTime,
            String fertilizingEndTime,
            String totalQuantity,
            String totalCost,
            String fertilizingNote,String imgUrl,
            ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance().fertilizingSaveOrUpdate(
                token,fieldId,cropId,fertilizingActivityId,fertilizingName,fertilizingType,fertilizingMethod,fertilizingAcre,fertilizingStartTime,
                fertilizingEndTime,totalQuantity,totalCost,fertilizingNote,imgUrl
        );
        callenqueue(call,callBack);
    }


    public static void harvestingSaveOrUpdate(
             String token,
             String fieldId,
             String cropId,
             String HarvestingActivityId,
             String harvestingStartTime,
             String harvestingUnit,
             String harvestingMachine,
             String harvestingCount,
            String pullTrackCount,
             String totalYield,
             String yieldPerAcre,
             String harvestingPricePreAcre,
            String totalCost,
            String harvestingNote,
             String userId,String imgUrl,
            ApiCallBack<Object> callBack
    ){
        Call<ResultObj<Object>> call = Doapi.instance().harvestingSaveOrUpdate(
                token,fieldId,cropId,HarvestingActivityId,harvestingStartTime,harvestingUnit,harvestingMachine,harvestingCount,
                pullTrackCount,totalYield,yieldPerAcre,harvestingPricePreAcre,totalCost,harvestingNote,userId,imgUrl
        );
        callenqueue(call,callBack);
    }

    /**
     * 保存或修改农田整地记录
     * @param token
     * @param fieldId
     * @param cropId
     * @param tillingActivityId
     * @param userId
     * @param tillingType
     * @param tillingTractor
     * @param tillingMechanical
     * @param tillingStartTime
     * @param tillingEndTime
     * @param tillingDepth
     * @param pricePerAcre
     * @param totalCost
     * @param tillingNote
     */
    public static void tillingSaveOrUpdate(
             String token,
             String fieldId,
             String cropId,
             String tillingActivityId,
            String userId,
             String tillingType,
             String tillingTractor,
             String tillingMechanical,
             String tillingStartTime,
             String tillingEndTime,
             String tillingDepth,
             String pricePerAcre,
             String totalCost,
             String tillingNote,String imgUrl,ApiCallBack<Object> callBack
    ){
        Call<ResultObj<Object>> call = Doapi.instance().tillingSaveOrUpdate(
                token,fieldId,cropId,tillingActivityId,userId,tillingType,tillingTractor,tillingMechanical,tillingStartTime,
                tillingEndTime,tillingDepth,pricePerAcre,totalCost,tillingNote,imgUrl
        );
        callenqueue(call,callBack);
    }

    public static void sprayingSaveOrUpdate(
             String token,
             String fieldId,
             String cropId,
             String sprayingActivityId,
             String sprayingType,
             String disasterType,
             String sprayingMethod,
            String drugName,
             String sprayingEffect,
             String sprayingStartTime,
             String sprayingEndTime,
             String quantityPerAcre,
             String totalCost,
             String sprayingNote,String imgUrl,ApiCallBack<Object> callBack
    ){
        Call<ResultObj<Object>> call = Doapi.instance().sprayingSaveOrUpdate(
                token,fieldId,cropId,sprayingActivityId,sprayingType,disasterType,sprayingMethod,drugName,sprayingEffect
                ,sprayingStartTime,sprayingEndTime,quantityPerAcre,totalCost,sprayingNote,imgUrl
        );
        callenqueue(call,callBack);
    }



    public static void getSowingById(String token, String sowingActivityId, ApiCallBack<SowingInfo> callBack){
        Call<ResultObj<SowingInfo>> call = Doapi.instance().getSowingById(token,sowingActivityId);
        callenqueue(call,callBack);

    }

    public static void getIrrigatingById(String token, String IrrigatingActivityId, ApiCallBack<IrrigatingInfo> callBack){
        Call<ResultObj<IrrigatingInfo>> call = Doapi.instance().getIrrigatingById(token,IrrigatingActivityId);
        callenqueue(call,callBack);

    }

    public static void getFertilizingById(String token, String FertilizingActivityId, ApiCallBack<FertilizingInfo> callBack){
        Call<ResultObj<FertilizingInfo>> call = Doapi.instance().getFertilizingById(token,FertilizingActivityId);
        callenqueue(call,callBack);

    }

    public static void getHarvestingById(String token, String HarvestingActivityId, ApiCallBack<HarvestingInfo> callBack){
        Call<ResultObj<HarvestingInfo>> call = Doapi.instance().getHarvestingById(token,HarvestingActivityId);
        callenqueue(call,callBack);

    }
    public static void getTillingById(String token, String TillingActivityId, ApiCallBack<TillingInfo> callBack){
        Call<ResultObj<TillingInfo>> call = Doapi.instance().getTillingById(token,TillingActivityId);
        callenqueue(call,callBack);

    }
    public static void getSprayingById(String token, String SprayingActivityId, ApiCallBack<SprayingInfo> callBack){
        Call<ResultObj<SprayingInfo>> call = Doapi.instance().getSprayingById(token,SprayingActivityId);
        callenqueue(call,callBack);

    }

    public static void deleteSowingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteSowingById(token,id);
        callenqueue(call,callBack);
    }

    public static void deleteIrrigatingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteIrrigatingById(token,id);
        callenqueue(call,callBack);
    }

    public static void deleteFertilizingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteFertilizingById(token,id);
        callenqueue(call,callBack);
    }

    public static void deleteHarvestingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteHarvestingById(token,id);
        callenqueue(call,callBack);
    }

    public static void deleteTillingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteTillingById(token,id);
        callenqueue(call,callBack);
    }

    public static void deleteSprayingById(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteSprayingById(token,id);
        callenqueue(call,callBack);
    }

    public static void uploadPic(String token, String bussType, File file, ApiCallBack<Object> callBack) {

        //
        RequestBody description = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                bussType
        );


        //
//        RequestBody body = RequestBody.create(
//                MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "files",
                file.getName(),
                requestBody
        );

        Call<ResultObj<Object>> call = Doapi.instance()
                .uploadPic(token,description,body);
        callenqueue(call,callBack);
    }


    public static void getCropInfoById(String token,String cropid,ApiCallBack<CropInfo> callBack){
        Call<ResultObj<CropInfo>> call = Doapi.instance()
                .getCropInfoById(token,cropid);
        callenqueue(call,callBack);
    }



    public static void findFiledAllActivity(
            String token,String fieldId,int currentPage,int pageSize,ApiCallBack<FieldThingInfo> callBack
    ){
        Call<ResultObj<FieldThingInfo>> call = Doapi.instance()
                .findFiledAllActivity(token,fieldId,currentPage,pageSize);
        callenqueue(call,callBack);
    }


    public static void findFiledAllActivityOfCrop(
            String token,String fieldId,String cropInfoId,int year,int currentPage,int pageSize,ApiCallBack<FieldThingInfo> callBack
    ){
        Call<ResultObj<FieldThingInfo>> call = Doapi.instance()
                .findFiledAllActivityOfCrop(token,fieldId,cropInfoId,year,currentPage,pageSize);
        callenqueue(call,callBack);
    }

    public static void getLoginUserInfo(String token, ApiCallBack<User> callBack){
        Call<ResultObj<User>> call = Doapi.instance()
                .getLoginUserInfo(token);
        callenqueue(call,callBack);
    }

    public static void findMyScoutingNotes(String token,int currentpage,int pagesize ,ApiCallBack<NoteInfo> callBack) {
        Call<ResultObj<NoteInfo>> call = Doapi.instance()
               .findMyScoutingNotes(token,currentpage,pagesize);
        callenqueue(call,callBack);
    }


    public static void findMyHealthImgsWeekdays(String token,ApiCallBack<HealthImgInfo> callBack) {
        Call<ResultObj<HealthImgInfo>> call = Doapi.instance()
                .findMyHealthImgsWeekdays(token);
        callenqueue(call,callBack);
    }

    public static void findSatellateImgsWeekdays(String token,ApiCallBack<SatellateImgInfo> callBack) {
        Call<ResultObj<SatellateImgInfo>> call = Doapi.instance()
                .findSatellateImgsWeekdays(token);
        callenqueue(call,callBack);
    }


    // todo: 这里的泛型要改
    public static void findTemperatureDatas(String token,String fieldid,String cropid,int type,String name,ApiCallBack<NameValuePair> callBack){
        Call<ResultObj<NameValuePair>> call = Doapi.instance()
                .findTemperatureDatas(token,fieldid,cropid,type,name);
        callenqueue(call,callBack);
    }

    // todo: 这里的泛型要改
    public static void findRainDatas(String token,String fieldid,String cropid,int type,String name,ApiCallBack<NameValuePair> callBack){
        Call<ResultObj<NameValuePair>> call = Doapi.instance()
                .findRainDatas(token,fieldid,cropid,type,name);
        callenqueue(call,callBack);
    }



    public static void getThreeDayWeather(String token, String fieldid, int type , ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance()
                .getThreeDayWeather(token,fieldid,type);
        callenqueue(call,callBack);
    }

    public static void findClimateDatas(String token, String fieldid, ApiCallBack<TotalRainAndTemp> callBack) {
        Call<ResultObj<TotalRainAndTemp>> call = Doapi.instance()
                .findClimateDatas(token,fieldid);
        callenqueue(call,callBack);
    }

    public static void getSoilsInfo(String token, String fieldid, ApiCallBack<SoilInfo> callBack) {
        Call<ResultObj<SoilInfo>> call = Doapi.instance()
                .getSoilsInfo(token,fieldid);
        callenqueue(call,callBack);
    }

    public static void addScoutingNote(
            String token,

            String fieldid,
            String cropid,
            String noteinfo,
            String time,
            String position,
            String imgurl,ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .addScoutingNote(token,fieldid,cropid,noteinfo,time,position,imgurl);
        callenqueue(call,callBack);
    }

    public static void updateScoutingNote(
            String token,
            String scoutingNoteId,
            String fieldid,
            String cropid,
            String noteinfo,
            String time,
            String position,
            String imgurl,ApiCallBack<Object> callBack
    ) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .updateScoutingNote(token,scoutingNoteId,fieldid,cropid,noteinfo,time,position,imgurl);
        callenqueue(call,callBack);
    }


    public static void getCropHelpList(String token , int currentPage,int pageSize,ApiCallBack<CropInfo> callBack){
        Call<ResultObj<CropInfo>> call = Doapi.instance()
                .getCropHelpList(token,currentPage,pageSize);
        callenqueue(call,callBack);
    }

    public static void findCropStages(String token, String cropId, ApiCallBack<StageInfo> callBack) {
        Call<ResultObj<StageInfo>> call = Doapi.instance()
                .findCropStages(token,cropId);
        callenqueue(call,callBack);
    }

    public static void findCropSubStages(String token, String stageid, ApiCallBack<SubStageInfo> callBack) {
        Call<ResultObj<SubStageInfo>> call = Doapi.instance()
                .findCropSubStages(token,stageid);
        callenqueue(call,callBack);
    }

    public static void findMyUsers(String token, int currentpage, int pagesize, ApiCallBack<User> callBack) {
        Call<ResultObj<User>> call = Doapi.instance()
                .findMyUsers(token,currentpage,pagesize);
        callenqueue(call,callBack);
    }

    public static void addUser(String token,String name,String phone,String auth,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance()
                .addUser(token,name,phone,auth);
        callenqueue(call,callBack);
    }

    public static void updateUser(String token,String id,String name,String phone,String auth,ApiCallBack<Object> callBack){
        Call<ResultObj<Object>> call = Doapi.instance()
                .updateUser(token,id,name,phone,auth);
        callenqueue(call,callBack);
    }

    public static void delUser(String token, String id, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .delUser(token,id);
        callenqueue(call,callBack);
    }

    public static void findCropsNames(String token , String pagesize, ApiCallBack<CropInfo> callBack){
        Call<ResultObj<CropInfo>> call = Doapi.instance()
                .findCropsNames(token,pagesize);
        callenqueue(call,callBack);
    }


    public static void deleteFieldById(String token, String fieldid, ApiCallBack<Object> callBack) {
        Call<ResultObj<Object>> call = Doapi.instance()
                .deleteFieldById(token,fieldid);
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
                Log.v("=====>>>onResponse:","--------------------------------------------------------");

                Log.v("=====>>>onResponse:",new Gson().toJson(response));
                if (response.isSuccessful()) {
                    callBack.onResponse(response.body());
                } else {
                    callBack.onError(RESPONSE_NOT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResultObj<E>> call, Throwable t) {
                Log.v("=====>>>onResponse:","--------------------------------------------------------");
                Log.v("=====>>>onFailure:","fffff");
                callBack.onError(RESPONSE_ON_FAILURE);
            }
        });
    }
}
