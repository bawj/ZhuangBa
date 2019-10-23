package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PersonalAdvertisingBillsDetailAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 */
public class PersonalAdvertisingBillsDetailFragment extends BaseListFragment {

    private PersonalAdvertisingBillsDetailAdapter personalAdvertisingBillsDetailAdapter;

    public static PersonalAdvertisingBillsDetailFragment newInstance(AdvertisingBillsBean advertisingBillsBean) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantUtil.ADVERTISING_BILLS, advertisingBillsBean);
        PersonalAdvertisingBillsDetailFragment fragment = new PersonalAdvertisingBillsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        getMasterHandleAdvertisingOrderList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        getMasterHandleAdvertisingOrderList();
    }

    private void getMasterHandleAdvertisingOrderList() {
        AdvertisingBillsBean advertisingBillsBean = getAdvertisingBillsBean();
        HashMap<String, String> hashMap = new HashMap<>();
        //省
        hashMap.put("province", advertisingBillsBean.getProvince());
        //市
        hashMap.put("city", advertisingBillsBean.getCity());
        //区
        hashMap.put("district", advertisingBillsBean.getDistrict());
        //街道
        hashMap.put("street", advertisingBillsBean.getStreet());
        //小区
        hashMap.put("villageName", advertisingBillsBean.getVillageName());
        //订单状态
        hashMap.put("orderStatus", "");
        //页数
        hashMap.put("pageNum", String.valueOf(getPage()));
        //一页数量
        hashMap.put("pageSize", String.valueOf(StaticExplain.PAGE_NUM.getCode()));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdvertisingOrderList(requestBody))
                .compose(this.<HttpResult<RefreshBaseList<OngoingOrdersList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<OngoingOrdersList>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> response) {
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            personalAdvertisingBillsDetailAdapter.setNewData(response.getList());
                            finishRefresh();
                        } else {
                            //加载
                            personalAdvertisingBillsDetailAdapter.addData(response.getList());
                        }
                        if (response.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });

    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        personalAdvertisingBillsDetailAdapter = new PersonalAdvertisingBillsDetailAdapter();
        personalAdvertisingBillsDetailAdapter.setOnDelListener(new PersonalAdvertisingBillsDetailAdapter.IOnSwipeListener() {
            @Override
            public void onDel(String orderCode, int pos) {
                delOrder(orderCode, pos);
            }
        });
        return personalAdvertisingBillsDetailAdapter;
    }

    private void delOrder(final String orderCode,final int pos) {
        RxUtils.getObservable(ServiceUrl.getUserApi().deleteOrder(orderCode , String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode())))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        //删除成功
                        List<OngoingOrdersList> data = personalAdvertisingBillsDetailAdapter.getData();
                        if (pos >= 0 && pos < data.size()) {
                            setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
                            data.remove(pos);
                            personalAdvertisingBillsDetailAdapter.notifyItemRemoved(pos);
                            personalAdvertisingBillsDetailAdapter.notifyDataSetChanged();

                            setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
                        }
                    }
                });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personal_advertising_bills_detail;
    }

    private AdvertisingBillsBean getAdvertisingBillsBean() {
        if (getArguments() != null) {
            return (AdvertisingBillsBean) getArguments().getSerializable(ConstantUtil.ADVERTISING_BILLS);
        }
        return new AdvertisingBillsBean();
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
