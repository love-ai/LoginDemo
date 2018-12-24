package com.ltz.mymvp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ltz.mymvp.feature.main.MainActivity;
import com.ltz.mymvp.feature.register.RegisterActivity;

/**
 * Created by xiaowei on 2018/5/16
 */
public class UIUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,int frameId) {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(frameId,fragment);
        transaction.commit();
    }

    public static void gotoMain(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void gotoRegister(Activity activity){
        Intent intent =new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    public static void callPhone(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }


    public static void closeKeyBoard(Activity activity){
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
