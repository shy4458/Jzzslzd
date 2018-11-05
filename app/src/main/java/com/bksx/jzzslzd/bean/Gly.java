package com.bksx.jzzslzd.bean;

import java.util.List;

/**服务站下管理员
 * Created by shy_4 on 2018/8/10.
 */

public class Gly {


    /**
     * returnCode : 200
     * returnMsg : 查询成功
     * returnData : [{"glyxm":"李雪芳","glybm":"0801034","ssfwzbh":"7dd84485359d00"},{"glyxm":"宁小虎","glybm":"0801068","ssfwzbh":"7dd84485359d00"},{"glyxm":"杨振荣","glybm":"0801123","ssfwzbh":"7dd84485359d00"},{"glyxm":"张雪立","glybm":"0801037","ssfwzbh":"7dd84485359d00"}]
     * pageCount : null
     * rowsCount : null
     * startNum : null
     */

    private int returnCode;
    private String returnMsg;
    private Object pageCount;
    private Object rowsCount;
    private Object startNum;
    private List<ReturnDataBean> returnData;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getPageCount() {
        return pageCount;
    }

    public void setPageCount(Object pageCount) {
        this.pageCount = pageCount;
    }

    public Object getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(Object rowsCount) {
        this.rowsCount = rowsCount;
    }

    public Object getStartNum() {
        return startNum;
    }

    public void setStartNum(Object startNum) {
        this.startNum = startNum;
    }

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * glyxm : 李雪芳
         * glybm : 0801034
         * ssfwzbh : 7dd84485359d00
         */

        private String glyxm;
        private String glybm;
        private String ssfwzbh;

        public String getGlyxm() {
            return glyxm;
        }

        public void setGlyxm(String glyxm) {
            this.glyxm = glyxm;
        }

        public String getGlybm() {
            return glybm;
        }

        public void setGlybm(String glybm) {
            this.glybm = glybm;
        }

        public String getSsfwzbh() {
            return ssfwzbh;
        }

        public void setSsfwzbh(String ssfwzbh) {
            this.ssfwzbh = ssfwzbh;
        }
    }
}
