package com.bksx.jzzslzd.ldrk.rykc;

import java.io.File;
import java.util.LinkedHashMap;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.IDCard;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rycj.Rycj_dengji;
import com.bksx.jzzslzd.ldrk.ryhc.Ryhc_renyuanliebiao_act;
import com.bksx.jzzslzd.tools.BlueToolsControler;
import com.bksx.jzzslzd.tools.BluetoothDeviceTool;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Saomiao_a extends Activity {
	private TextView title, t_csrq, t_xb, t_mz, et_mz, t_hjdz;
	private EditText et_xm, et_sfz, et_hjdz;
	private SelectViewAndHandlerAndMsg s_mz;
	private SharedPreferences preferences;
	private Button zdjc, sdhc;
	private BlueToolsControler scanner;
	private ImageView photo;
	String[] qianyi = new String[18];// 下一个界面 人员迁移提交的数据
	private Intent intent_f;
	private AlertDialog dialog;
	private String ryhcDatas[][];
	private String sfsm = "0";
	private String photoPath = "";
	private BluetoothDeviceTool btdt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_saomiao);
		findViewById(R.id.ryjc_dengji_back).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View arg0) {
						finish();
					}
				});
		title = (TextView) findViewById(R.id.rykc_renyuanheshi_title);
		t_csrq = (TextView) findViewById(R.id.top_csrq);
		t_xb = (TextView) findViewById(R.id.top_xb);
		t_mz = (TextView) findViewById(R.id.mz_l);
		scanner = new BlueToolsControler(this, handler);
		et_xm = (EditText) findViewById(R.id.top_xm);
		et_sfz = (EditText) findViewById(R.id.top_zjhm);
		et_mz = (TextView) findViewById(R.id.top_mz);
		t_hjdz = (TextView) findViewById(R.id.top_hjdz_t);

		et_hjdz = (EditText) findViewById(R.id.top_hjdz);

		zdjc = (Button) findViewById(R.id.b_zdjc);
		sdhc = (Button) findViewById(R.id.b_sdhc);
		btdt = new BluetoothDeviceTool(this);
		zdjc.setOnLongClickListener(btdt);
		photo = (ImageView) findViewById(R.id.top_photo);
		intent_f = getIntent();
		switch (intent_f.getFlags()) {
		// 人员核查
		case 0:
			t_mz.setVisibility(View.INVISIBLE);
			et_mz.setVisibility(View.INVISIBLE);
			et_hjdz.setVisibility(View.GONE);
			t_hjdz.setVisibility(View.VISIBLE);
			break;
		// 人员快采（租赁）
		case 1:
			// 人员快采（其他）
		case 2:
			title.setText("快采身份信息");
			LinkedHashMap<String, String> mymap = forMinzu();
			s_mz = new SelectViewAndHandlerAndMsg(this, "民族", mymap, et_mz,
					handler, 999, "01");
			break;

		default:
			break;
		}

		preferences = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);

		// 身份证号 焦点改变后触发
		et_sfz.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1) {
					String[] id_check = FormCheck.check_Card_ID(et_sfz
							.getText().toString().trim());
					if ("false".equals(id_check[0])) {
						StaticObject.showToast(Saomiao_a.this, id_check[1]);
					}
					if ("true".equals(id_check[0])) {
						et_sfz.setText(id_check[1]);
						t_csrq.setText(FormCheck.getBirthday(id_check[1]));
						t_xb.setText(FormCheck.getSex(id_check[1]));
					}
				}
			}
		});
		et_sfz.setSelectAllOnFocus(true);
		et_xm.setSelectAllOnFocus(true);
		zdjc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				scanner.openScan();
			}
		});
		sdhc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				switch (intent_f.getFlags()) {
				// 人员核查
				case 0:
					ryhc();
					break;
				// 人员快采（租赁）
				case 1:
					// 人员快采（其他）
				case 2:
					makeData();
					break;
				}
			}
		});
	}

	/**
	 * 设清空页面top身份证处
	 */
	private void cleanTopMsg() {
		et_xm.setText("");
		t_csrq.setText("");
		et_hjdz.setText("");
		t_hjdz.setText("");
		et_mz.setText("");
		if (s_mz != null) {
			s_mz.setCodeId("01");
		}

		t_xb.setText("");
		et_sfz.setText("");
		sfsm = "0";
		photoPath = "";
		photo.setImageBitmap(null);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 110:
				IDCard card = (IDCard) msg.obj;
				if (card == null) {
					StaticObject.showToast(Saomiao_a.this, "扫描失败");
					return;
				}
				et_xm.setText(card.getXm());
				et_sfz.setText(card.getZjhm());
				t_csrq.setText(card.getCsrq());
				et_hjdz.setText(card.getHjdz());
				t_hjdz.setText(card.getHjdz());
				et_mz.setText(card.getMz());
				t_xb.setText(card.getXb());
				photoPath = card.getPhotoPath();
				photo.setImageBitmap(BitmapFactory.decodeFile(photoPath));
				sfsm = "1";
				switch (intent_f.getFlags()) {
				// 人员核查
				case 0:
					ryhc();
					break;
				// 人员快采（租赁）
				case 1:
					// 人员快采（其他）
				case 2:
					makeData();
					break;
				}
				break;
			case 2:
				StaticObject.showToast(Saomiao_a.this, "网络连接失败");
				break;
			// 人员快采提交返回
			case 9:
				Gson gson = new Gson();
				String result = (String) msg.obj;
				final DataCommon dataCommon = gson.fromJson(result,
						DataCommon.class);
				if (dataCommon.getZtbs().equals("09")) {
					StaticObject.showToast(Saomiao_a.this, "提交成功");
					new AlertDialog.Builder(Saomiao_a.this)
							.setTitle("提示")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setMessage(
									"快采提交成功，人员登记表序号为：" + dataCommon.getZtxx())
							.setPositiveButton("确定", null).show();
					cleanTopMsg();
				} else if (dataCommon.getZtbs().equals("6")) {
					StaticObject.showToast(Saomiao_a.this, "需要迁移");
					Intent intent = new Intent(Saomiao_a.this,
							Rykc_renyuanqianyi_act.class);

					// 迁移需要提交的数据，本界面已经处理了 // 姓名// 身份证// 性别// 出生日期// 住所类型//
					// 房屋编号// 房屋登记表序号
					intent.putExtra("qianyi", qianyi);
					if (intent_f.getExtras() != null) {
						intent.putExtra("dizhi", intent_f.getExtras()
								.getString("dizhi"));
					} else {
						intent.putExtra("dizhi", "");
					}

					intent.putExtra("data", dataCommon.getData()[0]);
					startActivity(intent);
					cleanTopMsg();
				} else if (dataCommon.getZtbs().equals("45")) {
					StaticObject.showToast(Saomiao_a.this, "该人已被注销");
					/*
					 * dialog = StaticObject.showDialog(Saomiao_a.this,
					 * "此人在本服务站已被注销，正在恢复中，请您等候！"); String[][] arrays = new
					 * String[1][4]; arrays[0][0] =
					 * dataCommon.getData()[0][1];// 个人编号 arrays[0][1] =
					 * "phone";// 登记人 arrays[0][2] =
					 * preferences.getString("login_rbac", "");// 登记单位
					 * arrays[0][3] = preferences .getString("login_service_id",
					 * "");// 服务站编号 Ryxx ryxx = new Ryxx();
					 * ryxx.setData(arrays); final String ryxxJson =
					 * gson.toJson(ryxx); new Thread() { public void run() {
					 * String result = StaticObject.getMessage( Saomiao_a.this,
					 * RequestCode.RYXXZXHF, ryxxJson); dialog.dismiss(); if
					 * ("".equals(result)) { handler.sendEmptyMessage(2);
					 * return; } Message msg = Message.obtain(); msg.what = 95;
					 * msg.obj = result; handler.sendMessage(msg); }; }.start();
					 */
				} else if (dataCommon.getZtbs().equals("06")) {
					StaticObject.showToast(Saomiao_a.this, "新增失败");
				} else if (dataCommon.getZtbs().equals("13")) {
					StaticObject.showToast(Saomiao_a.this, "身份证号码无效");
				} else if (dataCommon.getZtbs().equals("16")) {
					StaticObject
							.showToast(Saomiao_a.this, dataCommon.getZtxx());
				} else if (dataCommon.getZtbs().equals("02")) {
					StaticObject.showToast(Saomiao_a.this, "因死亡注销");
				} else if (dataCommon.getZtbs().equals("18")) {
					StaticObject.showToast(Saomiao_a.this, "市级接口连接失败");
				} else if (dataCommon.getZtbs().equals("0")) {
					StaticObject.showToast(Saomiao_a.this, "该人已被快采过");
					new AlertDialog.Builder(Saomiao_a.this)
							.setTitle("提示")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setMessage("此人已被快采录入过，是否进入补采？")
							.setNeutralButton("否", null)
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													Saomiao_a.this,
													Rycj_dengji.class);
											RyzcVo vo = new RyzcVo(dataCommon
													.getData()[0]);
											if (vo.getJb_sfz().startsWith("X")) {
												StaticObject
														.showToast(
																Saomiao_a.this,
																"此人无身份证号码，请到流管平台进行修改或变更");
												return;
											}
											intent.setFlags(1);// 暂存0，补采1
											intent.putExtra("rybc",
													new Gson().toJson(vo));
											startActivity(intent);
										}
									}).show();

				}
				break;
			// 人员核查结果返回
			case 10:
				Gson gson1 = new Gson();
				String result1 = (String) msg.obj;
				DataCommon dataCommon1 = gson1.fromJson(result1,
						DataCommon.class);
				if ("18".equals(dataCommon1.getZtbs())) {
					StaticObject.showToast(Saomiao_a.this, "市级接口连接失败");
				} else if ("37".equals(dataCommon1.getZtbs())) {
					StaticObject.showToast(Saomiao_a.this, "无人员信息");
				} else if ("09".equals(dataCommon1.getZtbs())) {
					StaticObject.showToast(Saomiao_a.this, "操作成功");
					Intent intent = new Intent(Saomiao_a.this,
							Ryhc_renyuanliebiao_act.class);
					intent.putExtra("data", result1);
					intent.putExtra("fromA", "ryhc");
					intent.putExtra("arrayss", ryhcDatas[0]);
					startActivity(intent);
					cleanTopMsg();
				} else if ("02".equals(dataCommon1.getZtbs())) {
					StaticObject.showToast(Saomiao_a.this, "此人因死亡已被注销");
				} else if ("01".equals(dataCommon1.getZtbs())) {
					StaticObject.showToast(Saomiao_a.this, "此人已被注销");
				}
				break;
			/*
			 * case 95: // 注销恢复 String zx_result = (String) msg.obj; DataCommon
			 * zx_DataCommon = new Gson().fromJson(zx_result, DataCommon.class);
			 * String[][] zxgetData = zx_DataCommon.getData(); if (zxgetData ==
			 * null) { StaticObject.showToast(Saomiao_a.this, "返回结果解析错误");
			 * return; } // 获取登录状态号 String get_zxstate = zxgetData[0][0].trim();
			 * // 从表中查询出状态号对应的状态名称 if ("18".equals(get_zxstate)) {
			 * StaticObject.showToast(Saomiao_a.this, "市级接口连接失败"); } else if
			 * ("09".equals(get_zxstate)) {
			 * StaticObject.showToast(Saomiao_a.this, "注销恢复成功"); cleanTopMsg();
			 * } else if ("44".equals(get_zxstate)) {
			 * StaticObject.showToast(Saomiao_a.this, "注销恢复失败"); } break;
			 */
			default:
				break;
			}

		};
	};

	private void ryhc() {
		ryhcDatas = new String[1][6];

		// 姓名
		ryhcDatas[0][0] = et_xm.getText().toString().trim();

		// 身份证号码
		if (et_sfz.getText().toString().trim().equals("")) {
			ryhcDatas[0][1] = "";
		} else {
			String[] id_check = FormCheck.check_Card_ID(et_sfz.getText()
					.toString().trim());
			if (id_check[0].equals("false")) {
				StaticObject.showToast(this, id_check[1]);
				return;
			}
			ryhcDatas[0][1] = id_check[1];
			et_sfz.setText(id_check[1]);
		}
		// 性别
		ryhcDatas[0][2] = "";
		// 出生日期
		ryhcDatas[0][3] = "";
		// 服务站编号
		ryhcDatas[0][4] = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE).getString("login_service_id", "");
		ryhcDatas[0][5] = "1";

		// .........
		Ryxx ryxx = new Ryxx();
		ryxx.setData(ryhcDatas);
		sendData(ryxx, RequestCode.RYXXHC);
	}

	private LinkedHashMap<String, String> forMinzu() {
		String sql = "select CD_ID,CD_NAME from CDG_NATION where CD_AVAILABILITY='1' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(Saomiao_a.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		LinkedHashMap<String, String> mz = new LinkedHashMap<String, String>();
		if (l > 0) {
			while (c.moveToNext()) {
				mz.put(c.getString(c.getColumnIndex("CD_ID")),
						c.getString(c.getColumnIndex("CD_NAME")));
			}
		}
		c.close();
		helper.close();
		return mz;
	}

	private void makeData() {
		String[][] arrayss = new String[1][17];
		// 姓名
		String name = et_xm.getText().toString().trim();
		if ("".equals(name)) {
			StaticObject.showToast(this, "请填写姓名");
			et_xm.requestFocus();
			return;
		}
		arrayss[0][0] = name;
		qianyi[1] = arrayss[0][0];
		// 身份证号码
		String[] id_check = FormCheck.check_Card_ID(et_sfz.getText().toString()
				.trim());
		if (id_check[0].equals("false")) {
			StaticObject.showToast(this, id_check[1]);
			return;
		}
		arrayss[0][1] = id_check[1];// ...............................................
		qianyi[2] = arrayss[0][1];
		// 性别男1 女2
		arrayss[0][2] = (FormCheck.getSex(arrayss[0][1]).equals("男") ? 1 : 2)
				+ "";
		qianyi[3] = arrayss[0][3];
		// 民族
		arrayss[0][3] = s_mz.getCodeId();
		// 出生日期(yyyyMMddHHmmss)
		arrayss[0][4] = arrayss[0][1].substring(6, 14) + "000000";
		qianyi[4] = arrayss[0][4];
		// 户籍地址]
		String address = et_hjdz.getText().toString().trim();
		if (address.equals("")) {
			StaticObject.showToast(this, "户籍地址不能为空");
			return;
		}
		arrayss[0][5] = address;
		if (intent_f.getExtras() == null) {
			arrayss[0][6] = "99";
			arrayss[0][12] = "";
			// 房屋登记表序号
			arrayss[0][13] = "";
			// 房屋地址
			arrayss[0][15] = "";
		} else {
			arrayss[0][6] = "02";
			// 房屋编号
			arrayss[0][12] = intent_f.getExtras().getString("bianhao");
			// 房屋登记表序号
			arrayss[0][13] = intent_f.getExtras().getString("xuhao");
			// 房屋地址
			arrayss[0][15] = intent_f.getExtras().getString("dizhi");
		}
		// 居住类型 02/99
		arrayss[0][6] = (intent_f.getExtras() == null ? "99" : "02");
		qianyi[7] = arrayss[0][6];
		// 服务站编号
		arrayss[0][7] = preferences.getString("login_service_id", "");
		// 管理员编码
		arrayss[0][8] = preferences.getString("login_number", "");
		// 管理员编号
		arrayss[0][9] = preferences.getString("login_admin_id", "");

		// 登记日期
		arrayss[0][10] = "";
		// 登记单位
		arrayss[0][11] = preferences.getString("login_rbac", "");

		qianyi[8] = arrayss[0][12];

		qianyi[9] = arrayss[0][13];

		// 身份证扫了没？ 0为没 1为扫了~~
		arrayss[0][14] = sfsm;

		// 照片信息 默认为"" (先给默认值就行)
		arrayss[0][16] = "";
		if ("1".equals(sfsm) && !"".equals(photoPath)) {
			arrayss[0][16] = StaticObject.RFTString(new File(photoPath));
		}

		Ryxx ryxx = new Ryxx();
		ryxx.setData(arrayss);
		ryxx.setZshs("0");

		sendData(ryxx, RequestCode.RYKCHS);

	}

	private void sendData(final Ryxx ryxx, final String ywdm) {
		try {
			dialog = StaticObject.showDialog(Saomiao_a.this, "发送数据中...");
			new Thread() {
				@Override
				public void run() {
					Gson gson = new Gson();
					String ryxxJson = gson.toJson(ryxx);
					String result = StaticObject.getMessage(Saomiao_a.this,
							ywdm, ryxxJson);
					dialog.dismiss();
					if (RequestCode.CSTR.equals(result)) {
						return;
					}
					if ("".equals(result)) {
						handler.sendEmptyMessage(2);
						return;
					}
					if (RequestCode.RYXXHC.equals(ywdm)) {
						Message msg = Message.obtain();
						msg.obj = result;
						msg.what = 10;
						handler.sendMessage(msg);
					} else {
						Message msg = Message.obtain();
						msg.obj = result;
						msg.what = 9;
						handler.sendMessage(msg);
					}

				}

			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void finish() {
		scanner.closeDialog();
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		super.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == btdt.REQUESTCODE) {
			// TODO
			btdt.selectBTAddress();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
