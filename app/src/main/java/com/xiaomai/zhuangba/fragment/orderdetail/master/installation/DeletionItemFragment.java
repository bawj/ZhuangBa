package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DeletionItemAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Bawj
 * CreateDate:     2020/1/6 0006 11:11
 */
public class DeletionItemFragment extends BaseFragment {

    @BindView(R.id.recyclerDeletionItem)
    RecyclerView recyclerDeletionItem;

    public static final String ONGOING_ORDERS_LIST = "ongoing_orders_list";
    public static final String ORDER_SERVICE_ITEMS = "order_service_items";

    private DeletionItemAdapter deletionItemAdapter;

    public static DeletionItemFragment newInstance(String ongoingOrdersList,String orderServiceItems) {
        Bundle args = new Bundle();
        args.putString(ONGOING_ORDERS_LIST, ongoingOrdersList);
        args.putString(ORDER_SERVICE_ITEMS, orderServiceItems);
        DeletionItemFragment fragment = new DeletionItemFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        recyclerDeletionItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<OrderServiceItem> orderServiceItems = getOrderServiceItems();
        deletionItemAdapter = new DeletionItemAdapter();
        recyclerDeletionItem.setAdapter(deletionItemAdapter);
        deletionItemAdapter.setNewData(orderServiceItems);
    }

    @OnClick(R.id.btnSubmitAudit)
    public void onViewClicked() {
        //提交审核
        HashMap<String , Object> hashMap = new HashMap<>();
        //订单编号
        OngoingOrdersList ongoingOrdersList = getOngoingOrdersList();
        hashMap.put("orderCode" , ongoingOrdersList.getOrderCode());
        //服务项目
        List<OrderServicesBean> orderServicesBeans = new ArrayList<>();
        List<OrderServiceItem> data = deletionItemAdapter.getData();
        for (OrderServiceItem datum : data) {
            OrderServicesBean orderServicesBean = new OrderServicesBean();
            orderServicesBean.setOrderCode(ongoingOrdersList.getOrderCode());
            orderServicesBean.setServiceId(String.valueOf(datum.getServiceId()));
            orderServicesBeans.add(orderServicesBean);
        }
        hashMap.put("orderServiceList" , orderServicesBeans);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().initiateCutItem(requestBody))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        ToastUtil.showShort(getString(R.string.commit_success));
                        setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
                        popBackStack();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_deletion_item;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.delete_items_on_site);
    }

    private OngoingOrdersList getOngoingOrdersList() {
        if (getArguments() != null) {
            String s = getArguments().getString(ONGOING_ORDERS_LIST);
            return new Gson().fromJson(s, OngoingOrdersList.class);
        }
        return new OngoingOrdersList();
    }
    private List<OrderServiceItem>  getOrderServiceItems() {
        if (getArguments() != null) {
            String s = getArguments().getString(ORDER_SERVICE_ITEMS);
            return new Gson().fromJson(s, new TypeToken<List<OrderServiceItem>>(){}.getType());
        }
        return new ArrayList<>();
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
