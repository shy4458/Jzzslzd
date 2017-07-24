package com.bksx.jzzslzd.ldrk.rykc;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.BzdzSelector;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Rykc_fangzhu_act extends Activity {
	private LinearLayout linear2;
	private LinearLayout linear3, linear4;
	private EditText et_xingming;
	private AutoCompleteTextView au_dz;
	private EditText et_dz;
	private EditText et_xuhao;
	private TextView tv;
	private String[] tv_tag = new String[] { "租赁", "其他" };
	private String result;
	private ProgressDialog dialog;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 2) {
				// result返回字符串为null
				StaticObject.showToast(Rykc_fangzhu_act.this, "网络连接失败");
				return;
			}
			if (msg.obj.equals("18")) {
				StaticObject.showToast(Rykc_fangzhu_act.this, "连接市级接口失败");
			} else if (msg.obj.equals("26")) {
				StaticObject.showToast(Rykc_fangzhu_act.this, "未找到符合条件的房屋信息");
			} else if (msg.obj.equals("09")) {
				StaticObject.showToast(Rykc_fangzhu_act.this, "操作成功");
				Intent intent = new Intent(Rykc_fangzhu_act.this,
						Rykc_fangwuliebiao_act.class);
				intent.putExtra("data", result);
				startActivity(intent);
				finish();
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rykc_fangzhu);

		linear2 = (LinearLayout) findViewById(R.id.rykc_fangzhu_linear2);
		linear3 = (LinearLayout) findViewById(R.id.rykc_fangzhu_linear3);
		linear4 = (LinearLayout) findViewById(R.id.rykc_fangzhu_linear4);
		tv = (TextView) findViewById(R.id.rykc_fangzhu_tv);
		et_xingming = (EditText) findViewById(R.id.rykc_fangzhu_et_xingming);
		au_dz = (AutoCompleteTextView) findViewById(R.id.rykc_fangzhu_au_dz);
		et_dz = (EditText) findViewById(R.id.rykc_fangzhu_et_dz);
		new BzdzSelector(this, au_dz, et_dz, true);
		et_xuhao = (EditText) findViewById(R.id.rykc_fangzhu_et_xuhao);
		findViewById(R.id.rykc_fangzhu_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
		// 重置按钮，点击清空数据
		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Rykc_fangzhu_act.this)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setTitle("请选择")
						.setItems(tv_tag,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										tv.setText(tv_tag[which]);
										if (which == 0) {
											linear2.setVisibility(View.VISIBLE);
											linear3.setVisibility(View.VISIBLE);
											linear4.setVisibility(View.VISIBLE);
										} else if (which == 1) {
											linear2.setVisibility(View.INVISIBLE);
											linear3.setVisibility(View.INVISIBLE);
											linear4.setVisibility(View.INVISIBLE);
										}
									}
								}).show();
			}
		});
		findViewById(R.id.rykc_fangzhu_btn_chongzhi).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						et_xingming.setText("");
						et_xuhao.setText("");
						et_dz.setText("");
						new BzdzSelector(Rykc_fangzhu_act.this, au_dz, et_dz,
								true);
					}
				});
		// 下一步按钮
		findViewById(R.id.rykc_fangzhu_btn_next).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (tv.getText().equals(tv_tag[0])) {
							fwxx_cx();

						} else if (tv.getText().equals(tv_tag[1])) {

							Intent intent = new Intent(Rykc_fangzhu_act.this,
									Saomiao_a.class);
							intent.setFlags(2);
							startActivity(intent);
							Rykc_fangzhu_act.super.finish();
						}

					}
				});

	}

	// 房屋信息查询 方法
	public void fwxx_cx() {
		final String xingming = et_xingming.getText().toString().trim();
		final String xuhao = et_xuhao.getText().toString().trim();

		/*
		 * if (xingming.equals("") && xuhao.equals("")) {
		 * StaticObject.showToast(this, "房主姓名和登记表序号不能同时为空"); return; }
		 */
		dialog = StaticObject.showDialog(Rykc_fangzhu_act.this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				String[][] two_array = new String[1][4];
				// 姓名/单位名称
				two_array[0][0] = xingming;
				// 房屋登记表序号
				two_array[0][1] = xuhao;
				// two_array[0][2] = "bc246be8b1e400";
				two_array[0][2] = getSharedPreferences(
						StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE)
						.getString("login_service_id", "");
				two_array[0][3] = et_dz.getText().toString().trim();
				// ======================== 数据传输 开始
				Ryxx ryxx = new Ryxx();
				ryxx.setData(two_array);
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				result = StaticObject.getMessage(Rykc_fangzhu_act.this,
						com.bksx.jzzslzd.common.RequestCode.FWXXCX, ryxxJson);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result.trim())) {
					handler.sendEmptyMessage(2);
					return;
				}
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				Message msg = Message.obtain();
				msg.obj = dataCommon.getZtbs();
				handler.sendMessage(msg);
			}

		}.start();

		return;
	}

	@Override
	public void finish() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		super.finish();
	}

}
