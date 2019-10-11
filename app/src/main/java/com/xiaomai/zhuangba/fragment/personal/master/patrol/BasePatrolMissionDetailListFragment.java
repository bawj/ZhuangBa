package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PatrolMissionDetailListAdapter;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 * 巡查 详情 base
 */
public class BasePatrolMissionDetailListFragment extends BaseListFragment {

    private PatrolMissionDetailListAdapter patrolMissionDetailListAdapter;

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

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_mission_detail_list;
    }

    private void requestPatrolMission() {
        RxUtils.getObservable(getObservable())
                .compose(this.<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<PatrolMissionDetailListBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<PatrolMissionDetailListBean> patrolMissionDetailListBeanList) {
                        requestSuccess(patrolMissionDetailListBeanList.getList());
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });

    }

    private void requestSuccess(List<PatrolMissionDetailListBean> patrolMissionDetailListBeanList) {
        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
            //加载
            loadSuccess(patrolMissionDetailListBeanList);
        } else {
            refreshSuccess(patrolMissionDetailListBeanList);
            finishRefresh();
        }
        if (patrolMissionDetailListBeanList.size() < StaticExplain.PAGE_NUM.getCode()) {
            //加载结束
            loadMoreEnd();
        } else {
            //加载完成
            loadMoreComplete();
        }
    }

    private void refreshSuccess(List<PatrolMissionDetailListBean> patrolBeanList) {
        patrolMissionDetailListAdapter.setNewData(patrolBeanList);
    }

    private void loadSuccess(List<PatrolMissionDetailListBean> patrolBeanList) {
        patrolMissionDetailListAdapter.addData(patrolBeanList);
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        return patrolMissionDetailListAdapter = new PatrolMissionDetailListAdapter();
    }

    @Override
    protected String getActivityTitle() {
        return getTitle() == null ? "" : getTitle();
    }

    public String getDetailNo() {
        return null;
    }

    public String getTitle(){
        return null;
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    public Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> getObservable(){
        return null;
    }

}
