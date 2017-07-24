package com.bksx.jzzslzd.bo;

import java.util.ArrayList;

public class SjxxcyBean {
	private String ztbs;  //状态标识
	private String ztxx;  //状态信息
	private ArrayList<List1> list;//反馈数据
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
	public ArrayList<List1> getList() {
		return list;
	}
	public void setList(ArrayList<List1> list) {
		this.list = list;
	}
	
}
