package com.ltz.mymvp.feature.login;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.ltz.mymvp.feature.common.CommonInteractor;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.remote.Encrypt;
import com.ltz.mymvp.data.local.LoginData;
import com.ltz.mymvp.data.remote.UserInfo;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.feature.common.MyCallBack;
import com.ltz.mymvp.network.Network;
import com.ltz.mymvp.network.interceptor.ErrorConsumer;
import com.ltz.mymvp.util.encrypt.DUCEncryptUtil;

import java.util.Objects;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by xiaowei on 2018/5/17
 */
@SuppressLint("CheckResult")
public class LoginInteractor extends CommonInteractor implements LoginContract.Interactor {

    /**
     * 登陆
     *
     * @param loginData
     * @param captchaData
     * @param loginFragment
     * @param callBack
     */

    @Override
    public void doLogin(final LoginData loginData, final CaptchaData captchaData,
                        final LoginFragment loginFragment, final MyCallBack callBack) {
        //开始网络请求
        if (TextUtils.isEmpty(loginData.userName.get())) {
            callBack.onFailure("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(loginData.passWord.get())) {
            callBack.onFailure("请输入密码");
            return;
        }
        if (captchaData.isShow && TextUtils.isEmpty(captchaData.getVerify_code())) {
            callBack.onFailure("请输入图形验证码");
            return;
        }
        Network.service().getEncrypt()
                .compose(loginFragment.<Encrypt>rx())
                .flatMap((Function<Encrypt, ObservableSource<UserInfo>>) encrypt -> {
                    String publicKey = DUCEncryptUtil.getRsaPublicKey(encrypt.getEncrypt().getPublic_key());
                    String name = DUCEncryptUtil.getEncryptString(Objects.requireNonNull(loginData.userName.get()), publicKey);
                    String password = DUCEncryptUtil.getEncryptString(Objects.requireNonNull(loginData.passWord.get()), publicKey);
                    return Network.service().login(name, password, encrypt.getEncrypt().getField_value(),
                            captchaData.getVerify_code(), captchaData.getField_value())
                            .compose(loginFragment.<UserInfo>rx());
                })
                .flatMap((Function<UserInfo, ObservableSource<UserProfileInfo>>) userInfo ->
                        Network.service().getUserInfo().compose(loginFragment.<UserProfileInfo>rx()))
                .subscribe(callBack::onSuccess, new ErrorConsumer() {
                    @Override
                    public void onError(int code, String message) {
                        callBack.onFailure(code, message);
                    }
                });
    }
}
