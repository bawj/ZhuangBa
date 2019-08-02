package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.ProcessedDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 *
 * 需处理
 */
public class NeedDealWithFragment extends BaseMasterEmployerContentFragment {

    public static NeedDealWithFragment newInstance() {
        Bundle args = new Bundle();
        NeedDealWithFragment fragment = new NeedDealWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        Log.e("需处理 initView");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_need_deal_with;
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
        if (StringTypeExplain.REFRESH_NEED_DEAL_WITH_FRAGMENT.getCode().equals(code)){
            iModule.requestOngoingOrders();
        }
    }

    @Override
    public void onMItemClick(View view, int position) {
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        if (ongoingOrdersList.getOrderStatus() == OrdersEnum.MASTER_PENDING_DISPOSAL.getCode()){
            //待处理
            startFragment(ProcessedDetailFragment.newInstance(ongoingOrdersList.getOrderCode()));
        }
    }

    @Override
    public void onBaseLoadMoreRequested() {
        super.onBaseLoadMoreRequested();
        iModule.requestOngoingOrders();
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);

    }
}
