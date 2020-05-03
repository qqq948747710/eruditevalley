package com.app.erudite.administrator.eruditevalley.Services.Interface;

import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;


import io.reactivex.Observable;
import retrofit2.http.GET;
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
}
