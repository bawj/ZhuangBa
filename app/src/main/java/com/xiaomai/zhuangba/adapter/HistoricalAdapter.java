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
import com.xiaomai.zhuangba.util.OrderStatusUtil;

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
        if (ordersList.getMaintenanceFlag() == StaticExplain.YES_MAINTENANCE.getCode()){
            //有维保
            tvHistoricalMaintenance.setVisibility(View.VISIBLE);
        }else if (ordersList.getMaintenanceFlag() == StaticExplain.NO_MAINTENANCE.getCode()){
            //没有维保
            tvHistoricalMaintenance.setVisibility(View.GONE);
        }
        //order status
        int orderStatus = ordersList.getOrderStatus();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端
            OrderStatusUtil.masterStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
        } else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
            //雇主端
            OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemHistoricalOrdersType);
        }
    }
}
