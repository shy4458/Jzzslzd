package com.bksx.jzzslzd;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.bksx.jzzslzd.bo.UserLogin;
import com.bksx.jzzslzd.common.StaticObject;
import com.google.gson.GsonBuilder;

public class HomeActivity extends TabActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_a);
        initData();
        addTab(this.getTabHost());
        //Intent intent = new Intent(this, UploadPositionService.class);
        //startService(intent);

    }

    /**
     * 初始化头部数据
     */
    private void initData() {
        TextView textView = (TextView) findViewById(R.id.frame_top_user_tv);
        TextView cxdl = (TextView) findViewById(R.id.frame_top_cxdl);
        TextView tcxt = (TextView) findViewById(R.id.frame_top_tcxt);
        // ImageView imageView = (ImageView)
        // findViewById(R.id.frame_top_user_img);
        SharedPreferences preferences = this.getSharedPreferences(
                StaticObject.SHAREPREFERENC, Activity.MODE_PRIVATE);
        String preferencesString = preferences.getString(StaticObject.USERDATA, "");
        UserLogin userLogin = new GsonBuilder().create().fromJson(preferencesString, UserLogin.class);
        textView.setText(userLogin.getU_name() + "(" + userLogin.getU_id() + ")");
        // imageView.setOnClickListener(this);
        cxdl.setOnClickListener(this);
        tcxt.setOnClickListener(this);

        // xtcz = (LinearLayout) findViewById(R.id.frame_xtcz_r);
    }

    /**
     * 为TabHost添加标签页
     *
     * @param tabHost
     */
    private void addTab(TabHost tabHost) {
        String[] tags = new String[]{"sy", "menu", "set"};
        String[] labels = new String[]{"首页", "功能", "设置"};
        int[] drawables = new int[]{R.drawable.frame_sy_b,
                R.drawable.frame_menu_b, R.drawable.frame_set_b};
        Class<?>[] classs = new Class<?>[]{Tab_Sy_Activity.class,
                Tab_Menu_Activity.class, Tab_Set_Activity.class};

        for (int i = 0; i < tags.length; i++) {
            Drawable drawable = getResources().getDrawable(drawables[i]);
            Intent intent = new Intent(this, classs[i]);
            TabSpec tab_sy = tabHost.newTabSpec(tags[i])
                    .setIndicator(labels[i], drawable).setContent(intent);
            tabHost.addTab(tab_sy);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_top_cxdl:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("isRestart", true);
                this.startActivity(intent);
                this.finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.frame_top_tcxt:
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
    }

}
