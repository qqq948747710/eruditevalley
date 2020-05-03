package com.app.erudite.administrator.eruditevalley.Services;


import android.content.Context;

import com.app.erudite.administrator.eruditevalley.Services.Interface.UserServiceInterface;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @Override
    protected void onFinishqueue(int queue) {

    }
}
