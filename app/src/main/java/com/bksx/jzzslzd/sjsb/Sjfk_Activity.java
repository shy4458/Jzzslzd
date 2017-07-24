package com.bksx.jzzslzd.sjsb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.List1;
import com.bksx.jzzslzd.bo.PhoneSjfk;
import com.bksx.jzzslzd.bo.SjxxcyBean;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.MyTask;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Sjfk_Activity extends Activity {
	private LinearLayout ll2, ll_zhaopian, ll_luyin, ll_fknr, ll_sfwj;
	private String result, sjbh, result1;
	private ProgressDialog dialog;
	private TextView sjfk_sjmc, sjfk_sjlb, sjfk_fsdd, sjfk_jtms,
			sjfk_dangqianchuzhixinxi, sjfk_tv_lishichuzhixinxi;
	private EditText sjfk_fknr;
	private RadioButton shi, fou;
	private ImageView sjfk_img_photo;
	private ImageButton sjfk_back;
	private Button sjfk_baocun, sjfk_fanhui, sjfk_luyin;
	private ArrayList<List1> data;
	private SharedPreferences preferences;
	private BufferedInputStream bis = null;
	protected MediaPlayer mPlayer;
	private final String FilePath = "/sdcard/cysgt/media/";
	private String fileName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjfk_a);
		findView();
		Intent intent = getIntent();
		result = intent.getStringExtra("result1");
		sjbh = intent.getStringExtra("sjbh");
		Gson gson = new Gson();
		SjxxcyBean sjxxcy = gson.fromJson(result, SjxxcyBean.class);
		data = sjxxcy.getList();
		show();
		add();

		sjfk_baocun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (check()) {
					sendData();

				}
			}
		});
		sjfk_fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Sjfk_Activity.this.finish();
			}
		});
		sjfk_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Sjfk_Activity.this.finish();
			}
		});

	}

	private boolean check() {
		if (isNull(sjfk_fknr, "反馈内容")) {
			return false;
		}
		return true;

	}

	/**
	 * 校验EditText表单内容不为null和""
	 * 
	 * @param view
	 * @param name
	 * @return
	 */
	private boolean isNull(EditText view, String name) {
		if (view != null && "".equals(view.getText().toString().trim())) {
			view.requestFocus();
			InputMethodManager imm = (InputMethodManager) view.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
			StaticObject.showToast(this, "请填写" + name);
			return true;
		}
		return false;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(final Message msg) {
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
			case 1:
				StaticObject.showToast(Sjfk_Activity.this, "上传成功");
				Sjfk_Activity.this.setResult(601);
				Sjfk_Activity.this.finish();
				break;
			case 2:
				StaticObject.showToast(Sjfk_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Sjfk_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Sjfk_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
		};
	};

	private void sendData() {

		dialog = StaticObject.showDialog(Sjfk_Activity.this, "数据提交中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				PhoneSjfk sjfk = new PhoneSjfk();
				sjfk.setCzdwxzqh(preferences.getString("login_number", ""));
				sjfk.setCzzt("3");
				sjfk.setJsdw(data.get(0).getXzqh());
				sjfk.setSjbh(sjbh);
				sjfk.setPsfknr(sjfk_fknr.getText().toString().trim());
				if ("0".equals(getSfwj())) {
					sjfk.setSfczwj("0");
				}
				if ("1".equals(getSfwj())) {
					sjfk.setSfczwj("1");
				}
				Gson gson = new Gson();
				String data = gson.toJson(sjfk);
				result1 = StaticObject.getMessage(Sjfk_Activity.this,
						RequestCode.SJFK, data);
				dialog.dismiss();
				if (RequestCode.CSTR.equals(result1)) {
					return;
				}
				if ("".equals(result1)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					DataCommon dataCommon1 = gson.fromJson(result1,
							DataCommon.class);

					if ("09".equals(dataCommon1.getZtbs())) {
						Message message1 = new Message();
						message1.what = 1;
						handler.sendMessage(message1);// 发送消息
					} else if ("18".equals(dataCommon1.getZtbs())) {
						Message message2 = new Message();// 创建消息
						message2.what = 2;// 设置消息的what值
						handler.sendMessage(message2);// 发送消息
					} else {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						message3.obj = dataCommon1.getZtxx();
						handler.sendMessage(message3);// 发送消息
					}
				}

			}
		}).start();

	}

	/**
	 * 获取是否完结
	 */
	private String getSfwj() {
		String sfwj = "";
		int i = 0;
		RadioButton[] radioButtons = new RadioButton[] { fou, shi };
		if (radioButtons[i].isChecked()) {
			sfwj = i + "";
		} else {
			sfwj = (i + 1) + "";
		}
		return sfwj;
	}

	private void show() {
		sjfk_sjmc.setText(data.get(0).getSjmc());
		sjfk_sjlb.setText(data.get(0).getSjlb());
		sjfk_fsdd.setText(data.get(0).getFsdd());
		sjfk_jtms.setText(data.get(0).getJtms());
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
					StaticObject.showToast(Sjfk_Activity.this, "录音加载失败");
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
					if (!data.get(data.size() - 1).getJsdw()
							.equals(preferences.getString("login_number", ""))) {
						ll_fknr.setVisibility(View.GONE);
						ll_sfwj.setVisibility(View.GONE);
						sjfk_dangqianchuzhixinxi.setText("事件正在等待处理.....");

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
		preferences = Sjfk_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		ll2 = (LinearLayout) findViewById(R.id.sjfk_ll2);
		ll_zhaopian = (LinearLayout) findViewById(R.id.ll_zhaopian);
		ll_luyin = (LinearLayout) findViewById(R.id.ll_luyin);
		ll_fknr = (LinearLayout) findViewById(R.id.ll_fknr);
		ll_sfwj = (LinearLayout) findViewById(R.id.ll_sfwj);
		ll2 = (LinearLayout) findViewById(R.id.sjfk_ll2);
		sjfk_sjmc = (TextView) findViewById(R.id.sjfk_sjmc);
		sjfk_sjlb = (TextView) findViewById(R.id.sjfk_sjlb);
		sjfk_fsdd = (TextView) findViewById(R.id.sjfk_fsdd);
		sjfk_jtms = (TextView) findViewById(R.id.sjfk_jtms);
		sjfk_dangqianchuzhixinxi = (TextView) findViewById(R.id.sjfk_dangqianchuzhixinxi);
		sjfk_tv_lishichuzhixinxi = (TextView) findViewById(R.id.sjfk_tv_lishichuzhixinxi);
		sjfk_fknr = (EditText) findViewById(R.id.sjfk_fknr);
		shi = (RadioButton) findViewById(R.id.shi);
		fou = (RadioButton) findViewById(R.id.fou);
		sjfk_baocun = (Button) findViewById(R.id.sjfk_baocun);
		sjfk_fanhui = (Button) findViewById(R.id.sjfk_fanhui);
		sjfk_back = (ImageButton) findViewById(R.id.sjfk_back);
		sjfk_img_photo = (ImageView) findViewById(R.id.sjfk_img_photo);
		sjfk_luyin = (Button) findViewById(R.id.sjfk_luyin);

	}

}
