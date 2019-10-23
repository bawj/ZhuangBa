package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class HistoricalAdapter extends BaseQuickAdapter<OngoingOrdersList, BaseViewHolder> {

    public HistoricalAdapter() {
        super(R.layout.item_historical, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OngoingOrdersList ordersList) {
        QMUILinearLayout itemOrderLayoutFor = helper.getView(R.id.itemOrderLayoutFor);
        itemOrderLayoutFor.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15), QMUIDisplayHelper.dp2px(mContext, 14),
                0.0f);
        //title
        TextView tvItemHistoricalOrdersTitle = helper.getView(R.id.tvItemHistoricalOrdersTitle);
        //status
        TextView tvItemHistoricalOrdersType = helper.getView(R.id.tvItemHistoricalOrdersType);
        //location
        TextView tvItemHistoricalOrdersLocation = helper.getView(R.id.tvItemHistoricalOrdersLocation);
        //number
        TextView tvItemHistoricalOrdersNumber = helper.getView(R.id.tvItemHistoricalOrdersNumber);
        //money
        TextView tvItemHistoricalOrdersMoney = helper.getView(R.id.tvItemHistoricalOrdersMoney);

        //公共安全
        tvItemHistoricalOrdersTitle.setText(TextUtils.isEmpty(ordersList.getServiceText()) ? "" : ordersList.getServiceText());
        tvItemHistoricalOrdersLocation.setText(TextUtils.isEmpty(ordersList.getAddress()) ? "" : ordersList.getAddress());
        tvItemHistoricalOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(ordersList.getNumber())));
        tvItemHistoricalOrdersMoney.setText(mContext.getString(R.string.content_money, String.valueOf(ordersList.getOrderAmount())));
        tvItemHistoricalOrdersMoney.setTag(ordersList);

        TextView tvHistoricalMaintenance = helper.getView(R.id.tvHistoricalMaintenance);

        //order status
        int orderStatus = ordersList.getOrderStatus();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();

        String orderType = ordersList.getOrderType();
        //安装单
        if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))){
            //师傅 安装单
            if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                OrderStatusUtil.masterStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
            } else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                //雇主 安装单
                OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
            }
            if (ordersList.getMaintenanceFlag() == StaticExplain.YES_MAINTENANCE.getCode()){
                //有维保
                tvHistoricalMaintenance.setText(mContext.getString(R.string.maintenance));
                tvHistoricalMaintenance.setBackgroundResource(R.drawable.green_radius_bg);
                tvHistoricalMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_199898));
                tvHistoricalMaintenance.setVisibility(View.VISIBLE);
            }else if (ordersList.getMaintenanceFlag() == StaticExplain.NO_MAINTENANCE.getCode()){
                //没有维保
                tvHistoricalMaintenance.setVisibility(View.GONE);
            }
            //广告单
        }else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
            //师傅
            if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                AdvertisingStatusUtil.masterStatus(mContext , orderStatus , tvItemHistoricalOrdersType);
            }else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                //雇主
                AdvertisingStatusUtil.employerStatus(mContext , orderStatus , tvItemHistoricalOrdersType);
            }
            tvHistoricalMaintenance.setVisibility(View.VISIBLE);
            // maintenanceFlag  0 单次 1 持续
            int maintenanceFlag = ordersList.getMaintenanceFlag();
            if (maintenanceFlag == StaticExplain.SINGLE_SERVICE.getCode()) {
                tvHistoricalMaintenance.setText(mContext.getString(R.string.single_service));
                tvHistoricalMaintenance.setBackgroundResource(R.drawable.blue_radius_bg);
                tvHistoricalMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_287CDF));
            } else {
                tvHistoricalMaintenance.setText(mContext.getString(R.string.continuous_service));
                tvHistoricalMaintenance.setBackgroundResource(R.drawable.violet_radius_bg);
                tvHistoricalMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_542BE9));
            }
        }else if (orderType.equals(String.valueOf(StaticExplain.PATROL.getCode()))){
            if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                //师傅
                PatrolStatusUtil.masterStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
            }else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                //雇主
                PatrolStatusUtil.employerStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
            }
        }
    }
}
