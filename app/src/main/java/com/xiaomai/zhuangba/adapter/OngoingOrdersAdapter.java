package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class OngoingOrdersAdapter extends BaseQuickAdapter<OngoingOrdersList, BaseViewHolder> {


    public OngoingOrdersAdapter() {
        super(R.layout.item_orders, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OngoingOrdersList ongoingOrders) {
        //title
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        //time
        TextView tvItemOrdersTime = helper.getView(R.id.tvItemOrdersTime);
        //location
        TextView tvItemOrdersLocation = helper.getView(R.id.tvItemOrdersLocation);
        //number
        TextView tvItemOrdersNumber = helper.getView(R.id.tvItemOrdersNumber);
        //money
        TextView tvItemOrdersMoney = helper.getView(R.id.tvItemOrdersMoney);
        //type
        TextView tvItemOrdersType = helper.getView(R.id.tvItemOrdersType);

        tvItemOrdersTitle.setText(ongoingOrders.getServiceText());
        tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getAppointmentTime()));
        tvItemOrdersLocation.setText(ongoingOrders.getAddress());
        tvItemOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(ongoingOrders.getNumber())));
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money, String.valueOf(ongoingOrders.getOrderAmount()))));

        TextView tvMaintenance = helper.getView(R.id.tvMaintenance);
        if (ongoingOrders.getMaintenanceFlag() == StaticExplain.YES_MAINTENANCE.getCode()){
            //有维保
            tvMaintenance.setVisibility(View.VISIBLE);
        }else if (ongoingOrders.getMaintenanceFlag() == StaticExplain.NO_MAINTENANCE.getCode()){
            //没有维保
            tvMaintenance.setVisibility(View.GONE);
        }
        tvItemOrdersTitle.setTag(ongoingOrders);
        //order status
        int orderStatus = ongoingOrders.getOrderStatus();
        //雇主端
        OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);

    }
}
