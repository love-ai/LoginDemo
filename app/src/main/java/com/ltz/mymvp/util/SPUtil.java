package com.ltz.mymvp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.ltz.mymvp.MApplication;

import java.util.HashSet;

@SuppressLint("CommitPrefEdits")
public class SPUtil {
    public static final String SP_NAME = "ltz";

    public static Editor editor() {
        SharedPreferences preferences = MApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        return editor;
    }

    public static SharedPreferences getPreferences() {
        SharedPreferences preferences = MApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return preferences;
    }


    public static void saveString(String key, String value) {
        editor().putString(key, value).commit();
    }

    public static String getString(String key) {

        String value = getPreferences().getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }


    public static void saveSet(String key, HashSet<String> value) {
        editor().putStringSet(key, value).commit();
    }

    public static void clearCookie() {
        editor().putStringSet("cookie", null).commit();
    }

    public static HashSet<String> getSet(String key) {
        HashSet<String> value = (HashSet<String>) getPreferences().getStringSet(key, new HashSet<String>());
        return value;
    }


    public static void saveBoolean(String key, boolean value) {
        editor().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        boolean value = getPreferences().getBoolean(key, defaultValue);
        return value;
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        int value = getPreferences().getInt(key, defaultValue);
        return value;
    }

    public static void saveInt(String key, int value) {
        editor().putInt(key, value).commit();
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        long value = getPreferences().getLong(key, defaultValue);
        return value;
    }

    public static void saveLong(String key, long value) {
        editor().putLong(key, value).commit();
    }

    public static void remove(String key) {
        if (key == null || key.isEmpty()) {
            return;
        }
        editor().remove(key).commit();
    }
}
