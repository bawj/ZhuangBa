package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;
import com.xiaomai.zhuangba.util.Util;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 */
public class PatrolMissionDetailListAdapter extends BaseQuickAdapter<PatrolMissionDetailListBean , BaseViewHolder> {


    public PatrolMissionDetailListAdapter() {
        super(R.layout.item_patrol_mission_detail , null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolMissionDetailListBean item) {
        //设备号
        TextView tvPatrolMissionEquipmentNumber = helper.getView(R.id.tvPatrolMissionEquipmentNumber);
        tvPatrolMissionEquipmentNumber.setText(mContext.getString(R.string.device_number , item.getEquipmentNo()));
        //订单状态
        TextView tvBasePatrolDetailType = helper.getView(R.id.tvBasePatrolDetailType);
        PatrolStatusUtil.masterPatrolMission(mContext , item.getStatus() , tvBasePatrolDetailType);
        //地址
        TextView tvItemInspectionAddress = helper.getView(R.id.tvItemInspectionAddress);
        String province = item.getProvince();
        String city = item.getCity();
        String street = item.getStreet();
        String address = item.getAddress();
        StringBuilder stringBuilder = new StringBuilder(province);
        stringBuilder.append(city).append(item.getRegion()).append(street).append(address);
        tvItemInspectionAddress.setText(stringBuilder);
        //A B C D 面
        TextView tvNoodles = helper.getView(R.id.tvNoodles);
        tvNoodles.setText(Util.getNoodles(item.getCover()));

        tvPatrolMissionEquipmentNumber.setTag(item);
    }
}
