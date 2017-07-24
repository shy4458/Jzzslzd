package com.bksx.jzzslzd.bo;

import com.bksx.jzzslzd.tools.FormCheck;

public class RyzcVo {
	/**
	 * 人员编号(补采用)
	 */
	private String rybh;
	/**
	 * 填表日期(补采用)
	 */
	private String tbrq;
	/**
	 * 登记日期(补采用)
	 */
	private String djrq;
	/**
	 * 个人登记表号(补采用)
	 */
	private String grdjbh;
	/*************/
	/** 基本信息 **/
	/*************/
	/**
	 * 姓名
	 */
	private String jb_xm;
	/**
	 * 民族
	 */
	private String jb_mz;
	/**
	 * 身份证
	 */
	private String jb_sfz;
	/**
	 * 出生日期
	 */
	private String jb_csrq;
	/**
	 * 性别
	 */
	private String jb_xb;
	/**
	 * 户籍地址
	 */
	private String jb_hjdz;
	/**
	 * 户籍详细地址
	 */
	private String jb_hjxxdz;
	/**
	 * 所属辖区
	 */
	private String jb_ssxq;
	/**
	 * 受教育程度
	 */
	private String jb_sjycd;
	/**
	 * 政治面貌
	 */
	private String jb_zzmm;
	/**
	 * 居住证件
	 */
	private String jb_jzzj;
	/**
	 * 户籍类别
	 */
	private String jb_hjlb;
	/**
	 * 免疫接种
	 */
	private String jb_myjzz;
	/**
	 * 出生地
	 */
	private String jb_csd;
	/**
	 * 婚姻状况
	 */
	private String jb_hyzk;
	/**
	 * 婚育证明
	 */
	private String jb_hyzm;
	/**
	 * 联系电话
	 */
	private String jb_lxdh;
	/**
	 * 是否扫描
	 */
	private String jb_isScanned;
	/**
	 * 照片路径
	 */
	private String jb_photoPath;
	/***************/
	/** 家庭户信息 **/
	/***************/
	/**
	 * 是否家庭户
	 */
	private String jt_sfjth;
	/**
	 * 是否户主
	 */
	private String jt_sfhz;
	/**
	 * 外来人口数
	 */
	private String jt_wlrk;
	/**
	 * 16岁以下
	 */
	private String jt_16yx;
	/**
	 * 16岁以下男
	 */
	private String jt_16nan;
	/**
	 * 16岁以下女
	 */
	private String jt_16nv;
	/**
	 * 与户主关系
	 */
	private String jt_yhzgx;
	/**
	 * 与户主其他关系
	 */
	private String jt_yhzqagx;
	/**
	 * 户主登记表号
	 */
	private String jt_hzdjbh;
	/**
	 * 户主登记表查询结果
	 */
	private String jt_hid;
	/*************/
	/** 居住信息 **/
	/*************/
	/**
	 * 离开原籍日期
	 */
	private String jz_lkyjrq;
	/**
	 * 来京日期
	 */
	private String jz_ljrq;
	/**
	 * 来现住地日期
	 */
	private String jz_lxzdrq;
	/**
	 * 来京原因
	 */
	private String jz_ljyy;
	/**
	 * 来京其他原因
	 */
	private String jz_ljqtyy;
	/**
	 * 前居住地址
	 */
	private String jz_qjzdz;
	/**
	 * 居住类型
	 */
	private String jz_jzlx;
	/**
	 * 房屋登记表号
	 */
	private String jz_fwdjbh;
	/**
	 * 房屋房主姓名
	 */
	private String jz_fzxm;
	/**
	 * 房屋房主身份证号
	 */
	private String jz_fzsfzh;

