package com.app.erudite.administrator.eruditevalley.Services.Entity;

import java.util.List;

/**
 * @ClassName: ExescisesEntity
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/4/30 11:00
 */
public class ExercisesEntity {

    /**
     * code : 0
     * result : [{"id":0,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0},{"id":1,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0},{"id":2,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0},{"id":3,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0},{"id":4,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0},{"id":5,"title":"第一章 安卓基础入门","sum":"共五章","content":"基础数据测试题选A吧","option":["我是A","我是B","我是C","我是D"],"right":0}]
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
         * id : 0
         * title : 第一章 安卓基础入门
         * sum : 共五章
         * content : 基础数据测试题选A吧
         * option : ["我是A","我是B","我是C","我是D"]
         * right : 0
         */

        private int id;
        private String title;
        private String sum;
        private String content;
        private int right;
        private List<String> option;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public List<String> getOption() {
            return option;
        }

        public void setOption(List<String> option) {
            this.option = option;
        }
    }
}
