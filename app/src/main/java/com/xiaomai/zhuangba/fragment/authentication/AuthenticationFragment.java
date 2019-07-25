package com.xiaomai.zhuangba.fragment.authentication;

import android.os.Bundle;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 *
 * 认证
 */
public class AuthenticationFragment extends BaseFragment {

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
    protected String getActivityTitle() {
        return null;
    }
}
