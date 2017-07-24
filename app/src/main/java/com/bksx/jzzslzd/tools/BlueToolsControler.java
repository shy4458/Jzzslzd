package com.bksx.jzzslzd.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.bksx.jzzslzd.bo.IDCard;
import com.bksx.jzzslzd.common.StaticObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class BlueToolsControler {
	public Handler handler;
	public Context context;
	public ProgressDialog dialog;
	public static BlueToolsControler blueToolsControler;
	/**
	 * 3：关闭dialog
	 */
	public boolean readcard;

	public BlueToolsControler(Context con, Handler handler) {
		this.context = con;
		this.handler = handler;
		readcard = false;
		blueToolsControler = this;
	}



	// 扫描开始时间
	private long startTime;

	// 每隔delayTime毫秒读一次卡，直到超时或读到卡为止
	private final int dTime = 500;
	// 超时时间
	private long DelayTime = 1000 * 10;

	/**
	 * 打开一体机直接扫描设备并进行扫描
	 */
	public void openScanDirect() {

		dialog.dismiss();
		StaticObject.showToast(context, "扫描设备已开启，请放置身份证到扫描区域");
		// 循环读卡
		handler.post(runnable);
		startTime = System.currentTimeMillis();
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// 读身份证，大约需要1s


		}
	};

	private void backData(IDCard card) {

	}

	/**
	 * 将bitmap转换成jpg
	 * 
	 * @param bitmap
	 *            bitmap资源
	 * @param path
	 *            保存后jpg的路径及名字
	 */
	private void bitmapToFile(Bitmap bitmap, String path) {
		File f = new File(path);
		try {
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开扫描设备并进行扫描
	 */
	public void openScan() {
		if (readcard) {
			StaticObject.showToast(context, "请求设备连接中，请稍后..");
		} else {
			readcard = true;
			dialog = StaticObject.showDialog(context, "等待设备就绪...");
			if (android.os.Build.MODEL.startsWith("BM")) { // 区别于黑白版。一体机是BM7500
				// TODO 一体机读卡流程
				openScanDirect();
			} else {

			}
		}
	}

	public void closeDialog() {
		readcard = false;

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

	}
}
