package com.ltz.mymvp.feature.login;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.ltz.mymvp.MApplication;
import com.ltz.mymvp.data.db.DbHelper;
import com.ltz.mymvp.data.remote.CaptchaData;
import com.ltz.mymvp.data.local.LoginData;
import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.feature.common.MyCallBack;
import com.ltz.mymvp.tinker.util.Utils;
import com.ltz.mymvp.network.ResponseCode;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by xiaowei on 2018/5/17
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginFragment loginView;
    private LoginInteractor loginInteractor;

    /**
     * 初始化loginview和Interactor
     *
     * @param loginView
     */
    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = (LoginFragment) loginView;
        loginInteractor = new LoginInteractor();
        loginView.setPresenter(this);
    }

    /**
     * 执行登录操作
     *
     * @param loginData
     * @param captchaData
     */
    @Override
    public void login(LoginData loginData, final CaptchaData captchaData) {
        loginView.closeKeyBoard();
        loginInteractor.doLogin(loginData, captchaData, loginView, new MyCallBack<UserProfileInfo>() {
            @Override
            public void onSuccess(UserProfileInfo info) {
                if (loginView != null) {
                    DbHelper.saveOrUpdate(info);
                    loginView.gotoMain();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if (captchaData.isShow() || code == ResponseCode.ERR_WRONG_CAPTCHA) {
                    if (!captchaData.isShow) {
                        loginView.showToast("请输入验证码");
                    } else {
                        loginView.showToast(msg);
                    }
                    refreshCaptcha();
                } else {
                    if (loginView != null && !TextUtils.isEmpty(msg)) {
                        loginView.showToast(msg);
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                if (loginView != null && !TextUtils.isEmpty(msg)) {
                    loginView.showToast(msg);
                }
            }
        });
    }

    /**
     * 刷新二维码
     */
    @Override
    public void refreshCaptcha() {
        loginInteractor.refreshCaptcha(loginView, new MyCallBack<CaptchaData>() {
            @Override
            public void onSuccess(CaptchaData captchaData) {
                loginView.refreshCaptcha(captchaData);
            }

            @Override
            public void onFailure(int code, String msg) {
                loginView.showToast(msg);
            }

        });
    }

    @Override
    public void loadPatch() {
        if (EasyPermissions.hasPermissions(loginView.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //启动SampleResultService
            TinkerInstaller.onReceiveUpgradePatch(MApplication.getContext(),
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed.apk");
        } else {
            EasyPermissions.requestPermissions(loginView, "请允许访问存储设备", 1,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void clearPatch() {
        ShareTinkerInternals.killAllOtherProcess(MApplication.getContext());
        Tinker.with(MApplication.getContext()).cleanPatch();
        new Utils.ScreenState(MApplication.getContext(), new Utils.ScreenState.IOnScreenOff() {
            @Override
            public void onScreenOff() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    @Override
    public void showTinkerInfo(Context context) {
        Utils.showTinkerInfo(context);
    }

    /**
     * 进行初始化地一些操作
     */
    @Override
    public void start() {
        if (loginView.captchaData.isShow) {
            refreshCaptcha();
        }

    }

    @Override
    public void destroy() {
        loginView = null;
    }


}
