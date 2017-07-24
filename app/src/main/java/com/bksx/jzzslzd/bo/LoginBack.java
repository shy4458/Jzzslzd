package com.bksx.jzzslzd.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 登录返回解析类
 * 
 * @author Y_Jie
 * 
 */
public class LoginBack implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ztbs; // 状态标识
	private String ztxx; // 状态信息
	private String varDb_new;// 新的数据库版本
	private String varSys_new;// 最新的系统版本
	private GlyInfo glyInfo; // 管理员信息
	private List<Menu> menus;// 菜单列表
	private List<RoleFuncOper> roleFuncOper;// 角色对应功能操作权限

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getVarDb_new() {
		return varDb_new;
	}

	public void setVarDb_new(String varDbNew) {
		varDb_new = varDbNew;
	}

	public String getVarSys_new() {
		return varSys_new;
	}

	public void setVarSys_new(String varSysNew) {
		varSys_new = varSysNew;
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

	public GlyInfo getGlyInfo() {
		return glyInfo;
	}

	public void setGlyInfo(GlyInfo glyInfo) {
		this.glyInfo = glyInfo;
	}

	public List<RoleFuncOper> getRoleFuncOper() {
		return roleFuncOper;
	}

	public void setRoleFuncOper(List<RoleFuncOper> roleFuncOper) {
		this.roleFuncOper = roleFuncOper;
	}

}