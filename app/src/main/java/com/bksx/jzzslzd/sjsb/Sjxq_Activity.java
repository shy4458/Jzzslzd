package com.bksx.jzzslzd.sjsb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.List1;
import com.bksx.jzzslzd.bo.SjxxcyBean;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.MyTask;
import com.google.gson.Gson;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sjxq_Activity extends Activity {
	private LinearLayout ll2, ll_zhaopian, ll_luyin;
	private String result;
	private TextView sjfk_sjmc, sjfk_sjlb, sjfk_fsdd, sjfk_jtms,
			sjfk_shifouwanjie, sjfk_tv_lishichuzhixinxi;
	private ImageView sjfk_img_photo;
	private ImageButton sjfk_back;
	private Button sjfk_queding, sjfk_luyin;
	private ArrayList<List1> data;
	private BufferedInputStream bis = null;
	protected MediaPlayer mPlayer;

	private final String FilePath = "/sdcard/cysgt/media/";
	private String fileName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjxq_a);
		findView();
		Intent intent = getIntent();
		result = intent.getStringExtra("result1");
		Gson gson = new Gson();
		SjxxcyBean sjxxcy = gson.fromJson(result, SjxxcyBean.class);
		data = sjxxcy.getList();
		show();
		add();

		sjfk_queding.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Sjxq_Activity.this.finish();
			}
		});
		sjfk_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Sjxq_Activity.this.finish();
			}
		});

	}

	private void show() {
		sjfk_sjmc.setText(data.get(0).getSjmc());
		sjfk_sjlb.setText(data.get(0).getSjlb());
		sjfk_fsdd.setText(data.get(0).getFsdd());
		sjfk_jtms.setText(data.get(0).getJtms());
		if ("1".equals(data.get(0).getSfczwj())) {
			sjfk_shifouwanjie.setText("是");
		} else {
			sjfk_shifouwanjie.setText("否");

		}
		if ("1".equals(data.get(0).getSfyzp())) {
			String zpdz = data.get(0).getZpdz().replace("\\", "/");
			if (zpdz == null || "".equals(zpdz)) {
				ll_zhaopian.setVisibility(View.VISIBLE);
			} else {
				ll_zhaopian.setVisibility(View.VISIBLE);
				new MyTask(this, sjfk_img_photo).execute(zpdz);
			}
		}
		if ("1".equals(data.get(0).getSfyly())) {
			String lydz = data.get(0).getLydz().replace("\\", "/");
			if (lydz == null || "".equals(lydz)) {
				ll_luyin.setVisibility(View.VISIBLE);
			} else {
				ll_luyin.setVisibility(View.VISIBLE);
				sjfk_luyin.setText("下载中...");
				Download(lydz);
			}

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(final android.os.Message msg) {
			switch (msg.what) {
			case 0:
				sjfk_luyin.setText("重新播放");
				sjfk_luyin.setClickable(true);
				break;
			case 11:
				sjfk_luyin.setText("播放");
				sjfk_luyin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						sjfk_luyin.setText("播放中...");
						sjfk_luyin.setClickable(false);
						startPlaying();
					}
				});
				break;
			case 12:
				sjfk_luyin.setText("重新加载");
				sjfk_luyin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						sjfk_luyin.setText("加载中...");
						sjfk_luyin.setClickable(false);
						Download((String) msg.obj);
					}
				});
				break;

			default:
				break;
			}

		};
	};

	/**
	 * 开始播放
	 */
	public void startPlaying() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				mPlayer = new MediaPlayer();
				try {
					mPlayer.setDataSource(FilePath + fileName);
					mPlayer.prepare();
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							handler.sendEmptyMessage(0);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}

				Looper.loop();
			}
		}.start();
	}

	private void Download(final String lydz) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					String[] lydzs = lydz.split("/");
					fileName = lydzs[lydzs.length - 1]; // 获取文件名字，如果存在，直接加载就别下载了。没有再下载
					File filed = new File(FilePath);
					if (!filed.exists()) {
						filed.mkdirs();
					}
					File file = new File(FilePath + fileName);
					if (!file.exists()) {

						URL url = new URL(lydz);
						HttpURLConnection httpconn = (HttpURLConnection) url
								.openConnection();
						if (httpconn.getResponseCode() == 200) {
							bis = new BufferedInputStream(httpconn
									.getInputStream());
							BufferedOutputStream bos = new BufferedOutputStream(
									new FileOutputStream(FilePath + fileName));

							byte[] buffer = new byte[1024];
							int c = 0;
							while ((c = bis.read(buffer)) != -1) {
								bos.write(buffer, 0, c);
								bos.flush();
							}
						}
					}
					if (!file.exists()) {
						Message msg = Message.obtain();
						msg.obj = lydz;
						msg.what = 12;
						handler.sendMessage(msg);
					} else {
						handler.sendEmptyMessage(11);
					}
				} catch (Exception e) {
					e.printStackTrace();
					StaticObject.showToast(Sjxq_Activity.this, "录音加载失败");
				}
				Looper.loop();
			}
		}).start();
	}

	private void add() {
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				View view = LayoutInflater.from(this).inflate(R.layout.lsczxx,
						null);
				LinearLayout ll_jieshoudanwei = (LinearLayout) view
						.findViewById(R.id.ll_jieshoudanwei);
				LinearLayout ll_chuzhineirong = (LinearLayout) view
						.findViewById(R.id.ll_chuzhineirong);
				TextView sjfk_chuzhidanwei = (TextView) view
						.findViewById(R.id.sjfk_chuzhidanwei);
				TextView sjfk_chuzhiriqi = (TextView) view
						.findViewById(R.id.sjfk_chuzhiriqi);
				TextView sjfk_chuzhifangshi = (TextView) view
						.findViewById(R.id.sjfk_chuzhifangshi);
				TextView sjfk_jieshoudanwei = (TextView) view
						.findViewById(R.id.sjfk_jieshoudanwei);
				TextView sjfk_chuzhineirong = (TextView) view
						.findViewById(R.id.sjfk_chuzhineirong);
				TextView sjfk_tv_chuzhineirong = (TextView) view
						.findViewById(R.id.sjfk_tv_chuzhineirong);
				if (i == data.size() - 1) {
					view.findViewById(R.id.ls_xhx).setVisibility(View.GONE);
				}
				ll2.addView(view);
				if (data.get(i).getXzqhmc() == null
						|| "".equals(data.get(i).getXzqhmc())) {
					sjfk_chuzhidanwei.setText("无");
				} else {
					sjfk_chuzhidanwei.setText(data.get(i).getXzqhmc());
				}
				String date = data.get(i).getCzrq();
				if (date == null || "".equals(date)) {
					sjfk_chuzhiriqi.setText("无");
				} else {
					sjfk_chuzhiriqi
							.setText(date.substring(0, 4) + "-"
									+ date.substring(4, 6) + "-"
									+ date.substring(6, 8));
				}
				if (data.get(i).getCzzt() == null
						|| "".equals(data.get(i).getCzzt())) {
					sjfk_chuzhifangshi.setText("无");
				} else {
					sjfk_chuzhifangshi.setText(data.get(i).getCzzt());
				}
				if (!"4".equals(data.get(i).getDqzt())) {
					ll_jieshoudanwei.setVisibility(View.VISIBLE);
					if (data.get(i).getJsdw() == null
							|| "".equals(data.get(i).getJsdw())) {
						sjfk_jieshoudanwei.setText("无");
					} else {
						sjfk_jieshoudanwei.setText(data.get(i).getJsdw());
					}
				}
				if (!"1".equals(data.get(i).getDqzt())) {
					if ("3".equals(data.get(i).getDqzt())) {
						ll_chuzhineirong.setVisibility(View.VISIBLE);
						sjfk_tv_chuzhineirong.setText("反馈内容:");
						if (data.get(i).getPsfknr() == null
								|| "".equals(data.get(i).getPsfknr())) {
							sjfk_chuzhineirong.setText("无");
						} else {
							sjfk_chuzhineirong.setText(data.get(i).getPsfknr());
						}
					} else if ("4".equals(data.get(i).getDqzt())) {
						ll_chuzhineirong.setVisibility(View.VISIBLE);
						sjfk_tv_chuzhineirong.setText("处置内容:");
						if (data.get(i).getPsfknr() == null
								|| "".equals(data.get(i).getPsfknr())) {
							sjfk_chuzhineirong.setText("无");
						} else {
							sjfk_chuzhineirong.setText(data.get(i).getPsfknr());
						}
					} else if ("5".equals(data.get(i).getDqzt())) {
						ll_chuzhineirong.setVisibility(View.VISIBLE);
						sjfk_tv_chuzhineirong.setText("批示内容:");
						if (data.get(i).getPsfknr() == null
								|| "".equals(data.get(i).getPsfknr())) {
							sjfk_chuzhineirong.setText("无");
						} else {
							sjfk_chuzhineirong.setText(data.get(i).getPsfknr());
						}
					} else if ("2".equals(data.get(i).getDqzt())) {
						ll_chuzhineirong.setVisibility(View.VISIBLE);
						sjfk_tv_chuzhineirong.setText("上报原因:");
						if (data.get(i).getPsfknr() == null
								|| "".equals(data.get(i).getPsfknr())) {
							sjfk_chuzhineirong.setText("无");
						} else {
							sjfk_chuzhineirong.setText(data.get(i).getPsfknr());
						}
					} else if ("0".equals(data.get(i).getDqzt())) {
						ll_chuzhineirong.setVisibility(View.VISIBLE);
						sjfk_tv_chuzhineirong.setText("登记原因:");
						if (data.get(i).getPsfknr() == null
								|| "".equals(data.get(i).getPsfknr())) {
							sjfk_chuzhineirong.setText("无");
						} else {
							sjfk_chuzhineirong.setText(data.get(i).getPsfknr());
						}

					}
				}
			}

		} else {
			sjfk_tv_lishichuzhixinxi.setText("无符合查询条件的记录");
		}
	}

	private void findView() {
		ll2 = (LinearLayout) findViewById(R.id.sjfk_ll2);
		ll_zhaopian = (LinearLayout) findViewById(R.id.ll_zhaopian);
		ll_luyin = (LinearLayout) findViewById(R.id.ll_luyin);
		ll2 = (LinearLayout) findViewById(R.id.sjfk_ll2);
		sjfk_sjmc = (TextView) findViewById(R.id.sjfk_sjmc);
		sjfk_sjlb = (TextView) findViewById(R.id.sjfk_sjlb);
		sjfk_fsdd = (TextView) findViewById(R.id.sjfk_fsdd);
		sjfk_jtms = (TextView) findViewById(R.id.sjfk_jtms);
		sjfk_shifouwanjie = (TextView) findViewById(R.id.sjfk_shifouwanjie);
		sjfk_tv_lishichuzhixinxi = (TextView) findViewById(R.id.sjfk_tv_lishichuzhixinxi);
		sjfk_queding = (Button) findViewById(R.id.sjfk_queding);
		sjfk_back = (ImageButton) findViewById(R.id.sjfk_back);
		sjfk_img_photo = (ImageView) findViewById(R.id.sjfk_img_photo);
		sjfk_luyin = (Button) findViewById(R.id.sjfk_luyin);

	}

}
