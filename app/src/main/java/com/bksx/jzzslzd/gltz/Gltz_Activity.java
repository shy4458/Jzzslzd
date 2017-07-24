package com.bksx.jzzslzd.gltz;

import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.ryhc.Ryhc_renyuanliebiao_act;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Gltz_Activity extends Activity implements View.OnClickListener {
	protected SharedPreferences preferences;
	private String[][] array = new String[1][4];
	protected Gson gson;
	protected String result;
	private TextView djkbls, wzkrs, djslrs, gltz_gly;
	private TextView gltz_et_qsrq, gltz_et_jzrq;
	private ImageButton back;
	private ProgressDialog dialog;
	private LinearLayout checkLayout;// 筛选框
	private SelectViewAndHandlerAndMsg select_gly;
	private String[][] glylb;
	private RelativeLayout djkbls_ll, wzkrs_ll, djslrs_ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gltz);
		findView();
		setClick();
		init();
		getData("", "", "");

	}
	/**
	 * 初始化数据
	 */
	private void init() {
		SharedPreferences preferences = this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		glylb = new Gson().fromJson(preferences.getString("glylb", null),
				String[][].class);
		LinkedHashMap<String, String> mzmap = new LinkedHashMap<String, String>();
		mzmap.put("", "请选择管理员");
		if (glylb != null && glylb.length > 0) {
			for (int i = 0; i < glylb.length; i++) {
				mzmap.put(glylb[i][2], glylb[i][1] + "(" + glylb[i][2] + ")");
			}
		}
		select_gly = new SelectViewAndHandlerAndMsg(this, "管理员", mzmap,
				gltz_gly, handler, 22, "");

	}

	/**
	 * 点击事件
	 */
	private void setClick() {
		djkbls_ll.setOnClickListener(this);
		wzkrs_ll.setOnClickListener(this);
		djslrs_ll.setOnClickListener(this);
		back.setOnClickListener(this);
		checkLayout.setOnClickListener(null);
		gltz_et_qsrq.setOnClickListener(this);
		gltz_et_jzrq.setOnClickListener(this);

	}

	/**
	 * 初始化对象
	 */
	private void findView() {
		djkbls = (TextView) findViewById(R.id.dengjikabanlishu);
		wzkrs = (TextView) findViewById(R.id.weizhikarenshu);
		djslrs = (TextView) findViewById(R.id.dengjishoulirenshu);
		djkbls_ll = (RelativeLayout) findViewById(R.id.gltz_ll_djkbls);
		wzkrs_ll = (RelativeLayout) findViewById(R.id.gltz_ll_wzkrs);
		djslrs_ll = (RelativeLayout) findViewById(R.id.gltz_ll_djslrs);
		gltz_gly = (TextView) findViewById(R.id.gltz_gly);
		gltz_et_qsrq = (TextView) findViewById(R.id.gltz_et_qsrq);
		gltz_et_jzrq = (TextView) findViewById(R.id.gltz_et_jzrq);
		checkLayout = (LinearLayout) findViewById(R.id.gltz_checkLayout);
		back = (ImageButton) findViewById(R.id.gltz_back);
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

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				String string1 = bundle.getString("rsl");
				String string2 = bundle.getString("czfsl");
				djkbls.setText(string1 + "人");
				wzkrs.setText(string2 + "人");
				//djslrs.setText(string2 + "人");
				break;
			case 2:
				StaticObject.showToast(Gltz_Activity.this, msg.obj.toString());
				break;
			case 18:
				StaticObject.showToast(Gltz_Activity.this, "市级接口连接失败");
				if (msg.arg1 == 1) {
					djkbls.setText("数据获取失败");
					wzkrs.setText("数据获取失败");
				}
				break;
			case 11:
				// 查询成功人
				String result1 = (String) msg.obj;
				Intent intent = new Intent(Gltz_Activity.this,
						Ryhc_renyuanliebiao_act.class);
				String[][] arrayr = new String[1][5];
				System.arraycopy(array[0], 0, arrayr[0], 0, 4);
				arrayr[0][4] = "1";

				intent.putExtra("data", result1);
				intent.putExtra("arrayss", arrayr[0]);
				intent.putExtra("fromA", "gltz");
				intent.setFlags(2);
				startActivityForResult(intent, 700);
				break;
			case 12:
				// 查询成功房
				String result2 = (String) msg.obj;
				Intent intent1 = new Intent(Gltz_Activity.this,
						Fwlb_Activity.class);
				intent1.putExtra("data", result2);
				String[][] arrayf = new String[1][5];
				System.arraycopy(array[0], 0, arrayf[0], 0, 4);
				arrayf[0][4] = "1";
				intent1.putExtra("arrayss", arrayf[0]);
				startActivityForResult(intent1, 700);
				break;
			case 404:
				StaticObject.showToast(Gltz_Activity.this, "网络连接失败");
				break;
			default:
				dialog.dismiss();
				break;
			}
		};
	};

	public void click(View view) {
		Gltz_Activity.this.finish();
	}

	public void tzcx(View view) {
		String glybm = select_gly.getCodeId();
		String qsrq = "";
		String jzrq = "";
		if (!"".equals(gltz_et_qsrq.getText().toString().trim())) {
			qsrq = FormCheck.getDate(gltz_et_qsrq.getText().toString().trim())
					+ "000000";
		}
		if (!"".equals(gltz_et_jzrq.getText().toString().trim())) {
			jzrq = FormCheck.getDate(gltz_et_jzrq.getText().toString().trim())
					+ "235959";
		}
		checkLayout.setVisibility(8);
		getData(glybm, qsrq, jzrq);
	}

	public void showCheck(View view) {
		int v = checkLayout.getVisibility();
		if (v == 0) {
			checkLayout.setVisibility(8);
		} else if (v == 8) {
			checkLayout.setVisibility(0);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 700) {
			if (resultCode == 701) {
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getData(final String glybm, final String qssj,
			final String jzsj) {
		dialog = StaticObject.showDialog(Gltz_Activity.this, "数据请求中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				preferences = Gltz_Activity.this.getSharedPreferences(
						StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
				// 个人编号
				array[0][0] = glybm;
				// 服务站编号
				array[0][1] = preferences.getString("login_service_id", "");
				// 服务站编号
				array[0][2] = qssj;
				// 服务站编号
				array[0][3] = jzsj;
				Ryxx ryxx = new Ryxx();
				ryxx.setData(array);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);

				result = StaticObject.getMessage(Gltz_Activity.this,
						RequestCode.SHHGLTZ, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				DataCommon dataCommon;
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					dataCommon = gson.fromJson(result, DataCommon.class);
					if ("09".equals(dataCommon.getZtbs())) {
						String[][] rfsl = dataCommon.getData();
						String s1 = rfsl[0][2];
						String s2 = rfsl[0][3];
						Message message1 = new Message();
						message1.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("rsl", s1);
						bundle.putString("czfsl", s2);
						message1.setData(bundle);
						handler.sendMessage(message1);

					} else if ("18".equals(dataCommon.getZtbs())) {
						Message message1 = new Message();
						message1.what = 18;
						message1.arg1 = 1;
						handler.sendMessage(message1);

					} else {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						message2.obj = dataCommon.getZtxx();
						handler.sendMessage(message2);// 发送消息
					}
				}

			}

		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 人
		case R.id.gltz_ll_djkbls:
			getRyData();
			break;
		// 房
		case R.id.gltz_ll_wzkrs:
			getFwData();
			break;
		// 房
		case R.id.gltz_et_qsrq:
			datePick(gltz_et_qsrq);
			break;
		// 房
		case R.id.gltz_et_jzrq:
			datePick(gltz_et_jzrq);
			break;
		case R.id.gltz_back:
			finish();
			break;

		default:
			break;
		}
	}

	private void getRyData() {
		dialog = StaticObject.showDialog(Gltz_Activity.this, "数据请求中....");
		new Thread(new Runnable() {

			private JSONObject jsonObject;

			@Override
			public void run() {
				// TODO 查人
				String[][] arrayr = new String[1][5];
				System.arraycopy(array[0], 0, arrayr[0], 0, 4);
				arrayr[0][4] = "1";
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayr);
				gson = new Gson();
				String data = gson.toJson(ryxx);
				String result = StaticObject.getMessage(Gltz_Activity.this,
						RequestCode.RYTZXX, data);
				dialog.dismiss();

				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					try {
						jsonObject = new JSONObject(result);
						if ("09".equals(jsonObject.get("ztbs"))) {
							Message message = Message.obtain();
							message.what = 11;
							message.obj = result;
							handler.sendMessage(message);

						} else if ("18".equals(jsonObject.get("ztbs"))) {
							handler.sendEmptyMessage(18);
						} else {
							Message message2 = new Message();// 创建消息
							message2.what = 2;// 设置消息的what值
							message2.obj = jsonObject.get("ztxx");
							handler.sendMessage(message2);// 发送消息
						}
					} catch (JSONException e) {
						e.printStackTrace();

					}
				}

			}

		}).start();
	}

	private void getFwData() {
		dialog = StaticObject.showDialog(Gltz_Activity.this, "数据请求中....");
		new Thread(new Runnable() {

			private JSONObject jsonObject;

			@Override
			public void run() {
				String[][] arrayf = new String[1][5];
				System.arraycopy(array[0], 0, arrayf[0], 0, 4);
				arrayf[0][4] = "1";
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayf);
				gson = new Gson();
				String data = gson.toJson(ryxx);
				String result = StaticObject.getMessage(Gltz_Activity.this,
						RequestCode.FWTZXX, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					try {
						jsonObject = new JSONObject(result);
						if ("09".equals(jsonObject.get("ztbs"))) {
							Message message = Message.obtain();
							message.what = 12;
							message.obj = result;
							handler.sendMessage(message);

						} else if ("18".equals(jsonObject.get("ztbs"))) {
							handler.sendEmptyMessage(18);
						} else {
							Message message2 = new Message();// 创建消息
							message2.what = 2;// 设置消息的what值
							message2.obj = jsonObject.get("ztxx");
							handler.sendMessage(message2);// 发送消息
						}
					} catch (JSONException e) {
						e.printStackTrace();

					}
				}

			}

		}).start();
	}

}
