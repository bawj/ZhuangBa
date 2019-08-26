package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdvertisingReplacementDetailBean;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.MaintenanceUtil;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class AdvertisingReplacementDetailAdapter extends BaseQuickAdapter<AdvertisingReplacementDetailBean , BaseViewHolder> {

    public AdvertisingReplacementDetailAdapter() {
        super(R.layout.item_advertising_replacement_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisingReplacementDetailBean item) {
        //设备编号
        TextView tvEquipmentNumber = helper.getView(R.id.tvEquipmentNumber);
        tvEquipmentNumber.setText(item.getEquipmentNum());
        //状态 已完成 或 施工中
        TextView tvItemAdvertisingReplacementType = helper.getView(R.id.tvItemAdvertisingReplacementType);
        MaintenanceUtil.maintenanceType(mContext, item.getMakeEndTIme(), tvItemAdvertisingReplacementType);
        //跟换位置
        TextView tvChangePlaces = helper.getView(R.id.tvChangePlaces);
        tvChangePlaces.setText(item.getEquipmentSurface());
        //订单号
        TextView tvOrderNumber = helper.getView(R.id.tvOrderNumber);
        tvOrderNumber.setText(item.getOrderCode());
        //更换定位
        TextView tvPositioning = helper.getView(R.id.tvPositioning);
        tvPositioning.setText(item.getAddress());
        //预约时间
        TextView tvDateAppointment = helper.getView(R.id.tvDateAppointment);
        String makeStartTime = DateUtil.dateToFormat(item.getMakeStartTime() , "yyyy-MM-dd HH:mm:ss" , "yyyy/MM/dd HH:mm:ss");
        String makeEndTIme = DateUtil.dateToFormat(item.getMakeEndTIme() , "yyyy-MM-dd HH:mm:ss" , "yyyy/MM/dd HH:mm:ss");
        tvDateAppointment.setText(mContext.getString(R.string.symbol , makeStartTime , makeEndTIme));
    }
}
