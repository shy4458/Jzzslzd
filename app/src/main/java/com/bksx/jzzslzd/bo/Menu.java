package com.bksx.jzzslzd.bo;

import java.io.Serializable;

/**
 * 手机菜单实体
 * 
 * @author Y_Jie
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// 菜单ID
	private String name;// 菜单名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}