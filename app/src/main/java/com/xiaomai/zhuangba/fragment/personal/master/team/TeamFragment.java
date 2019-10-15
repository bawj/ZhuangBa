package com.xiaomai.zhuangba.fragment.personal.master.team;

import android.content.Intent;
import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.Log;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.personal.master.team.create.CreateJoinFragment;

/**
 * @author Administrator
 * @date 2019/10/9 0009
 * <p>
 * 创建团队 或者加入团队
 */
public class TeamFragment extends BaseFragment {

    private static final String STATUS = "status";

    public static TeamFragment newInstance(int status) {
        Bundle args = new Bundle();
        TeamFragment fragment = new TeamFragment();
        args.putInt(STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        int status = getStatus();
        if (status == StaticExplain.NO_TEAM_WAS_CREATED_JOINED.getCode()) {
            //没有创建或者加入团队
            init(CreateJoinFragment.newInstance());
        }
    }
    private void init(QMUIFragment qmuiFragment) {
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, qmuiFragment, qmuiFragment.getClass().getSimpleName())
                    .addToBackStack(qmuiFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ForResultCode.RESULT_OK.getCode()){
            if (resultCode == ForResultCode.RESULT_KEY.getCode()){
                init(TheTeamJoinedFragment.newInstance());
            }
        }
        Log.e("requestCode = " + requestCode + "  resultCode = " + resultCode);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_team;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.my_team);
    }

    public int getStatus() {
        if (getArguments() != null) {
            return getArguments().getInt(STATUS);
        }
        return 0;
    }

}
