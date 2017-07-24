package com.bksx.jzzslzd.bo;


/**
 * AjaxJson结果工具类
 * <p>
 * 用于在Action层对Ajax请求返回的数据进行统一处理
 *
 * @author limiao
 * @version 1.0
 */
@SuppressWarnings("all")
public class JsonResult {


    private int returnCode;// 返回码

    private String returnMsg;// 返回消息

    private Object returnData = "";// 返回数据

    private String pageCount;// 总页数

    private String rowsCount;// 总行数

    private String startNum;// 开始行号

    public int getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public Object getReturnData() {
        return returnData;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(String rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }


    /**
     * 构造方法
     *
     * @param returnCode
     * @throws IllegalArgumentException
     */
    public JsonResult(int returnCode, String returnMsg, Object returnData) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.returnData = returnData;
    }

    /**
     * 构造方法
     *
     * @param returnCode
     * @throws IllegalArgumentException
     */
    public JsonResult(int returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;

    }


    /**
     * 设置返回数据
     *
     * @param returnData
     * @throws NullPointerException
     */
    public void setReturnData(Object returnData) {
        if (returnData == null) {
            throw new NullPointerException("returnData is null");
        }
        this.returnData = returnData;
    }



    /**
     * 设置分页信息
     *
     * @param startNum  开始行数
     * @param pageCount 总页数
     * @param rowsCount 总行数
     */
    public void setPageInfo(int startNum, int pageCount, int rowsCount) {
        this.startNum = String.valueOf(startNum);//开始行号
        this.pageCount = String.valueOf(pageCount);//总记录条数
        this.rowsCount = String.valueOf(rowsCount);
        ;//总页数
    }


}
