package com.ltz.mymvp.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ltz.mymvp.R;
import com.ltz.mymvp.base.BaseFragment;
import com.ltz.mymvp.base.baseinterface.OnClickListener;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.databinding.MainFragmentBinding;

/**
 * Created by xiaowei on 2018/6/4
 */
public class MainFragment extends BaseFragment implements MainContract.View ,OnClickListener {
    MainContract.Presenter presenter;
    MainFragmentBinding binding;

    @Override
    public View getContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.main_fragment, null);
        binding = DataBindingUtil.bind(view);
        binding.setListener(this);
        return binding.getRoot();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void showInfo(UserProfileInfo info) {
        binding.setInfo(info);
    }

    @Override
    public void loginOut() {
        getActivity().finish();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_login:
                presenter.exitLogin();
                break;
        }
    }
}
