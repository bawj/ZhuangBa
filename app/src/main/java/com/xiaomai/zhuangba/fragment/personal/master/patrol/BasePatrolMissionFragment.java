package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.BasePatrolMissionAdapter;
import com.xiaomai.zhuangba.data.bean.PatrolBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/9 0009
 */
public class BasePatrolMissionFragment extends BaseListFragment {

    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;

    private BasePatrolMissionAdapter basePatrolMissionAdapter;

    public static BasePatrolMissionFragment newInstance() {
        Bundle args = new Bundle();
        BasePatrolMissionFragment fragment = new BasePatrolMissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_patrol_mission;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        requestPatrolMission();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        requestPatrolMission();
    }

    private void requestPatrolMission() {
        RxUtils.getObservable(getObservable())
                .compose(this.<HttpResult<RefreshBaseList<PatrolBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<PatrolBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<PatrolBean> patrolBeanList) {
                        requestSuccess(patrolBeanList.getList());
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });

    }

    private void requestSuccess(List<PatrolBean> patrolBeanList) {
        int page = getPage();
        if (page > StaticExplain.PAGE_NUMBER.getCode()) {
            //加载
            loadSuccess(patrolBeanList);
        } else {
            refreshSuccess(patrolBeanList);
            finishRefresh();
        }
        if (patrolBeanList.size() < StaticExplain.PAGE_NUM.getCode()) {
            //加载结束
            loadMoreEnd();
        } else {
            //加载完成
            loadMoreComplete();
        }
    }

    private void refreshSuccess(List<PatrolBean> patrolBeanList) {
        List<PatrolMissionBean> patrolData = getPatrolData(patrolBeanList);
        basePatrolMissionAdapter.setNewData(patrolData);
    }

    private void loadSuccess(List<PatrolBean> patrolBeanList) {
        List<PatrolMissionBean> patrolData = getPatrolData(patrolBeanList);
        basePatrolMissionAdapter.addData(patrolData);
    }

    private List<PatrolMissionBean> getPatrolData(List<PatrolBean> patrolBeanList) {
        List<PatrolMissionBean> missionBeanList = new ArrayList<>();
        for (PatrolBean patrolBean : patrolBeanList) {
            missionBeanList.add(new PatrolMissionBean(true, patrolBean.getTime(), false));
            List<PatrolBean.TasklistBean> taskList = patrolBean.getTaskList();
            for (PatrolBean.TasklistBean taskListBean : taskList) {
                missionBeanList.add(new PatrolMissionBean(taskListBean));
            }
        }
        return missionBeanList;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        PatrolMissionBean patrolMissionBean = (PatrolMissionBean) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        if (!patrolMissionBean.isHeader) {
            onBasePatrolItemClick(patrolMissionBean);
        }
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        basePatrolMissionAdapter = new BasePatrolMissionAdapter(R.layout.item_base_patrol_mission_child_view
                , R.layout.item_base_patrol_mission_group_view, new ArrayList<PatrolMissionBean>());
        return basePatrolMissionAdapter;
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.no_patrol_duty_today);
    }

    public Observable<HttpResult<RefreshBaseList<PatrolBean>>> getObservable() {
        return null;
    }

    public void onBasePatrolItemClick(PatrolMissionBean patrolMissionBean) {

    }
}
