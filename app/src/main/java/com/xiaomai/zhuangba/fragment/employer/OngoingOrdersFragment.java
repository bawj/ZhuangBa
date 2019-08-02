package com.xiaomai.zhuangba.fragment.employer;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * <p>
 * 进行中的订单 列表
 */
public class OngoingOrdersFragment extends BaseMasterEmployerContentFragment {

    public static OngoingOrdersFragment newInstance() {
        Bundle args = new Bundle();
        OngoingOrdersFragment fragment = new OngoingOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void update(String code, Handler handler) {
        super.update(code, handler);
        if (code.equals(StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode())) {
            iModule.employerOrderList();
        }
    }

    @Override
    public void onMItemClick(View view, int position) {
        super.onMItemClick(view, position);
        //进入 订单详情
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_ongoing_orders;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

}
