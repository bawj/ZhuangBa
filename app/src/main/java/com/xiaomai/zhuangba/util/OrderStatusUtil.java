package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.OrdersEnum;

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
            tvItemOrdersType.setBackgroundResource(R.drawable.received_orders_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_PENDING_DISPOSAL.getCode()) {
            //待处理
            tvItemOrdersType.setText(mContext.getString(R.string.disposal_half_fillet));
            tvItemOrdersType.setBackgroundResource(R.drawable.pending_disposal_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_IN_PROCESSING.getCode()) {
            //处理中
            tvItemOrdersType.setText(mContext.getString(R.string.in_processing));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE.getCode()) {
            //验收中
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_COMPLETED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_CANCELLED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
        } else if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()) {
            //已过期
            tvItemOrdersType.setText(mContext.getString(R.string.expired_));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //验收不通过
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance_is_not_acceptable));
            tvItemOrdersType.setBackgroundResource(R.drawable.allocation_failed_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED_ALREADY_EVALUATED.getCode()) {
            //已完成 已评价
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED_CANCEL.getCode()) {
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
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
            //处理中
            tvItemOrdersType.setText(mContext.getString(R.string.in_processing));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_CHECK_AND_ACCEPT.getCode()) {
            //去验收
            tvItemOrdersType.setText(mContext.getString(R.string.check_and_accept));
            tvItemOrdersType.setBackgroundResource(R.drawable.check_accept_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED.getCode()) {
            //已完成 未评价
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
        } else if (orderStatus == OrdersEnum.EMPLOYER_CANCELLED.getCode()) {
            //已取消
            tvItemOrdersType.setText(mContext.getString(R.string.cancelled));
        } else if (orderStatus == OrdersEnum.EMPLOYER_UNPAID.getCode()) {
            //未支付
            tvItemOrdersType.setText(mContext.getString(R.string.unpaid));
        } else if (orderStatus == OrdersEnum.EMPLOYER_ALLOCATION_FAILED.getCode()) {
            //分配失败
            tvItemOrdersType.setText(mContext.getString(R.string.allocation_failed));
            tvItemOrdersType.setBackgroundResource(R.drawable.allocation_failed_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_ACCEPTANCE_IS_NOT_ACCEPTABLE.getCode()) {
            //验收不通过处理中
            // TODO: 2019/6/3 0003 验收不通过处理中   显示文字
            tvItemOrdersType.setText(mContext.getString(R.string.acceptance_is_not_acceptable_));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == OrdersEnum.EMPLOYER_COMPLETED_ALREADY_EVALUATED.getCode()) {
            //已完成 已评价
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
        }
    }

}
