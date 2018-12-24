package com.ltz.mymvp.network.interceptor;

import com.ltz.logindemo.VariantContants;
import com.ltz.mymvp.MApplication;
import com.ltz.mymvp.util.UAUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddheaderIntercepter implements Interceptor{
    protected static final String KEY_REFERER = "Referer";
    protected static final String KEY_USER_AGENT = "User-agent";
    protected static final String REFERER = VariantContants.BASE_URL + "api/";
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                .addHeader(KEY_USER_AGENT, UAUtil.getUA(MApplication.getContext()))
                .addHeader(KEY_REFERER,REFERER)
                .build();
        return chain.proceed(request);
    }
}
