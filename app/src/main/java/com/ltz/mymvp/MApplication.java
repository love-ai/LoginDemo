package com.ltz.mymvp;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


/**
 * Created by xiaowei on 2018/5/16
 */
public class MApplication {

    public static Application application = null;
    public static Context context = null;


    public static Context getContext() {
        return context;
    }

    public static Application getApplication() {
        return application;
    }
}
