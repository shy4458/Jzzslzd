package com.bksx.jzzslzd.bo;

public class DjkSl {
    private static final long serialVersionUID = 1L;
    /******************* 查询条件 开始 **************************************/
    private String lymk; // 来源模块 11 登记卡 21 居住证
    private int yylx; // 业务类型 0 受理 1签注 2 补办签注3地址变更 4 补领5 换领 6注销 7  打印取证凭证
    private int pageNum; // 页码
    //private String ssjb; //所属级别
    private String fwzbh; //服务站编号
    private String pcsbh; //派出所编号
    //private String pcsmc;//派出所名称
    private String xqbh;//辖区编号
    private String fwzmc; //服务站名称
/*    private String xzqhmc; //行政区划名称
    private String pcslxdz; //派出所联系地址
    private String pcsszlxdh; //派出所所长联系电话
    private String fjbh; //分局编号
    private String fjmc; //分局名称*/
    /*********************************流管用******************************************************/
    private String jzzmk;//请求流管时的业务模块 *01 受理  *02 地址变更 *03 签注  *04 补办签注 *05 户籍信息换领      1开头为登记卡 2开头为居住证
    /********************************************人员信息**********************************************************************************/

    //人员基本信息数据
    private String rdj_qtmz;
    private String rdj_grbh;     //个人编号
    private String bip_xm;     //姓名
    private String bip_sfzhm;     //身份证号码
    private String bip_yjxzqh;     //原籍行政区划
    private String bip_xb;     //性别
    private String bip_nation;     //民族
    private String bip_birthday;     //出生日期
    private String bip_educationallevel;     //文化程度
    private String bip_politicsaspect;     //政治面貌
    private String bip_marriage;     //婚姻状况
    private String bip_rpr_type;     //户籍类型
    private String bip_rpr_address;     //户籍地址
    private String bip_con_telephone;     //电话
    private String rdj_grdjdbh;     //个人登记表序号
    private String rdj_rylx;     //人员类型
    private String rld_sfjtslr;     //是否家庭式流入
    private String rdj_sfhz;     //是否户主
    private String rdj_hid;     //户id
    private String rdj_yhzgx;     //与户主关系
    private String rdj_hzdjbxh;     //户主登记表序号
    private String rdj_lkyjsj;     //离开原籍日期
    private String rdj_ljyy;     //来京原因
    private String rdj_ljrq;     //来京日期
    private String rdj_zzzjyw;     //暂住证件有无
    private String rdj_hyzmyw;     //婚育证明有无
    private String rdj_ywjzjl;     //有无接种记录
    private String rdj_ljzdyy;     //来居住地原因
    private String rdj_mqzk;     //目前状况
    private String rdj_csd; //出生地
    private String rdj_dqzt;     //当前状态
    private String rdj_ddzxsj;     //等待注销标识时间
    private String rdj_tbr;     //填表人
    private String rdj_xgybm;     //管理员编码
    private String rdj_tbrq;     //填表日期
    private String rdj_sfyx;     //是否有效
    private String rdj_djrq;     //登记日期
    private String rdj_djr;     //登记人
    private String rdj_djdw;     //登记单位
    private String rdj_sfzz;     //是否暂存
    private String rdj_bz;     //备注
    private String rld_qrdxzqh;     //迁入地行政区划
    private String rld_qcdxzqh;     //迁出地行政区划
    private String rld_dqzt;     //当前状态
    private String rld_qrqcbzxx;     //迁出备注消息
    private String rld_tbr;     //填表人
    private String rld_xgybm;     //管理员编码
    private String rld_tbrq;     //填表日期
    private String rld_djrq;     //登记日期
    private String rld_djr;     //登记人
    private String rld_djdw;     //登记单位
    private String rld_bz;     //备注
    private String rzf_zslx;     //住所类型
    private String rzf_fwbh;     //房屋编号
    private String rzf_fwdjdbh;     //房屋登记表序号
    private String rzf_lxzdrq;     //来现住地日期
    private String rzf_xzdxzqh;     //现住地行政区划(到居委会/村)
    private String rzf_xzdxxdz;     //现住地详细地址
    private String rzf_pcs;     //所属分局及派出所代码(名称)
    private String rzf_sssq;     //所属社区(公安标准)
    private String rzf_sqmjxm;     //社区民警姓名
    private String rzf_tbr;     //填表人
    private String rzf_xgybm;     //管理员编码
    private String rzf_tbrq;     //填表日期
    private String rzf_djrq;     //登记日期
    private String rzf_djr;     //登记人
    private String rzf_djdw;     //登记单位
    private String rzf_bz;     //备注
    //房主信息
    private String rzf_fzxm; //	房主姓名
    private String rzf_fzsfz; //	房主身份证
    private String rzf_fzxzd; //	房主现住地
    private String rzf_lxdh; //	房主联系电话
    private String rzf_fwszddz; //	房屋所在地地址

    private String rjy_jydwmc;     //就业单位名称
    private String rjy_dwbh;     //单位编号
    private String rjy_jydwdjdbh;     //就业单位登记表序号
    private String rjy_jydwszqx;     //就业单位所在地
    private String rjy_jydwhy;     //就业单位行业
    private String rjy_zycsgz;     //主要从事工作
    private String rjy_bczylb;     //本次职业类别(职业)
    private String rjy_sfqdldht;     //是否签订劳动合同
    private String rjy_sfsbx;     //是否上保险
    private String rjy_zjjnshbxlb;     //在京参加社会保险
    private String rjy_syjnshbxlb;     //参加社会保险
    private String rjy_tbr;     //填表人
    private String rjy_xgybm;     //管理员编码
    private String rjy_tbrq;     //填表日期
    private String rjy_djrq;     //登记日期
    private String rjy_djr;     //登记人
    private String rjy_djdw;     //登记单位
    private String rjy_bz;     //备注
    private String rdj_gzlx;     //关注类型
    private String rjy_tshy;     //就业特殊行业
    private String rjy_jydwxxdz;     //就业单位详细地址
    private String rjy_dwcjdjdbh;     //单位采集登记表序号
    private String rjx_jdxxmc;     //就读学校名称
    private String rjx_jdxxdz;     //就读学校地址
    private String rdj_yhzqtgx;     //与户主其他关系
    private String rdj_ljqtyy;     //来京其他原因
    private String rzf_jzqtlx;     //居住其他类型
    private String rjy_jydwqthy;     //就业单位其他行业
    private String rdj_mqzkqtxx;     //目前状况其他信息
    private String rdj_sfgzdcj;     //是否工作地采集
    private String rdj_sfjzdcj;     //是否居住地采集
    private String rdj_zycsqtgz;//主要从事其他工作描述
    private String rjy_jydwdjbxh;//单位登记表序号
    private String rzf_ssfjjpcsdm;
    private String fwzjbxxdjb_fwzbh;//服务站编号

    //户信息

