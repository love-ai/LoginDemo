package com.ltz.mymvp.feature.register;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.remote.Encrypt;
import com.ltz.mymvp.data.local.RegisterData;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.data.remote.UserInfo;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.feature.common.CommonInteractor;
import com.ltz.mymvp.feature.common.MyCallBack;
import com.ltz.mymvp.network.Network;
import com.ltz.mymvp.network.interceptor.ErrorConsumer;
import com.ltz.mymvp.util.encrypt.DUCEncryptUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by xiaowei on 2018/5/23
 */
@SuppressLint("CheckResult")
public class RegisterInteractor extends CommonInteractor implements RegisterContract.Interactor {


    @Override
    public void doRegister(final RegisterData registerData, final CaptchaData captchaData,
                           final RegisterFragment registerFragment, final MyCallBack callBack) {
        //开始网络请求
        if (TextUtils.isEmpty(registerData.mobile.get())) {
            callBack.onFailure("请输入手机号");
            return;
        }
        if (captchaData.isShow && TextUtils.isEmpty(registerData.messageCode.get())) {
            callBack.onFailure("请输入短信验证码");
            return;
        }
        if (TextUtils.isEmpty(registerData.passWord.get())) {
            callBack.onFailure("请输入密码");
            return;
        }

        Network.service().checkMobileCode(registerData.mobile.get(), registerData.messageCode.get())
                .compose(registerFragment.<SimpleInfo>rx())
                .flatMap((Function<SimpleInfo, ObservableSource<Encrypt>>) simpleInfo ->
                        Network.service().getEncrypt().compose(registerFragment.<Encrypt>rx()))
                .doOnNext(encrypt -> {
                    //dosomething
                })
                .flatMap((Function<Encrypt, ObservableSource<UserInfo>>) encrypt -> {
                    String publicKey = DUCEncryptUtil.getRsaPublicKey(encrypt.getEncrypt().getPublic_key());
                    String mobile = DUCEncryptUtil.getEncryptString(registerData.mobile.get(), publicKey);
                    String password = DUCEncryptUtil.getEncryptString(registerData.passWord.get(), publicKey);
                    return Network.service().registerWithMobile(mobile, password, captchaData.getField_value())
                            .compose(registerFragment.<UserInfo>rx());
                })
                .flatMap((Function<UserInfo, ObservableSource<UserProfileInfo>>) userInfo ->
                        Network.service().getUserInfo().compose(registerFragment.<UserProfileInfo>rx()))
                .subscribe(callBack::onSuccess, new ErrorConsumer() {
                    @Override
                    public void onError(int code, String message) {
                        callBack.onFailure(code, message);
                    }
                });
    }

    @Override
    public void sendMessage(RegisterData registerData, CaptchaData captchaData, RegisterFragment registerFragment, final MyCallBack callBack) {
        if (registerData.mobile.get().length() >= 11) {
            Network.service().sendMobileCode(registerData.mobile.get(), captchaData.verify_code, captchaData.getField_value())
                    .compose(registerFragment.<SimpleInfo>rx())
                    .subscribe(callBack::onSuccess, new ErrorConsumer() {
                        @Override
                        public void onError(int code, String message) {
                            callBack.onFailure(code, message);
                        }
                    });
        } else {
            callBack.onFailure("请先输入正确的手机号码");
        }
    }

    @Override
    public void doCountDown(final int count, RegisterFragment registerFragment, final MyCallBack<String> callBack) {
        Flowable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(registerFragment.<Long>bindToLifecycle())
                .doOnNext(aLong -> callBack.onSuccess((count - aLong) + "")).doOnComplete(callBack::onComplete).subscribe();
    }

    @Override
    public void getAudioCaptcha(RegisterData registerData, RegisterFragment registerFragment, final MyCallBack myCallBack) {
        Network.service().getAudioCaptcha(registerData.mobile.get())
                .compose(registerFragment.<SimpleInfo>rx())
                .subscribe(myCallBack::onSuccess, new ErrorConsumer() {
                    @Override
                    public void onError(int code, String message) {
                        myCallBack.onFailure(code, message);
                    }
                });


    }


}
