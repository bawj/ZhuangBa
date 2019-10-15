package com.xiaomai.zhuangba.fragment.personal.master.team;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.TeamJoinedAdapter;
import com.xiaomai.zhuangba.data.bean.TeamJoinedBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 * <p>
 * 我创建的团队
 */
public class TheTeamJoinedFragment extends BaseListFragment {

    private TeamJoinedAdapter teamJoinedAdapter;

    public static TheTeamJoinedFragment newInstance() {
        Bundle args = new Bundle();
        TheTeamJoinedFragment fragment = new TheTeamJoinedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        return teamJoinedAdapter = new TeamJoinedAdapter();
    }

    @Override
    public void onBaseRefresh(final RefreshLayout refreshLayout) {
        RxUtils.getObservable(ServiceUrl.getUserApi()
                .getTeamPersonnel(String.valueOf(StaticExplain.REGIMENTAL_COMMANDER.getCode())))
                .compose(this.<HttpResult<List<TeamJoinedBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<TeamJoinedBean>>() {
                    @Override
                    protected void onSuccess(List<TeamJoinedBean> teamJoinedBeanList) {
                        teamJoinedAdapter.setNewData(teamJoinedBeanList);
                        finishRefresh();
                        loadMoreEnd();
                    }
                });
    }



    @OnClick(R.id.btnInvitedMembers)
    public void onViewClicked() {
        //邀请成员

    }

    @Override
    public void rightTitleClick(View v) {
        //解散团队
        RxUtils.getObservable(ServiceUrl.getUserApi().dissolutionTeam(String.valueOf(StaticExplain.REGIMENTAL_COMMANDER.getCode())))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    public void onNext(HttpResult<Object> httpResult) {
                        super.onNext(httpResult);
                        startFragment(MasterWorkerFragment.newInstance());
                    }

                    @Override
                    protected void onSuccess(Object response) {
                    }
                });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_the_team;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.my_team);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.disbanded_team);
    }

}
