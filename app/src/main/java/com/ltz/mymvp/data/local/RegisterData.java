package com.ltz.mymvp.data.local;

import android.databinding.ObservableField;

/**
 * Created by xiaowei on 2018/5/23
 */
public class RegisterData {
    public ObservableField<String> mobile = new ObservableField<>("");
    public ObservableField<String> passWord = new ObservableField<>("");
    public ObservableField<String> messageCode = new ObservableField<>("");
    public ObservableField<String> countDownText = new ObservableField<>("");
    public ObservableField<Boolean> clickAble = new ObservableField<>(false);

}
