package com.bksx.jzzslzd.bo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhoneSjcxcy implements Serializable{
	private String sffk; // 是否是反馈查询
	private String glybm;	//管理员编码
	private String pageNum; // 当前页
	public String getSffk() {
		return sffk;
	}
	public void setSffk(String sffk) {
		this.sffk = sffk;
	}
	public String getGlybm() {
		return glybm;
	}
	public void setGlybm(String glybm) {
		this.glybm = glybm;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	
}
