package com.app.erudite.administrator.eruditevalley.Services;


import android.content.Context;

import com.app.erudite.administrator.eruditevalley.Services.Entity.HeadEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.app.erudite.administrator.eruditevalley.Services.Interface.UserServiceInterface;
import com.google.gson.Gson;


import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserService extends Service{
    private UserServiceInterface server;
    public UserService(Context context){
        super(context);
        server=retrofit.create(UserServiceInterface.class);
    }
    public void login(String username, String password, Observer observer){
        server.login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void register(String name,String username,String password,String repassword,Observer observer){
        server.register(name,username,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void getuserinfo(Observer observer){
        server.getuserinof()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void uploadhaad(File file, Observer<HeadEntity> observer){
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part filefrom=MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        server.uploadhead(filefrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void alterprofile(UserEntity entity, Observer<UserEntity> observer){
        Gson gson=new Gson();
        String userstr=gson.toJson(entity);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),userstr);
        server.alterprofile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    @Override
    protected void onFinishqueue(int queue) {

    }
}
