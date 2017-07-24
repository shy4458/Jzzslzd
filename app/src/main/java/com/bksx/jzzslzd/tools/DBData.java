package com.bksx.jzzslzd.tools;

import java.util.LinkedHashMap;

import android.content.Context;
import android.database.Cursor;

public class DBData {
	private static final Context context = null;

	/**
	 * 获取房屋用途
	 * 
	 * @param id
	 * @return
	 */
	public static String getCZCount(String id) {
		String sql = "select CD_NAME from SJCJ_D_CZYT where CD_ID=? and CD_AVAILABILITY=1";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, new String[] { id });
		String ver = "";
		if (c.getCount() > 0) {
			c.moveToFirst();
			ver = c.getString(0);
		}
		c.close();
		helper.close();
		return ver;

	};
	/**
	 * 获取所有权类型
	 * 
	 * @param id
	 * @return
	 */
	public static String getCatagory(String id) {
		String sql = "select CD_NAME from SJCJ_D_SYQLX where CD_ID=? and CD_AVAILABILITY=1";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, new String[] { id });
		String ver = "";
		if (c.getCount() > 0) {
			c.moveToFirst();
			ver = c.getString(0);
		}
		c.close();
		helper.close();
		return ver;
		
	};

	/**
	 * 获取数据库数据
	 * 
	 * @param sql
	 * @param map
	 */
	public static void getData(String sql, LinkedHashMap<String, String> map) {
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, null);
		while (c.moveToNext()) {
			String id = c.getString(c.getColumnIndex("CD_ID"));
			String name = c.getString(c.getColumnIndex("CD_NAME"));
			map.put(id, name);
		}
		c.close();
		helper.close();

	};

}
