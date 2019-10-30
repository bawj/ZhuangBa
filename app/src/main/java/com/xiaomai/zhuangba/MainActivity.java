package com.xiaomai.zhuangba;

import android.os.Bundle;

import com.example.toollib.base.BaseActivity;
import com.example.toollib.http.version.MessageEvent;
import com.example.toollib.http.version.Version;
import com.example.toollib.http.version.VersionEnums;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.AuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.RoleSelectionFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.CertificationSuccessfulFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.guide.GuidePageFragment;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.dialog.UpdateVersionDialog;
import com.xiaomai.zhuangba.weight.dialog.VersionDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Administrator
 * @date 2019/6/24 0024
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.id.frameLayoutMain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.tool_lib_QMUI_AppTheme);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            boolean aBoolean = SPUtils.getInstance().getBoolean(SpfConst.FIRST_ENTRY, true);
            if (aBoolean) {
                init(GuidePageFragment.newInstance());
                SPUtils.getInstance().put(SpfConst.FIRST_ENTRY, false);
            } else {
                UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                if (userInfo == null) {
                    init(LoginFragment.newInstance());
                } else {
                    yesLogin(userInfo);
                }
            }
        }
    }

    /**
     * 已登录
     *
     * @param userInfo 用户信息
     */
    private void yesLogin(UserInfo userInfo) {
        //角色;0:师傅;1:雇主
        int authenticationStatue = userInfo.getAuthenticationStatue();
        if (authenticationStatue == StaticExplain.NO_CERTIFICATION.getCode()) {
            //未选择角色
            startFragment(RoleSelectionFragment.newInstance());
        } else {
            //已选择角色
            String role = userInfo.getRole();
            if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                //雇主端
                startFragment(EmployerFragment.newInstance());
                ///authentication(userInfo, EmployerFragment.newInstance());
            } else if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                //师傅端
                startFragment(MasterWorkerFragment.newInstance());
                //authentication(userInfo, MasterWorkerFragment.newInstance());
            }
        }
    }

    private void init(QMUIFragment qmuiFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), qmuiFragment, qmuiFragment.getClass().getSimpleName())
                .addToBackStack(qmuiFragment.getClass().getSimpleName())
                .commit();
    }

    private CommonlyDialog commonlyDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        Log.e("code = " + messageEvent.getErrCode());
        //您的登录 信息已过期重新登录
        boolean isTokenOverdueDialog = (messageEvent.getErrCode() == VersionEnums.LOGIN_STATUS.getCode()
                || messageEvent.getErrCode() == VersionEnums.LOGIN_STATUS_.getCode());

        //防止dialog 重复 弹出
        boolean isTokenOverdueDialogs = false;
        if (commonlyDialog != null) {
            isTokenOverdueDialogs = commonlyDialog.isShow();
        }
        //您的账号在其它手机登录
        boolean isOtherMobileLogin = (messageEvent.getErrCode() == VersionEnums.LOGIN_OTHER_STATUS.getCode());
        if (isTokenOverdueDialog && !isTokenOverdueDialogs) {
            logout();
            commonlyDialog = CommonlyDialog.getInstance().initView(this);
            commonlyDialog.setTvDialogCommonlyContent(getString(R.string.re_login_tip))
                    .isVisibleClose(false)
                    .isCancelable()
                    .setICallBase(new CommonlyDialog.BaseCallback() {
                        @Override
                        public void sure() {
                            startFragment(LoginFragment.newInstance());
                        }
                    }).showDialog();
        } else if (isOtherMobileLogin && !isTokenOverdueDialogs) {
            logout();
            commonlyDialog = CommonlyDialog.getInstance().initView(this);
            commonlyDialog.setTvDialogCommonlyContent(getString(R.string.re_login_tip_))
                    .isVisibleClose(false)
                    .isCancelable()
                    .setICallBase(new CommonlyDialog.BaseCallback() {
                        @Override
                        public void sure() {
                            startFragment(LoginFragment.newInstance());
                        }
                    }).showDialog();
        } else if (messageEvent.getErrCode() == VersionEnums.APP_UPDATE.getCode()) {
            //更新
            final Version version = messageEvent.getVersion();
            if (version != null) {
                VersionDialog.getInstance().initView(this, version)
                        .setICallBase(new VersionDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                //立即更新
                                startUpdateVersion(version.getVersionUrl());
                            }
                        }).showDialog();
            }
        }
    }

    private void logout() {
        Util.logout();
    }

    private void startUpdateVersion(String downLoadUrl) {
        UpdateVersionDialog.getInstance().initView(this, downLoadUrl)
                .showDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long touchTime = 0;
    private static final long WAIT_TIME = 2000L;

    @Override
    public void onBackPressed() {
        QMUIFragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof LoginFragment || currentFragment instanceof MasterWorkerFragment
                || currentFragment instanceof EmployerFragment || currentFragment instanceof AuthenticationFragment
                || currentFragment instanceof CertificationSuccessfulFragment || currentFragment instanceof RoleSelectionFragment) {
            if (System.currentTimeMillis() - touchTime < WAIT_TIME) {
                finish();
            } else {
                touchTime = System.currentTimeMillis();
                ToastUtil.showShort(getString(R.string.press_again_exit));
            }
        } else {
            super.onBackPressed();
        }
    }


}
