package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.GrowthDataInfo;
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
import com.today.todayfarm.dom.FertilizingInfo;
import com.today.todayfarm.dom.SprayingInfo;
import com.today.todayfarm.dom.TillingInfo;
import com.today.todayfarm.dom.TotalRainAndTemp;
import com.today.todayfarm.dom.User;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyApiService {


    /**
     * 请求发送手机验证码
     * @param phone
     * @return
     */
    @GET("app/static/sendRandomCode")
    Call<ResultObj<PhoneCode>> sendRandomCode(
            @Query("phone") String phone
    );

    /**
     * 注册用户
     * @param phone
     * @param code
     * @return
     */
    @GET("app/static/registerUser")
    Call<ResultObj<Object>>  registerUser(
            @Query("phone") String phone,
            @Query("code") String code
    );

    /**
     * 登陆
     * @param phone
     * @param code
     * @return
     */
    @GET("app/static/login")
    Call<ResultObj<Object>> login(
            @Query("phone") String phone,
            @Query("code") String code
    );

    /**
     * 创建或更新农田
     * @param token
     * @param fieldName
     * @param fieldArea
     * @param fieldBoundary
     * @param cropName
     * @return
     */
    @GET("app/field/saveOrUpdate")
    Call<ResultObj<Object>> saveOrUpdate(
        @Query("token") String token,
        @Query("fieldName") String fieldName,
        @Query("fieldArea") Double fieldArea,
        @Query("fieldBoundary") String fieldBoundary,
        @Query("cropName") String cropName
    );


    /**
     * 获取农田列表
     * @param token
     * @param currentpage
     * @param pageSize
     * @return
     */
    @GET("app/field/findMyFields")
    Call<ResultObj<FieldInfo>> findMyFields(
            @Query("token") String token,
            @Query("currentPage") int currentpage,
            @Query("pageSize") int pageSize
    );

    /**
     * 获取农田详细信息
     * @return
     */
    @GET("app/field/getById")
    Call<ResultObj<FieldInfo>> getFieldById(
            @Query("token") String token,
            @Query("fieldId")String fieldId
    );


    /**
     * 获取所有 农事列表
     * @param token
     * @param currentpage
     * @param pageSize
     * @return
     */
    @GET("app/AllActivity/findMyFiledsAllActivity")
    Call<ResultObj<FieldThingInfo>> findMyFieldsAllActivity(
            @Query("token") String token,
            @Query("currentPage") int currentpage,
            @Query("pageSize") int pageSize
    );


    /**
     * 获取农田作物列表
     * @param token
     * @param fieldId
     * @return
     */
    @GET("app/cropInfo/findCropInfosByFieldId")
    Call<ResultObj<CropInfo>> findCropInfosByFieldId(
            @Query("token") String token,
            @Query("fieldId") String fieldId
    );


    /**
     * 添加农作物
     * @param token
     * @param fieldId
     * @param cropName
     * @param plantYear
     * @return
     */
    @GET("app/cropInfo/saveOrUpdate")
    Call<ResultObj<Object>> saveOrUpdateCropInfo(
      @Query("token") String token,
      @Query("fieldId") String fieldId,
      @Query("cropName") String cropName,
      @Query("plantYear") String plantYear
    );


    /**
     *
     * @param token
     * @param fieldId
     * @param sowingActivityId 播种记录ID
     * @param cropId
     * @param seedName 品种名称
     * @param quantityPreAcer 亩播数量
     * @param sowingType 播种方法
     * @param rowDistance 播种行距
     * @param columnDistance 株距
     * @param reservedSeedingQuantity 保苗株数
     * @param sowingTractor 车头
     * @param sowingMechanical 机械
     * @param sowingStartTime 开始时间 yyyy-MM-dd
     * @param sowingEndTime 结束时间 yyyy-MM-dd
     * @param sowingDepth 深度
     * @param sowingPerAcre 每亩价格
     * @param totalCost 总费用
     * @param sowingNote 备注
     * @return
     */
    @GET("app/SowingActivity/saveOrUpdate")
    Call<ResultObj<Object>> sowingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("sowingActivityId") String sowingActivityId,
            @Query("cropId") String cropId,
            @Query("seedName") String seedName,
            @Query("quantityPreAcer") String quantityPreAcer,
            @Query("sowingType") String sowingType,
            @Query("rowDistance") String rowDistance,
            @Query("columnDistance") String columnDistance,
            @Query("reservedSeedingQuantity") String reservedSeedingQuantity,
            @Query("sowingTractor") String sowingTractor,
            @Query("sowingMechanical") String sowingMechanical,
            @Query("sowingStartTime") String sowingStartTime,
            @Query("sowingEndTime") String sowingEndTime,
            @Query("sowingDepth") String sowingDepth,
            @Query("sowingPerAcre")String sowingPerAcre,
            @Query("totalCost") String totalCost,
            @Query("sowingNote") String sowingNote,
            @Query("imgUrl") String imgUrl

    );


    /**
     * 保存或修改农田灌溉记录
     * @param token
     * @param fieldId
     * @param cropId
     * @param irrigatingStartTime
     * @param irrigatingEndTime
     * @param waterPressure // 水压
     * @param voltage // 电压
     * @param equipmentSpeed 设备运行速度
     * @param totalCost
     * @param irrigatingNote
     * @return
     */
    @GET("app/IrrigatingActivity/saveOrUpdate")
    Call<ResultObj<Object>> irrigatingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("irrigatingActivityId") String irrigatingId,
            @Query("cropId") String cropId,
            @Query("irrigatingStartTime") String irrigatingStartTime,
            @Query("irrigatingEndTime") String irrigatingEndTime,
            @Query("waterPressure") String waterPressure,
            @Query("voltage") String voltage,
            @Query("equipmentSpeed") String equipmentSpeed,
            @Query("totalCost") String totalCost,
            @Query("irrigatingNote") String irrigatingNote,
            @Query("imgUrl") String imgUrl

    );


    /**
     * 保存或施肥记录
     * @param token
     * @param fieldId
     * @param cropId
     * @param fertilizingActivityId
     * @param fertilizingType
     * @param fertilizingMethod
     * @param fertilizingAcre
     * @param fertilizingStartTime
     * @param fertilizingEndTime
     * @param totalQuantity
     * @param totalCost
     * @param fertilizingNote
     * @return
     */
    @GET("app/FertilizingActivity/saveOrUpdate")
    Call<ResultObj<Object>> fertilizingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("fertilizingActivityId") String fertilizingActivityId,
            @Query("fertilizingName") String fertilizingName,
            @Query("fertilizingType") String fertilizingType,
            @Query("fertilizingMethod") String fertilizingMethod,
            @Query("fertilizingAcre") String fertilizingAcre,
            @Query("fertilizingStartTime") String fertilizingStartTime,
            @Query("fertilizingEndTime") String fertilizingEndTime,
            @Query("totalQuantity") String totalQuantity,
            @Query("totalCost") String totalCost,
            @Query("fertilizingNote") String fertilizingNote,
            @Query("imgUrl") String imgUrl

    );


    /**
     * 收割记录保存
     * @param token
     * @param fieldId
     * @param cropId
     * @param HarvestingActivityId
     * @param harvestingStartTime
     * @param harvestingUnit
     * @param harvestingMachine
     * @param harvestingCount
     * @param pullTrackCount
     * @param totalYield
     * @param yieldPerAcre
     * @param harvestingPricePreAcre
     * @param totalCost
     * @param harvestingNote
     * @param userId
     * @return
     */
    @GET("app/HarvestingActivity/saveOrUpdate")
    Call<ResultObj<Object>> harvestingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("HarvestingActivityId") String HarvestingActivityId,
            @Query("harvestingStartTime") String harvestingStartTime,
            @Query("harvestingUnit") String harvestingUnit,
            @Query("harvestingMachine") String harvestingMachine,
            @Query("harvestingCount") String harvestingCount,
            @Query("pullTrackCount") String pullTrackCount,
            @Query("totalYield") String totalYield,
            @Query("yieldPerAcre") String yieldPerAcre,
            @Query("harvestingPricePreAcre") String harvestingPricePreAcre,
            @Query("totalCost") String totalCost,
            @Query("harvestingNote") String harvestingNote,
            @Query("userId") String userId,
            @Query("imgUrl") String imgUrl
    );


    /**
     * 整地记录
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
     * @return
     */
    @GET("app/TillingActivity/saveOrUpdate")
    Call<ResultObj<Object>> tillingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("tillingActivityId") String tillingActivityId,
            @Query("userId") String userId,
            @Query("tillingType") String tillingType,
            @Query("tillingTractor") String tillingTractor,
            @Query("tillingMechanical") String tillingMechanical,
            @Query("tillingStartTime") String tillingStartTime,
            @Query("tillingEndTime") String tillingEndTime,
            @Query("tillingDepth") String tillingDepth,
            @Query("pricePerAcre") String pricePerAcre,
            @Query("totalCost") String totalCost,
            @Query("tillingNote") String tillingNote,
            @Query("imgUrl") String imgUrl
    );


    @GET("app/SprayingActivity/saveOrUpdate")
    Call<ResultObj<Object>> sprayingSaveOrUpdate(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("sprayingActivityId") String sprayingActivityId,
            @Query("sprayingType") String sprayingType,
            @Query("disasterType") String disasterType,
            @Query("sprayingMethod") String sprayingMethod,
            @Query("drugName") String drugName,
            @Query("sprayingEffect") String sprayingEffect,
            @Query("sprayingStartTime") String sprayingStartTime,
            @Query("sprayingEndTime") String sprayingEndTime,
            @Query("quantityPerAcre") String quantityPerAcre,
            @Query("totalCost") String totalCost,
            @Query("sprayingNote") String sprayingNote,
            @Query("imgUrl") String imgUrl
    );


    /**
     * 根据记录id获取播种记录详情
     * @param token
     * @param sowingActivityId
     * @return
     */
    @GET("app/SowingActivity/getById")
    Call<ResultObj<SowingInfo>> getSowingById(
            @Query("token") String token,
            @Query("sowingActivityId") String sowingActivityId
    );


    /**
     * 根据记录id获取灌溉记录详情
     * @param token
     * @param irrigatingActivityId
     * @return
     */
    @GET("app/IrrigatingActivity/getById")
    Call<ResultObj<IrrigatingInfo>> getIrrigatingById(
            @Query("token") String token,
            @Query("irrigatingActivityId") String irrigatingActivityId
    );


    /**
     * 根据记录id获取施肥记录详情
     * @param token
     * @param fertilizingActivityId
     * @return
     */
    @GET("app/FertilizingActivity/getById")
    Call<ResultObj<FertilizingInfo>> getFertilizingById(
            @Query("token") String token,
            @Query("fertilizingActivityId") String fertilizingActivityId
    );


    /**
     * 根据记录id获取收割记录详情
     * @param token
     * @param HarvestingActivityId
     * @return
     */
    @GET("app/HarvestingActivity/getById")
    Call<ResultObj<HarvestingInfo>> getHarvestingById(
            @Query("token") String token,
            @Query("harvestingActivityId") String HarvestingActivityId
    );


    /**
     * 根据记录id获取整地记录详情
     * @param token
     * @param TillingActivityId
     * @return
     */
    @GET("app/TillingActivity/getById")
    Call<ResultObj<TillingInfo>> getTillingById(
            @Query("token") String token,
            @Query("tillingActivityId") String TillingActivityId
    );


    /**
     * 根据记录id获取植保记录详情
     * @param token
     * @param sprayingActivityId
     * @return
     */
    @GET("app/SprayingActivity/getById")
    Call<ResultObj<SprayingInfo>> getSprayingById(
            @Query("token") String token,
            @Query("sprayingActivityId") String sprayingActivityId
    );



    // 删除弄事的六个接口

    @GET("app/SowingActivity/deleteById")
    Call<ResultObj<Object>> deleteSowingById(
            @Query("token") String token,
            @Query("sowingActivityId") String sowingActivityId
    );
    @GET("app/IrrigatingActivity/deleteById")
    Call<ResultObj<Object>> deleteIrrigatingById(
            @Query("token") String token,
            @Query("irrigatingActivityId") String irrigatingActivityId
    );
    @GET("app/FertilizingActivity/deleteById")
    Call<ResultObj<Object>> deleteFertilizingById(
            @Query("token") String token,
            @Query("fertilizingActivityId") String fertilizingActivityId
    );
    @GET("app/HarvestingActivity/deleteById")
    Call<ResultObj<Object>> deleteHarvestingById(
            @Query("token") String token,
            @Query("HarvestingActivityId") String HarvestingActivityId
    );
    @GET("app/TillingActivity/deleteById")
    Call<ResultObj<Object>> deleteTillingById(
            @Query("token") String token,
            @Query("tillingActivityId") String tillingActivityId
    );
    @GET("app/SprayingActivity/deleteById")
    Call<ResultObj<Object>> deleteSprayingById(
            @Query("token") String token,
            @Query("sprayingActivityId") String sprayingActivityId
    );


    /**
     * 上传接口
     * @param token
     * @param description
     * @param file
     * @return
     */
    @Multipart
    @POST("app/file/uploadMulti")
    Call<ResultObj<Object>> uploadPic(
            @Query("token") String token,
            @Part("bussType") RequestBody description,

            @Part MultipartBody.Part file
            );


    @GET("app/cropInfo/getById")
    Call<ResultObj<CropInfo>> getCropInfoById(@Query("token") String token,@Query("cropId") String cropId);


    /**
     * 获取指定农田的全部农事记录
     * @param token
     * @param fieldId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("app/AllActivity/findFiledAllActivity")
    Call<ResultObj<FieldThingInfo>> findFiledAllActivity(
            @Query("token") String token,
            @Query("fieldId") String fieldId,

            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );


    /**
     * 获取指定农田的指定作物的农事记录
     * @param token
     * @param fieldId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("app/AllActivity/findFiledAllActivity")
    Call<ResultObj<FieldThingInfo>> findFiledAllActivityOfCrop(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropInfoId") String cropInfoId,
            @Query("year") int year,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );


    /**
     * 获取登陆用户的信息
     * @param token
     * @return
     */
    @GET("app/user/getLoginUserInfo")
    Call<ResultObj<User>> getLoginUserInfo(
            @Query("token") String token
    );

    /**
     * 获取注记列表
     * @param token
     * @param currentpage
     * @param pageSize
     * @return
     */
    @GET("app/ScoutingNote/findMyScoutingNotes")
    Call<ResultObj<NoteInfo>> findMyScoutingNotes(
            @Query("token") String token,
            @Query("currentPage") int currentpage,
            @Query("pageSize") int pageSize
    );


    /**
     * 添加注记
     * @param token
     * @param scoutingNoteId
     * @param fieldId
     * @param cropId
     * @param scoutingNoteInfo
     * @param scoutingTime yyyy-MM-dd
     * @param scoutingPosition 注记位置
     * @param imgUrl 用分号隔开
     * @return
     */
    @GET("app/ScoutingNote/saveOrUpdate")
    Call<ResultObj<Object>> addScoutingNote(
            @Query("token") String token,

            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("scoutingNoteInfo") String scoutingNoteInfo,
            @Query("scoutingTime") String scoutingTime,
            @Query("scoutingPosition") String scoutingPosition,
            @Query("imgUrl") String imgUrl
    );

    /**
     * 修改注记
     * @param token
     * @param fieldId
     * @param cropId
     * @param scoutingNoteInfo
     * @param scoutingTime
     * @param scoutingPosition
     * @param imgUrl
     * @return
     */
    @GET("app/ScoutingNote/saveOrUpdate")
    Call<ResultObj<Object>> updateScoutingNote(
            @Query("token") String token,
            @Query("scoutingNoteId") String scoutingNoteId,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("scoutingNoteInfo") String scoutingNoteInfo,
            @Query("scoutingTime") String scoutingTime,
            @Query("scoutingPosition") String scoutingPosition,
            @Query("imgUrl") String imgUrl
    );

    /**
     * 获取指定时间段内的健康监测影像数据 TODO 后面补充时间段相关字段
     * @param token
     * @return
     */
    @GET("app/HealthImg/findMyHealthImgsWeekdays")
    Call<ResultObj<HealthImgInfo>> findMyHealthImgsWeekdays(
            @Query("token") String token
    );

    @GET("app/SatellateImg/findMyFieldImages")
    Call<ResultObj<SatellateImgInfo>> findSatellateImgsWeekdays(
            @Query("token") String token
    );


    @GET("app/ClimateData/findClimateDatas")
    Call<ResultObj<TotalRainAndTemp>> findClimateDatas(
            @Query("token") String token,
            @Query("fieldId") String fieldId
    );

    /**
     * 获取农田的积温或累积积温数据
     * * @param token
     *      * @param fieldId
     *      * @param type （1：种植日期，0：全年数据）
     *      * @param name（（temperature：积温，totalTemperature：累积积温）
     * @return
     */
    @GET("app/ClimateData/findTemperatureDatas")
    Call<ResultObj<NameValuePair>> findTemperatureDatas(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("type") int type,
            @Query("name") String name
    );

    /**
     * 获取农田的降水或累积降水数据
     * @param token
     * @param fieldId
     * @param type （1：种植日期，0：全年数据）
     * @param name （rain：降水，totalRain：累积降水）
     * @return
     */
    @GET("app/ClimateData/findRainDatas")
    Call<ResultObj<NameValuePair>> findRainDatas(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("cropId") String cropId,
            @Query("type") int type,
            @Query("name") String name
    );


    /**
     * 获取农田的未来三天预测数据
     * @param token
     * @param fieldId
     * @param type(0:温度，1：湿度,2:降水)
     * @return
     */
    @GET("app/ThreeDayWeather/getThreeDayWeather")
    Call<ResultObj<Object>> getThreeDayWeather(
            @Query("token") String token,
            @Query("fieldId") String fieldId,
            @Query("type") int type
    );




    @GET("app/soilgrids/getSoilsInfo")
    Call<ResultObj<SoilInfo>> getSoilsInfo(
            @Query("token") String token,
            @Query("fieldId") String fieldId
    );



    @GET("app/Agronomy/findCropsByPage")
    Call<ResultObj<CropInfo>> getCropHelpList(
            @Query("token") String token,
            @Query("currentPage") int currentpage,
            @Query("pageSize") int pageSize
    );



    //-------------------------------------------------------------------old

    @POST("editUserInfo")
    Call<ResultObj<Object>> editUserInfo(
            @Header("usertoken")String usertoken,
            @Query("address")String address,
            @Query("company")String company,
            @Query("job")String job,
            @Query("phone")String phone,
            @Query("sex")String  sex

    );



    @POST("changePw")
    Call<ResultObj<Object>> changePw(
            @Header("usertoken")String usertoken,
            @Query("oldpw")String oldpw,
            @Query("newpw")String newpw
    );

    @POST("addfield")
    Call<ResultObj<Object>> addfield(
            @Header("usertoken")String usertoken,
            @Query("farmid")String farmid,
            @Query("name")String name,
            @Query("area")String area,
            @Query("croptype")String croptype,
            @Query("boundry")String boundry
    );

    @POST("addfarm")
    Call<ResultObj<Object>> addfarm(
            @Header("usertoken")String usertoken,
            @Query("name")String name,
            @Query("address")String address
    );

    @POST("getfarms")
    Call<ResultObj<FarmInfo>> getfarms(
            @Header("usertoken")String usertoken
    );

    @POST("delfarm")
    Call<ResultObj<Object>> delfarm(
            @Header("usertoken")String usertoken,
            @Query("farmid")String farmid
    );

    @POST("getFields")
    Call<ResultObj<FieldInfo>> getFields(
            @Header("usertoken")String usertoken

    );

    @Multipart
    @PUT("uploadFieldSateliteImg")
    Call<ResultObj<Object>> uploadFieldSateliteImg(
            @Header("usertoken")String usertoken,
            @Part("uploadFile\"; filename=\"test.jpg\"") RequestBody part,
            @Query("description") String description,
            @Query("fieldid")String fieldid,
            @Query("type")String type,
            @Query("createtime")String createtime,
            @Query("extentleft")String extentleft,
            @Query("extentbottom")String extentbottom,
            @Query("extentright")String extentright,
            @Query("extenttop")String extenttop

            );

    @POST("addGrowthData")
    Call<ResultObj<Object>> addGrowthData(
            @Header("usertoken")String usertoken,
            @Query("fieldid")String fieldid,
            @Query("datetime")String datetime,
            @Query("datavalue")String datavalue
    );

    @POST("addHumidData")
    Call<ResultObj<Object>> addHumidData(
            @Header("usertoken")String usertoken,
            @Query("fieldid")String fieldid,
            @Query("datetime")String datetime,
            @Query("datavalue")String datavalue
    );

    @POST("getGrowthData")
    Call<ResultObj<GrowthDataInfo>> getGrowthData(
            @Header("usertoken")String usertoken,
            @Query("fieldid")String fieldid
    );

    @POST("getHumidData")
    Call<ResultObj<GrowthDataInfo>> getHumidData(
            @Header("usertoken")String usertoken,
            @Query("fieldid")String fieldid
            );
}
