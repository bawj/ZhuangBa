package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.DeviceOrder;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;

public class AllocationListAdapter extends BaseQuickAdapter<DeviceOrder, BaseViewHolder> {

    public AllocationListAdapter() {
        super(R.layout.item_allocation_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceOrder deviceOrder) {
        QMUILinearLayout itemOrderLayoutFor = helper.getView(R.id.itemOrderLayoutFor);
        itemOrderLayoutFor.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15), QMUIDisplayHelper.dp2px(mContext, 14),
                0.0f);
        //title
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        tvItemOrdersTitle.setText(TextUtils.isEmpty(deviceOrder.getEquipmentNum()) ? mContext.getString(R.string.advertisement_replacement) : deviceOrder.getEquipmentNum());
        //time
        TextView tvItemOrdersTime = helper.getView(R.id.tvItemOrdersTime);
        //tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getSlottingStartLength()));
        //location
        TextView tvItemOrdersLocation = helper.getView(R.id.tvItemOrdersLocation);
        tvItemOrdersLocation.setText(deviceOrder.getAddress());
        //number
        TextView tvItemOrdersNumber = helper.getView(R.id.tvItemOrdersNumber);
        tvItemOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(deviceOrder.getServiceNum())));
        //money
        TextView tvItemOrdersMoney = helper.getView(R.id.tvItemOrdersMoney);
        //type
        TextView tvItemOrdersType = helper.getView(R.id.tvItemOrdersType);

        TextView tvMaintenance = helper.getView(R.id.tvMaintenance);

        //持续服务
        ImageView ivItemMaintenanceTime = helper.getView(R.id.ivItemMaintenanceTime);
        TextView tvItemOrdersMaintenanceTime = helper.getView(R.id.tvItemOrdersMaintenanceTime);

        //如果是广告单  maintenanceFlag  0 单次 1 持续
        int maintenanceFlag = deviceOrder.getServiceType();
        if (maintenanceFlag == StaticExplain.SINGLE_SERVICE.getCode()) {
            tvMaintenance.setText(mContext.getString(R.string.single_service));
            tvMaintenance.setBackgroundResource(R.drawable.blue_radius_bg);
            tvMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_287CDF));
            ivItemMaintenanceTime.setVisibility(View.GONE);
            tvItemOrdersMaintenanceTime.setVisibility(View.GONE);
        } else {
            tvMaintenance.setText(mContext.getString(R.string.continuous_service));
            tvMaintenance.setBackgroundResource(R.drawable.violet_radius_bg);
            tvMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_542BE9));
            ivItemMaintenanceTime.setVisibility(View.VISIBLE);
            tvItemOrdersMaintenanceTime.setVisibility(View.VISIBLE);
            //维护时间
            //tvItemOrdersMaintenanceTime.setText(mContext.getString(R.string.ending_date_ , ongoingOrders.getSlottingEndLength()));
        }
        // TODO: 2019/12/9 0009 订单状态 新任务状态 显示 接单按钮
        //int orderStatus = deviceOrder.getOrderStatus();
        //AdvertisingStatusUtil.masterStatus(mContext , orderStatus ,tvItemOrdersType);

        //接单
        QMUIButton button = helper.getView(R.id.btnAcceptanceReceipt);

        // TODO: 2019/12/9 0009  是否是团队的订单
        //TextView tvMaintenanceTeam = helper.getView(R.id.tvMaintenanceTeam);
        //String assigner = ongoingOrders.getAssigner();
        // if (!TextUtils.isEmpty(assigner)){
        //    tvMaintenanceTeam.setVisibility(View.VISIBLE);
        //}else {
        //     tvMaintenanceTeam.setVisibility(View.GONE);
        // }
        //如果 assigner == 登录账号 则是 团长 显示金额 否则不显示
//        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
//        String phoneNumber = unique.getPhoneNumber();
//        if (TextUtils.isEmpty(assigner) || phoneNumber.equals(assigner)) {
//            tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money, String.valueOf(ongoingOrders.getMasterOrderAmount()))));
//        } else {
//            tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.asterisk)));
//        }
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.asterisk)));

        tvItemOrdersType.setTag(deviceOrder);
    }
}
