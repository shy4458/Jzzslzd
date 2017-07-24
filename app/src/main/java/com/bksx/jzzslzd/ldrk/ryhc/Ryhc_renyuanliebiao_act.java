package com.bksx.jzzslzd.ldrk.ryhc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.google.gson.Gson;

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
import android.widget.TextView;

public class Ryhc_renyuanliebiao_act extends Activity implements
		OnScrollListener {
	private String[][] arrayss = new String[1][6];// 请求数据使用
	private int lastItem, flag, page = 0, pageCount;
	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;
	private View footerView;
	private String result;
	private Dialog dialog;
	boolean isok = false;
	// intent取出的数据
	private String[] from = { "xingming", "xingbie", "id", "xh", "glybm" };
	private String[] mapAll = { "xingming", "xingbie", "id", "grdjbh", "hjdz",
			"ljyy", "dh", "xzdz", "jydw", "idxx", "grbh", "11", "12", "13",
			"14", "15", "16", "glybm", "18", "19", "20", "21" };

	private int[] to = { R.id.ryhc_renyuanliebiao_item_xingming,
			R.id.ryhc_renyuanliebiao_item_xingbie,
			R.id.ryhc_renyuanliebiao_item_id, R.id.rylb_xh,
			R.id.ryhc_renyuanliebiao_item_gly };
	private int index = 0;
	Map<String, String> glymap = new LinkedHashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_renyuanliebiao);

		findViewById(R.id.ryhc_renyuanliebiao_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		Intent intent = getIntent();
		result = intent.getStringExtra("data");
		arrayss[0] = intent.getStringArrayExtra("arrayss");
		String fromA = intent.getStringExtra("fromA");
		if ("ryhc".equals(fromA)) {
			((TextView) findViewById(R.id.ryhc_renyuanliebiao_title))
					.setText("人员列表（注：所属本服务站的人员）");
		} else if ("gltz".equals(fromA)) {
			((TextView) findViewById(R.id.ryhc_renyuanliebiao_title))
					.setText("人员列表");
		}

		String[][] glylb = new Gson().fromJson(
				this.getSharedPreferences(StaticObject.SHAREPREFERENC,
						Activity.MODE_PRIVATE).getString("glylb", null),
				String[][].class);

		for (int i = 0; i < glylb.length; i++) {
			glymap.put(glylb[i][2], glylb[i][1]);
		}

		// 获取总页数
		DataCommon common = new Gson().fromJson(result, DataCommon.class);
		pageCount = Integer.valueOf(common.getZtxx());

		footerView = View.inflate(this, R.layout.footerview, null);
		ListView lv = (ListView) findViewById(R.id.ryhc_renyuanliebiao_list);
		list = new ArrayList<Map<String, String>>();

		adapter = new SimpleAdapter(this, list,
				R.layout.ryhc_renyuanliebiao_item, from, to);
		lv.addFooterView(footerView);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Ryhc_renyuanliebiao_act.this,
						Ryhc_zuzhurenyuan_act.class);
				Map<String, String> map = list.get(arg2);
				if (map.get("id").startsWith("X")) {
					StaticObject.showToast(Ryhc_renyuanliebiao_act.this,
							"此人无身份证号码，请到流管平台进行修改或变更");
					return;
				}
				for (int j = 0; j <= 21; j++) {
					intent.putExtra(mapAll[j], map.get(mapAll[j]));
				}
				startActivityForResult(intent, 800);
			}
		});
		// 上一个界面获取到的数据，作为第一次的显示
		// fwhc界面 跳过来 数据处理方法不一样 flags==1时，代表fwhc2代表管理台账
		flag = intent.getFlags();
		addList(common.getData());
		adapter.notifyDataSetChanged();
		// 第一次如果总页数》1，那么再把第二页的数据 自动请求
		if (pageCount > 1) {
			loadMoreData();
		}
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
			StaticObject.showToast(Ryhc_renyuanliebiao_act.this, "已显示全部数据");
		}

	}

	private synchronized void loadMoreData() {
		dialog = StaticObject.showDialog(this, "数据查询中...");
		isok = false;
		new Thread() {
			@Override
			public void run() {
				// ======================== 数据传输 开始
				if (flag == 1) {
					arrayss[0][2] = page + 1 + "";
				} else if (flag == 2) {
					arrayss[0][4] = page + 1 + "";
				} else {
					arrayss[0][5] = page + 1 + "";
				}

				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrayss);
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String re = new String();
				if (flag == 1) {
					re = StaticObject.getMessage(Ryhc_renyuanliebiao_act.this,
							RequestCode.FWXXTORYXX, ryxxJson);
				} else if (flag == 2) {
					re = StaticObject.getMessage(Ryhc_renyuanliebiao_act.this,
							RequestCode.RYTZXX, ryxxJson);
				} else {
					re = StaticObject.getMessage(Ryhc_renyuanliebiao_act.this,
							RequestCode.RYXXHC, ryxxJson);
				}
				if (RequestCode.CSTR.equals(re)) {
					return;
				}
				dialog.dismiss();
				if (re.equals("")) {
					handler.sendEmptyMessage(2);
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
			case 2:
				StaticObject.showToast(Ryhc_renyuanliebiao_act.this, "网络连接失败");
				break;
			case 9:
				addList((String[][]) msg.obj);
				adapter.notifyDataSetChanged();
				footerView.setVisibility(View.GONE);
				break;
			case 18:
				StaticObject
						.showToast(Ryhc_renyuanliebiao_act.this, "市级接口连接失败");
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
		if (flag == 1) {
			forListFwhc(data);
		} else if (flag == 2) {
			forListFwhc(data);
		} else {
			forList(data);
		}
		page++;
	}

	/**
	 * 将字符串数据，json解析之后，放入listview数据源 返回字符串中的ztbs
	 * 
	 * @param result
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
			for (int j = 0; j <= 21; j++) {
				if (j == 1) {
					// 性别数据为1时 记录男性 否则记录女性
					getData[i][1] = getData[i][j].equals("1") ? "男" : "女";
				}
				map.put(mapAll[j], getData[i][j] == null ? "" : getData[i][j]);

			}
			map.put("xh", ++index + "");
			map.put(mapAll[17], glymap.get(map.get(mapAll[17])));// 管理员编号变姓名
			list.add(map);
		}
	}

	private void forListFwhc(String[][] getData) {

		// 解析二维数组，放入list
		// 判断有没有数据
		if (getData.length == 1) {
			return;
		}
		for (int i = 1; i <= getData.length - 1; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(mapAll[0], getData[i][2] == null ? "" : getData[i][2]);
			getData[i][3] = getData[i][3].equals("1") ? "男" : "女";
			map.put(mapAll[1], getData[i][3] == null ? "" : getData[i][3]);
			map.put(mapAll[2], getData[i][5] == null ? "" : getData[i][5]);
			map.put(mapAll[3], getData[i][0] == null ? "" : getData[i][0]);
			map.put(mapAll[4], getData[i][11] == null ? "" : getData[i][11]);
			map.put(mapAll[5], getData[i][12] == null ? "" : getData[i][12]);
			map.put(mapAll[6], getData[i][13] == null ? "" : getData[i][13]);
			map.put(mapAll[7], getData[i][14] == null ? "" : getData[i][14]);
			map.put(mapAll[8], getData[i][15] == null ? "" : getData[i][15]);
			map.put(mapAll[9], getData[i][16] == null ? "" : getData[i][16]);

			map.put(mapAll[10], getData[i][1] == null ? "" : getData[i][1]);
			map.put(mapAll[11], getData[i][4] == null ? "" : getData[i][4]);
			map.put(mapAll[12], getData[i][6] == null ? "" : getData[i][6]);
			map.put(mapAll[13], getData[i][7] == null ? "" : getData[i][7]);

			map.put(mapAll[14], getData[i][8] == null ? "" : getData[i][8]);
			map.put(mapAll[15], getData[i][9] == null ? "" : getData[i][9]);
			map.put(mapAll[16], getData[i][10] == null ? "" : getData[i][10]);
			for (int j = 17; j <= 21; j++) {
				map.put(mapAll[j], getData[i][j] == null ? "" : getData[i][j]);
			}
			map.put("xh", ++index + "");
			map.put(mapAll[17], glymap.get(map.get(mapAll[17])));// 管理员编号变姓名
			list.add(map);
		}
	}
}