    private String hxx_bhrks;
    private String hxx_slsyxrks;
    private String hxx_slsyxnrks;
    private String Hxx_slsyxnvrks;
    /************************代办人信息*****************************************/
    private String sfdb;//此次申领是否代办
    private String dbr_xm;//代办人姓名
    private String dbr_dh;//代办人电话
    private String dbr_sfzhm;//代办人身份证号码
    private String dbr_zjlx;//代办人证件类型
    /************************证明材料*****************************************/
    private String[] zmcl_wjmc;//文件名称
    private String[] zmcl_bclj;//保存路径
    private String zmcl_lx;//证明材料类型
    private String[] zmcl_sfky;//证明材料是否可疑
    private String czr;//操作人
    private String czdwid;//操作单位编号
    private String czdwjb;//操作单位级别
    private String czdwmc; //操作单位名称
    //办证人员信息
    private String bzryxx_xm;
    private String bzryxx_sfzhm;
    private String bzryxx_bh;  //          个人编号
    private String bzryxx_xb;  //		性别
    private String bzryxx_mz;  //		民族
    private String bzryxx_yjxzqh;  //	原籍行政区划
    private String bzryxx_hjdz;  //	户籍地址
    private String bzryxx_xzdxzqh;  //	现住地行政区划(到居委会/村)
    private String bzryxx_xzdxxdz;  //	现住地详细地址
    private String bzryxx_pcsbm;  //	受理派出所编码
    private String bzryxx_slsj;  //	受理时间
    private String bzryxx_sfyx;  //	是否有效
    private String bzryxx_wxyf;  //	居住无效月份
    private String bzryxx_xgsj;  //	最后一次修改时间
    private String zzzsfyx;//暂住证是否有效
    private String bzryxx_fwzbh;//服务站编号
    private String bzryxx_jznxkssj;//居住年限开始时间
    private String bzryxx_csrq; //出生日期
    private String bzryxx_lxdh;//联系电话
    private String bzryxx_hjlx;//户籍类型
    private String bzryxx_scxzdz;//数采现住地址
    private String bzryxx_xxly; //办证人员信息来源

    //证卡信息
    private String returnStatus;
    private String returnMsg;
    private String returnSfcz;
    private String zkxx_bh; // 证卡编号
    private String zkxx_cjsj; // 创建时间
    private String zkxx_xgsj; // 最后一次修改时间
    private String zkxx_zkzt; // 证卡状态
    private String zkxx_zplj; // 证件照片路径
    private String zkxx_yxkssj; // 有效开始时间
    private String zkxx_yxjzsj; // 有效结束时间
    private String zkxx_jznxkssj; // 居住年限开始时间
    private int zkxx_wxjzy; // 无效月数
    private String zkxx_kdf; // 密钥
    private String zkxx_ykth; // 一卡通号
    private String zkxx_zxyy; // 注销原因
    private String zkxx_sfyx;
    private String zkxx_rybh;
    private String zkxx_lx;
    private String zkxx_qfdwlx;
    private String flag;// 此人在流管是否存在   3存在
    private String zkxx_sfxysh;//本次业务申请类型
    private String zkxx_wtgyy;//审核未通过原因
    private String zkxx_sqlx;//申领类型
    private String zkxx_sldw;//受理单位编码
    private String czrid;//操作人
    private String czdwbm;//操作单位编码
    /****************上传照片信息***************/
    private String sczpmc; //上传照片名称
    private String sczplx;//上传证明材料类型
    private String sczpstr;//上传证明材料类型
    private String sfsysfzzp; //是否使用身份证照片
    private String qfdwmc;//签发单位名称

    //预约受理
    private String yysj; //预约时间
    private String yysjlx; //预约时间类型
    private String yybh; //预约编号

    /*********************自主登记照片****************************/
    private String zzdjzp_bh; //照片编号
    private String zzdjzp_grbh; //个人编号
    private String zzdjzp_zplj; //照片路径
    private String zzdjzp_sjdzfc; //实际的字符串
    private String zzdjzp_yshzfc; //压缩后的字符串
    private String zzdjzp_yshszcd; //压缩后的数组长度
    private String zzdjzp_cjsj; //创建时间
    private String zzdjzp_xgsj; //修改时间
    private String zzdjzp_sfyx; //是否有效
    private String zzdjzp_zjbh; //证件编号


    /////////////////新加字段/////////////////
    private String zkbh;
    private String zkzt;
    private String yxgsj;
    private String kdf;
    private String zmcl;//证明材料


    private String zmcl_suffix;//证明材料后缀名

    private String rzf_sspcsdm;//所属派出所
    private String rzf_sspcsmc;//所属派出所名称
    private String rzf_fwzbh;//服务站编号
    private String rzf_ssxqbh;//所属辖区编号

    private String xm;//姓名
    private String sfzhm;//身份证号码
    private String ssjb; //发送信息所属级别
    private String pcsmc; //派出所名称
    private String pcsszlxdh;//派出所所长联系电话
    private String pcslxdz;//派出所联系地址
    private String fjmc;//分局名称
    private String fjbh;//分局编号
    private String fjldlxdh;//分局领导联系电话
    private String sjldlxdh;//市局领导联系电话
    private String dgdwmc;//对公单位名称
    private String dgdwbh;//对公单位bh
    private String dwsspcsdm;//单位所属派出所代码

    /**************短信信息**************/
    private String fxrid; //发现人id
    private String fxrxm; //发现人姓名
    private String fxdw; //发现单位编码
    private String fxdwlx; //发现单位类型
    private String fxpcsbm; //发现派出所编码

    public String getFxrid() {
        return fxrid;
    }

    public void setFxrid(String fxrid) {
        this.fxrid = fxrid;
    }

    public String getFxrxm() {
        return fxrxm;
    }

    public void setFxrxm(String fxrxm) {
        this.fxrxm = fxrxm;
    }

    public String getFxdw() {
        return fxdw;
    }

    public void setFxdw(String fxdw) {
        this.fxdw = fxdw;
    }

    public String getFxdwlx() {
        return fxdwlx;
    }

    public void setFxdwlx(String fxdwlx) {
        this.fxdwlx = fxdwlx;
    }

    public String getFxpcsbm() {
        return fxpcsbm;
    }

