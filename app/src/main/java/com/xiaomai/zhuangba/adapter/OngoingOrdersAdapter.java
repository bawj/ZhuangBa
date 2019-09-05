package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
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
        tvItemOrdersLocation.setText(ongoingOrders.getAddress());
        tvItemOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(ongoingOrders.getNumber())));
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money, String.valueOf(ongoingOrders.getOrderAmount()))));

        String orderType = ongoingOrders.getOrderType();
        TextView tvMaintenance = helper.getView(R.id.tvMaintenance);

        //开槽 调试 辅材
        TextView tvSlotting = helper.getView(R.id.tvSlotting);
        TextView tvDebugging = helper.getView(R.id.tvDebugging);
        TextView tvAuxiliaryMaterials = helper.getView(R.id.tvAuxiliaryMaterials);
        //安装单
        if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))) {
            tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getAppointmentTime()));
            if (ongoingOrders.getMaintenanceFlag() == StaticExplain.YES_MAINTENANCE.getCode()) {
                //有维保
                tvMaintenance.setText(mContext.getString(R.string.maintenance));
                tvMaintenance.setBackgroundResource(R.drawable.green_radius_bg);
                tvMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_199898));
                tvMaintenance.setVisibility(View.VISIBLE);
            } else if (ongoingOrders.getMaintenanceFlag() == StaticExplain.NO_MAINTENANCE.getCode()) {
                //没有维保
                tvMaintenance.setVisibility(View.GONE);
            }
            // slottingStartLength 开槽  slottingEndLength
            if (DensityUtils.stringTypeInteger(ongoingOrders.getSlottingEndLength()) > 0) {
                tvSlotting.setVisibility(View.VISIBLE);
                tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                        ongoingOrders.getSlottingStartLength(), ongoingOrders.getSlottingEndLength()));
            } else {
                tvSlotting.setVisibility(View.GONE);
            }
            //debugging 调试
            String debugging = ongoingOrders.getDebugging();
            if (debugging.equals(String.valueOf(StaticExplain.DEBUGGING.getCode()))) {
                tvDebugging.setVisibility(View.GONE);
            } else {
                tvDebugging.setText(mContext.getString(R.string.debugging));
                tvDebugging.setVisibility(View.VISIBLE);
            }
            String materialsEndLength = ongoingOrders.getMaterialsEndLength();
            if (DensityUtils.stringTypeInteger(materialsEndLength) > 0) {
                tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
                tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                        ongoingOrders.getMaterialsStartLength(), ongoingOrders.getMaterialsEndLength()));
            } else {
                tvAuxiliaryMaterials.setVisibility(View.GONE);
            }
            //广告单
        } else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
            tvMaintenance.setVisibility(View.VISIBLE);
            tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getSlottingStartLength()));
            //如果是广告单  maintenanceFlag  0 单次 1 持续
            int maintenanceFlag = ongoingOrders.getMaintenanceFlag();
            if (maintenanceFlag == StaticExplain.SINGLE_SERVICE.getCode()) {
                tvMaintenance.setText(mContext.getString(R.string.single_service));
                tvMaintenance.setBackgroundResource(R.drawable.blue_radius_bg);
                tvMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_287CDF));
            } else {
                tvMaintenance.setText(mContext.getString(R.string.continuous_service));
                tvMaintenance.setBackgroundResource(R.drawable.violet_radius_bg);
                tvMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_542BE9));
            }
            tvSlotting.setVisibility(View.GONE);
            tvDebugging.setVisibility(View.GONE);
            tvAuxiliaryMaterials.setVisibility(View.GONE);
        }

        tvItemOrdersTitle.setTag(ongoingOrders);
        //order status
        int orderStatus = ongoingOrders.getOrderStatus();
        if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
            AdvertisingStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
        }else if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))){
            OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
        }
    }
}
