package com.ltz.mymvp.feature.common;

import com.ltz.mymvp.base.BaseFragment;
import com.ltz.mymvp.feature.common.MyCallBack;

/**
 * Created by xiaowei on 2018/5/23
 */
public class CommonContract {
    interface Interactor {
        void refreshCaptcha(BaseFragment baseFragment, MyCallBack callback);
    }
}
