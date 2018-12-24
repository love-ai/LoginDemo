package com.ltz.mymvp.feature.main;

import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/6/4
 */
public class MainPresenter implements MainContract.Presenter {

    private MainFragment mainView;
    private MainInteractor interactor;

    public MainPresenter(MainContract.View view) {
        mainView = (MainFragment) view;
        interactor = new MainInteractor();
        mainView.setPresenter(this);
    }

    @Override
    public void showInfo() {
        interactor.getUserInfo(new MyCallBack<UserProfileInfo>() {
            @Override
            public void onSuccess(UserProfileInfo info) {
                mainView.showInfo(info);
            }
        });
    }

    @Override
    public void exitLogin() {
        interactor.exitLogin(mainView, new MyCallBack<SimpleInfo>() {
            @Override
            public void onSuccess(SimpleInfo simpleInfo) {
                mainView.showToast("退出成功，请重新登陆。");
                mainView.loginOut();
            }

            @Override
            public void onFailure(int code, String msg) {
                mainView.showToast(msg+" "+code);
            }
        });
    }

    @Override
    public void start() {
        showInfo();
    }

    @Override
    public void destroy() {
        mainView = null;
    }
}
