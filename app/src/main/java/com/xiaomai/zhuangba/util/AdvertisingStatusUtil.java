package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementAcceptanceFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementAcceptedOrdersFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementCancelledFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementCompleteFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementDistributionFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementHavingSetOutFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementNoPassageFragment;
import com.xiaomai.zhuangba.fragment.advertisement.employer.EmployerAdvertisementUnderConstructionFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.MasterAdvertisementAcceptanceFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.beunder.MasterAdvertisementBeUnderConstructionFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.having.MasterAdvertisementHavingSetOutFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.MasterAdvertisementNewTaskDetailFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.MasterAdvertisementStartTheMissionFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.BeUnderConstructionFragment;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 */
public class AdvertisingStatusUtil {

    /**
     * 师傅端
     *
     * @param mContext         context
     * @param orderStatus      状态
     * @param tvItemOrdersType textView
     */
    public static void masterStatus(Context mContext, int orderStatus, TextView tvItemOrdersType) {
        if (orderStatus == AdvertisingEnum.MASTER_NEW_TASK.getCode()) {
            //新任务
            tvItemOrdersType.setText(mContext.getString(R.string.new_task));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_PENDING_DISPOSAL.getCode()) {
            //已接单
            tvItemOrdersType.setText(mContext.getString(R.string.received_orders));
            tvItemOrdersType.setBackgroundResource(R.drawable.pending_disposal_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_IN_PROCESSING.getCode()) {
            //已出发
            tvItemOrdersType.setText(mContext.getString(R.string.having_set_out));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_ACCEPTANCE.getCode()) {
            //施工中
            tvItemOrdersType.setText(mContext.getString(R.string.be_under_construction));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_COMPLETED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_EXPIRED.getCode()) {
            //已过期
            tvItemOrdersType.setText(mContext.getString(R.string.expired_));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //师傅取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_ACCEPTANCE.getCode()) {
            //验收中
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_FAILURE_OF_ACCEPTANCE.getCode()) {
            //验收不通过
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance_is_not));
            tvItemOrdersType.setBackgroundResource(R.drawable.acceptance_is_not_bg);
        } else if (orderStatus == AdvertisingEnum.MASTER_CANCELLATION_UNDER_WAY.getCode()) {
            //正在取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancellation_under_way));
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
        if (orderStatus == AdvertisingEnum.EMPLOYER_IN_DISTRIBUTION.getCode()) {
            //分配中
            tvItemOrdersType.setText(mContext.getString(R.string.in_distribution));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_RECEIVED_ORDERS.getCode()) {
            //已接单
            tvItemOrdersType.setText(mContext.getString(R.string.received_orders));
            tvItemOrdersType.setBackgroundResource(R.drawable.received_orders_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_IN_PROCESSING.getCode()) {
            //已出发
            tvItemOrdersType.setText(mContext.getString(R.string.having_set_out));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CHECK_AND_ACCEPT.getCode()) {
            //施工中
            tvItemOrdersType.setText(mContext.getString(R.string.be_under_construction));
            tvItemOrdersType.setBackgroundResource(R.drawable.check_accept_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_COMPLETED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_UNPAID.getCode()) {
            //未支付
            tvItemOrdersType.setText(mContext.getString(R.string.unpaid));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_ACCEPTANCE.getCode()) {
            //验收中
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_FAILURE_OF_ACCEPTANCE.getCode()) {
            //验收不通过
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance_is_not));
            tvItemOrdersType.setBackgroundResource(R.drawable.acceptance_is_not_bg);
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CANCELLATION_UNDER_WAY.getCode()) {
            //正在取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancellation_under_way));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }

    /**
     * 雇主
     * 跳转到广告单详情
     *
     * @param qmuiFragment fragment
     * @param orderCode    订单编号
     * @param orderStatus  订单状态
     */
    public static void startEmployerAdvertisingBills(QMUIFragmentActivity qmuiFragment, String orderCode, String orderType, int orderStatus) {
        if (orderStatus == AdvertisingEnum.EMPLOYER_IN_DISTRIBUTION.getCode()) {
            //分配中
            qmuiFragment.startFragment(EmployerAdvertisementDistributionFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_RECEIVED_ORDERS.getCode()) {
            //已接单
            qmuiFragment.startFragment(EmployerAdvertisementAcceptedOrdersFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_IN_PROCESSING.getCode()) {
            //已出发
            qmuiFragment.startFragment(EmployerAdvertisementHavingSetOutFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CHECK_AND_ACCEPT.getCode()) {
            //施工中
            qmuiFragment.startFragment(EmployerAdvertisementUnderConstructionFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_COMPLETED.getCode()) {
            //已取消
            qmuiFragment.startFragment(EmployerAdvertisementCancelledFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CANCELLED.getCode()) {
            //已完成
            qmuiFragment.startFragment(EmployerAdvertisementCompleteFragment.newInstance(orderCode , orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_UNPAID.getCode()) {
            //未支付
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_ACCEPTANCE.getCode()) {
            //验收中
            qmuiFragment.startFragment(EmployerAdvertisementAcceptanceFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_FAILURE_OF_ACCEPTANCE.getCode()) {
            //验收不通过
            qmuiFragment.startFragment(EmployerAdvertisementNoPassageFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.EMPLOYER_CANCELLATION_UNDER_WAY.getCode()) {
            //正在取消
            qmuiFragment.startFragment(EmployerAdvertisementCancelledFragment.newInstance(orderCode, orderType));
        }
    }


    /**
     * 师傅端跳转到订单详情
     *
     * @param qmuiFragment baseFragment
     * @param orderCode    订单编号
     * @param orderStatus  订单状态
     */
    public static void startMasterOrderDetail(QMUIFragmentActivity qmuiFragment, String orderCode, String orderType, int orderStatus) {
        if (orderStatus == OrdersEnum.MASTER_NEW_TASK.getCode()) {
            //新任务 -- > 已接单
            qmuiFragment.startFragment(MasterAdvertisementNewTaskDetailFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_PENDING_DISPOSAL.getCode()) {
            //已接单 -- > 已出发
            qmuiFragment.startFragment(MasterAdvertisementStartTheMissionFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_IN_PROCESSING.getCode()) {
            //已出发 -- > 开始施工
            qmuiFragment.startFragment(MasterAdvertisementHavingSetOutFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_BE_UNDER_CONSTRUCTION.getCode()) {
            //施工中 -- >验收中
            qmuiFragment.startFragment(MasterAdvertisementBeUnderConstructionFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_ACCEPTANCE.getCode()) {
            //验收中
            qmuiFragment.startFragment(MasterAdvertisementAcceptanceFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_FAILURE_OF_ACCEPTANCE.getCode()) {
            //验收不通过
            qmuiFragment.startFragment(MasterAdvertisementAcceptanceFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_CANCELLATION_UNDER_WAY.getCode()) {
            //正在取消
            qmuiFragment.startFragment(MasterAdvertisementAcceptanceFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_CANCELLED.getCode()) {
            //已完成
            qmuiFragment.startFragment(MasterAdvertisementAcceptanceFragment.newInstance(orderCode, orderType));
        } else if (orderStatus == AdvertisingEnum.MASTER_COMPLETED.getCode()) {
            //雇主取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        } else if (orderStatus == AdvertisingEnum.MASTER_EXPIRED.getCode()) {
            //已过期
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_expired));
        } else if (orderStatus == AdvertisingEnum.MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //师傅取消
            ToastUtil.showShort(qmuiFragment.getString(R.string.order_cancelled));
        }
    }
}
