package com.bksx.jzzslzd.sjsb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.List;
import com.bksx.jzzslzd.bo.PhoneSjcxcy;
import com.bksx.jzzslzd.bo.Sjlb;
import com.bksx.jzzslzd.bo.Sjxxcy;
import com.bksx.jzzslzd.bo.SjxxcyBean;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Sjlb_Activity extends Activity implements OnScrollListener {
	private ProgressDialog dialog;
	private PhoneSjcxcy sjcxcy;
	private SharedPreferences preferences;
	private String result, result1, sjbh;
	private int lastItem, page, totalPage;
	private View footerView;
	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;
	private ArrayList<List> data;
	private ListView lv;
	boolean isok = false;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjlb_a);
		footerView = View
				.inflate(Sjlb_Activity.this, R.layout.footerview, null);
		lv = (ListView) findViewById(R.id.sjlb_listView);

		lv.addFooterView(footerView);

		lv.setOnScrollListener(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sjbh = list.get(position).get("bianhao");
				getData1(sjbh);

			}
		});
		initData();

	}

	private void initData() {
		page = 0;
		index = 0;
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(Sjlb_Activity.this, list,
				R.layout.sjlb_list_item, new String[] { "name", "categroy",
						"address", "xh" }, new int[] {
						R.id.sjlb_list_item_mingcheng,
						R.id.sjlb_list_item_leibie,
						R.id.sjlb_list_item_fashengdizhi, R.id.rylb_xh });
		lv.setAdapter(adapter);
		getData();
	}

	public void click(View view) {
		finish();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				String result = bundle.getString("result");
				Gson gson = new Gson();
				Sjlb json = gson.fromJson(result, Sjlb.class);
				data = json.getList();
				addList(data);
				adapter.notifyDataSetChanged();
				footerView.setVisibility(View.GONE);
				totalPage = Integer.valueOf(json.getTotalPage());
				Integer.valueOf(json.getCurrentPage());
				if (page == 1) {
					getData();
				}

				break;
			case 2:
				StaticObject.showToast(Sjlb_Activity.this, "市级接口连接失败");
				break;

			case 404:
				StaticObject.showToast(Sjlb_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
			isok = true;
		}

		private void addList(ArrayList<List> data) {
			for (int i = 0; i < data.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", data.get(i).getSjmc());
				map.put("categroy", getValue(data.get(i).getSjlb()));
				map.put("address", data.get(i).getFsdd());
				map.put("bianhao", data.get(i).getSjbh());
				map.put("xh", ++index + "");

				list.add(map);
			}
			page++;
		};

	};

	@SuppressLint("HandlerLeak")
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Intent intent = new Intent(Sjlb_Activity.this,
						Sjfk_Activity.class);
				Bundle bundle = msg.getData();
				result1 = bundle.getString("result1");
				intent.putExtra("result1", result1);
				intent.putExtra("sjbh", sjbh);
				startActivityForResult(intent, 600);
				break;
			case 2:
				StaticObject.showToast(Sjlb_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Sjlb_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Sjlb_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 600) {
			if (resultCode == 601) {
				initData();
			}
		}
	};

	/**
	 * 获取值value
	 * 
	 */
	public String getValue(String key) {
		LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
		;
		String sql = "select cd_id,cd_name from cy_d_sjlb where cd_id ='" + key
				+ "'";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(this);
		Cursor c = helper.Query(sql, null);
		map1.put("", "请选择");
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				map1.put(c.getString(0), c.getString(1));
			}
		}
		c.close();
		helper.close();
		if (key == null || "".equals(key)) {
			return "---";
		}
		return map1.get(key);

	}

	private void getData() {
		preferences = Sjlb_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		dialog = StaticObject.showDialog(this, "数据查询中...");
		isok = false;
		new Thread(new Runnable() {

			@Override
			public void run() {
				sjcxcy = new PhoneSjcxcy();
				sjcxcy.setSffk("1");
				sjcxcy.setGlybm(preferences.getString("login_number", ""));
				sjcxcy.setPageNum(page + 1 + "");
				Gson gson = new Gson();
				String data = gson.toJson(sjcxcy);
				result = StaticObject.getMessage(Sjlb_Activity.this,
						RequestCode.SJCX, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					Sjlb json = gson.fromJson(result, Sjlb.class);
					if ("09".equals(json.getZtbs())) {
						Message message1 = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("result", result);
						message1.setData(bundle);
						message1.what = 1;
						handler.sendMessage(message1);// 发送消息
					} else if ("18".equals(json.getZtbs())) {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						handler.sendMessage(message2);// 发送消息
					} else {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						message3.obj = json.getZtxx();
						handler.sendMessage(message3);// 发送消息
					}
				}

			}

		}).start();

	}

	private void getData1(final String sjbh) {
		dialog = StaticObject.showDialog(this, "数据查询中...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				Sjxxcy sjxxcy = new Sjxxcy();
				sjxxcy.setSjbh(sjbh);
				Gson gson = new Gson();
				String data = gson.toJson(sjxxcy);
				result1 = StaticObject.getMessage(Sjlb_Activity.this,
						RequestCode.SJXXCX, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result1)) {
					return;
				}
				if ("".equals(result1)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler1.sendMessage(message);// 发送消息
				} else {
					SjxxcyBean sjxxcyBean = gson.fromJson(result1,
							SjxxcyBean.class);
					if ("09".equals(sjxxcyBean.getZtbs())) {
						Message message1 = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("result1", result1);
						message1.setData(bundle);
						message1.what = 1;
						handler1.sendMessage(message1);// 发送消息
					} else if ("18".equals(sjxxcyBean.getZtbs())) {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						handler1.sendMessage(message2);// 发送消息
					} else {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						message3.obj = sjxxcyBean.getZtxx();
						handler1.sendMessage(message3);// 发送消息
					}
				}
			}
		}).start();

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (lastItem == list.size()
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& page < totalPage && isok) {
			footerView.setVisibility(View.VISIBLE);
			getData();
		} else if (view.getLastVisiblePosition() == view.getCount() - 1
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& page <= totalPage) {
			// 最后一页 下拉提示到底了
			StaticObject.showToast(Sjlb_Activity.this, "已显示全部数据");
			footerView.setVisibility(View.GONE);
		}
	}

}
