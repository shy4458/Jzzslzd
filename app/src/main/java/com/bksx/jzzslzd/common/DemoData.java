package com.bksx.jzzslzd.common;

import java.util.HashMap;
import java.util.Map;

public class DemoData {
	private Map<String, String> demoData = new HashMap<String, String>();

	public DemoData() {
		// 登录
		demoData.put(
				RequestCode.LOGIN,
				"{'ztbs':'30','ztxx':'登录成功','glyInfo':{'login_state':'30','login_address':'00:13:EF:FF:85:54','login_area':'北京市朝阳区建外街道办事处前辛庄村村委会综治中心','login_service_id':'bc246c316fc800','login_admin_id':'bc132c0675a500','login_admin_name':'刘宝珍','login_number':'0501155','login_card_id':'','login_rbac':'0105001027','login_govern_id':'bc246c316fc801','login_govern_name':'光华里社区','login_czzx_id':'7da46ab834ea01','login_xzqh':'110105001000','login_rylx':'流动人口管理员','login_rylxid':'02','login_gzrw':'门岗执勤'},'menus':[{'id':'df000','name':'* 实有房屋 *'},{'id':'df001','name':'实有房屋核查'},{'id':'dq000','name':'* 企业信息 *'},{'id':'dq003','name':'\u003e\u003e企业核查'},{'id':'qb000','name':'* 社会服务 *'},{'id':'qb001','name':'便民服务租赁房屋'},{'id':'qb002','name':'便民服务职业介绍'},{'id':'qb003','name':'便民服务超市信息'},{'id':'qb004','name':'便民服务菜店信息'},{'id':'qb005','name':'便民服务药店信息'},{'id':'qm000','name':'* 民意信息 *'},{'id':'qm001','name':'\u003e\u003e民意登记'},{'id':'qm002','name':'\u003e\u003e民意查询'},{'id':'rh000','name':'* 户籍人口 *'},{'id':'rh001','name':'户籍人员采集'},{'id':'rh002','name':'户籍人员核查'},{'id':'rh003','name':'户籍人员暂存'},{'id':'rk000','name':'* 出入管理 *'},{'id':'rk001','name':'\u003e\u003e市民卡'},{'id':'rk002','name':'\u003e\u003e入村登记'},{'id':'rk003','name':'\u003e\u003e出入查询'},{'id':'rk004','name':'\u003e\u003e出入台账'},{'id':'rl000','name':'* 流动人口 *'},{'id':'rl001','name':'流动人员快采'},{'id':'rl002','name':'流动人员简采'},{'id':'rl003','name':'流动人员详采'},{'id':'rl004','name':'流动人员补采'},{'id':'rl005','name':'流动人员注销'},{'id':'rl006','name':'流动人员暂存'},{'id':'rl007','name':'流动人员查询'},{'id':'rl008','name':'流动人员核查'},{'id':'rs000','name':'* 实有人口 *'},{'id':'rs001','name':'实有人口核查'},{'id':'rs002','name':'实有人口查询'},{'id':'ss000','name':'* 事件信息 *'},{'id':'ss001','name':'\u003e\u003e事件查询'},{'id':'ss002','name':'\u003e\u003e事件上报'},{'id':'wj000','name':'* 设施信息 *'},{'id':'wj003','name':'\u003e\u003e设施核查'}],'roleFuncOper':[{'id':'rh002','opecode':'10'},{'id':'rl008','opecode':'12'},{'id':'df003','opecode':'2'},{'id':'df002','opecode':'14'},{'id':'df001','opecode':'14'},{'id':'dq003','opecode':'6'},{'id':'ss001','opecode':'1'},{'id':'wj003','opecode':'14'}]}");
		// 快采查询
		demoData.put(
				"022009",
				"{'ztbs':'09','data':[['09',null,null,null,null,null],['7de39aaf3a2c00','田宝玉','000036','北京市大兴区采育镇采育镇东半壁店村东文巷一条1号','1','01'],['7de39ba9836d00','田宝玉','000070','北京市大兴区采育镇采育镇东半壁店村东文巷一条1号','1','01']]}");
		demoData.put("010018",
				"{'ztbs':'09','ztxx':'002328','data':[['09','002328']]}");
		demoData.put(
				"010022",
				"{'ztbs':'09','ztxx':'159','data':[['09','159',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null],['藏廷标','1','342127196711175812','000016','安徽省阜南','11',null,'北京市大兴区采育镇庙洼营村市场南口','庙洼营村市场南口自营',null,'fa83ab63ca9000','19671117000000','1','bc5aeada992901',null,'1',null,'1409067','bc246be8b1e400','bc246be8b1e403','20131029112056','1'],['康艳凯','1','13252319810412143X','000015','河北省张家口市沽源县二道沟乡四号村四号自然村855号','11','15011189413','北京市大兴区采育镇庙洼营村市场南口',null,null,'7dacda5f3e8300','19810412000000',null,null,null,'0',null,'1409067','bc246be8b1e400','bc246be8b1e403','20131029111937','2'],['边鹤','1','232326199202012057','000062','黑龙江省哈尔滨市青冈县芦河镇先锋村','11','80203053','北京市大兴区采育镇张各庄村','北京兴凤印刷厂',null,'bbfc23ddd5f400','19920201000000',null,null,null,'0',null,'1409067','bc246be8b1e400','bc246be8b1e407','20131023221056','3'],['孙显英','2','232326195003202044','000061','黑龙江省哈尔滨市青冈县芦河镇先锋村','11','80203053','北京市大兴区采育镇张各庄村','北京兴凤印刷厂',null,'bbfc23ddd3c800','19500320000000','0','bc5abd7bc90500','1','1','000060','1409067','bc246be8b1e400','bc246be8b1e407','20131023220413','4'],['边义','1','232326195003132031','000060','黑龙江省哈尔滨市青冈县芦河镇先锋村','11','80203053','北京市大兴区采育镇张各庄村','北京兴凤印刷厂',null,'bbfc23ddd29000','19500313000000','1','bc5abd7bc90500',null,'1',null,'1409067','bc246be8b1e400','bc246be8b1e407','20131023215950','5']]}");
		demoData.put("010007",
				"{'ztbs':'09','ztxx':'核销成功','data':[['09','核销成功']]}");
		demoData.put(
				"022012",
				"{'ztbs':'09','data':[['09',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null],['7de39aaf3a2c00','北壁店村东文巷一条1号','1','1','152','02','01','1 ','1000','20141117000000','20141117000000','04',null,null,'faec944b096701.jpg§','18']]}");
		demoData.put(
				"010026",
				"{'ztbs':'09','ztxx':'3','data':[['09','3',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null],['000100','bc293a27961300','关丽娜','1','19111111000000','111111191111111113',null,null,null,null,null,'的',null,null,'北京市大兴区采育镇采育镇东半壁店村东文巷一条1号',null,null,'1409163','bc246be8b1e400',null,'20141210114348','1'],['000095','faec2bf939f400','刘鑫','1','19111111000000','111111191111111113',null,null,null,null,null,'和你',null,null,'北京市大兴区采育镇采育镇东半壁店村东文巷一条1号',null,null,'1409163','bc246be8b1e400',null,'20141205155714','2'],['000086','7dd53c5c0cc000','测试题','1','19111111000000','111111191111111113',null,null,null,null,null,'有',null,null,'北京市大兴区采育镇采育镇东半壁店村东文巷一条1号',null,null,'1409163','bc246be8b1e400',null,'20141205155325','3'],['000084','faec2bf2ee2200','pis','1','19111111000000','111111191111111113',null,null,null,null,null,'发给',null,null,'北京市大兴区采育镇采育镇东半壁店村东文巷一条1号',null,null,'1409163','bc246be8b1e400',null,'20141205155059','4'],['000083','faec2bf1ec7d00','个月','1','19111111000000','111111191111111113',null,null,null,null,null,'好吧',null,null,'北京市大兴区采育镇采育镇东半壁店村东文巷一条1号',null,null,'1409163','bc246be8b1e400',null,'20141205154959','5']]}");
		demoData.put("000012", "{'ztbs':'09','ztxx':'操作成功'}");
		demoData.put("070002", "{'ztbs':'09','ztxx':'签到成功'}");
		demoData.put(
				"000011",
				"{'ztbs':'09','ztxx':'操作成功','list':[{'rsl':2,'czfsl':0,'qyfsl':0,'qysl':0,'sssl':0,'sjsl':0,'mysl':0,'crsl':0,'gzdsj':'(其中工作地采集0)人','hjsl':0,'zzfsl':0}]}");
		demoData.put(
				"000006",
				"{'ztbs':'09','ztxx':'有新工作提示','list':[{'xfbh':12345,'gzbh':1234,'gzmc':asd,'gznr':qwe,'djrq':20150405000000,'gzwcrq':20150501000000,'zzzxmc':建外街道办事处}]}");

	}

	public String getDemoData(String code) {
		StaticObject.print("获取DemoData：" + code);
		if (demoData.get(code) == null) {
			return "";
		}
		return demoData.get(code);
	}

}
