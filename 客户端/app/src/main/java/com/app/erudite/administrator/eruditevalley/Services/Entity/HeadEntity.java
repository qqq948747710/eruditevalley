package com.app.erudite.administrator.eruditevalley.Services.Entity;

/**
 * @ClassName: HeadEntity
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/5/9 22:32
 */
public class HeadEntity {

    /**
     * error : 1
     * result : {"msg":"创建文件失败","path":""}
     */

    private int error;
    private ResultBean result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * msg : 创建文件失败
         * path :
         */

        private String msg;
        private String path;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
