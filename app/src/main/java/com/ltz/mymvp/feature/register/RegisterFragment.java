package com.ltz.mymvp.feature.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseFragment;
import com.ltz.mymvp.base.baseinterface.OnClickListener;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.local.RegisterData;
import com.ltz.mymvp.databinding.RegisterFragmentBinding;
import com.ltz.mymvp.feature.common.MConstants;
import com.ltz.mymvp.util.UIUtils;

/**
 * Created by xiaowei on 2018/5/16
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View, OnClickListener {
    RegisterContract.Presenter presenter;

    public CaptchaData captchaData;
    public RegisterData registerData;
    RegisterFragmentBinding registerFragmentBinding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }


    @Override
    public View getContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.register_fragment, null);
        registerFragmentBinding = DataBindingUtil.bind(view);
        registerFragmentBinding.setListener(this);
        return registerFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        captchaData = new CaptchaData();
        registerData = new RegisterData();
        registerFragmentBinding.setCaptchaData(captchaData);
        registerFragmentBinding.setRegisterData(registerData);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                presenter.register(registerData, captchaData);
                break;
            case R.id.do_login:
                getActivity().finish();
                break;
            case R.id.iv_captcha:
                presenter.refreshCaptcha();
                break;
            case R.id.coutdown_btn:
                presenter.getCaptcha();
                break;
        }
    }

    @Override
    public void closeKeyBoard() {
        UIUtils.closeKeyBoard(getActivity());
    }


    @Override
    public void refreshCaptcha(CaptchaData captcha) {
        captchaData = captcha;
        registerFragmentBinding.setCaptchaData(captchaData);
    }

    @Override
    public void gotoMain() {
        UIUtils.gotoMain(getActivity());
    }

    @Override
    public void callKefu() {
        UIUtils.callPhone(getActivity(), MConstants.KEFU_TEL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.destroy();
    }

}
