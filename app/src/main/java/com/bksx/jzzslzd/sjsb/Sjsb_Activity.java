package com.bksx.jzzslzd.sjsb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.PhoneSjdj;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.LbxzHelper;
import com.bksx.jzzslzd.tools.PicDispose;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class Sjsb_Activity extends Activity {
	private TextView sjsb_isEnd, tv_sjsb_sjCatagary1, tv_sjsb_sjCatagary2,
			sjsb_suoshuxiaqu;
	private ImageView sjsb_sjPhoto, sjsb_fsAddress;
	private EditText et_sjsb_fsAddress, et_sjsb_sjName, et_sjsb_jtDescription;
	private Button sjsb_record, sjsb_play, sjsb_queding, sjsb_reset;
	private ImageButton sjsb_back;
	private SelectViewAndHandlerAndMsg selectView_isEnd,
			selectView_suoshuxiaqu;
	private String path = "/sdcard/cysgt/cPhoto/"; // 拍照图片地址/
	private String fileName; // 照片名
	private File houseFile;// 房屋补采拍照图片文件
	private PicDispose picDispose = new PicDispose(this);
	private File sjsb_file;// 房屋补采缓存照片
	private Bitmap bitmap;
	protected MediaRecorder mRecorder;
	protected MediaPlayer mPlayer;
	public String mediaPath = "/sdcard/cysgt/media.3gp";
	private long firstTime = 0;

	private ProgressDialog dialog;
	protected String result;
	private SharedPreferences preferences;
	private LbxzHelper sjlb;
	private int flag1 = 0, flag2 = 0;

	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				sjsb_play.setText("播放");
				break;
			case 2:
				StaticObject.showToast(Sjsb_Activity.this, "事件登记成功");
				Sjsb_Activity.this.finish();
				break;
			case 3:
				StaticObject.showToast(Sjsb_Activity.this, "市级接口连接失败");
				break;
			case 4:
				StaticObject.showToast(Sjsb_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Sjsb_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjsb_a);
		initData();
		show();
		record();

		setClick();

	}

	private boolean check() {
		if (isNull(et_sjsb_sjName, "事件名称")) {
			return false;
		}
		if (sjlb != null && "".equals(sjlb.getCodeId())) {
			sjlb.requestFocus();
			StaticObject.showToast(Sjsb_Activity.this, "请选择事件类别");
			return false;
		}
		if (isNull(selectView_suoshuxiaqu, "所属辖区")) {
			return false;
		}
		if (isNull(et_sjsb_fsAddress, "发生地点")) {
			return false;
		}
		if (isNull(et_sjsb_jtDescription, "具体描述")) {
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

	/**
	 * 校验SelectView表单内容不为null和""
	 * 
	 * @param
	 * @param name
	 * @return
	 */
	private boolean isNull(SelectViewAndHandlerAndMsg select, String name) {
		if (select != null && "".equals(select.getCodeId())) {
			select.requestFocus();
			StaticObject.showToast(this, "请选择" + name);
			return true;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		flag1 = 0;
		flag2 = 0;
	}

	private void setClick() {
		sjsb_queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (check()) {
					sendData();
				}
			}
		});
		sjsb_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Sjsb_Activity.this.finish();
				flag1 = 0;
				flag2 = 0;
			}
		});
		sjsb_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_sjsb_sjName.setText("");
				sjlb.setCodeId("");
				et_sjsb_fsAddress.setText("");
				et_sjsb_jtDescription.setText("");
				selectView_isEnd.setCodeId("");
				sjsb_sjPhoto.setImageBitmap(null);
				sjsb_record.setText("点击开始录音");
				sjsb_play.setVisibility(View.GONE);
				selectView_isEnd.setCodeId("0");
				flag1 = 0;
				flag2 = 0;
			}
		});

	}

	/**
	 * 上传数据
	 */
	private void sendData() {

		dialog = StaticObject.showDialog(Sjsb_Activity.this, "数据提交中....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				PhoneSjdj sjdj = new PhoneSjdj();
				sjdj.setSsxzqh(selectView_suoshuxiaqu.getCodeId());
				sjdj.setFxr(preferences.getString("login_number", ""));
				sjdj.setSjmc(et_sjsb_sjName.getText().toString().trim());
				sjdj.setSjlb(sjlb.getCodeId());
				sjdj.setFsdd(et_sjsb_fsAddress.getText().toString().trim());
				sjdj.setJtms(et_sjsb_jtDescription.getText().toString().trim());
				if (flag1 == 1) {
					sjdj.setSfyzp("1");
				} else {
					sjdj.setSfyzp("0");
				}
				if (flag2 == 1) {
					sjdj.setSfyly("1");
				} else {
					sjdj.setSfyly("0");
				}
				sjdj.setSjly("1");
				sjdj.setSfczwj(selectView_isEnd.getCodeId());
				sjdj.setDqzt("0");
				sjdj.setBz("");
				Gson gson = new Gson();
				String data = gson.toJson(sjdj);
				if ("0".equals(sjdj.getSfyzp())) {
					if ("0".equals(sjdj.getSfyly())) {
						result = StaticObject.getMessage(Sjsb_Activity.this,
								RequestCode.SJDJ, data);
					} else {
						result = StaticObject.getMessage(Sjsb_Activity.this,
								RequestCode.SJDJ, data, mediaPath);
					}
				} else {
					if ("0".equals(sjdj.getSfyly())) {
						result = StaticObject.getMessage(Sjsb_Activity.this,
								RequestCode.SJDJ, data, path + fileName);
					} else {
						result = StaticObject.getMessage(Sjsb_Activity.this,
								RequestCode.SJDJ, data, path + fileName,
								mediaPath);

					}

				}
				if (RequestCode.CSTR.equals(result)) {
					return;
				}
				dialog.dismiss();
				if ("".equals(result)) {
					Message message = new Message();// 创建消息
					message.what = 404;// 设置消息的what值
					handler.sendMessage(message);// 发送消息
				} else {
					DataCommon dataCommon = gson.fromJson(result,
							DataCommon.class);
					if ("09".equals(dataCommon.getZtbs())) {
						Message message2 = new Message();
						message2.what = 2;
						handler.sendMessage(message2);// 发送消息
					} else if ("18".equals(dataCommon.getZtbs())) {
						Message message3 = new Message();// 创建消息
						message3.what = 3;// 设置消息的what值
						handler.sendMessage(message3);// 发送消息
					} else {
						Message message4 = new Message();// 创建消息
						message4.what = 4;// 设置消息的what值
						message4.obj = dataCommon.getZtxx();
						handler.sendMessage(message4);// 发送消息
					}
				}

			}
		}).start();
	}

	private void record() {
		sjsb_record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long secondTime = System.currentTimeMillis();
				long t = secondTime - firstTime;
				System.out.println("=======" + t);
				firstTime = secondTime;
				if (t < 1000) {
					StaticObject.showToast(Sjsb_Activity.this, "点击太快！！");
				} else {
					if (sjsb_record.getText().equals("点击开始录音")
							|| sjsb_record.getText().equals("点击重新录音")) {
						sjsb_record.setText("点击停止");
						startRecording();
					} else {
						sjsb_record.setText("点击重新录音");
						sjsb_play.setVisibility(View.VISIBLE);
						stopRecording();
						flag2 = 1;

					}

				}
			}
		});

		sjsb_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sjsb_play.getText().equals("播放")) {
					startPlaying();
					sjsb_play.setText("停止");
					mPlayer = new MediaPlayer();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							sjsb_play.setText("播放");
						}
					});

				} else {
					sjsb_play.setText("播放");
					stopPlaying();
				}
			}
		});
	}

	public void startRecording() {

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				mRecorder = new MediaRecorder();
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.setOutputFile(mediaPath);
				try {
					mRecorder.prepare();
					mRecorder.start();

				} catch (IOException e) {
					e.printStackTrace();
				}
				Looper.loop();
			}

		}.start();

	}

	public void stopRecording() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				System.out.println("准备结束录音。。。");
				if (mRecorder != null) {
					mRecorder.stop();
					mRecorder.release();
					mRecorder = null;
					System.out.println("结束录音。。。");
				}

				Looper.loop();
			}

		}.start();
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
					mPlayer.setDataSource(mediaPath);
					mPlayer.prepare();
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							Message message = new Message();// 创建消息
							message.what = 1;// 设置消息的what值
							handler.sendMessage(message);// 发送消息
						}
					});
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Looper.loop();
			}
		}.start();
	}

	/**
	 * 播放停止
	 */
	public void stopPlaying() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				if (mPlayer != null) {
					mPlayer.release();
					mPlayer = null;
					Looper.loop();
				}
			}
		}.start();
	}

	private void show() {
		String[] data1, data2;
		data1 = preferences.getString("login_govern_id", "").split(",");
		data2 = preferences.getString("login_govern_name", "").split(",");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < data1.length; i++) {
			map.put(data1[i], data2[i]);
		}
		selectView_suoshuxiaqu = new SelectViewAndHandlerAndMsg(
				Sjsb_Activity.this, "所属辖区", map, sjsb_suoshuxiaqu, handler, 0,
				data1[0]);
		sjlb = new LbxzHelper(this, tv_sjsb_sjCatagary1, tv_sjsb_sjCatagary2);
		LinkedHashMap<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("0", "否");
		map3.put("1", "是");
		selectView_isEnd = new SelectViewAndHandlerAndMsg(Sjsb_Activity.this,
				"是否完结", map3, sjsb_isEnd, handler, 0, "0");

		/**
		 * 补采拍照
		 */

		sjsb_sjPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				houseFile = new File(path);
				if (houseFile.exists()) {
					if (houseFile.isFile()) {
						houseFile.delete();
					} else if (houseFile.isDirectory()) {
						File[] files = houseFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							files[i].delete();
						}
					}
				} else {
					houseFile.mkdirs();
				}
				// 照片名
				fileName = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date().getTime()) + ".jpg";

				houseFile = new File(path, fileName);

				// 调用系统照相机
				Uri outputFileUri = Uri.fromFile(houseFile);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				startActivityForResult(intent, 1);

			}
		});
		/**
		 * 定位
		 */
		sjsb_fsAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			try {
				if (houseFile.exists()) {
					bitmap = picDispose.CompressPicFile(houseFile, 1);
					sjsb_sjPhoto.setImageBitmap(bitmap);
					sjsb_file = houseFile;
					flag1 = 1;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
			houseFile = new File(path, fileName);
			// 删除房屋补采拍照照片
			houseFile.delete();
			houseFile = sjsb_file;
		}
	}

	private void initData() {
		preferences = Sjsb_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		sjsb_suoshuxiaqu = (TextView) findViewById(R.id.textView_sjsb_suoshuxiaqu);
		sjsb_isEnd = (TextView) findViewById(R.id.textView_sjsb_isEnd);
		tv_sjsb_sjCatagary1 = (TextView) findViewById(R.id.tv_sjsb_sjCatagary1);
		tv_sjsb_sjCatagary2 = (TextView) findViewById(R.id.tv_sjsb_sjCatagary2);
		sjsb_record = (Button) findViewById(R.id.textView_sjsb_record);
		sjsb_play = (Button) findViewById(R.id.textView_sjsb_play);
		sjsb_queding = (Button) findViewById(R.id.sjsb_queding);
		sjsb_reset = (Button) findViewById(R.id.sjsb_reset);
		sjsb_back = (ImageButton) findViewById(R.id.sjsb_back);
		sjsb_sjPhoto = (ImageView) findViewById(R.id.imageView_sjsb_sjPhoto);
		sjsb_fsAddress = (ImageView) findViewById(R.id.imageView_sjsb_fsAddress);
		et_sjsb_fsAddress = (EditText) findViewById(R.id.editText_sjsb_fsAddress);
		et_sjsb_sjName = (EditText) findViewById(R.id.editText_sjsb_sjName);
		et_sjsb_jtDescription = (EditText) findViewById(R.id.editText_sjsb_jtDescription);

	}

}
