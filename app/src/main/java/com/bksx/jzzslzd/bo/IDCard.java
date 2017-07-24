package com.bksx.jzzslzd.bo;

import java.io.Serializable;

/**
 * 身份证扫描后的人员信息类
 * 
 * @author Y_Jie
 * 
 */
public class IDCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xm;
	private String xb;
	private String csrq;
	private String mz;
	private String hjdz;
	private String zjhm;
	private String photoPath;

	public IDCard() {
	}

	public void setIDCard(String cardMsg) {
		String[] cmsgs = cardMsg.split(",");
		this.xm = cmsgs[0];
		this.xb = cmsgs[1];
		this.mz = cmsgs[2] + "族";
		this.csrq = cmsgs[3].substring(0, 4) + "-" + cmsgs[3].substring(4, 6)
				+ "-" + cmsgs[3].substring(6, 8);
		this.hjdz = cmsgs[4];
		this.zjhm = cmsgs[5];
		this.photoPath = cmsgs[8];
	}

	/**
	 * @return the xm
	 */
	public String getXm() {
		return xm;
	}

	/**
	 * @param xm
	 *            the xm to set
	 */
	public void setXm(String xm) {
		this.xm = xm;
	}

	/**
	 * @return the xb
	 */
	public String getXb() {
		return xb;
	}

	/**
	 * @param xb
	 *            the xb to set
	 */
	public void setXb(String xb) {
		this.xb = xb;
	}

	/**
	 * @return the csrq
	 */
	public String getCsrq() {
		return csrq;
	}

	/**
	 * @param csrq
	 *            the csrq to set
	 */
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	/**
	 * @return the mz
	 */
	public String getMz() {
		return mz;
	}

	/**
	 * @param mz
	 *            the mz to set
	 */
	public void setMz(String mz) {
		this.mz = mz;
	}

	/**
	 * @return the hjdz
	 */
	public String getHjdz() {
		return hjdz;
	}

	/**
	 * @param hjdz
	 *            the hjdz to set
	 */
	public void setHjdz(String hjdz) {
		this.hjdz = hjdz;
	}

	/**
	 * @return the zjhm
	 */
	public String getZjhm() {
		return zjhm;
	}

	/**
	 * @param zjhm
	 *            the zjhm to set
	 */
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	/**
	 * @return the photoPath
	 */
	public String getPhotoPath() {
		return photoPath;
	}

	/**
	 * @param photoPath
	 *            the photoPath to set
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

}
