package com.bksx.jzzslzd.bo;

import java.io.Serializable;

/**
 * <p>
 * Title: 台账信息(用于接收手机端bean)
 * </p>
 * <p>
 * Description: PhoneTzcsInfo
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012-09-06
 * </p>
 * <p>
 * Company: bksx
 * </p>
 * 
 * @author 杜伟
 * @version V1.0
 */
@SuppressWarnings("serial")
public class PhoneTzcsInfo implements Serializable {

	// ******************台账(公共字段)**************
	private String glybm;// 管理员编码

	// ******************社区化业务(专用字段)*************
	private String zzzxbh;// 综治中心编号

	// ******************流管业务(专用字段)**************
	private String fwzbh;// 服务站编号

	private String ssxzqh;// 行政区划

	private String rylx;// 人员类型

	private String qssj;// 起始时间
	private String jzsj;// 截止时间

	/**
	 * @return the qssj
	 */
	public String getQssj() {
		return qssj;
	}

	/**
	 * @param qssj
	 *            the qssj to set
	 */
	public void setQssj(String qssj) {
		this.qssj = qssj;
	}

	/**
	 * @return the jzsj
	 */
	public String getJzsj() {
		return jzsj;
	}

	/**
	 * @param jzsj
	 *            the jzsj to set
	 */
	public void setJzsj(String jzsj) {
		this.jzsj = jzsj;
	}

	public String getRylx() {
		return rylx;
	}

	public void setRylx(String rylx) {
		this.rylx = rylx;
	}

	public String getSsxzqh() {
		return ssxzqh;
	}

	public void setSsxzqh(String ssxzqh) {
		this.ssxzqh = ssxzqh;
	}

	public String getGlybm() {
		return glybm;
	}

	public void setGlybm(String glybm) {
		this.glybm = glybm;
	}

	public String getZzzxbh() {
		return zzzxbh;
	}

	public void setZzzxbh(String zzzxbh) {
		this.zzzxbh = zzzxbh;
	}

	public String getFwzbh() {
		return fwzbh;
	}

	public void setFwzbh(String fwzbh) {
		this.fwzbh = fwzbh;
	}

}
