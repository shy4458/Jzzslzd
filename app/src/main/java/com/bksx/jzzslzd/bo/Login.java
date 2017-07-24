package com.bksx.jzzslzd.bo;

/**
 * 登录
 * 
 * @author Y_Jie
 * 
 */
public class Login {

	private String userName; // 用户名
	private String passWord; // 用户密码
	private String passWordYuan;// 原始密码
	private String jlmm; // 是否记录密码
	private String versionDb; // 数据库版本
	private String versionSys;// 系统版本

	public String getPassWordYuan() {
		return passWordYuan;
	}

	public void setPassWordYuan(String passWordYuan) {
		this.passWordYuan = passWordYuan;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getJlmm() {
		return jlmm;
	}

	public void setJlmm(String jlmm) {
		this.jlmm = jlmm;
	}

	public String getVersionDb() {
		return versionDb;
	}

	public void setVersionDb(String versionDb) {
		this.versionDb = versionDb;
	}

	public String getVersionSys() {
		return versionSys;
	}

	public void setVersionSys(String versionSys) {
		this.versionSys = versionSys;
	}

}
