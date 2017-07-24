package com.bksx.jzzslzd.tools;

import java.util.LinkedHashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

/**
 * 单选框对象
 * 
 * @author Y_jie
 * 
 */
public class SelectView {
	private TextView view;
	private LinkedHashMap<String, String> map;
	private String[] values;

	/**
	 * 初始化
	 * 
	 * @param context
	 *            上下文
	 * @param mymap
	 *            <codeId,codeName>
	 * @param textview
	 *            要显示的地方
	 */
	public SelectView(final Context context, LinkedHashMap<String, String> mymap,
			TextView textview) {
		this.map = mymap;
		this.view = textview;
		setValues();
		this.view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setTitle("请选择")
						.setItems(values,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										view.setText(values[which]);
									}
								}).show();
			}
		});
	}

	/**
	 * 设置这个框的值
	 *                                                                                                                                                                                  
	 * @param codeId
	 */
	public void setCodeId(String codeId) {
		this.view.setText(this.map.get(codeId));
	}

	/**
	 * 设置这个框的值
	 * 
	 * @param codeId
	 */
	public void setCodeName(String codeName) {
		if (getKey(codeName) != null) {
			this.view.setText(codeName);
		}
	}

	/**
	 * 获取值
	 */
	public String getCodeId() {
		return getKey(this.view.getText().toString().trim());
	}

	private String getKey(Object value) {
		if (value == null)
			return null;
		for (String key : this.map.keySet()) {
			if (value.equals(this.map.get(key))) {
				return key;
			}
		}

		return null;
	}

	private void setValues() {
		values = new String[this.map.size()];
		int i = 0;
		for (Object key : this.map.keySet()) {
			values[i] = this.map.get(key);
			i++;
		}

	}

}
