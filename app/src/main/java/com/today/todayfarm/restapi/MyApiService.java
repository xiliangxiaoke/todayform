package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.FieldInfo;
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

    @GET("app/field/saveOrUpdate")
    Call<ResultObj<Object>> saveOrUpdate(
        @Query("token") String token,
        @Query("fieldName") String fieldName,
        @Query("fieldArea") Double fieldArea,
        @Query("fieldBoundary") String fieldBoundary,
        @Query("cropName") String cropName
    );

    @GET("app/field/findMyFields")
    Call<ResultObj<FieldInfo>> findMyFields(
            @Query("token") String token,
            @Query("currentPage") int currentpage,
            @Query("pageSize") int pageSize
    );


    //--------------------old

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
