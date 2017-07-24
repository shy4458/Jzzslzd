package com.bksx.jzzslzd.service;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.ReturnDzk;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.SqliteHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UpdateBzdzk {
	private ProgressBar mProgress;
	private TextView textview;
	private AlertDialog dialog;
	private Context c;
	private final String filePath = "/sdcard/sgt";
	private List<ReturnDzk> dzks;
	private Button btn;

	public UpdateBzdzk(Context c, Button btn) {
		this.c = c;
		this.btn = btn;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 9:
				running = false;
				StaticObject.showToast(c, "更新成功");
				btn.setText(getDzkVersion());
				dialog.dismiss();
				break;
			case 0:
				StaticObject.showToast(c, "更新中，请等待...");
				break;
			case 1:
				running = false;
				StaticObject.showToast(c, "地址库更新失败，请重试");
				dialog.dismiss();
				break;
			case 2:
				mProgress.setProgress(msg.arg1);
				textview.setText(msg.obj.toString() + ":\t" + msg.arg1 + "%");

				break;
			case 3:
				StaticObject.showToast(c, "暂无新版本发布，无需更新");
				break;
			case 4:
				StaticObject.showToast(c, "文件下载失败");
				break;
			default:
				break;
			}

		};
	};
	private boolean running = false;

	Runnable runnable = new Runnable() {
		@Override
		public void run() {

			try {
				if (getNewVersion()) {
					if (downLoadFile()) {
						for (int i = 0; i < dzks.size(); i++) {

							ArrayList<String> list = getSqls(i);
							String[] sqls = new String[list.size()];
							list.toArray(sqls);
							sendMsg(100, "数据包" + (i + 1) + "解压完成");
							if (i == 0) {
								sqls = concat(deleteOldData(), sqls);
							}
							SqliteHelper sh = SqliteHelper.getInstance(c);
							sh.execSQL(sqls, handler, i + 1);
							sh.close();
							sendMsg(100, "更新数据包" + (i + 1) + "成功");
						}

						updateDzkVersion(dzks.get(0).getScrq().substring(0, 10));
						handler.sendEmptyMessage(9);
						return;
					} else {
						handler.sendEmptyMessage(4);
					}
				} else {
					handler.sendEmptyMessage(3);
				}

			} catch (Exception e) {
				handler.sendEmptyMessage(1);
				e.printStackTrace();
			}

		}

	};

	private String[] concat(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * 获取最新版本，是否需要更新
	 * 
	 * @return
	 */
	private boolean getNewVersion() {
		// TODO
		sendMsg(10, "获取最新版本中...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendMsg(100, "获取最新版本中完成");
		return true;
	}

	/**
	 * 下载最新版本的文件
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean downLoadFile() throws Exception {


		sendMsg(100, "文件全部下载完成");
		return true;
	}

	private void sendMsg(int arg1, String obj) {
		Message msg = Message.obtain();
		msg.what = 2;
		msg.arg1 = arg1;
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	private void showProgress() {
		AlertDialog.Builder builder = new Builder(c);
		builder.setTitle("地址库版本更新");
		final LayoutInflater inflater = LayoutInflater.from(c);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		textview = (TextView) v.findViewById(R.id.sx_update_progress_text);
		builder.setView(v);
		dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}

	public void onStart(List<ReturnDzk> dzks) {
		this.dzks = dzks;
		if (!running) {
			running = true;
			showProgress();

			new Thread(runnable).start();
		} else {
			handler.sendEmptyMessage(0);
		}
	}

	private String[] deleteOldData() {
		return new String[] { "delete from pcsjlx ", "delete from pcslph ",
				"delete from pcsfjh " };
	}

	public ArrayList<String> getSqls(int i) throws Exception {
		upRarFile(dzks.get(i).getPcsdm() + ".zip");
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(getJlxSqls(filePath + "/jlx" + dzks.get(i).getPcsdm()
				+ ".tsv"));
		sendMsg(20, "数据包" + (i + 1) + "解压中...");
		list.addAll(getLphSqls(filePath + "/lph" + dzks.get(i).getPcsdm()
				+ ".tsv"));
		sendMsg(40, "数据包" + (i + 1) + "解压中...");
		list.addAll(getFjhSqls(filePath + "/fjh" + dzks.get(i).getPcsdm()
				+ ".tsv"));
		sendMsg(90, "数据包" + (i + 1) + "解压中...");
		return list;
	}

	/**
	 * 解压文件
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public boolean upRarFile(String zipName) throws Exception {
		File desDir = new File(filePath);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}
		ZipFile zf = new ZipFile(filePath + File.separator + zipName);
		for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
			ZipEntry entry = ((ZipEntry) entries.nextElement());
			InputStream in = zf.getInputStream(entry);
			String str = filePath + File.separator + entry.getName();
			File desFile = new File(str);
			if (!desFile.exists()) {
				File fileParentDir = desFile.getParentFile();
				if (!fileParentDir.exists()) {
					fileParentDir.mkdirs();
				}
				desFile.createNewFile();
			}
			OutputStream out = new FileOutputStream(desFile);
			byte buffer[] = new byte[1024 * 1024];
			int realLength;
			while ((realLength = in.read(buffer)) > 0) {
				out.write(buffer, 0, realLength);
			}
			in.close();
			out.close();
		}
		return true;
	}

	public ArrayList<String> getJlxSqls(String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "gbk");
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while ((str = br.readLine()) != null) {
			String[] args = str.split("\t");
			list.add("insert into pcsjlx (pcsmc,pcsdm,jlxmc,jlxdm,jlxmcpy) values ('"
					+ args[0]
					+ "','"
					+ args[1]
					+ "','"
					+ args[2]
					+ "','"
					+ args[3] + "','" + args[4] + "')");
		}
		br.close();
		isr.close();
		fis.close();

		return list;
	}

	public ArrayList<String> getLphSqls(String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "gbk");
		BufferedReader br = new BufferedReader(isr);
		String str = null;

		while ((str = br.readLine()) != null) {
			String[] args = str.split("\t");
			list.add("insert into pcslph (jlxdm,lph,lphpy,dzbm,jlxmc) values ('"
					+ args[0]
					+ "','"
					+ args[1]
					+ "','"
					+ args[2]
					+ "','"
					+ args[3] + "','" + args[4] + "')");
		}
		br.close();
		isr.close();
		fis.close();
		return list;
	}

	public ArrayList<String> getFjhSqls(String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "gbk");
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while ((str = br.readLine()) != null) {
			String[] args = str.split("\t");
			list.add("insert into pcsfjh (dzbm,fjh,fjhpy,huid,jlxmc,lph) values ('"
					+ args[0]
					+ "','"
					+ args[1]
					+ "','"
					+ args[2]
					+ "','"
					+ args[3] + "','" + args[4] + "','" + args[5] + "')");
		}
		br.close();
		isr.close();
		fis.close();
		return list;
	}

	/**
	 * 获取地址库版本
	 * 
	 * @return
	 */
	private String getDzkVersion() {
		String sql = "select sys_version from version ";
		SqliteHelper helper = SqliteHelper.getInstance(c);
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
	 * 修改地址库版本
	 * 
	 * @return
	 */
	private void updateDzkVersion(String version_new) {
		String sql = "update   version set sys_version = '" + version_new + "'";
		SqliteHelper helper = SqliteHelper.getInstance(c);
		helper.execSQL(sql, null);
		helper.close();
	}
}
