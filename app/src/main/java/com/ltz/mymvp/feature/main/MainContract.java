package com.ltz.mymvp.feature.main;

import com.ltz.mymvp.base.baseinterface.BasePresenter;
import com.ltz.mymvp.base.baseinterface.BaseView;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/6/4
 */
public class MainContract {

    interface View extends BaseView<Presenter>{
        void showInfo(UserProfileInfo info);
        void loginOut();
    }

    interface Presenter extends BasePresenter{
       void showInfo();
       void exitLogin();
    }

    interface Interactor{
        void getUserInfo(MyCallBack<UserProfileInfo> myCallBack);
        void exitLogin(MainFragment mainFragment, MyCallBack<SimpleInfo> callBack);
    }
}
