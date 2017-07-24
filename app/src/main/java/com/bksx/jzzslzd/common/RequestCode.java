package com.bksx.jzzslzd.common;

public class RequestCode {
	/**
	 * 管理员登录
	 */
	public static final String LOGIN = "000001";
	public static final String LOGIN_NEW = "000014";
	public static final String LOGOUT = "000013";

	public static final String GLTZ = "010023"; // 管理台帐
	// public static final String SHHGLTZ = "000011"; // 管理台帐
	public static final String SHHGLTZ = "011017"; // 管理台帐
	public static final String RYTZXX = "011018";// 点击数量查人
	// 人员信息 010001 --- 019999
	public static final String RYKCHS = "010018"; // 人员快采核实
	public static final String RYJCHS = "010020"; // 人员简采核实
	public static final String RYXXBC = "010032"; // 人员信息补采
	public static final String RYXXQY = "010011"; // 人员信息迁移
	public static final String RYXXJZDZX = "010006"; // 人员信息按居住的注销
	public static final String RYXXHX = "010007"; // 人员信息核销
	public static final String JZDRYXXHX = "010008"; // 人员信息核销
	public static final String RYXXZXHF = "010033"; // 人员注销恢复
	public static final String RYXXXCHS = "010021";// 人员信息详采核实
	public static final String RYXXXCDJ = "010002";// 人员信息详采登记
	public static final String RYXXJDCJ = "010019";// 人员信息简采录入
	public static final String RYXXLSXXCX = "010025";// 人员历史信息查询
	public static final String RYXXHC = "010022";// 人员信息核查
	public static final String RYBCCX = "010030";// 人员信息补采
	public static final String RYXXBCDJ = "010028";// 人员补采登记
	public static final String RYXXZCDJ = "011019";// 人员暂存登记
	public static final String RYXXXZ = "011020";// 人员信息下载

	public static final String RYDWCJHC = "010031";// 人员按单位采集核查
	public static final String RYDWCJDJ = "010034";// 人员按单位采集登记
	public static final String RYXCFWHS = "010027";// 人员按单位采集登记
	public static final String RYXXHZCX = "010017";// 人员信息户主查询
	public static final String RYXXHXCX = "010004";// 人员信息户主查询
	public static final String RYXXHCBYNOLG = "011000";// 人员信息核查非流管员
	public static final String RYXXHCQDBYNOLG = "011007";// 人员信息核查确定非流管员
	public static final String RYXXCXQDBYNOLG = "011008";// 人员信息核查确定非流管员
	public static final String RYHS = "011016"; // 人员采集核实
	public static final String CRGLXXDJ = "012001";// 出入管理信息-登记
	public static final String WKWZLBCX = "012002";// 无卡无证列表查询
	public static final String CRGLXXCX = "012000";// 出入管理信息-查询
	public static final String CRTZ = "012004";// 出入台账

	// 户籍人员核查
	public static final String HJSMHSRY = "011010";// 扫描人员核实
	public static final String HJSLARCX = "011012";// 手录按人查询
	public static final String HJSLAZZFCX = "023007";// 手录按自住房查询
	public static final String HJSLACZFCX = "022009";// 手录按自住房查询
	public static final String RYXXBYFW = "011011";// 房屋下人员列表查询
	public static final String HJRYXXZX = "011009";// 户籍人员注销

	// 地信息 020001 --- 029999
	public static final String FWXXCX = "022009";// 房屋信息查询
	public static final String WZJZCJ = "020001";// 违章建筑采集
	public static final String FWXXXGCX = "022005"; // 房屋信息修改查询
	public static final String FWXXCXXX = "022008"; // 房屋信息查询
	public static final String FWXXXG = "022007"; // 房屋信息修改
	public static final String FWXXDJ = "022002"; // 房屋信息登记
	public static final String FWXXZXCX = "022003"; // 房屋信息注销查询
	public static final String FWZX = "022004"; // 房屋注销
	public static final String FWXXBCCX = "022010"; // 房屋信息补采查询
	public static final String FWXXBC = "022011"; // 房屋信息补采
	public static final String FWTZXX = "022014";// 点击房屋查房
	public static final String FWXXXX = "022012";// 房屋详细信息
	public static final String FWXXTORYXX = "010026";// 人员信息查询（通过房屋编号查询）由房屋详细信息页面点击获得该房人员信息
														// -->
	public static final String QYFWCX = "023002";// 企业房屋信息查询
	public static final String QYFWBC = "023004";// 企业房屋补采
	public static final String QYFWXG = "023003";// 企业房屋修改
	public static final String ZZFWCX = "023007";// 自住房屋查询
	public static final String ZZFWXG = "023008";// 自住房屋修改
	public static final String ZZFWBC = "023009";// 自住房屋补采
	public static final String ZZFWZX = "023010";// 自住房屋注销
	// 事件
	public static final String SJDJ = "040004";// 事件登记
	public static final String SJFK = "040005";// 事件反馈
	public static final String SJCX = "040006";// 事件查询
	public static final String SJXXCX = "040007";// 事件详细查询

	public static final String CSTR = "连接超时，返回登录界面";// 事件详细查询
}
