package com.ltz.mymvp.feature.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseFragment;
import com.ltz.mymvp.base.baseinterface.OnClickListener;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.local.LoginData;
import com.ltz.mymvp.databinding.LoginFragmentBinding;
import com.ltz.mymvp.util.UIUtils;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by xiaowei on 2018/5/16
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, OnClickListener,EasyPermissions.PermissionCallbacks {

    LoginContract.Presenter mPresenter;
    public LoginData loginData;
    public CaptchaData captchaData;
    private LoginFragmentBinding loginFragmentBinding;

    @Override
    public View getContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.login_fragment, null);
        loginFragmentBinding = DataBindingUtil.bind(view);
        return loginFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginData = new LoginData();
//        loginData.userName.set("15607165560");
//        loginData.passWord.set("12345678");
        captchaData = new CaptchaData();
        loginFragmentBinding.setLoginData(loginData);
        loginFragmentBinding.setListener(this);
        loginFragmentBinding.setCaptchaData(captchaData);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void refreshCaptcha(CaptchaData captcha) {
        captchaData = captcha;
        loginFragmentBinding.setCaptchaData(captchaData);
    }

    @Override
    public void gotoMain() {
        UIUtils.gotoMain(getActivity());
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void closeKeyBoard() {
        UIUtils.closeKeyBoard(getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                mPresenter.login(loginData, captchaData);
                break;
            case R.id.iv_captcha:
                mPresenter.refreshCaptcha();
                break;
            case R.id.do_login:
                UIUtils.gotoRegister(getActivity());
                break;
            case R.id.load:
                mPresenter.loadPatch();
                break;
            case R.id.show_tinker_info:
                mPresenter.showTinkerInfo(getActivity());
                break;
            case R.id.clear_patch:
                mPresenter.clearPatch();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        mPresenter.loadPatch();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
