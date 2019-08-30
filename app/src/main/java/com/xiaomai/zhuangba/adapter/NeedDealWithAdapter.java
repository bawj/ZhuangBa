package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public class NeedDealWithAdapter extends BaseQuickAdapter<OngoingOrdersList, BaseViewHolder> {

    public NeedDealWithAdapter() {
        super(R.layout.item_need_deal_with, new ArrayList<OngoingOrdersList>());
    }

    @Override
    protected void convert(BaseViewHolder helper, OngoingOrdersList ongoingOrders) {
        QMUILinearLayout itemOrderLayoutFor = helper.getView(R.id.itemOrderLayoutFor);
        itemOrderLayoutFor.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15), QMUIDisplayHelper.dp2px(mContext, 14),
                0.0f);
        //title
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        tvItemOrdersTitle.setText(ongoingOrders.getServiceText());
        //time
        TextView tvItemOrdersTime = helper.getView(R.id.tvItemOrdersTime);
        tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getAppointmentTime()));
        //location
        TextView tvItemOrdersLocation = helper.getView(R.id.tvItemOrdersLocation);
        tvItemOrdersLocation.setText(ongoingOrders.getAddress());
        //number
        TextView tvItemOrdersNumber = helper.getView(R.id.tvItemOrdersNumber);
        tvItemOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(ongoingOrders.getNumber())));
        //money
        TextView tvItemOrdersMoney = helper.getView(R.id.tvItemOrdersMoney);
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money, String.valueOf(ongoingOrders.getOrderAmount()))));
        //type
        TextView tvItemOrdersType = helper.getView(R.id.tvItemOrdersType);

        //是否显示 维保
        TextView tvMaintenance = helper.getView(R.id.tvMaintenance);
        if (ongoingOrders.getMaintenanceFlag() == StaticExplain.YES_MAINTENANCE.getCode()) {
            //有维保
            tvMaintenance.setVisibility(View.VISIBLE);
        } else if (ongoingOrders.getMaintenanceFlag() == StaticExplain.NO_MAINTENANCE.getCode()) {
            //没有维保
            tvMaintenance.setVisibility(View.GONE);
        }

        ImageView ivItemOrdersIdentification = helper.getView(R.id.ivItemOrdersIdentification);
        TextView tvItemOrdersIdentification = helper.getView(R.id.tvItemOrdersIdentification);

        tvItemOrdersTitle.setTag(ongoingOrders);
        int orderStatus = ongoingOrders.getOrderStatus();
        String expireTime = ongoingOrders.getExpireTime();

        if (orderStatus == OrdersEnum.MASTER_NEW_TASK.getCode()) {
            tvItemOrdersIdentification.setText(mContext.getString(R.string.waiting_for_orders_, expireTime));
            ivItemOrdersIdentification.setVisibility(View.VISIBLE);
            ivItemOrdersIdentification.setVisibility(View.VISIBLE);
        } else if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()) {
            tvItemOrdersIdentification.setText(mContext.getString(R.string.expired, expireTime));
            ivItemOrdersIdentification.setVisibility(View.VISIBLE);
            ivItemOrdersIdentification.setVisibility(View.VISIBLE);
        } else {
            ivItemOrdersIdentification.setVisibility(View.GONE);
            tvItemOrdersIdentification.setVisibility(View.GONE);
        }
        ivItemOrdersIdentification.setVisibility(View.GONE);
        tvItemOrdersIdentification.setVisibility(View.GONE);


        //开槽 调试 辅材
        TextView tvSlotting = helper.getView(R.id.tvSlotting);
        TextView tvDebugging = helper.getView(R.id.tvDebugging);
        TextView tvAuxiliaryMaterials = helper.getView(R.id.tvAuxiliaryMaterials);
        String orderType = ongoingOrders.getOrderType();
        //安装单
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
            tvDebugging.setText(mContext.getString(R.string.debugging));
            tvDebugging.setVisibility(View.VISIBLE);
        } else {
            tvDebugging.setVisibility(View.GONE);
        }
        String materialsEndLength = ongoingOrders.getMaterialsEndLength();
        if (DensityUtils.stringTypeInteger(materialsEndLength) > 0) {
            tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
            tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                    ongoingOrders.getMaterialsStartLength(), ongoingOrders.getMaterialsEndLength()));
        } else {
            tvAuxiliaryMaterials.setVisibility(View.GONE);
        }
        ///广告单
         /*else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
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
        }*/
        OrderStatusUtil.masterStatus(mContext, orderStatus, tvItemOrdersType);
    }
}
