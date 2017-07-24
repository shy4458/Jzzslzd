package com.bksx.jzzslzd;

import java.util.List;

import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.ReturnDzk;
import com.bksx.jzzslzd.bo.ReturnDzkxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.SxConfig;
import com.bksx.jzzslzd.service.UpdateBzdzk;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Tab_Set_Activity extends TabActivity {
	/**
	 * 两次点击退出系统
	 */
	private long temptime;
	private LinearLayout set_list;
	private TabHost host;
	// 左侧当前选中的菜单索引
	private int select_item = 0;
	private SharedPreferences preferences;
	private EditText jmm, xmm, cfmm;
	private Button qd, dzkbb;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_set_a);
		addMenuBar();
		preferences = this.getSharedPreferences(StaticObject.SHAREPREFERENC,
				Activity.MODE_PRIVATE);
		((TextView) findViewById(R.id.xm)).setText(preferences.getString(
				"login_admin_name", "")
				+ "("
				+ preferences.getString("login_number", "") + ")");
		((TextView) findViewById(R.id.xzqh)).setText(preferences.getString(
				"login_area", ""));
		((TextView) findViewById(R.id.ssxq)).setText(preferences.getString(
				"login_govern_name", ""));
		try {
			((TextView) findViewById(R.id.xtbb)).setText(getPackageManager()
					.getPackageInfo(getPackageName(), 0).versionName);
			((TextView) findViewById(R.id.sjkbb)).setText(getDBVersion());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		jmm = (EditText) findViewById(R.id.jmm);
		xmm = (EditText) findViewById(R.id.xmm);
		cfmm = (EditText) findViewById(R.id.cfmm);

		qd = (Button) findViewById(R.id.xgmm);
		qd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String jmms = jmm.getText().toString().trim();
				if (!jmms.equals(preferences.getString("password", ""))) {
					StaticObject.showToast(Tab_Set_Activity.this, "旧密码输入错误");
					return;
				}
				String xmms = xmm.getText().toString().trim();
				String cfmms = cfmm.getText().toString().trim();
				if (xmms == null || "".equals(xmms)) {
					StaticObject.showToast(Tab_Set_Activity.this, "请输入新密码");
					return;
				}
				if (cfmms == null || "".equals(cfmms)) {
					StaticObject.showToast(Tab_Set_Activity.this, "请再次输入新密码");
					return;
				}
				if (!xmms.equals(cfmms)) {
					StaticObject.showToast(Tab_Set_Activity.this, "两次密码输入不一致");
					return;
				}
				xgmm(jmms, xmms);
			}
		});
		dzkbb = (Button) findViewById(R.id.dzkbb);
		dzkbb.setText(getDzkVersion());
		dzkbb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getDzkbb(false);
			}
		});
		dzkbb.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				new AlertDialog.Builder(Tab_Set_Activity.this)
						.setTitle("提示")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("是否需要重新下载地址库？")
						.setNegativeButton("否", null)
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										getDzkbb(true);
									}
								}).show();
				return false;
			}
		});
	}

	UpdateBzdzk ub = null;
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 密码修改成功
			case 1:
				StaticObject.showToast(Tab_Set_Activity.this, "修改成功");
				break;
			// 地址库需要更新
			case 2:
				ReturnDzkxx rv = new Gson().fromJson((String) msg.obj,
						ReturnDzkxx.class);
				List<ReturnDzk> dzs = (List<ReturnDzk>) rv.getList();
				if (ub == null) {
					ub = new UpdateBzdzk(Tab_Set_Activity.this, dzkbb);
				}
				ub.onStart(dzs);
				break;
			// 地址库需要更新
			case 3:
				StaticObject.showToast(Tab_Set_Activity.this, "地址库暂无更新包");
				break;
			case 999:
				StaticObject.showToast(Tab_Set_Activity.this, "网络连接失败");
				break;

			default:
				break;
			}
		};
	};

	public String xgmm(final String jmms, final String xmms) {
		dialog = StaticObject.showDialog(this, "数据提交中...");
		new Thread() {
			public void run() {
				String data = "{'passWordYuan':'" + StaticObject.Md5(jmms)
						+ "','passWord':'" + StaticObject.Md5(xmms) + "'}";
				String result = StaticObject.getMessage(Tab_Set_Activity.this,
						"000002", data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 999;// 设置消息的what值
					myHandler.sendMessage(message);// 发送消息
				} else {
					Gson gson = new Gson();
					DataCommon returnValue = gson.fromJson(result,
							DataCommon.class);

					if (returnValue != null) {
						String ztbs = returnValue.getZtbs();
						if ("09".equals(ztbs)) {
							preferences.edit().putString("password", xmms)
									.commit();
							myHandler.sendEmptyMessage(1);// 发送消息
						}
					}
				}

			}
		}.start();

		return null;
	}

	public String getDzkbb(final boolean qzgx) {
		dialog = StaticObject.showDialog(this, "数据提交中...");
		new Thread() {
			public void run() {
				// TODO 修改参数及业务代码
				String data = "{'scrq':'" + getDzkVersion() + "5900'}";
				if (qzgx) {
					data = "{'scrq':'20010101010101'}";
				}

				String result = StaticObject.getMessage(Tab_Set_Activity.this,
						"070004", data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 999;// 设置消息的what值
					myHandler.sendMessage(message);// 发送消息
				} else {
					Gson gson = new Gson();
					DataCommon returnValue = gson.fromJson(result,
							DataCommon.class);

					if (returnValue != null) {
						String ztbs = returnValue.getZtbs();
						if ("09".equals(ztbs)) {
							Message msg = Message.obtain();
							msg.what = 2;
							msg.obj = result;
							myHandler.sendMessage(msg);// 发送消息
						} else if ("93".equals(ztbs)) {
							myHandler.sendEmptyMessage(3);// 发送消息
						}
					}
				}

			}
		}.start();

		return null;
	}

	/**
	 * 获取地址库版本
	 * 
	 * @return
	 */
	private String getDzkVersion() {
		String sql = "select sys_version from version ";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(this);
		Cursor c = helper.Query(sql, null);
		String ver = "";
		if (c.getCount() > 0) {
			c.moveToFirst();
			ver = c.getString(0);
		}
		c.close();
		helper.close();
		return ver;
	}

	/**
	 * 增加左侧bar
	 */
	private void addMenuBar() {
		set_list = (LinearLayout) this.findViewById(R.id.set_bar);
		host = this.getTabHost();
		ItemOnClick itemOnClick = new ItemOnClick();
		// 左侧bar文字
		// String[] icon_ts = new String[] { "用户信息", "快捷键设置", "修改密码", "系统信息" };
		String[] icon_ts = new String[] { "用户信息", "修改密码" };
		int[] content_ts = new int[] { R.id.set_user_info, R.id.set_xxmm };
		// 左侧bar图片
		// int[] icon_imgs = new int[] { R.drawable.set_sysinfo,
		// R.drawable.set_kjj, R.drawable.set_updatepwd,
		// R.drawable.set_sysinfo };
		int[] icon_imgs = new int[] { R.drawable.set_sysinfo,
				R.drawable.set_updatepwd };

		for (int i = 0; i < icon_ts.length; i++) {
			// 左侧图标整个view
			View v = LayoutInflater.from(this).inflate(R.layout.set_list_item,
					null);
			// 左侧图标文字
			TextView title = (TextView) v.findViewById(R.id.set_icon_title);
			// 左侧图标图片
			ImageView icon = (ImageView) v.findViewById(R.id.set_icon_img);

			title.setText(icon_ts[i]);
			icon.setImageResource(icon_imgs[i]);
			// 增加点击事件
			v.setOnClickListener(itemOnClick);
			// 添加到左侧上
			set_list.addView(v);

			host.addTab(host.newTabSpec(i + "").setIndicator(i + "")
					.setContent(content_ts[i]));

		}
		set_list.getChildAt(select_item).setSelected(true);
	}

	/**
	 * 左侧bar菜单点击事件
	 */
	class ItemOnClick implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			set_list.getChildAt(select_item).setSelected(false);
			view.setSelected(true);
			select_item = set_list.indexOfChild(view);
			host.setCurrentTabByTag(select_item + "");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		StaticObject.print("");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - temptime > 2000) {
				StaticObject.showToast(Tab_Set_Activity.this, "再按一次返回退出系统");
				temptime = System.currentTimeMillis();
			} else {
				if (!android.os.Build.MODEL.startsWith("BM")) {
					final EditText e = new EditText(getApplicationContext());
					AlertDialog ad = new AlertDialog.Builder(this)
							.setTitle("请输入管理员密码")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setView(e)
							.setPositiveButton("退出",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											String admin_password = SxConfig
													.read(Tab_Set_Activity.this,
															"admin_password");
											if (admin_password.equals(e
													.getText().toString()
													.trim())) {
												StaticObject.showToast(
														Tab_Set_Activity.this,
														"系统退出");
												Intent i = new Intent();
												i.setClassName(
														"com.android.launcher",
														"com.android.launcher2.Launcher");
												startActivity(i);
												finish();
												System.exit(0);
											} else {
												StaticObject.showToast(
														Tab_Set_Activity.this,
														"密码错误，退出失败");
											}
										}
									}).create();

					ad.show();
				} else {
					finish();
					System.exit(0);
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * 获取数据库版本
	 * 
	 * @return
	 */
	private String getDBVersion() {
		String sql = "select database_version from version ";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(Tab_Set_Activity.this);
		Cursor c = helper.Query(sql, null);
		String ver = "";
		if (c.getCount() > 0) {
			c.moveToFirst();
			ver = c.getString(0);
		}
		c.close();
		helper.close();
		return ver;
	}
}
