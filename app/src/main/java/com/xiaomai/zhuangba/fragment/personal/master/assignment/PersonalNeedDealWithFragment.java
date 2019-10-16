package com.xiaomai.zhuangba.fragment.personal.master.assignment;

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
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 * <p>
 * 分配任务 安装单
 */
public class PersonalNeedDealWithFragment extends BaseListFragment {

    private static final String PHONE = "phone";
    private PersonalNeedDealWithAdapter personalNeedDealWithAdapter;

    public static PersonalNeedDealWithFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE , phone);
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
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> ongoingOrdersLists) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            personalNeedDealWithAdapter.addData(ongoingOrdersLists.getList());
                        } else {
                            //刷新
                            personalNeedDealWithAdapter.setNewData(ongoingOrdersLists.getList());
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

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        personalNeedDealWithAdapter = new PersonalNeedDealWithAdapter();
        return personalNeedDealWithAdapter;
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
