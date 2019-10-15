package com.xiaomai.zhuangba.fragment.personal.master.team.create;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.master.team.join.JoinFragment;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 */
public class CreateJoinFragment extends BaseFragment {

    public static CreateJoinFragment newInstance() {
        Bundle args = new Bundle();
        CreateJoinFragment fragment = new CreateJoinFragment();
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
        return R.layout.fragment_create_join;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.create_team);
    }

    @OnClick({R.id.btnCreateTeam, R.id.btnJoinTheTeam})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateTeam:
                startFragment(CreateTeamFragment.newInstance());
                break;
            case R.id.btnJoinTheTeam:
                startFragment(JoinFragment.newInstance());
                break;
            default:
        }
    }

}
