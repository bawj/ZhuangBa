package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PersonalAdvertisingBillsAddTaskAdapter;
import com.xiaomai.zhuangba.adapter.PersonalAdvertisingBillsAddTaskDetail;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 */
public class PersonalAdvertisingBillsAddTaskFragment extends BaseListFragment {

    private static final String PHONE = "phone";
    private PersonalAdvertisingBillsAddTaskAdapter personalAdvertisingBillsAddTaskAdapter;

    public static PersonalAdvertisingBillsAddTaskFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        PersonalAdvertisingBillsAddTaskFragment fragment = new PersonalAdvertisingBillsAddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personal_advertising_bills_add_task;
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
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingOrderByStaff(""
                , String.valueOf(getPage()), String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<AdvertisingBillsBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<AdvertisingBillsBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<AdvertisingBillsBean> ongoingOrdersLists) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            personalAdvertisingBillsAddTaskAdapter.addData(ongoingOrdersLists.getList());
                        } else {
                            //刷新
                            personalAdvertisingBillsAddTaskAdapter.setNewData(ongoingOrdersLists.getList());
                            finishRefresh();
                        }
                        if (ongoingOrdersLists.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
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

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AdvertisingBillsBean advertisingBillsBean = (AdvertisingBillsBean) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        startFragmentForResult(PersonalAdvertisingBillsAddTaskDetail.newInstance(advertisingBillsBean, getPhone()), ForResultCode.START_FOR_RESULT_CODE.getCode());
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //刷新
                refresh();
                setFragmentResult(ForResultCode.RESULT_OK.getCode(), new Intent());
            }
        }
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        personalAdvertisingBillsAddTaskAdapter = new PersonalAdvertisingBillsAddTaskAdapter();
        return personalAdvertisingBillsAddTaskAdapter;
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
