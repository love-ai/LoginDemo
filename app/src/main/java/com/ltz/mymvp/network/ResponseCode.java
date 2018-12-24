package com.ltz.mymvp.network;

public interface ResponseCode {
    int SUCCESS = 1;
    int ERR_GENERAL_NETWORK = -1;
    int ERR_GENERAL_DATA_ERROR = -2;
    int ERR_SOS = -3;
    int ERR_NOT_LOGIN = -1001;
    int ERR_WRONG_CAPTCHA = -1002;
    int ERR_NEED_AUTHORIZATION = -1003;

    int ERR_ACCOUNT_PASSWORD = -1020;
    int ECODE_NETWORK_TIMEOUT = 10002;

    /**
     * 手机号未注册，用于找回密码第一步，发送验证码。
     */
    int ERR_MOBILE_NOT_REGISTERED = -1021;
    /**
     * 手机号已绑定，用于注册第一步。
     */
    int ERR_REGISTER_MOBILE_REPEAT = -1022;
}
