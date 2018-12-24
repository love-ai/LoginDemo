package com.ltz.mymvp.network.interceptor;

import com.ltz.mymvp.network.ResponseCode;
import com.ltz.mymvp.network.RxError;

import io.reactivex.functions.Consumer;

/**
 * Created by xiaowei on 2018/5/22
 */
public abstract class ErrorConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        if(throwable instanceof RxError){
            int code = ((RxError)throwable).getCode();
            String message = ((RxError)throwable).getMessage();
            onError(code,message);
        }else{
             onError(ResponseCode.ERR_GENERAL_NETWORK,"网络异常，请稍后重试。");
        }
    }

    public abstract void onError(int code, String message);
}
