package com.ltz.mymvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ltz.mymvp.tinker.util.Utils;

/**
 * Created by xiaowei on 2018/5/16
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.setBackground(false);
    }
}