	/**
	 * 现住地址
	 */
	private String jz_xzdz;
	/**
	 * 房屋查询结果
	 */
	private String[] jz_fwcxjg;
	/**
	 * 房屋其他居住信息
	 */
	private String jz_fwqtjzlx;
	/*************/
	/** 就业信息 **/
	/*************/
	/**
	 * 目前状况
	 */
	private String jy_mqzk;
	/**
	 * 单位名称
	 */
	private String jy_jydwmc;
	/**
	 * 单位登记表号
	 */
	private String jy_dwdjbh;
	/**
	 * 详细地址
	 */
	private String jy_dwxxdz;
	/**
	 * 所属行业
	 */
	private String jy_dwsshy;
	/**
	 * 其他所属行业
	 */
	private String jy_dwqtsshy;
	/**
	 * 从事工作
	 */
	private String jy_csgz;
	/**
	 * 其他从事工作
	 */
	private String jy_qtcsgz;
	/**
	 * 职业
	 */
	private String jy_zy;
	/**
	 * 签订劳动合同
	 */
	private String jy_qdldht;
	/**
	 * 单位所在地
	 */
	private String jy_dwdz;
	/**
	 * 在京参加社保
	 */
	private String jy_zjcjsb;
	/**
	 * 参加社保
	 */
	private String jy_cjsb;
	/**
	 * 学校名称
	 */
	private String jy_xxmc;
	/**
	 * 学校所在地
	 */
	private String jy_xxszd;
	/**
	 * 详细信息
	 */
	private String jy_xxxx;
	/**
	 * 证明材料信息
	 *
	 */
	private String zmcl;
	/**
	 * 房屋信息
	 *
	 */
	private String[] fwxx;
	//所属派出所
	private String sspcs;

	/**
	 * 备份信息
	 */
	private String bz;
	/**
	 * 管理员信息
	 */
	private String tbr;//填表人
	private String glybm;//管理员编码
	private String glyxm;//管理员姓名
	
	
	

	public RyzcVo() {
	}

	public RyzcVo(String[] data) {
		this.setRybh(data[1]);
		this.setTbrq(data[31]);
		this.setDjrq(data[33]);
		this.setGrdjbh(data[85]);
		/***************/
		/** 基本信息 **/
		/***************/
		this.setJb_xm(data[2]);
		this.setJb_mz(data[6]);
		this.setJb_sfz(data[3]);
		this.setJb_csrq(FormCheck.getBirthday(data[3]));
		this.setJb_xb(FormCheck.getSex(data[3]));
		this.setJb_hjdz(data[4]);
		this.setJb_hjxxdz(data[12]);
		this.setJb_ssxq(data[43]);
		this.setJb_sjycd(data[8]);
		this.setJb_zzmm(data[9]);
		this.setJb_jzzj(data[23]);
		this.setJb_hjlb(data[11]);
		this.setJb_myjzz(data[25]);
		this.setJb_csd(data[45]);
		this.setJb_hyzk(data[10]);
		this.setJb_hyzm(data[24]);
		this.setJb_lxdh(data[13]);
		this.setJb_isScanned("");
		this.setJb_photoPath("");
		/***************/
		/** 家庭户信息 **/
		/***************/
		this.setJt_sfjth(data[15]);
		this.setJt_sfhz(data[16]);
		this.setJt_wlrk(data[70]);
		this.setJt_16yx(data[71]);
		this.setJt_16nan(data[72]);
		this.setJt_16nv(data[73]);
		this.setJt_yhzgx(data[18]);
		this.setJt_yhzqagx(data[83]);
		this.setJt_hzdjbh(data[19]);
		this.setJt_hid(data[17]);
		/*************/
		/** 居住信息 **/
		/*************/
		this.setJz_lkyjrq(FormCheck.getDate1(data[20]));
		this.setJz_ljrq(FormCheck.getDate1(data[22]));
		this.setJz_lxzdrq(FormCheck.getDate1(data[49]));
		this.setJz_ljyy(data[21]);
		this.setJz_ljqtyy(data[41]);
		this.setJz_qjzdz("");
		this.setJz_jzlx(data[46]);
		this.setJz_fwdjbh(data[48]);
		this.setJz_xzdz(data[51]);
		this.setJz_fwqtjzlx(data[84]);
		this.setJz_fwcxjg(new String[] { data[47], "", data[48], data[51] });
		/*************/
		/** 就业信息 **/
		/*************/
		this.setJy_mqzk(data[27]);
		this.setJy_jydwmc(data[55]);
		this.setJy_dwdjbh(data[57]);
		this.setJy_dwxxdz(data[67]);
		this.setJy_dwsshy(data[59]);
		this.setJy_csgz(data[60]);
		this.setJy_dwqtsshy(data[68]);
		this.setJy_qtcsgz(data[44]);
		this.setJy_zy(data[61]);
		this.setJy_qdldht(data[62]);
		this.setJy_dwdz(data[58]);
		this.setJy_zjcjsb(data[64]);
		this.setJy_cjsb(data[65]);
		this.setJy_xxmc(data[39]);
		this.setJy_xxszd(data[40]);
		this.setJy_xxxx(data[42]);
		/*************/
		/** 就业信息 **/
		/*************/
		this.setBz(data[86]);
	}

