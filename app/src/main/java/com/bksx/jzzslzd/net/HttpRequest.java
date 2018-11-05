package com.bksx.jzzslzd.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.bksx.jzzslzd.common.StaticObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 *
 */
public class HttpRequest {
    public static final String LOGIN = "/login/login";//登录
    public static final String BKHS = "/rycj/bkhs";//办卡人员核实
    public static final String FWCX = "/fw/fwcx";//房屋查询
    public static final String FWJY = "/fw/check";//房屋校验
    public static final String FWLR = "/fw/fwlr";//房屋录入
    public static final String SSXQCX = "/login/getSsxq";//所属辖区查询
    public static final String DJKSL = "/rycj/djksl";//登记卡受理
    public static final String GLY = "/login/cxgly";//查询管理员
    private HttpURLConnection urlConnection;
    private URL url;

    public static void POST(Context c, String code, String json, Callback.CommonCallback callback) {
        SharedPreferences preferences = c.getSharedPreferences(
                StaticObject.SHAREPREFERENC, 0);
        String ip_address = preferences.getString("IP_ADDRESS", "");
        if ("".equals(ip_address)) {
            ip_address = SxConfig.read(c, SxConfig.GATEWAYURL);
        }
        String url = "http://" + ip_address + "/jzzslzdjk" + code;
        RequestParams params = new RequestParams(url);
        Gson gson = new GsonBuilder().create();
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        StaticObject.print("URL:" + url);
        StaticObject.print("REQUEST:" + json);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.addParameter(
                    entry.getKey(),
                    entry.getValue());
        }
        params.setReadTimeout(Integer.parseInt(SxConfig.read(c,SxConfig.READTIMEOUT))*1000);
        params.setConnectTimeout(Integer.parseInt(SxConfig.read(c,SxConfig.CONNECTTIMEOUT))*1000);
        x.http().post(params, callback);
    }

    /**
     * 发送文本
     *
     * @param context 上下文
     * @param id      业务编码
     * @param IMSI    移动设备编号
     * @param message 信息
     * @return String 网关回执信息
     */
    public String sendString(Context context, String id, String IMSI,
                             String message) {
        return this.sendString();
    }

    /**
     * 发送文件
     *
     * @param context  上下文
     * @param id       业务编码
     * @param IMSI     移动设备编号
     * @param fileName 所有发送文件详细路径数组，包括文件名
     * @param message  文本信息
     * @return String 网关回执信息
     */
    public String sendFile(Context context, String id, String IMSI,
                           String fileName[], String message) {
        return this.sendFile();
    }

    /**
     * 发送文件
     *
     * @return String 网关回执信息
     */
    public String sendString() {
        return "";
    }

    /**
     * 发送文件数据
     *
     * @return String 网关回执信息
     */
    public String sendFile() {
        return "";
    }
}