package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.AcceptedOrdersFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.EmployerCompleteFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.EmployerDistributionFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.EmployerHavingSetOutFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.EmployerUnderConstructionFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.BeUnderConstructionFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.HavingSetOutFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.MasterCompleteFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.NewTaskDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.StartTheMissionFragment;

/**
 * @author Administrator
 * @date 2019/7/8 0008
 */
public class OrderStatusUtil {

    /**
     * 师傅端
     *
     * @param mContext         context
     * @param orderStatus      状态
     * @param tvItemOrdersType textView
     */
    public static void masterStatus(Context mContext, int orderStatus, TextView tvItemOrdersType) {
        if (orderStatus == OrdersEnum.MASTER_NEW_TASK.getCode()) {
            //新任务
            tvItemOrdersType.setText(mContext.getString(R.string.new_task));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == OrdersEnum.MASTER_PENDING_DISPOSAL.getCode()) {
            //已接单
            tvItemOrdersType.setText(mContext.getString(R.string.received_orders));
            tvItemOrdersType.setBackgroundResource(R.drawable.pending_disposal_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_IN_PROCESSING.getCode()) {
            //已出发
            tvItemOrdersType.setText(mContext.getString(R.string.having_set_out));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE.getCode()) {
            //施工中
            tvItemOrdersType.setText(mContext.getString(R.string.be_under_construction));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_COMPLETED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()) {
            //已过期
            tvItemOrdersType.setText(mContext.getString(R.string.expired_));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //师傅取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }


    /**
     * 雇主端
     *
     * @param mContext         context
     * @param orderStatus      状态
     * @param tvItemOrdersType textView
     */
    public static void employerStatus(Context mContext, int orderStatus, TextView tvItemOrdersType) {
        if (orderStatus == OrdersEnum.EMPLOYER_IN_DISTRIBUTION.getCode()) {
            //分配中
            tvItemOrdersType.setText(mContext.getString(R.string.in_distribution));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_RECEIVED_ORDERS.getCode()) {
            //已接单
            tvItemOrdersType.setText(mContext.getString(R.string.received_orders));
            tvItemOrdersType.setBackgroundResource(R.drawable.received_orders_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_IN_PROCESSING.getCode()) {
            //已出发
            tvItemOrdersType.setText(mContext.getString(R.string.having_set_out));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_CHECK_AND_ACCEPT.getCode()) {
            //施工中
            tvItemOrdersType.setText(mContext.getString(R.string.be_under_construction));
            tvItemOrdersType.setBackgroundResource(R.drawable.check_accept_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_UNPAID.getCode()) {
            //未支付
            tvItemOrdersType.setText(mContext.getString(R.string.unpaid));
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED_CANCEL.getCode()) {
            //师傅取消订单
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }

    /**
     * 师傅端跳转到订单详情
     *
     * @param qmuiFragment baseFragment
     * @param orderCode    订单编号
     * @param orderStatus  订单状态
     * @param expireTime   最晚确认接单时间
     */
    public static void startMasterOrderDetail(QMUIFragmentActivity qmuiFragment, String orderCode, int orderStatus, String... expireTime) {
        if (orderStatus == OrdersEnum.MASTER_NEW_TASK.getCode()) {
            String expireDate = "";
            if (expireTime != null && expireTime.length > 0) {
                expireDate = expireTime[0];
            }
            //新任务
            qmuiFragment.startFragment(NewTaskDetailFragment.newInstance(orderCode, expireDate));
        } else if (orderStatus == OrdersEnum.MASTER_PENDING_DISPOSAL.getCode()) {
            //已接单
            qmuiFragment.startFragment(StartTheMissionFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.MASTER_IN_PROCESSING.getCode()) {
            //已出发
            qmuiFragment.startFragment(HavingSetOutFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE.getCode()) {
            //施工中
            qmuiFragment.startFragment(BeUnderConstructionFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.MASTER_COMPLETED.getCode()) {
            //雇主取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        } else if (orderStatus == OrdersEnum.MASTER_CANCELLED.getCode()) {
            //已完成
            qmuiFragment.startFragment(MasterCompleteFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()) {
            //已过期
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_expired));
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //师傅取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        }
    }


    /**
     * 雇主端跳转到订单详情
     *
     * @param qmuiFragment baseFragment
     * @param orderCode    订单编号
     * @param orderStatus  订单状态
     */
    public static void startEmployerOrderDetail(QMUIFragmentActivity qmuiFragment, String orderCode, int orderStatus) {
        if (orderStatus == OrdersEnum.EMPLOYER_IN_DISTRIBUTION.getCode()) {
            //分配中
            qmuiFragment.startFragment(EmployerDistributionFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.EMPLOYER_RECEIVED_ORDERS.getCode()) {
            //已接单
            qmuiFragment.startFragment(AcceptedOrdersFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.EMPLOYER_IN_PROCESSING.getCode()) {
            //已出发
            qmuiFragment.startFragment(EmployerHavingSetOutFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.EMPLOYER_CHECK_AND_ACCEPT.getCode()) {
            //施工中
            qmuiFragment.startFragment(EmployerUnderConstructionFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED.getCode()) {
            //已取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        } else if (orderStatus == OrdersEnum.EMPLOYER_CANCELLED.getCode()) {
            //已完成
            qmuiFragment.startFragment(EmployerCompleteFragment.newInstance(orderCode));
        } else if (orderStatus == OrdersEnum.EMPLOYER_UNPAID.getCode()) {
            //未支付
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED_CANCEL.getCode()) {
            //师傅取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        }
    }


}
