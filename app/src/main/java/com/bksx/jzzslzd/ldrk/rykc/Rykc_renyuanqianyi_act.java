package com.bksx.jzzslzd.ldrk.rykc;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rycj.Rycj_dengji;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Rykc_renyuanqianyi_act extends Activity {
	String result;
	Dialog dialog;
	int year;
	int month;
	int day;
	TextView btn_date;
	String[][] two_array = new String[1][18]; // 上一个界面已经处理了：// 姓名// 身份证// 性别//
												// 出生日期// 住所类型// 房屋编号// 房屋登记表序号
	String[] resultData;
	String[] leixing;
	HashMap<String, String> leixingId;
	Map<String, String> xiaquId;
	EditText et_xianzhudizhi;
	TextView tv_suoshuxiaqu;
	TextView tv_juzhuleixing;
	SharedPreferences mySharedPreferences;
	SelectViewAndHandlerAndMsg ssxq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rykc_renyuanqianyi);

		Intent intent = getIntent();
		two_array[0] = intent.getStringArrayExtra("qianyi");// 上一个界面处理的数据放入当前数组里
		resultData = intent.getStringArrayExtra("data");

		btn_date = (TextView) findViewById(R.id.rykc_renyuanqianyi_btn_date);
		TextView tv_xingming = (TextView) findViewById(R.id.rykc_renyuanqianyi_tv_xingming);
		TextView tv_yuanfuwuzhan = (TextView) findViewById(R.id.rykc_renyuanqianyi_tv_yuanfuwuzhan);
		TextView tv_yuanriqi = (TextView) findViewById(R.id.rykc_renyuanqianyi_tv_yuanriqi);
		tv_juzhuleixing = (TextView) findViewById(R.id.rykc_renyuanqianyi_tv_juzhuleixing);
		et_xianzhudizhi = (EditText) findViewById(R.id.rykc_renyuanqianyi_et_xianzhudizhi);
		tv_suoshuxiaqu = (TextView) findViewById(R.id.rykc_renyuanqianyi_tv_suoshuxiaqu);
		// 显示3个数据
		tv_xingming.setText(two_array[0][1]);
		tv_yuanfuwuzhan.setText(resultData[2]);
		tv_yuanriqi.setText(resultData[3].substring(0, 4) + "-"
				+ resultData[3].substring(4, 6) + "-"
				+ resultData[3].substring(6, 8));
		et_xianzhudizhi.setText(intent.getStringExtra("dizhi"));

		mySharedPreferences = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		initDate();
		forXiaqu();
		juzhuleixing();
		if (leixing.length > 1) {
			StaticObject.setSelectView(this, leixing, tv_juzhuleixing);

		}
		if (two_array[0][7].equals("02")) {
			tv_juzhuleixing.setText("租赁");
			tv_juzhuleixing.setClickable(false);
			et_xianzhudizhi.setEnabled(false);
		}
		// 选择日期按钮后 弹出日期选择器
		btn_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						Rykc_renyuanqianyi_act.this, dateListener, year,
						month - 1, day);
				dateDialog.show();
			}
		});
		findViewById(R.id.rykc_renyuanqianyi_btn_queding).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						makeData();
					}
				});
	}

	// 时间选择器的监听 选择时间后触发
	DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			year = arg1;
			month = arg2 + 1;
			day = arg3;
			btn_date.setText(year + "-" + month + "-" + day);
		}
	};

	private void initDate() {
		// 获得当前时间，显示在日期btn_date上
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		// 先显示出app所记录时间的值
		btn_date.setText(year + "-" + month + "-" + day);
	}

	private void forXiaqu() {
		String xiaqu = mySharedPreferences.getString("glyszxq", "");// 123123,绍兴县,123132,大东方
		LinkedHashMap<String, String> mymap = new LinkedHashMap<String, String>();
		String[] xq = xiaqu.split(",");
		for (int i = 0; i < xq.length; i++) {
			mymap.put(xq[i], xq[++i]);
		}
		ssxq = new SelectViewAndHandlerAndMsg(this, "所属辖区", mymap,
				tv_suoshuxiaqu, handler, 999, xq[0]);
	}

	private void juzhuleixing() {
		String sql = "select CD_ID,CD_NAME from SJCJ_D_ZSLX where CD_ID !='02' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable
				.getInstance(Rykc_renyuanqianyi_act.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			leixing = new String[l];
			leixingId = new HashMap<String, String>();
			int i = 0;
			while (c.moveToNext()) {
				leixing[i] = c.getString(c.getColumnIndex("CD_NAME"));
				leixingId.put(c.getString(c.getColumnIndex("CD_NAME")),
						c.getString(c.getColumnIndex("CD_ID")));
				i++;
			}
		}
		c.close();
		helper.close();
	}

	private void makeData() {
		// 个人编号
		two_array[0][0] = resultData[1];
		// 管理员编码
		two_array[0][5] = mySharedPreferences.getString("login_number", "");
		// 填报日期
		two_array[0][6] = "";
		// 来现住地日期
		two_array[0][10] = "" + year + (month < 10 ? "0" + month : month)
				+ (day < 10 ? "0" + day : day) + "000000";
		// 现住地址
		two_array[0][11] = et_xianzhudizhi.getText().toString().trim();
		// 填表人
		two_array[0][12] = mySharedPreferences.getString("login_admin_id", "");
		// 登记人
		two_array[0][13] = "phone";
		// 登记单位
		two_array[0][14] = mySharedPreferences.getString("login_rbac", "");
		// 所属服务站编号
		two_array[0][15] = mySharedPreferences
				.getString("login_service_id", "");
		// 所属辖区编号
		String suoshuxiaqu = ssxq.getCodeId();
		if ("".equals(suoshuxiaqu)) {
			StaticObject.showToast(this, "所属辖区不能为空");
			return;
		}
		two_array[0][16] = suoshuxiaqu;
		// 其他居住类型
		String juzhuleixing = tv_juzhuleixing.getText().toString();
		two_array[0][17] = two_array[0][7].equals("02") ? "" : leixingId
				.get(juzhuleixing);
		Ryxx ryxx = new Ryxx();
		ryxx.setData(two_array);
		ryxx.setZshs("0");
		sendData(ryxx);
	}

	private void sendData(final Ryxx ryxx) {
		dialog = StaticObject.showDialog(Rykc_renyuanqianyi_act.this,
				"发送数据中...");
		new Thread() {
			@Override
			public void run() {
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				result = StaticObject.getMessage(Rykc_renyuanqianyi_act.this,
						com.bksx.jzzslzd.common.RequestCode.RYXXQY, ryxxJson);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					handler.sendEmptyMessage(2);
					return;
				}
				Message msg = Message.obtain();
				msg.obj = result;
				msg.what = 1;
				handler.sendMessage(msg);
			}

		}.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				StaticObject.showToast(Rykc_renyuanqianyi_act.this, "网络连接失败");
				break;
			case 999:
				break;
			case 18:
				StaticObject.showToast(Rykc_renyuanqianyi_act.this, "市级接口连接失败");
				break;
			case 0:
				DataCommon dataCommon1 = (DataCommon) msg.obj;
				Intent intent = new Intent(Rykc_renyuanqianyi_act.this,
						Rycj_dengji.class);
				RyzcVo vo = new RyzcVo(dataCommon1.getData()[0]);
				if (dataCommon1.getData().length > 1) {
					vo.setJz_qjzdz(dataCommon1.getData()[1][0]);
				}
				if (vo.getJb_sfz().startsWith("X")) {
					StaticObject.showToast(Rykc_renyuanqianyi_act.this,
							"此人无身份证号码，请到流管平台进行修改或变更");
					return;
				}
				intent.setFlags(1);// 暂存0，补采1
				intent.putExtra("rybc", new Gson().toJson(vo));
				startActivityForResult(intent, 900);
				finish();
				break;
			case 1:
				Gson gson = new Gson();
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				if (dataCommon.getZtbs().equals("09")) {
					StaticObject.showToast(Rykc_renyuanqianyi_act.this, "迁移成功");
					skipBc();
				} else {
					StaticObject.showToast(Rykc_renyuanqianyi_act.this,
							dataCommon.getZtxx());
				}
				break;
			default:

				break;
			}
		}

	};

	private void skipBc() {
		dialog = StaticObject.showDialog(this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				String[][] arrayss = new String[1][2];
				// 个人编号
				arrayss[0][0] = resultData[1];
				// 服务站编号
				arrayss[0][1] = mySharedPreferences.getString(
						"login_service_id", "");
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayss);
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String result = StaticObject.getMessage(
						Rykc_renyuanqianyi_act.this, RequestCode.RYXXBCDJ,
						ryxxJson);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if (result.equals("")) {
					handler.sendEmptyMessage(2);
					return;
				}
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				Message msg = Message.obtain();
				if (dataCommon.getZtbs().equals("18")) {
					msg.what = 18;
				} else if (dataCommon.getZtbs().equals("0")) {
					msg.what = 0;
				}
				msg.obj = dataCommon;
				handler.sendMessage(msg);

			}

		}.start();
	}

}
