package com.xiaomai.zhuangba.fragment.personal.master.team;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.TeamMessageAdapter;
import com.xiaomai.zhuangba.data.bean.TeamMessageBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 *
 * 团队消息
 */
public class TeamMessageFragment extends BaseListFragment {

    private TeamMessageAdapter teamMessageAdapter;

    public static TeamMessageFragment newInstance() {
        Bundle args = new Bundle();
        TeamMessageFragment fragment = new TeamMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestSelectTeamUserByPhone();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestSelectTeamUserByPhone();
    }

    private void requestSelectTeamUserByPhone() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectTeamUserByPhone(unique.getPhoneNumber(),""))
                .compose(this.<HttpResult<List<TeamMessageBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<TeamMessageBean>>() {
                    @Override
                    protected void onSuccess(List<TeamMessageBean> response) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            teamMessageAdapter.addData(response);
                        } else {
                            //刷新
                            teamMessageAdapter.setNewData(response);
                            finishRefresh();
                        }
                        if (response.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        teamMessageAdapter = new TeamMessageAdapter();
        teamMessageAdapter.setOnDelListener(new TeamMessageAdapter.IOnSwipeListener() {
            @Override
            public void isAgree(String isAgree, int pos) {
                updateTeam(isAgree , pos);
            }
        });
        return teamMessageAdapter;
    }

    private void updateTeam(String isAgree,final int pos){
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().updateTeam(phoneNumber,isAgree))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        List<TeamMessageBean> data = teamMessageAdapter.getData();
                        if (pos >= 0 && pos < data.size()) {
                            data.remove(pos);
                            teamMessageAdapter.notifyItemRemoved(pos);
                        }
                    }
                });
    }

    @Override
    public void rightTitleClick(View v) {
        //清空
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        RxUtils.getObservable(ServiceUrl.getUserApi().updateAll(unique.getPhoneNumber(),String.valueOf(StaticExplain.EMPTY.getCode())))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        if (teamMessageAdapter != null){
                            teamMessageAdapter.getData().clear();
                            teamMessageAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.empty);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_team_message;
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
    protected String getActivityTitle() {
        return getString(R.string.system_notification);
    }

}
