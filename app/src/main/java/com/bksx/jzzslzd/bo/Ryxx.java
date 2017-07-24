package com.bksx.jzzslzd.bo;

/**
 * 数据传输状态
 * @author wb
 *
 */
public class Ryxx {
	
	 private String zshs;  //状态标识
	 private String ryzc; //人员暂存状态
	 private String[][] data;//发送数据
	 
	 
	 
	 
	public String getRyzc() {
		return ryzc;
	}
	public void setRyzc(String ryzc) {
		this.ryzc = ryzc;
	}
	public String getZshs() {
		return zshs;
	}
	public void setZshs(String zshs) {
		this.zshs = zshs;
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	
	
	

	
	
	
}
