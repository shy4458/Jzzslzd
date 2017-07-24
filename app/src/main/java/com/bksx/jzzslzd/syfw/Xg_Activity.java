package com.bksx.jzzslzd.syfw;

import java.util.Calendar;
import java.util.LinkedHashMap;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.DBData;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SelectView;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 修改房屋信息
 * 
 * @author Administrator
 * 
 */
public class Xg_Activity extends Activity {
	private ImageButton fwxg_back;
	private Button fwxg_reset, fwxg_queding;
	private EditText fwxg_fwAddress, fwxg_czCount, fwxg_pingmi, fwxg_rent;
	private TextView fwxg_fzName, fwxg_info, fwxg_czAction, fwxg_czCatagory,
			fwxg_zujin, fwxg_czStartDate, fwxg_czEndDate;
	private String fwAddress, czCount, pingmi, rent, czStartDate, czEndDate;
	private String result, xingming, result1;
	private String qsdate, jzdate;
	private String[][] data, array;
	private SelectView selectView_czAction, selectView_czCatagory,
			selectView_zujin;
	private ProgressDialog dialog;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_fangwuxiugai_a);
		initObject();
		show();
		dataPicker(fwxg_czStartDate);
		dataPicker(fwxg_czEndDate);
		fwxg_queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getData();

			}
		});

		// 重置
		fwxg_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fwxg_fwAddress.setText("");
				fwxg_czCount.setText("");
				fwxg_pingmi.setText("");
				selectView_czAction.setCodeId("");
				selectView_czCatagory.setCodeId("");
				selectView_zujin.setCodeId("");
				fwxg_rent.setText("");
				fwxg_czStartDate.setText("");
				fwxg_czEndDate.setText("");
				show();
			}
		});

		// 返回
		fwxg_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Xg_Activity.this.finish();
			}
		});
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				StaticObject.showToast(Xg_Activity.this, "修改成功");
				Xg_Activity.this.finish();
				break;
			case 2:
				StaticObject.showToast(Xg_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Xg_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Xg_Activity.this, "网络连接失败");
				break;

			default:
				dialog.dismiss();
				break;
			}
		};
	};

	/**
	 * 校验
	 */
	private boolean checkOut() {
		fwAddress = fwxg_fwAddress.getText().toString().trim();
		czCount = fwxg_czCount.getText().toString().trim();
		pingmi = fwxg_pingmi.getText().toString().trim();
		rent = fwxg_rent.getText().toString().trim();
		czStartDate = fwxg_czStartDate.getText().toString().trim();
		czEndDate = fwxg_czEndDate.getText().toString().trim();
		if (fwAddress == null || "".equals(fwAddress)) {
			StaticObject.showToast(Xg_Activity.this, "请填写房屋所在地详细地址!");
			return false;
		}
		if (czCount == null || "".equals(czCount)) {
			StaticObject.showToast(Xg_Activity.this, "请填写出租间数!");
			return false;
		}
		if (pingmi == null || "".equals(pingmi)) {
			StaticObject.showToast(Xg_Activity.this, "请填写出租平米数!");
			return false;
		}

		if (rent == null || "".equals(rent)) {
			StaticObject.showToast(Xg_Activity.this, "请填写租金!");
			return false;
		}
		if (czStartDate == null || "".equals(czStartDate)) {
			StaticObject.showToast(Xg_Activity.this, "请填写出租起始日期!");
			return false;
		}
		if (czEndDate == null || "".equals(czEndDate)) {
			StaticObject.showToast(Xg_Activity.this, "请填写出租截止日期!");
			return false;
		}
		if (FormCheck.check_Date("yyyy-MM-dd", czStartDate, czEndDate) == false) {
			StaticObject.showToast(Xg_Activity.this, "出租截止日期应大于出租开始日期!");
			return false;
		}
		return true;

	}

	/**
	 * 显示
	 */
	private void show() {
		Intent intent = getIntent();
		result = intent.getStringExtra("result");
		DataCommon dataCommon = new Gson().fromJson(result, DataCommon.class);
		data = dataCommon.getData();
		xingming = intent.getStringExtra("name");
		if (xingming == null || "".equals(xingming)) {
			fwxg_fzName.setText("无");
		} else {
			fwxg_fzName.setText(xingming);
		}
		if (data[1][1] == null || "".equals(data[1][1])) {
			fwxg_info.setText("无");
		} else {
			fwxg_info.setText(data[1][1]);
			fwxg_fwAddress.setText(data[1][1]);
		}

		fwxg_czCount.setText(data[1][3]);

		fwxg_pingmi.setText(data[1][4]);

		LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
		String sql1 = "select CD_ID,CD_NAME from SJCJ_D_CZYT order by CD_INDEX";
		DBData.getData(sql1, map1);
		selectView_czAction = new SelectView(Xg_Activity.this, map1,
				fwxg_czAction);
		if (data[1][5] == null || "".equals(data[1][5])) {
			fwxg_czAction.setText("生活居住");
		} else {
			selectView_czAction.setCodeId(data[1][5]);
		}
		LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
		String sql2 = "select CD_ID,CD_NAME from SJCJ_D_CHZLX order by CD_INDEX";
		DBData.getData(sql2, map2);
		selectView_czCatagory = new SelectView(Xg_Activity.this, map2,
				fwxg_czCatagory);
		if (data[1][6] == null || "".equals(data[1][6])) {
			fwxg_czCatagory.setText("普通出租房屋类");
		} else {
			selectView_czCatagory.setCodeId(data[1][6]);
		}
		LinkedHashMap<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("0", "月");
		map3.put("1", "年");
		selectView_zujin = new SelectView(Xg_Activity.this, map3, fwxg_zujin);
		if (data[1][7] == null || "".equals(data[1][7])) {
			fwxg_zujin.setText("月");
		} else {
			selectView_zujin.setCodeId(data[1][7].trim());
		}

		fwxg_rent.setText(data[1][8]);

		if (data[1][9] != null && !"".equals(data[1][9])) {
			qsdate = data[1][9].substring(0, 4) + "-"
					+ data[1][9].substring(4, 6) + "-"
					+ data[1][9].substring(6, 8);
			fwxg_czStartDate.setText(qsdate);
		}

		if (data[1][10] != null && !"".equals(data[1][10])
				&& data[1][10].length() == 14) {
			jzdate = data[1][10].substring(0, 4) + "-"
					+ data[1][10].substring(4, 6) + "-"
					+ data[1][10].substring(6, 8);
			fwxg_czEndDate.setText(jzdate);
		}

	}

	/**
	 * 日期选择框
	 * 
	 * @param textView
	 */
	private void dataPicker(final TextView textView) {
		textView.setOnClickListener(new OnClickListener() {
			private DatePickerDialog timePicker;
			private Calendar calendar = Calendar.getInstance();

			@Override
			public void onClick(View arg0) {
				DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year,
							int month, int dayOfMonth) {
						// Calendar月份是从0开始,所以month要加1

						textView.setText(year + "-" + (month + 1) + "-"
								+ dayOfMonth);

					}
				};

				timePicker = new DatePickerDialog(Xg_Activity.this,
						dateListener, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				timePicker.show();

			}
		});
	}

	/**
	 * 初始化对象
	 */
	private void initObject() {
		fwxg_back = (ImageButton) findViewById(R.id.fwxg_back);
		fwxg_reset = (Button) findViewById(R.id.fwxg_reset);
		fwxg_queding = (Button) findViewById(R.id.fwxg_queding);
		fwxg_fwAddress = (EditText) findViewById(R.id.editText_fwxg_fwAddress);
		fwxg_czCount = (EditText) findViewById(R.id.editText_fwxg_czCount);
		fwxg_pingmi = (EditText) findViewById(R.id.editText_fwxg_pingmi);
		fwxg_rent = (EditText) findViewById(R.id.editText_fwxg_yuan);
		fwxg_czStartDate = (TextView) findViewById(R.id.textView_fwxg_czStartDate);
		fwxg_czEndDate = (TextView) findViewById(R.id.textView_fwxg_czEndDate);
		fwxg_fzName = (TextView) findViewById(R.id.fwxg_name);
		fwxg_info = (TextView) findViewById(R.id.fwxg_info);
		fwxg_czAction = (TextView) findViewById(R.id.textView_fwxg_czAction);
		fwxg_czCatagory = (TextView) findViewById(R.id.textView_fwxg_czCatagory);
		fwxg_zujin = (TextView) findViewById(R.id.textView_fwxg_rent);

	}

	/**
	 * 获取数据
	 */
	private void getData() {
		if (checkOut()) {
			dialog = StaticObject.showDialog(Xg_Activity.this, "数据修改中....");
			new Thread(new Runnable() {

				@Override
				public void run() {
					preferences = Xg_Activity.this.getSharedPreferences(
							StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
					array = new String[1][15];
					array[0][0] = czCount;
					array[0][1] = pingmi;
					array[0][2] = selectView_czAction.getCodeId();
					array[0][3] = selectView_czCatagory.getCodeId();
					array[0][4] = selectView_zujin.getCodeId();
					array[0][5] = rent;
					array[0][6] = (FormCheck.getDate(czStartDate) + "000000")
							.trim();
					array[0][7] = (FormCheck.getDate(czEndDate) + "000000")
							.trim();
					array[0][8] = data[1][0].trim();
					array[0][9] = preferences.getString("login_service_id", "");
					array[0][10] = preferences.getString("login_govern_id", "")
							.split(",")[0];
					array[0][11] = preferences.getString("login_admin_id", "");
					array[0][12] = preferences.getString("login_number", "");
					array[0][13] = preferences.getString("login_rbac", "");
					array[0][14] = fwAddress;
					Ryxx ryxx = new Ryxx();
					ryxx.setData(array);
					Gson gson = new Gson();
					String data1 = gson.toJson(ryxx);
					result1 = StaticObject.getMessage(Xg_Activity.this,
							RequestCode.FWXXXG, data1);

					dialog.dismiss();
					if (RequestCode.CSTR.equals(result1)) {
						return;
					}
					if ("".equals(result1)) {
						Message message = new Message();// 创建消息
						message.what = 404;// 设置消息的what值
						handler.sendMessage(message);// 发送消息
					} else {
						DataCommon dataCommon1 = gson.fromJson(result1,
								DataCommon.class);

						if ("09".equals(dataCommon1.getZtbs())) {
							Message message1 = new Message();
							message1.what = 1;
							handler.sendMessage(message1);// 发送消息

						} else if ("18".equals(dataCommon1.getZtbs())) {
							Message message2 = new Message();// 创建消息
							message2.what = 2;// 设置消息的what值
							handler.sendMessage(message2);// 发送消息
						} else {
							Message message3 = new Message();// 创建消息
							message3.what = 3;// 设置消息的what值
							message3.obj = dataCommon1.getZtxx();
							handler.sendMessage(message3);// 发送消息
						}
					}
				}
			}).start();
		}
	}
}
