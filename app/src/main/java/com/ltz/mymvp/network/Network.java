package com.ltz.mymvp.network;

import com.ltz.logindemo.StethoUtils;
import com.ltz.logindemo.VariantContants;
import com.ltz.mymvp.network.interceptor.AddheaderIntercepter;
import com.ltz.mymvp.network.interceptor.ReadCookiesInterceptor;
import com.ltz.mymvp.network.interceptor.SaveCookiesInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Network {

    private static ApiService apiService;
    private static final int DEFAULT_TIME_OUT = 30;

    public static ApiService service() {
        if (apiService == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new ReadCookiesInterceptor())
                    .addInterceptor(new SaveCookiesInterceptor())
                    .addInterceptor(new AddheaderIntercepter())
                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

            builder = StethoUtils.addStethoNetWork(builder);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(VariantContants.BASE_URL)
                    .addConverterFactory(CIGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }


}
