package com.ltz.mymvp.feature.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseActivity;
import com.ltz.mymvp.util.UIUtils;

@SuppressLint("SetTextI18n")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager()
                    , mainFragment, R.id.fragment_container);
        }
        new MainPresenter(mainFragment);

    }
}
