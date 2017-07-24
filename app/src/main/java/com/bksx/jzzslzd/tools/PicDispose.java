package com.bksx.jzzslzd.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bksx.jzzslzd.common.StaticObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PicDispose {
    Activity activity;
    Bitmap bitmap_pic, compbip;

    public PicDispose(Activity activity) {
        this.activity = activity;
    }

    /**
     * 将一个图片文件进行 缩后的文件名及路径与源文件相同
     *
     * @param picF
     * @param i
     */
    public Bitmap CompressPicFile(File picF, int i) {
        try {
            // 获取图片文件的Bitmap

            if (bitmap_pic != null) {
                bitmap_pic.recycle();
                System.gc();
            }
            if (compbip != null) {
                compbip.recycle();
                System.gc();
            }

            FileInputStream fis = new FileInputStream(picF);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer, 0, buffer.length);
            bitmap_pic = BitmapFactory
                    .decodeByteArray(buffer, 0, buffer.length);

            // 将bitmap压缩
            compbip = Bitmap
                    .createScaledBitmap(bitmap_pic, bitmap_pic.getWidth() / 2,
                            bitmap_pic.getHeight() / 2, true);
            FileOutputStream fos = new FileOutputStream(picF.getAbsolutePath());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            compbip.compress(Bitmap.CompressFormat.JPEG, 50, bos);

            fis.close();
            buffer = null;
            bos.flush();
            bos.close();
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return compbip;
    }

    /**
     * 将一个图片文件进行指定大小的压缩后的文件名及路径与源文件相同
     *
     * @param picF
     * @param size 照片压缩后的大小单位（kb）
     */
    public Bitmap CompressPicFileBySize(File picF, int size) {
        try {
            // 获取图片文件的Bitmap

            if (bitmap_pic != null) {
                bitmap_pic.recycle();
                System.gc();
            }
            if (compbip != null) {
                compbip.recycle();
                System.gc();
            }

            FileInputStream fis = new FileInputStream(picF);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer, 0, buffer.length);
            bitmap_pic = BitmapFactory
                    .decodeByteArray(buffer, 0, buffer.length);
            System.out.println(bitmap_pic.getByteCount());
            StaticObject.print("s:" + bitmap_pic.getByteCount());
            while (bitmap_pic.getByteCount() > 1024 * size * 10) {
                bitmap_pic = Bitmap.createScaledBitmap(bitmap_pic,
                        bitmap_pic.getWidth() * 9 / 10,
                        bitmap_pic.getHeight() * 9 / 10, true);
            }
            StaticObject.print("E:" + bitmap_pic.getByteCount());

            FileOutputStream fos = new FileOutputStream(picF.getAbsolutePath());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap_pic.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            fis.close();
            buffer = null;
            bos.flush();
            bos.close();
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bitmap_pic.getByteCount());
        return bitmap_pic;
    }
}
