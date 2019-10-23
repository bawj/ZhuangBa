package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.InspectionSheetEnum;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.masterworker.inspection.PatrolHaveHandDetailFragment;
import com.xiaomai.zhuangba.fragment.masterworker.inspection.PatrolNewTaskDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.PatrolInDistributionDetailFragment;

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
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_APPLY_FOR_REFUND.getCode()){
            //申请退款
            tvItemOrdersType.setText(mContext.getString(R.string.apply_for_refund));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_REFUNDED.getCode()){
            //已退款
            tvItemOrdersType.setText(mContext.getString(R.string.refunded));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }


    /**
     * 雇主
     * @param baseFragmentActivity baseFragment
     * @param orderCode 订单编号
     * @param orderType 订单类型
     * @param orderStatus 订单状态
     */
    public static void startEmployerPatrol(QMUIFragmentActivity baseFragmentActivity, String orderCode, String orderType, int orderStatus) {
        if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_UNALLOCATED.getCode()){
            //分配中
            QMUIFragment currentFragment = baseFragmentActivity.getCurrentFragment();
            currentFragment.startFragmentForResult(PatrolInDistributionDetailFragment.newInstance(orderCode , orderType) , ForResultCode.RESULT_OK.getCode());
        } else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_IN_DISTRIBUTION.getCode()){
            //分配中
            QMUIFragment currentFragment = baseFragmentActivity.getCurrentFragment();
            currentFragment.startFragmentForResult(PatrolInDistributionDetailFragment.newInstance(orderCode , orderType) , ForResultCode.RESULT_OK.getCode());
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_HAVE_IN_HAND.getCode()){
            //进行中
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_SHEET_CANCELLED.getCode()){
            //已完成
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_APPLY_FOR_REFUND.getCode()){
            //申请退款
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.INSPECTION_REFUNDED.getCode()){
            //已退款
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }
    }



    /**
     * 师傅端
     *
     * @param mContext         context
     * @param orderStatus      状态
     * @param tvItemOrdersType textView
     */
    public static void masterStatus(Context mContext, int orderStatus, TextView tvItemOrdersType) {
        if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_IN_DISTRIBUTION.getCode()) {
            //新任务
            tvItemOrdersType.setText(mContext.getString(R.string.new_task));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        }else if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_HAVE_IN_HAND.getCode()) {
            //进行中
            tvItemOrdersType.setText(mContext.getString(R.string.have_in_hand));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_CANCELLED.getCode()) {
            //已完成
            tvItemOrdersType.setText(mContext.getString(R.string.completed));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }else if (orderStatus == InspectionSheetEnum.MASTER_APPLY_FOR_REFUND.getCode()){
            //申请退款
            tvItemOrdersType.setText(mContext.getString(R.string.apply_for_refund));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }else if (orderStatus == InspectionSheetEnum.MASTER_REFUNDED.getCode()){
            //已退款
            tvItemOrdersType.setText(mContext.getString(R.string.refunded));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }

    /**
     * 巡查任务订单详情状态
     * @param mContext context
     * @param status 订单状态：processing：未处理  processed：已处理
     * @param tv TextView
     */
    public static void masterPatrolMission(Context mContext , String status , TextView tv){
        if (status.equals(StringTypeExplain.PROCESSING.getCode())){
            //未处理
            tv.setText(mContext.getString(R.string.no_inspection));
            tv.setBackgroundResource(R.drawable.having_set_out_bg);
        }else if (status.equals(StringTypeExplain.PROCESSED.getCode())){
            //已处理
            tv.setText(mContext.getString(R.string.inspected));
            tv.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        }
    }

    /**
     * @param baseFragmentActivity baseFragment
     * @param orderCode 订单编号
     * @param orderType 订单类型
     * @param orderStatus 订单状态
     */
    public static void startMasterPatrol(QMUIFragmentActivity baseFragmentActivity,String orderCode, String orderType, int orderStatus) {
        if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_IN_DISTRIBUTION.getCode()){
            //新任务
            QMUIFragment currentFragment = baseFragmentActivity.getCurrentFragment();
            currentFragment.startFragmentForResult(PatrolNewTaskDetailFragment.newInstance(orderCode , orderType) , ForResultCode.RESULT_OK.getCode());
        } else if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_HAVE_IN_HAND.getCode()){
            //进行中
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.MASTER_INSPECTION_SHEET_CANCELLED.getCode()){
            //已完成
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.MASTER_APPLY_FOR_REFUND.getCode()){
            //申请退款
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }else if (orderStatus == InspectionSheetEnum.MASTER_REFUNDED.getCode()){
            //已退款
            baseFragmentActivity.startFragment(PatrolHaveHandDetailFragment.newInstance(orderCode , orderType));
        }
    }
}
