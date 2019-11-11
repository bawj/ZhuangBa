package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 */
public class PersonalAdvertisingBillsDetailAdapter extends BaseQuickAdapter<OngoingOrdersList, BaseViewHolder> {

    public PersonalAdvertisingBillsDetailAdapter() {
        super(R.layout.item_personal_advertising_bills_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OngoingOrdersList ongoingOrders) {
        QMUILinearLayout itemOrderLayoutFor = helper.getView(R.id.itemOrderLayoutFor);
        itemOrderLayoutFor.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15), QMUIDisplayHelper.dp2px(mContext, 14),
                0.0f);
        //title
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        tvItemOrdersTitle.setText(TextUtils.isEmpty(ongoingOrders.getServiceText()) ? mContext.getString(R.string.advertising_bills) : ongoingOrders.getServiceText());
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

        tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getSlottingStartLength()));

        TextView tvMaintenance = helper.getView(R.id.tvMaintenance);
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
        int orderStatus = ongoingOrders.getOrderStatus();
        AdvertisingStatusUtil.masterStatus(mContext, orderStatus, tvItemOrdersType);

        tvItemOrdersType.setTag(ongoingOrders);

        Button btnDelete = helper.getView(R.id.btnDelete);
        final String orderCode = ongoingOrders.getOrderCode();
        final int adapterPosition = helper.getAdapterPosition();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onDel(orderCode, adapterPosition);
                }
            }
        });
    }

    private IOnSwipeListener mOnSwipeListener;

    public IOnSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(IOnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    public interface IOnSwipeListener {
        /**
         * 删除
         *
         * @param orderCode 订单编号
         * @param pos       pos
         */
        void onDel(String orderCode, int pos);
    }
}
