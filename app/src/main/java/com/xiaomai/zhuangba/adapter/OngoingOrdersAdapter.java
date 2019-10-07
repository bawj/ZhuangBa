package com.xiaomai.zhuangba.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

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
        //巡查
        TextView tvItemPatrolFrequency = helper.getView(R.id.tvItemPatrolFrequency);
        TextView tvItemInspectionDate = helper.getView(R.id.tvItemInspectionDate);


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
            installation(ongoingOrders, tvItemOrdersTime, tvMaintenance, tvSlotting, tvDebugging, tvAuxiliaryMaterials);
        } else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
            //广告单
            advertisement(ongoingOrders, tvItemOrdersTime, tvMaintenance, tvSlotting, tvDebugging, tvAuxiliaryMaterials);
        } else if (orderType.equals(String.valueOf(StaticExplain.PATROL.getCode()))) {
            //巡查
            patrol(mContext, ongoingOrders, tvItemOrdersTime, tvMaintenance, tvItemPatrolFrequency, tvItemInspectionDate, tvItemOrdersLocation);
        }
        tvItemOrdersTitle.setTag(ongoingOrders);
        //order status
        int orderStatus = ongoingOrders.getOrderStatus();
        if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
            AdvertisingStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
        } else if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))) {
            OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
        } else if (orderType.equals(String.valueOf(StaticExplain.PATROL.getCode()))) {
            PatrolStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
        }
    }


    /**
     * 巡查任务
     *
     * @param mContext              context
     * @param ongoingOrders         订单
     * @param tvItemOrdersTime      巡查时长
     * @param tvMaintenance         维保
     * @param tvItemPatrolFrequency 巡查频率
     * @param tvItemInspectionDate  巡查日期
     * @param tvItemOrdersLocation  服务地址
     */
    private void patrol(Context mContext, OngoingOrdersList ongoingOrders, TextView tvItemOrdersTime, TextView tvMaintenance,
                        TextView tvItemPatrolFrequency, TextView tvItemInspectionDate, TextView tvItemOrdersLocation) {
        tvItemPatrolFrequency.setVisibility(View.VISIBLE);
        tvItemInspectionDate.setVisibility(View.VISIBLE);
        tvMaintenance.setVisibility(View.GONE);
        //巡查时长
        String debugging = ongoingOrders.getDebugging();
        tvItemOrdersTime.setText(mContext.getString(R.string.length_of_inspection, debugging));
        tvItemOrdersLocation.setText(ongoingOrders.getAddress());
        //巡查时长 单位 month 月  week周
        String slottingStartLength = ongoingOrders.getSlottingStartLength();
        //巡查日期
        String slottingEndLength = ongoingOrders.getSlottingEndLength();
        if (slottingStartLength.equals(ConstantUtil.MONTH)) {
            tvItemPatrolFrequency.setText(mContext.getString(R.string.month));
            tvItemInspectionDate.setText(mContext.getString(R.string.inspection_date, slottingEndLength));
        } else if (slottingStartLength.equals(ConstantUtil.WEEK)) {
            tvItemPatrolFrequency.setText(mContext.getString(R.string.week));
            tvItemInspectionDate.setText(mContext.getString(R.string.inspection_date_week, DateUtil.getWeek(slottingEndLength)));
        }
    }

    /**
     * 广告单显示
     *
     * @param ongoingOrders        订单集合
     * @param tvItemOrdersTime     预约时间
     * @param tvMaintenance        维保
     * @param tvSlotting           开槽
     * @param tvDebugging          调试
     * @param tvAuxiliaryMaterials 辅材
     */
    private void advertisement(OngoingOrdersList ongoingOrders, TextView tvItemOrdersTime, TextView tvMaintenance, TextView tvSlotting, TextView tvDebugging, TextView tvAuxiliaryMaterials) {
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

    /**
     * 安装单显示
     *
     * @param ongoingOrders        订单列表
     * @param tvItemOrdersTime     预约时间
     * @param tvMaintenance        维保
     * @param tvSlotting           开槽
     * @param tvDebugging          调试
     * @param tvAuxiliaryMaterials 辅材
     */
    private void installation(OngoingOrdersList ongoingOrders, TextView tvItemOrdersTime, TextView tvMaintenance, TextView tvSlotting, TextView tvDebugging, TextView tvAuxiliaryMaterials) {
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
    }
}