	public String[] getFwxx() {
		return fwxx;
	}

	public void setFwxx(String[] fwxx) {
		this.fwxx = fwxx;
	}

	public String getSspcs() {
		return sspcs;
	}

	public void setSspcs(String sspcs) {
		this.sspcs = sspcs;
	}

	public String getGlyxm() {
		return glyxm;
	}

	public void setGlyxm(String glyxm) {
		this.glyxm = glyxm;
	}
	
	public String getTbr() {
		return tbr;
	}

	public void setTbr(String tbr) {
		this.tbr = tbr;
	}

	public String getGlybm() {
		return glybm;
	}

	public void setGlybm(String glybm) {
		this.glybm = glybm;
	}


	/**
	 * @return the bz
	 */
	public String getBz() {
		return bz;
	}

	/**
	 * @param bz
	 *            the bz to set
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}

	/**
	 * @return the jb_xm
	 */
	public String getJb_xm() {
		return jb_xm;
	}

	/**
	 * @param jb_xm
	 *            the jb_xm to set
	 */
	public void setJb_xm(String jb_xm) {
		this.jb_xm = jb_xm;
	}

	/**
	 * @return the jb_mz
	 */
	public String getJb_mz() {
		return jb_mz;
	}

	/**
	 * @param jb_mz
	 *            the jb_mz to set
	 */
	public void setJb_mz(String jb_mz) {
		this.jb_mz = jb_mz;
	}

	/**
	 * @return the jb_sfz
	 */
	public String getJb_sfz() {
		return jb_sfz;
	}

	/**
	 * @param jb_sfz
	 *            the jb_sfz to set
	 */
	public void setJb_sfz(String jb_sfz) {
		this.jb_sfz = jb_sfz;
	}

	/**
	 * @return the jb_csrq
	 */
	public String getJb_csrq() {
		return jb_csrq;
	}

	/**
	 * @param jb_csrq
	 *            the jb_csrq to set
	 */
	public void setJb_csrq(String jb_csrq) {
		this.jb_csrq = jb_csrq;
	}

	/**
	 * @return the jb_xb
	 */
	public String getJb_xb() {
		return jb_xb;
	}

	/**
	 * @param jb_xb
	 *            the jb_xb to set
	 */
	public void setJb_xb(String jb_xb) {
		this.jb_xb = jb_xb;
	}

	/**
	 * @return the jb_hjdz
	 */
	public String getJb_hjdz() {
		return jb_hjdz;
	}

	/**
	 * @param jb_hjdz
	 *            the jb_hjdz to set
	 */
	public void setJb_hjdz(String jb_hjdz) {
		this.jb_hjdz = jb_hjdz;
	}

	/**
	 * @return the jb_hjxxdz
	 */
	public String getJb_hjxxdz() {
		return jb_hjxxdz;
	}

	/**
	 * @param jb_hjxxdz
	 *            the jb_hjxxdz to set
	 */
	public void setJb_hjxxdz(String jb_hjxxdz) {
		this.jb_hjxxdz = jb_hjxxdz;
	}

	/**
	 * @return the jb_ssxq
	 */
	public String getJb_ssxq() {
		return jb_ssxq;
	}

	/**
	 * @param jb_ssxq
	 *            the jb_ssxq to set
	 */
	public void setJb_ssxq(String jb_ssxq) {
		this.jb_ssxq = jb_ssxq;
	}

	/**
	 * @return the jb_sjycd
	 */
	public String getJb_sjycd() {
		return jb_sjycd;
	}

	/**
	 * @param jb_sjycd
	 *            the jb_sjycd to set
	 */
	public void setJb_sjycd(String jb_sjycd) {
		this.jb_sjycd = jb_sjycd;
	}

	/**
	 * @return the jb_zzmm
	 */
	public String getJb_zzmm() {
		return jb_zzmm;
	}

	/**
	 * @param jb_zzmm
	 *            the jb_zzmm to set
	 */
	public void setJb_zzmm(String jb_zzmm) {
		this.jb_zzmm = jb_zzmm;
	}

	/**
	 * @return the jb_jzzj
	 */
	public String getJb_jzzj() {
		return jb_jzzj;
	}