    public void setFxpcsbm(String fxpcsbm) {
        this.fxpcsbm = fxpcsbm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public String getPcsszlxdh() {
        return pcsszlxdh;
    }

    public void setPcsszlxdh(String pcsszlxdh) {
        this.pcsszlxdh = pcsszlxdh;
    }

    public String getPcslxdz() {
        return pcslxdz;
    }

    public void setPcslxdz(String pcslxdz) {
        this.pcslxdz = pcslxdz;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjbh() {
        return fjbh;
    }

    public void setFjbh(String fjbh) {
        this.fjbh = fjbh;
    }

    public String getFjldlxdh() {
        return fjldlxdh;
    }

    public void setFjldlxdh(String fjldlxdh) {
        this.fjldlxdh = fjldlxdh;
    }

    public String getSjldlxdh() {
        return sjldlxdh;
    }

    public void setSjldlxdh(String sjldlxdh) {
        this.sjldlxdh = sjldlxdh;
    }

    public String getDgdwmc() {
        return dgdwmc;
    }

    public void setDgdwmc(String dgdwmc) {
        this.dgdwmc = dgdwmc;
    }

    public String getDgdwbh() {
        return dgdwbh;
    }

    public void setDgdwbh(String dgdwbh) {
        this.dgdwbh = dgdwbh;
    }

    public String getDwsspcsdm() {
        return dwsspcsdm;
    }

    public void setDwsspcsdm(String dwsspcsdm) {
        this.dwsspcsdm = dwsspcsdm;
    }

    public String getRzf_sspcsmc() {
        return rzf_sspcsmc;
    }

    public void setRzf_sspcsmc(String rzf_sspcsmc) {
        this.rzf_sspcsmc = rzf_sspcsmc;
    }

    public String getRzf_sspcsdm() {
        return rzf_sspcsdm;
    }

    public void setRzf_sspcsdm(String rzf_sspcsdm) {
        this.rzf_sspcsdm = rzf_sspcsdm;
    }

    public String getRzf_fwzbh() {
        return rzf_fwzbh;
    }

    public void setRzf_fwzbh(String rzf_fwzbh) {
        this.rzf_fwzbh = rzf_fwzbh;
    }

    public String getRzf_ssxqbh() {
        return rzf_ssxqbh;
    }

    public void setRzf_ssxqbh(String rzf_ssxqbh) {
        this.rzf_ssxqbh = rzf_ssxqbh;
    }

    public String getZmcl() {
        return zmcl;
    }

    public void setZmcl(String zmcl) {
        this.zmcl = zmcl;
    }
    public String getZmcl_suffix() {
        return zmcl_suffix;
    }

    public void setZmcl_suffix(String zmcl_suffix) {
        this.zmcl_suffix = zmcl_suffix;
    }


    /**
     * @return the kdf
     */
    public String getKdf() {
        return kdf;
    }

    /**
     * @param kdf the kdf to set
     */
    public void setKdf(String kdf) {
        this.kdf = kdf;
    }

    /**
     * @return the yxgsj
     */
    public String getYxgsj() {
        return yxgsj;
    }

    /**
     * @param yxgsj the yxgsj to set
     */
    public void setYxgsj(String yxgsj) {
        this.yxgsj = yxgsj;
    }

    /**
     * @return the zkbh
     */
    public String getZkbh() {
        return zkbh;
    }

    /**
     * @param zkbh the zkbh to set
     */
    public void setZkbh(String zkbh) {
        this.zkbh = zkbh;
    }

    /**
     * @return the zkzt
     */
    public String getZkzt() {
        return zkzt;
    }

    /**
     * @param zkzt the zkzt to set
     */
    public void setZkzt(String zkzt) {
        this.zkzt = zkzt;
    }

    /**
     * @return the fwzmc
     */
    public String getFwzmc() {
        return fwzmc;
    }

    /**
     * @param fwzmc the fwzmc to set
     */
    public void setFwzmc(String fwzmc) {
        this.fwzmc = fwzmc;
    }

    /**
     * @return the czdwmc
     */
    public String getCzdwmc() {
        return czdwmc;
    }

    /**
     * @param czdwmc the czdwmc to set
     */
    public void setCzdwmc(String czdwmc) {
        this.czdwmc = czdwmc;
    }

    /**
     * @return the bzryxx_xxly
     */
    public String getBzryxx_xxly() {
        return bzryxx_xxly;
    }

    /**
     * @param bzryxxXxly the bzryxx_xxly to set
     */
    public void setBzryxx_xxly(String bzryxxXxly) {
        bzryxx_xxly = bzryxxXxly;
    }

    /**
     * @return the zzdjzp_bh
     */
    public String getZzdjzp_bh() {
        return zzdjzp_bh;
    }

    /**
     * @param zzdjzpBh the zzdjzp_bh to set
     */
    public void setZzdjzp_bh(String zzdjzpBh) {
        zzdjzp_bh = zzdjzpBh;
    }

    /**
     * @return the zzdjzp_grbh
     */
    public String getZzdjzp_grbh() {
        return zzdjzp_grbh;
    }

    /**
     * @param zzdjzpGrbh the zzdjzp_grbh to set
     */
    public void setZzdjzp_grbh(String zzdjzpGrbh) {
        zzdjzp_grbh = zzdjzpGrbh;
    }

    /**
     * @return the zzdjzp_zplj
     */
    public String getZzdjzp_zplj() {
        return zzdjzp_zplj;
    }

    /**
     * @param zzdjzpZplj the zzdjzp_zplj to set
     */
    public void setZzdjzp_zplj(String zzdjzpZplj) {
        zzdjzp_zplj = zzdjzpZplj;
    }

    /**
     * @return the zzdjzp_sjdzfc
     */
    public String getZzdjzp_sjdzfc() {
        return zzdjzp_sjdzfc;
    }

    /**
     * @param zzdjzpSjdzfc the zzdjzp_sjdzfc to set
     */
    public void setZzdjzp_sjdzfc(String zzdjzpSjdzfc) {
        zzdjzp_sjdzfc = zzdjzpSjdzfc;
    }

    /**
     * @return the zzdjzp_yshzfc
     */
    public String getZzdjzp_yshzfc() {
        return zzdjzp_yshzfc;
    }

    /**
     * @param zzdjzpYshzfc the zzdjzp_yshzfc to set
     */
    public void setZzdjzp_yshzfc(String zzdjzpYshzfc) {
        zzdjzp_yshzfc = zzdjzpYshzfc;
    }

    /**
     * @return the zzdjzp_yshszcd
     */
    public String getZzdjzp_yshszcd() {
        return zzdjzp_yshszcd;
    }

    /**
     * @param zzdjzpYshszcd the zzdjzp_yshszcd to set
     */
    public void setZzdjzp_yshszcd(String zzdjzpYshszcd) {
        zzdjzp_yshszcd = zzdjzpYshszcd;
    }

    /**
     * @return the zzdjzp_cjsj
     */
    public String getZzdjzp_cjsj() {
        return zzdjzp_cjsj;
    }

    /**
     * @param zzdjzpCjsj the zzdjzp_cjsj to set
     */
    public void setZzdjzp_cjsj(String zzdjzpCjsj) {
        zzdjzp_cjsj = zzdjzpCjsj;
    }

    /**
     * @return the zzdjzp_xgsj
     */
    public String getZzdjzp_xgsj() {
        return zzdjzp_xgsj;
    }

    /**
     * @param zzdjzpXgsj the zzdjzp_xgsj to set
     */
    public void setZzdjzp_xgsj(String zzdjzpXgsj) {
        zzdjzp_xgsj = zzdjzpXgsj;
    }

    /**
     * @return the zzdjzp_sfyx
     */
    public String getZzdjzp_sfyx() {
        return zzdjzp_sfyx;
    }

    /**
     * @param zzdjzpSfyx the zzdjzp_sfyx to set
     */
    public void setZzdjzp_sfyx(String zzdjzpSfyx) {
        zzdjzp_sfyx = zzdjzpSfyx;
    }

    /**
     * @return the zzdjzp_zjbh
     */
    public String getZzdjzp_zjbh() {
        return zzdjzp_zjbh;
    }

    /**
     * @param zzdjzpZjbh the zzdjzp_zjbh to set
     */
    public void setZzdjzp_zjbh(String zzdjzpZjbh) {
        zzdjzp_zjbh = zzdjzpZjbh;
    }

    /**
     * @return the sfsysfzzp
     */
    public String getSfsysfzzp() {
        return sfsysfzzp;
    }

    /**
     * @param sfsysfzzp the sfsysfzzp to set
     */
    public void setSfsysfzzp(String sfsysfzzp) {
        this.sfsysfzzp = sfsysfzzp;
    }

    /**
     * @return the rzf_jzqtlx
     */
    public String getRzf_jzqtlx() {
        return rzf_jzqtlx;
    }

    /**
     * @param rzfJzqtlx the rzf_jzqtlx to set
     */
    public void setRzf_jzqtlx(String rzfJzqtlx) {
        rzf_jzqtlx = rzfJzqtlx;
    }

    /**
     * @return the returnSfcz
     */
    public String getReturnSfcz() {
        return returnSfcz;
    }

    /**
     * @param returnSfcz the returnSfcz to set
     */
    public void setReturnSfcz(String returnSfcz) {
        this.returnSfcz = returnSfcz;
    }

    /**
     * @return the yybh
     */
    public String getYybh() {
        return yybh;
    }

    /**
     * @param yybh the yybh to set
     */
    public void setYybh(String yybh) {
        this.yybh = yybh;
    }

    /**
     * @return the yysj
     */
    public String getYysj() {
        return yysj;
    }

    /**
     * @param yysj the yysj to set
     */
    public void setYysj(String yysj) {
        this.yysj = yysj;
    }

    /**
     * @return the yysjlx
     */
    public String getYysjlx() {
        return yysjlx;
    }

    /**
     * @param yysjlx the yysjlx to set
     */
    public void setYysjlx(String yysjlx) {
        this.yysjlx = yysjlx;
    }

    /**
     * @return the fwzbh
     */
    public String getFwzbh() {
        return fwzbh;
    }

    /**
     * @param fwzbh the fwzbh to set
     */
    public void setFwzbh(String fwzbh) {
        this.fwzbh = fwzbh;
    }

    /**
     * @return the pcsbh
     */
    public String getPcsbh() {
        return pcsbh;
    }

    /**
     * @param pcsbh the pcsbh to set
     */
    public void setPcsbh(String pcsbh) {
        this.pcsbh = pcsbh;
    }

    /**
     * @return the ssjb
     */
    public String getSsjb() {
        return ssjb;
    }

    /**
     * @param ssjb the ssjb to set
     */
    public void setSsjb(String ssjb) {
        this.ssjb = ssjb;
    }

    public String getLymk() {
        return lymk;
    }

    public void setLymk(String lymk) {
        this.lymk = lymk;
    }

    public int getYylx() {
        return yylx;
    }

    public void setYylx(int yylx) {
        this.yylx = yylx;
    }

    public String getJzzmk() {
        return jzzmk;
    }

    public void setJzzmk(String jzzmk) {
        this.jzzmk = jzzmk;
    }


    public String getSfdb() {
        return sfdb;
    }

    public void setSfdb(String sfdb) {
        this.sfdb = sfdb;
    }

    public String getDbr_xm() {
        return dbr_xm;
    }

    public void setDbr_xm(String dbrXm) {
        dbr_xm = dbrXm;
    }

    public String getDbr_dh() {
        return dbr_dh;
    }

    public void setDbr_dh(String dbrDh) {
        dbr_dh = dbrDh;
    }

    public String getDbr_sfzhm() {
        return dbr_sfzhm;
    }

    public void setDbr_sfzhm(String dbrSfzhm) {
        dbr_sfzhm = dbrSfzhm;
    }

    public String getDbr_zjlx() {
        return dbr_zjlx;
    }

    public void setDbr_zjlx(String dbrZjlx) {
        dbr_zjlx = dbrZjlx;
    }

    public String[] getZmcl_wjmc() {
        return zmcl_wjmc;
    }

    public void setZmcl_wjmc(String[] zmclWjmc) {
        zmcl_wjmc = zmclWjmc;
    }

    public String[] getZmcl_bclj() {
        return zmcl_bclj;
    }

    public void setZmcl_bclj(String[] zmclBclj) {
        zmcl_bclj = zmclBclj;
    }

    public String getZmcl_lx() {
        return zmcl_lx;
    }

    public void setZmcl_lx(String zmclLx) {
        zmcl_lx = zmclLx;
    }

    public String[] getZmcl_sfky() {
        return zmcl_sfky;
    }

    public void setZmcl_sfky(String[] zmclSfky) {
        zmcl_sfky = zmclSfky;
    }


    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }


    public String getCzdwid() {
        return czdwid;
    }

    public void setCzdwid(String czdwid) {
        this.czdwid = czdwid;
    }

    public String getCzdwjb() {
        return czdwjb;
    }

    public void setCzdwjb(String czdwjb) {
        this.czdwjb = czdwjb;
    }

    public String getRdj_qtmz() {
        return rdj_qtmz;
    }

    public void setRdj_qtmz(String rdjQtmz) {
        rdj_qtmz = rdjQtmz;
    }

    public String getRdj_grbh() {
        return rdj_grbh;
    }

    public void setRdj_grbh(String rdjGrbh) {
        rdj_grbh = rdjGrbh;
    }

    public String getBip_xm() {
        return bip_xm;
    }

    public void setBip_xm(String bipXm) {
        bip_xm = bipXm;
    }

    public String getBip_sfzhm() {
        return bip_sfzhm;
    }

    public void setBip_sfzhm(String bipSfzhm) {
        bip_sfzhm = bipSfzhm;
    }

    public String getBip_yjxzqh() {
        return bip_yjxzqh;
    }

    public void setBip_yjxzqh(String bipYjxzqh) {
        bip_yjxzqh = bipYjxzqh;
    }

    public String getBip_xb() {
        return bip_xb;
    }

    public void setBip_xb(String bipXb) {
        bip_xb = bipXb;
    }

    public String getBip_nation() {
        return bip_nation;
    }

    public void setBip_nation(String bipNation) {
        bip_nation = bipNation;
    }

    public String getBip_birthday() {
        return bip_birthday;
    }

    public void setBip_birthday(String bipBirthday) {
        bip_birthday = bipBirthday;
    }

    public String getBip_educationallevel() {
        return bip_educationallevel;
    }

    public void setBip_educationallevel(String bipEducationallevel) {
        bip_educationallevel = bipEducationallevel;
    }

    public String getBip_politicsaspect() {
        return bip_politicsaspect;
    }

    public void setBip_politicsaspect(String bipPoliticsaspect) {
        bip_politicsaspect = bipPoliticsaspect;
    }

    public String getBip_marriage() {
        return bip_marriage;
    }

    public void setBip_marriage(String bipMarriage) {
        bip_marriage = bipMarriage;
    }

    public String getBip_rpr_type() {
        return bip_rpr_type;
    }

    public void setBip_rpr_type(String bipRprType) {
        bip_rpr_type = bipRprType;
    }

    public String getBip_rpr_address() {
        return bip_rpr_address;
    }

    public void setBip_rpr_address(String bipRprAddress) {
        bip_rpr_address = bipRprAddress;
    }

    public String getBip_con_telephone() {
        return bip_con_telephone;
    }

    public void setBip_con_telephone(String bipConTelephone) {
        bip_con_telephone = bipConTelephone;
    }

    public String getRdj_grdjdbh() {
        return rdj_grdjdbh;
    }

    public void setRdj_grdjdbh(String rdjGrdjdbh) {
        rdj_grdjdbh = rdjGrdjdbh;
    }

    public String getRdj_rylx() {
        return rdj_rylx;
    }

    public void setRdj_rylx(String rdjRylx) {
        rdj_rylx = rdjRylx;
    }

    public String getRld_sfjtslr() {
        return rld_sfjtslr;
    }

    public void setRld_sfjtslr(String rldSfjtslr) {
        rld_sfjtslr = rldSfjtslr;
    }

    public String getRdj_sfhz() {
        return rdj_sfhz;
    }

    public void setRdj_sfhz(String rdjSfhz) {
        rdj_sfhz = rdjSfhz;
    }

    public String getRdj_hid() {
        return rdj_hid;
    }

    public void setRdj_hid(String rdjHid) {
        rdj_hid = rdjHid;
    }

    public String getRdj_yhzgx() {
        return rdj_yhzgx;
    }

    public void setRdj_yhzgx(String rdjYhzgx) {
        rdj_yhzgx = rdjYhzgx;
    }

    public String getRdj_hzdjbxh() {
        return rdj_hzdjbxh;
    }

    public void setRdj_hzdjbxh(String rdjHzdjbxh) {
        rdj_hzdjbxh = rdjHzdjbxh;
    }

    public String getRdj_lkyjsj() {
        return rdj_lkyjsj;
    }

    public void setRdj_lkyjsj(String rdjLkyjsj) {
        rdj_lkyjsj = rdjLkyjsj;
    }

    public String getRdj_ljyy() {
        return rdj_ljyy;
    }

    public void setRdj_ljyy(String rdjLjyy) {
        rdj_ljyy = rdjLjyy;
    }

    public String getRdj_ljrq() {
        return rdj_ljrq;
    }

    public void setRdj_ljrq(String rdjLjrq) {
        rdj_ljrq = rdjLjrq;
    }

    public String getRdj_zzzjyw() {
        return rdj_zzzjyw;
    }

    public void setRdj_zzzjyw(String rdjZzzjyw) {
        rdj_zzzjyw = rdjZzzjyw;
    }

    public String getRdj_hyzmyw() {
        return rdj_hyzmyw;
    }

    public void setRdj_hyzmyw(String rdjHyzmyw) {
        rdj_hyzmyw = rdjHyzmyw;
    }

    public String getRdj_ywjzjl() {
        return rdj_ywjzjl;
    }

    public void setRdj_ywjzjl(String rdjYwjzjl) {
        rdj_ywjzjl = rdjYwjzjl;
    }

    public String getRdj_ljzdyy() {
        return rdj_ljzdyy;
    }

    public void setRdj_ljzdyy(String rdjLjzdyy) {
        rdj_ljzdyy = rdjLjzdyy;
    }

    public String getRdj_mqzk() {
        return rdj_mqzk;
    }

    public void setRdj_mqzk(String rdjMqzk) {
        rdj_mqzk = rdjMqzk;
    }

    public String getRdj_dqzt() {
        return rdj_dqzt;
    }

    public void setRdj_dqzt(String rdjDqzt) {
        rdj_dqzt = rdjDqzt;
    }

    public String getRdj_ddzxsj() {
        return rdj_ddzxsj;
    }

    public void setRdj_ddzxsj(String rdjDdzxsj) {
        rdj_ddzxsj = rdjDdzxsj;
    }

    public String getRdj_tbr() {
        return rdj_tbr;
    }

    public void setRdj_tbr(String rdjTbr) {
        rdj_tbr = rdjTbr;
    }

    public String getRdj_xgybm() {
        return rdj_xgybm;
    }

    public void setRdj_xgybm(String rdjXgybm) {
        rdj_xgybm = rdjXgybm;
    }

    public String getRdj_tbrq() {
        return rdj_tbrq;
    }

    public void setRdj_tbrq(String rdjTbrq) {
        rdj_tbrq = rdjTbrq;
    }

    public String getRdj_sfyx() {
        return rdj_sfyx;
    }

    public void setRdj_sfyx(String rdjSfyx) {
        rdj_sfyx = rdjSfyx;
    }

    public String getRdj_djrq() {
        return rdj_djrq;
    }

    public void setRdj_djrq(String rdjDjrq) {
        rdj_djrq = rdjDjrq;
    }

    public String getRdj_djr() {
        return rdj_djr;
    }

    public void setRdj_djr(String rdjDjr) {
        rdj_djr = rdjDjr;
    }

    public String getRdj_djdw() {
        return rdj_djdw;
    }

    public void setRdj_djdw(String rdjDjdw) {
        rdj_djdw = rdjDjdw;
    }

    public String getRdj_sfzz() {
        return rdj_sfzz;
    }

    public void setRdj_sfzz(String rdjSfzz) {
        rdj_sfzz = rdjSfzz;
    }

    public String getRdj_bz() {
        return rdj_bz;
    }

    public void setRdj_bz(String rdjBz) {
        rdj_bz = rdjBz;
    }

    public String getRld_qrdxzqh() {
        return rld_qrdxzqh;
    }

    public void setRld_qrdxzqh(String rldQrdxzqh) {
        rld_qrdxzqh = rldQrdxzqh;
    }

    public String getRld_qcdxzqh() {
        return rld_qcdxzqh;
    }

    public void setRld_qcdxzqh(String rldQcdxzqh) {
        rld_qcdxzqh = rldQcdxzqh;
    }

    public String getRld_dqzt() {
        return rld_dqzt;
    }

    public void setRld_dqzt(String rldDqzt) {
        rld_dqzt = rldDqzt;
    }

    public String getRld_qrqcbzxx() {
        return rld_qrqcbzxx;
    }

    public void setRld_qrqcbzxx(String rldQrqcbzxx) {
        rld_qrqcbzxx = rldQrqcbzxx;
    }

    public String getRld_tbr() {
        return rld_tbr;
    }

    public void setRld_tbr(String rldTbr) {
        rld_tbr = rldTbr;
    }

    public String getRld_xgybm() {
        return rld_xgybm;
    }

    public void setRld_xgybm(String rldXgybm) {
        rld_xgybm = rldXgybm;
    }

    public String getRld_tbrq() {
        return rld_tbrq;
    }

    public void setRld_tbrq(String rldTbrq) {
        rld_tbrq = rldTbrq;
    }

    public String getRld_djrq() {
        return rld_djrq;
    }

    public void setRld_djrq(String rldDjrq) {
        rld_djrq = rldDjrq;
    }

    public String getRld_djr() {
        return rld_djr;
    }

    public void setRld_djr(String rldDjr) {
        rld_djr = rldDjr;
    }

    public String getRld_djdw() {
        return rld_djdw;
    }

    public void setRld_djdw(String rldDjdw) {
        rld_djdw = rldDjdw;
    }

    public String getRld_bz() {
        return rld_bz;
    }

    public void setRld_bz(String rldBz) {
        rld_bz = rldBz;
    }

    public String getRzf_zslx() {
        return rzf_zslx;
    }

    public void setRzf_zslx(String rzfZslx) {
        rzf_zslx = rzfZslx;
    }

    public String getRzf_fwbh() {
        return rzf_fwbh;
    }

    public void setRzf_fwbh(String rzfFwbh) {
        rzf_fwbh = rzfFwbh;
    }

    public String getRzf_fwdjdbh() {
        return rzf_fwdjdbh;
    }

    public void setRzf_fwdjdbh(String rzfFwdjdbh) {
        rzf_fwdjdbh = rzfFwdjdbh;
    }

    public String getRzf_lxzdrq() {
        return rzf_lxzdrq;
    }

    public void setRzf_lxzdrq(String rzfLxzdrq) {
        rzf_lxzdrq = rzfLxzdrq;
    }

    public String getRzf_xzdxzqh() {
        return rzf_xzdxzqh;
    }

    public void setRzf_xzdxzqh(String rzfXzdxzqh) {
        rzf_xzdxzqh = rzfXzdxzqh;
    }

    public String getRzf_xzdxxdz() {
        return rzf_xzdxxdz;
    }

    public void setRzf_xzdxxdz(String rzfXzdxxdz) {
        rzf_xzdxxdz = rzfXzdxxdz;
    }

    public String getRzf_pcs() {
        return rzf_pcs;
    }

    public void setRzf_pcs(String rzfPcs) {
        rzf_pcs = rzfPcs;
    }

    public String getRzf_sssq() {
        return rzf_sssq;
    }

    public void setRzf_sssq(String rzfSssq) {
        rzf_sssq = rzfSssq;
    }

    public String getRzf_sqmjxm() {
        return rzf_sqmjxm;
    }

    public void setRzf_sqmjxm(String rzfSqmjxm) {
        rzf_sqmjxm = rzfSqmjxm;
    }

    public String getRzf_tbr() {
        return rzf_tbr;
    }

    public void setRzf_tbr(String rzfTbr) {
        rzf_tbr = rzfTbr;
    }

    public String getRzf_xgybm() {
        return rzf_xgybm;
    }

    public void setRzf_xgybm(String rzfXgybm) {
        rzf_xgybm = rzfXgybm;
    }

    public String getRzf_tbrq() {
        return rzf_tbrq;
    }

    public void setRzf_tbrq(String rzfTbrq) {
        rzf_tbrq = rzfTbrq;
    }

    public String getRzf_djrq() {
        return rzf_djrq;
    }

    public void setRzf_djrq(String rzfDjrq) {
        rzf_djrq = rzfDjrq;
    }

    public String getRzf_djr() {
        return rzf_djr;
    }

    public void setRzf_djr(String rzfDjr) {
        rzf_djr = rzfDjr;
    }

    public String getRzf_djdw() {
        return rzf_djdw;
    }

    public void setRzf_djdw(String rzfDjdw) {
        rzf_djdw = rzfDjdw;
    }

    public String getRzf_bz() {
        return rzf_bz;
    }

    public void setRzf_bz(String rzfBz) {
        rzf_bz = rzfBz;
    }

    public String getRjy_jydwmc() {
        return rjy_jydwmc;
    }

    public void setRjy_jydwmc(String rjyJydwmc) {
        rjy_jydwmc = rjyJydwmc;
    }

    public String getRjy_dwbh() {
        return rjy_dwbh;
    }

    public void setRjy_dwbh(String rjyDwbh) {
        rjy_dwbh = rjyDwbh;
    }

    public String getRjy_jydwdjdbh() {
        return rjy_jydwdjdbh;
    }

    public void setRjy_jydwdjdbh(String rjyJydwdjdbh) {
        rjy_jydwdjdbh = rjyJydwdjdbh;
    }

    public String getRjy_jydwszqx() {
        return rjy_jydwszqx;
    }

    public void setRjy_jydwszqx(String rjyJydwszqx) {
        rjy_jydwszqx = rjyJydwszqx;
    }

    public String getRjy_jydwhy() {
        return rjy_jydwhy;
    }

    public void setRjy_jydwhy(String rjyJydwhy) {
        rjy_jydwhy = rjyJydwhy;
    }

    public String getRjy_zycsgz() {
        return rjy_zycsgz;
    }

    public void setRjy_zycsgz(String rjyZycsgz) {
        rjy_zycsgz = rjyZycsgz;
    }

    public String getRjy_bczylb() {
        return rjy_bczylb;
    }

    public void setRjy_bczylb(String rjyBczylb) {
        rjy_bczylb = rjyBczylb;
    }

    public String getRjy_sfqdldht() {
        return rjy_sfqdldht;
    }

    public void setRjy_sfqdldht(String rjySfqdldht) {
        rjy_sfqdldht = rjySfqdldht;
    }

    public String getRjy_sfsbx() {
        return rjy_sfsbx;
    }

    public void setRjy_sfsbx(String rjySfsbx) {
        rjy_sfsbx = rjySfsbx;
    }


    public String getRjy_zjjnshbxlb() {
        return rjy_zjjnshbxlb;
    }

    public void setRjy_zjjnshbxlb(String rjyZjjnshbxlb) {
        rjy_zjjnshbxlb = rjyZjjnshbxlb;
    }

    public String getRjy_syjnshbxlb() {
        return rjy_syjnshbxlb;
    }

    public void setRjy_syjnshbxlb(String rjySyjnshbxlb) {
        rjy_syjnshbxlb = rjySyjnshbxlb;
    }

    public String getRjy_tbr() {
        return rjy_tbr;
    }

    public void setRjy_tbr(String rjyTbr) {
        rjy_tbr = rjyTbr;
    }

    public String getRjy_xgybm() {
        return rjy_xgybm;
    }

    public void setRjy_xgybm(String rjyXgybm) {
        rjy_xgybm = rjyXgybm;
    }

    public String getRjy_tbrq() {
        return rjy_tbrq;
    }

    public void setRjy_tbrq(String rjyTbrq) {
        rjy_tbrq = rjyTbrq;
    }

    public String getRjy_djrq() {
        return rjy_djrq;
    }

    public void setRjy_djrq(String rjyDjrq) {
        rjy_djrq = rjyDjrq;
    }

    public String getRjy_djr() {
        return rjy_djr;
    }

    public void setRjy_djr(String rjyDjr) {
        rjy_djr = rjyDjr;
    }

    public String getRjy_djdw() {
        return rjy_djdw;
    }

    public void setRjy_djdw(String rjyDjdw) {
        rjy_djdw = rjyDjdw;
    }

    public String getRjy_bz() {
        return rjy_bz;
    }

    public void setRjy_bz(String rjyBz) {
        rjy_bz = rjyBz;
    }

    public String getRdj_gzlx() {
        return rdj_gzlx;
    }

    public void setRdj_gzlx(String rdjGzlx) {
        rdj_gzlx = rdjGzlx;
    }

    public String getRjy_tshy() {
        return rjy_tshy;
    }

    public void setRjy_tshy(String rjyTshy) {
        rjy_tshy = rjyTshy;
    }

    public String getRjy_jydwxxdz() {
        return rjy_jydwxxdz;
    }

    public void setRjy_jydwxxdz(String rjyJydwxxdz) {
        rjy_jydwxxdz = rjyJydwxxdz;
    }

    public String getRjy_dwcjdjdbh() {
        return rjy_dwcjdjdbh;
    }

    public void setRjy_dwcjdjdbh(String rjyDwcjdjdbh) {
        rjy_dwcjdjdbh = rjyDwcjdjdbh;
    }

    public String getRjx_jdxxmc() {
        return rjx_jdxxmc;
    }

    public void setRjx_jdxxmc(String rjxJdxxmc) {
        rjx_jdxxmc = rjxJdxxmc;
    }

    public String getRjx_jdxxdz() {
        return rjx_jdxxdz;
    }

    public void setRjx_jdxxdz(String rjxJdxxdz) {
        rjx_jdxxdz = rjxJdxxdz;
    }

    public String getRdj_yhzqtgx() {
        return rdj_yhzqtgx;
    }

    public void setRdj_yhzqtgx(String rdjYhzqtgx) {
        rdj_yhzqtgx = rdjYhzqtgx;
    }

    public String getRdj_ljqtyy() {
        return rdj_ljqtyy;
    }

    public void setRdj_ljqtyy(String rdjLjqtyy) {
        rdj_ljqtyy = rdjLjqtyy;
    }


    public String getRjy_jydwqthy() {
        return rjy_jydwqthy;
    }

    public void setRjy_jydwqthy(String rjyJydwqthy) {
        rjy_jydwqthy = rjyJydwqthy;
    }

    public String getRdj_mqzkqtxx() {
        return rdj_mqzkqtxx;
    }

    public void setRdj_mqzkqtxx(String rdjMqzkqtxx) {
        rdj_mqzkqtxx = rdjMqzkqtxx;
    }

    public String getRdj_sfgzdcj() {
        return rdj_sfgzdcj;
    }

    public void setRdj_sfgzdcj(String rdjSfgzdcj) {
        rdj_sfgzdcj = rdjSfgzdcj;
    }

    public String getRdj_sfjzdcj() {
        return rdj_sfjzdcj;
    }

    public void setRdj_sfjzdcj(String rdjSfjzdcj) {
        rdj_sfjzdcj = rdjSfjzdcj;
    }

    public String getBzryxx_xm() {
        return bzryxx_xm;
    }

    public void setBzryxx_xm(String bzryxxXm) {
        bzryxx_xm = bzryxxXm;
    }

    public String getBzryxx_sfzhm() {
        return bzryxx_sfzhm;
    }

    public void setBzryxx_sfzhm(String bzryxxSfzhm) {
        bzryxx_sfzhm = bzryxxSfzhm;
    }

    public String getBzryxx_bh() {
        return bzryxx_bh;
    }

    public void setBzryxx_bh(String bzryxxBh) {
        bzryxx_bh = bzryxxBh;
    }

    public String getBzryxx_xb() {
        return bzryxx_xb;
    }

    public void setBzryxx_xb(String bzryxxXb) {
        bzryxx_xb = bzryxxXb;
    }

    public String getBzryxx_mz() {
        return bzryxx_mz;
    }

    public void setBzryxx_mz(String bzryxxMz) {
        bzryxx_mz = bzryxxMz;
    }

    public String getBzryxx_yjxzqh() {
        return bzryxx_yjxzqh;
    }

    public void setBzryxx_yjxzqh(String bzryxxYjxzqh) {
        bzryxx_yjxzqh = bzryxxYjxzqh;
    }

    public String getBzryxx_hjdz() {
        return bzryxx_hjdz;
    }

    public void setBzryxx_hjdz(String bzryxxHjdz) {
        bzryxx_hjdz = bzryxxHjdz;
    }

    public String getBzryxx_xzdxzqh() {
        return bzryxx_xzdxzqh;
    }

    public void setBzryxx_xzdxzqh(String bzryxxXzdxzqh) {
        bzryxx_xzdxzqh = bzryxxXzdxzqh;
    }

    public String getBzryxx_xzdxxdz() {
        return bzryxx_xzdxxdz;
    }

    public void setBzryxx_xzdxxdz(String bzryxxXzdxxdz) {
        bzryxx_xzdxxdz = bzryxxXzdxxdz;
    }

    public String getBzryxx_pcsbm() {
        return bzryxx_pcsbm;
    }

    public void setBzryxx_pcsbm(String bzryxxPcsbm) {
        bzryxx_pcsbm = bzryxxPcsbm;
    }

    public String getBzryxx_slsj() {
        return bzryxx_slsj;
    }

    public void setBzryxx_slsj(String bzryxxSlsj) {
        bzryxx_slsj = bzryxxSlsj;
    }

    public String getBzryxx_sfyx() {
        return bzryxx_sfyx;
    }

    public void setBzryxx_sfyx(String bzryxxSfyx) {
        bzryxx_sfyx = bzryxxSfyx;
    }

    public String getBzryxx_wxyf() {
        return bzryxx_wxyf;
    }

    public void setBzryxx_wxyf(String bzryxxWxyf) {
        bzryxx_wxyf = bzryxxWxyf;
    }

    public String getBzryxx_xgsj() {
        return bzryxx_xgsj;
    }

    public void setBzryxx_xgsj(String bzryxxXgsj) {
        bzryxx_xgsj = bzryxxXgsj;
    }

    public String getZzzsfyx() {
        return zzzsfyx;
    }

    public void setZzzsfyx(String zzzsfyx) {
        this.zzzsfyx = zzzsfyx;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getZkxx_bh() {
        return zkxx_bh;
    }

    public void setZkxx_bh(String zkxxBh) {
        zkxx_bh = zkxxBh;
    }

    public String getZkxx_cjsj() {
        return zkxx_cjsj;
    }

    public void setZkxx_cjsj(String zkxxCjsj) {
        zkxx_cjsj = zkxxCjsj;
    }

    public String getZkxx_xgsj() {
        return zkxx_xgsj;
    }

    public void setZkxx_xgsj(String zkxxXgsj) {
        zkxx_xgsj = zkxxXgsj;
    }

    public String getZkxx_zkzt() {
        return zkxx_zkzt;
    }

    public void setZkxx_zkzt(String zkxxZkzt) {
        zkxx_zkzt = zkxxZkzt;
    }

    public String getZkxx_zplj() {
        return zkxx_zplj;
    }

    public void setZkxx_zplj(String zkxxZplj) {
        zkxx_zplj = zkxxZplj;
    }

    public String getZkxx_yxkssj() {
        return zkxx_yxkssj;
    }

    public void setZkxx_yxkssj(String zkxxYxkssj) {
        zkxx_yxkssj = zkxxYxkssj;
    }

    public String getZkxx_yxjzsj() {
        return zkxx_yxjzsj;
    }

    public void setZkxx_yxjzsj(String zkxxYxjzsj) {
        zkxx_yxjzsj = zkxxYxjzsj;
    }

    public String getZkxx_jznxkssj() {
        return zkxx_jznxkssj;
    }

    public void setZkxx_jznxkssj(String zkxxJznxkssj) {
        zkxx_jznxkssj = zkxxJznxkssj;
    }

    public int getZkxx_wxjzy() {
        return zkxx_wxjzy;
    }

    public void setZkxx_wxjzy(int zkxxWxjzy) {
        zkxx_wxjzy = zkxxWxjzy;
    }

    public String getZkxx_kdf() {
        return zkxx_kdf;
    }

    public void setZkxx_kdf(String zkxxKdf) {
        zkxx_kdf = zkxxKdf;
    }

    public String getZkxx_ykth() {
        return zkxx_ykth;
    }

    public void setZkxx_ykth(String zkxxYkth) {
        zkxx_ykth = zkxxYkth;
    }

    public String getZkxx_zxyy() {
        return zkxx_zxyy;
    }

    public void setZkxx_zxyy(String zkxxZxyy) {
        zkxx_zxyy = zkxxZxyy;
    }

    public String getZkxx_sfyx() {
        return zkxx_sfyx;
    }

    public void setZkxx_sfyx(String zkxxSfyx) {
        zkxx_sfyx = zkxxSfyx;
    }

    public String getZkxx_rybh() {
        return zkxx_rybh;
    }

    public void setZkxx_rybh(String zkxxRybh) {
        zkxx_rybh = zkxxRybh;
    }

    public String getZkxx_lx() {
        return zkxx_lx;
    }

    public void setZkxx_lx(String zkxxLx) {
        zkxx_lx = zkxxLx;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRdj_csd() {
        return rdj_csd;
    }

    public void setRdj_csd(String rdjCsd) {
        rdj_csd = rdjCsd;
    }

    public String getHxx_bhrks() {
        return hxx_bhrks;
    }

    public void setHxx_bhrks(String hxxBhrks) {
        hxx_bhrks = hxxBhrks;
    }

    public String getHxx_slsyxrks() {
        return hxx_slsyxrks;
    }

    public void setHxx_slsyxrks(String hxxSlsyxrks) {
        hxx_slsyxrks = hxxSlsyxrks;
    }

    public String getHxx_slsyxnrks() {
        return hxx_slsyxnrks;
    }

    public void setHxx_slsyxnrks(String hxxSlsyxnrks) {
        hxx_slsyxnrks = hxxSlsyxnrks;
    }

    public String getHxx_slsyxnvrks() {
        return Hxx_slsyxnvrks;
    }

    public void setHxx_slsyxnvrks(String hxxSlsyxnvrks) {
        Hxx_slsyxnvrks = hxxSlsyxnvrks;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public String getCzdwbm() {
        return czdwbm;
    }

    public void setCzdwbm(String czdwbm) {
        this.czdwbm = czdwbm;
    }

    public String getBzryxx_fwzbh() {
        return bzryxx_fwzbh;
    }

    public void setBzryxx_fwzbh(String bzryxxFwzbh) {
        bzryxx_fwzbh = bzryxxFwzbh;
    }

    public String getBzryxx_jznxkssj() {
        return bzryxx_jznxkssj;
    }

    public void setBzryxx_jznxkssj(String bzryxxJznxkssj) {
        bzryxx_jznxkssj = bzryxxJznxkssj;
    }

    public String getBzryxx_csrq() {
        return bzryxx_csrq;
    }

    public void setBzryxx_csrq(String bzryxxCsrq) {
        bzryxx_csrq = bzryxxCsrq;
    }

    public String getZkxx_qfdwlx() {
        return zkxx_qfdwlx;
    }

    public void setZkxx_qfdwlx(String zkxxQfdwlx) {
        zkxx_qfdwlx = zkxxQfdwlx;
    }

    public String getSczpmc() {
        return sczpmc;
    }

    public void setSczpmc(String sczpmc) {
        this.sczpmc = sczpmc;
    }

    public String getSczplx() {
        return sczplx;
    }

    public void setSczplx(String sczplx) {
        this.sczplx = sczplx;
    }

    public String getSczpstr() {
        return sczpstr;
    }

    public void setSczpstr(String sczpstr) {
        this.sczpstr = sczpstr;
    }

    public String getRzf_ssfjjpcsdm() {
        return rzf_ssfjjpcsdm;
    }

    public void setRzf_ssfjjpcsdm(String rzfSsfjjpcsdm) {
        rzf_ssfjjpcsdm = rzfSsfjjpcsdm;
    }

    public String getFwzjbxxdjb_fwzbh() {
        return fwzjbxxdjb_fwzbh;
    }

    public void setFwzjbxxdjb_fwzbh(String fwzjbxxdjbFwzbh) {
        fwzjbxxdjb_fwzbh = fwzjbxxdjbFwzbh;
    }

    public String getZkxx_sfxysh() {
        return zkxx_sfxysh;
    }

    public void setZkxx_sfxysh(String zkxxSfxysh) {
        zkxx_sfxysh = zkxxSfxysh;
    }

    public String getZkxx_wtgyy() {
        return zkxx_wtgyy;
    }

    public void setZkxx_wtgyy(String zkxxWtgyy) {
        zkxx_wtgyy = zkxxWtgyy;
    }

    public String getZkxx_sqlx() {
        return zkxx_sqlx;
    }

    public void setZkxx_sqlx(String zkxxSqlx) {
        zkxx_sqlx = zkxxSqlx;
    }

    public String getBzryxx_lxdh() {
        return bzryxx_lxdh;
    }

    public void setBzryxx_lxdh(String bzryxxLxdh) {
        bzryxx_lxdh = bzryxxLxdh;
    }

    public String getQfdwmc() {
        return qfdwmc;
    }

    public void setQfdwmc(String qfdwmc) {
        this.qfdwmc = qfdwmc;
    }

    public String getBzryxx_hjlx() {
        return bzryxx_hjlx;
    }

    public void setBzryxx_hjlx(String bzryxxHjlx) {
        bzryxx_hjlx = bzryxxHjlx;
    }

    public String getBzryxx_scxzdz() {
        return bzryxx_scxzdz;
    }

    public void setBzryxx_scxzdz(String bzryxxScxzdz) {
        bzryxx_scxzdz = bzryxxScxzdz;
    }

    public String getRdj_zycsqtgz() {
        return rdj_zycsqtgz;
    }

    public void setRdj_zycsqtgz(String rdjZycsqtgz) {
        rdj_zycsqtgz = rdjZycsqtgz;
    }

    public String getRjy_jydwdjbxh() {
        return rjy_jydwdjbxh;
    }

    public void setRjy_jydwdjbxh(String rjyJydwdjbxh) {
        rjy_jydwdjbxh = rjyJydwdjbxh;
    }

    public String getZkxx_sldw() {
        return zkxx_sldw;
    }

    public void setZkxx_sldw(String zkxxSldw) {
        zkxx_sldw = zkxxSldw;
    }

    /*********************20161008 增加身份证六项信息*****************************************/
    private String sfz_mz;
    private String sfz_hjdz;
    private String sfsfzdq;


    /**
     * @return the sfz_mz
     */
    public String getSfz_mz() {
        return sfz_mz;
    }

    /**
     * @param sfzMz the sfz_mz to set
     */
    public void setSfz_mz(String sfzMz) {
        sfz_mz = sfzMz;
    }

    /**
     * @return the sfz_hjdz
     */
    public String getSfz_hjdz() {
        return sfz_hjdz;
    }

    /**
     * @param sfzHjdz the sfz_hjdz to set
     */
    public void setSfz_hjdz(String sfzHjdz) {
        sfz_hjdz = sfzHjdz;
    }

    /**
     * @return the sssfzdq
     */
    public String getSfsfzdq() {
        return sfsfzdq;
    }

    /**
     * @param sssfzdq the sssfzdq to set
     */
    public void setSfsfzdq(String sssfzdq) {
        this.sfsfzdq = sssfzdq;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getRzf_fzxm() {
        return rzf_fzxm;
    }

    public void setRzf_fzxm(String rzf_fzxm) {
        this.rzf_fzxm = rzf_fzxm;
    }

    public String getRzf_fzsfz() {
        return rzf_fzsfz;
    }

    public void setRzf_fzsfz(String rzf_fzsfz) {
        this.rzf_fzsfz = rzf_fzsfz;
    }

    public String getRzf_fzxzd() {
        return rzf_fzxzd;
    }

    public void setRzf_fzxzd(String rzf_fzxzd) {
        this.rzf_fzxzd = rzf_fzxzd;
    }

    public String getRzf_lxdh() {
        return rzf_lxdh;
    }

    public void setRzf_lxdh(String rzf_lxdh) {
        this.rzf_lxdh = rzf_lxdh;
    }

    public String getRzf_fwszddz() {
        return rzf_fwszddz;
    }

    public void setRzf_fwszddz(String rzf_fwszddz) {
        this.rzf_fwszddz = rzf_fwszddz;
    }

    public String getPcsmc() {
        return pcsmc;
    }

    public void setPcsmc(String pcsmc) {
        this.pcsmc = pcsmc;
    }

    public String getXqbh() {
        return xqbh;
    }

    public void setXqbh(String xqbh) {
        this.xqbh = xqbh;
    }
}
