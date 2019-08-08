package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * 新任务 订单详情
 */
public class NewTaskDetailFragment extends BaseMasterOrderDetailFragment {

    /**
     * 请在13:50前确认订单
     */
    @BindView(R.id.tvConfirmationOfOrder)
    TextView tvConfirmationOfOrder;
    /**
     * 标题
     */
    @BindView(R.id.topNewTaskTitle)
    QMUITopBarLayout topNewTaskTitle;

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

    /**
     * 请在 16:56 之前接单
     */
    public static final String EXPIRE_TIME = "expire_time";

    public static NewTaskDetailFragment newInstance(String orderCode, String expireTime) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(EXPIRE_TIME, expireTime);
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
        statusBarWhite();
        topNewTaskTitle.setTitle(getString(R.string.order_detail));
        topNewTaskTitle.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        refreshBaseList.setHeaderInsetStart(76);
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

    @OnClick({R.id.btnNewTaskCancelTheOrder, R.id.btnNewTaskReceipt})
    public void onViewNewTaskClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNewTaskCancelTheOrder:
                //取消接单
                CommonlyDialog.getInstance().initView(getActivity())
                        .setTvDialogCommonlyContent(getString(R.string.cancel_order_tip))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                iModule.requestCancelOrder();
                            }
                        }).showDialog();
                break;
            case R.id.btnNewTaskReceipt:
                //接单
                iModule.requestReceiptOrder();
                break;
            default:
        }
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }

    @Override
    public void leftBackClick() {
        statusBarWhite();
        super.leftBackClick();
    }
}
