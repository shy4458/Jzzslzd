package com.bksx.jzzslzd.net;

import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/***
 * 网络连接的配置文件，可进行设置URL等配置。
 *
 * @author
 *
 */
public class SxConfig {
    public static final String GATEWAYURL = "gatewayurl";
    public static final String PHOTOPATH = "photoPath";
    public static final String CONNECTTIMEOUT = "connecttimeout";
    public static final String READTIMEOUT = "readtimeout";
    public static String read(Context context, String tag) {
        try {
            AssetManager aManager = context.getAssets();
            InputStream iStream = aManager.open("sx_android_config.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(iStream);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName(tag);
            return items.item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}