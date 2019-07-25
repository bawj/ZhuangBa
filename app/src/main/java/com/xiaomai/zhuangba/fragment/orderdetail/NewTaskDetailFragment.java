package com.xiaomai.zhuangba.fragment.orderdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.OngoingOrdersList;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * 新任务 订单详情
 */
public class NewTaskDetailFragment extends BaseMasterOrderDetailFragment {

    /** 请在13:50前确认订单 */
    @BindView(R.id.tvConfirmationOfOrder)
    TextView tvConfirmationOfOrder;
    /**
     * 标题
     */
    @BindView(R.id.topNewTaskTitle)
    QMUITopBarLayout topNewTaskTitle;

    public static NewTaskDetailFragment newInstance() {
        Bundle args = new Bundle();
        NewTaskDetailFragment fragment = new NewTaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_new_task_detail;
    }

    @Override
    public void initView() {
        super.initView();
        topNewTaskTitle.setTitle(getString(R.string.order_detail));
        topNewTaskTitle.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
    }

    @Override
    public void masterOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.masterOngoingOrdersListSuccess(ongoingOrdersList);
        tvConfirmationOfOrder.setText(getString(R.string.waiting_for_orders_, ongoingOrdersList.getExpireTime()));
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
