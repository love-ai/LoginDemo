package com.ltz.mymvp.data.remote;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ltz.mymvp.BR;

/**
 * Created by xiaowei on 2018/5/21
 */
public class CaptchaData extends BaseObservable {
    private String img_url;
    private String field_name;
    private String field_value;
    public boolean isShow;
    public String verify_code;

    @Bindable
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
        notifyPropertyChanged(BR.img_url);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    @Bindable
    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyPropertyChanged(BR.show);
    }

    @Bindable
    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
        notifyPropertyChanged(BR.verify_code);
    }




    /**
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String url) {
        if(!TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }
    }

}
