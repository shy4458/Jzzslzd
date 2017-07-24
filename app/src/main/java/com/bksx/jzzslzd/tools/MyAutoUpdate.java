package com.bksx.jzzslzd.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.common.StaticObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 软件更新类
 * 
 * @author Y_Jie
 * 
 */
public class MyAutoUpdate {

	private Context mContext;
	// 提示语
	private String updateMsg = "软件已发布新版本，请更新";

	// 返回的安装包url
	private String loadUrl = "";
	private Dialog noticeDialog;
	private Dialog downloadDialog;
	/* 下载包安装路径 */
	private static final String savePath = "/sdcard/sgt/";

	private static final String saveFileName = savePath + "sgt.apk";

	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private static final int ISNEWVERSION = 0;

	private static final int NEEDUPDATE = -1;

	private int progress;

	private boolean interceptFlag = false;

	private TextView textview;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ISNEWVERSION:
				// 不需要更新
				StaticObject.showToast(mContext, "已经是最新版本");
				break;
			case NEEDUPDATE:
				showNoticeDialog();
				// 需要更新
				break;
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				textview.setText("" + progress + "%");
				break;
			case DOWN_OVER:
				mProgress.setProgress(100);
				textview.setText("" + 100 + "%");
				installApk();
				break;
			case 999:
				// 获取版本失败
				StaticObject.showToast(mContext, "获取版本信息失败");
				break;
			default:
				break;
			}
		};
	};

	public MyAutoUpdate(Context context, String loadUrl) {
		this.mContext = context;
		this.loadUrl = loadUrl;
	}

	public void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
				interceptFlag = false;
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	public void showNoticeDialog(String[] obj) {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = false;
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		textview = (TextView) v.findViewById(R.id.sx_update_progress_text);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				StaticObject.print("软件更新下载：" + loadUrl);
				URL url = new URL(loadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);
				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	/**
	 * 下载apk
	 * 
	 * @param url
	 */

	private void downloadApk() {
		new Thread(mdownApkRunnable).start();
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
