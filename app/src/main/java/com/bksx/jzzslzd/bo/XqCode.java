package com.bksx.jzzslzd.bo;

/**
 * 辖区代码表
 * Created by YangJ on 2017/6/29.
 */
public class XqCode {
    private String id; //辖区编号
    private String name; //辖区名称
    private String xzqh; //辖区行政区划

    public XqCode() {

    }

    public XqCode(String id, String name, String xzqh) {
        this.id = id;
        this.name = name;
        this.xzqh = xzqh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }
}
