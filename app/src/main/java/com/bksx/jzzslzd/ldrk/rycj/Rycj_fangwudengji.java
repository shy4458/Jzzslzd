package com.bksx.jzzslzd.ldrk.rycj;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bksx.jzzslzd.R;
import com.bksx.jzzslzd.bean.Gly;
import com.bksx.jzzslzd.bean.GlyCode;
import com.bksx.jzzslzd.bo.CodeTable;
import com.bksx.jzzslzd.bo.Fwxx;
import com.bksx.jzzslzd.bo.UserLogin;
import com.bksx.jzzslzd.bo.XqCode;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.HttpCallBack;
import com.bksx.jzzslzd.net.HttpRequest;
import com.bksx.jzzslzd.tools.FormCheck;
import com.bksx.jzzslzd.tools.SelectViewAndHandlerAndMsg;
import com.bksx.jzzslzd.tools.SqliteCodeTable;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bksx.jzzslzd.common.StaticObject.showToast;

/**房屋登記
 * Created by user on 2017/8/9.
 */

public class Rycj_fangwudengji extends Activity {
    // 输入框：房屋地址，房主姓名，房主身份证号，房主现住地址，房主联系电话
    private EditText fwdj_fwdz_e, fwdj_fzxm_e, fwdj_fzsfzh_e, fwdj_fzxzdz_e, fwdj_fzlxdh_e;
    // 选择框：所属服务站，所属辖区，房屋类型
    private SelectViewAndHandlerAndMsg fwdj_ssfwz_s, fwdj_ssxq_s, fwdj_fwlx_s;
    // 选择框背景：所属服务站，所属辖区,房屋类型
    private TextView fwdj_ssfwz_t, fwdj_ssxq_t, fwdj_fwlx_t;
    private Button baocun;
    private ImageButton back;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 12:
                    String id = fwdj_ssfwz_s.getCodeId();
                    ssxqcx(id);
                    break;

            }
        }
    };


    private LinkedHashMap<String, String> xzqhmap;
    private TextView fwdj_gly;
    private SelectViewAndHandlerAndMsg fwdj_gly_s;

    public void ssxqcx(String id) {
        String json = "{'id':'" + id + "'}";
        ssxqcx.showDialog("请求中...");
        HttpRequest.POST(Rycj_fangwudengji.this, HttpRequest.SSXQCX, json, ssxqcx);

        GlyCode glyCode = new GlyCode();
        glyCode.setSsfwzbh(id);
        Gson gson = new GsonBuilder().create();
        HttpRequest.POST(Rycj_fangwudengji.this, HttpRequest.GLY,gson.toJson(glyCode), glycx);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rycj_fangwudengji);
        findView();
        init();
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    fwdj();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rycj_fangwudengji.this.finish();
            }
        });
        fwdj_fwdz_e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String fwlx = fwdj_fwlx_s.getCodeId();
                    String fzxm = fwdj_fzxm_e.getText().toString().trim();
                    String fwdz = fwdj_fwdz_e.getText().toString().trim();
                    if (!fwlx.isEmpty()&&!fzxm.isEmpty()&&!fwdz.isEmpty()) {
                        fwjy();
                    }
                }
            }
        });
    }

    private HttpCallBack<String> fwjy = new HttpCallBack<String>(Rycj_fangwudengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 700) {
                showToast(Rycj_fangwudengji.this, "房屋已存在");
            } else {
                showToast(Rycj_fangwudengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    private void fwjy() {
        String json = "{'fwlb':'" + fwdj_fwlx_s.getCodeId() + "','fzxm':'" + fwdj_fzxm_e.getText().toString().trim() + "','fwdz':'" + fwdj_fwdz_e.getText().toString().trim() + "'}";
        fwjy.showDialog("请求中...");
        HttpRequest.POST(Rycj_fangwudengji.this, HttpRequest.FWJY, json, fwjy);
    }

    private boolean checkThree() {
        if (isNull(fwdj_fwlx_s, "房屋类型")) {
            return false;
        }
        if (isNull(fwdj_fzxm_e, "房主姓名")) {
            return false;
        }
        if (isNull(fwdj_fwdz_e, "房屋地址")) {
            return false;
        }
        return true;
    }

    private boolean check() {
        if (isNull(fwdj_fwlx_s, "房屋类型")) {
            return false;
        }
        if (isNull(fwdj_fzxm_e, "房主姓名")) {
            return false;
        }
        if (isNull(fwdj_fwdz_e, "房屋地址")) {
            return false;
        }
        String sfzhm = fwdj_fzsfzh_e.getText().toString().trim();
        if (!TextUtils.isEmpty(sfzhm)) {
            String[] id_check = FormCheck.check_Card_ID(sfzhm);
            if (id_check[0].equals("false")) {
                StaticObject.showToast(this, id_check[1]);
                fwdj_fzsfzh_e.requestFocus();
                return false;
            } else {
                fwdj_fzsfzh_e.setText(id_check[1]);
            }
        }
        if (isNull(fwdj_fzxzdz_e, "房主现住地址")) {
            return false;
        }
        if (isNull(fwdj_fzlxdh_e, "房主联系电话")) {
            return false;
        } else if (!checkPhone()) {
            StaticObject.showToast(Rycj_fangwudengji.this, "请输入正确的手机号码");
            return false;
        }
        return true;
    }

    HttpCallBack<String> fwlr = new HttpCallBack<String>(Rycj_fangwudengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 700) {
                showToast(Rycj_fangwudengji.this, "房屋已存在");
            } else if (jsonResult.getReturnCode() == 201) {
                showToast(Rycj_fangwudengji.this, jsonResult.getReturnMsg());
                Rycj_fangwudengji.this.finish();
            } else {
                showToast(Rycj_fangwudengji.this, jsonResult.getReturnMsg());
            }


        }
    };

    private void fwdj() {
        Fwxx fwxx = new Fwxx();
        fwxx.setFwzbh(fwdj_ssfwz_s.getCodeId());
        fwxx.setSsxqbh(fwdj_ssxq_s.getCodeId());
        fwxx.setSzdxzqh(xzqhmap.get(fwdj_ssxq_s.getCodeId()));
        fwxx.setFwlb(fwdj_fwlx_s.getCodeId());
        fwxx.setFwdz(fwdj_fwdz_e.getText().toString().trim());
        fwxx.setFzxm(fwdj_fzxm_e.getText().toString().trim());
        fwxx.setFzsfzhm(fwdj_fzsfzh_e.getText().toString().trim());
        fwxx.setFzxzdxxdz(fwdj_fzxzdz_e.getText().toString().trim());
        fwxx.setFzlxdh(fwdj_fzlxdh_e.getText().toString().trim());
        fwxx.setGlybm(fwdj_gly_s.getCodeId());
        String json = new GsonBuilder().create().toJson(fwxx);
        fwlr.showDialog("请求中...");
        HttpRequest.POST(Rycj_fangwudengji.this, HttpRequest.FWLR, json, fwlr);

    }
    //所属辖区
    private HttpCallBack<String> ssxqcx = new HttpCallBack<String>(Rycj_fangwudengji.this) {
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 200) {
                LinkedHashMap<String, String> xqmap = new LinkedHashMap<String, String>();
                xzqhmap = new LinkedHashMap<String, String>();
                ArrayList<XqCode> xqData = gson.fromJson(gson.toJson(jsonResult.getReturnData()), new TypeToken<ArrayList<XqCode>>() {
                }.getType());
                for (int i = 0; i < xqData.size(); i++) {
                    xqmap.put(xqData.get(i).getId(), xqData.get(i).getName());
                    xzqhmap.put(xqData.get(i).getId(), xqData.get(i).getXzqh());
                }
                fwdj_ssxq_s = new SelectViewAndHandlerAndMsg(Rycj_fangwudengji.this, "所属辖区", xqmap, fwdj_ssxq_t, handler, 56, xqData.get(0).getId());
            } else {
                showToast(Rycj_fangwudengji.this, jsonResult.getReturnMsg());
            }
        }
    };

    //管理员
    private HttpCallBack<String> glycx = new HttpCallBack<String>(Rycj_fangwudengji.this) {
        private String glybmMR;
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            if (jsonResult.getReturnCode() == 200) {
                String str = gson.toJson(jsonResult.getReturnData());
                LinkedHashMap<String, String> glyMap = new LinkedHashMap<String, String>();
                try {
                    JSONArray array = new JSONArray(str);
                    for (int i = 0; i < array.length(); i++) {
                        if (i == 0){
                            JSONObject jsonObject = array.getJSONObject(i);
                            //第一个显示
                            glybmMR = jsonObject.getString("glybm");
                        }
                        JSONObject jsonObject = array.getJSONObject(i);
                        String glybm = jsonObject.getString("glybm");
                        String glyxm = jsonObject.getString("glyxm");
                        glyMap.put(glybm,glyxm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fwdj_gly_s = new SelectViewAndHandlerAndMsg(Rycj_fangwudengji.this, "管理员", glyMap, fwdj_gly, handler, 11,glybmMR);
            } else {
                showToast(Rycj_fangwudengji.this, jsonResult.getReturnMsg());
            }
        }
    };


    private void init() {
        UserLogin userData = StaticObject.getUserData(Rycj_fangwudengji.this);
        ArrayList<CodeTable> fwz = userData.getFwz();
        LinkedHashMap<String, String> fwzmap = new LinkedHashMap<String, String>();
        for (int i = 0; i < fwz.size(); i++) {
            fwzmap.put(fwz.get(i).getId(), fwz.get(i).getName());
        }
        String id = fwz.get(0).getId();
        fwdj_ssfwz_s = new SelectViewAndHandlerAndMsg(this, "所属服务站", fwzmap, fwdj_ssfwz_t, handler, 12, id);
        ssxqcx(id);
        fwdj_fwlx_s = new SelectViewAndHandlerAndMsg(this, "房屋类型", getMap("SJCJ_D_ZSLX"), fwdj_fwlx_t, handler, 11, "02");

    }

    private void findView() {
        fwdj_ssfwz_t = (TextView) findViewById(R.id.fangwudengji_select_suoshufuwuzhan);
        fwdj_ssxq_t = (TextView) findViewById(R.id.fangwudengji_select_suoshuxiaqu);
        fwdj_fwlx_t = (TextView) findViewById(R.id.fangwudengji_select_fangwuleixing);
        fwdj_fwdz_e = (EditText) findViewById(R.id.fangwudengji_et_fangwudizhi);
        fwdj_gly = (TextView) findViewById(R.id.fangwudengji_select_guanliyuan);
        fwdj_fzxm_e = (EditText) findViewById(R.id.fangwudengji_et_fangzhuxingming);
        fwdj_fzsfzh_e = (EditText) findViewById(R.id.fangwudengji_et_fangzhushenfenzhenghao);
        fwdj_fzxzdz_e = (EditText) findViewById(R.id.fangwudengji_et_fangzhuxianzhudizhi);
        fwdj_fzlxdh_e = (EditText) findViewById(R.id.fangwudengji_et_fangzhulianxidianhua);

        baocun = (Button) findViewById(R.id.fangwudengji_btn_baocun);
        back = (ImageButton) findViewById(R.id.ryjc_fangwudengji_back);


    }

    /**
     * 获取代码表数据
     *
     * @param  　表名
     * @return
     */
    private LinkedHashMap<String, String> getMap(String name) {
        String sql = "select CD_ID,CD_NAME from " + name
                + " where CD_AVAILABILITY='1' order by CD_INDEX";
        SqliteCodeTable helper = SqliteCodeTable.getInstance(Rycj_fangwudengji.this);
        Cursor c = helper.Query(sql, null);
        int l = c.getCount();
        LinkedHashMap<String, String> linkedMap = null;
        if (l > 0) {
            linkedMap = new LinkedHashMap<String, String>();
            linkedMap.put("", "请选择");
            while (c.moveToNext()) {
                linkedMap.put(c.getString(c.getColumnIndex("CD_ID")), c
                        .getString(c.getColumnIndex("CD_NAME")).trim());
            }
        }
        c.close();
        helper.close();
        return linkedMap;
    }

    /**
     * 校验EditText表单内容不为null和""
     */
    private boolean isNull(EditText view, String name) {
        if (view != null && "".equals(view.getText().toString().trim())) {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            showToast(this, "请填写" + name);
            return true;
        }
        return false;
    }

    /**
     * 校验SelectViewAndHandlerAndMsg表单内容不为null和""
     */
    private boolean isNull(SelectViewAndHandlerAndMsg select, String name) {
        if (select != null
                && ("".equals(select.getCodeId()) || select.getCodeId() == null)) {
            select.requestFocus();
            showToast(this, "请选择" + name);
            return true;
        }
        return false;
    }

    /**
     * 校验手机号码
     */
    private boolean checkPhone() {
        String str = fwdj_fzlxdh_e.getText().toString().trim();
        String regEx = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }
}
