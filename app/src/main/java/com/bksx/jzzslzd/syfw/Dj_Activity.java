package com.bksx.jzzslzd.syfw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.BzdzSelector;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.TextView;

public class Dj_Activity extends Activity implements OnClickListener,
		android.widget.CompoundButton.OnCheckedChangeListener {
	private TextView fangwujibenxinxi, fangzhuxinxi, chuzuxinxi, suoshuxiaqu,
			fangwuleixing, suoyouquanleixing, chuzurenleixing,
			zhengjianleixing, zhuanzurenzhengjianleixing, xingbie, hujidi,
			guoji, zhengzhimianmao, chuzuyongtu, fangwucengshu, zulinhetong,
			nashui, dengjibeian;
	private TextView chushengriqi, qiandingzerenshuriqi, chuzujiezhiriqi,
			fwcj_czxx_djba_t;
	private LinearLayout ll1, ll2, ll3, ll_qitafangwuleixing,
			ll_gerenfangzhuxinxi, ll_danweifangzhuxinxi,
			ll_zhongjiegongsixinxi, ll_zhuanzurenxinxi, ll_qitazhengjian,
			ll_zhuanzurenqitazhengjian, ll_guoji, ll_qitachuzuyongtu,
			ll_anquanyinhuan;
	private SelectViewAndHandlerAndMsg select_suoshuxiaqu,
			select_fangwuleixing, select_suoyouquanleixing,
			select_chuzurenleixing, select_zhengjianleixing,
			select_zhuanzurenzhengjianleixing, select_xingbie, select_hujidi,
			select_guoji, select_zhengzhimianmao, select_chuzuyongtu,
			select_fangwucengshu, select_zulinhetong, select_nashui,
			select_dengjibeian;
	private EditText et_qitafangwuleixing, et_danweimingcheng,
			et_fuzerenlianxidianhua, et_fangzhuxingming, et_lianxidianhua,
			et_zhengjianhaoma, et_gongsimingcheng, et_qitazhengjian,
			et_fangwufuzerenxingming, et_fangwufuzerenlianxidianhua,
			et_zhuanzurenxingming, et_zhuanzurenlianxidianhua,
			et_zhuanzurenzhengjianhaoma, et_chuzujianshu, et_pingfangmi,
			et_qitachuzuyongtu, et_benshirenshu, et_waishengshirenshu,
			et_gangaotairenshu, et_waijirenshu, et_zujin, et_fangchanzhenghao,
			et_fangwusuozaidizhi, et_fuzerenxingming, et_fuzerenzhengjianhaoma,
			et_zhuanzurenqitazhengjian;
	private AutoCompleteTextView dengji_auto_xianzhudizhi;
	private TextView tv_zhengjianhaoma, tv_zhuanzurenzhengjianhaoma,
			tv_chuzuqishiriqi, tv_tianbiaoriqi;
	private RadioGroup rg;
	private RadioButton yuezu, nianzu, ygyk, aqfx, ygjz;
	private Button tijiao, reset;
	private ImageButton back;
	private CheckBox cb_wu, cb_zhian, cb_xiaofang, cb_hunyu,
			cb_buwendingfengxian, cb_zhianyinhuan, cb_xiaofangyinhuan,
			cb_qitaanquanyinhuan;
	private SharedPreferences preferences;
	private ProgressDialog dialog;
	protected String[][] arrs;
	protected String result;
	private String[] data1, data2;
	private LinearLayout bwdfx, zayh, xfyh, qtyh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_fangwudengji);
		bwdfx = (LinearLayout) findViewById(R.id.fwdj_ll_buwendingfengxian);
		addView(bwdfx, 7, "400");
		zayh = (LinearLayout) findViewById(R.id.fwdj_ll_zhianyinhuan);
		addView(zayh, 5, "100");
		xfyh = (LinearLayout) findViewById(R.id.fwdj_ll_xiaofangyinhuan);
		addView(xfyh, 6, "200");
		qtyh = (LinearLayout) findViewById(R.id.fwdj_ll_qitaanquanyinhuan);
		addView(qtyh, 4, "300");
		findView();
		initshow();
		init();
		setClick();

	}

	// 添加view
	private void addView(LinearLayout ll, int num, String code) {
		LinkedHashMap<String, String> map = getMap("FWGL_D_AQYHLX");
		for (int i = 0; i < num; i++) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setText(map.get(code + (i + 1)));
			checkBox.setTextColor(getResources().getColor(R.color.black));
			checkBox.setTextSize(20);
			ll.addView(checkBox);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				StaticObject.showToast(Dj_Activity.this, "提交成功");
				String s = (String) msg.obj;
				String[] strings = s.split(",");
				sucess(strings[1]);
				break;
			case 2:
				StaticObject.showToast(Dj_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Dj_Activity.this, "提交失败");
				break;
			case 4:
				StaticObject.showToast(Dj_Activity.this, "房屋已存在");
				break;
			case 5:
				StaticObject.showToast(Dj_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Dj_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 12:
				if (select_fangwuleixing.getCodeId().equals("02")) {

					ll_qitafangwuleixing.setVisibility(View.GONE);
				} else if (select_fangwuleixing.getCodeId().equals("09")) {
					ll_qitafangwuleixing.setVisibility(View.VISIBLE);

				} else {

					ll_qitafangwuleixing.setVisibility(View.GONE);
				}
				break;
			case 21:
				if (select_suoyouquanleixing.getCodeId().equals("2")) {
					ll_danweifangzhuxinxi.setVisibility(View.VISIBLE);
					ll_gerenfangzhuxinxi.setVisibility(View.GONE);
				} else {
					ll_danweifangzhuxinxi.setVisibility(View.GONE);
					ll_gerenfangzhuxinxi.setVisibility(View.VISIBLE);
				}
				break;
			case 22:
				if (select_chuzurenleixing.getCodeId().equals("2")) {
					ll_zhongjiegongsixinxi.setVisibility(View.VISIBLE);
					ll_zhuanzurenxinxi.setVisibility(View.GONE);
				} else if (select_chuzurenleixing.getCodeId().equals("3")) {
					ll_zhuanzurenxinxi.setVisibility(View.VISIBLE);
					ll_zhongjiegongsixinxi.setVisibility(View.GONE);
				} else {
					ll_zhongjiegongsixinxi.setVisibility(View.GONE);
					ll_zhuanzurenxinxi.setVisibility(View.GONE);
				}
				break;
			case 23:
				if (select_zhengjianleixing.getCodeId().equals("04")) {
					ll_qitazhengjian.setVisibility(View.VISIBLE);

				} else {
					ll_qitazhengjian.setVisibility(View.GONE);

				}
				if (select_zhengjianleixing.getCodeId().equals("01")) {
					tv_zhengjianhaoma.setTextColor(getResources().getColor(
							R.color.black));

				} else {
					tv_zhengjianhaoma.setTextColor(getResources().getColor(
							R.color.viewcolor));

				}
				break;
			case 24:
				if (select_zhuanzurenzhengjianleixing.getCodeId().equals("04")) {
					ll_zhuanzurenqitazhengjian.setVisibility(View.VISIBLE);

				} else {
					ll_zhuanzurenqitazhengjian.setVisibility(View.GONE);

				}
				if (select_zhuanzurenzhengjianleixing.getCodeId().equals("01")) {
					tv_zhuanzurenzhengjianhaoma.setTextColor(getResources()
							.getColor(R.color.black));

				} else {
					tv_zhuanzurenzhengjianhaoma.setTextColor(getResources()
							.getColor(R.color.viewcolor));

				}
				break;
			case 25:
				if (select_hujidi.getCodeId().equals("4")) {
					ll_guoji.setVisibility(View.VISIBLE);

				} else {
					ll_guoji.setVisibility(View.GONE);

				}
				break;
			case 31:
				if (select_chuzuyongtu.getCodeId().equals("09")) {
					ll_qitachuzuyongtu.setVisibility(View.VISIBLE);

				} else {
					ll_qitachuzuyongtu.setVisibility(View.GONE);

				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 初始化数据
	 */
	private void init() {
		LinkedHashMap<String, String> xingbieMap = new LinkedHashMap<String, String>();
		xingbieMap.put("", "请选择");
		xingbieMap.put("0", "男");
		xingbieMap.put("1", "女");
		LinkedHashMap<String, String> cengshuMap = new LinkedHashMap<String, String>();
		for (int i = 0; i <= 50; i++) {
			if (i == 0) {
				cengshuMap.put("", "请选择");
			} else {
				cengshuMap.put("" + i, "" + i);
			}
		}
		LinkedHashMap<String, String> youwuMap = new LinkedHashMap<String, String>();
		youwuMap.put("", "请选择");
		youwuMap.put("1", "有");
		youwuMap.put("0", "无");
		LinkedHashMap<String, String> nashuiMap = new LinkedHashMap<String, String>();
		nashuiMap.put("", "请选择");
		nashuiMap.put("1", "已缴");
		nashuiMap.put("0", "未缴");
		LinkedHashMap<String, String> dengjibeianMap = new LinkedHashMap<String, String>();
		dengjibeianMap.put("", "请选择");
		dengjibeianMap.put("1", "已办");
		dengjibeianMap.put("0", "未办");
		preferences = Dj_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		data1 = preferences.getString("login_govern_id", "").split(",");
		data2 = preferences.getString("login_govern_name", "").split(",");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < data1.length; i++) {
			map.put(data1[i], data2[i]);
		}
		select_suoshuxiaqu = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"所属辖区", map, suoshuxiaqu, handler, 11, "");
		select_suoshuxiaqu.setCodeId(data1[0]);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date()
				.getTime());// 当天日期
		tv_tianbiaoriqi.setText(today);
		select_fangwuleixing = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"房屋类型", getMap("SJCJ_D_CHZLX"), fangwuleixing, handler, 12, "");
		select_suoyouquanleixing = new SelectViewAndHandlerAndMsg(
				Dj_Activity.this, "所有权类型", getMap("SJCJ_D_SYQLX"),
				suoyouquanleixing, handler, 21, "");
		select_chuzurenleixing = new SelectViewAndHandlerAndMsg(
				Dj_Activity.this, "出租人类型", getMap("sjcj_d_czfs"),
				chuzurenleixing, handler, 22, "");
		select_zhengjianleixing = new SelectViewAndHandlerAndMsg(
				Dj_Activity.this, "证件类型", getMap("sjcj_d_zjlb"),
				zhengjianleixing, handler, 23, "");
		select_zhuanzurenzhengjianleixing = new SelectViewAndHandlerAndMsg(
				Dj_Activity.this, "转租人证件类型", getMap("sjcj_d_zjlb"),
				zhuanzurenzhengjianleixing, handler, 24, "");
		select_xingbie = new SelectViewAndHandlerAndMsg(Dj_Activity.this, "性别",
				xingbieMap, xingbie, handler, 11, "");
		select_hujidi = new SelectViewAndHandlerAndMsg(Dj_Activity.this, "户籍地",
				getMap("sjcj_d_hjd"), hujidi, handler, 25, "");
		select_guoji = new SelectViewAndHandlerAndMsg(Dj_Activity.this, "国籍",
				getMap("cdg_nationality"), guoji, handler, 11, "");
		select_zhengzhimianmao = new SelectViewAndHandlerAndMsg(
				Dj_Activity.this, "政治面貌", getMap("cdg_politicsaspect"),
				zhengzhimianmao, handler, 11, "");
		select_chuzuyongtu = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"出租用途", getMap("sjcj_d_czyt"), chuzuyongtu, handler, 31, "");
		select_fangwucengshu = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"房屋层数", cengshuMap, fangwucengshu, handler, 11, "");
		select_zulinhetong = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"租赁合同", youwuMap, zulinhetong, handler, 11, "");
		select_nashui = new SelectViewAndHandlerAndMsg(Dj_Activity.this, "纳税",
				nashuiMap, nashui, handler, 11, "");
		select_dengjibeian = new SelectViewAndHandlerAndMsg(Dj_Activity.this,
				"登记备案", dengjibeianMap, dengjibeian, handler, 11, "");
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.yanguanyankong) {
					ll_anquanyinhuan.setVisibility(View.VISIBLE);
				} else {
					ll_anquanyinhuan.setVisibility(View.GONE);
					cleanAqyh();

				}
			}
		});
	}

	/**
	 * 获取代码表数据　　
	 * 
	 * @param 　表名
	 * @return
	 */
	private LinkedHashMap<String, String> getMap(String name) {
		String sql = "select CD_ID,CD_NAME from " + name
				+ " where CD_AVAILABILITY='1' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(Dj_Activity.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		LinkedHashMap<String, String> linkedMap = null;
		if (l > 0) {
			linkedMap = new LinkedHashMap<String, String>();
			linkedMap.put("", "请选择");
			while (c.moveToNext()) {
				linkedMap.put(c.getString(c.getColumnIndex("CD_ID")), c
						.getString(c.getColumnIndex("CD_NAME")).trim());
			}
		}
		c.close();
		helper.close();
		return linkedMap;

	}

	/**
	 * 设置组件显隐关系
	 */
	private void initshow() {
		// 房屋基本信息
		ll_qitafangwuleixing.setVisibility(View.GONE);
		// 房主信息
		ll_danweifangzhuxinxi.setVisibility(View.GONE);
		ll_zhongjiegongsixinxi.setVisibility(View.GONE);
		ll_zhuanzurenxinxi.setVisibility(View.GONE);
		ll_qitazhengjian.setVisibility(View.GONE);
		ll_zhuanzurenqitazhengjian.setVisibility(View.GONE);
		ll_guoji.setVisibility(View.GONE);
		// 出租信息
		ll_qitachuzuyongtu.setVisibility(View.GONE);
		ll_qitachuzuyongtu.setVisibility(View.GONE);
		ll_anquanyinhuan.setVisibility(View.GONE);

	}

	/**
	 * 初始化对象ID
	 */
	private void findView() {
		fangwujibenxinxi = (TextView) findViewById(R.id.fwdj_fangwujibenxinxi);
		fangzhuxinxi = (TextView) findViewById(R.id.fwdj_fangzhuxinxi);
		chuzuxinxi = (TextView) findViewById(R.id.fwdj_chuzuxinxi);
		suoshuxiaqu = (TextView) findViewById(R.id.fwdj_select_suoshuxiaqu);
		fangwuleixing = (TextView) findViewById(R.id.fwdj_select_fangwuleixing);
		suoyouquanleixing = (TextView) findViewById(R.id.fwdj_select_suoyouquanleixing);
		chuzurenleixing = (TextView) findViewById(R.id.fwdj_select_chuzurenleixing);
		zhengjianleixing = (TextView) findViewById(R.id.fwdj_select_zhengjianleixing);
		tv_zhengjianhaoma = (TextView) findViewById(R.id.fwdj_tv_zhengjianhaoma);
		zhuanzurenzhengjianleixing = (TextView) findViewById(R.id.fwdj_select_zhuanzurenzhengjianleixing);
		tv_zhuanzurenzhengjianhaoma = (TextView) findViewById(R.id.fwdj_tv_zhuanzurenzhengjianhaoma);
		xingbie = (TextView) findViewById(R.id.fwdj_select_xingbie);
		hujidi = (TextView) findViewById(R.id.fwdj_select_hujidi);
		guoji = (TextView) findViewById(R.id.fwdj_select_guoji);
		zhengzhimianmao = (TextView) findViewById(R.id.fwdj_select_zhengzhimianmao);
		chuzuyongtu = (TextView) findViewById(R.id.fwdj_select_chuzuyongtu);
		fangwucengshu = (TextView) findViewById(R.id.fwdj_select_fangwucengshu);
		zulinhetong = (TextView) findViewById(R.id.fwdj_select_zulinhetong);
		nashui = (TextView) findViewById(R.id.fwdj_select_nashui);
		dengjibeian = (TextView) findViewById(R.id.fwdj_select_dengjibeian);
		chushengriqi = (TextView) findViewById(R.id.fwdj_chushengriqi);
		qiandingzerenshuriqi = (TextView) findViewById(R.id.fwdj_qiandingzerenshuriqi);
		tv_chuzuqishiriqi = (TextView) findViewById(R.id.fwdj_tv_chuzuqishiriqi);
		tv_tianbiaoriqi = (TextView) findViewById(R.id.fwdj_tv_tianbiaoriqi);
		chuzujiezhiriqi = (TextView) findViewById(R.id.fwdj_chuzujiezhiriqi);
		fwcj_czxx_djba_t = (TextView) findViewById(R.id.fwcj_czxx_djba_t);
		ll1 = (LinearLayout) findViewById(R.id.fwdj_ll1);
		ll2 = (LinearLayout) findViewById(R.id.fwdj_ll2);
		ll3 = (LinearLayout) findViewById(R.id.fwdj_ll3);
		ll_qitafangwuleixing = (LinearLayout) findViewById(R.id.fwdj_ll_qitafangwuleixing);
		ll_gerenfangzhuxinxi = (LinearLayout) findViewById(R.id.fwdj_ll_gerenfangzhuxinxi);
		ll_danweifangzhuxinxi = (LinearLayout) findViewById(R.id.fwdj_ll_danweifangzhuxinxi);
		ll_zhongjiegongsixinxi = (LinearLayout) findViewById(R.id.fwdj_ll_zhongjiegongsixinxi);
		ll_zhuanzurenxinxi = (LinearLayout) findViewById(R.id.fwdj_ll_zhuanzurenxinxi);
		ll_qitazhengjian = (LinearLayout) findViewById(R.id.fwdj_ll_qitazhengjian);
		ll_zhuanzurenqitazhengjian = (LinearLayout) findViewById(R.id.fwdj_ll_zhuanzurenqitazhengjian);
		ll_guoji = (LinearLayout) findViewById(R.id.fwdj_ll_guoji);
		ll_qitachuzuyongtu = (LinearLayout) findViewById(R.id.fwdj_ll_qitachuzuyongtu);
		ll_anquanyinhuan = (LinearLayout) findViewById(R.id.fwdj_ll_anquanyinhuan);
		rg = (RadioGroup) findViewById(R.id.fwdj_rg);
		tijiao = (Button) findViewById(R.id.fwdj_tijiao);
		reset = (Button) findViewById(R.id.fwdj_reset);
		back = (ImageButton) findViewById(R.id.fwdj_back);
		et_qitafangwuleixing = (EditText) findViewById(R.id.fwdj_qitafangwuleixing);
		et_danweimingcheng = (EditText) findViewById(R.id.fwdj_et_danweimingcheng);
		et_fuzerenlianxidianhua = (EditText) findViewById(R.id.fwdj_et_fuzerenlianxidianhua);
		et_fangzhuxingming = (EditText) findViewById(R.id.fwdj_et_fangzhuxingming);
		et_lianxidianhua = (EditText) findViewById(R.id.fwdj_et_lianxidianhua);
		et_zhengjianhaoma = (EditText) findViewById(R.id.fwdj_et_zhengjianhaoma);
		et_qitazhengjian = (EditText) findViewById(R.id.fwdj_et_qitazhengjian);
		et_gongsimingcheng = (EditText) findViewById(R.id.fwdj_et_gongsimingcheng);
		et_fangwufuzerenxingming = (EditText) findViewById(R.id.fwdj_et_fangwufuzerenxingming);
		et_fangwufuzerenlianxidianhua = (EditText) findViewById(R.id.fwdj_et_fangwufuzerenlianxidianhua);
		et_zhuanzurenxingming = (EditText) findViewById(R.id.fwdj_et_zhuanzurenxingming);
		et_zhuanzurenlianxidianhua = (EditText) findViewById(R.id.fwdj_et_zhuanzurenlianxidianhua);
		et_zhuanzurenzhengjianhaoma = (EditText) findViewById(R.id.fwdj_et_zhuanzurenzhengjianhaoma);
		et_chuzujianshu = (EditText) findViewById(R.id.fwdj_et_chuzujianshu);
		et_pingfangmi = (EditText) findViewById(R.id.fwdj_et_pingfangmi);
		et_qitachuzuyongtu = (EditText) findViewById(R.id.fwdj_et_qitachuzuyongtu);
		et_benshirenshu = (EditText) findViewById(R.id.fwdj_et_benshirenshu);
		et_waishengshirenshu = (EditText) findViewById(R.id.fwdj_et_waishengshirenshu);
		et_gangaotairenshu = (EditText) findViewById(R.id.fwdj_et_gangaotairenshu);
		et_waijirenshu = (EditText) findViewById(R.id.fwdj_et_waijirenshu);
		et_zujin = (EditText) findViewById(R.id.fwdj_et_zujin);
		et_fangchanzhenghao = (EditText) findViewById(R.id.fwdj_et_fangchanzhenghao);
		et_fangwusuozaidizhi = (EditText) findViewById(R.id.fwdj_et_fangwusuozaidizhi);
		dengji_auto_xianzhudizhi = (AutoCompleteTextView) findViewById(R.id.fwdj_auto_fangwusuozaidizhi);
		et_fuzerenxingming = (EditText) findViewById(R.id.fwdj_et_fuzerenxingming);
		et_fuzerenzhengjianhaoma = (EditText) findViewById(R.id.fwdj_et_fuzerenzhengjianhaoma);
		et_zhuanzurenqitazhengjian = (EditText) findViewById(R.id.fwdj_et_zhuanzurenqitazhengjian);
		cb_zhian = (CheckBox) findViewById(R.id.fwdj_cb_zhian);
		cb_xiaofang = (CheckBox) findViewById(R.id.fwdj_cb_xiaofang);
		cb_hunyu = (CheckBox) findViewById(R.id.fwdj_cb_hunyu);
		cb_wu = (CheckBox) findViewById(R.id.fwdj_cb_wu);
		cb_zhianyinhuan = (CheckBox) findViewById(R.id.fwdj_cb_zhianyinhuan);
		cb_xiaofangyinhuan = (CheckBox) findViewById(R.id.fwdj_cb_xiaofangyinhuan);
		cb_qitaanquanyinhuan = (CheckBox) findViewById(R.id.fwdj_cb_qitaanquanyinhuan);
		cb_buwendingfengxian = (CheckBox) findViewById(R.id.fwdj_cb_buwendingfengxian);
		yuezu = (RadioButton) findViewById(R.id.yuezu);
		nianzu = (RadioButton) findViewById(R.id.nianzu);
		ygyk = (RadioButton) findViewById(R.id.yanguanyankong);
		aqfx = (RadioButton) findViewById(R.id.anquanfangxin);
		ygjz = (RadioButton) findViewById(R.id.yangejinzhi);

		new BzdzSelector(this, dengji_auto_xianzhudizhi, et_fangwusuozaidizhi,
				true);
	}

	private void setClick() {
		chushengriqi.setOnClickListener(this);
		qiandingzerenshuriqi.setOnClickListener(this);
		tv_chuzuqishiriqi.setOnClickListener(this);
		tv_tianbiaoriqi.setOnClickListener(this);
		chuzujiezhiriqi.setOnClickListener(this);
		tijiao.setOnClickListener(this);
		reset.setOnClickListener(this);
		back.setOnClickListener(this);
		fangwujibenxinxi.setOnClickListener(this);
		fangzhuxinxi.setOnClickListener(this);
		chuzuxinxi.setOnClickListener(this);
		cb_wu.setOnCheckedChangeListener(this);
		cb_zhian.setOnCheckedChangeListener(this);
		cb_xiaofang.setOnCheckedChangeListener(this);
		cb_hunyu.setOnCheckedChangeListener(this);
		cb_buwendingfengxian.setOnCheckedChangeListener(this);
		cb_zhianyinhuan.setOnCheckedChangeListener(this);
		cb_xiaofangyinhuan.setOnCheckedChangeListener(this);
		cb_qitaanquanyinhuan.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fwdj_tv_tianbiaoriqi:
			datePick(tv_tianbiaoriqi);
			break;
		case R.id.fwdj_chushengriqi:
			datePick(chushengriqi);
			break;
		case R.id.fwdj_qiandingzerenshuriqi:
			datePick(qiandingzerenshuriqi);
			break;
		case R.id.fwdj_tv_chuzuqishiriqi:
			datePick(tv_chuzuqishiriqi);
			break;
		case R.id.fwdj_chuzujiezhiriqi:
			datePick(chuzujiezhiriqi);
			break;
		case R.id.fwdj_reset:
			resetData();
			break;
		case R.id.fwdj_back:
			Dj_Activity.this.finish();
			break;
		case R.id.fwdj_fangwujibenxinxi:
			titleClick(ll1, fangwujibenxinxi, R.drawable.ryhc_icon_word);
			break;
		case R.id.fwdj_fangzhuxinxi:
			titleClick(ll2, fangzhuxinxi, R.drawable.rycj_jt);
			break;
		case R.id.fwdj_chuzuxinxi:
			titleClick(ll3, chuzuxinxi, R.drawable.fwhc_icon_fw);
			break;
		case R.id.fwdj_tijiao:
			// 校验所有必填项，如果某项不填，便终止
			if (checkAllForm()) {
				// 提交
				sendData();
			}
			break;
		default:
			break;
		}

	}

	private void resetData() {
		// 房屋基本信息
		select_suoshuxiaqu.setCodeId(data1[0]);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date()
				.getTime());// 当天日期
		tv_tianbiaoriqi.setText(today);
		select_fangwuleixing.setCodeId("");
		et_qitafangwuleixing.setText("");
		et_fangwusuozaidizhi.setText("");
		et_fangchanzhenghao.setText("");

		// 房主信息
		select_suoyouquanleixing.setCodeId("");
		et_fangzhuxingming.setText("");
		select_zhengjianleixing.setCodeId("");
		et_qitazhengjian.setText("");
		et_zhengjianhaoma.setText("");
		et_lianxidianhua.setText("");
		select_xingbie.setCodeId("");
		select_hujidi.setCodeId("");
		select_zhengzhimianmao.setCodeId("");
		chushengriqi.setText("");
		select_guoji.setCodeId("");
		et_danweimingcheng.setText("");
		et_fuzerenxingming.setText("");
		et_fuzerenlianxidianhua.setText("");
		select_chuzurenleixing.setCodeId("");
		et_gongsimingcheng.setText("");
		et_fangwufuzerenxingming.setText("");
		et_fuzerenzhengjianhaoma.setText("");
		et_fangwufuzerenlianxidianhua.setText("");
		et_zhuanzurenxingming.setText("");
		et_zhuanzurenlianxidianhua.setText("");
		select_zhuanzurenzhengjianleixing.setCodeId("");
		et_zhuanzurenzhengjianhaoma.setText("");
		et_zhuanzurenqitazhengjian.setText("");
		// 出租信息
		et_chuzujianshu.setText("");
		et_pingfangmi.setText("");
		select_fangwucengshu.setCodeId("");
		aqfx.setChecked(true);
		cleanAqyh();
		select_chuzuyongtu.setCodeId("");
		select_zulinhetong.setCodeId("");
		et_qitachuzuyongtu.setText("");
		et_benshirenshu.setText("");
		et_waishengshirenshu.setText("");
		et_gangaotairenshu.setText("");
		et_waijirenshu.setText("");
		yuezu.setChecked(true);
		et_zujin.setText("");
		select_nashui.setCodeId("");
		select_dengjibeian.setCodeId("");
		cleanSfqdzrs();
		qiandingzerenshuriqi.setText("");
		tv_chuzuqishiriqi.setText("");
		chuzujiezhiriqi.setText("");
		initshow();

		new BzdzSelector(this, dengji_auto_xianzhudizhi, et_fangwusuozaidizhi,
				true);

	}

	@SuppressLint("SimpleDateFormat")
	private void sendData() {
		dialog = StaticObject.showDialog(Dj_Activity.this, "数据提交中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				arrs = new String[1][149];
				arrs[0][0] = "";
				arrs[0][1] = preferences.getString("login_xzqh", "");
				arrs[0][2] = select_suoyouquanleixing.getCodeId();
				arrs[0][3] = et_fangchanzhenghao.getText().toString().trim();
				arrs[0][4] = et_fangwusuozaidizhi.getText().toString().trim();
				arrs[0][5] = "";// 房屋建设性质
				arrs[0][6] = "";// 房屋建筑类别
				arrs[0][7] = et_fangzhuxingming.getText().toString().trim();
				if (et_fangzhuxingming.getText().toString() == null
						|| "".equals(et_fangzhuxingming.getText().toString())) {
					arrs[0][7] = et_danweimingcheng.getText().toString().trim();
				}
				arrs[0][8] = select_zhengjianleixing.getCodeId();
				arrs[0][9] = et_zhengjianhaoma.getText().toString().trim();
				arrs[0][10] = et_lianxidianhua.getText().toString().trim();
				arrs[0][11] = "";// 房主所在地行政区划/单位所在地行政区划
				arrs[0][12] = "";// 现居住地详细地址/单位所在地详细地址
				arrs[0][13] = select_guoji.getCodeId();
				arrs[0][14] = select_hujidi.getCodeId();
				arrs[0][15] = "";// 房主户籍地址(行政区划)
				arrs[0][16] = "";// 房主户籍详细地址
				arrs[0][17] = select_zhengzhimianmao.getCodeId();
				if ("".equals(chushengriqi.getText().toString())) {
					arrs[0][18] = "";
				} else {
					arrs[0][18] = (FormCheck.getDate(chushengriqi.getText()
							.toString()) + "000000").trim();
				}
				arrs[0][19] = select_xingbie.getCodeId();
				arrs[0][20] = et_fuzerenxingming.getText().toString().trim();
				if ("2".equals(select_chuzurenleixing.getCodeId())) {
					arrs[0][20] = et_fangwufuzerenxingming.getText().toString()
							.trim();
				}
				arrs[0][21] = (FormCheck.getDate(tv_tianbiaoriqi.getText()
						.toString()) + "000000").trim();// 填表日期
				arrs[0][22] = "";// 房屋登记表序号
				arrs[0][23] = getSfqdzrs();
				arrs[0][24] = select_dengjibeian.getCodeId();
				arrs[0][25] = select_zulinhetong.getCodeId();
				arrs[0][26] = "";// 所属分局及派出所名称ID
				arrs[0][27] = "";// 所属社区
				arrs[0][28] = "";// 社区民警姓名
				arrs[0][29] = preferences.getString("login_admin_id", "");// 填表人
				arrs[0][30] = preferences.getString("login_number", "");// 管理员编码
				arrs[0][31] = (FormCheck.getDate(tv_tianbiaoriqi.getText()
						.toString()) + "000000").trim();// 填表日期
				arrs[0][32] = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime());// 登记日期
				arrs[0][33] = "phone";// 登记人
				arrs[0][34] = "phone";// 登记单位
				arrs[0][35] = "0";// 是否暂存
				arrs[0][36] = "";// 备注
				arrs[0][37] = select_chuzurenleixing.getCodeId();
				arrs[0][38] = et_zhuanzurenxingming.getText().toString().trim();
				if (et_zhuanzurenxingming.getText().toString() == null
						|| "".equals(et_zhuanzurenxingming.getText().toString())) {
					arrs[0][38] = et_gongsimingcheng.getText().toString()
							.trim();
				}
				arrs[0][39] = select_zhuanzurenzhengjianleixing.getCodeId();// 证件类别(转租人)
				arrs[0][40] = et_zhuanzurenzhengjianhaoma.getText().toString()
						.trim();
				if ("2".equals(select_chuzurenleixing.getCodeId())) {
					arrs[0][40] = et_fuzerenzhengjianhaoma.getText().toString()
							.trim();
				}
				arrs[0][41] = "";// 转租人现住地址/中介单位详细地址
				arrs[0][42] = et_zhuanzurenlianxidianhua.getText().toString()
						.trim();
				if (et_zhuanzurenlianxidianhua.getText().toString() == null
						|| "".equals(et_zhuanzurenlianxidianhua.getText()
								.toString())) {
					arrs[0][42] = et_fangwufuzerenlianxidianhua.getText()
							.toString().trim();
				}
				arrs[0][43] = preferences.getString("login_admin_id", "");// 填表人
				arrs[0][44] = preferences.getString("login_number", "");// 管理员编码
				arrs[0][45] = (FormCheck.getDate(tv_tianbiaoriqi.getText()
						.toString()) + "000000").trim();// 填表日期
				arrs[0][46] = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime());// 登记日期
				arrs[0][47] = "phone";// 登记人
				arrs[0][48] = "phone";// 登记单位
				arrs[0][49] = "";// 备注
				arrs[0][50] = select_chuzurenleixing.getCodeId();// 出租人类型
				arrs[0][51] = select_chuzuyongtu.getCodeId();// 出租用途
				arrs[0][52] = et_chuzujianshu.getText().toString().trim();
				arrs[0][53] = et_pingfangmi.getText().toString().trim();// 出租面积
				arrs[0][54] = et_benshirenshu.getText().toString().trim();
				arrs[0][55] = et_waishengshirenshu.getText().toString().trim();
				arrs[0][56] = et_gangaotairenshu.getText().toString().trim();
				arrs[0][57] = et_waijirenshu.getText().toString().trim();
				arrs[0][58] = (FormCheck.getDate(tv_chuzuqishiriqi.getText()
						.toString()) + "000000").trim();
				if ("".equals(chuzujiezhiriqi.getText().toString())) {
					arrs[0][59] = "";
				} else {
					arrs[0][59] = (FormCheck.getDate(chuzujiezhiriqi.getText()
							.toString()) + "000000").trim();

				}
				arrs[0][60] = ""; // 房间内设施
				arrs[0][61] = ""; // 房屋装修情况
				arrs[0][62] = getZjfs(); // 租金方式
				arrs[0][63] = et_zujin.getText().toString().trim();
				arrs[0][64] = select_nashui.getCodeId();
				arrs[0][65] = preferences.getString("login_admin_id", "");// 填表人
				arrs[0][66] = preferences.getString("login_number", "");// 管理员编码
				arrs[0][67] = (FormCheck.getDate(tv_tianbiaoriqi.getText()
						.toString()) + "000000").trim();// 填表日期
				arrs[0][68] = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime());// 登记日期
				arrs[0][69] = "phone";// 登记人
				arrs[0][70] = "phone";// 登记单位
				arrs[0][71] = "";// 备注
				arrs[0][72] = et_qitazhengjian.getText().toString().trim();// 其他
				arrs[0][73] = "";// 房屋建设性质其他
				arrs[0][74] = "";// 房屋建筑类别其他
				arrs[0][75] = et_qitachuzuyongtu.getText().toString().trim();
				arrs[0][76] = et_zhuanzurenqitazhengjian.getText().toString()
						.trim();
				arrs[0][77] = ""; // 关注类型
				arrs[0][78] = ""; // 行政区划扩展码，自然村
				arrs[0][79] = preferences.getString("login_service_id", ""); // 服务站登记编号
				arrs[0][80] = select_suoshuxiaqu.getCodeId(); // 所属辖区编号
				arrs[0][81] = select_fangwuleixing.getCodeId();
				arrs[0][82] = et_qitafangwuleixing.getText().toString().trim();
				arrs[0][83] = ""; // 是否导出
				arrs[0][84] = "1"; // 是否有效
				arrs[0][85] = ""; // 房屋编号
				arrs[0][86] = ""; // 出租用途个性化数据(大兴项目新加表的字段)
				arrs[0][87] = ""; // 房屋照片名称（大兴项目新加表的字段)
				arrs[0][88] = ""; // 房屋建筑面积
				arrs[0][89] = ""; // 房屋间数
				arrs[0][90] = ""; // 单位组织机构代码
				arrs[0][91] = preferences.getString("login_admin_id", "");// 填表人
				arrs[0][92] = preferences.getString("login_number", "");// 管理员编码
				arrs[0][93] = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime());// 登记日期
				arrs[0][94] = "phone";// 登记人
				arrs[0][95] = "phone";// 登记单位
				arrs[0][96] = "";// 备注
				arrs[0][97] = "";
				arrs[0][98] = ""; // 房主编号?
				arrs[0][99] = ""; // 房主类别?
				arrs[0][100] = "1"; // 是否有效
				arrs[0][101] = preferences.getString("login_xzqh", "");// 行政区划
				arrs[0][102] = "";
				arrs[0][103] = ""; // 转租人序号
				arrs[0][104] = "1"; // 是否有效
				arrs[0][105] = preferences.getString("login_xzqh", "");// 行政区划
				arrs[0][106] = "";
				arrs[0][107] = ""; // 房屋出租序号
				arrs[0][108] = "1"; // 是否有效
				arrs[0][109] = preferences.getString("login_xzqh", "");// 行政区划
				arrs[0][110] = ""; // 归档编号
				arrs[0][111] = ""; // 归档人
				arrs[0][112] = ""; // 归档日期
				arrs[0][113] = ""; // 归档原因
				arrs[0][114] = ""; // 归档登记人
				arrs[0][115] = ""; // 归档登记日期
				arrs[0][116] = ""; // 归档登记单位
				arrs[0][117] = ""; // 当前状态
				arrs[0][118] = ""; // 修改编号
				arrs[0][119] = ""; // 操作编号编号
				arrs[0][120] = ""; // 修改数据项
				arrs[0][121] = ""; // 修改前数据值
				arrs[0][122] = ""; // 修改后数据值
				arrs[0][123] = "";// 备注
				arrs[0][124] = preferences.getString("login_admin_id", "");// 填表人
				arrs[0][125] = preferences.getString("login_number", "");// 管理员编码
				arrs[0][126] = (FormCheck.getDate(tv_tianbiaoriqi.getText()
						.toString()) + "000000").trim();// 填表日期
				arrs[0][127] = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime());// 登记日期
				arrs[0][128] = "phone";// 登记人
				arrs[0][129] = "phone";// 登记单位
				arrs[0][130] = et_danweimingcheng.getText().toString().trim(); // 房主单位名称
				arrs[0][131] = et_fuzerenlianxidianhua.getText().toString()
						.trim(); // 单位联系电话
				arrs[0][132] = "";// 单位所在详细地址
				arrs[0][133] = "";// 区域代码
				arrs[0][134] = et_gongsimingcheng.getText().toString().trim();
				arrs[0][135] = et_fangwufuzerenlianxidianhua.getText()
						.toString().trim();
				arrs[0][136] = ""; // 经度
				arrs[0][137] = ""; // 纬度
				arrs[0][138] = select_fangwucengshu.getCodeId();
				arrs[0][139] = getAqyh();
				arrs[0][140] = ""; // 房屋不稳定风险描述
				arrs[0][141] = ""; // 房屋治安隐患描述
				arrs[0][142] = ""; // 房屋消防隐患描述
				arrs[0][143] = ""; // 房屋建筑安全描述
				arrs[0][144] = getAqyhyw();
				arrs[0][145] = "";// 群租房类型
				arrs[0][146] = et_fangwufuzerenxingming.getText().toString()
						.trim();
				arrs[0][147] = et_fuzerenzhengjianhaoma.getText().toString()
						.trim();
				if ("".equals(qiandingzerenshuriqi.getText().toString())) {
					arrs[0][148] = "";
				} else {
					arrs[0][148] = (FormCheck.getDate(qiandingzerenshuriqi
							.getText().toString()) + "000000").trim();
				}
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrs);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Dj_Activity.this,
						RequestCode.FWXXDJ, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler1.sendMessage(message);// 发送消息
				} else {
					DataCommon dataCommon = gson.fromJson(result,
							DataCommon.class);

					if ("09".equals(dataCommon.getZtbs())) {
						Message message1 = new Message();
						message1.what = 1;
						String[][] data3 = dataCommon.getData();
						message1.obj = data3[0][1];
						handler1.sendMessage(message1);// 发送消息
					} else if ("18".equals(dataCommon.getZtbs())) {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						handler1.sendMessage(message2);// 发送消息
					} else if ("10".equals(dataCommon.getZtbs())) {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						handler1.sendMessage(message3);// 发送消息
					} else if ("14".equals(dataCommon.getZtbs())) {
						Message message4 = new Message();// 创建消息
						message4.what = 4;// 设置消息的what值
						handler1.sendMessage(message4);// 发送消息
					} else {
						Message message5 = new Message();// 创建消息
						message5.what = 5;// 设置消息的what值
						message5.obj = dataCommon.getZtxx();
						handler1.sendMessage(message5);// 发送消息
					}
				}
			}
		}).start();
	}

	/**
	 * 操作成功
	 */
	private void sucess(String djbh) {
		resetData();
		new Builder(this).setTitle("提示")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("操作成功！自动生成房屋登记表序号：" + djbh + "\n是否继续采集房屋？")
				.setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Dj_Activity.this.finish();
					}
				})
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						initshow();

					}
				}).show();
	}

	/**
	 * 校验所有必填项
	 */
	private boolean checkAllForm() {
		if (!checkfwBasicInfo()) {
			return false;
		}
		if (!checkfzInfo()) {
			return false;
		}
		if (!checkczInfo()) {
			return false;
		}
		return true;
	}

	/**
	 * 校验出租信息必填项
	 */
	private boolean checkczInfo() {
		if (isNull(et_chuzujianshu, "出租间数")) {
			return false;
		}
		if (isNull(et_pingfangmi, "平方米数")) {
			return false;
		}
		if (isNull(select_fangwucengshu, "房屋层数")) {
			return false;
		}
		if ("1".equals(getAqyhyw())) {
			if ("".equals(getAqyh())) {
				StaticObject.showToast(this, "请勾选安全隐患类型!");
				return false;
			}
			if (cb_buwendingfengxian.isChecked()) {
				if ("".equals(getAqyhlx(bwdfx, "400"))) {
					StaticObject.showToast(this, "请勾选不稳定风险类型!");
					return false;
				}
			}
			if (cb_zhianyinhuan.isChecked()) {
				if ("".equals(getAqyhlx(zayh, "100"))) {
					StaticObject.showToast(this, "请勾选治安隐患类型!");
					return false;
				}
			}
			if (cb_xiaofangyinhuan.isChecked()) {
				if ("".equals(getAqyhlx(xfyh, "200"))) {
					StaticObject.showToast(this, "请勾选消防隐患类型!");
					return false;
				}
			}
			if (cb_qitaanquanyinhuan.isChecked()) {
				if ("".equals(getAqyhlx(qtyh, "300"))) {
					StaticObject.showToast(this, "请勾选其他安全隐患类型!");
					return false;
				}
			}

		}
		if (isNull(select_chuzuyongtu, "出租用途")) {
			return false;
		}
		if ("09".equals(select_chuzuyongtu.getCodeId())) {
			if (isNull(et_qitachuzuyongtu, "其他出租用途")) {
				return false;
			}
		}
		if (isNull(select_zulinhetong, "租赁合同")) {
			return false;
		}
		if (isNull(et_benshirenshu, "本市人数")) {
			return false;
		}
		if (isNull(et_waishengshirenshu, "外省市人数")) {
			return false;
		}
		if (isNull(et_gangaotairenshu, "港澳台人数")) {
			return false;
		}
		if (isNull(et_waijirenshu, "外籍人数")) {
			return false;
		}
		if (isNull(et_zujin, "租金")) {
			return false;
		}
		if (isNull(select_nashui, "纳税")) {
			return false;
		}
		if ("".equals(getSfqdzrs())) {
			StaticObject.showToast(this, "如果没有签订责任书请勾选”无“!");
			return false;
		}
		String qiandingzerenshu = qiandingzerenshuriqi.getText().toString()
				.trim();// 签订责任书日期
		String chuzuqishiriqi = tv_chuzuqishiriqi.getText().toString().trim();// 出租起始日期
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date()
				.getTime());// 当天日期
		if (!("".equals(qiandingzerenshuriqi.getText().toString().trim()))) {
			if (check_Date("yyyy-MM-dd", today, qiandingzerenshu)) {
				StaticObject.showToast(Dj_Activity.this, "签订责任书日期不能大于当前日期");
				return false;
			}
		}
		if (isNull(tv_chuzuqishiriqi, "出租起始日期")) {
			return false;
		}
		if (check_Date("yyyy-MM-dd", today, chuzuqishiriqi)) {
			StaticObject.showToast(Dj_Activity.this, "出租起始日期不能大于当前日期");
			return false;
		}
		return true;
	}

	/**
	 * 校验房主信息必填项
	 */
	private boolean checkfzInfo() {
		String[] id_check = FormCheck.check_Card_ID(et_zhengjianhaoma.getText()
				.toString().trim());
		if (isNull(select_suoyouquanleixing, "所有权类型")) {
			return false;
		}
		if ("2".equals(select_suoyouquanleixing.getCodeId())) {
			if (isNull(et_danweimingcheng, "单位名称")) {
				return false;
			}
			if (isNull(et_fuzerenlianxidianhua, "负责人联系电话")) {
				return false;
			}
		} else {
			if (isNull(et_fangzhuxingming, "房主姓名")) {
				return false;
			}
			if (isNull(select_zhengjianleixing, "证件类型")) {
				return false;
			}
			if ("01".equals(select_zhengjianleixing.getCodeId())) {
				if (isNull(et_zhengjianhaoma, "证件号码")) {
					return false;
				}

				if (id_check[0].equals("false")) {
					StaticObject.showToast(this, id_check[1]);
					et_zhengjianhaoma.requestFocus();
					return false;
				} else {
					et_zhengjianhaoma.setText(id_check[1]);
					chushengriqi.setText(FormCheck.getBirthday(id_check[1]));
					xingbie.setText(FormCheck.getSex(id_check[1]));
				}
			}
			if ("04".equals(select_zhengjianleixing.getCodeId())) {
				if (isNull(et_qitazhengjian, "其他证件")) {
					return false;
				}
			}
			if (isNull(select_xingbie, "性别")) {
				return false;
			}
			if (isNull(select_hujidi, "户籍地")) {
				return false;
			}
			if (isNull(et_lianxidianhua, "联系电话")) {
				return false;
			}
		}

		if (isNull(select_chuzurenleixing, "出租人类型")) {
			return false;
		}
		if ("2".equals(select_chuzurenleixing.getCodeId())) {
			if (isNull(et_gongsimingcheng, "公司名称")) {
				return false;
			}
			if (isNull(et_fangwufuzerenxingming, "房屋负责人姓名")) {
				return false;
			}

			if (isNull(et_fangwufuzerenlianxidianhua, "负责人联系电话")) {
				return false;
			}

		} else if ("3".equals(select_chuzurenleixing.getCodeId())) {
			if (isNull(et_zhuanzurenxingming, "转租人姓名")) {
				return false;
			}
			if (isNull(et_zhuanzurenlianxidianhua, "转租人联系电话")) {
				return false;
			}
			if ("01".equals(select_zhuanzurenzhengjianleixing.getCodeId())) {
				if (isNull(et_zhuanzurenzhengjianhaoma, "转租人证件号码")) {
					return false;
				}
				if (id_check[0].equals("false")) {
					StaticObject.showToast(this, id_check[1]);
					et_zhuanzurenzhengjianhaoma.requestFocus();
					return false;
				} else {
					et_zhuanzurenzhengjianhaoma.setText(id_check[1]);

				}
			}
		}
		return true;
	}

	/**
	 * 校验房屋基本信息必填项
	 */
	private boolean checkfwBasicInfo() {
		if (isNull(select_suoshuxiaqu, "所属辖区")) {
			return false;
		}
		if (isNull(tv_tianbiaoriqi, "填表日期")) {
			return false;
		}
		String tiaobiaoriqi = tv_tianbiaoriqi.getText().toString().trim();// 填表日期
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date()
				.getTime());// 当天日期
		if (check_Date("yyyy-MM-dd", today, tiaobiaoriqi)) {
			StaticObject.showToast(Dj_Activity.this, "填表日期不能大于当前日期");
			return false;
		}
		if (isNull(select_fangwuleixing, "房屋类型")) {
			return false;
		}
		if ("09".equals(select_fangwuleixing.getCodeId())) {
			if (isNull(et_qitafangwuleixing, "其他房屋类型")) {
				return false;
			}
		}

		if (isNull(et_fangwusuozaidizhi, "房屋所在地址")) {
			return false;
		}
		return true;
	}

	/**
	 * 日期选择器
	 * 
	 * @param tv
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
	 * 几个标题 点击后的反应：1改变下方可见 2改变右侧图标
	 * 
	 * @param linear
	 * @param tv
	 * @param id
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
	 * 校验EditText表单内容不为null和""
	 * 
	 * @param view
	 * @param name
	 * @return
	 */
	private boolean isNull(EditText view, String name) {
		if (view != null && "".equals(view.getText().toString().trim())) {
			view.requestFocus();
			InputMethodManager imm = (InputMethodManager) view.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
			StaticObject.showToast(this, "请填写" + name);
			return true;
		}
		return false;
	}

	/**
	 * 校验TextView表单内容不为null和""
	 * 
	 * @param view
	 * @param name
	 * @return
	 */
	private boolean isNull(TextView view, String name) {
		if (view != null && "".equals(view.getText().toString().trim())) {
			datePick(view);
			StaticObject.showToast(this, "请选择" + name);
			return true;
		}
		return false;
	}

	/**
	 * 校验时间起小于时间止
	 * 
	 * @param fmt
	 *            yyyy-MM-dd之类的格式
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 开始小于结束返回true,大于结束返回false
	 */
	private boolean check_Date(String fmt, String startDate, String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.CHINA);

			Date sd = sdf.parse(startDate);
			Date se = sdf.parse(endDate);

			if (se.before(sd) || se.equals(sd)) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * 校验SelectView表单内容不为null和""
	 * 
	 * @param view
	 * @param name
	 * @return
	 */
	private boolean isNull(SelectViewAndHandlerAndMsg select, String name) {
		if (select != null && "".equals(select.getCodeId())) {
			select.requestFocus();
			StaticObject.showToast(this, "请选择" + name);
			return true;
		}
		return false;
	}

	/**
	 * 获取签订责任书状态
	 */
	private String getSfqdzrs() {
		String sfqd = "";
		CheckBox[] cbBoxs = new CheckBox[] { cb_zhian, cb_xiaofang, cb_hunyu,
				cb_wu };
		for (int i = 0; i < cbBoxs.length; i++) {
			if (cbBoxs[i].isChecked()) {
				sfqd += (i + 1) + ",";
			}
		}
		if (sfqd.length() > 1) {
			sfqd = sfqd.substring(0, sfqd.length() - 1);
		}
		return sfqd;
	}

	/**
	 * 清空签订责任书状态
	 */
	private void cleanSfqdzrs() {

		CheckBox[] cbBoxs = new CheckBox[] { cb_zhian, cb_xiaofang, cb_hunyu,
				cb_wu };
		for (int i = 0; i < cbBoxs.length; i++) {
			cbBoxs[i].setChecked(false);
		}

	}

	/**
	 * 清空安全隐患状态
	 */
	private void cleanAqyh() {

		CheckBox[] cbBoxs = new CheckBox[] { cb_zhianyinhuan,
				cb_xiaofangyinhuan, cb_qitaanquanyinhuan, cb_buwendingfengxian };
		for (int i = 0; i < cbBoxs.length; i++) {
			cbBoxs[i].setChecked(false);
		}

	}

	/**
	 * 清空安全隐患类型
	 */
	private void cleanAqyhlx(LinearLayout ll) {
		for (int i = 0; i < ll.getChildCount(); i++) {
			CheckBox cb = (CheckBox) ll.getChildAt(i);
			cb.setChecked(false);
		}
	}

	/**
	 * 获取安全隐患类型
	 */
	private String getAqyhlx(LinearLayout ll, String code) {
		String aqyhlx = "";
		for (int i = 0; i < ll.getChildCount(); i++) {
			CheckBox cb = (CheckBox) ll.getChildAt(i);
			if (cb.isChecked()) {
				aqyhlx += code + (i + 1) + ",";
			}
		}
		if (aqyhlx.length() > 1) {
			aqyhlx = aqyhlx.substring(0, aqyhlx.length() - 1);
		}
		return aqyhlx;
	}

	/**
	 * 获取安全隐患状态
	 */
	private String getAqyh() {
		String aqyh = "";
		CheckBox[] cbBoxs = new CheckBox[] { cb_zhianyinhuan,
				cb_xiaofangyinhuan, cb_qitaanquanyinhuan, cb_buwendingfengxian };
		for (int i = 0; i < cbBoxs.length; i++) {
			if (cbBoxs[i].isChecked()) {
				aqyh += (i + 1) + ",";
			}
		}
		if (aqyh.length() > 1) {
			aqyh = aqyh.substring(0, aqyh.length() - 1);
		}
		return aqyh;
	}

	/**
	 * 获取安全隐患有无
	 */
	private String getAqyhyw() {
		String aqyhyw = "";
		int i = 0;
		RadioButton[] radioButtons = new RadioButton[] { aqfx, ygyk, ygjz };
		if (radioButtons[i].isChecked()) {
			aqyhyw = i + "";
		} else if (radioButtons[i + 1].isChecked()) {
			aqyhyw = (i + 1) + "";
		} else {
			aqyhyw = (i + 2) + "";
		}
		return aqyhyw;
	}

	/**
	 * 获取租金方式
	 */
	private String getZjfs() {
		String zjfs = "";
		int i = 0;
		RadioButton[] radioButtons = new RadioButton[] { yuezu, nianzu };
		if (radioButtons[i].isChecked()) {
			zjfs = i + "";
		} else {
			zjfs = (i + 1) + "";
		}
		return zjfs;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		buttonView.setChecked(isChecked);
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.fwdj_cb_wu:
				cb_zhian.setChecked(false);
				cb_xiaofang.setChecked(false);
				cb_hunyu.setChecked(false);
				break;
			case R.id.fwdj_cb_zhian:
				cb_wu.setChecked(false);
				break;
			case R.id.fwdj_cb_xiaofang:
				cb_wu.setChecked(false);
				break;
			case R.id.fwdj_cb_hunyu:
				cb_wu.setChecked(false);
				break;

			default:
				break;
			}
		}
		switch (buttonView.getId()) {
		case R.id.fwdj_cb_buwendingfengxian:
			if (isChecked) {
				bwdfx.setVisibility(View.VISIBLE);
			} else {
				bwdfx.setVisibility(View.GONE);
				cleanAqyhlx(bwdfx);
			}
			break;
		case R.id.fwdj_cb_zhianyinhuan:
			if (isChecked) {
				zayh.setVisibility(View.VISIBLE);
			} else {
				zayh.setVisibility(View.GONE);
				cleanAqyhlx(zayh);
			}
			break;
		case R.id.fwdj_cb_xiaofangyinhuan:
			if (isChecked) {
				xfyh.setVisibility(View.VISIBLE);
			} else {
				xfyh.setVisibility(View.GONE);
				cleanAqyhlx(xfyh);
			}
			break;
		case R.id.fwdj_cb_qitaanquanyinhuan:
			if (isChecked) {
				qtyh.setVisibility(View.VISIBLE);
			} else {
				qtyh.setVisibility(View.GONE);
				cleanAqyhlx(qtyh);
			}
			break;
		default:
			break;
		}

	}

}
