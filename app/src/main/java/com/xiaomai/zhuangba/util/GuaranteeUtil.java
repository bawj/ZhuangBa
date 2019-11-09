package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.GuaranteeEnums;
import com.xiaomai.zhuangba.fragment.masterworker.guarantee.HaveInHandGuaranteeFragment;
import com.xiaomai.zhuangba.fragment.masterworker.guarantee.NewTaskGuaranteeDetailFragment;
import com.xiaomai.zhuangba.fragment.masterworker.guarantee.NotYetBegunGuaranteeFragment;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 * <p>
 * 师傅维保单状态
 */
public class GuaranteeUtil {


    /**
     * 师傅
     * @param mContext context
     * @param orderStatus 订单状态
     * @param tvItemOrdersType 订单类型
     */
    public static void guaranteeStatus(Context mContext, String orderStatus, TextView tvItemOrdersType) {
        if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_NEW_TASK.getCode()))) {
            //新任务
            tvItemOrdersType.setText(mContext.getString(R.string.new_task));
            tvItemOrdersType.setBackgroundResource(R.drawable.having_set_out_bg);
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_HAVE_IN_HAND.getCode()))) {
            //进行中
            tvItemOrdersType.setText(mContext.getString(R.string.have_in_hand));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_NOT_YET_BEGUN.getCode()))) {
            //未开始
            tvItemOrdersType.setText(mContext.getString(R.string.not_yet_begun));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_HAS_ENDED.getCode()))) {
            //已结束
        }
    }

    /**
     * @param qmuiFragment fragment
     * @param orderCode    订单编号
     * @param orderType    订单类型 安装单 或  广告维保单
     * @param orderStatus  订单状态
     */
    public static void startGuaranteeOrderDetail(QMUIFragmentActivity qmuiFragment, String orderCode, String orderType, String orderStatus) {
        if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_NEW_TASK.getCode()))) {
            //新任务
            QMUIFragment currentFragment = qmuiFragment.getCurrentFragment();
            currentFragment.startFragmentForResult(NewTaskGuaranteeDetailFragment.newInstance(orderCode , orderType)  , ForResultCode.RESULT_OK.getCode());
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_HAVE_IN_HAND.getCode()))) {
            //进行中
            qmuiFragment.startFragment(HaveInHandGuaranteeFragment.newInstance(orderCode, orderType));
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_NOT_YET_BEGUN.getCode()))) {
            //未开始
            qmuiFragment.startFragment(NotYetBegunGuaranteeFragment.newInstance(orderCode, orderType));
        } else if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_HAS_ENDED.getCode()))) {
            //已结束
        }
    }

    /**
     * 雇主
     * @param mContext context
     * @param orderStatus 订单状态
     * @param tvItemOrdersType 订单类型
     */
    public static void guaranteeEmployerStatus(Context mContext, String orderStatus, TextView tvItemOrdersType) {
        if (orderStatus.equals(String.valueOf(GuaranteeEnums.GUARANTEE_NEW_TASK.getCode()))) {
            //分配中
            tvItemOrdersType.setText(mContext.getString(R.string.in_distribution));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        }
    }

}
