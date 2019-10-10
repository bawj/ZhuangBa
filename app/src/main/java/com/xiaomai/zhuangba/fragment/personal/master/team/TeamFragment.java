package com.xiaomai.zhuangba.fragment.personal.master.team;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/10/9 0009
 */
public class TeamFragment extends BaseFragment {

    public static TeamFragment newInstance() {
        Bundle args = new Bundle();
        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }
}
