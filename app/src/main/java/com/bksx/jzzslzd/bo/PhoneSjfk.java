package com.bksx.jzzslzd.bo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhoneSjfk implements Serializable{
	private String sjbh; // 事件编号
	private String czzt; // 处置状态
	private String czdwxzqh; // 处置单位行政区划
	private String jsdw; // 接收单位
	private String psfknr; // 处置方法
	private String sfczwj; // 是否处置完结
	public String getSjbh() {
		return sjbh;
	}
	public void setSjbh(String sjbh) {
		this.sjbh = sjbh;
	}
	public String getCzzt() {
		return czzt;
	}
	public void setCzzt(String czzt) {
		this.czzt = czzt;
	}
	public String getCzdwxzqh() {
		return czdwxzqh;
	}
	public void setCzdwxzqh(String czdwxzqh) {
		this.czdwxzqh = czdwxzqh;
	}
	public String getJsdw() {
		return jsdw;
	}
	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}
	public String getPsfknr() {
		return psfknr;
	}
	public void setPsfknr(String psfknr) {
		this.psfknr = psfknr;
	}
	public String getSfczwj() {
		return sfczwj;
	}
	public void setSfczwj(String sfczwj) {
		this.sfczwj = sfczwj;
	}
	

}
