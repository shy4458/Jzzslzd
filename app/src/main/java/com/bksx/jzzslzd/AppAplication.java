package com.bksx.jzzslzd;

import android.app.Application;

import com.bksx.jzzslzd.common.StaticObject;

import org.xutils.x;


/**
 * Created by user on 2017/7/3.
 */

public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        StaticObject.print("AppAplication init...");
    }
}
