package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.GrowthDataInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyApiService {


    @POST("register")
    Call<ResultObj<User>> register(
            @Query("username") String username,
            @Query("passwordmd5") String passwordmd5,
            @Query("key") String key
    );

    @POST("editUserInfo")
    Call<ResultObj<Object>> editUserInfo(
            @Header("usertoken")String usertoken,
            @Query("address")String address,
            @Query("company")String company,
            @Query("job")String job,
            @Query("phone")String phone,
            @Query("sex")String  sex

    );

    @POST("login")
    Call<ResultObj<User>> login(
            @Query("username") String username,
            @Query("passwordmd5")String passwordmd5
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
