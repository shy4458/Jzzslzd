package com.bksx.jzzslzd.bo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: ReturnValue.java（类名称）
 * </p>
 * <p>
 * Description: 登录返回实体
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012-04-24
 * </p>
 * <p>
 * Company: 北控三兴
 * </p>
 * 
 * @author 杜伟
 * @version V1.0
 */
public class ReturnDzkxx implements Serializable {
	String ztbs;// 状态标识
	String ztxx;// 状态信息
	List<ReturnDzk> list;// 返回值列表

	/**
	 * @return the ztbs
	 */
	public String getZtbs() {
		return ztbs;
	}

	/**
	 * @param ztbs
	 *            the ztbs to set
	 */
	public void setZtbs(String ztbs) {
		this.ztbs = ztbs;
	}

	/**
	 * @return the ztxx
	 */
	public String getZtxx() {
		return ztxx;
	}

	/**
	 * @param ztxx
	 *            the ztxx to set
	 */
	public void setZtxx(String ztxx) {
		this.ztxx = ztxx;
	}

	/**
	 * @return the list
	 */
	public List<ReturnDzk> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<ReturnDzk> list) {
		this.list = list;
	}

}