	/**
	 * @param jb_jzzj
	 *            the jb_jzzj to set
	 */
	public void setJb_jzzj(String jb_jzzj) {
		this.jb_jzzj = jb_jzzj;
	}

	/**
	 * @return the jb_hjlb
	 */
	public String getJb_hjlb() {
		return jb_hjlb;
	}

	/**
	 * @param jb_hjlb
	 *            the jb_hjlb to set
	 */
	public void setJb_hjlb(String jb_hjlb) {
		this.jb_hjlb = jb_hjlb;
	}

	/**
	 * @return the jb_myjzz
	 */
	public String getJb_myjzz() {
		return jb_myjzz;
	}

	/**
	 * @param jb_myjzz
	 *            the jb_myjzz to set
	 */
	public void setJb_myjzz(String jb_myjzz) {
		this.jb_myjzz = jb_myjzz;
	}

	/**
	 * @return the jb_csd
	 */
	public String getJb_csd() {
		return jb_csd;
	}

	/**
	 * @return the jz_qjzdz
	 */
	public String getJz_qjzdz() {
		return jz_qjzdz;
	}

	/**
	 * @param jz_qjzdz
	 *            the jz_qjzdz to set
	 */
	public void setJz_qjzdz(String jz_qjzdz) {
		this.jz_qjzdz = jz_qjzdz;
	}

	/**
	 * @param jb_csd
	 *            the jb_csd to set
	 */
	public void setJb_csd(String jb_csd) {
		this.jb_csd = jb_csd;
	}

	/**
	 * @return the jb_hyzk
	 */
	public String getJb_hyzk() {
		return jb_hyzk;
	}

	/**
	 * @param jb_hyzk
	 *            the jb_hyzk to set
	 */
	public void setJb_hyzk(String jb_hyzk) {
		this.jb_hyzk = jb_hyzk;
	}

	/**
	 * @return the jb_hyzm
	 */
	public String getJb_hyzm() {
		return jb_hyzm;
	}

	/**
	 * @param jb_hyzm
	 *            the jb_hyzm to set
	 */
	public void setJb_hyzm(String jb_hyzm) {
		this.jb_hyzm = jb_hyzm;
	}

	/**
	 * @return the jb_lxdh
	 */
	public String getJb_lxdh() {
		return jb_lxdh;
	}

	/**
	 * @param jb_lxdh
	 *            the jb_lxdh to set
	 */
	public void setJb_lxdh(String jb_lxdh) {
		this.jb_lxdh = jb_lxdh;
	}

	/**
	 * @return the jb_isScanned
	 */
	public String getJb_isScanned() {
		return jb_isScanned;
	}

	/**
	 * @param jb_isScanned
	 *            the jb_isScanned to set
	 */
	public void setJb_isScanned(String jb_isScanned) {
		this.jb_isScanned = jb_isScanned;
	}

	/**
	 * @return the jb_photoPath
	 */
	public String getJb_photoPath() {
		return jb_photoPath;
	}

	/**
	 * @param jb_photoPath
	 *            the jb_photoPath to set
	 */
	public void setJb_photoPath(String jb_photoPath) {
		this.jb_photoPath = jb_photoPath;
	}

	/**
	 * @return the jt_sfjth
	 */
	public String getJt_sfjth() {
		return jt_sfjth;
	}

	/**
	 * @param jt_sfjth
	 *            the jt_sfjth to set
	 */
	public void setJt_sfjth(String jt_sfjth) {
		this.jt_sfjth = jt_sfjth;
	}

	/**
	 * @return the jt_sfhz
	 */
	public String getJt_sfhz() {
		return jt_sfhz;
	}

	/**
	 * @param jt_sfhz
	 *            the jt_sfhz to set
	 */
	public void setJt_sfhz(String jt_sfhz) {
		this.jt_sfhz = jt_sfhz;
	}

	/**
	 * @return the jt_wlrk
	 */
	public String getJt_wlrk() {
		return jt_wlrk;
	}

	/**
	 * @param jt_wlrk
	 *            the jt_wlrk to set
	 */
	public void setJt_wlrk(String jt_wlrk) {
		this.jt_wlrk = jt_wlrk;
	}

	/**
	 * @return the jt_16yx
	 */
	public String getJt_16yx() {
		return jt_16yx;
	}

