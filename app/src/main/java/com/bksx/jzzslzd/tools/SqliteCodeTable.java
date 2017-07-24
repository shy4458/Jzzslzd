package com.bksx.jzzslzd.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.SxConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

/**
 * 数据库工具类(存放代码表数据，更新这里。会更新代码表)
 * 
 * @author Y_Jie
 * 
 */
public class SqliteCodeTable {
	@SuppressLint("SdCardPath")
	private final String DATABASE_PATH = "/data/data/com.bksx.jzzslzd/databases";
	private final String DATABASE_FILENAME = "codetable.db";
	private SQLiteDatabase database;
	private Context context;
	private static SqliteCodeTable helper = new SqliteCodeTable();

	private SqliteCodeTable() {

	}

	/**
	 * 单例模式
	 * 
	 * @param context
	 * @return
	 */
	public static SqliteCodeTable getInstance(Context context) {
		// 打开数据库，database是在Main类中定义的一个SQLiteDatabase类型的变量
		helper.context = context;
		helper.database = helper.openDatabase();
		return helper;
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Cursor Query(String sql, String[] selectionArgs) {
		if (selectionArgs != null) {
			for (int i = 0; i < selectionArgs.length; i++) {
				sql.replace("?", "'" + selectionArgs[i] + "'");
			}
		}
		StaticObject.print("数据库查询:" + sql);
		if (helper.database == null) {
			helper.database = this.openDatabase();
		}
		return helper.database.rawQuery(sql, selectionArgs);
	}

	/**
	 * 事物执行Sql
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public void execSQL(String sql, Object[] args) {
		StaticObject.print("数据库行执行:" + sql);
		if (helper.database == null) {
			helper.database = this.openDatabase();
		}

		try {
			helper.database.beginTransaction();
			if (args == null) {
				helper.database.execSQL(sql);
			} else {
				helper.database.execSQL(sql, args);
			}
			helper.database.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 结束事务
			helper.database.endTransaction();
		}

	}

	/**
	 * 事物执行Sql
	 * 
	 * @param sqls
	 * @return
	 */
	public void execSQL(String[] sqls, Handler handler, int index) {
		if (helper.database == null) {
			helper.database = this.openDatabase();
		}

		try {
			int n = 0;
			helper.database.beginTransaction();
			for (int i = 0; i < sqls.length; i++) {
				helper.database.execSQL(sqls[i]);
				if (n < (int) (((float) i / sqls.length) * 100)) {
					n = (int) (((float) i / sqls.length) * 100);
					Message msg = Message.obtain();
					msg.what = 2;
					msg.arg1 = n;
					msg.obj = "数据包" + index + "写入中...";
					handler.sendMessage(msg);
				}

			}

			helper.database.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 结束事务
			helper.database.endTransaction();
		}

	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		helper.database.close();
	}

	private SQLiteDatabase openDatabase() {

		try {
			// 获得local_database.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			StaticObject.print("打开数据库:" + databaseFilename);
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/Cysgt目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/Cysgt目录中不存在
			// local_database.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/Cysgt）
			if (!(new File(databaseFilename)).exists()) {
				StaticObject.print("数据库不存在，新建数据库" + databaseFilename + context);
				// 获得封装local_database.db文件的InputStream对象
				InputStream is = helper.context.getResources().openRawResource(
						R.raw.local_database);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制local_database.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// 打开/sdcard/Cysgt目录中的local_database.db文件
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 数据库更新（只调用一次 ）
	 */
	public void updateDatabase() {
		String new_ver = SxConfig.read(context, "dbversion");
		String old_ver = getDBVersion();
		if (!new_ver.equals(old_ver)) {
			StaticObject.print("数据库需要更新：" + old_ver + "-->" + new_ver);
			try {
				String databaseFilename = DATABASE_PATH + "/"
						+ DATABASE_FILENAME;
				File dir = new File(DATABASE_PATH);
				if (!dir.exists())
					dir.mkdir();
				InputStream is = helper.context.getResources().openRawResource(
						R.raw.local_database);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制local_database.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				// 数据库
				StaticObject.print("数据库更新完成");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取数据库版本
	 * 
	 * @return
	 */
	private String getDBVersion() {
		String sql = "select database_version from version ";
		Cursor c = helper.Query(sql, null);
		String ver = "";
		if (c.getCount() > 0) {
			c.moveToFirst();
			ver = c.getString(0);
		}
		c.close();
		return ver;
	}
}
