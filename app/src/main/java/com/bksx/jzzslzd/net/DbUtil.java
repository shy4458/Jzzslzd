package com.bksx.jzzslzd.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.bksx.jzzslzd.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 工具类，需要上下文context
 * 
 * @author 姬昇君 开始日期：2013-10-10
 */
public class DbUtil {
	private Context context;
	@SuppressLint("SdCardPath")
	private static final String DATABASE_PATH = "/data/data/com/bksx/cysgt/databases";
	private static final String DATABASE_NAME = "local_database.db";
	private static String outFileName = DATABASE_PATH + "/" + DATABASE_NAME;
	private SQLiteDatabase dataBase;

	public DbUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获取数据库对象
	 * 
	 * @return SQliteDatabase对象
	 */
	public SQLiteDatabase openDataBase() {

		File file = new File(outFileName);
		if (file.exists()) {
			dataBase = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
			return dataBase;
		} else {
			try {
				buildDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataBase = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
			Log.i("info", "open  database ~!");
			return dataBase;
		}
	}

	private void buildDatabase() throws Exception {
		InputStream myInput = context.getResources().openRawResource(
				R.raw.local_database);
		File file = new File(outFileName);

		File dir = new File(DATABASE_PATH);
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				throw new Exception("创建失败");
			}
		}
		if (!file.exists()) {
			try {
				OutputStream myOutput = new FileOutputStream(outFileName);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}
				myOutput.close();
				myInput.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/***
	 * 
	 * @param control
	 *            接口id
	 * @param data
	 *            要发送的数据
	 * @return 结果为json字符串
	 */
	public synchronized String getMassage(final String control,
			final String data) {

		String result = "";
		Log.i("msg", "cid:" + control);
		Log.i("msg", "data:" + data);
		HttpRequest hr = new HttpRequest();
		result = hr.sendString(context, control, "", data);

		if (result == null)
			result = "";
		Log.i("msg", "result:" + result);
		return result;
	}

	/*
	 * 向网关发送信息，接收返回的数据信息 ---- 通用方法的 主要是根据条件查询相关信息 control ：需要目标接口执行的业务状态值
	 * data：传输给目标接口的数据
	 */
	public synchronized String getMassage(final String control,
			final String[] fileName, final String data) {
		Log.i("msg", "cid:" + control);
		Log.i("msg", "data:" + data);
		Log.i("msg", "fileName:" + fileName[0]);
		HttpRequest hr = new HttpRequest();
		String result = hr.sendFile(context, control, "", fileName, data);
		Log.i("msg", "result:" + result);
		if (result == null)
			result = "";
		return result;
	}

	/**
	 * 图片下载回显
	 * 
	 * @param url
	 *            下载地址
	 * @param script
	 *            下载完成后执行的script方法名
	 * @return 下载到手机上的地址
	 */
	public synchronized String getPhotoPathByUrl(final String url,
			final String script) {
		final String u = url.replaceAll("\\\\", "/");
		String ns = u.substring(u.lastIndexOf("/") + 1);
		final String path_zc = Environment.getExternalStorageDirectory()
				.getPath() + "camera" + ns;

		new Thread() {
			public void run() {
				Looper.prepare();
				File f = new File(path_zc);
				if (!f.exists()) {
					try {
						InputStream is = new URL(u).openStream();
						FileOutputStream fo = new FileOutputStream(f);
						byte[] bs = new byte[1024];
						int l = 0;
						while ((l = is.read(bs)) != -1) {
							fo.write(bs, 0, l);
						}
						is.close();
						fo.flush();
						fo.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Looper.loop();
			};
		}.start();
		return path_zc;

	}

}
