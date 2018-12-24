package com.ltz.logindemo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by xiaowei on 2018/6/4
 */
public class StethoUtils {
    public static void  initStetho(Application application){
        Stetho.initializeWithDefaults(application);
    }

    public static OkHttpClient.Builder addStethoNetWork(OkHttpClient.Builder builder){
        return builder.addNetworkInterceptor(new StethoInterceptor());
    }
}
