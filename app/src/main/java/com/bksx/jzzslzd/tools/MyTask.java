package com.bksx.jzzslzd.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bksx.jzzslzd.common.StaticObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class MyTask extends AsyncTask<String, Void, byte[]> {
	private Context context;
	private ImageView imageView;
	private BufferedInputStream bis = null;
	private ByteArrayOutputStream baos = null;

	public MyTask(Context context, ImageView imageView) {
		this.context = context;
		this.imageView = imageView;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected byte[] doInBackground(String... params) {
		baos = new ByteArrayOutputStream();
		try {
			URL url = new URL(params[0]);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			if (httpConn.getResponseCode() == 200) {
				bis = new BufferedInputStream(httpConn.getInputStream());
				byte[] buffer = new byte[1024 * 10];
				int c = 0;
				while ((c = bis.read(buffer)) != -1) {
					baos.write(buffer, 0, c);
					baos.flush();
				}
				return baos.toByteArray();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(byte[] result) {
		super.onPostExecute(result);
		if (result == null) {
			StaticObject.showToast(context, "照片加载失败");
		} else {
			Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
					result.length);
			// 将bitmap压缩
			Bitmap compbip = Bitmap.createScaledBitmap(bitmap,
					bitmap.getWidth() / 4, bitmap.getHeight() / 6, true);
			compbip.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			imageView.setImageBitmap(compbip);

		}
	}

}
