package com.bksx.jzzslzd.gltz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.syfw.Gxfw_Activity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fwlb_Activity extends Activity implements OnScrollListener {
	private String[][] arrayss = new String[1][3];// 请求数据使用
	private int lastItem, page = 0, pageCount;
	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;
	private View footerView;
	private String result;
	private Dialog dialog;
	boolean isok = false;
	// intent取出的数据
	private String[] from = { "xuhao", "xingming", "dizhi", "bh", "catagory" };

	private int[] to = { R.id.rykc_list_item_xuhao,
			R.id.rykc_list_item_xingming, R.id.rykc_list_item_dizhi };
	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rykc_fangwuliebiao);

		findViewById(R.id.rykc_fangwuliebiao_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		Intent intent = getIntent();
		result = intent.getStringExtra("data");
		arrayss[0] = intent.getStringArrayExtra("arrayss");
		// 获取总页数
		DataCommon common = new Gson().fromJson(result, DataCommon.class);
		pageCount = Integer.valueOf(common.getZtxx());

		footerView = View.inflate(this, R.layout.footerview, null);
		ListView lv = (ListView) findViewById(R.id.rykc_fangwuliebiao_list);
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(this, list,
				R.layout.rykc_fangwuliebiao_list_item, from, to);
		lv.addFooterView(footerView);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				getData(position);
			}
		});
		// 上一个界面获取到的数据，作为第一次的显示
		// fwhc界面 跳过来 数据处理方法不一样 flags==1时，代表fwhc
		addList(common.getData());
		adapter.notifyDataSetChanged();
		// 第一次如果总页数》1，那么再把第二页的数据 自动请求
		if (pageCount > 1) {
			loadMoreData();
		}
	}

	/**
	 * 获取数据
	 */
	private void getData(final int position) {
		dialog = StaticObject.showDialog(Fwlb_Activity.this, "数据请求中....");
		new Thread(new Runnable() {
			public void run() {
				String[][] array = new String[1][1];
				array[0][0] = list.get(position).get("bh");
				Ryxx ryxx = new Ryxx();
				ryxx.setData(array);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Fwlb_Activity.this,
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 800) {
			if (resultCode == 801) {
				setResult(701);
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// 可见最后位置为listview最后一位（底部），同时当前页数《总页数 才有效
		if (lastItem == list.size()
				&& arg1 == OnScrollListener.SCROLL_STATE_IDLE
				&& page < pageCount && isok) {
			footerView.setVisibility(View.VISIBLE);
			loadMoreData();

		} else if (arg0.getLastVisiblePosition() == arg0.getCount() - 1
				&& arg1 == OnScrollListener.SCROLL_STATE_IDLE
				&& page == pageCount) {
			// 最后一页 下拉提示到底了
			StaticObject.showToast(Fwlb_Activity.this, "已显示全部数据");
		}

	}

	private synchronized void loadMoreData() {
		dialog = StaticObject.showDialog(this, "数据查询中...");
		isok = false;
		new Thread() {
			@Override
			public void run() {
				arrayss[0][4] = page + 1 + "";

				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayss);
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String re = new String();
				re = StaticObject.getMessage(Fwlb_Activity.this,
						RequestCode.FWTZXX, ryxxJson);
				dialog.dismiss();
				if (re.equals("")) {
					handler.sendEmptyMessage(2);
					return;
				}
				if (RequestCode.CSTR.equals(re)) {
					return;
				}
				DataCommon common = new Gson().fromJson(re, DataCommon.class);
				if ("09".equals(common.getZtbs())) {
					// fwhc界面 跳过来 数据处理方法不一样 flags==1时，代表fwhc
					Message message = Message.obtain();
					message.what = 9;
					message.obj = common.getData();
					handler.sendMessage(message);
				} else if ("18".equals(common.getZtbs())) {
					handler.sendEmptyMessage(18);
				}

			}
		}.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				result = bundle.getString("result");
				int position = bundle.getInt("position");
				Intent intent = new Intent(Fwlb_Activity.this,
						Gxfw_Activity.class);
				intent.putExtra("result", result);
				intent.putExtra("xingming", list.get(position).get("xingming"));
				intent.putExtra("catagory", list.get(position).get("catagory"));
				startActivityForResult(intent, 800);
				break;
			case 2:
				StaticObject.showToast(Fwlb_Activity.this, "网络连接失败");
				break;
			case 9:
				addList((String[][]) msg.obj);
				adapter.notifyDataSetChanged();
				footerView.setVisibility(View.GONE);
				break;
			case 18:
				StaticObject.showToast(Fwlb_Activity.this, "市级接口连接失败");
				break;

			default:
				break;
			}
			isok = true;
		}

	};

	/**
	 * 数据添加到List中
	 * 
	 * @param data
	 */
	private void addList(String data[][]) {
		forList(data);
		page++;
	}

	/**
	 * 将字符串数据，json解析之后，放入listview数据源 返回字符串中的ztbs
	 * 
	 * @param
	 */
	private void forList(String[][] getData) {

		// 解析二维数组，放入list
		// 判断有没有数据
		if (getData.length == 1) {
			return;
		}
		for (int i = 1; i <= getData.length - 1; i++) {
			Map<String, String> map = new HashMap<String, String>();
			// 放入11种数据,但只显示3种----改！！放入所有的数据
			map.put(from[0], getData[i][2]);
			map.put(from[1], getData[i][1]);
			map.put(from[2], getData[i][3]);
			map.put(from[3], getData[i][0]);
			map.put(from[4], getData[i][4]);
			map.put("xh", ++index + "");
			list.add(map);
		}
	}
}
