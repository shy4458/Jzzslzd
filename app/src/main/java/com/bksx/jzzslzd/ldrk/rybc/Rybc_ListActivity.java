package com.bksx.jzzslzd.ldrk.rybc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rycj.Rycj_dengji;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Rybc_ListActivity extends Activity implements OnScrollListener {
	// cpage当前页,tpage总条数
	private int cpage = 0, tpage = 0, lastItem;
	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;
	private View footerView;
	private String count;
	private SharedPreferences preferences;
	// intent取出的数据
	private String[] from = { "xingming", "xingbie", "id", "xh" };

	private int[] to = { R.id.ryhc_renyuanliebiao_item_xingming,
			R.id.ryhc_renyuanliebiao_item_xingbie,
			R.id.ryhc_renyuanliebiao_item_id, R.id.rylb_xh };
	private Dialog dialog;
	boolean isok = false;
	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_renyuanliebiao);
		preferences = this.getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		findViewById(R.id.ryhc_renyuanliebiao_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Rybc_ListActivity.this.finish();
					}
				});

		footerView = View.inflate(this, R.layout.footerview, null);
		ListView lv = (ListView) findViewById(R.id.ryhc_renyuanliebiao_list);
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(this, list,
				R.layout.ryhc_renyuanliebiao_item, from, to);
		lv.addFooterView(footerView);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterview,
					View view, int i, long l) {

				final HashMap<String, String> map = (HashMap<String, String>) list
						.get(i);
				new AlertDialog.Builder(Rybc_ListActivity.this)
						.setTitle("提示")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("删除该条人员信息？")
						.setNegativeButton("否", null)
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										String xh = map.get("grbh");
										makeData(xh);
									}
								}).show();
				return true;
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				if (list.size() <= arg2) {
					StaticObject.print("数组越界" + arg2 + "-->" + list.size());
					return;
				}
				if (list.get(arg2).get("id").startsWith("X")) {
					StaticObject.showToast(Rybc_ListActivity.this,
							"此人无身份证号码，请到流管平台进行修改或变更！");
					return;
				}
				dialog = StaticObject.showDialog(Rybc_ListActivity.this,
						"数据查询中...");
				new Thread() {
					public void run() {
						Map<String, String> map = list.get(arg2);
						String ryxxJson = "{'data':[['" + map.get("grbh")
								+ "','"
								+ preferences.getString("login_service_id", "")
								+ "']]}";
						String result = StaticObject.getMessage(
								Rybc_ListActivity.this, RequestCode.RYXXBCDJ,
								ryxxJson);
						dialog.dismiss();
						if (RequestCode.CSTR.equals(result)) {
							return;
						}
						if ("".equals(result)) {
							mHandler.sendEmptyMessage(999);
						} else {
							DataCommon dataCommon = new Gson().fromJson(result,
									DataCommon.class);
							Message msg = Message.obtain();
							msg.what = 2;
							msg.obj = dataCommon;
							mHandler.sendMessage(msg);
						}
					};
				}.start();
			}
		});

	}

	@Override
	protected void onStart() {
		list.clear();
		cpage = 1;
		index = 0;
		loadMoreData();
		super.onStart();
	}

	private void addDateToList(String[][] data) { // 准备数据
		for (int i = 1; i < data.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			String xb = data[i][1].equals("1") ? "男" : "女";
			map.put("xingming", data[i][0]);
			map.put("xingbie", xb);
			map.put("id", data[i][2]);
			map.put("grbh", data[i][10]);
			map.put("xh", ++index + "");
			list.add(map);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if (lastItem == list.size()
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& cpage <= tpage && isok) {
			footerView.setVisibility(View.VISIBLE);
			loadMoreData();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			footerView.setVisibility(View.GONE);
			switch (msg.what) {
			case 0:
				DataCommon rv = (DataCommon) msg.obj;
				if (count != null && !count.equals(rv.getZtxx()) && cpage != 1) {
					StaticObject.showToast(Rybc_ListActivity.this,
							"数据发生变动，请退出本页面重试");
					return;
				}
				count = rv.getZtxx();
				if (cpage == 1) {
					StaticObject.showToast(Rybc_ListActivity.this, "目前需要补采人数为："
							+ count);
				}

				tpage = Integer.parseInt(rv.getData()[0][1]);
				addDateToList(rv.getData());
				cpage++;
				if (cpage == tpage) {
					StaticObject.showToast(Rybc_ListActivity.this, "数据加载已完成");
					// listView.removeFooterView(moreView); // 移除底部视图
				}
				adapter.notifyDataSetChanged();
				isok = true;

				if (cpage == 2 && list.size() < tpage) {
					loadMoreData();
				}

				break;
			case 1:
				StaticObject.showToast(Rybc_ListActivity.this,
						msg.obj.toString());
				adapter.notifyDataSetChanged();
				break;
			case 2:
				DataCommon r = (DataCommon) msg.obj;
				String[][] getData = r.getData();
				String ryhs_state = getData[0][0];
				if (ryhs_state.equals("18")) {
					StaticObject.showToast(Rybc_ListActivity.this, "市级接口连接失败");
				} else if (ryhs_state.equals("0")) {
					// 核实状态位
					Intent intent = new Intent(Rybc_ListActivity.this,
							Rycj_dengji.class);
					RyzcVo vo = new RyzcVo(getData[0]);
					if (getData.length > 1) {
						vo.setJz_qjzdz(getData[1][0]);
					}

					intent.setFlags(1);// 暂存0，补采1
					intent.putExtra("rybc", new Gson().toJson(vo));
					startActivity(intent);
				}

				break;
			case 9:
				StaticObject.showToast(Rybc_ListActivity.this, "删除成功");
				onStart();
				break;
			case 999:
				StaticObject.showToast(Rybc_ListActivity.this, "网络连接失败");
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 加载更多数据
	 */
	private synchronized void loadMoreData() {
		dialog = StaticObject.showDialog(this, "数据查询中...");
		isok = false;
		new Thread() {
			public void run() {

				String msg = "{'data':[['','','','','','1','"
						+ preferences.getString("login_service_id", "") + "','"
						+ cpage + "']]}";
				String result = StaticObject.getMessage(Rybc_ListActivity.this,
						com.bksx.jzzslzd.common.RequestCode.RYBCCX, msg);
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					cpage = 0;
					mHandler.sendEmptyMessage(999);
				} else {
					DataCommon rv = new Gson().fromJson(result,
							DataCommon.class);
					if ("09".equals(rv.getZtbs())) {
						Message msg1 = Message.obtain();
						msg1.what = 0;
						msg1.obj = rv;
						mHandler.sendMessage(msg1);
					} else {
						cpage = 1;
						Message msg2 = Message.obtain();
						msg2.what = 1;
						msg2.obj = rv.getZtxx();
						mHandler.sendMessage(msg2);
					}
				}
			};
		}.start();
	}

	@SuppressLint("SimpleDateFormat")
	private void makeData(String xh) {
		String[][] arrayss = new String[1][18];
		SharedPreferences mySharedPreferences = getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		for (int i = 0; i < 18; i++) {
			arrayss[0][i] = "";
		}

		arrayss[0][0] = xh;

		// 是否家庭式流入
		arrayss[0][3] = "0";
		// 填表人
		arrayss[0][8] = mySharedPreferences.getString("login_admin_id", "");
		// 管理员编码
		arrayss[0][9] = mySharedPreferences.getString("login_number", "");
		// 行政区划 2015-6-12新增的
		arrayss[0][10] = mySharedPreferences.getString("login_xzqh", "");
		// 所属服务站编号
		arrayss[0][11] = mySharedPreferences.getString("login_service_id", "");
		// 所属辖区
		arrayss[0][12] = mySharedPreferences.getString("login_govern_id", "")
				.split(",")[0];

		// 注销原因
		arrayss[0][13] = "X001";

		// 所属单位
		arrayss[0][15] = mySharedPreferences.getString("login_rbac", "");

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = format.format(new Date());
		// 注销时间
		arrayss[0][16] = nowTime;
		// .........
		Ryxx ryxx = new Ryxx();
		ryxx.setData(arrayss);
		sendData(ryxx);
	}

	private void sendData(final Ryxx ryxx) {
		dialog = StaticObject.showDialog(Rybc_ListActivity.this, "发送数据中...");
		new Thread() {
			@Override
			public void run() {
				Gson gson = new Gson();
				String ryxxJson = gson.toJson(ryxx);
				String result = StaticObject.getMessage(Rybc_ListActivity.this,
						com.bksx.jzzslzd.common.RequestCode.RYXXHX, ryxxJson);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if (result.equals("")) {
					// handler.sendEmptyMessage(2);
					return;
				}
				DataCommon dataCommon = gson.fromJson(result, DataCommon.class);
				Message msg = Message.obtain();
				if (dataCommon.getZtbs().equals("09")) {
					msg.what = 9;
				} else {
					msg.what = 10;
				}
				msg.obj = dataCommon.getZtxx();
				mHandler.sendMessage(msg);
			}

		}.start();
	}
}
