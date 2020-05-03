package com.app.erudite.administrator.eruditevalley.Model;

import android.graphics.Bitmap;

import com.app.erudite.administrator.eruditevalley.Config.AppConfig;

/**
 * 该类是主页课程类
 * */
public class IndexListinfo {
    private  String title;
    private String imagename;
    private Bitmap bp;
    private String videoname;

    public IndexListinfo(String title, String imagename,String videoname) {
        this.title = title;
        this.imagename = imagename;
        this.videoname=videoname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public Bitmap getBp() {
        return bp;
    }

    public void setBp(Bitmap bp) {
        this.bp = bp;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

}
