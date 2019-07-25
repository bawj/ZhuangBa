package com.xiaomai.zhuangba.fragment.personal.set;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.about.AboutFragment;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 设置
 */
public class SetUpFragment extends BaseFragment {

    public static SetUpFragment newInstance() {
        Bundle args = new Bundle();
        SetUpFragment fragment = new SetUpFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
    }

    @OnClick({R.id.relSetUpUpdatePass, R.id.relSetUpAbout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relSetUpUpdatePass:
                //修改密码
                startFragment(UpdatePassFragment.newInstance());
                break;
            case R.id.relSetUpAbout:
                //关于
                startFragment(AboutFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_set_up;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.set_up);
    }
}
