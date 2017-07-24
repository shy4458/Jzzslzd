package com.bksx.jzzslzd.tzgg;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.SqliteHelper;
import com.google.gson.Gson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Tzgg_Activity extends Activity {
	private ListView lv;
	private ProgressDialog dialog;
	private String result;
	private MyAdapter adapter;
	protected Gson gson;
	protected JSONObject jsonObject;
	private ArrayList<HashMap<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tzgg_a);
		lv = (ListView) findViewById(R.id.tzgg_list);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Tzgg_Activity.this,
						Ggxq_Activity.class);
				intent.putExtra("xfbh", list.get(position).get("xfbh"));
				intent.putExtra("biaoti", list.get(position).get("ggmc"));
				intent.putExtra("date", list.get(position).get("ggrq"));
				intent.putExtra("content", list.get(position).get("ggnr"));
				startActivity(intent);
			}
		});
		list = new ArrayList<HashMap<String, String>>();
		adapter = new MyAdapter();
		lv.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		Select();
		adapter.notifyDataSetChanged();
		super.onStart();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(Tzgg_Activity.this).inflate(
						R.layout.tzgg_item, null);
				holder = new ViewHolder();
				holder.biaoti = (TextView) convertView
						.findViewById(R.id.biaoti);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.biaoti.setText(list.get(position).get("ggmc"));
			String date = list.get(position).get("ggrq");
			holder.date.setText(date.substring(0, 4) + "-"
					+ date.substring(4, 6) + "-" + date.substring(6, 8));
			if (list.get(position).get("sfck").equals("1")) {
				StaticObject.print("灰色" + list.get(position).get("ggmc"));
				setGray(holder);
			} else {
				StaticObject.print("黑色" + list.get(position).get("ggmc"));
				setBlack(holder);
			}
			return convertView;
		}

		private void setGray(ViewHolder holder) {
			holder.biaoti.setTextColor(getResources().getColor(R.color.gray));
			holder.date.setTextColor(getResources().getColor(R.color.gray));
		}

		private void setBlack(ViewHolder holder) {
			holder.biaoti.setTextColor(getResources().getColor(R.color.black));
			holder.date.setTextColor(getResources().getColor(R.color.black));
		}

		class ViewHolder {
			public TextView biaoti;
			public TextView date;
		}

	}

	private void Select() {
		SqliteHelper helper = SqliteHelper.getInstance(Tzgg_Activity.this);
		String sql1 = "select xfbh,ggmc,ggnr,ggrq,sfck from tzgg order by sfck ,ggrq desc";
		Cursor cursor = helper.Query(sql1, null);
		list.clear();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("xfbh", cursor.getString(cursor.getColumnIndex("xfbh")));
			map.put("ggmc", cursor.getString(cursor.getColumnIndex("ggmc")));
			map.put("ggnr", cursor.getString(cursor.getColumnIndex("ggnr")));
			map.put("ggrq", cursor.getString(cursor.getColumnIndex("ggrq")));
			map.put("sfck", cursor.getString(cursor.getColumnIndex("sfck")));
			list.add(map);
		}
		cursor.close();
		helper.close();

	}

	public void click(View view) {
		Tzgg_Activity.this.finish();
	}
}
