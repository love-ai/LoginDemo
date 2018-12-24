package com.ltz.mymvp.feature.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseActivity;
import com.ltz.mymvp.util.UIUtils;

/**
 * Created by xiaowei on 2018/5/16
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager()
                    , loginFragment, R.id.fragment_container);
        }
        new LoginPresenter(loginFragment);
    }



}
