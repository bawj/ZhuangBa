package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.InstallationAssignmentTaskAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 */
public class InstallationAssignmentTask extends BaseListFragment {

    /**
     * 全选
     */
    @BindView(R.id.chkTaskAllElection)
    CheckBox chkTaskAllElection;
    @BindView(R.id.relInstallationAssignmentTask)
    RelativeLayout relInstallationAssignmentTask;

    private static final String PHONE = "phone";
    private InstallationAssignmentTaskAdapter installationAssignmentTaskAdapter;

    public static InstallationAssignmentTask newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        InstallationAssignmentTask fragment = new InstallationAssignmentTask();
        fragment.setArguments(args);
        return fragment;
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
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            installationAssignmentTaskAdapter.addData(ordersListRefreshBaseList.getList());
                        } else {
                            //刷新
                            installationAssignmentTaskAdapter.setNewData(ordersListRefreshBaseList.getList());
                            if (ordersListRefreshBaseList.getList() != null && ordersListRefreshBaseList.getList().size() > 0) {
                                relInstallationAssignmentTask.setVisibility(View.VISIBLE);
                            } else {
                                relInstallationAssignmentTask.setVisibility(View.GONE);
                            }
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
                //查询是否所有的 都选中了
                boolean check = true;
                List<OngoingOrdersList> data = installationAssignmentTaskAdapter.getData();
                for (OngoingOrdersList datum : data) {
                    if (!datum.isCheck()) {
                        check = false;
                        break;
                    }
                }
                chkTaskAllElection.setChecked(check);
            }
        });
        return installationAssignmentTaskAdapter;
    }

    @OnClick({R.id.btnFinishAdding, R.id.chkTaskAllElection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnFinishAdding:
                //分配人
                //订单编号集合
                List<String> orderList = new ArrayList<>();
                List<OngoingOrdersList> data = installationAssignmentTaskAdapter.getData();
                for (OngoingOrdersList datum : data) {
                    boolean check = datum.isCheck();
                    if (check) {
                        String orderCode = datum.getOrderCode();
                        orderList.add(orderCode);
                    }
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("staff", getPhone());
                hashMap.put("orderCodes", orderList);
                hashMap.put("orderType", "1");
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                RxUtils.getObservable(ServiceUrl.getUserApi().addOrder(requestBody))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                refresh();
                                setFragmentResult(ForResultCode.RESULT_OK.getCode(), new Intent());
                            }
                        });
                break;
            case R.id.chkTaskAllElection:
                List<OngoingOrdersList> ongoingOrdersLists = installationAssignmentTaskAdapter.getData();
                for (OngoingOrdersList datum : ongoingOrdersLists) {
                    datum.setCheck(chkTaskAllElection.isChecked());
                }
                installationAssignmentTaskAdapter.notifyDataSetChanged();
                break;
            default:
        }
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

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.assignment_task);
    }
}
