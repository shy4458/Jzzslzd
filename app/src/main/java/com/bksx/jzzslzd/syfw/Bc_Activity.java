package com.bksx.jzzslzd.syfw;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.DataCommon;
import com.bksx.jzzslzd.bo.Ryxx;
import com.bksx.jzzslzd.common.RequestCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.DBData;
import com.bksx.jzzslzd.tools.PicDispose;
import com.bksx.jzzslzd.tools.SelectView;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 补采房屋信息
 * 
 * @author Administrator
 * 
 */
public class Bc_Activity extends Activity {
	private ImageView fwbc_pz;
	private ImageButton fwbc_back;
	private Button fwzx_queding;
	private TextView fwbc_fzName, fwbc_fwAddress, fwbc_syqlx, fwbc_czAction,
			fwbc_syAction, fwbc_ssxq;
	private View linearLayout;
	private String path = Environment.getExternalStorageDirectory().toString()
			+ "/cysgt/"; // 拍照图片地址/
	private String fileName; // 照片名
	private File houseFile;// 房屋补采拍照图片文件
	private PicDispose picDispose = new PicDispose(this);
	private File fwbc_file;// 房屋补采缓存照片
	private String result, result1;
	private String catagory, xingming;
	private String[][] getData;
	private SelectViewAndHandlerAndMsg selectView_czAction;
	private SelectView selectView_syAction, selectView_ssxq;
	private SharedPreferences preferences;
	private String[][] array;
	private Intent intent;
	private Bitmap bitmap;
	private ProgressDialog dialog;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 112:
				if ("06".equals(selectView_czAction.getCodeId())) {
					linearLayout.setVisibility(View.VISIBLE);
				} else {
					linearLayout.setVisibility(View.GONE);
				}
				break;
			case 1:
				StaticObject.showToast(Bc_Activity.this, "上传成功");
				Bc_Activity.this.finish();
				break;
			case 2:
				StaticObject.showToast(Bc_Activity.this, "市级接口连接失败");
				break;
			case 3:
				StaticObject.showToast(Bc_Activity.this, msg.obj.toString());
				break;
			case 404:
				StaticObject.showToast(Bc_Activity.this, "网络连接失败");
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fwhc_fangwubucai_a);
		intent = getIntent();
		result = intent.getStringExtra("result");
		getData = new Gson().fromJson(result, DataCommon.class).getData();
		initObject();

		show();

		/**
		 * 补采拍照
		 */

		fwbc_pz.setOnClickListener(new OnClickListener() {

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
		// 返回
		fwbc_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bc_Activity.this.finish();
			}
		});
		// 确定
		fwzx_queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sendData();
			}

		});

	}

	/**
	 * 上传数据
	 */
	private void sendData() {
		if (fileName == null || "".equals(fileName)) {
			StaticObject.showToast(Bc_Activity.this, "照片不存在,不能提交");
		} else {
			dialog = StaticObject.showDialog(Bc_Activity.this, "数据提交中....");
			new Thread(new Runnable() {
				@Override
				public void run() {
					array = new String[1][12];
					array[0][0] = getData[1][0].trim();
					array[0][1] = selectView_syAction.getCodeId();
					array[0][2] = "";
					array[0][3] = "";
					array[0][4] = catagory;
					array[0][5] = fileName;
					array[0][6] = preferences.getString("login_service_id", "");
					array[0][7] = selectView_ssxq.getCodeId();
					array[0][8] = preferences.getString("login_admin_id", "");
					array[0][9] = preferences.getString("login_number", "");
					array[0][10] = preferences.getString("login_rbac", "");
					array[0][11] = selectView_czAction.getCodeId();
					Ryxx ryxx = new Ryxx();
					ryxx.setData(array);
					Gson gson = new Gson();
					String data = gson.toJson(ryxx);
					result1 = StaticObject.getMessage(Bc_Activity.this,
							RequestCode.FWXXBC, data, path + fileName);
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
	}

	/**
	 * 显示
	 */
	private void show() {
		xingming = intent.getStringExtra("name");
		if (xingming == null || "".equals(xingming)) {
			fwbc_fzName.setText("无");
		} else {
			fwbc_fzName.setText(xingming);
		}
		if (getData[1][1] == null || "".equals(getData[1][1])) {
			fwbc_fwAddress.setText("无");
		} else {
			fwbc_fwAddress.setText(getData[1][1]);
		}
		catagory = intent.getStringExtra("catagory");
		if (catagory == null || "".equals(catagory)) {
			fwbc_syqlx.setText("无");
		} else {
			fwbc_syqlx.setText(DBData.getCatagory(catagory));
		}
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String sql = "select CD_ID,CD_NAME from SJCJ_D_CZYT order by CD_INDEX";
		DBData.getData(sql, map);
		selectView_czAction = new SelectViewAndHandlerAndMsg(this, "出租用途", map,
				fwbc_czAction, handler, 112, "");
		if (getData[1][5] == null || "".equals(getData[1][5])) {
			fwbc_czAction.setText("生活居住");
		} else if ("06".equals(getData[1][5])) {
			selectView_czAction.setCodeId(getData[1][5]);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			selectView_czAction.setCodeId(getData[1][5]);
		}
		LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
		String sql1 = "select CD_ID,CD_NAME from DXYD_D_FWSYCZLX order by CD_INDEX";
		DBData.getData(sql1, map1);
		selectView_syAction = new SelectView(Bc_Activity.this, map1,
				fwbc_syAction);
		if (getData[1][11] == null || "".equals(getData[1][11])) {
			fwbc_syAction.setText("仓储");
		} else {
			selectView_syAction.setCodeId(getData[1][11]);
		}
		preferences = Bc_Activity.this.getSharedPreferences(
				StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
		String[] data1 = preferences.getString("login_govern_id", "")
				.split(",");
		String[] data2 = preferences.getString("login_govern_name", "").split(
				",");
		LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
		for (int i = 0; i < data1.length; i++) {
			map2.put(data1[i], data2[i]);
		}
		selectView_ssxq = new SelectView(Bc_Activity.this, map2, fwbc_ssxq);
		selectView_ssxq.setCodeId(data1[0]);
		if (getData[1][14] == null || "".equals(getData[1][14])) {
			fwbc_pz.setImageBitmap(BitmapFactory.decodeFile(path + fileName));
		} else {
			fwbc_pz.setImageBitmap(BitmapFactory.decodeFile(getData[1][14]));
		}

	}

	/**
	 * 初始化对象
	 */

	private void initObject() {
		fwbc_back = (ImageButton) findViewById(R.id.fwbc_back);
		fwzx_queding = (Button) findViewById(R.id.fwzx_queding);
		fwbc_pz = (ImageView) findViewById(R.id.imageView_fwbc_zpyl);
		fwbc_fzName = (TextView) findViewById(R.id.TextView_fwbc_fzName);
		fwbc_fwAddress = (TextView) findViewById(R.id.TextView_fwbc_fwAddress);
		fwbc_syqlx = (TextView) findViewById(R.id.textView_fwbc_syqlx);
		fwbc_czAction = (TextView) findViewById(R.id.textView_fwbc_czAction);
		fwbc_syAction = (TextView) findViewById(R.id.textView_fwbc_syAction);
		fwbc_ssxq = (TextView) findViewById(R.id.textView_fwbc_ssxq);
		linearLayout = findViewById(R.id.fwbc_syAction_containter);
	}

	/**
	 * 拍照取证后返回
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			try {
				if (houseFile.exists()) {
					bitmap = picDispose.CompressPicFile(houseFile, 1);
					fwbc_pz.setImageBitmap(bitmap);
					fwbc_file = houseFile;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
			houseFile = new File(path, fileName);
			// 删除房屋补采拍照照片
			houseFile.delete();
			houseFile = fwbc_file;
		}
	}

}
