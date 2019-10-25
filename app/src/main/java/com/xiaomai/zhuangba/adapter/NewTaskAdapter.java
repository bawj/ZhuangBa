package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public class NewTaskAdapter extends BaseQuickAdapter<OngoingOrdersList, BaseViewHolder> {

    public NewTaskAdapter() {
        super(R.layout.item_new_task, new ArrayList<OngoingOrdersList>());
    }

    @Override
    protected void convert(BaseViewHolder helper, OngoingOrdersList ongoingOrders) {
        QMUILinearLayout itemOrderLayoutFor = helper.getView(R.id.itemOrderLayoutFor);
        itemOrderLayoutFor.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15), QMUIDisplayHelper.dp2px(mContext, 14),
                0.0f);
        //title
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        if (ongoingOrders.getOrderType().equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
            tvItemOrdersTitle.setText(mContext.getString(R.string.advertising_replacement));
        }else {
            tvItemOrdersTitle.setText(ongoingOrders.getServiceText());
        }
        //time
        TextView tvItemOrdersTime = helper.getView(R.id.tvItemOrdersTime);

        //巡查任务
        if (ongoingOrders.getOrderType().equals(String.valueOf(StaticExplain.PATROL.getCode()))) {
            //巡查时长 单位 month 月  week周
            String slottingStartLength = ongoingOrders.getSlottingStartLength();
            //巡查日期
            String slottingEndLength = ongoingOrders.getSlottingEndLength();
            if (slottingStartLength.equals(ConstantUtil.MONTH)) {
                tvItemOrdersTime.setText(mContext.getString(R.string.inspection_date, slottingEndLength));
            } else if (slottingStartLength.equals(ConstantUtil.WEEK)) {
                tvItemOrdersTime.setText(mContext.getString(R.string.inspection_date_week, slottingEndLength));
            }
        }else {
            tvItemOrdersTime.setText(mContext.getString(R.string.time, ongoingOrders.getSlottingStartLength()));
        }

        //location
        TextView tvItemOrdersLocation = helper.getView(R.id.tvItemOrdersLocation);
        tvItemOrdersLocation.setText(ongoingOrders.getAddress());
        //number
        TextView tvItemOrdersNumber = helper.getView(R.id.tvItemOrdersNumber);
        tvItemOrdersNumber.setText(mContext.getString(R.string.task_number, String.valueOf(ongoingOrders.getNumber())));
        //money
        TextView tvItemOrdersMoney = helper.getView(R.id.tvItemOrdersMoney);
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money, String.valueOf(ongoingOrders.getOrderAmount()))));
        tvItemOrdersTitle.setTag(ongoingOrders);
    }
}
