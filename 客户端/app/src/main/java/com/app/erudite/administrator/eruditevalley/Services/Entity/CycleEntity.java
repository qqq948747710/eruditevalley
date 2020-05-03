package com.app.erudite.administrator.eruditevalley.Services.Entity;

import java.util.List;

public class CycleEntity {

    /**
     * 轮播请求json实体类
     * code : 0
     * result : [{"title":"国家信息技术","imagename":"index1.jpg"},{"title":"传播博客IT","imagename":"index2.jpg"},{"title":"IT人才培训机构","imagename":"index3.jpg"}]
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
         * title : 国家信息技术
         * imagename : index1.jpg
         */

        private String title;
        private String imagename;

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
