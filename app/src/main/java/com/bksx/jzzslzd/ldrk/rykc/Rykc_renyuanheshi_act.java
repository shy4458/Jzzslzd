package com.bksx.jzzslzd.ldrk.rykc;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.IDCard;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

public class Rykc_renyuanheshi_act extends Activity {
	String dizhi;
	Bundle three;
	String[] minzu;
	String[] qianyi = new String[18];// 下一个界面 人员迁移提交的数据
	Map<String, String> minzuId;
	String result;
	ProgressDialog dialog;

	SharedPreferences preference;
	EditText et_name;
	EditText et_sex;
	EditText et_address;
	EditText et_birth;
	EditText et_ID;

	TextView et_minzu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rykc_renyuanheshi_act);
		TextView title = (TextView) findViewById(R.id.rykc_renyuanheshi_title);
		title.setText("快采身份信息");
		three = getIntent().getExtras();

		Button btn = (Button) findViewById(R.id.rykc_renyuanheshi_btn);
		et_name = (EditText) findViewById(R.id.rykc_et1);
		et_sex = (EditText) findViewById(R.id.rykc_et2);
		et_minzu = (TextView) findViewById(R.id.rykc_et3);
		et_address = (EditText) findViewById(R.id.rykc_et4);
		et_birth = (EditText) findViewById(R.id.rykc_et5);
		et_ID = (EditText) findViewById(R.id.rykc_et6);
		ImageView iv = (ImageView) findViewById(R.id.rykc_touxiang);
		findViewById(R.id.rykc_renyuanheshi_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		preference = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		forMinzu();
		if (minzu.length > 1) {
			StaticObject.setSelectView(this, minzu, et_minzu);
		}

		// 身份证号 焦点改变后触发
		et_ID.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				makeData();
			}
		});
		IDCard card = (IDCard) getIntent().getSerializableExtra("IDCard");
		if (card != null) {
			et_name.setText(card.getXm());
			et_name.setEnabled(false);
			et_sex.setText(card.getXb());
			et_sex.setEnabled(false);
			et_minzu.setText(card.getMz());
			et_minzu.setClickable(false);
			et_address.setText(card.getHjdz());
			et_address.setEnabled(false);
			et_birth.setText(card.getCsrq());
			et_ID.setText(card.getZjhm());
			et_ID.setEnabled(false);
			iv.setImageBitmap(BitmapFactory.decodeFile(card.getPhotoPath()));
			iv.setScaleType(ScaleType.FIT_XY);
			// iv.setBackgroundDrawable(new
			// BitmapDrawable(card.getPhotoPath()));
			// BitmapFactory.decodeFile("", new Options());
		}
	}

	private void forMinzu() {
		String sql = "select CD_ID,CD_NAME from CDG_NATION where CD_AVAILABILITY='1' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable
				.getInstance(Rykc_renyuanheshi_act.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			minzu = new String[l];
			minzuId = new HashMap<String, String>();
			int i = 0;
			while (c.moveToNext()) {
				minzu[i] = c.getString(c.getColumnIndex("CD_NAME"));
				minzuId.put(c.getString(c.getColumnIndex("CD_NAME")),
						c.getString(c.getColumnIndex("CD_ID")));
				i++;
			}
		}
		c.close();
		helper.close();
	}

	private void makeData() {
		// 民族选择的位置
		// String mzid = minzuId[et_minzu.getSelectedItemPosition()][0];
		String[][] arrayss = new String[1][17];
		// 姓名
		String name = et_name.getText().toString().trim();
		if (name.equals("")) {
			StaticObject.showToast(this, "姓名不能为空");
			return;
		}
		arrayss[0][0] = name;
		qianyi[1] = arrayss[0][0];
		// 身份证号码
		String[] id_check = FormCheck.check_Card_ID(et_ID.getText().toString()
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
		arrayss[0][3] = minzuId.get(et_minzu.getText());
		// 出生日期(yyyyMMddHHmmss)
		arrayss[0][4] = arrayss[0][1].substring(6, 14) + "000000";
		qianyi[4] = arrayss[0][4];
		// 户籍地址]
		String address = et_address.getText().toString().trim();
		if (address.equals("")) {
			StaticObject.showToast(this, "户籍地址不能为空");
			return;
		}
		arrayss[0][5] = address;
		// 居住类型 02/99
		arrayss[0][6] = (three.getString("bianhao") == ""
				&& three.getString("xuhao") == ""
				&& three.getString("dizhi") == "" ? "99" : "02");
		qianyi[7] = arrayss[0][6];
		// 服务站编号
		arrayss[0][7] = preference.getString("login_service_id", "");
		// 管理员编码
		arrayss[0][8] = preference.getString("login_number", "");
		// 管理员编号
		arrayss[0][9] = preference.getString("login_admin_id", "");

		// 登记日期
		arrayss[0][10] = "";
		// 登记单位
		arrayss[0][11] = preference.getString("login_rbac", "");
		// 房屋编号
		arrayss[0][12] = three.getString("bianhao");
		qianyi[8] = arrayss[0][12];
		// 房屋登记表序号
		arrayss[0][13] = three.getString("xuhao");
		qianyi[9] = arrayss[0][13];

		// 身份证扫了没？ 0为没 1为扫了~~
		arrayss[0][14] = three.getString("0");
		// 房屋地址
		arrayss[0][15] = three.getString("dizhi");
		dizhi = arrayss[0][15];
		// 照片信息 默认为"" (先给默认值就行)
		arrayss[0][16] = "";
		Ryxx ryxx = new Ryxx();
		ryxx.setData(arrayss);
		ryxx.setZshs("0");
		sendData(ryxx);
	}

	private void sendData(final Ryxx ryxx) {
		dialog = StaticObject
				.showDialog(Rykc_renyuanheshi_act.this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				result = StaticObject.getMessage(Rykc_renyuanheshi_act.this,
						com.bksx.jzzslzd.common.RequestCode.RYKCHS, ryxxJson);
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
				handler.sendMessage(msg);

			}

		}.start();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 2) {
				StaticObject.showToast(Rykc_renyuanheshi_act.this, "没获取到数据");
			} else {
				Gson gson = new Gson();
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				if (dataCommon.getZtbs().equals("09")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this, "提交成功");
					finish();
				} else if (dataCommon.getZtbs().equals("6")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this, "需要迁移");
					Intent intent = new Intent(Rykc_renyuanheshi_act.this,
							Rykc_renyuanqianyi_act.class);

					// 迁移需要提交的数据，本界面已经处理了 // 姓名// 身份证// 性别// 出生日期// 住所类型//
					// 房屋编号// 房屋登记表序号
					intent.putExtra("qianyi", qianyi);
					intent.putExtra("dizhi", dizhi);
					intent.putExtra("data", dataCommon.getData()[0]);
					startActivity(intent);
					finish();
				} else if (dataCommon.getZtbs().equals("45")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this, "注销恢复");
				} else if (dataCommon.getZtbs().equals("06")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this, "新增失败");
				} else if (dataCommon.getZtbs().equals("13")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this,
							"身份证号码无效");
				} else if (dataCommon.getZtbs().equals("16")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this,
							dataCommon.getZtxx());
				} else if (dataCommon.getZtbs().equals("02")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this,
							"该人因死亡注销过");
				} else if (dataCommon.getZtbs().equals("0")) {
					StaticObject.showToast(Rykc_renyuanheshi_act.this,
							"该人已被快采过");

				}

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
