package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.OngoingOrdersList;
import com.xiaomai.zhuangba.data.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2019/7/8 0008
 * 订单列表
 */
public class OrderListAdapter extends BaseQuickAdapter<OngoingOrdersList , BaseViewHolder> {

    public OrderListAdapter() {
        super(R.layout.item_order_list, new ArrayList<OngoingOrdersList>());
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
        tvItemOrdersMoney.setText(String.valueOf(mContext.getString(R.string.content_money,String.valueOf(ongoingOrders.getOrderAmount()))));
        //type
        TextView tvItemOrdersType = helper.getView(R.id.tvItemOrdersType);
        //超时
        TextView tvItemOrdersTimeout = helper.getView(R.id.tvItemOrdersTimeout);

        //雇主端不显示
        ImageView ivItemOrdersIdentification = helper.getView(R.id.ivItemOrdersIdentification);
        TextView tvItemOrdersIdentification = helper.getView(R.id.tvItemOrdersIdentification);

        tvItemOrdersTitle.setTag(ongoingOrders);

        int orderStatus = ongoingOrders.getOrderStatus();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo != null){
            if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                //师傅端
                OrderStatusUtil.masterStatus(mContext, orderStatus, tvItemOrdersType);
                String expireTime = ongoingOrders.getExpireTime();
                if (orderStatus == OrdersEnum.MASTER_NEW_TASK.getCode()){
                    tvItemOrdersIdentification.setText(mContext.getString(R.string.waiting_for_orders_, expireTime));
                }else if (orderStatus == OrdersEnum.MASTER_EXPIRED.getCode()){
                    tvItemOrdersIdentification.setText(mContext.getString(R.string.expired,  expireTime));
                }else if (orderStatus == OrdersEnum.MASTER_PENDING_DISPOSAL.getCode() && !TextUtils.isEmpty(expireTime)){
                    ivItemOrdersIdentification.setVisibility(View.GONE);
                    tvItemOrdersTimeout.setText(mContext.getString(R.string.timeout));
                }else {
                    ivItemOrdersIdentification.setVisibility(View.GONE);
                }
            }else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))){
                //雇主端
                OrderStatusUtil.employerStatus(mContext, orderStatus, tvItemOrdersType);
                ivItemOrdersIdentification.setVisibility(View.GONE);
                tvItemOrdersIdentification.setVisibility(View.GONE);
            }
        }
    }
}
