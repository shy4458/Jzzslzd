package com.bksx.jzzslzd.bo;

import java.util.ArrayList;

/**
 * 用户登录
 * Created by YangJ on 2017/6/29.
 */
public class UserLogin {
    private String username; //用户名。默认为管理员身份证号码
    private String password; //密码。默认为0
    private String version; //版本号。

    private String version_new;//最新版本
    private String url;//最新版本

    private String u_id; //管理员编码
    private String u_name; //管理员姓名
    private String u_duty;// 管理员职务
    private String u_phone; //管理员电话
    private String u_did; //管理员单位编码
    private String u_dname; //管理员单位名称

    private String u_pcs; //管理员所属派出所

    private ArrayList<CodeTable> fwz;//管理员流管站列表

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_new() {
        return version_new;
    }

    public void setVersion_new(String version_new) {
        this.version_new = version_new;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_duty() {
        return u_duty;
    }

    public void setU_duty(String u_duty) {
        this.u_duty = u_duty;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_did() {
        return u_did;
    }

    public void setU_did(String u_did) {
        this.u_did = u_did;
    }

    public String getU_dname() {
        return u_dname;
    }

    public void setU_dname(String u_dname) {
        this.u_dname = u_dname;
    }

    public String getU_pcs() {
        return u_pcs;
    }

    public void setU_pcs(String u_pcs) {
        this.u_pcs = u_pcs;
    }

    public ArrayList<CodeTable> getFwz() {
        return fwz;
    }

    public void setFwz(ArrayList<CodeTable> fwz) {
        this.fwz = fwz;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
