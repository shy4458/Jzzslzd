package com.bksx.jzzslzd.bo;

import java.util.ArrayList;

public class RyxxzcBean {
	private String ztbs;  //状态标识
	private String ztxx;  //状态信息
	private ArrayList<RyzcVo> list;//反馈数据
	public String getZtbs() {
		return ztbs;
	}
	public void setZtbs(String ztbs) {
		this.ztbs = ztbs;
	}
	public String getZtxx() {
		return ztxx;
	}
	public void setZtxx(String ztxx) {
		this.ztxx = ztxx;
	}
	public ArrayList<RyzcVo> getList() {
		return list;
	}
	public void setList(ArrayList<RyzcVo> list) {
		this.list = list;
	}
	
}
