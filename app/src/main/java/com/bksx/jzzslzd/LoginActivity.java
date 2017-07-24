package com.bksx.jzzslzd;

/**
 * 登陆界面:点击IMSI按钮，可以设置后台接口的IP地址
 * <p>
 * 1.回显记录的账号，密码及相关状态。
 * 2.如果自动登录为true并且不是从重新登录界面跳转过来的进行自动登录
 * 3.登录成功后如果需要软件更新或数据库更新则进行更新操作
 * 4.正常登录记录管理员所有状态及权限
 *
 * @author Y_Jie
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bksx.jzzslzd.bo.UserLogin;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.HttpCallBack;
import com.bksx.jzzslzd.net.HttpRequest;
import com.bksx.jzzslzd.net.SxConfig;
import com.bksx.jzzslzd.tools.MyAutoUpdate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.util.MD5;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends Activity implements View.OnClickListener {
    // 账号，密码
    private EditText login_user_e, login_pwd_e;
    // 记录密码，自动登录
    private CheckBox login_rem_pwd_c;
    // 重置，登录 按钮
    private Button login_reset_b, login_login_b;
    private ImageView login_pwd_show;
    // SharePerences
    private SharedPreferences preferences;
    private MyAutoUpdate autoUpdate;

    /**
     * 是否为重新登录
     */
    // private boolean isRestart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_a);
        initObject(); // 初始化对象
        initData(); // 初始化数据
        String tips = getIntent().getStringExtra("tips");
        if (("conn_time_out").equals(tips)) { //长时间无操作，session过期跳转到登录界面
            StaticObject.showToast(this, "由于您长时间无操作，系统已自动断开，请重新登录");
            return;
        }
        boolean isRestart = getIntent().getBooleanExtra("isRestart", false); //点击重新登录跳转到登录界面
        if (isRestart) {
            return;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Looper.prepare();
                login();
                Looper.loop();
            }
        }, 300);
    }

    private HttpCallBack<String> httpCallBack = new HttpCallBack<String>(LoginActivity.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            switch (jsonResult.getReturnCode()) {
                case 200:// 登录成功
                    UserLogin userLogin = gson.fromJson(gson.toJson(jsonResult.getReturnData()), UserLogin.class);
                    Editor editor = preferences.edit();
                    // 编辑登录ShareP
                    editor.putString("username", userLogin.getUsername());
                    editor.putString("password", login_pwd_e.getText().toString().trim());
                    editor.putBoolean("is_rem_pwd", login_rem_pwd_c.isChecked());
                    editor.putString(StaticObject.USERDATA, gson.toJson(userLogin));
                    // 提交
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    break;
                case 204:// 版本更新
                    UserLogin userLogin1 = gson.fromJson(gson.toJson(jsonResult.getReturnData()), UserLogin.class);
                    if (autoUpdate == null) {
                        autoUpdate = new MyAutoUpdate(LoginActivity.this,
                                userLogin1.getUrl());
                    }
                    autoUpdate.showNoticeDialog();
                    break;
                case 400:// Toast消息
                    StaticObject.showToast(LoginActivity.this, jsonResult.getReturnMsg());
                    break;
                default:
                    break;
            }

        }
    };


    /**
     * 初始化数据显示
     */
    private void initData() {

        if (preferences != null) {
            String username = preferences.getString("username", null);
            String password = preferences.getString("password", null);
            boolean is_rem_pwd = preferences.getBoolean("is_rem_pwd", false);
            // boolean is_auto_login = preferences.getBoolean("is_auto_login",
            // false);
            if (username != null) {
                login_user_e.setText(username);
                if (password != null && is_rem_pwd) {
                    login_pwd_e.setText(password);
                    login_rem_pwd_c.setChecked(is_rem_pwd);

                }
            }
        }
    }

    /**
     * 初始化对象
     */
    private void initObject() {
        login_user_e = (EditText) findViewById(R.id.login_user);
        login_pwd_e = (EditText) findViewById(R.id.login_pwd);
        login_rem_pwd_c = (CheckBox) findViewById(R.id.login_rem_pw);

        login_reset_b = (Button) findViewById(R.id.login_reset_b);
        login_login_b = (Button) findViewById(R.id.login_login_b);
        login_pwd_show = (ImageView) findViewById(R.id.login_show_pwd);

        login_reset_b.setOnClickListener(this);
        login_login_b.setOnClickListener(this);
        login_pwd_show.setOnClickListener(this);

        login_reset_b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showURLDialog();
                return true;
            }


        });
        preferences = this.getSharedPreferences(StaticObject.SHAREPREFERENC,
                Activity.MODE_PRIVATE);
    }

    private void showURLDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.login_imsi_dialog, null);
        final EditText ip_address = ((EditText) view.findViewById(R.id.login_ip_edit));
        String ip = preferences.getString("IP_ADDRESS", "");
        if ("".equals(ip)) {
            ip = SxConfig.read(LoginActivity.this, SxConfig.GATEWAYURL);
        }
        ip_address.setText(ip);
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle("接口设置")
                .setIcon(android.R.drawable.ic_dialog_alert).setView(view)
                .create();
        ad.setCanceledOnTouchOutside(true);
        ad.show();
        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String ip_address_ = ip_address.getText().toString().trim();
                Editor edit = preferences.edit();
                edit.putString("IP_ADDRESS", ip_address_);
                edit.commit();
                StaticObject.showToast(LoginActivity.this, "IP地址修改为："
                        + ip_address_);
            }

        });


    }

    /**
     * 登录请求
     */
    private void login() {
        // 如果不自动登录，要校验账号密码非空
        String user = login_user_e.getText().toString().trim();
        if ("".equals(user)) {
            StaticObject.showToast(LoginActivity.this, "请填写管理员账号");
            return;
        }
        String pwd = login_pwd_e.getText().toString().trim();
        if ("".equals(pwd)) {
            StaticObject.showToast(LoginActivity.this, "请填写管理员密码");
            return;
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(user);
        userLogin.setPassword(MD5.md5(pwd));//密码MD5加密
        userLogin.setVersion(this.getVersion());
        Gson gson = new GsonBuilder().create();
        httpCallBack.showDialog("登录中...");
        HttpRequest.POST(this, HttpRequest.LOGIN, gson.toJson(userLogin), httpCallBack);
    }


    /*
     * 按钮点击事件(non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_reset_b: // 重置
                login_user_e.setText("");
                login_pwd_e.setText("");
                break;
            case R.id.login_login_b: // 登录
                login();
                break;
            case R.id.login_show_pwd: // 密码显隐图标
                // 修改密码的显隐状态
                updatePwdShowType();
                break;

            default:
                break;
        }
    }


    /**
     * 修改密码的显隐状态
     */
    private void updatePwdShowType() {
        TransformationMethod m = login_pwd_e.getTransformationMethod();
        if (m.equals(PasswordTransformationMethod.getInstance())) {
            login_pwd_e.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        } else {
            // 设置密码为隐藏的
            login_pwd_e.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    private String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
