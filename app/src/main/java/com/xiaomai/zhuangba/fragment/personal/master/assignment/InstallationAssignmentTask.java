package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.InstallationAssignmentTaskAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 */
public class InstallationAssignmentTask extends BaseListFragment implements CompoundButton.OnCheckedChangeListener {

    /**
     * 全选
     */
    @BindView(R.id.chkTaskAllElection)
    CheckBox chkTaskAllElection;
    @BindView(R.id.relInstallationAssignmentTask)
    RelativeLayout relInstallationAssignmentTask;

    private InstallationAssignmentTaskAdapter installationAssignmentTaskAdapter;

    public static InstallationAssignmentTask newInstance() {
        Bundle args = new Bundle();
        InstallationAssignmentTask fragment = new InstallationAssignmentTask();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        chkTaskAllElection.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isChecked()) {
            return;
        }
        List<OngoingOrdersList> data = installationAssignmentTaskAdapter.getData();
        for (OngoingOrdersList datum : data) {
            datum.setCheck(isChecked);
        }
        installationAssignmentTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestOrderListByStaff();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestOrderListByStaff();
    }

    private void requestOrderListByStaff() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderListByStaff(""
                , String.valueOf(getPage()), String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<OngoingOrdersList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<OngoingOrdersList>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> ordersListRefreshBaseList) {
                        relInstallationAssignmentTask.setVisibility(View.VISIBLE);
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            installationAssignmentTaskAdapter.addData(ordersListRefreshBaseList.getList());
                        } else {
                            //刷新
                            installationAssignmentTaskAdapter.setNewData(ordersListRefreshBaseList.getList());
                            finishRefresh();
                        }
                        if (ordersListRefreshBaseList.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
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
        installationAssignmentTaskAdapter = new InstallationAssignmentTaskAdapter();
        installationAssignmentTaskAdapter.setOnCheckedChangeListener(new InstallationAssignmentTaskAdapter.IOnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (!isChecked) {
                    chkTaskAllElection.setChecked(false);
                } else {
                    //查询是否所有的 都选中了
                    boolean check = false;
                    List<OngoingOrdersList> data = installationAssignmentTaskAdapter.getData();
                    for (OngoingOrdersList datum : data) {
                        if (!datum.isCheck()) {
                            check = false;
                            break;
                        }
                        check = true;
                    }
                    chkTaskAllElection.setChecked(check);
                }
            }
        });
        return installationAssignmentTaskAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_installation_assignment_task;
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
        return getString(R.string.assignment_task);
    }

}
