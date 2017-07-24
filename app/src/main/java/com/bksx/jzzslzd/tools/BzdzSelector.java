package com.bksx.jzzslzd.tools;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.FjhVo;
import com.bksx.jzzslzd.bo.JlxVo;
import com.bksx.jzzslzd.bo.LphVo;

import java.util.LinkedHashMap;

/**
 * 标准地址选择器
 * 
 * @author Y_Jie
 * 
 */
public class BzdzSelector {
	private Context context;
	private AutoCompleteTextView auto;
	private EditText et;
	private int dzjb;// 地址级别
	private LinkedHashMap<String, JlxVo> jlx;
	private LinkedHashMap<String, LphVo> lph;
	private LinkedHashMap<String, FjhVo> fjh;

	private int textViewResourceId;

	String[] dqdz = new String[3];

	String[] cary = null;
	ArrayAdapter<String> adapter;

	public BzdzSelector(Context c, final AutoCompleteTextView auto,
			final EditText et, boolean a) {
		this.context = c;
		this.auto = auto;
		this.et = et;

		et.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				handler.sendEmptyMessage(0);
			}
		});

		this.auto.setDropDownHeight(LayoutParams.WRAP_CONTENT);
		this.auto.setThreshold(1);
		textViewResourceId = R.layout.mydropdownitem;
		init();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			et.clearFocus();
			auto.requestFocus();
			auto.showDropDown();
		};
	};

	private void init() {

		initJlx();

		auto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				auto.showDropDown();
			}
		});
		auto.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg, View arg1,
					int position, long arg3) {
				switch (dzjb) {
				case 1:
					dqdz[0] = cary[position].substring(1);
					initLph();
					et.setText(dqdz[0]);
					handler.sendEmptyMessage(0);
					break;
				case 2:
					dqdz[1] = cary[position].substring(1);
					initFjh();
					et.setText(dqdz[0] + dqdz[1]);
					handler.sendEmptyMessage(0);
					break;
				case 3:
					dqdz[2] = cary[position].substring(1);
					dzjb = 4;
					et.setText(dqdz[0] + dqdz[1] + dqdz[2]);
					et.requestFocus();
					et.setSelection((dqdz[0] + dqdz[1] + dqdz[2]).length());
					break;
				default:
					break;
				}

			}

		});
		TextWatcher tw = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 手动输入的不管，删除的话就管
				if (before == 1 && count == 0) {// 删除按钮
					auto.setText("");
					switch (dzjb) {
					case 4:
						s = dqdz[0] + dqdz[1];
						dzjb = 3;
						dqdz[2] = "";
						initFjh();
						break;
					case 3:
						s = dqdz[0];
						initLph();
						dqdz[1] = "";
						break;
					case 2:
						s = " ";
						initJlx();
						dqdz[0] = "";
						break;
					}
					et.setText(s);
					et.setSelection(s.length());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
		et.addTextChangedListener(tw);
		auto.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_DEL) {
					String s = "";
					auto.setText("");
					switch (dzjb) {
					case 4:
						s = dqdz[0] + dqdz[1];
						dzjb = 3;
						dqdz[2] = "";
						initFjh();
						break;
					case 3:
						s = dqdz[0];
						initLph();
						dqdz[1] = "";
						break;
					case 2:
						s = " ";
						initJlx();
						dqdz[0] = "";
						break;
					}
					et.setText(s);
					et.setSelection(s.length());
				}
				return false;
			}
		});

	}

	private void initJlx() {
		this.dzjb = 1;
		getJlx();
		if (jlx.size() == 0) {
			return;
		}
		cary = new String[jlx.size()];
		jlx.keySet().toArray(cary);
		for (int i = 0; i < cary.length; i++) {
			cary[i] = " " + cary[i];
		}
		adapter = new ArrayAdapter<String>(context, textViewResourceId, cary);
		auto.setAdapter(adapter);
	}

	private void initLph() {
		dzjb = 2;
		getLph(dqdz[0]);
		cary = new String[lph.size()];
		lph.keySet().toArray(cary);
		for (int i = 0; i < cary.length; i++) {
			cary[i] = " " + cary[i];
		}
		adapter = new ArrayAdapter<String>(context, textViewResourceId, cary);
		auto.setAdapter(adapter);
		handler.sendEmptyMessage(0);
	}

	private void initFjh() {
		dzjb = 3;
		getFjh(dqdz[1]);
		cary = new String[fjh.size()];
		fjh.keySet().toArray(cary);
		for (int i = 0; i < cary.length; i++) {
			cary[i] = " " + cary[i];
		}
		adapter = new ArrayAdapter<String>(context, textViewResourceId, cary);
		auto.setAdapter(adapter);
		handler.sendEmptyMessage(0);
	}

	/**
	 * 获取街路巷
	 */
	private void getJlx() {
		jlx = new LinkedHashMap<String, JlxVo>();
		String sql = "select pcsmc,pcsdm,jlxmc,jlxdm,jlxmcpy from pcsjlx order by pcsdm,jlxmc desc";
		SqliteHelper helper = SqliteHelper.getInstance(context);
		Cursor c = helper.Query(sql, null);
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				JlxVo vo = new JlxVo();
				vo.setPcsmc(c.getString(0));
				vo.setPcsdm(c.getString(1));
				vo.setJlxmc(c.getString(2));
				vo.setJlxdm(c.getString(3));
				vo.setJlxmcpy(c.getString(4));
				jlx.put(vo.getJlxmc(), vo);
			}
		}
		c.close();
		helper.close();
	}

	/**
	 * 获取楼牌号
	 */
	private void getLph(String jlxmc) {
		lph = new LinkedHashMap<String, LphVo>();
		String sql = "select jlxdm,lph,lphpy,dzbm,jlxmc from pcslph where jlxdm='"
				+ jlx.get(jlxmc).getJlxdm() + "' order by lph ";
		SqliteHelper helper = SqliteHelper.getInstance(context);
		Cursor c = helper.Query(sql, null);
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				LphVo vo = new LphVo();
				vo.setJlxdm(c.getString(0));
				vo.setLph(c.getString(1));
				vo.setLphpy(c.getString(2));
				vo.setDzbm(c.getString(3));
				vo.setJlxmc(c.getString(4));

				lph.put(vo.getLph(), vo);
			}
		}
		c.close();
		helper.close();
	}

	/**
	 * 获取房间号
	 */
	private void getFjh(String lph) {
		fjh = new LinkedHashMap<String, FjhVo>();

		String sql = "select dzbm,fjh,fjhpy,huid,jlxmc,lph from pcsfjh where dzbm='"
				+ this.lph.get(lph).getDzbm() + "' order by  fjh ";
		SqliteHelper helper = SqliteHelper.getInstance(context);
		Cursor c = helper.Query(sql, null);
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				FjhVo vo = new FjhVo();
				vo.setDzbm(c.getString(0));
				vo.setFjh(c.getString(1));
				vo.setFjhpy(c.getString(2));
				vo.setHuid(c.getString(3));
				vo.setJlxmc(c.getString(4));
				vo.setLph(c.getString(5));

				fjh.put(vo.getFjh(), vo);
			}
		}
		c.close();
		helper.close();
	}

}
