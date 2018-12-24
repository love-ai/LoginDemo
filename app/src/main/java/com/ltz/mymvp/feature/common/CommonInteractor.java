package com.ltz.mymvp.feature.common;

import android.annotation.SuppressLint;

import com.ltz.mymvp.base.BaseFragment;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.network.Network;
import com.ltz.mymvp.network.interceptor.ErrorConsumer;

import io.reactivex.functions.Consumer;

/**
 * Created by xiaowei on 2018/5/23
 */
public class CommonInteractor implements CommonContract.Interactor {
    @SuppressLint("CheckResult")
    @Override
    public void refreshCaptcha(BaseFragment baseFragment, final MyCallBack callback) {
        Network.service().getCaptcha()
                .compose(baseFragment.<CaptchaData>rx())
                .subscribe(captchaData -> {
                    captchaData.setShow(true);
                    callback.onSuccess(captchaData);
                }, new ErrorConsumer() {
                    @Override
                    public void onError(int code, String message) {
                        callback.onFailure(code, message);
                    }
                });
    }
}
