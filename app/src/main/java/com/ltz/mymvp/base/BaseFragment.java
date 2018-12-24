package com.ltz.mymvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.ltz.mymvp.R;
import com.ltz.mymvp.base.baseinterface.LoadingListener;
import com.ltz.mymvp.network.HttpResponse;
import com.ltz.mymvp.util.RxUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.ObservableTransformer;

/**
 * Created by xiaowei on 2018/5/16
 */
public abstract class BaseFragment extends RxFragment implements LoadingListener {

    private ConstraintLayout rootLayout;
    private FrameLayout contentLayout;
    private RelativeLayout progressBarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootLayout = (ConstraintLayout) inflater.inflate(getRootLayoutRes(), container, false);
        contentLayout = rootLayout.findViewById(R.id.contentLayout);
        contentLayout.addView(getContentLayout());
        progressBarLayout = rootLayout.findViewById(R.id.progressBar_layout);
        return rootLayout;
    }


    public abstract View getContentLayout();

    protected int getRootLayoutRes() {
        return R.layout.base_fragment_layout;
    }

    public void showProgress() {
        if(progressBarLayout!=null&&progressBarLayout.getVisibility()==View.INVISIBLE){
            progressBarLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if(progressBarLayout!=null&&progressBarLayout.getVisibility()==View.VISIBLE){
            progressBarLayout.setVisibility(View.INVISIBLE);
        }
    }

    public <T> ObservableTransformer<HttpResponse<T>,T> rx(){
        return RxUtils.rx(this,this);
    }


    public <T> ObservableTransformer<HttpResponse<T>,T> rx_noLoading(){
        return RxUtils.rx(this,null);
    }


    public <T> ObservableTransformer<HttpResponse<T>,T> rx(FragmentEvent event){
        return RxUtils.rx(this,event,this);
    }

    @Override
    public void showLoading() {
        showProgress();
    }
    @Override
    public void hideLoading() {
        hideProgress();
    }


    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

}
