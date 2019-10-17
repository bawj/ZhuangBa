package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PersonalNeedDealWithAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 * <p>
 * 分配任务 安装单
 */
public class PersonalNeedDealWithFragment extends BaseListFragment {

    private static final String PHONE = "phone";

    private List<OngoingOrdersList> ongoingOrdersLists;
    private PersonalNeedDealWithAdapter personalNeedDealWithAdapter;

    public static PersonalNeedDealWithFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        PersonalNeedDealWithFragment fragment = new PersonalNeedDealWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personal_need_deal_with;
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
        String phone = getPhone();
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderListByStaff(phone
                , String.valueOf(getPage()), String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<OngoingOrdersList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<OngoingOrdersList>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> ordersListRefreshBaseList) {
                        ongoingOrdersLists = ordersListRefreshBaseList.getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            personalNeedDealWithAdapter.addData(ongoingOrdersLists);
                        } else {
                            //刷新
                            personalNeedDealWithAdapter.setNewData(ongoingOrdersLists);
                            finishRefresh();
                        }
                        if (ongoingOrdersLists.size() < StaticExplain.PAGE_NUM.getCode()) {
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
        personalNeedDealWithAdapter = new PersonalNeedDealWithAdapter();
        personalNeedDealWithAdapter.setOnDelListener(new PersonalNeedDealWithAdapter.IOnSwipeListener() {
            @Override
            public void onDel(String orderCode, int pos) {
                delOrder(orderCode, pos);
            }
        });
        return personalNeedDealWithAdapter;
    }

    private void delOrder(String orderCode, final int pos) {
        RxUtils.getObservable(ServiceUrl.getUserApi().deleteOrder(orderCode, String.valueOf(StaticExplain.INSTALLATION_LIST.getCode())))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        //删除成功
                        if (pos >= 0 && pos < ongoingOrdersLists.size()) {
                            setFragmentResult(ForResultCode.RESULT_OK.getCode(), new Intent());
                            ongoingOrdersLists.remove(pos);
                            personalNeedDealWithAdapter.notifyItemRemoved(pos);
                        }
                    }
                });
    }

    @OnClick(R.id.btnAddTask)
    public void onViewClicked() {
        //安装单 添加任务
        startFragmentForResult(InstallationAssignmentTask.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //刷新
                refresh();
            }
        }
    }

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
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
    public boolean isCustomView() {
        return false;
    }

}
