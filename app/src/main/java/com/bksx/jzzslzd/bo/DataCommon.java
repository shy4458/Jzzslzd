package com.bksx.jzzslzd.bo;

/**
 * 数据传输状态
 * @author wb
 *
 */
public class DataCommon {
	
	private String ztbs;  //状态标识
	private String ztxx;  //状态信息
	private String[][] data;//反馈数据
	
	
	

	
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
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
	
	
}
