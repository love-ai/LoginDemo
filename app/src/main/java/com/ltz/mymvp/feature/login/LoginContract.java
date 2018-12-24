package com.ltz.mymvp.feature.login;

import android.content.Context;

import com.ltz.mymvp.base.baseinterface.BasePresenter;
import com.ltz.mymvp.base.baseinterface.BaseView;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.local.LoginData;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/5/17
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void closeKeyBoard();

        void refreshCaptcha(CaptchaData captchaData);

        void gotoMain();
    }


    interface Presenter extends BasePresenter {
        void login(LoginData loginData, CaptchaData captchaData);
        void refreshCaptcha();
        void loadPatch();
        void clearPatch();
        void showTinkerInfo(Context context);
    }

    interface Interactor {
        void doLogin(LoginData loginData, CaptchaData captchaData, LoginFragment loginFragment, MyCallBack callBack);
    }

}
