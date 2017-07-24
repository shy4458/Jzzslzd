package com.bksx.jzzslzd;

/**
 * 欢迎界面:显示欢迎图片。预期效果：欢迎图片可以后台发布自动更新（eg:节日问候等）
 * <p>
 * 1.检测空间大小:checkSize();
 * 2.安装扫描设备apk:install_IDservice();
 * 3.检测网络状态:checkApn
 * 4.软件更新:updateAPK();
 * 5.自动登录:autoLogin();
 *
 * @author Y_Jie
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.bksx.jzzslzd.tools.SqliteHelper;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class WelActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_a);

        /**
         * 初始化数据库并关闭
         */
        SqliteHelper helper = SqliteHelper.getInstance(this);
        helper.close();
        SqliteCodeTable codeTable = SqliteCodeTable.getInstance(this);
        codeTable.updateDatabase();
        codeTable.close();
        // 检测手机内存>5M
        checkSize();

        // 5检测shareP有没有用户自动登录
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

                toLoginPage();
            }
        }, 300);
        // 启动定位服务

    }

    /**
     * 检测内存大小
     */
    private void checkSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        long m = (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
        Log.i(StaticObject.LOGTAG, "剩余" + m);
        if (m < 5) {
            StaticObject.showToast(this, "您的手机内存过低，请清理");
        }


    }

    /**
     * 跳转到登录页
     */
    private void toLoginPage() {
        Intent intent = new Intent(WelActivity.this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

}
