package com.bksx.jzzslzd.bo;

import java.util.ArrayList;


public class Sjlb {
	private String ztbs;  //状态标识
	private String ztxx;  //状态信息
	private ArrayList<List> list;//反馈数据
	private String currentPage;  //当前页数
	private String totalPage;  //总页数
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
	public ArrayList<List> getList() {
		return list;
	}
	public void setList(ArrayList<List> list) {
		this.list = list;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	@Override
	public String toString() {
		return "Sjlb [ztbs=" + ztbs + ", ztxx=" + ztxx + ", list=" + list
				+ ", currentPage=" + currentPage + ", totalPage=" + totalPage
				+ "]";
	}
	
}
