package com.sx.mobile.gateway;

import java.io.Serializable;

/**
 * @author oyxz 数据Bean
 */
public class DataBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 业务标识
	 */
	private String id;
	/**
	 * 手机IMSI
	 */
	private String IMSI;
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * 业务内容
	 */
	private String content;
	/**
	 * SessionID
	 */
	private String sessionId;

	/**
	 * 文件名
	 */
	private String[] fileName;
	/**
	 * 文件内容
	 */
	private byte[][] bFile;

	/**
	 * @return Returns the bFile.
	 */
	public byte[][] getBFile() {
		return bFile;
	}

	/**
	 * @param file
	 *            The bFile to set.
	 */
	public void setBFile(byte[][] file) {
		bFile = file;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String[] getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the iMSI.
	 */
	public String getIMSI() {
		return IMSI;
	}

	/**
	 * @param imsi
	 *            The iMSI to set.
	 */
	public void setIMSI(String imsi) {
		IMSI = imsi;
	}

	/**
	 * @return Returns the phoneNumber.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            The phoneNumber to set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
