package com.bksx.jzzslzd.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 单选框对象
 * 
 * @author Y_jie
 * 
 */
public class SelectViewAndHandlerAndMsg {
	private TextView view;
	private LinkedHashMap<String, String> map;
	private String[] values;
	private Context context;
	private View.OnClickListener listener;
	private Handler handler;
	private int what;
	private String title;
    private String value;

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
	public SelectViewAndHandlerAndMsg(final Context context, String title,
			LinkedHashMap<String, String> mymap, TextView textview,
			final Handler handler, final int what, String cd_id) {
		this.map = mymap;
		this.view = textview;
		this.context = context;
		this.handler = handler;
		this.what = what;
		this.title = title;
		setValues();
		listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestFocus();
			}
		};

		this.view.setOnClickListener(listener);
		if (cd_id != null) {
			this.setCodeId(cd_id);

		}

	}

	public void setClickable(boolean flag) {
		if (flag) {
			this.view.setOnClickListener(listener);
		} else {
			this.view.setOnClickListener(null);
		}
	}

	/**
	 * 设置这个框的值
	 * 
	 * @param codeId
	 */
	public void setCodeId(String codeId) {
		this.view.setText(this.map.get(codeId));
	}

	public void requestFocus() {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("请选择" + title)
				.setItems(values, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
                        value = values[which];
						view.setText(values[which]);
						handler.sendEmptyMessage(what);
					}
				}).show();
	}

	/**
	 * 设置这个框的值
	 * 
	 * @param codeName
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
		if (value == "")
			return null;
		for (String key : this.map.keySet()) {
			if (value.equals(this.map.get(key))) {
				return key;
			}
		}

		return "";
	}

	private void setValues() {
		values = new String[this.map.size()];
		int i = 0;
		for (Object key : this.map.keySet()) {
			values[i] = this.map.get(key);
			i++;
		}
	}
    public String getKey(){
        String key="";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(value.equals(entry.getValue())){
                key=entry.getKey();
                continue;
            }
        }
        return key;
    }
}
