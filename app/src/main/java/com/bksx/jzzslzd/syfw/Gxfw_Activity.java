package com.bksx.jzzslzd.syfw;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.ryhc.Ryhc_renyuanliebiao_act;
import com.bksx.jzzslzd.tools.DBData;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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

public class Gxfw_Activity extends Activity implements OnClickListener {
	private Button gxfw_btn_zhuxiao, gxfw_btn_bucai, gxfw_btn_xiugai,
			gxfw_btn_zzPerson;
	private ImageButton gxfw_back;
	private TextView gxfw_fzName, gxfw_fwAddress, gxfw_czAction, gxfw_czCount,
			gxfw_czStartDate, gxfw_czEndDate;
	private String result, catagory, xingming;
	private String[][] data;
	private String[][] array1 = new String[1][1];
	private String[][] array2 = new String[1][3];
	private ProgressDialog dialog;
	private Intent intent;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_guanxiafangwu_a);
		// 获取从列表界面传来的数据
		intent = getIntent();
		catagory = intent.getStringExtra("catagory");
		result = intent.getStringExtra("result");
		data = new Gson().fromJson(result, DataCommon.class).getData();
		initObject();
		show();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		array1[0][0] = data[1][0];
		getData(array1, RequestCode.FWXXXX, handler);

	}

	private void show() {
		xingming = intent.getStringExtra("xingming");
		if (xingming == null || "".equals(xingming)) {
			gxfw_fzName.setText("无");
		} else {
			gxfw_fzName.setText(xingming);
		}
		if (data[1][1] == null || "".equals(data[1][1])) {
			gxfw_fwAddress.setText("无");
		} else {
			gxfw_fwAddress.setText(data[1][1]);
		}
		if (data[1][3] == null || "".equals(data[1][3])) {
			gxfw_czCount.setText("无");
		} else {
			gxfw_czCount.setText(data[1][3]);
		}

		if (data[1][5] == null || "".equals(data[1][5])) {
			gxfw_czAction.setText("无");
		} else {
			gxfw_czAction.setText(DBData.getCZCount(data[1][5]));
		}

		if (data[1][9] == null || "".equals(data[1][9])) {
			gxfw_czStartDate.setText("无起始时间");
		} else {
			String qsdate = data[1][9].substring(0, 4) + "-"
					+ data[1][9].substring(4, 6) + "-"
					+ data[1][9].substring(6, 8);
			gxfw_czStartDate.setText(qsdate);

		}
		if (data[1][10] == null || "".equals(data[1][10])
				|| data[1][10].length() < 14) {
			gxfw_czEndDate.setText("无截止日期");
		} else {
			String jzdate = data[1][10].substring(0, 4) + "-"
					+ data[1][10].substring(4, 6) + "-"
					+ data[1][10].substring(6, 8);
			gxfw_czEndDate.setText(jzdate);
		}

	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				result = bundle.getString("result");
				data = new Gson().fromJson(result, DataCommon.class).getData();

				show();
				break;

			case 3:
				StaticObject.showToast(Gxfw_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Gxfw_Activity.this, "网络连接失败");
				break;

			default:
				dialog.dismiss();
				break;
			}
		}

	};
	@SuppressLint("HandlerLeak")
	public Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				String result1 = bundle.getString("result");
				Intent intent4 = new Intent(Gxfw_Activity.this,
						Ryhc_renyuanliebiao_act.class);
				intent4.putExtra("data", result1);
				intent4.putExtra("arrayss", array2[0]);
				intent4.setFlags(1);
				startActivity(intent4);
				break;
			case 2:
				StaticObject.showToast(Gxfw_Activity.this, "该房屋里没人");
				break;
			case 3:
				StaticObject.showToast(Gxfw_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Gxfw_Activity.this, "网络连接失败");
				break;
			default:
				dialog.dismiss();
				break;
			}
		}

	};

	/**
	 * 获取数据
	 */
	private void getData(final String[][] arr, final String requestCode,
			final Handler handler) {
		dialog = StaticObject.showDialog(Gxfw_Activity.this, "数据请求中....");
		new Thread(new Runnable() {
			public void run() {
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arr);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				if (requestCode.equals(RequestCode.FWXXXX)) {

					result = StaticObject.getMessage(Gxfw_Activity.this,
							requestCode, data);
					dialog.dismiss();
					if (RequestCode.CSTR.equals(result)) {
						return;
					}
					if ("".equals(result)) {
						Message message = new Message();// 创建消息
						message.what = 404;// 设置消息的what值
						handler.sendMessage(message);// 发送消息
					} else {
						DataCommon dataCommon = gson.fromJson(result,
								DataCommon.class);
						if ("09".equals(dataCommon.getZtbs())) {
							Message message1 = new Message();
							message1.what = 1;
							Bundle bundle = new Bundle();
							bundle.putString("result", result);
							message1.setData(bundle);
							handler.sendMessage(message1);

						} else if ("37".equals(dataCommon.getZtbs())) {
							Message message2 = new Message();// 创建消息
							message2.what = 2;// 设置消息的what值
							message2.obj = dataCommon.getZtxx();
							handler.sendMessage(message2);// 发送消息
						} else {
							Message message3 = new Message();// 创建消息
							message3.what = 3;// 设置消息的what值
							message3.obj = dataCommon.getZtxx();
							handler.sendMessage(message3);// 发送消息
						}

					}

				} else {
					String result1 = StaticObject.getMessage(
							Gxfw_Activity.this, requestCode, data);
					dialog.dismiss();
					if (RequestCode.CSTR.equals(result1)) {
						return;
					}
					if ("".equals(result1)) {
						Message message = new Message();// 创建消息
						message.what = 404;// 设置消息的what值
						handler.sendMessage(message);// 发送消息
					} else {
						DataCommon dataCommon = gson.fromJson(result1,
								DataCommon.class);
						if ("09".equals(dataCommon.getZtbs())) {
							Message message1 = new Message();
							message1.what = 1;
							Bundle bundle = new Bundle();
							bundle.putString("result", result1);
							message1.setData(bundle);
							handler.sendMessage(message1);

						} else if ("37".equals(dataCommon.getZtbs())) {
							Message message2 = new Message();// 创建消息
							message2.what = 2;// 设置消息的what值
							message2.obj = dataCommon.getZtxx();
							handler.sendMessage(message2);// 发送消息
						} else {
							Message message3 = new Message();// 创建消息
							message3.what = 3;// 设置消息的what值
							message3.obj = dataCommon.getZtxx();
							handler.sendMessage(message3);// 发送消息
						}

					}

				}
			}

		}).start();
	}

	/**
	 * 初始化对象
	 */
	private void initObject() {
		gxfw_btn_zhuxiao = (Button) findViewById(R.id.gxfw_zhuxiao);
		gxfw_btn_bucai = (Button) findViewById(R.id.gxfw_bucai);
		gxfw_btn_xiugai = (Button) findViewById(R.id.gxfw_xiugai);
		gxfw_btn_zzPerson = (Button) findViewById(R.id.gxfw_zzPerson);
		gxfw_back = (ImageButton) findViewById(R.id.gxfw_back);
		gxfw_fzName = (TextView) findViewById(R.id.TextView_gxfw_fzName);
		gxfw_fwAddress = (TextView) findViewById(R.id.TextView_gxfw_fwAddress);
		gxfw_czAction = (TextView) findViewById(R.id.TextView_gxfw_czAction);
		gxfw_czCount = (TextView) findViewById(R.id.TextView_gxfw_czCount);
		gxfw_czStartDate = (TextView) findViewById(R.id.TextView_gxfw_czStartDate);
		gxfw_czEndDate = (TextView) findViewById(R.id.TextView_gxfw_czEndDate);
		gxfw_btn_zhuxiao.setOnClickListener(this);
		gxfw_btn_bucai.setOnClickListener(this);
		gxfw_btn_xiugai.setOnClickListener(this);
		gxfw_back.setOnClickListener(this);
		gxfw_btn_zzPerson.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.gxfw_zhuxiao:
			Intent intent1 = new Intent(Gxfw_Activity.this, Zx_Activity.class);
			intent1.putExtra("name", xingming);
			intent1.putExtra("info", data[1][15]);
			intent1.putExtra("bianhao", data[1][0]);
			startActivityForResult(intent1, 900);
			break;
		case R.id.gxfw_bucai:
			Intent intent2 = new Intent(Gxfw_Activity.this, Bc_Activity.class);
			intent2.putExtra("result", result);
			intent2.putExtra("name", xingming);
			intent2.putExtra("catagory", catagory);
			startActivity(intent2);
			break;
		case R.id.gxfw_xiugai:
			Intent intent3 = new Intent(Gxfw_Activity.this, Xg_Activity.class);
			intent3.putExtra("result", result);
			intent3.putExtra("name", xingming);
			startActivity(intent3);
			break;
		case R.id.gxfw_zzPerson:
			preferences = Gxfw_Activity.this.getSharedPreferences(
					StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
			array2[0][0] = data[1][0];
			array2[0][1] = preferences.getString("login_service_id", "");
			array2[0][2] = "1";
			getData(array2, RequestCode.FWXXTORYXX, handler1);
			break;
		case R.id.gxfw_back:
			Gxfw_Activity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 900) {
			if (resultCode == 901) {
				setResult(801);
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
