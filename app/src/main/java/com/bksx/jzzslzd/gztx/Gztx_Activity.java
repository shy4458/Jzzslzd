package com.bksx.jzzslzd.gztx;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rybc.Rybc_ListActivity;
import com.bksx.jzzslzd.tools.SqliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class Gztx_Activity extends Activity {
	protected SharedPreferences preferences;
	private TextView bcrs;// 补采人数
	private TextView ggsl;// 公告数量
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gztx_a);
		bcrs = (TextView) findViewById(R.id.renshu);

		ggsl = (TextView) findViewById(R.id.jianshu);

		findViewById(R.id.gltz_rsl).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Gztx_Activity.this,
								Rybc_ListActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.gltz_czfsl).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Gztx_Activity.this,
								com.bksx.jzzslzd.tzgg.Tzgg_Activity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.gltz_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
		preferences = Gztx_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		getData();
		super.onStart();
	}

	/**
	 * 
	 */
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 9:
				DataCommon rv = new Gson().fromJson((String) msg.obj,
						DataCommon.class);
				if ("37".equals(rv.getZtbs())) {
					bcrs.setText("0人");
					findViewById(R.id.gltz_rsl).setOnClickListener(null);
				} else if ("09".equals(rv.getZtbs())) {
					bcrs.setText(rv.getZtxx() + "人");
				}

				break;
			case 2:
				StaticObject.showToast(Gztx_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Gztx_Activity.this, "网络连接失败");
				break;
			case 8:
				ggsl.setText((String) msg.obj + "条");
				break;

			default:

				break;
			}
		};
	};

	/**
	 * 获取数据
	 */
	private void getData() {
		dialog = StaticObject.showDialog(this, "数据请求中....");
		new Thread(new Runnable() {
			@Override
			public void run() {

				String data = "{'data':[['','','','','','1','"
						+ preferences.getString("login_service_id", "")
						+ "','1']]}";
				String result = StaticObject.getMessage(Gztx_Activity.this,
						RequestCode.RYBCCX, data);

				String data2 = "{'IMSI':'" + preferences.getString("IMSI", "")
						+ "'}";
				String result2 = StaticObject.getMessage(Gztx_Activity.this,
						"000006", data2);

				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if (RequestCode.CSTR.equals(result2)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					Message msg = Message.obtain();
					msg.what = 9;
					msg.obj = result;
					handler.sendMessage(msg);
				}

				try {
					JSONObject jsonObject = new JSONObject(result2);
					if ("09".equals(jsonObject.getString("ztbs"))) {
						String string;

						string = jsonObject.getString("list");

						Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
						}.getType();
						ArrayList<HashMap<String, String>> list = new Gson()
								.fromJson(string, type);
						insertTzgg(list);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String c = getGgCount();
				Message msg = Message.obtain();
				msg.what = 8;
				msg.obj = c;
				handler.sendMessage(msg);
			}

		}).start();
	}

	private void insertTzgg(ArrayList<HashMap<String, String>> list) {
		for (int i = 0; i < list.size(); i++) {
			String sql = "insert into tzgg (xfbh,ggbh,ggmc,ggnr,ggrq,ggrqjs,zzzxmc,sfck) values (?,?,?,?,?,?,?,?)";
			SqliteHelper helper = SqliteHelper.getInstance(this);
			helper.execSQL(
					sql,
					new String[] { list.get(i).get("xfbh"),
							list.get(i).get("gzbh"), list.get(i).get("gzmc"),
							list.get(i).get("gznr"), list.get(i).get("djrq"),
							list.get(i).get("gzwcrq"),
							list.get(i).get("zzzxmc"), "0" });
		}
	}

	private String getGgCount() {
		String sql1 = "select xfbh,ggmc,ggnr,ggrq,sfck from tzgg where sfck='0' order by sfck ,ggrq desc";
		SqliteHelper helper = SqliteHelper.getInstance(this);
		Cursor cursor = helper.Query(sql1, null);
		String count = "0";
		if (cursor != null) {
			count = cursor.getCount() + "";
		}
		cursor.close();
		helper.close();
		return count;
	}
}
