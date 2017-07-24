package com.bksx.jzzslzd;

import java.util.HashMap;
import java.util.List;

import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.SxConfig;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Tab_Menu_Activity extends TabActivity {
	/**
	 * 两次点击退出系统
	 */
	private long temptime;
	private LinearLayout menu_list;
	// 左侧当前选中的菜单索引
	private int select_item = 0;
	private TabHost host;
	// 右侧每列多少个图标
	private int col_length = 2;
	// 左侧bar文字,图片，tabid,
	private String[] icon_ts, icon_imgs, tab_id;
	// 右侧tab 文字,图片，intentclass
	private String[][] tab_icon_tv, tab_icon_imgs, tab_icon_className;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_menu_a);
		// 左侧bar
		addMenuBar();
	}

	/**
	 * 增加左侧bar
	 */
	private void addMenuBar() {
		menu_list = (LinearLayout) this.findViewById(R.id.menu_bar);
		host = this.getTabHost();
		ItemOnClick itemOnClick = new ItemOnClick();
		IconOnClick iconOnClick = new IconOnClick();
		// 初始化左侧数据信息
		initMenuLeftInfo();

		if (icon_ts != null && icon_ts.length > 0) {

			for (int i = 0; i < icon_ts.length; i++) {
				// 左侧图标整个view
				View v = LayoutInflater.from(this).inflate(
						R.layout.menu_list_item, null);
				// 左侧图标文字
				TextView title = (TextView) v
						.findViewById(R.id.menu_icon_title);
				// 左侧图标图片
				ImageView icon = (ImageView) v.findViewById(R.id.menu_icon_img);

				title.setText(icon_ts[i]);
				icon.setImageResource(StaticObject.getIconId(icon_imgs[i]));
				// 增加点击事件
				v.setOnClickListener(itemOnClick);
				// 添加到左侧上
				menu_list.addView(v);

				addRightTable(iconOnClick, i);
				host.addTab(host.newTabSpec(i + "").setIndicator(i + "")
						.setContent(StaticObject.getViewId(tab_id[i])));

			}
			menu_list.getChildAt(select_item).setSelected(true);
		}
	}

	/**
	 * 初始化左侧数据信息
	 */
	private void initMenuLeftInfo() {
		SharedPreferences preferences = this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		String menu_gson = preferences.getString("menu", null);

		// 在权限列表中找到后三位为000的项，为左侧功能项
		if (menu_gson != null) {
			@SuppressWarnings("unchecked")
			List<HashMap<String, String>> menus = new Gson().fromJson(
					menu_gson, List.class);
			String[] codes = null;
			String sql = "select icon_name,icon_img,tabName,icon_code from menu_left where sfyx='1' and (";
			for (int i = 0; i < menus.size(); i++) {
				if (menus.get(i).get("id").endsWith("000")) {
					sql += " icon_code = '" + menus.get(i).get("id") + "'  or";
				}
			}

			sql = sql.substring(0, sql.length() - 3) + ") order by icon_index";
			SqliteCodeTable helper = SqliteCodeTable
					.getInstance(Tab_Menu_Activity.this);
			Cursor c = helper.Query(sql, null);
			int l = c.getCount();
			if (l > 0) {
				icon_ts = new String[l];
				icon_imgs = new String[l];
				tab_id = new String[l];
				codes = new String[l];
				for (int i = 0; c.moveToNext(); i++) {
					icon_ts[i] = c.getString(0);
					icon_imgs[i] = c.getString(1);
					tab_id[i] = c.getString(2);
					codes[i] = c.getString(3);
				}
			}
			c.close();

			if (codes != null && codes.length > 0) {
				// 初始化右侧数据信息
				initRigthInfo(menus, codes, helper);
			}

			helper.close();
		}
	}

	/**
	 * 初始化右侧数据信息
	 * 
	 * @param menus
	 * @param codes
	 * @param helper
	 */
	private void initRigthInfo(List<HashMap<String, String>> menus,
			String[] codes, SqliteCodeTable helper) {
		Cursor c;
		int l;
		String[] sqls = new String[codes.length];
		tab_icon_tv = new String[codes.length][];
		tab_icon_imgs = new String[codes.length][];
		tab_icon_className = new String[codes.length][];

		for (int i = 0; i < codes.length; i++) {
			sqls[i] = "select icon_name,icon_img,icon_className,icon_code from menu_right where sfyx='1' and (";
			for (int j = 0; j < menus.size(); j++) {
				String id = menus.get(j).get("id");
				String prefix = codes[i].substring(0, 2);
				if (id.startsWith(prefix)) {
					sqls[i] += " icon_code = '" + id + "'  or";
				}
			}
			sqls[i] = sqls[i].substring(0, sqls[i].length() - 3)
					+ ") order by icon_index";

			c = helper.Query(sqls[i], null);
			l = c.getCount();
			if (l > 0) {
				tab_icon_tv[i] = new String[l];
				tab_icon_imgs[i] = new String[l];
				tab_icon_className[i] = new String[l];
				for (int k = 0; c.moveToNext(); k++) {
					tab_icon_tv[i][k] = c.getString(0);
					tab_icon_imgs[i][k] = c.getString(1);
					tab_icon_className[i][k] = c.getString(2);
				}
			}
			c.close();
		}
	}

	/**
	 * 添加右侧table
	 * 
	 * @param iconOnClick
	 * @param tab_id
	 * @param tab_icon_tv
	 * @param tab_icon_imgs
	 * @param i
	 */
	private void addRightTable(IconOnClick iconOnClick, int i) {
		// 右侧图标整个table
		TableLayout tl = (TableLayout) findViewById(StaticObject
				.getViewId(tab_id[i]));
		// 右侧图标行 居中
		TableRow row = new TableRow(this);
		row.setGravity(Gravity.CENTER_HORIZONTAL);

		for (int j = 0; j < tab_icon_tv[i].length; j++) {
			if (row.getChildCount() == col_length) { // 换行
				row = new TableRow(this);
				row.setGravity(Gravity.CENTER_HORIZONTAL);
			}
			// 右侧图标view
			View tab_icon = LayoutInflater.from(this).inflate(R.layout.sy_icon,
					null);
			// 右侧图标上的文字
			TextView tab_tv = (TextView) tab_icon.findViewById(R.id.icon_tv);
			// 右侧图标图片
			ImageView tab_img = (ImageView) tab_icon
					.findViewById(R.id.icon_img);

			tab_tv.setText(tab_icon_tv[i][j]);
			tab_img.setImageResource(StaticObject
					.getIconId(tab_icon_imgs[i][j]));
			// 右侧图标点击事件
			tab_icon.setOnClickListener(iconOnClick);
			// 添加到row
			row.addView(tab_icon);
			// 添加到table
			if (row.getChildCount() == col_length
					|| j == tab_icon_tv[i].length - 1) {
				tl.addView(row);
			}

		}
	}

	/**
	 * 图标点击事件
	 */
	class IconOnClick implements View.OnClickListener {
		@Override
		public void onClick(View view) {

			TextView title = (TextView) view.findViewById(R.id.icon_tv);
			for (int i = 0; i < tab_icon_tv[select_item].length; i++) {
				if (title.getText().equals(tab_icon_tv[select_item][i])) {
					try {
						Class<?> c = Class
								.forName(tab_icon_className[select_item][i]);
						Intent intent = new Intent(Tab_Menu_Activity.this, c);
						startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
					} catch (ClassNotFoundException e) {
						StaticObject.print("图标点击出错"
								+ tab_icon_tv[select_item][i] + "-->"
								+ tab_icon_className[select_item][i]);
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 左侧bar菜单点击事件
	 */
	class ItemOnClick implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			menu_list.getChildAt(select_item).setSelected(false);
			TextView title = (TextView) view.findViewById(R.id.menu_icon_title);
			view.setSelected(true);
			select_item = menu_list.indexOfChild(view);

			host.setCurrentTabByTag(select_item + "");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - temptime > 2000) {
				StaticObject.showToast(this, "再按一次返回退出系统");
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
													.read(Tab_Menu_Activity.this,
															"admin_password");
											if (admin_password.equals(e
													.getText().toString()
													.trim())) {
												StaticObject.showToast(
														Tab_Menu_Activity.this,
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
														Tab_Menu_Activity.this,
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
}
