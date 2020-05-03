package com.app.erudite.administrator.eruditevalley.Model;

import android.content.Context;

import android.widget.Toast;



import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.app.erudite.administrator.eruditevalley.Services.UserService;
import com.app.erudite.administrator.eruditevalley.View.Fragment.MyFragment;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class UserInfo implements Observer<UserEntity> {
    private String username;
    private String name;
    private String headpath;
    private static UserInfo suserinfo;
    private UserService service;
    private Context mContext;
    private MyFragment myFragment;


    public UserInfo(MyFragment myfragment){
        this.myFragment=myfragment;
        this.mContext=myFragment.getContext();
        this.name="";
        this.username="";
        this.headpath="";
        service=new UserService(mContext);
        getinfo();
    }

    public static UserInfo get(MyFragment myfragment){
        if(suserinfo!=null){
            return suserinfo;
        }
        return suserinfo=new UserInfo(myfragment);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadpath() {
        return headpath;
    }

    public void setHeadpath(String headpath) {
        this.headpath = headpath;
    }

    public void getinfo(){
        service.getuserinfo(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(UserEntity userEntity) {
        if(userEntity.getCode()!=0){
            //Toast.makeText(mContext,"信息请求失败",Toast.LENGTH_SHORT).show();
            return;
        }
        this.setName(userEntity.getName());
        this.setUsername(userEntity.getUsername());
        this.setHeadpath(userEntity.getHeadpath());
        myFragment.update(this.name);
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {

    }
    public static boolean unlogin(){
        if(suserinfo==null||suserinfo.getUsername().isEmpty()){
            return true;
        }
        return false;
    }
}
