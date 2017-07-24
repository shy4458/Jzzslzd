package com.bksx.jzzslzd.syfw;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.BzdzSelector;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * 房屋核查
 * 
 * @author Administrator
 * 
 */
public class Fwhc_Activity extends Activity {
	private Button fwhc_reset, fwhc_queding;
	private EditText fwhc_fzName, fwhc_djNumber, fwhc_dz_e;
	private AutoCompleteTextView fwhc_dz_auto;
	private ImageButton fwhc_back;

	private SharedPreferences preferences;
	private ProgressDialog dialog;
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_activity);
		initObject();

	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				result = bundle.getString("result");
				Intent intent = new Intent(Fwhc_Activity.this,
						Fwhc_fangwuliebiao_act.class);
				intent.putExtra("data", result);
				Fwhc_Activity.this.startActivity(intent);
				break;
			case 2:
				StaticObject.showToast(Fwhc_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Fwhc_Activity.this, "网络连接失败");
				break;

			default:
				dialog.dismiss();
				break;
			}
		};
	};

	/**
	 * 初始化对象
	 */
	private void initObject() {
		fwhc_reset = (Button) findViewById(R.id.fwhc_reset);
		fwhc_queding = (Button) findViewById(R.id.fwhc_queding);
		fwhc_fzName = (EditText) findViewById(R.id.editText_fwhc_fzName);
		fwhc_djNumber = (EditText) findViewById(R.id.editText_fwhc_djNumber);
		fwhc_dz_e = (EditText) findViewById(R.id.et_fwhc_dz);
		fwhc_dz_auto = (AutoCompleteTextView) findViewById(R.id.auto_fwhc_dz);
		new BzdzSelector(this, fwhc_dz_auto, fwhc_dz_e, true);
		fwhc_back = (ImageButton) findViewById(R.id.fwhc_back);

		// 重置
		fwhc_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fwhc_fzName.setText("");
				fwhc_djNumber.setText("");
				fwhc_dz_e.setText("");
				new BzdzSelector(Fwhc_Activity.this, fwhc_dz_auto, fwhc_dz_e,
						true);
			}
		});
		// 确定
		fwhc_queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getData();

			}
		});

		// 返回
		fwhc_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Fwhc_Activity.this.finish();
			}
		});
	}

	/**
	 * 请求数据
	 */
	private void getData() {
		dialog = StaticObject.showDialog(Fwhc_Activity.this, "数据请求中....");
		new Thread(new Runnable() {
			@Override
			public void run() {
				String[][] array = new String[1][4];
				preferences = Fwhc_Activity.this.getSharedPreferences(
						StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
				array[0][0] = fwhc_fzName.getText().toString().trim();
				array[0][1] = fwhc_djNumber.getText().toString().trim();
				array[0][2] = preferences.getString("login_service_id", "");
				array[0][3] = fwhc_dz_e.getText().toString().trim();
				Ryxx ryxx = new Ryxx();
				ryxx.setData(array);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Fwhc_Activity.this,
						RequestCode.FWXXCX, data);
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

}
