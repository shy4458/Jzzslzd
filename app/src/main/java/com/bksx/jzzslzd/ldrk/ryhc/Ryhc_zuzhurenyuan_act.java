package com.bksx.jzzslzd.ldrk.ryhc;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rycj.Rycj_dengji;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Ryhc_zuzhurenyuan_act extends Activity {
	Bundle bundle;// 前一个listview中点击的item 对应的人的数据
	String[] mapAll = { "xingming", "xingbie", "id", "grdjbh", "hjdz", "ljyy",
			"dh", "xzdz", "jydw", "idxx", "grbh", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21" };
	private SharedPreferences preference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_zuzhurenyuan);
		preference = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		bundle = getIntent().getExtras();

		Button btn_zhuxiao = (Button) findViewById(R.id.ryhc_zuzhurenyuan_btn_zhuxiao);
		Button btn_bucai = (Button) findViewById(R.id.ryhc_zuzhurenyuan_btn_bucai);
		TextView tv_xingming = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_xingming);
		TextView tv_xingbie = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_xingbie);
		TextView tv_id = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_id);
		TextView tv_huji = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_huji);
		TextView tv_juzhu = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_juzhu);
		TextView tv_tel = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_tel);
		TextView tv_yuanyin = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_yuanyin);
		TextView tv_dengjibiao = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_dengjibiao);
		TextView tv_jiuye = (TextView) findViewById(R.id.ryhc_zuzhuangrenyuan_tv_jiuye);
		btn_bucai.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				skipBc();
			}
		});
		findViewById(R.id.ryhc_zuzhurenyuan_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		// 填充数据
		tv_xingming.setText(FormCheck.subString(
				(String) bundle.get("xingming"), 8));

		tv_xingbie.setText((String) bundle.get("xingbie"));
		tv_id.setText((String) bundle.get("id"));
		tv_huji.setText((String) bundle.get("hjdz"));
		setStyle(tv_huji, (String) bundle.get("hjdz"));
		tv_juzhu.setText((String) bundle.get("xzdz"));
		tv_tel.setText("".equals((String) bundle.get("dh")) ? "未填写"
				: (String) bundle.get("dh"));
		tv_dengjibiao.setText((String) bundle.get("grdjbh"));
		String sql = "select CD_ID,CD_NAME from  SJCJ_D_LJYY where CD_AVAILABILITY='1' and cd_id='"
				+ (String) bundle.get("ljyy") + "' order by CD_INDEX";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			c.moveToFirst();
			tv_yuanyin.setText(c.getString(c.getColumnIndex("CD_NAME")));
		}
		c.close();
		helper.close();

		tv_jiuye.setText("".equals((String) bundle.get("jydw")) ? "未填写"
				: (String) bundle.get("jydw"));
		setStyle(tv_xingming, (String) bundle.get("xingming"));
		setStyle(tv_juzhu, (String) bundle.get("xzdz"));
		setStyle(tv_jiuye, "".equals((String) bundle.get("jydw")) ? "未填写"
				: (String) bundle.get("jydw"));

		btn_zhuxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Ryhc_zuzhurenyuan_act.this,
						Ryhc_zhuxiao_act.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 900);
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 900) {
			if (resultCode == 901) {
				setResult(801);
				finish();
			}
			if (resultCode == 902) {
				finish();
			}
		}
	};

	private void setStyle(TextView tv, final String text) {
		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StaticObject.showToast(Ryhc_zuzhurenyuan_act.this, text);
			}
		});
	}

	private Dialog dialog;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 404:
				StaticObject.showToast(Ryhc_zuzhurenyuan_act.this, "网络连接失败");
				break;
			case 18:
				StaticObject.showToast(Ryhc_zuzhurenyuan_act.this, "市级接口连接失败");
				break;
			case 0:
				DataCommon dataCommon = (DataCommon) msg.obj;
				Intent intent = new Intent(Ryhc_zuzhurenyuan_act.this,
						Rycj_dengji.class);
				RyzcVo vo = new RyzcVo(dataCommon.getData()[0]);
				if (dataCommon.getData().length > 1) {
					vo.setJz_qjzdz(dataCommon.getData()[1][0]);
				}

				if (vo.getJb_sfz().startsWith("X")) {
					StaticObject.showToast(Ryhc_zuzhurenyuan_act.this,
							"此人无身份证号码，请到流管平台进行修改或变更");
					return;
				}
				intent.setFlags(1);// 暂存0，补采1
				intent.putExtra("rybc", new Gson().toJson(vo));
				startActivityForResult(intent, 900);
				break;
			default:
				break;
			}
		};
	};

	private void skipBc() {
		dialog = StaticObject.showDialog(this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				String[][] arrayss = new String[1][2];
				// 个人编号
				arrayss[0][0] = (String) bundle.get("grbh");
				// 服务站编号
				arrayss[0][1] = preference.getString("login_service_id", "");
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayss);
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String result = StaticObject.getMessage(
						Ryhc_zuzhurenyuan_act.this, RequestCode.RYXXBCDJ,
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
