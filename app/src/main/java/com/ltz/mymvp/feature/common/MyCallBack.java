package com.ltz.mymvp.feature.common;

/**
 * Created by xiaowei on 2018/5/23
 */
public abstract class MyCallBack<T> {

    public void onFailure(String msg){

    }

    public abstract void onSuccess(T t);

    public  void onFailure(int code, String msg){

    }

    public void onComplete(){

    }
}
