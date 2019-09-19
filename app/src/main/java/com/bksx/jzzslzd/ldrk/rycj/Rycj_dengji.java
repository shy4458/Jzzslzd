package com.bksx.jzzslzd.ldrk.rycj;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bean.GlyCode;
import com.bksx.jzzslzd.bo.CodeTable;
import com.bksx.jzzslzd.bo.DjkSl;
import com.bksx.jzzslzd.bo.Fwxx;
import com.bksx.jzzslzd.bo.IDCard;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.bo.UserLogin;
import com.bksx.jzzslzd.bo.XqCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rykc.Rykc_fangwuliebiao_act;
import com.bksx.jzzslzd.net.HttpCallBack;
import com.bksx.jzzslzd.net.HttpRequest;
import com.bksx.jzzslzd.net.SxConfig;
import com.bksx.jzzslzd.tools.DzxzHelper;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.PicDispose;
import com.bksx.jzzslzd.tools.ReadCardControler;
import com.bksx.jzzslzd.tools.SelectDwhyCsgzLd;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.bksx.jzzslzd.tools.SqliteHelper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bksx.jzzslzd.common.StaticObject.imageToBase64;
import static com.bksx.jzzslzd.common.StaticObject.showToast;

public class Rycj_dengji extends Activity implements
        android.view.View.OnClickListener, OnCheckedChangeListener {
    // 控制显隐用的LinearLayout
    private LinearLayout jyxx_mqzk_jy_l, jyxx_mqzk_qt_l, jyxx_mqzk_xs_l, jyxx_jy_qtgz_l, jzxx_qtljyy_l,
            jyxx_jy_qtsshy_l;

    private SharedPreferences preference;
    // 控制显隐的TextView
    private TextView jbxx_t, jzxx_t, jyxx_t, sqcl_t;
    // 标题下的最大单位
    private LinearLayout jbxx_l, jzxx_l, jyxx_l, sqcl_l;

    private ImageButton btn_back_i;
    private Button btn_hs, btn_xctj, btn_fwcx, btn_fwdj, btn_zctj;
    private String path; // 拍照图片地址/
    private String fileName; // 照片名
    private File photoFile;
    private long t1 = 0;

    /**
     * 人员暂存id（用于提交成功后的删除操作）
     */
    private String zc_id;

    /**
     * 保存提交的vo，用于下次界面回填
     */
    private RyzcVo saveVo;
    /**
     * 页面分为五部分--基本信息,家庭户信息,居住信息,就业社保信息,备注,管理员信息（新增）
     */
    // 填表日期
    private TextView tbrq;
    /** ---------------------------------------------------------- */
    /** -------------------------基本信息-------------------------- */
    /**
     * -----------------------------------------------------------
     */
    // 输入框： 姓名，出生日期，身份证号，户籍详细地址，联系电话，QQ号码，E-mail
    private EditText jbxx_xm_e, jbxx_csrq_e, jbxx_sfzh_e, jbxx_hjxxdz_e, jbxx_lxdh_e;
    // 选择框： 民族，政治面貌，受教育程度，婚姻状况，户籍类别，出生地，居住证件，免疫接种证，婚育证明
    private SelectViewAndHandlerAndMsg jbxx_mz_s, jbxx_zzmm_s, jbxx_sjycd_s, jbxx_hyzk_s, jbxx_hjlb_s, jbxx_csd_s, jbxx_jzzj_s,
            jbxx_myjzz_s, jbxx_hyzm_s;
    // 选择框背景：性别，民族，政治面貌，受教育程度，婚姻状况，户籍类别，出生地，出生地（前），居住证件，免疫接种证，婚育证明
    private TextView jbxx_xb_t, jbxx_mz_t, jbxx_zzmm_t, jbxx_sjycd_t, jbxx_hyzk_t, jbxx_hjlb_t, jbxx_csd_t, jbxx_csd_qt,
            jbxx_jzzj_t, jbxx_myjzz_t, jbxx_hyzm_t;
    // 户籍地址选择器
    private DzxzHelper jbxx_hjdz_s;
    // 照片
    private ImageView jbxx_photo_i, jbxx_warn;
    // 照片路径
    private String jbxx_photoPath = "";
    // 申请材料
    private Button clxx_sfzmcl, clxx_zjzscl, clxx_zzrkdjb;

    /** -------------------------------------------------------- */
    /**
     * -------------------------居住信息------------------------
     */
    // 输入框：房主姓名，来京其他原因
    private EditText jzxx_fzxm_e, jzxx_fzsfzh_e, jzxx_ljqtyy_e, jzxx_xzdz_e;
    // 选择框：来京原因，居住类型
    private SelectViewAndHandlerAndMsg jzxx_ljyy_s, jzxx_jzlx_s;
    // 时间选择框背景：来京日期，来现住地日期,居住类型,来京原因
    private TextView jzxx_ljrq_t, jzxx_lxzdrq_t, jzxx_jzlx_t, jzxx_ljyy_t;
    private String[] fwxx;
    /** ------------------------------------------------------------ */
    /** -------------------------就业社保信息------------------------ */
    /**
     * ------------------------------------------------------------
     */
    // 输入框：就业单位名称，单位详细地址，学校名称，学校所在地，详细信息，其他工作（描述）
    private EditText jyxx_dwmc_e, jyxx_dwxxdz_e, jyxx_xxmc_e, jyxx_xxszd_e, jyxx_jyxxxx_e, jyxx_qthy_e, jyxx_qtgz_e;
    // 选择框：目前状况， 职业，签订劳动合同，单位地址
    private SelectViewAndHandlerAndMsg jyxx_mqzk_s, jyxx_sszy_s, jyxx_sfqdht_s,
            jyxx_dwdz_s, jyxx_xxszd_s;
    // 选择框背景：目前状况，单位所属行业，从事工作，职业，签订劳动合同，单位地址,学校所在地
    private TextView jyxx_mqzk_t, jyxx_dwsshy_t, jyxx_ccgz_t, jyxx_sszy_t,
            jyxx_sfqdht_t, jyxx_dwdz_t;
    //单位详细地址
    private ImageView jyxx_dwxxdz_dw_i;
    // 单位所属行业和从事工作联动选择器
    private SelectDwhyCsgzLd jyxx_dwhy_csgz_s;
    // 社保 (在京社保：无，养老，失业，医疗，工伤，生育)
    private CheckBox jyxx_zjsb_w, jyxx_zjsb_yl, jyxx_zjsb_sy, jyxx_zjsb_yil,
            jyxx_zjsb_gs, jyxx_zjsb_shy, jyxx_sbxx_w, jyxx_sbxx_yl,
            jyxx_sbxx_sy, jyxx_sbxx_yil, jyxx_sbxx_gs, jyxx_sbxx_shy;
    private ReadCardControler readCardControler;

    private LinearLayout ll_sfzmcl, ll_zjzscl, ll_zzrkdjb;
    private ArrayList<ImageView> sfzmclList = new ArrayList<ImageView>();
    private ArrayList<String> sfzmclPathList = new ArrayList<String>();
    private ArrayList<ImageView> zjzsclList = new ArrayList<ImageView>();
    private ArrayList<String> zjzsclPathList = new ArrayList<String>();
    private ArrayList<ImageView> zzrkdjbList = new ArrayList<ImageView>();
    private ArrayList<String> zzrkdjbPathList = new ArrayList<String>();
    private UserLogin userData;
    private TextView tvSsfwz;
    private TextView tvSxxq;
    private TextView tvGly;
    private SelectViewAndHandlerAndMsg jdxx_fwz_s;
    private LinkedHashMap<String, String> xzqhmap;
    private SelectViewAndHandlerAndMsg xqS;
    private SelectViewAndHandlerAndMsg glyS;
    private LinkedHashMap<String, String> glyMap;
    private RyzcVo rv;
    private LinkedHashMap<String, String> fwzmap;
    private LinkedHashMap<String, String> xqmap;
    private String fwz;
    private String xq;
    private String gly;
    private String ryzcData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rycj_dengji);

        findView();
        setClick();
        initData();
        //身份证扫描
        readCardControler = ReadCardControler.getInstance(Rycj_dengji.this, handler);
    }


    /**
     * 回显
     */
    private void initData() {
        init();
        Intent intent = getIntent();
        // 暂存0，
        int flag = intent.getFlags();
        if (flag == 0) {// 暂存0，
            ryzcData = intent.getStringExtra("ryzcData");
            if (ryzcData != null && !"".equals(ryzcData)) {
                rv = new Gson().fromJson(ryzcData, RyzcVo.class);
                StaticObject.nullConverNullString(rv); // 属性null全转成""
                setData(rv);
                zc_id = rv.getJb_sfz() + rv.getJb_xm();
                btn_zctj.setVisibility(View.GONE);

            } else {
                initShow();
                zc_id = null;
                ArrayList<CodeTable> fwz = userData.getFwz();
                String id = fwz.get(0).getId();
                cxXqAndGly(id);
            }
        } else {

        }
    }

    /*
     * 点击事件
     *
     */
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.dengji_title_jibenxinxi:
                // 标题 ——基本信息——点击
                titleClick(jbxx_l, jbxx_t, R.drawable.ryhc_icon_word);
                break;
            case R.id.dengji_title_juzhuxinxi:
                // 标题 ——居住信息——点击
                titleClick(jzxx_l, jzxx_t, R.drawable.fwhc_icon_fw);
                break;
            case R.id.dengji_title_jiuyeshebao:
                // 标题 ——就业社保——点击
                titleClick(jyxx_l, jyxx_t, R.drawable.ryhc_icon_msg);
                break;
            case R.id.dengji_title_sqcl:
                // 标题 ——申请材料——点击
                titleClick(sqcl_l, sqcl_t, R.drawable.ryhc_icon_word);
                break;
            // 填表日期
            case R.id.dengji_tv_date_tbrq:
                datePick(tbrq);
                break;
            // 来京日期
            case R.id.dengji_tv_date_laijingriqi:
                datePick(jzxx_ljrq_t);
                break;
            // 来现住地日期
            case R.id.dengji_tv_date_laixianzhudi:
                datePick(jzxx_lxzdrq_t);
                break;

            // 重置改人员核实
            case R.id.dengji_btn_chongzhi:
                ryhs();
                break;
            // 点击离线暂存数据！！！
            case R.id.dengji_btn_zancun:
                ryzc();
                break;
            // 详采提交数据！！！
            case R.id.dengji_btn_next:
                // 人员6项为必校验项
                if (!checkSix()) {
                    break;
                }
                // 校验所有必填项
                if (checkAllForm()) {
                    if (t1 == 0) {//第一次单击，初始化为本次单击的时间
                        t1 = (new Date()).getTime();
                        ryxc();
                    } else {
                        long curTime = (new Date()).getTime();//本次单击的时间
                        System.out.println("两次单击间隔时间：" + (curTime - t1));//计算本次和上次的时间差
                        if (curTime - t1 > 3 * 1000) {
                            //间隔3秒允许点击，可以根据需要修改间隔时间
                            t1 = curTime;//当前单击事件变为上次时间
                            ryxc();
                        } else {
                            StaticObject.showToast(Rycj_dengji.this, "点击太过频繁");
                        }
                    }

                }
                break;
            // 房屋信息查询
            case R.id.dengji_btn_fangwu:
                fwcx();
                break;
            // 房屋信息录入
            case R.id.dengji_btn_fangwudengji:
                Intent intent = new Intent(Rycj_dengji.this,
                        Rycj_fangwudengji.class);
                startActivity(intent);
                break;
            // 返回
            case R.id.ryjc_dengji_back:
                this.finish();
                break;
            default:
                break;
        }
    }


    private String pcsdm;
    HttpCallBack<String> fwcx = new HttpCallBack<String>(Rycj_dengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 200) {
                Fwxx fwxx = gson.fromJson(gson.toJson(jsonResult.getReturnData()), Fwxx.class);
                pcsdm = fwxx.getPcsdm();
                Intent intent = new Intent(Rycj_dengji.this,
                        Rykc_fangwuliebiao_act.class);
                intent.putExtra("data", gson.toJson(jsonResult.getReturnData()));
                startActivityForResult(intent, 0);
            } else {
                showToast(Rycj_dengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    /**
     * 房屋查询
     */
    private void fwcx() {
        if (isNull(jzxx_jzlx_s, "居住类型")) {
            return;
        }
        if (isNull(jzxx_fzxm_e, "房主姓名")) {
            return;
        }
        String sfzhm = jzxx_fzsfzh_e.getText().toString().trim();
        if (!TextUtils.isEmpty(sfzhm)) {
            String[] id_check = FormCheck.check_Card_ID(sfzhm);
            if (id_check[0].equals("false")) {
                StaticObject.showToast(this, id_check[1]);
                jzxx_fzsfzh_e.requestFocus();
                return;
            } else {
                jzxx_fzsfzh_e.setText(id_check[1]);
            }
        }
        Fwxx fwxx = new Fwxx();
        fwxx.setFwlb(jzxx_jzlx_s.getCodeId());
        fwxx.setFzxm(jzxx_fzxm_e.getText().toString().trim());
        fwxx.setFzsfzhm(jzxx_fzsfzh_e.getText().toString().trim());
        Gson gson = new GsonBuilder().create();
        fwcx.showDialog("请求中...");
        HttpRequest.POST(Rycj_dengji.this, HttpRequest.FWCX, gson.toJson(fwxx), fwcx);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 婚姻状况
                case 12:
                    // 未婚触发
                    if ("10".equals(jbxx_hyzk_s.getCodeId())) {
                        // 婚育证明无
                        jbxx_hyzm_s.setCodeId("0");
                        jbxx_hyzm_s.setClickable(false);
                        jbxx_hyzm_t.setTextColor(Rycj_dengji.this.getResources().getColor(R.color.gray));
                    } else {
                        jbxx_hyzm_s.setClickable(true);
                        jbxx_hyzm_t.setTextColor(Rycj_dengji.this.getResources().getColor(R.color.black));
                    }
                    break;
                case 32:
                    // 来京原因
                    if (jzxx_ljyy_s.getCodeId().equals("99")) {
                        jzxx_qtljyy_l.setVisibility(View.VISIBLE);
                    } else {
                        jzxx_qtljyy_l.setVisibility(View.GONE);
                    }
                    break;
                case 41:
                    if (jyxx_mqzk_s.getCodeId().equals("01")) {
                        // 就业
                        jyxx_mqzk_jy_l.setVisibility(View.VISIBLE);
                        jyxx_mqzk_qt_l.setVisibility(View.GONE);
                        jyxx_mqzk_xs_l.setVisibility(View.GONE);

                    } else if (jyxx_mqzk_s.getCodeId().equals("02")) {
                        // 学生
                        jyxx_mqzk_jy_l.setVisibility(View.GONE);
                        jyxx_mqzk_qt_l.setVisibility(View.GONE);
                        jyxx_mqzk_xs_l.setVisibility(View.VISIBLE);
                    } else if (jyxx_mqzk_s.getCodeId().equals("99")) {
                        // 其他
                        jyxx_mqzk_jy_l.setVisibility(View.GONE);
                        jyxx_mqzk_qt_l.setVisibility(View.VISIBLE);
                        jyxx_mqzk_xs_l.setVisibility(View.GONE);
                    } else {
                        jyxx_mqzk_jy_l.setVisibility(View.GONE);
                        jyxx_mqzk_qt_l.setVisibility(View.GONE);
                        jyxx_mqzk_xs_l.setVisibility(View.GONE);
                    }
                    break;

                case 56:
                    //TODO
                    String id = jdxx_fwz_s.getCodeId();
                    cxXqAndGly(id);
                    break;
                case 110:
                    // 扫描成功后 回调
                    IDCard card = (IDCard) msg.obj;
                    jbxx_xm_e.setText(card.getXm().trim());
                    jbxx_sfzh_e.setText(card.getZjhm().trim());
                    jbxx_csrq_e.setText(card.getCsrq());
                    jbxx_hjxxdz_e.setText(card.getHjdz());
                    jbxx_mz_t.setText(card.getMz() + "族");
                    jbxx_xb_t.setText(card.getXb());
                    StaticObject.print("code: " + card.getZjhm().substring(0, 6));
                    jbxx_hjdz_s
                            .setCodeId(card.getZjhm().substring(0, 6) + "000000");
                    jbxx_photoPath = card.getPhotoPath();
                    jbxx_photo_i.setImageBitmap(BitmapFactory
                            .decodeFile(jbxx_photoPath));
                    // 免疫
                    if (!FormCheck.age_before(card.getZjhm())) {
                        jbxx_myjzz_s.setCodeId("0");
                        jbxx_myjzz_s.setClickable(false);
                        jbxx_myjzz_t.setTextColor(Rycj_dengji.this.getResources().getColor(R.color.gray));
                    } else {
                        jbxx_myjzz_s.setClickable(true);
                        jbxx_myjzz_t.setTextColor(Rycj_dengji.this.getResources().getColor(R.color.black));
                    }
                    showToast(Rycj_dengji.this, "读卡成功!");
                    ryhs();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 锁死6项，不让输入了
     */
    private void lockbaseSix() {
        StaticObject.lock(jbxx_xm_e);
        StaticObject.lock(jbxx_sfzh_e);
        jbxx_xm_e.setTextColor(this.getResources().getColor(R.color.gray));
        jbxx_sfzh_e.setTextColor(this.getResources().getColor(R.color.gray));
    }

    /**
     * 清楚暂存人员信息，并判断是不是暂存数据提交
     */
    private boolean cleanZcInfo() {
        if (zc_id != null && zc_id.length() > 0) {
            SqliteHelper helper = SqliteHelper.getInstance(Rycj_dengji.this);
            String sql = "delete from ryxxzcb where id='" + zc_id + "'";
            helper.execSQL(sql, null);
            helper.close();
            return true;
        }
        return false;
    }

    /**
     * 校验所有必填项
     */
    private boolean checkAllForm() {
        if (isNull(tbrq, "填表日期")) {
            return false;
        }
        if (isNull(tvGly, "管理员")) {
            return false;
        }
        if (!checkOtherBaseInfo()) {
            return false;
        }
        if (!checkHouseInfo()) {
            return false;
        }
        if (!checkWorkInfo()) {
            return false;
        }
        if (!checkphotoInfo()) {
            return false;
        }
        return true;
    }

    /**
     * 照片校验
     *
     * @return
     */
    private boolean checkphotoInfo() {
        for (String path : sfzmclPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "身份证明材料缺失");
                return false;
            }
        }
        for (String path : zjzsclPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "在京住所证明材料缺失");
                return false;
            }
        }
        for (String path : zzrkdjbPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "暂住人口登记表材料缺失");
                return false;
            }
        }
        return true;
    }

    /**
     * 校验其他基本信息
     */
    private boolean checkOtherBaseInfo() {
        if (jbxx_hjdz_s != null && "".equals(jbxx_hjdz_s.getCodeId())) {
            jbxx_hjdz_s.requestFocus();
            showToast(Rycj_dengji.this, "请选择户籍地址");
            return false;
        }
        if (isNull(jbxx_sjycd_s, "受教育程度")) {
            return false;
        }
        if (isNull(jbxx_zzmm_s, "政治面貌")) {
            return false;
        }
        if (isNull(jbxx_jzzj_s, "居住证件")) {
            return false;
        }
        if (isNull(jbxx_hjlb_s, "户籍类别")) {
            return false;
        }
        if (isNull(jbxx_myjzz_s, "免疫接种证")) {
            return false;
        }
        if (isNull(jbxx_hyzk_s, "婚姻状况")) {
            return false;
        }
        if (isNull(jbxx_hyzm_s, "婚育证明")) {
            return false;
        }
        if (isNull(jbxx_lxdh_e, "联系电话")) {
            return false;
        } else if (!checkPhone()) {
            StaticObject.showToast(Rycj_dengji.this, "请输入正确的手机号码");
            return false;
        }
        return true;
    }

    /**
     * 校验手机号码
     */
    private boolean checkPhone() {
        String str = jbxx_lxdh_e.getText().toString().trim();
        String regEx = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }

    /**
     * 校验居住信息
     */
    private boolean checkHouseInfo() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String ljDdate = jzxx_ljrq_t.getText().toString().trim();
        String lxzdDdate = jzxx_lxzdrq_t.getText().toString().trim();
        if (isNull(jzxx_ljrq_t, "来京日期")) {
            return false;
        }
        if (isNull(jzxx_lxzdrq_t, "来现住地日期")) {
            return false;
        }
        if (!FormCheck.check_Date("yyyy-MM-dd", ljDdate, date)) {
            StaticObject.showToast(Rycj_dengji.this, "来京日期不能大于当前日期");
            return false;
        }
        if (!FormCheck.check_Date("yyyy-MM-dd", ljDdate, lxzdDdate)) {
            StaticObject.showToast(Rycj_dengji.this, "来京日期不能大于来现住地日期");
            return false;
        }

        if (isNull(jzxx_ljyy_s, "来京原因")) {
            return false;
        }
        if ("99".equals(jzxx_ljyy_s.getCodeId())) {
            if (isNull(jzxx_ljqtyy_e, "来京其他原因")) {
                return false;
            }
        }
        if (isNull(jzxx_jzlx_s, "居住类型")) {
            return false;
        }
        if (isNull(jzxx_fzxm_e, "房主姓名")) {
            return false;
        }
        String sfzhm = jzxx_fzsfzh_e.getText().toString().trim();
        if (!TextUtils.isEmpty(sfzhm)) {
            String[] id_check = FormCheck.check_Card_ID(sfzhm);
            if (id_check[0].equals("false")) {
                StaticObject.showToast(this, id_check[1]);
                jzxx_fzsfzh_e.requestFocus();
                return false;
            } else {
                jzxx_fzsfzh_e.setText(id_check[1]);
            }
        }
        if (isNullFw(jzxx_xzdz_e, "房屋")) {
            return false;
        }

        return true;
    }

    /**
     * 校验就业工作社保信息
     */
    private boolean checkWorkInfo() {
        if (isNull(jyxx_mqzk_s, "目前状况")) {
            return false;
        }

        if ("01".equals(jyxx_mqzk_s.getCodeId())) {// 就业
            if (isNull(jyxx_dwmc_e, "就业单位名称")) {
                return false;
            }
            if (isNull(jyxx_dwxxdz_e, "就业单位详细地址")) {
                return false;
            }
            if (jyxx_dwhy_csgz_s != null
                    && "".equals(jyxx_dwhy_csgz_s.getSshy())) {
                jyxx_dwhy_csgz_s.requestFocusSshy();
                showToast(this, "请选择所属行业");
                return false;
            }
            if ("Z0000".equals(jyxx_dwhy_csgz_s.getSshy())) {
                if (isNull(jyxx_qthy_e, "其他行业")) {
                    return false;
                }
            }
            if (jyxx_dwhy_csgz_s != null
                    && "".equals(jyxx_dwhy_csgz_s.getCsgz())) {
                jyxx_dwhy_csgz_s.requestFocusCsgz();
                showToast(this, "请选择从事工作");
                return false;
            }
            if (jyxx_dwhy_csgz_s.sfms() && isNull(jyxx_qtgz_e, "其他工作描述")) {
                return false;
            }
            if (isNull(jyxx_sszy_s, "职业")) {
                return false;
            }
            if (isNull(jyxx_sfqdht_s, "是否签订劳动合同")) {
                return false;
            }
            if (isNull(jyxx_dwdz_s, "单位地址")) {
                return false;
            }
            String[] sb = getSbxx();
            if ("".equals(sb[0])) {
                showToast(this, "如果没有在京参加社会保险请勾选”无“!");
                return false;
            }
            if ("".equals(sb[1])) {
                showToast(this, "如果没有参加社会保险请勾选”无“!");
                return false;
            }

        } else if ("02".equals(jyxx_mqzk_s.getCodeId())) {// 学生
            if (isNull(jyxx_xxmc_e, "学校名称")) {
                return false;
            }
            if (isNull(jyxx_xxszd_e, "学校所在地")) {
                return false;
            }
        } else if ("99".equals(jyxx_mqzk_s.getCodeId())) {// 其他
            if (isNull(jyxx_jyxxxx_e, "目前状况详细信息")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取社保状态
     * 在京社保状态及社保状态
     */
    private String[] getSbxx() {

        String zj = "";// 在京社保状态
        String sb = "";// 社保状态
        CheckBox[] zjcb = new CheckBox[]{jyxx_zjsb_w, jyxx_zjsb_yl,
                jyxx_zjsb_sy, jyxx_zjsb_yil, jyxx_zjsb_gs, jyxx_zjsb_shy};
        CheckBox[] sbcb = new CheckBox[]{jyxx_sbxx_w, jyxx_sbxx_yl,
                jyxx_sbxx_sy, jyxx_sbxx_yil, jyxx_sbxx_gs, jyxx_sbxx_shy};
        for (int i = 0; i < zjcb.length; i++) {
            if (zjcb[i].isChecked()) {
                zj += i + ",";
            }
        }
        for (int i = 0; i < sbcb.length; i++) {
            if (sbcb[i].isChecked()) {
                sb += i + ",";
            }
        }
        // 去掉最后","
        if (zj.length() > 0) {
            zj = zj.substring(0, zj.length() - 1);
        }
        if (sb.length() > 0) {
            sb = sb.substring(0, sb.length() - 1);
        }
        return new String[]{zj, sb};
    }

    /**
     * 在京社保状态及社保状态
     */
    private void setSbxx(String[] sbxx) {
        if (sbxx == null || sbxx.length < 2) {
            return;
        }
        CheckBox[] zjcb = new CheckBox[]{jyxx_zjsb_w, jyxx_zjsb_yl,
                jyxx_zjsb_sy, jyxx_zjsb_yil, jyxx_zjsb_gs, jyxx_zjsb_shy};
        CheckBox[] sbcb = new CheckBox[]{jyxx_sbxx_w, jyxx_sbxx_yl,
                jyxx_sbxx_sy, jyxx_sbxx_yil, jyxx_sbxx_gs, jyxx_sbxx_shy};
        if (!"".equals(sbxx[0]) && sbxx[0] != null) {
            String[] index = sbxx[0].split(",");
            if (index != null && index.length > 0) {
                for (int i = 0; i < index.length; i++) {
                    int y = Integer.parseInt(index[i]);
                    zjcb[y].setChecked(true);
                }
            }
        }
        if (!"".equals(sbxx[1]) && sbxx[1] != null) {
            String[] index = sbxx[1].split(",");
            if (index != null && index.length > 0) {
                for (int i = 0; i < index.length; i++) {
                    int y = Integer.parseInt(index[i]);
                    sbcb[y].setChecked(true);
                }
            }
        }
    }

    /**
     * 清空在京社保状态及社保状态
     */
    private void cleanSbxx() {

        CheckBox[] zjcb = new CheckBox[]{jyxx_zjsb_w, jyxx_zjsb_yl,
                jyxx_zjsb_sy, jyxx_zjsb_yil, jyxx_zjsb_gs, jyxx_zjsb_shy};
        CheckBox[] sbcb = new CheckBox[]{jyxx_sbxx_w, jyxx_sbxx_yl,
                jyxx_sbxx_sy, jyxx_sbxx_yil, jyxx_sbxx_gs, jyxx_sbxx_shy};

        for (int i = 0; i < zjcb.length; i++) {
            zjcb[i].setChecked(false);
        }
        for (int i = 0; i < sbcb.length; i++) {
            sbcb[i].setChecked(false);
        }
    }

    /**
     * 几个标题 点击后的反应：1改变下方可见 2改变右侧图标
     */
    private void titleClick(LinearLayout linear, TextView tv, int id) {
        if (linear.getVisibility() == View.VISIBLE) {
            linear.setVisibility(View.GONE);
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources()
                            .getDrawable(id), null,
                    getResources().getDrawable(R.drawable.rykc_input_down),
                    null);
        } else {
            linear.setVisibility(View.VISIBLE);
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources()
                    .getDrawable(id), null, null, null);
        }
    }

    /**
     * 日期选择器
     */
    private void datePick(final TextView tv) {
        Time time = new Time();
        time.setToNow();
        new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                tv.setText(arg1 + "-" + (arg2 + 1) + "-" + arg3);
            }
        }, time.year, time.month, time.monthDay).show();

    }

    /**
     * 获取代码表数据
     */
    private LinkedHashMap<String, String> getMap(String name) {
        String sql = "select CD_ID,CD_NAME from " + name
                + " where CD_AVAILABILITY='1' order by CD_INDEX";
        SqliteCodeTable helper = SqliteCodeTable.getInstance(Rycj_dengji.this);
        Cursor c = helper.Query(sql, null);
        int l = c.getCount();
        LinkedHashMap<String, String> linkedMap = null;
        if (l > 0) {
            linkedMap = new LinkedHashMap<String, String>();
            linkedMap.put("", "请选择");
            while (c.moveToNext()) {
                linkedMap.put(c.getString(c.getColumnIndex("CD_ID")),
                        c.getString(c.getColumnIndex("CD_NAME")));
            }
        }
        c.close();
        helper.close();
        return linkedMap;

    }

    /**
     * 重置数据
     */
    private void resetData() {
        tbrq.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /***************/
        /** 基本信息 **/
        /***************/
        cleanSix();
        jbxx_sjycd_s.setCodeId("");
        jbxx_zzmm_s.setCodeId("");
        jbxx_jzzj_s.setCodeId("");
        jbxx_hjlb_s.setCodeId("");
        jbxx_myjzz_s.setCodeId("");
        jbxx_csd_s.setCodeId("");
        jbxx_hyzk_s.setCodeId("");
        jbxx_hyzm_s.setCodeId("");
        jbxx_lxdh_e.setText("");
        /*************/
        /** 居住信息 **/
        /*************/
        jzxx_ljrq_t.setText("");
        jzxx_lxzdrq_t.setText("");
        jzxx_ljyy_s.setCodeId("");
        jzxx_ljqtyy_e.setText("");
        jzxx_jzlx_s.setCodeId("");
        jzxx_fzxm_e.setText("");
        jzxx_xzdz_e.setText("");
        /*************/
        /** 就业信息 **/
        /*************/
        jyxx_mqzk_s.setCodeId("");
        jyxx_dwmc_e.setText("");
        jyxx_dwxxdz_e.setText("");
        jyxx_dwhy_csgz_s.setSshyId("");
        jyxx_dwhy_csgz_s.setCsgzId("");
        jyxx_qthy_e.setText("");
        jyxx_qtgz_e.setText("");
        jyxx_sszy_s.setCodeId("");
        jyxx_sfqdht_s.setCodeId("");
        jyxx_dwdz_s.setCodeId("");
        cleanSbxx();
        jyxx_xxmc_e.setText("");
        jyxx_xxszd_e.setText("");
        jyxx_jyxxxx_e.setText("");
        /*************/
        /** 备注信息 **/
        /*************/
        initShow();
        initData();

    }

    /**
     * 操作成功
     */
    private void sucess() {
        resetData();
        if (cleanZcInfo()) {
            StaticObject.showToast(Rycj_dengji.this, "保存成功！");
            Rycj_dengji.this.finish();
        } else {
            StaticObject.showToast(Rycj_dengji.this, "保存成功！");
            Rycj_dengji.this.finish();
        }
    }

