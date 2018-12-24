package com.ltz.mymvp.feature.register;

import com.ltz.mymvp.base.baseinterface.BasePresenter;
import com.ltz.mymvp.base.baseinterface.BaseView;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.local.RegisterData;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/5/21
 */
public class RegisterContract {
    interface View extends BaseView<Presenter> {
        void closeKeyBoard();

        void refreshCaptcha(CaptchaData captchaData);

        void gotoMain();

        void callKefu();
    }


    interface Presenter extends BasePresenter {
        void register(RegisterData registerData, CaptchaData captchaData);

        void refreshCaptcha();

        void sendMessage();

        void getCaptcha();

        void startCoutDown(int count);
    }

    interface Interactor {
        void doRegister(RegisterData registerData, CaptchaData captchaData, RegisterFragment registerFragment, MyCallBack callBack);
        void sendMessage(RegisterData registerData,CaptchaData captchaData, RegisterFragment registerFragment,MyCallBack callBack);
        void doCountDown(int count,RegisterFragment registerFragment,MyCallBack<String> callBack);
        void getAudioCaptcha(RegisterData registerData,RegisterFragment registerFragment,MyCallBack<SimpleInfo> myCallBack);
    }
}