	/**
	 * @param jt_16yx
	 *            the jt_16yx to set
	 */
	public void setJt_16yx(String jt_16yx) {
		this.jt_16yx = jt_16yx;
	}

	/**
	 * @return the jt_16nan
	 */
	public String getJt_16nan() {
		return jt_16nan;
	}

	/**
	 * @param jt_16nan
	 *            the jt_16nan to set
	 */
	public void setJt_16nan(String jt_16nan) {
		this.jt_16nan = jt_16nan;
	}

	/**
	 * @return the jt_16nv
	 */
	public String getJt_16nv() {
		return jt_16nv;
	}

	/**
	 * @param jt_16nv
	 *            the jt_16nv to set
	 */
	public void setJt_16nv(String jt_16nv) {
		this.jt_16nv = jt_16nv;
	}

	/**
	 * @return the jt_yhzgx
	 */
	public String getJt_yhzgx() {
		return jt_yhzgx;
	}

	/**
	 * @param jt_yhzgx
	 *            the jt_yhzgx to set
	 */
	public void setJt_yhzgx(String jt_yhzgx) {
		this.jt_yhzgx = jt_yhzgx;
	}

	/**
	 * @return the jt_yhzqagx
	 */
	public String getJt_yhzqagx() {
		return jt_yhzqagx;
	}

	/**
	 * @param jt_yhzqagx
	 *            the jt_yhzqagx to set
	 */
	public void setJt_yhzqagx(String jt_yhzqagx) {
		this.jt_yhzqagx = jt_yhzqagx;
	}

	/**
	 * @return the jt_hzdjbh
	 */
	public String getJt_hzdjbh() {
		return jt_hzdjbh;
	}

	/**
	 * @param jt_hzdjbh
	 *            the jt_hzdjbh to set
	 */
	public void setJt_hzdjbh(String jt_hzdjbh) {
		this.jt_hzdjbh = jt_hzdjbh;
	}

	/**
	 * @return the djrq
	 */
	public String getDjrq() {
		return djrq;
	}

	/**
	 * @param djrq
	 *            the djrq to set
	 */
	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	/**
	 * @return the jt_hzcxjg
	 */
	public String getJt_hid() {
		return jt_hid;
	}

	/**
	 * @param jt_hzcxjg
	 *            the jt_hzcxjg to set
	 */
	public void setJt_hid(String jt_hzcxjg) {
		this.jt_hid = jt_hzcxjg;
	}

	/**
	 * @return the jz_lkyjrq
	 */
	public String getJz_lkyjrq() {
		return jz_lkyjrq;
	}

	/**
	 * @param jz_lkyjrq
	 *            the jz_lkyjrq to set
	 */
	public void setJz_lkyjrq(String jz_lkyjrq) {
		this.jz_lkyjrq = jz_lkyjrq;
	}

	/**
	 * @return the jz_ljrq
	 */
	public String getJz_ljrq() {
		return jz_ljrq;
	}

	/**
	 * @param jz_ljrq
	 *            the jz_ljrq to set
	 */
	public void setJz_ljrq(String jz_ljrq) {
		this.jz_ljrq = jz_ljrq;
	}

	/**
	 * @return the jz_lxzdrq
	 */
	public String getJz_lxzdrq() {
		return jz_lxzdrq;
	}

	/**
	 * @param jz_lxzdrq
	 *            the jz_lxzdrq to set
	 */
	public void setJz_lxzdrq(String jz_lxzdrq) {
		this.jz_lxzdrq = jz_lxzdrq;
	}

	/**
	 * @return the jz_ljyy
	 */
	public String getJz_ljyy() {
		return jz_ljyy;
	}

	/**
	 * @param jz_ljyy
	 *            the jz_ljyy to set
	 */
	public void setJz_ljyy(String jz_ljyy) {
		this.jz_ljyy = jz_ljyy;
	}

	/**
	 * @return the jz_ljqtyy
	 */
	public String getJz_ljqtyy() {
		return jz_ljqtyy;
	}

	/**
	 * @return the jz_fzxm
	 */
	public String getJz_fzxm() {
		return jz_fzxm;
	}

	/**
	 * @param jz_fzxm
	 *            the jz_fzxm to set
	 */
	public void setJz_fzxm(String jz_fzxm) {
		this.jz_fzxm = jz_fzxm;
	}

