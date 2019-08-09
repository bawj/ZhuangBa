package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 维保 状态
 */
public class MaintenanceUtil {

    public static void maintenanceType(Context mContext, String endTime, TextView tvItemOrdersType) {
        Long time = System.currentTimeMillis();
        Long aLong = DateUtil.dateToCurrentTimeMillis(endTime, "yyyy-MM-dd");
        if (time > aLong) {
            //已结束
            tvItemOrdersType.setText(mContext.getString(R.string.end));
            tvItemOrdersType.setBackgroundResource(R.drawable.expired_half_fillet_bg);
        } else {
            //进行中
            tvItemOrdersType.setText(mContext.getString(R.string.have_in_hand));
            tvItemOrdersType.setBackgroundResource(R.drawable.distribution_half_fillet_bg);
        }
    }

}