//    /**
//     * 清空人员身份证数据
//     */
//    private void resetRyBase() {
//        new Builder(this).setTitle("提示")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setMessage("清空人员基本6项信息，重新输入？").setNegativeButton("否", null)
//                .setPositiveButton("是", new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        cleanSix();
//                    }
//
//                }).show();
//    }

    /**
     * 清除人员6项
     */
    private void cleanSix() {
        jbxx_xm_e.setText("");
        jbxx_sfzh_e.setText("");
        jbxx_csrq_e.setText("");
        jbxx_xb_t.setText("");
        jbxx_mz_s.setClickable(true);
        jbxx_hjxxdz_e.setText("");
        jbxx_photo_i.setImageBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.rykc_touxiang));
        // 删除这张无用的临时照片
        StaticObject.deletes(jbxx_photoPath, "sfz_tmp");
        jbxx_photoPath = "";
        jbxx_hjdz_s.setCodeId("");
        StaticObject.Unlock(jbxx_xm_e);
        StaticObject.Unlock(jbxx_sfzh_e);
        jbxx_xm_e.setTextColor(this.getResources().getColor(R.color.black));
        jbxx_sfzh_e.setTextColor(this.getResources().getColor(R.color.black));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            fwxx = data.getStringArrayExtra("fw");
            for (int i = 0; i < fwxx.length; i++) {
                if (fwxx[i] == null) {
                    fwxx[i] = "";
                }
            }
            jzxx_jzlx_s.setCodeId(fwxx[5].trim());
            jzxx_fzxm_e.setText(fwxx[1].trim());
            jzxx_fzsfzh_e.setText(fwxx[2].trim());
            jzxx_xzdz_e.setText(fwxx[6].trim());
            StaticObject.lock(jzxx_xzdz_e);
            jzxx_xzdz_e.setTextColor(Rycj_dengji.this.getResources().getColor(R.color.gray));
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            setImage(photoFile, ll_sfzmcl, sfzmclList, sfzmclPathList);
            StaticObject.print("sfzmclPathList: " + sfzmclPathList.size());
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            setImage(photoFile, ll_zjzscl, zjzsclList, zjzsclPathList);
            StaticObject.print("zjzsclList: " + zjzsclList.size());

        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {

            setImage(photoFile, ll_zzrkdjb, zzrkdjbList, zzrkdjbPathList);
            StaticObject.print("zzrkdjbList: " + zzrkdjbList.size());

        }

    }

    private void setImage(File file, final LinearLayout ll, final ArrayList<ImageView> arrayList, final ArrayList<String> pathList) {
        try {
            if (file.exists()) {
                ll.setVisibility(View.VISIBLE);
                ImageView img = new ImageView(Rycj_dengji.this);
                arrayList.add(img);
                pathList.add(file.getAbsolutePath());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 0, 0);
                img.setLayoutParams(layoutParams);
                img.setImageBitmap(new PicDispose(this)
                        .CompressPicFileBySize(file, 100));
                addView(ll, arrayList);
                for (String path : pathList) {
                    StaticObject.print(path);
                }
                StaticObject.print("图片路径:" + file.getAbsolutePath());
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Builder builder = new Builder(Rycj_dengji.this);
                        builder.setTitle("图片删除");
                        builder.setMessage("是否删除图片?");
                        builder.setPositiveButton("是", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                int i = arrayList.indexOf(view);
                                StaticObject.print("delete photo->" + i);
                                arrayList.remove(i);
                                pathList.remove(i);
                                if (arrayList.size() == 0) {
                                    ll.setVisibility(View.GONE);
                                }
                                for (String path : pathList) {
                                    StaticObject.print(path);
                                }
                                addView(ll, arrayList);
                            }

                        });
                        builder.setNegativeButton("否", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();

                    }
                });
            } else {
                ll.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addView(LinearLayout ll, ArrayList<ImageView> arrayList) {
        ll.removeAllViewsInLayout();
        for (int i = 0; i < arrayList.size(); i++) {
            ll.addView(arrayList.get(i));
        }
    }


    /**
     * 设置组件显隐关系
     */
    private void initShow() {
        // 居住信息
        jzxx_qtljyy_l.setVisibility(View.GONE);
        // 目前就业信息
        jyxx_mqzk_jy_l.setVisibility(View.GONE);
        jyxx_mqzk_qt_l.setVisibility(View.GONE);
        jyxx_mqzk_xs_l.setVisibility(View.GONE);
        jyxx_jy_qtsshy_l.setVisibility(View.GONE);
        jyxx_jy_qtgz_l.setVisibility(View.GONE);

    }

    private void setLianDong() {
        handler.sendEmptyMessage(12);
        handler.sendEmptyMessage(21);
        handler.sendEmptyMessage(22);
        handler.sendEmptyMessage(23);
        handler.sendEmptyMessage(32);
        handler.sendEmptyMessage(41);
    }

    /**
     * 回显人员暂存信息
     */
    private void setData(RyzcVo vo) {
        initShow();
        setLianDong();
        if (vo.getTbrq().contains("-")) {
            tbrq.setText(vo.getTbrq());
        } else {
            tbrq.setText(FormCheck.getDate1(vo.getTbrq()));
        }

        fwz = vo.getFwz();
        xq = vo.getXq();
        gly = vo.getGly();
        if (fwz != null) {
            tvSsfwz.setText(fwz.split(",")[0]);
        } else {
            tvSsfwz.setText("");
        }
        if (xq != null) {
            tvSxxq.setText(xq.split(",")[0]);
        } else {
            tvSxxq.setText("");
        }
        if (gly != null) {
            tvGly.setText(gly.split(",")[0]);
        } else {
            tvGly.setText("");
        }

//        fwzmap = vo.getFwzMap();
//        xqmap = vo.getXqMap();
//        glyMap = vo.getGlyMap();

//        jdxx_fwz_s.setCodeId(vo.getFwz());
//        xqS.setCodeId(vo.getXq());
//        glyS.setCodeId(vo.getGly());

        /***************/
        /** 基本信息 **/
        /***************/

        jbxx_photoPath = vo.getJb_photoPath();
        jbxx_xm_e.setText(vo.getJb_xm());
        jbxx_sfzh_e.setText(vo.getJb_sfz());
        jbxx_csrq_e.setText(vo.getJb_csrq());
        jbxx_xb_t.setText(vo.getJb_xb());
        jbxx_mz_s.setCodeId(vo.getJb_mz());
        jbxx_hjxxdz_e.setText(vo.getJb_hjxxdz());
        // 照片这得校验存在不存在，不存在就拉倒，存在才能显示
        if (!"".equals(vo.getJb_photoPath())) {
            File f = new File(jbxx_photoPath);
            if (f.exists()) {
                jbxx_photo_i.setImageBitmap(BitmapFactory
                        .decodeFile(jbxx_photoPath));
            }
        }
        jbxx_myjzz_s.setCodeId(vo.getJb_myjzz());
        // 免疫
        if (!FormCheck.age_before(vo.getJb_sfz())) {
            jbxx_myjzz_s.setCodeId("0");
            jbxx_myjzz_s.setClickable(false);
        } else {
            jbxx_myjzz_s.setClickable(true);
        }
        lockbaseSix();
        jbxx_hjdz_s.setCodeId(vo.getJb_hjdz());
        //受教育信息
        String jb_sjycd = vo.getJb_sjycd();
        System.out.println(jb_sjycd);
        jbxx_sjycd_s.setCodeId(vo.getJb_sjycd());
        jbxx_zzmm_s.setCodeId(vo.getJb_zzmm());
        jbxx_jzzj_s.setCodeId(vo.getJb_jzzj());
        jbxx_hjlb_s.setCodeId(vo.getJb_hjlb());

        jbxx_csd_s.setCodeId(vo.getJb_csd());
        jbxx_hyzk_s.setCodeId(vo.getJb_hyzk());
        jbxx_hyzm_s.setCodeId(vo.getJb_hyzm());
        jbxx_lxdh_e.setText(vo.getJb_lxdh());
        /*************/
        /** 居住信息 **/
        /*************/
        jzxx_ljrq_t.setText(vo.getJz_ljrq());
        jzxx_lxzdrq_t.setText(vo.getJz_lxzdrq());
        jzxx_ljyy_s.setCodeId(vo.getJz_ljyy());
        jzxx_ljqtyy_e.setText(vo.getJz_ljqtyy());
        jzxx_jzlx_s.setCodeId(vo.getJz_jzlx());
        jzxx_fzxm_e.setText(vo.getJz_fzxm());
        jzxx_fzsfzh_e.setText(vo.getJz_fzsfzh());
        jzxx_xzdz_e.setText(vo.getJz_xzdz());
        fwxx = vo.getFwxx();
        pcsdm = vo.getSspcs();
        /*************/
        /** 就业信息 **/
        /*************/
        if (vo.getJy_mqzk() != null) {
            jyxx_mqzk_s.setCodeId(vo.getJy_mqzk());
        }
        jyxx_dwmc_e.setText(vo.getJy_jydwmc());
        jyxx_dwxxdz_e.setText(vo.getJy_dwxxdz());
        jyxx_dwhy_csgz_s.setSshyId(vo.getJy_dwsshy());
        jyxx_dwhy_csgz_s.setCsgzId(vo.getJy_csgz());
        jyxx_qthy_e.setText(vo.getJy_dwqtsshy());
        jyxx_qtgz_e.setText(vo.getJy_qtcsgz());
        jyxx_sszy_s.setCodeId(vo.getJy_zy());
        jyxx_sfqdht_s.setCodeId(vo.getJy_qdldht());
        jyxx_dwdz_s.setCodeId(vo.getJy_dwdz());
        setSbxx(new String[]{vo.getJy_zjcjsb(), vo.getJy_cjsb()});
        jyxx_xxmc_e.setText(vo.getJy_xxmc());
        jyxx_xxszd_e.setText(vo.getJy_xxszd());
        jyxx_jyxxxx_e.setText(vo.getJy_xxxx());
        /*************/
        /** 证明材料信息 **/
        /*************/
        ArrayList<String> sfzmclphtoPathList = new ArrayList<String>();
        ArrayList<String> zjzsclphtoPathList = new ArrayList<String>();
        ArrayList<String> zzrkdjbphtoPathList = new ArrayList<String>();
        String[] photos = vo.getZmcl().split(";");
        for (int i = 0; i < photos.length; i++) {
            if (photos[i].contains(",01")) {
                String[] photopath1 = photos[i].split(",");
                sfzmclphtoPathList.add(photopath1[0]);
            }
            if (photos[i].contains(",02")) {
                String[] photopath2 = photos[i].split(",");
                zjzsclphtoPathList.add(photopath2[0]);
            }
            if (photos[i].contains(",03")) {
                String[] photopath3 = photos[i].split(",");
                zzrkdjbphtoPathList.add(photopath3[0]);
            }
        }
        for (String path : sfzmclphtoPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "身份证明材料缺失");
            } else {
                setImage(file, ll_sfzmcl, sfzmclList, sfzmclPathList);
            }
        }
        for (String path : zjzsclphtoPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "在京住所证明材料缺失");
            } else {
                setImage(file, ll_zjzscl, zjzsclList, zjzsclPathList);
            }
        }
        for (String path : zzrkdjbphtoPathList) {
            File file = new File(path);
            if (!file.exists()) {
                StaticObject.showToast(Rycj_dengji.this, "暂住人口登记表材料缺失");
            } else {
                setImage(file, ll_zzrkdjb, zzrkdjbList, zzrkdjbPathList);
            }
        }
    }


    /**
     * 就业社保参加社保情况联动效果
     */
    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        arg0.setChecked(arg1);
        if (arg1) {
            switch (arg0.getId()) {
                case R.id.dengji_cb_j_wu:
                    jyxx_zjsb_yl.setChecked(false);
                    jyxx_zjsb_sy.setChecked(false);
                    jyxx_zjsb_yil.setChecked(false);
                    jyxx_zjsb_shy.setChecked(false);
                    jyxx_zjsb_gs.setChecked(false);
                    break;
                case R.id.dengji_cb_j_yanglao:
                    jyxx_zjsb_w.setChecked(false);
                    jyxx_sbxx_w.setChecked(false);
                    jyxx_sbxx_yl.setChecked(true);
                    break;
                case R.id.dengji_cb_j_shiye:
                    jyxx_zjsb_w.setChecked(false);
                    jyxx_sbxx_w.setChecked(false);
                    jyxx_sbxx_sy.setChecked(true);
                    break;
                case R.id.dengji_cb_j_yiliao:
                    jyxx_zjsb_w.setChecked(false);
                    jyxx_sbxx_w.setChecked(false);
                    jyxx_sbxx_yil.setChecked(true);
                    break;
                case R.id.dengji_cb_j_gongshang:
                    jyxx_zjsb_w.setChecked(false);
                    jyxx_sbxx_w.setChecked(false);
                    jyxx_sbxx_gs.setChecked(true);
                    break;
                case R.id.dengji_cb_j_shengyu:
                    jyxx_zjsb_w.setChecked(false);
                    jyxx_sbxx_w.setChecked(false);
                    jyxx_sbxx_shy.setChecked(true);
                    break;
                case R.id.dengji_cb_2_wu:
                    jyxx_zjsb_w.setChecked(true);
                    jyxx_zjsb_yl.setChecked(false);
                    jyxx_zjsb_sy.setChecked(false);
                    jyxx_zjsb_yil.setChecked(false);
                    jyxx_zjsb_shy.setChecked(false);
                    jyxx_zjsb_gs.setChecked(false);
                    jyxx_sbxx_yl.setChecked(false);
                    jyxx_sbxx_sy.setChecked(false);
                    jyxx_sbxx_yil.setChecked(false);
                    jyxx_sbxx_shy.setChecked(false);
                    jyxx_sbxx_gs.setChecked(false);
                    break;
                default:
                    if (arg0.getId() != R.id.dengji_cb_2_wu) {
                        jyxx_sbxx_w.setChecked(false);
                    }
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 人员6项校验(其实是4位，出生日期及性别不校验)
     */
    private boolean checkSix() {
        if (isNull(jbxx_xm_e, "姓名")) {
            return false;
        }
        if (isNull(jbxx_sfzh_e, "身份证号码")) {
            return false;
        }
        String[] id_check = FormCheck.check_Card_ID(jbxx_sfzh_e.getText()
                .toString().trim());
        if (id_check[0].equals("false")) {
            showToast(this, id_check[1]);
            jbxx_sfzh_e.requestFocus();
            return false;
        } else {
            jbxx_sfzh_e.setText(id_check[1]);
            jbxx_csrq_e.setText(FormCheck.getBirthday(id_check[1]));
            jbxx_xb_t.setText(FormCheck.getSex(id_check[1]));
        }
        if (isNull(jbxx_mz_s, "民族")) {
            return false;
        }
        if (isNull(jbxx_hjxxdz_e, "户籍详细地址")) {
            return false;
        }
        if (jbxx_photoPath == null || jbxx_photoPath.equals("")) {
            showToast(this, "请确定身份证照片不能为空");
            return false;
        }

        return true;
    }

    /**
     * 校验EditText表单内容不为null和""
     */
    private boolean isNull(EditText view, String name) {
        if (view != null && "".equals(view.getText().toString().trim())) {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            showToast(this, "请填写" + name);
            return true;
        }
        return false;
    }

    private boolean isNullFw(EditText view, String name) {
        if (view != null && "".equals(view.getText().toString().trim())) {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            showToast(this, "请选择" + name);
            return true;
        }
        return false;
    }

    /**
     * 校验TextView表单内容不为null和""
     */
    private boolean isNull(TextView view, String name) {
        if (view != null && "".equals(view.getText().toString().trim())) {
            datePick(view);
            showToast(this, "请选择" + name);
            return true;
        }
        return false;
    }

    /**
     * 校验SelectViewAndHandlerAndMsg表单内容不为null和""
     */
    private boolean isNull(SelectViewAndHandlerAndMsg select, String name) {
        if (select != null
                && ("".equals(select.getCodeId()) || select.getCodeId() == null)) {
            select.requestFocus();
            showToast(this, "请选择" + name);
            return true;
        }
        return false;
    }

    HttpCallBack<String> djksl = new HttpCallBack<String>(Rycj_dengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 201) {
                sucess();
            }
//            else if (jsonResult.getReturnCode() == 202) {
//                jbxx_warn.setVisibility(View.VISIBLE);
//            }
            else {
                showToast(Rycj_dengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    private void ryxc() {
        DjkSl djkSl = new DjkSl();
        if (ryzcData != null && !"".equals(ryzcData)) {
            //TODO  暂存数据受理
            String codeId = gly.split(",")[1];
            djkSl.setGlybm(codeId);
            String codeId2 = xq.split(",")[1];
            djkSl.setRzf_ssxqbh(codeId2);

            djkSl.setRzf_xzdxzqh(xq.split(",")[2]);
        } else {
            //TODO  正常受理
            String codeId = glyS.getCodeId();
            djkSl.setGlybm(codeId);
            String codeId2 = xqS.getCodeId();
            djkSl.setRzf_ssxqbh(codeId2);

            String xzqh = xzqhmap.get(xqS.getCodeId());
            djkSl.setRzf_xzdxzqh(xzqh);
        }

        djkSl.setBip_xm(jbxx_xm_e.getText().toString().trim());
        djkSl.setBip_xb("男".equals(jbxx_xb_t.getText().toString().trim()) ? "1" : "2");
        djkSl.setBip_sfzhm(jbxx_sfzh_e.getText().toString().trim());
        djkSl.setBip_nation(jbxx_mz_s.getCodeId());
        djkSl.setBip_yjxzqh(jbxx_hjdz_s.getCodeId());
        djkSl.setBip_rpr_address(jbxx_hjxxdz_e.getText().toString().trim());
        djkSl.setBip_politicsaspect(jbxx_zzmm_s.getCodeId());
        djkSl.setBip_educationallevel(jbxx_sjycd_s.getCodeId());
        djkSl.setBip_rpr_type(jbxx_hjlb_s.getCodeId());
        djkSl.setBip_marriage(jbxx_hyzk_s.getCodeId());
        djkSl.setBip_con_telephone(jbxx_lxdh_e.getText().toString().trim());
        djkSl.setRdj_csd(jbxx_csd_s.getCodeId());
        djkSl.setRdj_zzzjyw(jbxx_jzzj_s.getCodeId());
        djkSl.setRdj_hyzmyw(jbxx_hyzm_s.getCodeId());
        djkSl.setRdj_ywjzjl(jbxx_myjzz_s.getCodeId());
        djkSl.setRdj_ljrq(new StringBuilder().append(FormCheck.getDate(jzxx_ljrq_t.getText().toString().trim())).append("000000").toString());
        djkSl.setRzf_lxzdrq(new StringBuilder().append(FormCheck.getDate(jzxx_lxzdrq_t.getText().toString().trim())).append("000000").toString());
        djkSl.setRdj_ljyy(jzxx_ljyy_s.getCodeId());
        if (jzxx_ljyy_s.getCodeId().equals("99")) {
            djkSl.setRdj_ljqtyy(jzxx_ljqtyy_e.getText().toString().trim());
        }
        djkSl.setRdj_mqzk(jyxx_mqzk_s.getCodeId());
        // 就业
        if ("01".equals(jyxx_mqzk_s.getCodeId())) {
            djkSl.setRjy_jydwmc(jyxx_dwmc_e.getText().toString().trim());
            djkSl.setRjy_jydwxxdz(jyxx_dwxxdz_e.getText().toString().trim());
            djkSl.setRjy_jydwszqx(jyxx_dwdz_s.getCodeId());
            djkSl.setRjy_jydwhy(jyxx_dwhy_csgz_s.getSshy());
            if ("Z0000".equals(jyxx_dwhy_csgz_s.getSshy())) {
                djkSl.setRjy_jydwqthy(jyxx_qthy_e.getText().toString().trim());
            }
            djkSl.setRjy_zycsgz(jyxx_dwhy_csgz_s.getCsgz());
            if (jyxx_dwhy_csgz_s.sfms() && !isNull(jyxx_qtgz_e, "其他工作")) {
                djkSl.setRdj_zycsqtgz(jyxx_qtgz_e.getText().toString().trim());
            }
            djkSl.setRjy_bczylb(jyxx_sszy_s.getCodeId());
            djkSl.setRjy_sfqdldht(jyxx_sfqdht_s.getCodeId());
            String[] sb = getSbxx();
            djkSl.setRjy_zjjnshbxlb(sb[0]);
            djkSl.setRjy_syjnshbxlb(sb[1]);
            // 学生
        } else if ("02".equals(jyxx_mqzk_s.getCodeId())) {
            djkSl.setRjy_jydwmc(jyxx_xxmc_e.getText().toString().trim());
            djkSl.setRjy_jydwxxdz(jyxx_xxszd_e.getText().toString().trim());
            // 其他
        } else if ("99".equals(jyxx_mqzk_s.getCodeId())) {
            djkSl.setRdj_mqzkqtxx(jyxx_jyxxxx_e.getText().toString().trim());
        }
        djkSl.setRdj_djrq(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // 照片
        djkSl.setZzdjzp_zplj(imageToBase64(jbxx_photoPath));
        //证明材料
        String zmcl = "";
        String zmcl_suffix = "";
        String zmcl_lx = "";
        for (String path : sfzmclPathList) {
            zmcl += (imageToBase64(path) + ",01;");
            zmcl_suffix += "jpg;";
            zmcl_lx = zmcl_lx.contains("01") ? zmcl_lx : zmcl_lx + "01;";
        }
        for (String path : zjzsclPathList) {
            zmcl += (imageToBase64(path) + ",02;");
            zmcl_suffix += "jpg;";
            zmcl_lx = zmcl_lx.contains("02") ? zmcl_lx : zmcl_lx + "02;";
        }
        for (String path : zzrkdjbPathList) {
            zmcl += (imageToBase64(path) + ",03;");
            zmcl_suffix += "jpg;";
            zmcl_lx = zmcl_lx.contains("03") ? zmcl_lx : zmcl_lx + "03;";
        }
        djkSl.setZmcl(zmcl);
        djkSl.setZmcl_suffix(zmcl_suffix);
        djkSl.setZmcl_lx(zmcl_lx);
        //房屋返回信息
        djkSl.setRzf_zslx(fwxx[5].trim());
//        djkSl.setRzf_xzdxzqh(fwxx[7].trim());
        djkSl.setRzf_xzdxxdz(fwxx[6].trim());
        djkSl.setRzf_fzxm(fwxx[1].trim());
        djkSl.setRzf_fzsfz(fwxx[2].trim());
        djkSl.setRzf_fzxzd(fwxx[3].trim());
        djkSl.setRzf_lxdh(fwxx[4].trim());
        djkSl.setRzf_fwszddz(fwxx[6].trim());
        if (!TextUtils.isEmpty(pcsdm)) {
            djkSl.setRzf_sspcsdm(pcsdm.trim());
        }

        String codeId1 = jdxx_fwz_s.getCodeId();
        djkSl.setRzf_fwzbh(codeId1);


        String json = djksl.gson.toJson(djkSl);
        djksl.showDialog("数据请求中，所用时间较长，请勿取消并耐心等待...");
        HttpRequest.POST(Rycj_dengji.this, HttpRequest.DJKSL, json, djksl);

    }

    private HttpCallBack<String> ryhc = new HttpCallBack<String>(Rycj_dengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            showToast(Rycj_dengji.this, jsonResult.getReturnMsg());
//            if (jsonResult.getReturnCode() == 202) {
//                jbxx_warn.setVisibility(View.VISIBLE);
//            } else {
//
//            }
        }
    };

    /**
     * 人员核实
     */
    private void ryhs() {
        if (isNull(jbxx_sfzh_e, "身份证号码")) {
            return;
        }
        String[] id_check = FormCheck.check_Card_ID(jbxx_sfzh_e.getText()
                .toString().trim());
        if (id_check[0].equals("false")) {
            showToast(this, id_check[1]);
            jbxx_sfzh_e.requestFocus();
            return;
        } else {
            jbxx_sfzh_e.setText(id_check[1]);
            jbxx_csrq_e.setText(FormCheck.getBirthday(id_check[1]));
            jbxx_xb_t.setText(FormCheck.getSex(id_check[1]));
        }
        String json = "{'bip_xm':'" + jbxx_xm_e.getText().toString().trim() + "','bip_sfzhm':'" + id_check[1] + "'}";
        ryhc.showDialog("请求中...");
        HttpRequest.POST(Rycj_dengji.this, HttpRequest.BKHS, json, ryhc);
        readCardControler.read();

    }

    @Override
    protected void onStart() {
        readCardControler.read();
        super.onStart();
    }


    /**
     * 人员暂存  99999
     */
    private void ryzc() {
        if (isNull(jbxx_xm_e, "姓名")) {
            return;
        }
        if (isNull(jbxx_sfzh_e, "身份证号码")) {
            return;
        }
        if (!checkphotoInfo()) {
            return;
        }
        String[] id_check = FormCheck.check_Card_ID(jbxx_sfzh_e.getText()
                .toString().trim());
        if (id_check[0].equals("false")) {
            showToast(this, id_check[1]);
            jbxx_sfzh_e.requestFocus();
            return;
        } else {
            jbxx_sfzh_e.setText(id_check[1]);
            jbxx_csrq_e.setText(FormCheck.getBirthday(id_check[1]));
            jbxx_xb_t.setText(FormCheck.getSex(id_check[1]));
        }
        RyzcVo vo = setRyzcVo();
        final StringBuilder vo_sb = new StringBuilder();
        vo_sb.append(new Gson().toJson(vo));
        final String id = vo.getJb_sfz() + vo.getJb_xm();
        final String tjsj = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        SqliteHelper helper = SqliteHelper.getInstance(Rycj_dengji.this);
        // 以身份证+姓名为主键
        String sql = "select id from ryxxzcb  where id='" + id + "'";
        Cursor c = helper.Query(sql, null);
        int l = c.getCount();
        c.close();
        helper.close();
        if (l > 0) {// 已存在
            new Builder(this)
                    .setTitle("注意")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(
                            "姓名：" + vo.getJb_xm() + "\n身份证号：" + vo.getJb_sfz()
                                    + "\n该人员已被暂存，是否覆盖原暂存信息？")
                    .setNegativeButton("否", null)
                    .setPositiveButton("是", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            SqliteHelper helper = SqliteHelper
                                    .getInstance(Rycj_dengji.this);
                            String updateSql = "update ryxxzcb set json='"
                                    + vo_sb.toString() + "',tjsj='" + tjsj
                                    + "',glybm='" + userData.getU_id() + "' where id='" + id + "' ";
                            helper.execSQL(updateSql, null);
                            helper.close();
                            showToast(Rycj_dengji.this, "人员暂存修改成功");
                            resetData();

                        }
                    }).show();
        } else {// 不存在
            helper = SqliteHelper.getInstance(Rycj_dengji.this);
            // 以身份证+姓名为主键
            String insertSql = "insert into ryxxzcb(id,json,tjsj,glybm) values('"
                    + id + "','" + vo_sb.toString() + "','" + tjsj + "','" + userData.getU_id() + "')";
            helper.execSQL(insertSql, null);
            String selectsql = "select*from ryxxzcb";
            Cursor cursor = helper.Query(selectsql, null);
            int num = cursor.getCount();
            StaticObject.print("num: " + num);
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String json = cursor.getString(1);
                StaticObject.print("num: " + num + "name: " + name + "json: " + json);
            }
            helper.close();
            showToast(Rycj_dengji.this, "人员暂存新增成功");
            resetData();

        }
    }

    /**
     * 将页面保存到RyzcVo中
     */
    private RyzcVo setRyzcVo() {
        RyzcVo vo = new RyzcVo();

        //TODO 保存服务站名字和编号
        //TODO 保存辖区名字 辖区编号 行政区划
        //TODO 保存管理员名字 编号

        String s = tvSsfwz.getText().toString().trim() + "," + jdxx_fwz_s.getCodeId();
        String s1;
        if (xqS != null) {
            s1 = tvSxxq.getText().toString().trim() + "," + xqS.getCodeId() + "," + xzqhmap.get(xqS.getCodeId());
        } else {
            s1 = tvSxxq.getText().toString().trim() + " , , ";
        }

        String s2;
        if (glyS != null) {
            s2 = tvGly.getText().toString().trim() + "," + glyS.getCodeId();
        } else {
            s2 = tvGly.getText().toString().trim() + ", ";
        }

        vo.setFwz(s);
        vo.setXq(s1);
        vo.setGly(s2);

        vo.setTbrq(tbrq.getText().toString().trim());
        /***************/
        /** 基本信息 **/
        /***************/
        vo.setJb_xm(jbxx_xm_e.getText().toString().trim());
        vo.setJb_mz(jbxx_mz_s.getCodeId());
        vo.setJb_sfz(jbxx_sfzh_e.getText().toString().trim());
        vo.setJb_csrq(jbxx_csrq_e.getText().toString().trim());
        vo.setJb_xb(jbxx_xb_t.getText().toString().trim());
        vo.setJb_hjdz(jbxx_hjdz_s.getCodeId());
        vo.setJb_hjxxdz(jbxx_hjxxdz_e.getText().toString().trim());
        String codeId = jbxx_sjycd_s.getCodeId();
        vo.setJb_sjycd(codeId);
        vo.setJb_zzmm(jbxx_zzmm_s.getCodeId());
        vo.setJb_jzzj(jbxx_jzzj_s.getCodeId());
        vo.setJb_hjlb(jbxx_hjlb_s.getCodeId());
        vo.setJb_myjzz(jbxx_myjzz_s.getCodeId());
        vo.setJb_csd(jbxx_csd_s.getCodeId());
        vo.setJb_hyzk(jbxx_hyzk_s.getCodeId());
        vo.setJb_hyzm(jbxx_hyzm_s.getCodeId());
        vo.setJb_lxdh(jbxx_lxdh_e.getText().toString().trim());
        vo.setJb_photoPath(jbxx_photoPath);
        /*************/
        /** 居住信息 **/
        /*************/
        vo.setJz_ljrq(jzxx_ljrq_t.getText().toString().trim());
        vo.setJz_lxzdrq(jzxx_lxzdrq_t.getText().toString().trim());
        vo.setJz_ljyy(jzxx_ljyy_s.getCodeId());
        vo.setJz_ljqtyy(jzxx_ljqtyy_e.getText().toString().trim());
        vo.setJz_jzlx(jzxx_jzlx_s.getCodeId());
        vo.setJz_fzxm(jzxx_fzxm_e.getText().toString().trim());
        vo.setJz_fzsfzh(jzxx_fzsfzh_e.getText().toString().trim());
        vo.setJz_xzdz(jzxx_xzdz_e.getText().toString().trim());
        vo.setFwxx(fwxx);
        vo.setSspcs(pcsdm);
        /*************/
        /** 就业信息 **/
        /*************/
        vo.setJy_mqzk(jyxx_mqzk_s.getCodeId());
        vo.setJy_jydwmc(jyxx_dwmc_e.getText().toString().trim());
        vo.setJy_dwxxdz(jyxx_dwxxdz_e.getText().toString().trim());
        vo.setJy_dwsshy(jyxx_dwhy_csgz_s.getSshy());
        vo.setJy_csgz(jyxx_dwhy_csgz_s.getCsgz());
        vo.setJy_dwqtsshy(jyxx_qthy_e.getText().toString().trim());
        vo.setJy_qtcsgz(jyxx_qtgz_e.getText().toString().trim());
        vo.setJy_zy(jyxx_sszy_s.getCodeId());
        vo.setJy_qdldht(jyxx_sfqdht_s.getCodeId());
        vo.setJy_dwdz(jyxx_dwdz_s.getCodeId());
        String[] sb = this.getSbxx();
        vo.setJy_zjcjsb(sb[0]);
        vo.setJy_cjsb(sb[1]);
        vo.setJy_xxmc(jyxx_xxmc_e.getText().toString().trim());
        vo.setJy_xxszd(jyxx_xxszd_e.getText().toString().trim());
        vo.setJy_xxxx(jyxx_jyxxxx_e.getText().toString().trim());

        /*************/
        /** 证明材料信息 **/
        /*************/
        String zmcl = "";
        for (String path : sfzmclPathList) {
            zmcl += (path + ",01;");
        }
        for (String path : zjzsclPathList) {
            zmcl += (path + ",02;");
        }
        for (String path : zzrkdjbPathList) {
            zmcl += (path + ",03;");
        }
        vo.setZmcl(zmcl);

        /*************/
        /** 管理员信息 **/
        /*************/
        vo.setTbr(preference.getString("login_admin_id", ""));
        vo.setGlybm(preference.getString("login_number", ""));
        String login_admin_name = preference.getString("login_admin_name", "");
        vo.setGlyxm(login_admin_name);

//        vo.setFwzMap(fwzmap);
//        vo.setXqMap(xqmap);
//        vo.setGlyMap(glyMap);
        return vo;
    }

    private void cxXqAndGly(String id) {
        //查询辖区
        String json = "{'id':'" + id + "'}";
        cxXqAndGlyCallBack.showDialog("请求中...");
        HttpRequest.POST(Rycj_dengji.this, HttpRequest.SSXQCX, json, cxXqAndGlyCallBack);
        //查询管理员
        GlyCode glyCode = new GlyCode();
        glyCode.setSsfwzbh(id);
        Gson gson = new GsonBuilder().create();
        HttpRequest.POST(Rycj_dengji.this, HttpRequest.GLY, gson.toJson(glyCode), glycx);

    }

    //所属辖区
    private HttpCallBack<String> cxXqAndGlyCallBack = new HttpCallBack<String>(Rycj_dengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);

            if (jsonResult.getReturnCode() == 200) {
                xqmap = new LinkedHashMap<String, String>();
                xzqhmap = new LinkedHashMap<String, String>();
                ArrayList<XqCode> xqData = gson.fromJson(gson.toJson(jsonResult.getReturnData()), new TypeToken<ArrayList<XqCode>>() {
                }.getType());
                for (int i = 0; i < xqData.size(); i++) {
                    xqmap.put(xqData.get(i).getId(), xqData.get(i).getName());
                    xzqhmap.put(xqData.get(i).getId(), xqData.get(i).getXzqh());
                }
                xqS = new SelectViewAndHandlerAndMsg(Rycj_dengji.this, "所属辖区", xqmap, tvSxxq, handler, 11, xqData.get(0).getId());

            } else {
                showToast(Rycj_dengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    //管理员
    private HttpCallBack<String> glycx = new HttpCallBack<String>(Rycj_dengji.this) {
        private String glybmMR;

        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 200) {
                String str = gson.toJson(jsonResult.getReturnData());
                glyMap = new LinkedHashMap<String, String>();
                try {
                    JSONArray array = new JSONArray(str);
                    for (int i = 0; i < array.length(); i++) {
                        if (i == 0) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            //第一个显示
                            glybmMR = jsonObject.getString("glybm");
                        }
                        JSONObject jsonObject = array.getJSONObject(i);
                        String glybm = jsonObject.getString("glybm");
                        String glyxm = jsonObject.getString("glyxm");
                        glyMap.put(glybm, glyxm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                glyS = new SelectViewAndHandlerAndMsg(Rycj_dengji.this, "管理员", glyMap, tvGly, handler, 11, glybmMR);

            } else {
                showToast(Rycj_dengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    /**
     * 初始化界面数据及所有select框
     * q 暂存为0  正常受理为1
     */
    private void init() {
        tbrq.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        LinkedHashMap<String, String> youwuMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> shifouMap = new LinkedHashMap<String, String>();
        preference = getSharedPreferences(StaticObject.SHAREPREFERENC,
                Activity.MODE_PRIVATE);
        youwuMap.put("", "请选择");
        youwuMap.put("1", "有");
        youwuMap.put("0", "无");
        shifouMap.put("", "请选择");
        shifouMap.put("1", "是");
        shifouMap.put("0", "否");

        ArrayList<CodeTable> fwz = userData.getFwz();
        fwzmap = new LinkedHashMap<String, String>();
        for (int i = 0; i < fwz.size(); i++) {
            fwzmap.put(fwz.get(i).getId(), fwz.get(i).getName());
        }
        String id = fwz.get(0).getId();
        jdxx_fwz_s = new SelectViewAndHandlerAndMsg(this, "所属服务站", fwzmap, tvSsfwz, handler, 56, id);
//        cxXqAndGly(id);


        jbxx_csd_s = new SelectViewAndHandlerAndMsg(this, "出生地",
                getMap("SJCJ_D_CSD"), jbxx_csd_t, handler, 11, "02");
        jbxx_sjycd_s = new SelectViewAndHandlerAndMsg(this, "受教育程度",
                getMap("CDG_EDUCATIONALLEVEL"), jbxx_sjycd_t, handler, 11, "");
        jbxx_hyzk_s = new SelectViewAndHandlerAndMsg(this, "婚姻状况",
                getMap("CDG_MARRIAGE"), jbxx_hyzk_t, handler, 12, "");
        jbxx_zzmm_s = new SelectViewAndHandlerAndMsg(this, "政治面貌",
                getMap("CDG_POLITICSASPECT"), jbxx_zzmm_t, handler, 11, "13");
        jbxx_hjlb_s = new SelectViewAndHandlerAndMsg(this, "户籍类别",
                getMap("CDL_RPRTYPE"), jbxx_hjlb_t, handler, 11, "");
        jbxx_hyzm_s = new SelectViewAndHandlerAndMsg(this, "婚育证明",
                getMap("SJCJ_D_HYZM"), jbxx_hyzm_t, handler, 11, "");
        jzxx_ljyy_s = new SelectViewAndHandlerAndMsg(this, "来京原因",
                getMap("SJCJ_D_LJYY"), jzxx_ljyy_t, handler, 32, "11");
        jzxx_jzlx_s = new SelectViewAndHandlerAndMsg(this, "居住类型",
                getMap("SJCJ_D_ZSLX"), jzxx_jzlx_t, handler, 31, "02");
        jyxx_mqzk_s = new SelectViewAndHandlerAndMsg(this, "目前状况",
                getMap("SJCJ_D_MQZK"), jyxx_mqzk_t, handler, 41, "");
        jyxx_sszy_s = new SelectViewAndHandlerAndMsg(this, "从事职业",
                getMap("CDL_SPECIALTY"), jyxx_sszy_t, handler, 11, "");
        LinkedHashMap<String, String> mzmap = getMap("CDG_NATION");
        mzmap.remove("");
        jbxx_mz_s = new SelectViewAndHandlerAndMsg(this, "民族", mzmap,
                jbxx_mz_t, handler, 11, "01");
        jbxx_jzzj_s = new SelectViewAndHandlerAndMsg(this, "居住证件", youwuMap,
                jbxx_jzzj_t, handler, 11, "");
        jbxx_myjzz_s = new SelectViewAndHandlerAndMsg(this, "免疫接种证", youwuMap,
                jbxx_myjzz_t, handler, 11, "");
        jyxx_sfqdht_s = new SelectViewAndHandlerAndMsg(this, "是否签订劳动合同",
                youwuMap, jyxx_sfqdht_t, handler, 11, "");
        String sql = "select cd_id,cd_name from cdg_regioncode where CD_AVAILABILITY='1' and cd_id like '110___000000' and cd_id != '110000000000' and cd_id != '110103000000'and cd_id != '110104000000' order by cd_index";
        SqliteCodeTable helper = SqliteCodeTable.getInstance(Rycj_dengji.this);
        Cursor c = helper.Query(sql, null);
        int l = c.getCount();
        LinkedHashMap<String, String> dwdzMap = null;
        if (l > 0) {
            dwdzMap = new LinkedHashMap<String, String>();
            dwdzMap.put("", "请选择");
            while (c.moveToNext()) {
                dwdzMap.put(c.getString(c.getColumnIndex("CD_ID")),
                        c.getString(c.getColumnIndex("CD_NAME")));

            }

        }
        c.close();
        helper.close();
        // 单位地址 select
        jyxx_dwdz_s = new SelectViewAndHandlerAndMsg(this, "单位地址", dwdzMap,
                jyxx_dwdz_t, handler, 11, "");
        // 省市区 三级联动
        jbxx_hjdz_s = new DzxzHelper(this,
                (TextView) findViewById(R.id.dengji_dzxz_sheng),
                (TextView) findViewById(R.id.dengji_dzxz_shi),
                (TextView) findViewById(R.id.dengji_dzxz_qu), false);
        jyxx_dwhy_csgz_s = new SelectDwhyCsgzLd(this, jyxx_dwsshy_t,
                jyxx_ccgz_t, jyxx_jy_qtsshy_l, jyxx_jy_qtgz_l, "", "");
        // 所属辖区
        String[] data1 = preference.getString("login_govern_id", "").split(",");
        String[] data2 = preference.getString("login_govern_name", "").split(
                ",");
        LinkedHashMap<String, String> ssxqmap = new LinkedHashMap<String, String>();
        for (int i = 0; i < data1.length; i++) {
            ssxqmap.put(data1[i], data2[i]);
        }
    }


    @Override
    protected void onDestroy() {
        readCardControler.close();
        super.onDestroy();

    }

    /**
     * 初始化对象ID
     */
    private void findView() {
        userData = StaticObject.getUserData(Rycj_dengji.this);

        tvSsfwz = (TextView) findViewById(R.id.dengji_tv_date_ssfwz);
        tvSxxq = (TextView) findViewById(R.id.dengji_tv_date_sxxq);
        tvGly = (TextView) findViewById(R.id.dengji_tv_date_gly);
        jbxx_t = (TextView) findViewById(R.id.dengji_title_jibenxinxi);
        jzxx_t = (TextView) findViewById(R.id.dengji_title_juzhuxinxi);
        jyxx_t = (TextView) findViewById(R.id.dengji_title_jiuyeshebao);
        sqcl_t = (TextView) findViewById(R.id.dengji_title_sqcl);
        jzxx_ljrq_t = (TextView) findViewById(R.id.dengji_tv_date_laijingriqi);
        jzxx_lxzdrq_t = (TextView) findViewById(R.id.dengji_tv_date_laixianzhudi);
        jbxx_csd_t = (TextView) findViewById(R.id.dengji_select_chushengdi);
        jbxx_csd_qt = (TextView) findViewById(R.id.jbxx_csd_t);
        jyxx_ccgz_t = (TextView) findViewById(R.id.dengji_select_congshigongzuo);
        jyxx_dwsshy_t = (TextView) findViewById(R.id.dengji_select_danweisuoshuhangye);
        jbxx_hjlb_t = (TextView) findViewById(R.id.dengji_select_hujileibie);
        jbxx_hyzk_t = (TextView) findViewById(R.id.dengji_select_hunyinzhuangkuang);
        jbxx_hyzm_t = (TextView) findViewById(R.id.dengji_select_hunyuzhengming);
        jzxx_jzlx_t = (TextView) findViewById(R.id.dengji_select_juzhuleixing);
        jzxx_fzxm_e = (EditText) findViewById(R.id.dengji_et_fzxm);
        jzxx_fzsfzh_e = (EditText) findViewById(R.id.dengji_et_fzsfzhm);
        jbxx_jzzj_t = (TextView) findViewById(R.id.dengji_select_juzhuzhengjian);
        jzxx_ljyy_t = (TextView) findViewById(R.id.dengji_select_laijingyuanyin);
        jbxx_myjzz_t = (TextView) findViewById(R.id.dengji_select_mianyi);
        jbxx_mz_t = (TextView) findViewById(R.id.dengji_select_minzu);
        jyxx_mqzk_t = (TextView) findViewById(R.id.dengji_select_muqianzhuangkuang);
        jyxx_sfqdht_t = (TextView) findViewById(R.id.dengji_select_qianding);
        jbxx_sjycd_t = (TextView) findViewById(R.id.dengji_select_shoujiaoyu);
        jbxx_xb_t = (TextView) findViewById(R.id.dengji_select_xingbie);
        jbxx_zzmm_t = (TextView) findViewById(R.id.dengji_select_zhengzhimianmao);
        jyxx_sszy_t = (TextView) findViewById(R.id.dengji_select_zhiye);
        tbrq = (TextView) findViewById(R.id.dengji_tv_date_tbrq);
        jzxx_qtljyy_l = (LinearLayout) findViewById(R.id.dengji_visib_ljqitayuanyin);
        jyxx_jy_qtsshy_l = (LinearLayout) findViewById(R.id.dengji_visib_qtdwsshy);
        jyxx_jy_qtgz_l = (LinearLayout) findViewById(R.id.dengji_visib_qitagongzuo);
        jyxx_mqzk_jy_l = (LinearLayout) findViewById(R.id.dengji_visib_muqian_jiuye);
        jyxx_mqzk_qt_l = (LinearLayout) findViewById(R.id.dengji_visib_muqian_qita);
        jyxx_mqzk_xs_l = (LinearLayout) findViewById(R.id.dengji_visib_muqian_xuesheng);
        jbxx_l = (LinearLayout) findViewById(R.id.dengji_linear_jiben);
        jzxx_l = (LinearLayout) findViewById(R.id.dengji_linear_juzhuxinxi);
        jyxx_l = (LinearLayout) findViewById(R.id.dengji_linear_jiuyeshebao);
        sqcl_l = (LinearLayout) findViewById(R.id.dengji_linear_sqcl);
        jyxx_zjsb_w = (CheckBox) findViewById(R.id.dengji_cb_j_wu);
        jyxx_zjsb_yl = (CheckBox) findViewById(R.id.dengji_cb_j_yanglao);
        jyxx_zjsb_shy = (CheckBox) findViewById(R.id.dengji_cb_j_shengyu);
        jyxx_zjsb_sy = (CheckBox) findViewById(R.id.dengji_cb_j_shiye);
        jyxx_zjsb_yil = (CheckBox) findViewById(R.id.dengji_cb_j_yiliao);
        jyxx_zjsb_gs = (CheckBox) findViewById(R.id.dengji_cb_j_gongshang);
        jyxx_sbxx_w = (CheckBox) findViewById(R.id.dengji_cb_2_wu);
        jyxx_sbxx_yl = (CheckBox) findViewById(R.id.dengji_cb_2_yanglao);
        jyxx_sbxx_shy = (CheckBox) findViewById(R.id.dengji_cb_2_shengyu);
        jyxx_sbxx_sy = (CheckBox) findViewById(R.id.dengji_cb_2_shiye);
        jyxx_sbxx_yil = (CheckBox) findViewById(R.id.dengji_cb_2_yiliao);
        jyxx_sbxx_gs = (CheckBox) findViewById(R.id.dengji_cb_2_gongshang);
        jbxx_xm_e = (EditText) findViewById(R.id.dengji_six_xingming);
        jbxx_sfzh_e = (EditText) findViewById(R.id.dengji_six_haoma);
        //lockbaseSix();
        jbxx_csrq_e = (EditText) findViewById(R.id.dengji_six_riqi);
        jbxx_hjxxdz_e = (EditText) findViewById(R.id.dengji_six_dizhi);
        jzxx_xzdz_e = (EditText) findViewById(R.id.dengji_et_xianzhudizhi);
        StaticObject.lock(jzxx_xzdz_e);
        jyxx_dwmc_e = (EditText) findViewById(R.id.dengji_et_jiuyedanwei);
        jyxx_dwxxdz_e = (EditText) findViewById(R.id.dengji_et_dwxxdz);
        jyxx_dwxxdz_dw_i = (ImageView) findViewById(R.id.dwxxdz_b);
        jyxx_xxmc_e = (EditText) findViewById(R.id.dengji_et_xuexiaomingcheng);
        jyxx_xxszd_e = (EditText) findViewById(R.id.dengji_et_xuexiaosuozaidi);
        jyxx_jyxxxx_e = (EditText) findViewById(R.id.dengji_et_xiangxixinxi);
        jyxx_qthy_e = (EditText) findViewById(R.id.dengji_et_qtdwsshy);
        jyxx_qtgz_e = (EditText) findViewById(R.id.dengji_et_qitagongzuo);
        jbxx_lxdh_e = (EditText) findViewById(R.id.dengji_et_dianhua);
        jzxx_ljqtyy_e = (EditText) findViewById(R.id.dengji_et_ljqitayuanyin);
        jyxx_qtgz_e = (EditText) findViewById(R.id.dengji_et_qitagongzuo);
        btn_hs = (Button) findViewById(R.id.dengji_btn_chongzhi);
        btn_xctj = (Button) findViewById(R.id.dengji_btn_next);
        btn_zctj = (Button) findViewById(R.id.dengji_btn_zancun);
        btn_fwcx = (Button) findViewById(R.id.dengji_btn_fangwu);
        btn_fwdj = (Button) findViewById(R.id.dengji_btn_fangwudengji);
        clxx_sfzmcl = (Button) findViewById(R.id.dengji_btn_fanpai1);
        clxx_zjzscl = (Button) findViewById(R.id.dengji_btn_fanpai2);
        clxx_zzrkdjb = (Button) findViewById(R.id.dengji_btn_fanpai3);
        jyxx_dwdz_t = (TextView) findViewById(R.id.dengji_select_danweidizhi);
        jbxx_photo_i = (ImageView) findViewById(R.id.dengji_touxiang);
        jbxx_warn = (ImageView) findViewById(R.id.dengji_warning);
        btn_back_i = (ImageButton) findViewById(R.id.ryjc_dengji_back);
        ll_sfzmcl = (LinearLayout) findViewById(R.id.dengji_linear_sfzmcl);
        ll_zjzscl = (LinearLayout) findViewById(R.id.dengji_linear_zjzscl);
        ll_zzrkdjb = (LinearLayout) findViewById(R.id.dengji_linear_zzrkdjb);

    }

    /**
     * 添加监听事件
     */
    private void setClick() {
        tbrq.setOnClickListener(this);
        jbxx_t.setOnClickListener(this);
        jyxx_t.setOnClickListener(this);
        sqcl_t.setOnClickListener(this);
        jzxx_t.setOnClickListener(this);
        jzxx_ljrq_t.setOnClickListener(this);
        jzxx_lxzdrq_t.setOnClickListener(this);
        jyxx_zjsb_w.setOnCheckedChangeListener(this);
        jyxx_zjsb_yl.setOnCheckedChangeListener(this);
        jyxx_zjsb_shy.setOnCheckedChangeListener(this);
        jyxx_zjsb_sy.setOnCheckedChangeListener(this);
        jyxx_zjsb_yil.setOnCheckedChangeListener(this);
        jyxx_zjsb_gs.setOnCheckedChangeListener(this);

        jyxx_sbxx_w.setOnCheckedChangeListener(this);
        jyxx_sbxx_yl.setOnCheckedChangeListener(this);
        jyxx_sbxx_shy.setOnCheckedChangeListener(this);
        jyxx_sbxx_sy.setOnCheckedChangeListener(this);
        jyxx_sbxx_yil.setOnCheckedChangeListener(this);
        jyxx_sbxx_gs.setOnCheckedChangeListener(this);

        btn_xctj.setOnClickListener(this);
        btn_zctj.setOnClickListener(this);
        btn_fwcx.setOnClickListener(this);
        btn_fwdj.setOnClickListener(this);
        btn_hs.setOnClickListener(this);
        btn_back_i.setOnClickListener(this);
        jyxx_dwxxdz_dw_i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //baiduDw.getPosition(jyxx_dwxxdz_e);
            }
        });
        clxx_sfzmcl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (sfzmclList.size() < 3) {
                    photoGraph(1);
                    StaticObject.print(sfzmclList.size() + "%%%");

                } else {
                    StaticObject.showToast(Rycj_dengji.this, "身份证明材料最多只能上传三张");
                }
            }
        });

        clxx_zjzscl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (zjzsclList.size() < 6) {
                    photoGraph(2);
                } else {
                    StaticObject.showToast(Rycj_dengji.this, "在京住所证明最多只能上传六张");
                }
            }
        });
        clxx_zzrkdjb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (zzrkdjbList.size() < 3) {
                    photoGraph(3);
                } else {
                    StaticObject.showToast(Rycj_dengji.this, "暂住人口登记表最多只能上传三张");
                }
            }
        });

    }

    public void photoGraph(int TAKPHOTO) {
        path = SxConfig.read(Rycj_dengji.this, SxConfig.PHOTOPATH);
        photoFile = new File(path);
        if (!photoFile.exists()) {
            photoFile.mkdirs();
        }
        // 照片名
        fileName = new StringBuilder().append(new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date().getTime())).append(".jpg").toString();
        photoFile = new File(path, fileName);

        // 调用系统照相机
        Uri outputFileUri = Uri.fromFile(photoFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, TAKPHOTO);

    }


}
