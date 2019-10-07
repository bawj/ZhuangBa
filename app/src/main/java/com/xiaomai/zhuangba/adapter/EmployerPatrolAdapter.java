package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.Patrol;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

/**
 * @author Administrator
 * @date 2019/9/29 0029
 */
public class EmployerPatrolAdapter extends BaseQuickAdapter<Patrol.ListBean, BaseViewHolder> {

    public EmployerPatrolAdapter() {
        super(R.layout.item_employer_patrol, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Patrol.ListBean patrol) {
        //设备号
        TextView tvItemPatrolDeviceNumber = helper.getView(R.id.tvItemPatrolDeviceNumber);
        tvItemPatrolDeviceNumber.setText(mContext.getString(R.string.device_number , patrol.getMaterialsStartLength()));
        //服务时长
        TextView tvLengthOfService = helper.getView(R.id.tvLengthOfService);
        tvLengthOfService.setText(mContext.getString(R.string.length_of_service,String.valueOf(patrol.getDebugging())));
        //服务日期
        TextView tvServiceDate = helper.getView(R.id.tvServiceDate);
        tvServiceDate.setText(mContext.getString(R.string.service_date,patrol.getSlottingEndLength()));
        //A B C D 面
        TextView tvNoodles = helper.getView(R.id.tvNoodles);
        tvNoodles.setText(patrol.getMaterialsEndLength());
        //服务地址
        TextView tvPatrolAddress = helper.getView(R.id.tvPatrolAddress);
        tvPatrolAddress.setText(patrol.getAddress());
        //总计
        TextView tvAdvertisingMoney = helper.getView(R.id.tvAdvertisingMoney);
        tvAdvertisingMoney.setText(String.valueOf(patrol.getOrderAmount()));

        //订单状态
        TextView tvItemOrdersType = helper.getView(R.id.tvItemOrdersType);
        PatrolStatusUtil.employerStatus(mContext , patrol.getOrderStatus() , tvItemOrdersType);
    }
}
