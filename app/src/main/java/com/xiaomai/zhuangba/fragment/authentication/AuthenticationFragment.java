package com.xiaomai.zhuangba.fragment.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.MasterAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.PricingSheetFragment;
import com.xiaomai.zhuangba.fragment.personal.employer.EmployerPersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.master.MasterPersonalFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.UserInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 认证
 */
public class AuthenticationFragment extends BaseFragment {

    @BindView(R.id.ivUserHead)
    ImageView ivUserHead;

    public static AuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        AuthenticationFragment fragment = new AuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_authentication;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @OnClick({R.id.ivAuthenticationBack, R.id.ivUserHead, R.id.btnRectangle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAuthenticationBack:
                startFragment(RoleSelectionFragment.newInstance());
                break;
            case R.id.ivUserHead:
                String role = UserInfoUtil.getRole();
                if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                    //师傅端
                    startFragment(MasterPersonalFragment.newInstance(getString(R.string.zero) , getString(R.string.zero)));
                } else if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                    //雇主端
                    startFragment(EmployerPersonalFragment.newInstance());
                }
                break;
            case R.id.btnRectangle:
                startFragment(PricingSheetFragment.newInstance());
                break;
            default:
        }
    }


    public void findAuthentication() {
        final Observable<HttpResult<UserInfo>> user = ServiceUrl.getUserApi().getUser();
        RxUtils.getObservable(user)
                .compose(this.<HttpResult<UserInfo>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                    @Override
                    protected void onSuccess(UserInfo userInfo) {
                        DBHelper.getInstance().getUserInfoDao().deleteAll();
                        DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                        GlideManager.loadCircleImage(getActivity(), userInfo.getBareHeadedPhotoUrl(), ivUserHead);
                        findAuthenticationSuccess(userInfo);
                    }
                });
    }

    public void findAuthenticationSuccess(UserInfo userInfo) {
        startFragment(EmployerFragment.newInstance());
    }

}
