package com.ltz.mymvp.feature.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseActivity;
import com.ltz.mymvp.util.UIUtils;

/**
 * Created by xiaowei on 2018/5/16
 */
public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager()
                    , registerFragment, R.id.fragment_container);
        }

        new RegisterPresenter(registerFragment);
    }
}
