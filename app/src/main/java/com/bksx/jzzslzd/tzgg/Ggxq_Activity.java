package com.bksx.jzzslzd.tzgg;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.tools.SqliteHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Ggxq_Activity extends Activity {
	private TextView ggxq_biaoti, ggxq_date, ggxq_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ggxq_a);
		init();
		Intent intent = getIntent();
		String biaoti = intent.getStringExtra("biaoti");
		String date = intent.getStringExtra("date");
		String content = intent.getStringExtra("content");
		String xfbh = intent.getStringExtra("xfbh");
		SqliteHelper helper = SqliteHelper.getInstance(Ggxq_Activity.this);
		helper.execSQL("update tzgg set sfck='1' where xfbh='" + xfbh + "'",
				null);
		ggxq_biaoti.setText(biaoti);
		ggxq_date.setText(date.substring(0, 4) + "-" + date.substring(4, 6)
				+ "-" + date.substring(6, 8));
		ggxq_content.setText(content);

	}

	private void init() {
		ggxq_biaoti = (TextView) findViewById(R.id.ggxq_biaoti);
		ggxq_date = (TextView) findViewById(R.id.ggxq_date);
		ggxq_content = (TextView) findViewById(R.id.ggxq_content);
	}

	public void click(View view) {
		Ggxq_Activity.this.finish();
	}

}
