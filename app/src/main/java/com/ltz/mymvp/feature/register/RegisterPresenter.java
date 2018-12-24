package com.ltz.mymvp.feature.register;

import android.text.Editable;

import com.ltz.mymvp.data.db.DbHelper;
import com.ltz.mymvp.data.local.RegisterData;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/5/21
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterFragment registerView;
    private RegisterInteractor registerInteractor;
    private boolean isSendMessageCode;
    private boolean isRequetAudioCaptcha = false;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = (RegisterFragment) registerView;
        registerInteractor = new RegisterInteractor();
        registerView.setPresenter(this);
    }

    @Override
    public void register(final RegisterData registerData, CaptchaData captchaData) {
        if (!isSendMessageCode) {
            registerView.showToast("请输入正确的图形验证码");
            return;
        }
        registerView.closeKeyBoard();
        registerInteractor.doRegister(registerData, captchaData, registerView, new MyCallBack<UserProfileInfo>() {
            @Override
            public void onSuccess(UserProfileInfo info) {
                if (registerView != null) {
                    DbHelper.saveOrUpdate(info);
                    registerView.gotoMain();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                registerView.showToast(msg + " " + code);
            }

            @Override
            public void onFailure(String msg) {
                registerView.showToast(msg);
            }
        });
    }


    @Override
    public void refreshCaptcha() {
        registerInteractor.refreshCaptcha((RegisterFragment) registerView, new MyCallBack<CaptchaData>() {
            @Override
            public void onSuccess(CaptchaData captchaData) {
                registerView.refreshCaptcha(captchaData);
            }

            @Override
            public void onFailure(int code, String msg) {
                registerView.showToast(msg);
            }
        });
    }

    @Override
    public void sendMessage() {
        registerInteractor.sendMessage(registerView.registerData, registerView.captchaData, registerView, new MyCallBack<SimpleInfo>() {
            @Override
            public void onSuccess(SimpleInfo simpleInfo) {
                registerView.showToast("短信验证码已发送");
                isSendMessageCode = true;
                //开始倒计时
                startCoutDown(60);
            }

            @Override
            public void onFailure(int code, String msg) {
                registerView.showToast(msg + " " + code);
                refreshCaptcha();
            }

            @Override
            public void onFailure(String msg) {
                registerView.showToast(msg);
                refreshCaptcha();
            }
        });
    }

    @Override
    public void getCaptcha() {
        if (!isRequetAudioCaptcha) {
            registerInteractor.getAudioCaptcha(registerView.registerData, registerView, new MyCallBack<SimpleInfo>() {
                @Override
                public void onSuccess(SimpleInfo simpleInfo) {
                    startCoutDown(30);
                    isRequetAudioCaptcha = true;
                    registerView.registerData.clickAble.set(false);
                }

                @Override
                public void onFailure(int code, String msg) {
                    registerView.showToast(msg + " " + code);
                }
            });
        } else {
            registerView.callKefu();
        }

    }

    @Override
    public void startCoutDown(int count) {
        registerInteractor.doCountDown(count, registerView, new MyCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                registerView.registerData.countDownText.set("倒计时" + s + "秒");
            }

            @Override
            public void onComplete() {
                registerView.registerData.clickAble.set(true);
                if (!isRequetAudioCaptcha) {
                    registerView.registerData.countDownText.set("接收语音验证码");
                } else {
                    registerView.registerData.countDownText.set("联系客服");
                }

            }
        });
    }

    @Override
    public void start() {
        registerView.registerFragmentBinding.setPresenter(this);
        refreshCaptcha();
    }

    @Override
    public void destroy() {
        registerView = null;
    }


    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 4) {
            sendMessage();
        }
    }
}
