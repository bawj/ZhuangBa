package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.InspectionSheetEnum;

/**
 * @author Administrator
 * @date 2019/9/29 0029
 *
 * 巡查任务 订单状态
 */
public class PatrolStatusUtil {


    /**
     * 雇主端
     *
     * @param mContext         context
     * @param orderStatus      状态
     * @param tvItemOrdersType textView
     */
    public static void employerStatus(Context mContext, int orderStatus, TextView tvItemOrdersType) {
        if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_UNALLOCATED.getCode()) {
            //未分配 --- 和分配中 一样
            tvItemOrdersType.setText(mContext.getString(R.string.in_distribution));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_IN_DISTRIBUTION.getCode()) {
            //分配中
            tvItemOrdersType.setText(mContext.getString(R.string.in_distribution));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_HAVE_IN_HAND.getCode()) {
            //进行中
            tvItemOrdersType.setText(mContext.getString(R.string.have_in_hand));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }


    /**
     * @param baseFragmentActivity baseFragment
     * @param orderCode 订单编号
     * @param orderType 订单类型
     * @param orderStatus 订单状态
     */
    public static void startEmployerPatrol(QMUIFragmentActivity baseFragmentActivity, String orderCode, String orderType, int orderStatus) {
        if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_IN_DISTRIBUTION.getCode()){
            //分配中

        }else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_HAVE_IN_HAND.getCode()){
            //进行中
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_CANCELLED.getCode()){
            //已完成
        }


    }
}
