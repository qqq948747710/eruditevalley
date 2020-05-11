package com.app.erudite.administrator.eruditevalley.Model;

import android.content.Context;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import com.app.erudite.administrator.eruditevalley.Services.Entity.HeadEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.app.erudite.administrator.eruditevalley.Services.UserService;
import com.app.erudite.administrator.eruditevalley.View.Fragment.MyFragment;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class UserInfo implements Observer<UserEntity> {
    private static String username;
    private static String name;
    private static String headpath;
    private static String email;
    private static String sex;
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
        this.sex="0";
        this.email="";
        service=new UserService(mContext);
        getinfoandupdateView();
    }

    public static UserInfo get(MyFragment myfragment){
        if(suserinfo!=null){
            return suserinfo;
        }
        return suserinfo=new UserInfo(myfragment);
    }
    public void getinfoandupdateView(){
        service.getuserinfo(this);
    }
    public void alterinfoandupdateView(UserEntity userEntity){
        service.alterprofile(userEntity,this);
    }
    public void updatehead(Uri fileuri,Observer<HeadEntity> observer){
        File file=uri2File(fileuri);
        service.uploadhaad(file,observer);
    }
    public void updatehead(File file,Observer<HeadEntity> observer){
        service.uploadhaad(file,observer);
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
        this.setSex(userEntity.getSex());
        this.setEmail(userEntity.getEmail());
        this.updataView();
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

    private void updataView(){
        myFragment.updateView(this.name);
    }
    /**
     * user转换为file文件
     *返回值为file类型
     * @param uri
     * @return
     */
    private File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = myFragment.getActivity().getContentResolver().query(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getHeadpath() {
        return headpath;
    }

    public void setHeadpath(String headpath) {
        this.headpath = headpath;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserInfo.email = email;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        UserInfo.sex = sex;
    }
}
