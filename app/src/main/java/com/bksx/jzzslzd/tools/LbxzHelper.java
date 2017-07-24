package com.bksx.jzzslzd.tools;

import java.util.LinkedHashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

public class LbxzHelper {
	private final TextView tv_sjsb_sjCatagary1;// 1级
	private final TextView tv_sjsb_sjCatagary2;// 2级
	private LinkedHashMap<String, String> map1;
	private LinkedHashMap<String, String> map2;
	private String[] values1;
	private String[] values2;
	private Context context;

	/**
	 * 初始化对象方法
	 * 
	 * @param context
	 *            传入Activity.this
	 * @param tv_sjsb_sjCatagary1
	 *            1级TextView
	 * @param tv_sjsb_sjCatagary2
	 *            2级TextView
	 */
	public LbxzHelper(final Context context, TextView tv_sjsb_sjCatagary1,
			TextView tv_sjsb_sjCatagary2) {
		// 对象初始化
		this.context = context;
		this.tv_sjsb_sjCatagary1 = tv_sjsb_sjCatagary1;
		this.tv_sjsb_sjCatagary2 = tv_sjsb_sjCatagary2;

		// 对象初始化
		map1 = new LinkedHashMap<String, String>();
		map2 = new LinkedHashMap<String, String>();

		// 初始化1级对象
		getFirst();
		values1 = setValues(map1);
		tv_sjsb_sjCatagary1.setText("请选择");
		tv_sjsb_sjCatagary2.setText("---");
		// 1级添加监听事件
		this.tv_sjsb_sjCatagary1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				show1();
			}
		});

	}

	protected void show1() {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
				.setItems(values1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 显示选择的值
						LbxzHelper.this.tv_sjsb_sjCatagary1
								.setText(values1[which]);
						// 获取选择的cd_id值
						String cd_id_1 = getCodeId(map1,
								LbxzHelper.this.tv_sjsb_sjCatagary1);
						// 初始化2级对象
						initSecondView(cd_id_1);
					}
				}).show();
	}

	/**
	 * 初始化2级对象
	 * 
	 * @param cd_id
	 */
	protected void initSecondView(String cd_id) {
		// 如果1级选择的是“请选择或null”,将2级置空
		if (cd_id == null || "".equals(cd_id)) {
			this.tv_sjsb_sjCatagary2.setText("---");
			this.tv_sjsb_sjCatagary2.setOnClickListener(null);
			return;
		}
		// 初始化2级数据
		getSecond(cd_id);
		values2 = setValues(map2);
		this.tv_sjsb_sjCatagary2.setText("请选择");
		// 2级添加监听
		this.tv_sjsb_sjCatagary2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				show2();
			}
		});
	}

	protected void show2() {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
				.setItems(values2, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 只需要设置2级选择的值
						LbxzHelper.this.tv_sjsb_sjCatagary2
								.setText(values2[which]);
					}
				}).show();
	}

	/**
	 * 初始化2级数据
	 */
	private void getSecond(String cd_id) {
		map2.clear();
		if ("".equals(cd_id)) {
			return;
		}
		String f = "%";
		if (cd_id != null && !"".equals(cd_id)) {
			f = cd_id.substring(0, 1) + f;
		}
		String sql = "select cd_id,cd_name from cy_d_sjlb where cd_id like '"
				+ f + "' and cd_id!= '" + cd_id
				+ "' and cd_availability='1' order by cd_index";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, null);

		map2.put("", "请选择");
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				map2.put(c.getString(0), c.getString(1));
			}
		}
		c.close();
		helper.close();
	}

	/**
	 * 获取值 用于提交或校验
	 */
	public String getCodeId() {
		String id2 = getCodeId(map2, tv_sjsb_sjCatagary2);
		if (!"".equals(id2)) {
			return id2;
		}
		String id1 = getCodeId(map1, tv_sjsb_sjCatagary1);
		if (!"".equals(id1)) {
			return id1;
		}
		return "";
	}

	public void requestFocus() {
		this.tv_sjsb_sjCatagary1.requestFocus();
		String id1 = getCodeId(map1, tv_sjsb_sjCatagary1);
		if (!"".equals(id1)) {
			show1();
			return;
		}
		String id2 = getCodeId(map1, tv_sjsb_sjCatagary2);
		if (!"".equals(id2)) {
			show2();
			return;
		}

	}

	public void setCodeId(String codeId) {
		if (codeId == null || "".equals(codeId)) {
			this.tv_sjsb_sjCatagary1.setText(map1.get(codeId));
			this.tv_sjsb_sjCatagary2.setText("---");
		}

	}

	/**
	 * 获取值
	 */
	private String getCodeId(LinkedHashMap<String, String> map, TextView view) {
		return getKey(map, view.getText().toString().trim());
	}

	private String getKey(LinkedHashMap<String, String> map, Object value) {
		if (value == null)
			return "";
		for (String key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}

		return "";
	}

	/**
	 * 初始化1级对象
	 */
	private void getFirst() {
		String sql = "select cd_id,cd_name from cy_d_sjlb where cd_id like '_0000' and cd_availability='1' order by cd_index";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, null);

		map1.put("", "请选择");
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				map1.put(c.getString(0), c.getString(1));
			}
		}
		c.close();
		helper.close();
	}

	private String[] setValues(LinkedHashMap<String, String> map) {
		String[] values = new String[map.size()];
		int i = 0;
		for (Object key : map.keySet()) {
			values[i] = map.get(key);
			i++;
		}
		return values;
	}

}
