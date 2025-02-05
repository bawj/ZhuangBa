package com.xiaomai.zhuangba.fragment.masterworker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NewTaskAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.OrderPoolDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class OrderPoolFragment extends BaseMasterEmployerContentFragment {

    private String address;

    public static OrderPoolFragment newInstance() {
        Bundle args = new Bundle();
        OrderPoolFragment fragment = new OrderPoolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        Log.e("新任务 initView");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_pool;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public void update(String code, String address, Handler handler) {
        super.update(code, address, handler);
        this.address = address;
        Log.d("筛选 = " + address + "-- page = " + getPage());
        //下拉刷新
        if (StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode().equals(code)) {
            iModule.requestMasterNewTaskOrderList();
        }
    }

    @Override
    public void onBaseLoadMoreRequested() {
        Log.d("OrderPoolFragment 上拉加载  page = " + getPage());
        //上拉加载
        iModule.requestMasterNewTaskOrderList();
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);
    }

    @Override
    public BaseQuickAdapter getBaseOrderAdapter() {
        return new NewTaskAdapter();
    }

    @Override
    public int getEmptyView() {
        return R.layout.item_new_task_not_null;
    }

    @Override
    public void onMItemClick(View view, int position) {
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList)
                view.findViewById(R.id.tvItemOrdersTitle).getTag();
        //新任务详情
//        startFragmentForResult(NewTaskDetailFragment.newInstance(ongoingOrdersList.getOrderCode()),
//                ForResultCode.START_FOR_RESULT_CODE.getCode());

        startFragment(OrderPoolDetailFragment.newInstance(ongoingOrdersList.getOrderCode()));
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        Log.d("onFragmentResult requestCode = " + requestCode + "-- resultCode = " + resultCode);
    }

    @Override
    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }
}
