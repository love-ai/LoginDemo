package com.ltz.mymvp.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Xml;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xiaowei on 2018/5/22
 */
public class UAUtil {
    public static final String HTTP_UA_BASE = "ltz-app-android";
    private static String UA = null;

    public static String getUA(Context context) {
        if (context == null) {
            return "";
        }
        if (UA != null) {
            return UA;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_UA_BASE);

        sb.append("_").append(Build.BRAND != null ? Build.BRAND.replaceAll("_", "|") : " ");

        sb.append("_").append(Build.MODEL != null ? Build.MODEL.replaceAll("_", "|") : " ");

        sb.append("_").append(Build.VERSION.RELEASE != null ? Build.VERSION.RELEASE.replaceAll("_", "|") : " ");

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        sb.append("_").append(dm.heightPixels).append("*").append(dm.widthPixels);

        sb.append("_").append(ChannelUtils.getCurrentChannel(context));

        String channelName = "";
        try {
            channelName = URLEncoder.encode(channelName, Xml.Encoding.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("_").append(channelName);

        sb.append("_").append(ChannelUtils.getCurrentVersionName(context) + "(" + ChannelUtils.getCurrentVersionCode(context) + ")");

        return sb.toString();
    }
}
