package com.bksx.jzzslzd.ldrk.ryhc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Ryhc_zhuxiao_act extends Activity {
	TextView tv_likai;
	String[] yy;
	Map<String, String> yyId;
	Dialog dialog;
	Bundle bundle;
	String[] mapAll = { "xingming", "xingbie", "id", "grdjbh", "hjdz", "ljyy",
			"dh", "xzdz", "jydw", "idxx", "grbh", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_zhuxiao);

		bundle = getIntent().getExtras();
		forYy();

		TextView tv_xingming = (TextView) findViewById(R.id.ryhc_zhuxiao_tv_xingming);
		TextView tv_xingbie = (TextView) findViewById(R.id.ryhc_zhuxiao_tv_xingbie);
		tv_likai = (TextView) findViewById(R.id.ryhc_zhuxiao_likai);
		tv_xingming.setText(FormCheck.subString(
				(String) bundle.get("xingming"), 8));
		tv_xingbie.setText(bundle.getString("xingbie"));
		tv_likai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				StaticObject.setSelectView(Ryhc_zhuxiao_act.this, yy, tv_likai);
			}
		});
		findViewById(R.id.ryhc_zhuxiao_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// 确定要进行死亡注销？
						if (!yyId.get(tv_likai.getText().toString()).equals(
								"10")) {

							makeData();
							return;
						}
						AlertDialog.Builder builder = new AlertDialog.Builder(
								Ryhc_zhuxiao_act.this);
						builder.setMessage("确定要进行死亡注销？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										makeData();
									}
								});
						builder.setNegativeButton("取消", null);
						builder.create().show();

					}
				});
		findViewById(R.id.ryhc_zhuxiao_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
	}

	@SuppressLint("SimpleDateFormat")
	private void makeData() {
		String[][] arrayss = new String[1][18];
		SharedPreferences mySharedPreferences = getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		bundle.putString("xingbie", (bundle.get("xingbie").equals("男")) ? "1"
				: "2");

		for (int i = 0; i < 18; i++) {
			arrayss[0][i] = "";
		}
		// for (int j = 0; j < mapAll.length; j++) {
		// if (bundle.getString(mapAll[j]).equals(null)) {
		// bundle.putString(mapAll[j], "");
		// }
		// }

		arrayss[0][0] = bundle.getString(mapAll[10]);
		// 性别
		arrayss[0][1] = bundle.getString(mapAll[1]);
		// 出生日期
		arrayss[0][2] = bundle.getString(mapAll[11]);
		// 是否家庭式流入
		arrayss[0][3] = bundle.getString(mapAll[15]);
		// 是否户主
		arrayss[0][4] = bundle.getString(mapAll[12]);
		// 户ID
		arrayss[0][5] = bundle.getString(mapAll[13]);
		// 与户主关系
		arrayss[0][6] = bundle.getString(mapAll[14]);
		// 户主登记表序号
		arrayss[0][7] = bundle.getString(mapAll[16]);
		// 填表人
		arrayss[0][8] = mySharedPreferences.getString("login_admin_id", "");
		// 管理员编码
		arrayss[0][9] = mySharedPreferences.getString("login_number", "");
		// 行政区划 2015-6-12新增的
		arrayss[0][10] = mySharedPreferences.getString("login_xzqh", "");
		// 所属服务站编号
		arrayss[0][11] = mySharedPreferences.getString("login_service_id", "");
		// 所属辖区编号
		arrayss[0][12] = bundle.getString(mapAll[19]);

		// 注销原因
		arrayss[0][13] = yyId.get(tv_likai.getText().toString());

		// 所属单位
		arrayss[0][15] = mySharedPreferences.getString("login_rbac", "");

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = format.format(new Date());
		// 注销时间
		arrayss[0][16] = nowTime;

		// .........
		Ryxx ryxx = new Ryxx();
		ryxx.setData(arrayss);
		// ryxx.setZshs("0");
		sendData(ryxx);
	}

	private void sendData(final Ryxx ryxx) {
		dialog = StaticObject.showDialog(Ryhc_zhuxiao_act.this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String result = StaticObject.getMessage(Ryhc_zhuxiao_act.this,
						com.bksx.jzzslzd.common.RequestCode.RYXXHX, ryxxJson);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if (result.equals("")) {
					// handler.sendEmptyMessage(2);
					return;
				}
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				Message msg = Message.obtain();
				if (dataCommon.getZtbs().equals("09")) {
					msg.what = 9;
				} else {
					msg.what = 10;
				}
				msg.obj = dataCommon.getZtxx();
				handler.sendMessage(msg);

			}

		}.start();
	}

	private void forYy() {
		String sql = "select CD_ID,CD_NAME from SJCJ_D_ZXYY where CD_AVAILABILITY='1' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(Ryhc_zhuxiao_act.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			yy = new String[l];
			yyId = new HashMap<String, String>();
			int i = 0;
			while (c.moveToNext()) {
				yy[i] = c.getString(c.getColumnIndex("CD_NAME"));
				yyId.put(c.getString(c.getColumnIndex("CD_NAME")),
						c.getString(c.getColumnIndex("CD_ID")));
				i++;
			}
		}
		c.close();
		helper.close();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 9) {
				StaticObject.showToast(Ryhc_zhuxiao_act.this, "注销成功");
				setResult(901);
				finish();
			} else {
				StaticObject.showToast(Ryhc_zhuxiao_act.this, (String) msg.obj);
			}
		}

	};

	@Override
	public void finish() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		super.finish();
	}

}
