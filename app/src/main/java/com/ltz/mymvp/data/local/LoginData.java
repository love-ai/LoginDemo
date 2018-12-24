package com.ltz.mymvp.data.local;

import android.databinding.ObservableField;

/**
 * Created by xiaowei on 2018/5/18
 */
public class LoginData {
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> passWord = new ObservableField<>("");
}
