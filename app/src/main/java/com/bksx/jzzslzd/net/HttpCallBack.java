package com.bksx.jzzslzd.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.bksx.jzzslzd.LoginActivity;
import com.bksx.jzzslzd.bo.JsonResult;
import com.bksx.jzzslzd.common.StaticObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;

/**
 * Created by user on 2017/7/4.
 */

public class HttpCallBack<T> implements Callback.CommonCallback<T> {
    private Context context;
    private ProgressDialog progressDialog;
    public JsonResult jsonResult;
    public Gson gson = new GsonBuilder().create();

    public HttpCallBack(Context context) {
        this.context = context;
    }

    private void closeDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("进度");
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(null);
        progressDialog.show();
    }

    @Override
    public void onSuccess(T result) {
        this.closeDialog();
        StaticObject.print(result.toString());
        String jsonStr = (String) result;
        jsonResult = gson.fromJson(jsonStr, JsonResult.class);
        if (jsonResult.getReturnCode() == 500) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("tips", "conn_time_out");
            context.startActivity(intent);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        this.closeDialog();
        StaticObject.print("onError:" + ex);
        StaticObject.showToast(context, "网络连接失败");
    }

    @Override
    public void onCancelled(CancelledException cex) {
        this.closeDialog();
        StaticObject.print("onCancelled...");
    }

    @Override
    public void onFinished() {
        this.closeDialog();
        StaticObject.print("onFinished...");
    }
}