	public String getJz_fzsfzh() {
		return jz_fzsfzh;
	}

	public void setJz_fzsfzh(String jz_fzsfzh) {
		this.jz_fzsfzh = jz_fzsfzh;
	}

	/**
	 * @param jz_ljqtyy
	 *            the jz_ljqtyy to set
	 */
	public void setJz_ljqtyy(String jz_ljqtyy) {
		this.jz_ljqtyy = jz_ljqtyy;
	}

	/**
	 * @return the jz_jzlx
	 */
	public String getJz_jzlx() {
		return jz_jzlx;
	}

	/**
	 * @param jz_jzlx
	 *            the jz_jzlx to set
	 */
	public void setJz_jzlx(String jz_jzlx) {
		this.jz_jzlx = jz_jzlx;
	}

	/**
	 * @return the jz_fwdjbh
	 */
	public String getJz_fwdjbh() {
		return jz_fwdjbh;
	}

	/**
	 * @param jz_fwdjbh
	 *            the jz_fwdjbh to set
	 */
	public void setJz_fwdjbh(String jz_fwdjbh) {
		this.jz_fwdjbh = jz_fwdjbh;
	}

	/**
	 * @return the jz_xzdz
	 */
	public String getJz_xzdz() {
		return jz_xzdz;
	}

	/**
	 * @param jz_xzdz
	 *            the jz_xzdz to set
	 */
	public void setJz_xzdz(String jz_xzdz) {
		this.jz_xzdz = jz_xzdz;
	}

	/**
	 * @return the jz_fwcxjg
	 */
	public String[] getJz_fwcxjg() {
		return jz_fwcxjg;
	}

	/**
	 * @param jz_fwcxjg
	 *            the jz_fwcxjg to set
	 */
	public void setJz_fwcxjg(String[] jz_fwcxjg) {
		this.jz_fwcxjg = jz_fwcxjg;
	}

	/**
	 * @return the jz_fwqtjzlx
	 */
	public String getJz_fwqtjzlx() {
		return jz_fwqtjzlx;
	}

	/**
	 * @param jz_fwqtjzlx
	 *            the jz_fwqtjzlx to set
	 */
	public void setJz_fwqtjzlx(String jz_fwqtjzlx) {
		this.jz_fwqtjzlx = jz_fwqtjzlx;
	}

	/**
	 * @return the jy_mqzk
	 */
	public String getJy_mqzk() {
		return jy_mqzk;
	}

	/**
	 * @param jy_mqzk
	 *            the jy_mqzk to set
	 */
	public void setJy_mqzk(String jy_mqzk) {
		this.jy_mqzk = jy_mqzk;
	}

	/**
	 * @return the jy_jydwmc
	 */
	public String getJy_jydwmc() {
		return jy_jydwmc;
	}

	/**
	 * @param jy_jydwmc
	 *            the jy_jydwmc to set
	 */
	public void setJy_jydwmc(String jy_jydwmc) {
		this.jy_jydwmc = jy_jydwmc;
	}

	/**
	 * @return the jy_dwdjbh
	 */
	public String getJy_dwdjbh() {
		return jy_dwdjbh;
	}

	/**
	 * @param jy_dwdjbh
	 *            the jy_dwdjbh to set
	 */
	public void setJy_dwdjbh(String jy_dwdjbh) {
		this.jy_dwdjbh = jy_dwdjbh;
	}

	/**
	 * @return the jy_dwxxdz
	 */
	public String getJy_dwxxdz() {
		return jy_dwxxdz;
	}

	/**
	 * @param jy_dwxxdz
	 *            the jy_dwxxdz to set
	 */
	public void setJy_dwxxdz(String jy_dwxxdz) {
		this.jy_dwxxdz = jy_dwxxdz;
	}

	/**
	 * @return the jy_dwsshy
	 */
	public String getJy_dwsshy() {
		return jy_dwsshy;
	}

	/**
	 * @param jy_dwsshy
	 *            the jy_dwsshy to set
	 */
	public void setJy_dwsshy(String jy_dwsshy) {
		this.jy_dwsshy = jy_dwsshy;
	}

	/**
	 * @return the jy_dwqtsshy
	 */
	public String getJy_dwqtsshy() {
		return jy_dwqtsshy;
	}

