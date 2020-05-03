package com.app.erudite.administrator.eruditevalley.Services.Entity;

import android.graphics.Bitmap;

import java.util.List;

public class IndexListEntity {

    /**
     * 主页列表请求json实体类
     * code : 0
     * result : [{"title":"第一章 安卓基础入门","imagename":"chapter_1_icon.png"},{"title":"第二章 安卓UI入门","imagename":"chapter_2_icon.png"},{"title":"第三章 安卓基础入门","imagename":"chapter_3_icon.png"},{"title":"第四章 安卓进阶教程","imagename":"chapter_4_icon.png"},{"title":"第五章 安卓进阶教程","imagename":"chapter_5_icon.png"},{"title":"第六章 安卓高级教程","imagename":"chapter_6_icon.png"}]
     */

    private int code;
    private List<ResultBean> result;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title : 第一章 安卓基础入门
         * imagename : chapter_1_icon.png
         */

        private String title;
        private String imagename;
        private String videoname;

        public String getVideoname() {
            return videoname;
        }

        public void setVideoname(String videoname) {
            this.videoname = videoname;
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
    }
}
