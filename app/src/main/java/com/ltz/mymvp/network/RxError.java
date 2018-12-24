package com.ltz.mymvp.network;

/**
 * Created by xiaowei on 2018/5/22
 */
public class RxError extends Throwable {
    private int code;
    private String message;

    public RxError(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString() {
        return "RxError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
