package com.bksx.jzzslzd.ldrk.rykc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bo.Fwxx;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rykc_fangwuliebiao_act extends Activity {
    // 界面数据，为list
    private List<Map<String, String>> data;
    private String[] dataFrom = new String[]{"xuhao", "xingming", "dizhi"};// map使用的key
    private String result;
    private String[][] getData;// intent取出的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rykc_fangwuliebiao);
        findViewById(R.id.rykc_fangwuliebiao_back).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        back();
                    }
                });
        // 人员采集界面跳过来的
        Intent intent = getIntent();
        result = intent.getStringExtra("data");
        getData = new Gson().fromJson(result, Fwxx.class).getFwxx();
        // 将数据填充到list中
        data = forData();
        ListView listview = (ListView) findViewById(R.id.rykc_fangwuliebiao_list);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.rykc_fangwuliebiao_list_item,
                dataFrom,
                new int[]{R.id.rykc_list_item_xuhao,
                        R.id.rykc_list_item_xingming, R.id.rykc_list_item_dizhi});
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent();
                intent.putExtra("fw", getData[arg2]);
                setResult(RESULT_OK, intent);
                finish();
                return;

            }
        });

    }

    private List<Map<String, String>> forData() {
        data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < getData.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(dataFrom[0], getData[i][0]);
            map.put(dataFrom[1], getData[i][1]);
            map.put(dataFrom[2], getData[i][6]);
            data.add(map);
        }
        return data;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void back() {
        finish();

    }

}
