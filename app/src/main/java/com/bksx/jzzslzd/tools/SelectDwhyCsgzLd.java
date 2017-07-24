package com.bksx.jzzslzd.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;

/**
 * 单位所属行业，从事工作带其他框的二级联动
 * 
 * @author Y_jie
 * 
 */
public class SelectDwhyCsgzLd {
	private TextView t_sshy;
	private TextView t_csgz;
	private LinkedHashMap<String, String> m_sshy;
	private LinkedHashMap<String, String> m_csgz;
	private String[] v_sshy;
	private String[] v_csgz;
	private LinearLayout l_sshy;
	private LinearLayout l_csgz;
	private Context context;

	/**
	 * 
	 * 初始化
	 */
	public SelectDwhyCsgzLd(final Context context, TextView sshy,
			TextView csgz, LinearLayout l_sshy, LinearLayout l_csgz,
			String s_sshy, String s_csgz) {
		this.context = context;
		this.t_sshy = sshy;
		this.t_csgz = csgz;
		this.l_sshy = l_sshy;
		this.l_csgz = l_csgz;
		m_sshy = new LinkedHashMap<String, String>();
		m_csgz = new LinkedHashMap<String, String>();

		initSshy();
		v_sshy = this.setValues(m_sshy);

		this.t_sshy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestFocusSshy();
			}

		});
		if (s_sshy != null) {
			this.setSshyId(s_sshy);
		}
		initCsgz(s_csgz);

	}

	public void requestFocusSshy() {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
				.setItems(v_sshy, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						t_sshy.setText(v_sshy[which]);
						String cd_id = getCodeId(m_sshy, t_sshy);
						initCsgz(cd_id);
					}
				}).show();
	}

	/**
	 * 初始化从事工作
	 * 
	 */
	private void initCsgz(String s_csgz) {
		initdataCsgz(s_csgz);
		v_csgz = this.setValues(m_csgz);
		this.t_csgz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestFocusCsgz();
			}

		});
		this.setCsgzId(s_csgz);
	}

	public void requestFocusCsgz() {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
				.setItems(v_csgz, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						t_csgz.setText(v_csgz[which]);
						String cd_id = getCodeId(m_csgz, t_csgz);
						if (cd_id != null && !"".equals(cd_id)) {
							if ("".equals(getSshy())
									|| !cd_id.contains(getSshy())) {
								setSshyId(cd_id.substring(0, 5));
							}

							if (v_csgz[which].contains("描述")) {
								l_csgz.setVisibility(View.VISIBLE);
							} else {
								l_csgz.setVisibility(View.GONE);
							}
						} else {
							l_csgz.setVisibility(View.GONE);
						}
					}
				}).show();
	}

	/**
	 * 数据初始化所属行业
	 */
	private void initSshy() {
		String sql = "select cd_id,cd_name from SHHGL_D_CSHYDL where CD_AVAILABILITY='1' order by cd_index ";
		SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
		Cursor c = helper.Query(sql, null);

		m_sshy.put("", "请选择");
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				m_sshy.put(c.getString(0), c.getString(1));
			}
		}
		c.close();
		helper.close();
	}

	/**
	 * 数据初始化从事工作
	 * 
	 */
	private void initdataCsgz(String s_csgz) {
		m_csgz.clear();
		String f = "%";
		if (s_csgz != null && !"".equals(s_csgz)) {
			f = s_csgz.substring(0, 5) + f;
		}
		String sql = "select cd_id,cd_name from SJCJ_D_ZYCSGZ where CD_AVAILABILITY='1' and cd_id like '"
				+ f + "' order by cd_index ";
		SqliteHelper helper = SqliteHelper.getInstance(context);
		Cursor c = helper.Query(sql, null);

		m_csgz.put("", "请选择");
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				m_csgz.put(c.getString(0), c.getString(1));
			}
		}
		c.close();
		helper.close();
	}

	/**
	 * 设置所属行业
	 * 
	 */
	public void setSshyId(String codeId) {
		this.t_sshy.setText(this.m_sshy.get(codeId));
		if ("Z0000".equals(codeId)) {
			this.l_sshy.setVisibility(View.VISIBLE);
		} else {
			this.l_sshy.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置从事工作
	 * 
	 */
	public void setCsgzId(String codeId) {
		if (codeId == null || "".equals(codeId) || codeId.length() == 5) {
			this.t_csgz.setText(this.m_csgz.get(""));
		} else {
			this.t_csgz.setText(this.m_csgz.get(codeId));
			if (t_csgz.getText().toString().trim().contains("描述")) {
				l_csgz.setVisibility(View.VISIBLE);
			} else {
				l_csgz.setVisibility(View.GONE);
			}
		}
		if ("Z0000".equals(codeId)) {
			this.l_sshy.setVisibility(View.VISIBLE);
		} else {
			this.l_sshy.setVisibility(View.GONE);
		}

	}

	/**
	 * 获取所属行业
	 * 
	 */
	public String getSshy() {
		return getCodeId(m_sshy, t_sshy);
	}

	/**
	 * 获取从事工作
	 * 
	 */
	public String getCsgz() {
		return getCodeId(m_csgz, t_csgz);
	}

	/**
	 * 从事工作是否描述
	 * 
	 */
	public boolean sfms() {
		if (t_csgz.getText().toString().trim().contains("描述")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取值
	 */
	private String getCodeId(LinkedHashMap<String, String> map, TextView view) {
		return getKey(map, view.getText().toString().trim());
	}

	private String getKey(LinkedHashMap<String, String> map, Object value) {
		if (value == null)
			return "";
		for (String key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return "";
	}

	private String[] setValues(LinkedHashMap<String, String> map) {
		String[] values = new String[map.size()];
		int i = 0;
		for (Object key : map.keySet()) {
			values[i] = map.get(key);
			i++;
		}
		return values;
	}
}
