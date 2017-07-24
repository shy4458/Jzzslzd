package com.bksx.jzzslzd.syfw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.DBData;
import com.bksx.jzzslzd.tools.SelectView;
import com.google.gson.Gson;

import java.util.LinkedHashMap;

/**
 * 注销房屋信息
 * 
 * @author Administrator
 * 
 */
public class Zx_Activity extends Activity {
	private ImageButton fwzx_back;
	private Button fwzx_queding;
	private TextView fwzx_fzName, fwzx_info, fwzx_ryzxReason,
			tv_fwzx_ryzxReason, fwzx_fwzxReason;
	private String xingming, bianhao, info;
	private SelectView selectView_fwzxReason, selectView_ryzxReason;
	private String[][] array;
	private SharedPreferences preferences;
	private String result;
	private ProgressDialog dialog;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				StaticObject.showToast(Zx_Activity.this, "注销成功");
				setResult(901);
				finish();
				break;
			case 2:
				StaticObject.showToast(Zx_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Zx_Activity.this, "房屋里有暂存人员,不能进行房屋注销");
				break;
			case 4:
				StaticObject.showToast(Zx_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Zx_Activity.this, "网络连接失败");
				break;

			default:
				dialog.dismiss();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_fangwuzhuxiao_a);
		initObject();
		show();

		// 返回
		fwzx_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Zx_Activity.this.finish();
			}
		});
		// 确定
		final Builder builder = new AlertDialog.Builder(Zx_Activity.this);
		fwzx_queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				builder.setTitle("房屋注销");
				builder.setMessage("确定注销房屋?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if ("0".equals(info)) {
									sendData();
								} else if ("10".equals(selectView_ryzxReason
										.getCodeId())) {
									builder.setTitle("人员注销");
									builder.setMessage("确定为该房屋所有住户进行死亡注销?");
									builder.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													sendData();
												}
											});
									builder.setNegativeButton(
											"取消",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
												}
											});
									builder.create().show();
								} else {
									sendData();
								}
							}

						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		});

	}

	/**
	 * 显示
	 */
	private void show() {
		Intent intent = getIntent();
		bianhao = intent.getStringExtra("bianhao");
		info = intent.getStringExtra("info");
		xingming = intent.getStringExtra("name");
		if (xingming == null || "".equals(xingming)) {
			fwzx_fzName.setText("无");
		} else {
			fwzx_fzName.setText(xingming);
		}
		if ("0".equals(info) || "".equals(info) || info == null) {
			fwzx_info.setText("该房屋共有0人,将同时被注销.");
			fwzx_ryzxReason.setVisibility(View.GONE);
			tv_fwzx_ryzxReason.setVisibility(View.GONE);
			LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
			String sql1 = "select CD_ID,CD_NAME from SJCJ_D_FWZXYY order by CD_INDEX";
			DBData.getData(sql1, map1);
			selectView_fwzxReason = new SelectView(Zx_Activity.this, map1,
					fwzx_fwzxReason);
			selectView_fwzxReason.setCodeId("10");
		} else {
			fwzx_info.setText("该房屋共有" + info + "人.");
			LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
			String sql1 = "select CD_ID,CD_NAME from SJCJ_D_FWZXYY order by CD_INDEX";
			DBData.getData(sql1, map1);
			selectView_fwzxReason = new SelectView(Zx_Activity.this, map1,
					fwzx_fwzxReason);
			selectView_fwzxReason.setCodeId("10");
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			String sql2 = "select CD_ID,CD_NAME from SJCJ_D_ZXYY order by CD_INDEX";
			DBData.getData(sql2, map2);
			selectView_ryzxReason = new SelectView(Zx_Activity.this, map2,
					tv_fwzx_ryzxReason);
			selectView_ryzxReason.setCodeId("10");
		}
	}

	/**
	 * 初始化对象
	 */
	private void initObject() {
		fwzx_back = (ImageButton) findViewById(R.id.fwzx_back);
		fwzx_queding = (Button) findViewById(R.id.fwzx_queding);
		fwzx_fzName = (TextView) findViewById(R.id.fwzx_fzName);
		fwzx_info = (TextView) findViewById(R.id.fwzx_info);
		fwzx_ryzxReason = (TextView) findViewById(R.id.fwzx_ryzxReason);
		tv_fwzx_ryzxReason = (TextView) findViewById(R.id.textView_fwzx_ryzxReason);
		fwzx_fwzxReason = (TextView) findViewById(R.id.textView_fwzx_fwzxReason);

	}

	/**
	 * 提交
	 */
	private void sendData() {
		dialog = StaticObject.showDialog(Zx_Activity.this, "注销中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				preferences = Zx_Activity.this.getSharedPreferences(
						StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
				array = new String[1][8];
				array[0][0] = bianhao;
				array[0][1] = selectView_fwzxReason.getCodeId();
				array[0][2] = preferences.getString("login_service_id", "");
				array[0][3] = preferences.getString("login_govern_id", "");
				array[0][4] = preferences.getString("login_admin_id", "");
				array[0][5] = preferences.getString("login_number", "");
				array[0][6] = preferences.getString("login_rbac", "");
				if (selectView_ryzxReason == null) {
					array[0][7] = "";
				} else {
					array[0][7] = selectView_ryzxReason.getCodeId();
				}
				Ryxx ryxx = new Ryxx();
				ryxx.setData(array);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Zx_Activity.this,
						RequestCode.FWZX, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					DataCommon dataCommon1 = gson.fromJson(result,
							DataCommon.class);

					if ("09".equals(dataCommon1.getZtbs())) {
						Message message1 = new Message();
						message1.what = 1;
						handler.sendMessage(message1);// 发送消息

					} else if ("18".equals(dataCommon1.getZtbs())) {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						handler.sendMessage(message2);// 发送消息
					} else if ("41".equals(dataCommon1.getZtbs())) {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						handler.sendMessage(message3);// 发送消息
					} else {
						Message message4 = new Message();// 创建消息
						message4.what = 4;// 设置消息的what值
						message4.obj = dataCommon1.getZtxx();
						handler.sendMessage(message4);// 发送消息
					}

				}
			}
		}).start();
	}
}
