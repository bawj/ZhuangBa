package com.xiaomai.zhuangba.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.AmountUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.AdvertisingList;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.MaintenanceUtil;

import org.joda.time.DateTime;

/**
 * @author Administrator
 * @date 2019/8/23 0023
 */
public class AdvertisingReplacementAdapter extends BaseQuickAdapter<AdvertisingList , BaseViewHolder> {

    public AdvertisingReplacementAdapter() {
        super(R.layout.item_advertising_replacement, null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, AdvertisingList item) {
        //标题
        TextView tvItemAdvertisingReplacementTitle = helper.getView(R.id.tvItemAdvertisingReplacementTitle);
        tvItemAdvertisingReplacementTitle.setText(mContext.getString(R.string.advertising_replacement));
        //进行中  已完成
        TextView tvItemAdvertisingReplacementType = helper.getView(R.id.tvItemAdvertisingReplacementType);

        MaintenanceUtil.maintenanceType(mContext, item.getEndTime(), tvItemAdvertisingReplacementType);


        //设备编号
        TextView tvEquipmentNumber = helper.getView(R.id.tvEquipmentNumber);
        tvEquipmentNumber.setText(item.getEquipmentNum());
        //更换位置
        TextView tvChangePlaces = helper.getView(R.id.tvChangePlaces);
        tvChangePlaces.setText(item.getEquipmentSurface());
        //金额
        TextView tvMoney = helper.getView(R.id.tvMoney);
        tvMoney.setText(mContext.getString(R.string.content_money , String.valueOf( String.valueOf(AmountUtil.getDoubleValue(item.getOrderAmount() , 2)))));
        //维护周期
        TextView tvMaintenanceCycle = helper.getView(R.id.tvMaintenanceCycle);
        tvMaintenanceCycle.setText(mContext.getString(R.string.maintenance_time , item.getOrderTime()));
        //维护时间
        TextView tvEndingTime = helper.getView(R.id.tvEndingTime);
        String startTime = item.getStartTime();
        if (!TextUtils.isEmpty(startTime)){
            tvEndingTime.setText(startTime + "~" + item.getEndTime());
        }
        //地址
        TextView tvLocation = helper.getView(R.id.tvLocation);
        tvLocation.setText(item.getAddress());
        //订单编号
        TextView tvOrderNumber = helper.getView(R.id.tvOrderNumber);
        tvOrderNumber.setText(item.getOrderCode());
        //入账时间
        TextView tvAccountingTime = helper.getView(R.id.tvAccountingTime);
        String endTime = item.getEndTime();
        if (!TextUtils.isEmpty(endTime)){
            DateTime dateTime = DateUtil.strToDate(endTime, "yyyy-MM-dd");
            //往后推两天 为入账时间
            dateTime = dateTime.plusDays(2);
            int dayOfYear = dateTime.getDayOfMonth();
            tvAccountingTime.setText(mContext.getString(R.string.accounting_time , String.valueOf(dayOfYear)));
        }
        //入账金额
        TextView tvAccountingAmount = helper.getView(R.id.tvAccountingAmount);
        double earningsMoney = item.getMonthMoney();
        tvAccountingAmount.setText(mContext.getString(R.string.accounting_amount , String.valueOf(AmountUtil.getDoubleValue(earningsMoney , 2))));

    }
}
