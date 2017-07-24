package com.bksx.jzzslzd;

/**
 * 首页界面:只是首页界面上中间个快捷图标的那块，采用GridView做的
 * 
 * 1.初始化图标:addIcons(); 
 * @author Y_Jie
 * 
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.SqliteCodeTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Tab_Sy_Activity extends Activity {
	/**
	 * 两次点击退出系统
	 */
	private long temptime;
	/**
	 * 图标上的文字数组
	 */
	private String[] icon_ts;
	/**
	 * 图标上的图片数组
	 */
	private String[] icon_imgs;
	/**
	 * 图标上的className
	 */
	private String[] icon_className;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_sy_a);
		initIconInfo();
		if (icon_ts != null && icon_ts.length > 0) {
			addIcons();
		}

	}

	@Override
	protected void onStart() {
		StaticObject.sjqd(this);
		super.onStart();
	}

	/**
	 * 初始化图标
	 */
	private void addIcons() {
		GridView gridview = (GridView) findViewById(R.id.tab_sy_layout);

		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < icon_ts.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", StaticObject.getIconId(icon_imgs[i]));// 添加图像资源的ID
			map.put("ItemText", icon_ts[i]);// 按序号做ItemText
			lstImageItem.add(map);
		}
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.sy_icon,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.icon_img, R.id.icon_tv });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());
	}



	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> adapter, View arg1,
				int position, long arg3) {
			// 显示所选Item的ItemText
			try {
				Class<?> c = Class.forName(icon_className[position]);
				Intent intent = new Intent(Tab_Sy_Activity.this, c);
				startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
			} catch (ClassNotFoundException e) {
				StaticObject.print("图标点击出错" + icon_ts[position] + "-->"
						+ icon_className[position]);
				e.printStackTrace();
			}

		}

	}

	/**
	 * 图标对象初始化
	 */
	private void initIconInfo() {
		String sql = "select icon_name, icon_img, icon_className from desktop_icon where sfyx='1' order by icon_index";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(Tab_Sy_Activity.this);
		Cursor c = helper.Query(sql, null);
		int l = c.getCount();
		if (l > 0) {
			icon_ts = new String[l];
			icon_imgs = new String[l];
			icon_className = new String[l];
			for (int i = 0; c.moveToNext(); i++) {
				icon_ts[i] = c.getString(0);
				icon_imgs[i] = c.getString(1);
				icon_className[i] = c.getString(2);
			}
		}
		c.close();
		helper.close();
	}

}