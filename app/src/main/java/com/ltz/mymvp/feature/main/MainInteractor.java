package com.ltz.mymvp.feature.main;

import android.annotation.SuppressLint;

import com.ltz.mymvp.data.db.DbHelper;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.feature.common.MyCallBack;
import com.ltz.mymvp.network.Network;
import com.ltz.mymvp.network.interceptor.ErrorConsumer;

import io.reactivex.functions.Consumer;

/**
 * Created by xiaowei on 2018/6/4
 */
@SuppressLint("CheckResult")
public class MainInteractor implements MainContract.Interactor{

    @Override
    public void getUserInfo(MyCallBack<UserProfileInfo> myCallBack) {
        myCallBack.onSuccess(DbHelper.getUserInfo());
    }

    @Override
    public void exitLogin(MainFragment mainFragment, final MyCallBack<SimpleInfo> myCallBack) {
        Network.service().logout()
                .compose(mainFragment.<SimpleInfo>rx())
                .subscribe(myCallBack::onSuccess, new ErrorConsumer() {
                    @Override
                    public void onError(int code, String message) {
                        myCallBack.onFailure(code,message);
                    }
                });
    }

}
