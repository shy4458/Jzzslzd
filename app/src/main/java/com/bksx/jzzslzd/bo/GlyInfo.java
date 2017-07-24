package com.bksx.jzzslzd.bo;

/**
 * 管理员数据解析类
 * 
 * @author Y_Jie
 * 
 */
public class GlyInfo {

	private String login_state; // 登录状态
	private String login_address; // 蓝牙地址
	private String login_area;// 行政区划名称
	private String login_service_id;// 服务站编号
	private String login_admin_id; // 管理员编号
	private String login_admin_name;// 管理员姓名
	private String login_number;// 管理员编码
	private String login_card_id;// 身份证号码
	private String login_rbac;// rbac单位
	private String login_govern_id;// 辖区编号
	private String login_govern_name;// 辖区名称

	private String login_czzx_id;// 村中心编号
	private String login_xzqh;// 行政区划
	private String login_rylx;// 人员类型
	private String login_gzrw;// 工作任务
	private String login_rylxid;// 人员类型ID
	private String[][] login_glylb;

	public String getLogin_rylxid() {
		return login_rylxid;
	}

	public void setLogin_rylxid(String loginRylxid) {
		login_rylxid = loginRylxid;
	}

	public String getLogin_xzqh() {
		return login_xzqh;
	}

	public void setLogin_xzqh(String loginXzqh) {
		login_xzqh = loginXzqh;
	}

	public String getLogin_rylx() {
		return login_rylx;
	}

	public void setLogin_rylx(String loginRylx) {
		login_rylx = loginRylx;
	}

	public String getLogin_gzrw() {
		return login_gzrw;
	}

	public void setLogin_gzrw(String loginGzrw) {
		login_gzrw = loginGzrw;
	}

	public String getLogin_czzx_id() {
		return login_czzx_id;
	}

	public void setLogin_czzx_id(String loginCzzxId) {
		login_czzx_id = loginCzzxId;
	}

	public String getLogin_state() {
		return login_state;
	}

	public void setLogin_state(String loginState) {
		login_state = loginState;
	}

	public String getLogin_address() {
		return login_address;
	}

	public void setLogin_address(String loginAddress) {
		login_address = loginAddress;
	}

	public String getLogin_area() {
		return login_area;
	}

	public void setLogin_area(String loginArea) {
		login_area = loginArea;
	}

	public String getLogin_service_id() {
		return login_service_id;
	}

	public void setLogin_service_id(String loginServiceId) {
		login_service_id = loginServiceId;
	}

	public String getLogin_admin_id() {
		return login_admin_id;
	}

	public void setLogin_admin_id(String loginAdminId) {
		login_admin_id = loginAdminId;
	}

	public String getLogin_admin_name() {
		return login_admin_name;
	}

	public void setLogin_admin_name(String loginAdminName) {
		login_admin_name = loginAdminName;
	}

	public String getLogin_number() {
		return login_number;
	}

	public void setLogin_number(String loginNumber) {
		login_number = loginNumber;
	}

	public String getLogin_card_id() {
		return login_card_id;
	}

	public void setLogin_card_id(String loginCardId) {
		login_card_id = loginCardId;
	}

	public String getLogin_rbac() {
		return login_rbac;
	}

	public void setLogin_rbac(String loginRbac) {
		login_rbac = loginRbac;
	}

	public String getLogin_govern_id() {
		return login_govern_id;
	}

	public void setLogin_govern_id(String loginGovernId) {
		login_govern_id = loginGovernId;
	}

	public String getLogin_govern_name() {
		return login_govern_name;
	}

	public void setLogin_govern_name(String loginGovernName) {
		login_govern_name = loginGovernName;
	}

	/**
	 * @return the login_glylb
	 */
	public String[][] getLogin_glylb() {
		return login_glylb;
	}

	/**
	 * @param login_glylb
	 *            the login_glylb to set
	 */
	public void setLogin_glylb(String[][] login_glylb) {
		this.login_glylb = login_glylb;
	}
}
