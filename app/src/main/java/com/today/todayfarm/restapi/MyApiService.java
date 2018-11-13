package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.GrowthDataInfo;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
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
            @Query("sowingNote") String sowingNote

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
