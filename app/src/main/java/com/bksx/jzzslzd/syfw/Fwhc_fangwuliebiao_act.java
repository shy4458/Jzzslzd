package com.bksx.jzzslzd.syfw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fwhc_fangwuliebiao_act extends Activity {
	public static Fwhc_fangwuliebiao_act instance = null;
	// 界面数据，为list
	private List<Map<String, String>> data;
	private String[] dataFrom = new String[] { "xuhao", "xingming", "dizhi" };// map使用的key
	private String result1, result;
	private String[][] getData;// intent取出的数据
	private ImageButton Fwhc_fangwuliebiao_back;
	private ListView listview;
	private ProgressDialog dialog;
	private String[][] array = new String[1][1];
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				result = bundle.getString("result");
				int position = bundle.getInt("position");
				Intent intent = new Intent(Fwhc_fangwuliebiao_act.this,
						Gxfw_Activity.class);
				intent.putExtra("result", result);
				intent.putExtra("xingming", getData[position + 1][1]);
				intent.putExtra("catagory", getData[position + 1][4]);
				Fwhc_fangwuliebiao_act.this.startActivity(intent);
				break;
			case 2:
				StaticObject.showToast(Fwhc_fangwuliebiao_act.this,
						msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Fwhc_fangwuliebiao_act.this, "网络连接失败");
				break;

			default:
				dialog.dismiss();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rykc_fangwuliebiao);
		instance = this;
		// 获取房屋界面传过来的数据
		Intent intent = getIntent();
		result1 = intent.getStringExtra("data");
		getData = new Gson().fromJson(result1, DataCommon.class).getData();
		initObject();
		show();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				getData(getData[position + 1][0], position);

			}
		});
		Fwhc_fangwuliebiao_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Fwhc_fangwuliebiao_act.this.finish();
			}
		});
	}

	/**
	 * 获取数据
	 */
	private void getData(final String bianhao, final int position) {
		dialog = StaticObject.showDialog(Fwhc_fangwuliebiao_act.this,
				"数据请求中....");
		new Thread(new Runnable() {
			public void run() {
				array[0][0] = bianhao;
				Ryxx ryxx = new Ryxx();
				ryxx.setData(array);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Fwhc_fangwuliebiao_act.this,
						RequestCode.FWXXXX, data);
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
						bundle.putInt("position", position);
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

	/**
	 * 将数据填充到list中
	 */
	private void show() {
		data = forData();
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				data,
				R.layout.rykc_fangwuliebiao_list_item,
				dataFrom,
				new int[] { R.id.rykc_list_item_xuhao,
						R.id.rykc_list_item_xingming, R.id.rykc_list_item_dizhi });
		listview.setAdapter(adapter);

	}

	/**
	 * 初始化对象
	 */
	private void initObject() {
		Fwhc_fangwuliebiao_back = (ImageButton) findViewById(R.id.rykc_fangwuliebiao_back);
		listview = (ListView) findViewById(R.id.rykc_fangwuliebiao_list);

	}

	private List<Map<String, String>> forData() {
		data = new ArrayList<Map<String, String>>();

		for (int i = 0; i < getData.length - 1; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(dataFrom[0], getData[i + 1][2]);
			map.put(dataFrom[1], getData[i + 1][1]);
			map.put(dataFrom[2], getData[i + 1][3]);
			data.add(map);
		}
		return data;
	}

}
