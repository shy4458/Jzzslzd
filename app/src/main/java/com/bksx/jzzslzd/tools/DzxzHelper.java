package com.bksx.jzzslzd.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedHashMap;

/**
 * 地址选择对象，三级联动
 * <p>
 * 传入Context，省级选择框（TextView），市级选择框（TextView），县级选择框（TextView），
 *
 * @author Y_jie
 * @包含方法：
 * @1：对象初始化方法：DzxzHelper(final Context context, TextView province, TextView
 * city, TextView town, boolean flag);
 * @2:初始化地址cd_id：setCodeId(String codeId)
 * @3:获取地址cd_id:getCodeId()
 */
public class DzxzHelper {
    private final TextView province;// 省级
    private final TextView city;// 市级
    private final TextView town;// 县级
    private LinkedHashMap<String, String> map_p;
    private LinkedHashMap<String, String> map_c;
    private LinkedHashMap<String, String> map_t;
    private String[] values_p;
    private String[] values_c;
    private String[] values_t;

    private Context context;

    /**
     * 初始化对象方法
     *
     * @param context  传入Activity.this
     * @param province 省级TextView
     * @param city     市级TextView
     * @param town     县级TextView
     * @param flag     是不是包含北京市（true为包含，false为非北京市）
     */
    public DzxzHelper(final Context context, TextView province, TextView city,
                      TextView town, boolean flag) {
        // 对象初始化
        this.context = context;
        this.province = province;
        this.city = city;
        this.town = town;
        // 对象初始化
        map_p = new LinkedHashMap<String, String>();
        map_c = new LinkedHashMap<String, String>();
        map_t = new LinkedHashMap<String, String>();

        // 初始化省级对象
        getProvinces(flag);
        values_p = setValues(map_p);

        province.setText("请选择");
        city.setText("--");
        town.setText("--");
        // 省级添加监听事件
        this.province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProSel();
            }
        });
    }

    private void showProSel() {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
                .setItems(values_p, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 显示选择的值
                        DzxzHelper.this.province.setText(values_p[which]);
                        // 获取选择的cd_id值
                        String cd_id_p = getCodeId(map_p,
                                DzxzHelper.this.province);
                        // 初始化市级对象
                        initCityView(cd_id_p);
                    }
                }).show();
    }

    /**
     * 初始化市级对象
     *
     * @param cd_id
     */
    private void initCityView(String cd_id) {
        // 如果省级选择的是“请选择或null”,将市级及县级置空
        if (cd_id == null || "".equals(cd_id)) {
            this.city.setText("--");
            this.city.setOnClickListener(null);
            this.town.setText("--");
            this.town.setOnClickListener(null);
            return;
        }
        // 初始化市级数据
        getCities(cd_id);
        values_c = setValues(map_c);
        this.city.setText("请选择");
        initTownView(null);
        // 市级添加监听
        this.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCitySel();
            }

        });
    }

    private void showCitySel() {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
                .setItems(values_c, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 显示选择的市级值
                        DzxzHelper.this.city.setText(values_c[which]);
                        String cd_id_c = getCodeId(map_c, DzxzHelper.this.city);
                        // 初始化县级对象
                        initTownView(cd_id_c);
                    }
                }).show();
    }

    /**
     * 初始化县级对象
     *
     * @param cd_id
     */
    private void initTownView(String cd_id) {
        // 如果市级选择的是“请选择或null”，将县级置空
        if (cd_id == null || "".equals(cd_id)) {
            this.town.setText("--");
            this.town.setOnClickListener(null);
            return;
        }
        // 初始化县级数据
        getTowns(cd_id);
        values_t = setValues(map_t);
        // 当省级选择的是直辖市天津、重庆、上海时，县级无数据!
        if (values_t.length > 0) {
            this.town.setText("请选择");
            // 县级添加监听
            this.town.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTownSel();
                }

            });
        } else {// 当省级选择的是直辖市天津、重庆、上海时，县级无数据!
            this.town.setText("--");
            this.town.setOnClickListener(null);
        }

    }

    private void showTownSel() {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info).setTitle("请选择")
                .setItems(values_t, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 只需要设置县级选择的值
                        DzxzHelper.this.town.setText(values_t[which]);
                    }
                }).show();
    }

    /**
     * 初始化省级数据
     *
     * @param flag 要不要北京市的数据(true为要，false为不要)
     */
    private void getProvinces(boolean flag) {
        String f = "";
        if (!flag) {
            f = "and cd_id !='110000000000'";
        }
        String sql = "select cd_id,cd_name from cdg_regioncode where CD_AVAILABILITY='1' and cd_id like '__0000000000' "
                + f + " order by cd_index ";
        SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
        Cursor c = helper.Query(sql, null);
        map_p.put("", "请选择");
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                map_p.put(c.getString(0), c.getString(1));
            }
        }
        c.close();
        helper.close();
    }

    /**
     * 初始化市级数据
     *
     * @return
     * @!注意直辖市为前三位是市级，后三位为区县，再后三位为办事处，普通省为前俩位为省级，后两位为市级，再后两位为县级!
     */
    private void getCities(String cd_id) {
        map_c.clear();
        if ("".equals(cd_id)) {
            return;
        }
        String xx = cd_id.substring(0, 2);
        String shi = cd_id.substring(0, 2) + "__00000000";
        if ("11".equals(xx) || "12".equals(xx) || "31".equals(xx)
                || "50".equals(xx)) {
            shi = cd_id.substring(0, 3) + "___000000";
        }
        String sql = "select cd_id,cd_name from cdg_regioncode where CD_AVAILABILITY='1' and cd_id like '"
                + shi + "' and cd_id != '" + cd_id + "' order by cd_index ";
        SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
        Cursor c = helper.Query(sql, null);
        map_c.put("", "请选择");
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                map_c.put(c.getString(0), c.getString(1));
            }
        }
        c.close();
        helper.close();
    }

    /**
     * 初始化县级数据
     *
     * @return
     */
    private void getTowns(String cd_id) {
        if ("".equals(cd_id)) {
            return;
        }
        String xx = cd_id.substring(0, 2);
        map_t.clear();
        if (!"12".equals(xx) && !"31".equals(xx) && !"50".equals(xx)) {
            String sql = "";
            if ("11".equals(xx)) {
                sql = "select cd_id,cd_name from cdg_regioncode where CD_AVAILABILITY='1' and cd_id like '"
                        + cd_id.substring(0, 6)
                        + "___000' and cd_id != '"
                        + cd_id + "' order by cd_index ";
            } else {
                sql = "select cd_id,cd_name from cdg_regioncode where CD_AVAILABILITY='1' and cd_id like '"
                        + cd_id.substring(0, 4)
                        + "__000000' and cd_id != '"
                        + cd_id + "' order by cd_index ";
            }
            SqliteCodeTable helper = SqliteCodeTable.getInstance(context);
            Cursor c = helper.Query(sql, null);
            if (c.getCount() > 0) {
                map_t.put("", "请选择");
                while (c.moveToNext()) {
                    map_t.put(c.getString(0), c.getString(1));
                }
            }
            c.close();
            helper.close();
        }

    }

    /**
     * 设置地址选择器的cd_id，传入空值为重置。
     *
     * @param codeId
     */
    public void setCodeId(String codeId) {
        if (codeId == null || "".equals(codeId)) {
            this.province.setText(map_p.get(codeId));
            initCityView(codeId);
            this.city.setText("--");
            initTownView(codeId);
            this.town.setText("--");
            return;
        }
        String pro = map_p.get(codeId.substring(0, 2) + "0000000000");
        if (pro != null && !"".equals(pro)) {
            this.province.setText(pro);
            if (!"".equals(getCodeId(map_p, this.province))) {
                String xx = codeId.substring(0, 2);
                initCityView(codeId.substring(0, 2) + "0000000000");
                if ("11".equals(xx) || "12".equals(xx) || "31".equals(xx)
                        || "50".equals(xx)) {
                    this.city.setText(map_c.get(codeId.substring(0, 6)
                            + "000000"));
                    initTownView(codeId.substring(0, 6) + "00000000");
                } else {
                    this.city.setText(map_c.get(codeId.substring(0, 4)
                            + "00000000"));
                    initTownView(codeId.substring(0, 4) + "00000000");
                    this.town
                            .setText(map_t.get(codeId.substring(0, 9) + "000"));
                }
            } else {
                initCityView("");
                this.city.setText("--");
                initTownView("");
                this.town.setText("--");
            }
        } else {
            this.province.setText("");
        }
    }

    /**
     * 获取地址选择器的cd_id，用于校验及提交
     *
     * @return ""或具体值
     */
    public String getCodeId() {
        String dz3 = getCodeId(map_t, DzxzHelper.this.town);
        if (!"".equals(dz3)) {
            return dz3;
        }
        String dz2 = getCodeId(map_c, DzxzHelper.this.city);
        if (!"".equals(dz2)) {
            return dz2;
        }
        String dz1 = getCodeId(map_p, DzxzHelper.this.province);
        if (!"".equals(dz1)) {
            return dz1;
        }
        return "";
    }

    /**
     * 获取地址选择器的cd_id，用于校验及提交
     *
     * @return ""或具体值
     */
    public String getLastCodeId() {
        if (map_t.size() > 0) {
            return getCodeId(map_t, DzxzHelper.this.town);
        } else {
            return getCodeId(map_c, DzxzHelper.this.city);
        }
    }

    /**
     * 获取值
     */
    private String getCodeId(LinkedHashMap<String, String> map, TextView view) {
        return getKey(map, view.getText().toString().trim());
    }

    private String getKey(LinkedHashMap<String, String> map, Object value) {
        if (value == null)
            return "";
        for (String key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }

        return "";
    }

    public void requestFocus() {
        this.province.requestFocus();
        String dz1 = getCodeId(map_p, DzxzHelper.this.province);
        if ("".equals(dz1)) {
            showProSel();
            return;
        }
        String dz2 = getCodeId(map_c, DzxzHelper.this.city);
        if ("".equals(dz2)) {
            showCitySel();
            return;
        }
        String dz3 = getCodeId(map_t, DzxzHelper.this.town);
        if ("".equals(dz3)) {
            showTownSel();
            return;
        }
    }

    private String[] setValues(LinkedHashMap<String, String> map) {
        String[] values = new String[map.size()];
        int i = 0;
        for (Object key : map.keySet()) {
            values[i] = map.get(key);
            i++;
        }
        return values;
    }

}
