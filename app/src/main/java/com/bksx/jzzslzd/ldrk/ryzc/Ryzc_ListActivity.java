package com.bksx.jzzslzd.ldrk.ryzc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.bo.RyxxzcBean;
import com.bksx.jzzslzd.bo.RyzcVo;
import com.bksx.jzzslzd.bo.UserLogin;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.ldrk.rycj.Rycj_dengji;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 人员暂存
 */
public class Ryzc_ListActivity extends Activity implements OnScrollListener {

	ArrayList<Map<String, String>> list;
	ArrayList<RyzcVo> vos, vos1;
	SimpleAdapter adapter;
	String[] from = { "xingming", "xingbie", "id", "xh", "gly" };
	int[] to = { R.id.ryhc_renyuanliebiao_item_xingming,
			R.id.ryhc_renyuanliebiao_item_xingbie,
			R.id.ryhc_renyuanliebiao_item_id, R.id.rylb_xh,
			R.id.ryhc_renyuanliebiao_item_gly };
	ListView lv;
	private int index;
	private int lastItem, page = 0, pageCount;
	private final int PAGECOUNT = 20;
	private TextView title;
	private LinearLayout checkLayout;// 筛选框
	private SelectViewAndHandlerAndMsg select_gly;
	private String[][] glylb;
	private TextView ryhc_renyuanliebiao_gly;
	private Button btn_send, btn_download;
	private ImageButton check;
	private ProgressDialog dialog;
	private String[][] arrs, arrs1, arrs2;
	protected String result, result1, result2, date, id;
	private SharedPreferences preference;
	private UserLogin userData;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 11:
				if ("".equals(select_gly.getCodeId())) {
					onStart();
				} else {
					show();
					adapter.notifyDataSetChanged();
				}
				checkLayout.setVisibility(View.GONE);
				break;
			case 1:
				StaticObject.showToast(Ryzc_ListActivity.this, "上传备份成功");
				RyzcVo vo = null;
				SqliteHelper helper1 = SqliteHelper
						.getInstance(Ryzc_ListActivity.this);
				SQLiteDatabase database2 = helper1.getDatabase();
				try {
					database2.beginTransaction();
					for (int i = 0; i < vos.size(); i++) {
						if (vos.get(i).getGlybm() == null
								|| "".equals(vos.get(i).getGlybm())) {
							vo = vos.get(i);
							vo.setTbr(preference
									.getString("login_admin_id", ""));
							vo.setGlybm(preference
									.getString("login_number", ""));
							vo.setGlyxm(preference.getString(
									"login_admin_name", ""));
							StringBuilder vo_sb = new StringBuilder();
							vo_sb.append(new Gson().toJson(vo));
							String id = vo.getJb_sfz()+vo.getJb_xm();
							String updateSql = "update ryxxzcb set json='"
									+ vo_sb.toString()
									+ "'where id='"+id+"'";
							helper1.execSQL(updateSql, null);
						}
					}
					database2.setTransactionSuccessful();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					database2.endTransaction();
				}
				helper1.close();
				onStart();
				checkLayout.setVisibility(View.GONE);
				break;
			case 2:
				StaticObject.showToast(Ryzc_ListActivity.this,
						msg.obj.toString());
				break;
			case 3:
				vos = (ArrayList<RyzcVo>) msg.obj;
				for (int i = 0; i < vos.size(); i++) {
					date = vos.get(i).getDjrq();
				}
				Builder builder = new Builder(Ryzc_ListActivity.this);
				builder.setTitle("恢复备份");
				builder.setMessage("是否恢复当前账户上一次于" + date.substring(0, 4) + "-"
						+ date.substring(4, 6) + "-" + date.substring(6, 8)
						+ "日备份的数据?");
				builder.setPositiveButton("是", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						getData2();
					}
				});
				builder.setNegativeButton("否",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
				break;
			case 4:
				vos = (ArrayList<RyzcVo>) msg.obj;
				StaticObject.showToast(Ryzc_ListActivity.this, "恢复备份成功");
				ryhc_renyuanliebiao_gly.setText("请选择管理员");
				SqliteHelper helper = SqliteHelper
						.getInstance(Ryzc_ListActivity.this);
				SQLiteDatabase database = helper.getDatabase();
				try {
					database.beginTransaction();
					for (int i = 0; i < vos.size(); i++) {
						String id1 = vos.get(i).getJb_sfz()
								+ vos.get(i).getJb_xm();
						String json = new Gson().toJson(vos.get(i));
						String tjsj = vos.get(i).getDjrq();
						String sql = "select id from ryxxzcb where id ='" + id1
								+ "' order by tjsj desc ";
						Cursor c = helper.Query(sql, null);
						if (c.getCount() == 0) {
							String sql1 = "insert into ryxxzcb(id,json,tjsj)values('"
									+ id1 + "','" + json + "','" + tjsj + "')";
							helper.execSQL(sql1, null);
						}

					}
					database.setTransactionSuccessful();
				} catch (Exception e) {
					e.printStackTrace();
					StaticObject.showToast(Ryzc_ListActivity.this, "已存在");
				} finally {
					database.endTransaction();
				}
				helper.close();
				checkLayout.setVisibility(View.GONE);
				onStart();
				break;
			case 404:
				StaticObject.showToast(Ryzc_ListActivity.this, "网络连接失败");
				break;
			}
		}

		private void show() {
			String codeId = select_gly.getCodeId();
			String sql = "select id,json,tjsj from ryxxzcb where json like '%\"glybm\":\""
					+ codeId
					+ "\"%' or json not like '%\"glybm\":%' order by tjsj desc ";
			SqliteHelper helper = SqliteHelper
					.getInstance(Ryzc_ListActivity.this);
			Cursor c = helper.Query(sql, null);
			int l = c.getCount();
			title.setText("人员列表(共" + c.getCount() + "人)");
			list.clear();
			vos.clear();
			index = 0;
			if (l > 0) {
				while (c.moveToNext()) {
					RyzcVo vo = new Gson()
							.fromJson(c.getString(c.getColumnIndex("json")),
									RyzcVo.class);
					Map<String, String> hashMap = new HashMap<String, String>();
					hashMap.put(from[0], vo.getJb_xm());
					hashMap.put(from[1], vo.getJb_xb());
					hashMap.put(from[2], vo.getJb_sfz());
					hashMap.put(from[3], ++index + "");
					if ("".equals(vo.getGlyxm()) || vo.getGlyxm() == null) {
						hashMap.put(from[4], "无");
					} else {
						hashMap.put(from[4], vo.getGlyxm());
					}
					list.add(hashMap);
					vos.add(vo);
				}
				c.close();
				helper.close();
			}

		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ryhc_renyuanliebiao);
		init();
	}

	@Override
	protected void onStart() {
		pageCount = getPageCount();
		index = 0;
		page = 0;
		list.clear();
		vos1 = new ArrayList<RyzcVo>();
		getMoreData();
		adapter.notifyDataSetChanged();
		super.onStart();
	}

	private void init() {
		userData = StaticObject.getUserData(Ryzc_ListActivity.this);
		vos = new ArrayList<RyzcVo>();
		vos1 = new ArrayList<RyzcVo>();
		preference = getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		check = (ImageButton) findViewById(R.id.ryhc_renyuanliebiao_check);
		//check.setVisibility(View.VISIBLE);
		lv = (ListView) findViewById(R.id.ryhc_renyuanliebiao_list);
		checkLayout = (LinearLayout) findViewById(R.id.ryhc_renyuanliebiao_checkLayout);
		ryhc_renyuanliebiao_gly = (TextView) findViewById(R.id.ryhc_renyuanliebiao_gly);
		btn_send = (Button) findViewById(R.id.dengji_btn_send);
		btn_download = (Button) findViewById(R.id.dengji_btn_download);
		ImageButton ib = (ImageButton) findViewById(R.id.ryhc_renyuanliebiao_back);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		title = (TextView) findViewById(R.id.ryhc_renyuanliebiao_title);
		list = new ArrayList<Map<String, String>>();

		adapter = new SimpleAdapter(this, list,
				R.layout.ryhc_renyuanliebiao_item, from, to);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		adapter.notifyDataSetChanged();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Ryzc_ListActivity.this,
						Rycj_dengji.class);
				intent.setFlags(0);// 暂存0，
				intent.putExtra("state",true);//true 为缓存
				intent.putExtra("ryzcData", new Gson().toJson(vos1.get(arg2)));
				startActivity(intent);
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				RyzcVo vo = vos1.get(arg2);
				final String id = vo.getJb_sfz() + vo.getJb_xm();
				new AlertDialog.Builder(Ryzc_ListActivity.this).setTitle("提示")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("删除该条暂存信息？").setNegativeButton("否", null)
						.setPositiveButton("是", new OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								SqliteHelper helper = SqliteHelper
										.getInstance(Ryzc_ListActivity.this);
								String sql = "delete from ryxxzcb where id='"
										+ id + "'";
								helper.execSQL(sql, null);
								helper.close();
								Ryzc_ListActivity.this.onStart();
							}
						}).show();
				return true;
			}
		});
		//checkLayout.setOnClickListener(null);

		glylb = new Gson().fromJson(preference.getString("glylb", null),
				String[][].class);
		LinkedHashMap<String, String> mzmap = new LinkedHashMap<String, String>();
		mzmap.put("", "请选择管理员");
		if (glylb != null && glylb.length > 0) {
			for (int i = 0; i < glylb.length; i++) {
				mzmap.put(glylb[i][2], glylb[i][1] + "(" + glylb[i][2] + ")");
			}
		}

		select_gly = new SelectViewAndHandlerAndMsg(this, "管理员", mzmap,
				ryhc_renyuanliebiao_gly, handler, 11, "");
		/**
		 * 上传备份
		 */
		btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = sendCount();
				Builder builder = new Builder(Ryzc_ListActivity.this);
				builder.setTitle("上传备份");
				if (count == 0) {
					StaticObject.showToast(Ryzc_ListActivity.this, "无暂存人员信息");
					return;
				}
				builder.setMessage("是否上传当前账户的" + count + "条暂存人员的信息?");
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendData();
					}
				});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();

			}

		});
		/**
		 * 下载
		 */
		btn_download.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getData1();
			}
		});

	}

	private void getData1() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				arrs1 = new String[1][1];
				arrs1[0][0] = preference.getString("login_number", "");
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrs1);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result1 = StaticObject.getMessage(Ryzc_ListActivity.this,
						RequestCode.RYXXXZ, data);

				if (RequestCode.CSTR.equals(result1)) {
					return;
				}
				if ("".equals(result1)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					RyxxzcBean RyxxzcBean = gson.fromJson(result1,
							RyxxzcBean.class);

					if ("09".equals(RyxxzcBean.getZtbs())) {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						message3.obj = RyxxzcBean.getList();
						handler.sendMessage(message3);// 发送消息
					} else {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						message2.obj = RyxxzcBean.getZtxx();
						handler.sendMessage(message2);// 发送消息
					}
				}
			}
		}).start();

	}

	private void getData2() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				arrs2 = new String[1][1];
				arrs2[0][0] = preference.getString("login_number", "");
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrs2);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result2 = StaticObject.getMessage(Ryzc_ListActivity.this,
						RequestCode.RYXXXZ, data);

				if (RequestCode.CSTR.equals(result2)) {
					return;
				}
				if ("".equals(result2)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					RyxxzcBean RyxxzcBean = gson.fromJson(result2,
							RyxxzcBean.class);
					if ("09".equals(RyxxzcBean.getZtbs())) {
						Message message4 = new Message();// 创建消息
						message4.what = 4;// 设置消息的what值
						message4.obj = RyxxzcBean.getList();
						handler.sendMessage(message4);// 发送消息

					} else {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						message2.obj = RyxxzcBean.getZtxx();
						handler.sendMessage(message2);// 发送消息
					}

				}

			}
		}).start();
	}

	private void sendData() {
		sendCount();
		dialog = StaticObject.showDialog(Ryzc_ListActivity.this, "信息备份中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				arrs = new String[vos.size()][59];
				for (int i = 0; i < vos.size(); i++) {
					// 基本信息
					arrs[i][0] = vos.get(i).getJb_xm();
					arrs[i][1] = vos.get(i).getJb_mz();
					arrs[i][2] = vos.get(i).getJb_sfz();
					arrs[i][3] = vos.get(i).getJb_csrq();
					arrs[i][4] = vos.get(i).getJb_xb();
					arrs[i][5] = vos.get(i).getJb_hjdz();
					arrs[i][6] = vos.get(i).getJb_hjxxdz();
					arrs[i][7] = vos.get(i).getJb_ssxq();
					arrs[i][8] = vos.get(i).getJb_sjycd();
					arrs[i][9] = vos.get(i).getJb_zzmm();
					arrs[i][10] = vos.get(i).getJb_jzzj();
					arrs[i][11] = vos.get(i).getJb_hjlb();
					arrs[i][12] = vos.get(i).getJb_myjzz();
					arrs[i][13] = vos.get(i).getJb_csd();
					arrs[i][14] = vos.get(i).getJb_hyzk();
					arrs[i][15] = vos.get(i).getJb_hyzm();
					arrs[i][16] = vos.get(i).getJb_lxdh();
					arrs[i][17] = vos.get(i).getJb_isScanned();
					arrs[i][18] = vos.get(i).getJb_photoPath();
					// 家庭户信息
					arrs[i][19] = vos.get(i).getJt_sfjth();
					arrs[i][20] = vos.get(i).getJt_sfhz();
					arrs[i][21] = vos.get(i).getJt_wlrk();
					arrs[i][22] = vos.get(i).getJt_16yx();
					arrs[i][23] = vos.get(i).getJt_16nan();
					arrs[i][24] = vos.get(i).getJt_16nv();
					arrs[i][25] = vos.get(i).getJt_yhzgx();
					arrs[i][26] = vos.get(i).getJt_yhzqagx();
					arrs[i][27] = vos.get(i).getJt_hzdjbh();
					// 居住信息
					arrs[i][28] = vos.get(i).getJz_lkyjrq();
					arrs[i][29] = vos.get(i).getJz_ljrq();
					arrs[i][30] = vos.get(i).getJz_lxzdrq();
					arrs[i][31] = vos.get(i).getJz_ljyy();
					arrs[i][32] = vos.get(i).getJz_ljqtyy();
					arrs[i][33] = vos.get(i).getJz_qjzdz();
					arrs[i][34] = vos.get(i).getJz_jzlx();
					arrs[i][35] = vos.get(i).getJz_fzxm();
					arrs[i][36] = vos.get(i).getJz_xzdz();
					arrs[i][37] = vos.get(i).getJz_fwqtjzlx();
					// 就业信息
					arrs[i][38] = vos.get(i).getJy_mqzk();
					arrs[i][39] = vos.get(i).getJy_jydwmc();
					arrs[i][40] = vos.get(i).getJy_dwdjbh();
					arrs[i][41] = vos.get(i).getJy_dwxxdz();
					arrs[i][42] = vos.get(i).getJy_dwsshy();
					arrs[i][43] = vos.get(i).getJy_dwqtsshy();
					arrs[i][44] = vos.get(i).getJy_csgz();
					arrs[i][45] = vos.get(i).getJy_qtcsgz();
					arrs[i][46] = vos.get(i).getJy_zy();
					arrs[i][47] = vos.get(i).getJy_qdldht();
					arrs[i][48] = vos.get(i).getJy_dwdz();
					arrs[i][49] = vos.get(i).getJy_zjcjsb();
					arrs[i][50] = vos.get(i).getJy_cjsb();
					arrs[i][51] = vos.get(i).getJy_xxmc();
					arrs[i][52] = vos.get(i).getJy_xxszd();
					arrs[i][53] = vos.get(i).getJy_xxxx();
					// 备份信息
					arrs[i][54] = vos.get(i).getBz();
					// 管理员信息
					if (vos.get(i).getGlybm() == null
							|| "".equals(vos.get(i).getGlybm())) {
						arrs[i][55] = preference
								.getString("login_admin_id", "");
						arrs[i][56] = preference.getString("login_number", "");
						arrs[i][57] = preference.getString("login_admin_name",
								"");
					} else {
						arrs[i][55] = vos.get(i).getTbr();
						arrs[i][56] = vos.get(i).getGlybm();
						arrs[i][57] = vos.get(i).getGlyxm();
					}
					arrs[i][58] = vos.get(i).getTbrq();
				}
				Ryxx ryxx = new Ryxx();
				ryxx.setData(arrs);
				Gson gson = new Gson();
				String data = gson.toJson(ryxx);
				result = StaticObject.getMessage(Ryzc_ListActivity.this,
						RequestCode.RYXXZCDJ, data);
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
						handler.sendMessage(message1);// 发送消息

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

	private int sendCount() {
		String codeId = preference.getString("login_number", "");
		String sql = "select id,json,tjsj from ryxxzcb where json like '%\"glybm\":\""
				+ codeId + "\"%' or json not like '%\"glybm\":%' ";
		SqliteHelper helper = SqliteHelper.getInstance(Ryzc_ListActivity.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		vos.clear();
		if (l > 0) {
			while (c.moveToNext()) {
				RyzcVo vo = new Gson().fromJson(
						c.getString(c.getColumnIndex("json")), RyzcVo.class);
				vos.add(vo);
			}
		}
		return vos.size();

	}

//	public void showCheck(View view) {
//		int v = checkLayout.getVisibility();
//		if (v == View.GONE) {
//			checkLayout.setVisibility(View.VISIBLE);
//		} else if (v == View.VISIBLE) {
//			checkLayout.setVisibility(View.GONE);
//		}
//	}

	private void getMoreData() {
		String sql = "select id,json,tjsj from ryxxzcb where glybm='"+ userData.getU_id()+"' order by tjsj desc limit "
				+ PAGECOUNT + " offset " + PAGECOUNT * page;
		SqliteHelper helper = SqliteHelper.getInstance(this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			while (c.moveToNext()) {
				RyzcVo vo = new Gson().fromJson(
						c.getString(c.getColumnIndex("json")), RyzcVo.class);
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put(from[0], vo.getJb_xm());
				hashMap.put(from[1], vo.getJb_xb());
				hashMap.put(from[2], vo.getJb_sfz());
				hashMap.put(from[3], ++index + "");
				if ("".equals(vo.getGlyxm()) || vo.getGlyxm() == null) {
					hashMap.put(from[4], "无");
				} else {
					hashMap.put(from[4], vo.getGlyxm());
				}
				list.add(hashMap);
				vos1.add(vo);
			}
			c.close();
			helper.close();
		}
		page++;
	}

	private int getPageCount() {
		String sql = "select count(*) from ryxxzcb where glybm='"+userData.getU_id()+"' order by tjsj desc";
		SqliteHelper helper = SqliteHelper.getInstance(this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			c.moveToFirst();
			pageCount = c.getInt(0) % PAGECOUNT == 0 ? c.getInt(0) / PAGECOUNT
					: c.getInt(0) / PAGECOUNT + 1;
			title.setText("人员列表(共" + c.getInt(0) + "人)");
			c.close();
			helper.close();
		}

		return pageCount;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount; // 减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		if (lastItem == list.size()
				&& arg1 == OnScrollListener.SCROLL_STATE_IDLE
				&& page < pageCount) {
			getMoreData();
			adapter.notifyDataSetChanged();
		} else if (arg0.getLastVisiblePosition() == arg0.getCount() - 1
				&& arg1 == OnScrollListener.SCROLL_STATE_IDLE
				&& page <= pageCount) {
			// 最后一页 下拉提示到底了
			StaticObject.showToast(Ryzc_ListActivity.this, "已显示全部数据");
		}
	}

}
