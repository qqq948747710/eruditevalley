package com.app.erudite.administrator.eruditevalley.Services.Interface;

import com.app.erudite.administrator.eruditevalley.Services.Entity.HeadEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserServiceInterface {
    @GET("/login")
    Observable<UserEntity> login(@Query("username") String username
            , @Query("password")String password);
    @GET("/register")
    Observable<UserEntity>register(@Query("name") String name,@Query("username") String username
            , @Query("password")String password,@Query("repassword") String repwd);
    @GET("/getuserinfo")
    Observable<UserEntity>getuserinof();
    @Multipart
    @POST("/uploadhead")
    Observable<HeadEntity>uploadhead(@Part MultipartBody.Part file);
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/alterprofile")
    Observable<UserEntity>alterprofile(@Body RequestBody body);
}
