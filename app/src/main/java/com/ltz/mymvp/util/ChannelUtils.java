package com.ltz.mymvp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ChannelUtils {

	public static final String KEY_CHANNEL_CODE = "TD_CHANNEL_ID";

	/**
	 * 获取当前渠道代码
	 *
	 * @param context
	 * @return
	 */
	public static String getCurrentChannel(Context context) {
		return getMetaData(context, KEY_CHANNEL_CODE);
	}


	public static boolean isSameChannel(String channelA, String channelB) {
		if (channelA == null || channelB == null) {
			return false;
		}
		return channelA.equals(channelB);
	}

	public static int getCurrentVersionCode(Context context) {
		PackageInfo info = getPackageInfo(context);
		if (info == null) {
			return -1;
		}
		return info.versionCode;
	}

	public static String getCurrentVersionName(Context context) {
		PackageInfo info = getPackageInfo(context);
		if (info == null) {
			return "";
		}
		return info.versionName;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getApplicationContext().getPackageManager();
		String packageName = context.getApplicationContext().getPackageName();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo;
	}


	private static String getMetaData(Context context, String key) {
		String value = "";
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			value = appInfo.metaData.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (value == null) {
			value = "";
		}
		return value;
	}
}
