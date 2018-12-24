package com.ltz.mymvp.network;


import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.remote.Encrypt;
import com.ltz.mymvp.data.remote.SimpleInfo;
import com.ltz.mymvp.data.remote.UserInfo;
import com.ltz.mymvp.data.local.UserProfileInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/uc/get_key")
    Observable<HttpResponse<Encrypt>> getEncrypt();

    @POST("api/uc/captcha_access")
    Observable<HttpResponse<CaptchaData>> getCaptcha();

    @FormUrlEncoded
    @POST("api/uc/login")
    Observable<HttpResponse<UserInfo>> login(@Field("name") String mobile,
                                             @Field("password") String password,
                                             @Field("_encrypt_code") String encryptCode,
                                             @Field("verify_code") String verify_code,
                                             @Field("_captcha_code") String captcha_code);

    @GET("api/user/info/profile")
    Observable<HttpResponse<UserProfileInfo>> getUserInfo();

    @POST("api/uc/logout")
    Observable<HttpResponse<SimpleInfo>> logout();


    @FormUrlEncoded
    @POST("api/uc/register_send_mobile_code")
    Observable<HttpResponse<SimpleInfo>> sendMobileCode(@Field("mobile") String mobile,
                                                        @Field("verify_code") String verify_code,
                                                        @Field("_captcha_code") String captchaCode);

    @FormUrlEncoded
    @POST("api/uc/register_check_mobile_code")
    Observable<HttpResponse<SimpleInfo>> checkMobileCode(@Field("mobile") String mobile,
                                                         @Field("vcode") String messageCode);

    @FormUrlEncoded
    @POST("api/uc/register_only_with_mobile")
    Observable<HttpResponse<UserInfo>> registerWithMobile(@Field("mobile") String mobile,
                                                          @Field("password") String password,
                                                          @Field("_encrypt_code") String encryptCode);


    @FormUrlEncoded
    @POST("api/uc//register_send_voice_code")
    Observable<HttpResponse<SimpleInfo>> getAudioCaptcha(@Field("mobile") String mobile);


}
