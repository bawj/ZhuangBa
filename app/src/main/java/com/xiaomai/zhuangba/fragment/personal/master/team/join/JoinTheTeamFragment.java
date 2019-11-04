package com.xiaomai.zhuangba.fragment.personal.master.team.join;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.JoinTheTeamAdapter;
import com.xiaomai.zhuangba.data.bean.TeamJoinedBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.employer.BusinessLicenseFragment;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.AuthenticationDialog;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 * <p>
 * 我加入的团队 列表
 */
public class JoinTheTeamFragment extends BaseListFragment {

    private static final String TITLE = "title";
    private List<TeamJoinedBean> teamList;
    private JoinTheTeamAdapter teamJoinedAdapter;

    public static JoinTheTeamFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        JoinTheTeamFragment fragment = new JoinTheTeamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_join_the_team;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        RxUtils.getObservable(ServiceUrl.getUserApi()
                .getTeamPersonnel(String.valueOf(StaticExplain.LEAGUE_MEMBER.getCode())))
                .compose(this.<HttpResult<List<TeamJoinedBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<TeamJoinedBean>>() {
                    @Override
                    protected void onSuccess(List<TeamJoinedBean> teamJoinedBeanList) {
                        teamList = teamJoinedBeanList;
                        teamJoinedAdapter.setNewData(teamJoinedBeanList);
                        finishRefresh();
                        loadMoreEnd();
                    }
                });
    }

    @Override
    public void rightTitleClick(View v) {
        AuthenticationDialog.getInstance().initView(getActivity())
                .setTvAuthenticationContent(getString(R.string.exit_the_team))
                .setIvAuthenticationLog(R.drawable.ic_shape_log)
                .setTvDialogVersionOk(getString(R.string.confirm))
                .setICallBase(new AuthenticationDialog.BaseCallback() {
                    @Override
                    public void ok() {
                        exitTeam();
                    }
                }).showDialog();
    }

    private void exitTeam() {
        String phone = "";
        if (teamList != null) {
            for (TeamJoinedBean teamJoinedBean : teamList) {
                int id = teamJoinedBean.getId();
                if (id == 1) {
                    phone = teamJoinedBean.getUsernumber();
                }
            }
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(getString(R.string.phone_is_not_null));
        } else {
            RxUtils.getObservable(ServiceUrl.getUserApi().dropOutTeam(phone))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        protected void onSuccess(Object response) {
                            startFragment(MasterWorkerFragment.newInstance());
                        }
                    });
        }
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        return teamJoinedAdapter = new JoinTheTeamAdapter();
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.exit_team);
    }

    @Override
    protected String getActivityTitle() {
        return getTitle();
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
