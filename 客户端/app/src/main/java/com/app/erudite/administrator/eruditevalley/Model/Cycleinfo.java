package com.app.erudite.administrator.eruditevalley.Model;

import android.graphics.Bitmap;
/**
* 该类是轮播图信息类
* */
public class Cycleinfo {
    private String title;
    private String imagename;
    private Bitmap bp;

    public Cycleinfo(String title, String imagename) {
        this.title = title;
        this.imagename = imagename;
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
}
