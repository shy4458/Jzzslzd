package com.bksx.jzzslzd.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bksx.jzzslzd.bo.IDCard;
import com.bksx.jzzslzd.common.StaticObject;
import com.bksx.jzzslzd.net.SxConfig;
import com.handheld.IDCard.IDCardManager;
import com.handheld.IDCard.IDCardModel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 身份证读取控制器
 * 1初始化一个读卡线程
 * 2需要读卡是调用read方法
 * 3通过handler返回code=100的IDcard对象
 * 4销毁线程
 * Created by user on 2017/7/5.
 */

public class ReadCardControler {
    private boolean isrunning; //线程是否在运行
    private boolean isread; //是否需要进行读卡
    private IDCardManager manager; //身份证读卡器
    private Handler handler; //返回handler
    private Context context;
    private ReadCard readCard;
    private static ReadCardControler controler;

    private ReadCardControler() {
        StaticObject.print("读卡工具初始化...");
    }

    /**
     * 构造方法
     *
     * @param context
     * @param handler
     */
    public static ReadCardControler getInstance(Context context, Handler handler) {
        StaticObject.print("读卡工具类初始化...");
        if (controler == null)
            controler = new ReadCardControler();
        controler.handler = handler;
        controler.context = context;
        if (controler.manager == null)
            controler.manager = new IDCardManager(context);
        controler.isrunning = true;
        controler.isread = false;

        return controler;

    }

    /**
     * 关闭
     */
    public void close() {
        StaticObject.print("读卡工具类关闭...");
        controler.isread = false;
        controler.isrunning = false;
        controler.manager.Close();
        controler.manager = null;
        controler.readCard = null;

    }

    public void read() {
        StaticObject.print("开始读卡...");
        if (controler.readCard == null)
            controler.readCard = new ReadCard();

        if (!controler.readCard.isAlive())
            controler.readCard.start();
        controler.isread = true;


    }


    private void callBack(IDCardModel model) {
        String photo_path = "";
        if (model.getPhotoBitmap() != null) {
            System.gc();
            @SuppressLint("SdCardPath")
            String filePath = SxConfig.read(context, SxConfig.PHOTOPATH);
            // 如果目录不存在创建目录
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            photo_path = filePath + "/"
                    + StaticObject.Md5(model.getName() + model.getIDCardNumber())
                    + ".jpg";
            // 保存照片
            bitmapToFile(model.getPhotoBitmap(), photo_path);
        }

        IDCard card = new IDCard();
        card.setXm(model.getName());
        card.setXb(model.getSex());
        card.setMz(model.getNation());
        card.setCsrq(model.getYear() + "-" + model.getMonth() + "-" + model.getDay());
        card.setHjdz(model.getAddress());
        card.setZjhm(model.getIDCardNumber());
        card.setPhotoPath(photo_path);


        Message msg = Message.obtain();
        msg.what = 110;
        msg.obj = card;
        controler.handler.sendMessage(msg);
    }

    /**
     * 将bitmap转换成jpg
     *
     * @param bitmap bitmap资源
     * @param path   保存后jpg的路径及名字
     */
    private void bitmapToFile(Bitmap bitmap, String path) {
        File f = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ReadCard extends Thread {
        @Override
        public void run() {
            StaticObject.print("读卡线程启动...");
            while (controler.isrunning) {
                if (controler.isread && controler.manager != null) {
                    if (controler.manager.FindCard(1000)) {
                        IDCardModel model = null;
                        model = controler.manager.GetData(3000);
                        if (model != null) {
                            controler.isread = false;
                            callBack(model);

                        }
                    }
                }
            }
        }
    }
}