	/**
	 * @param jy_dwqtsshy
	 *            the jy_dwqtsshy to set
	 */
	public void setJy_dwqtsshy(String jy_dwqtsshy) {
		this.jy_dwqtsshy = jy_dwqtsshy;
	}

	/**
	 * @return the rybh
	 */
	public String getRybh() {
		return rybh;
	}

	/**
	 * @param rybh
	 *            the rybh to set
	 */
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	/**
	 * @return the jy_csgz
	 */
	public String getJy_csgz() {
		return jy_csgz;
	}

	/**
	 * @param jy_csgz
	 *            the jy_csgz to set
	 */
	public void setJy_csgz(String jy_csgz) {
		this.jy_csgz = jy_csgz;
	}

	/**
	 * @return the jy_qtcsgz
	 */
	public String getJy_qtcsgz() {
		return jy_qtcsgz;
	}

	/**
	 * @param jy_qtcsgz
	 *            the jy_qtcsgz to set
	 */
	public void setJy_qtcsgz(String jy_qtcsgz) {
		this.jy_qtcsgz = jy_qtcsgz;
	}

	/**
	 * @return the jy_zy
	 */
	public String getJy_zy() {
		return jy_zy;
	}

	/**
	 * @param jy_zy
	 *            the jy_zy to set
	 */
	public void setJy_zy(String jy_zy) {
		this.jy_zy = jy_zy;
	}

	/**
	 * @return the jy_qdldht
	 */
	public String getJy_qdldht() {
		return jy_qdldht;
	}

	/**
	 * @param jy_qdldht
	 *            the jy_qdldht to set
	 */
	public void setJy_qdldht(String jy_qdldht) {
		this.jy_qdldht = jy_qdldht;
	}

	/**
	 * @return the jy_dwdz
	 */
	public String getJy_dwdz() {
		return jy_dwdz;
	}

	/**
	 * @param jy_dwdz
	 *            the jy_dwdz to set
	 */
	public void setJy_dwdz(String jy_dwdz) {
		this.jy_dwdz = jy_dwdz;
	}

	/**
	 * @return the jy_zjcjsb
	 */
	public String getJy_zjcjsb() {
		return jy_zjcjsb;
	}

	/**
	 * @param jy_zjcjsb
	 *            the jy_zjcjsb to set
	 */
	public void setJy_zjcjsb(String jy_zjcjsb) {
		this.jy_zjcjsb = jy_zjcjsb;
	}

	/**
	 * @return the jy_cjsb
	 */
	public String getJy_cjsb() {
		return jy_cjsb;
	}

	/**
	 * @param jy_cjsb
	 *            the jy_cjsb to set
	 */
	public void setJy_cjsb(String jy_cjsb) {
		this.jy_cjsb = jy_cjsb;
	}

	/**
	 * @return the jy_xxmc
	 */
	public String getJy_xxmc() {
		return jy_xxmc;
	}

	/**
	 * @param jy_xxmc
	 *            the jy_xxmc to set
	 */
	public void setJy_xxmc(String jy_xxmc) {
		this.jy_xxmc = jy_xxmc;
	}

	/**
	 * @return the jy_xxszd
	 */
	public String getJy_xxszd() {
		return jy_xxszd;
	}

	/**
	 * @param jy_xxszd
	 *            the jy_xxszd to set
	 */
	public void setJy_xxszd(String jy_xxszd) {
		this.jy_xxszd = jy_xxszd;
	}

	/**
	 * @return the jy_xxxx
	 */
	public String getJy_xxxx() {
		return jy_xxxx;
	}

	/**
	 * @param jy_xxxx
	 *            the jy_xxxx to set
	 */
	public void setJy_xxxx(String jy_xxxx) {
		this.jy_xxxx = jy_xxxx;
	}

	/**
	 * @return the tbrq
	 */
	public String getTbrq() {
		return tbrq;
	}

	/**
	 * @return the grdjbh
	 */
	public String getGrdjbh() {
		return grdjbh;
	}

	/**
	 * @param grdjbh
	 *            the grdjbh to set
	 */
	public void setGrdjbh(String grdjbh) {
		this.grdjbh = grdjbh;
	}

	/**
	 * @param tbrq
	 *            the tbrq to set
	 */
	public void setTbrq(String tbrq) {
		this.tbrq = tbrq;
	}

    public String getZmcl() {
        return zmcl;
    }

    public void setZmcl(String zmcl) {
        this.zmcl = zmcl;
    }
}
