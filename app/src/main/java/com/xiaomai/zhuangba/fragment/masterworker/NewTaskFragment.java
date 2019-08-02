package com.xiaomai.zhuangba.fragment.masterworker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.NewTaskDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class NewTaskFragment extends BaseMasterEmployerContentFragment{

    public static NewTaskFragment newInstance() {
        Bundle args = new Bundle();
        NewTaskFragment fragment = new NewTaskFragment();
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
        return R.layout.fragment_new_task;
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
    public void update(String code , Handler handler) {
        super.update(code , handler);
        //下拉刷新
        if (StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode().equals(code)){
            iModule.requestMasterNewTaskOrderList();
        }
    }

    @Override
    public void onBaseLoadMoreRequested() {
        super.onBaseLoadMoreRequested();
        //上拉加载
        iModule.requestMasterNewTaskOrderList();
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);
    }

    @Override
    public void onMItemClick(View view, int position) {
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList)
                view.findViewById(R.id.tvItemOrdersTitle).getTag();
        int orderStatus = ongoingOrdersList.getOrderStatus();
        if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()) {
            showToast(getString(R.string.order_expired));
        } else if (orderStatus == OrdersEnum.MASTER_CANCELLED.getCode()) {
            showToast(getString(R.string.order_cancelled));
        } else {
            //新任务详情
            startFragmentForResult(NewTaskDetailFragment.newInstance() , ForResultCode.START_FOR_RESULT_CODE.getCode());
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        Log.e("onFragmentResult requestCode = " + requestCode + "-- resultCode = " + resultCode);

    }
}
