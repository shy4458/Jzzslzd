package com.bksx.jzzslzd.bo;

import java.io.Serializable;

/**
 * 角色功能对应操作权限
 * 
 * @author Y_Jie
 */
public class RoleFuncOper implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// 功能ID
	private String opecode;// 操作权限(8、4、2、1)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpecode() {
		return opecode;
	}

	public void setOpecode(String opecode) {
		this.opecode = opecode;
	}

}