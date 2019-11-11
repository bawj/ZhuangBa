package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetAdapter extends BaseQuickAdapter<InspectionSheetBean , BaseViewHolder> {

    public InspectionSheetAdapter() {
        super(R.layout.item_inspection_sheet, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionSheetBean item) {
        //地址
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        String villageName = item.getVillageName();
        if (!TextUtils.isEmpty(villageName)) {
            tvItemOrdersTitle.setText(villageName);
        } else {
            String street = item.getStreet();
            tvItemOrdersTitle.setText(street);
        }
        //任务数量
        TextView tvInspectionNumber = helper.getView(R.id.tvInspectionNumber);
        int num = item.getNum();
        tvInspectionNumber.setText(mContext.getString(R.string.task_number_, String.valueOf(num)));
        //详细地址
        TextView tvItemInspectionAddress = helper.getView(R.id.tvItemInspectionAddress);
        //省
        String province = item.getProvince();
        //市
        String city = item.getCity();
        //区
        String district = item.getDistrict();
        //街道
        String street = item.getStreet();
        StringBuffer stringBuffer = new StringBuffer(province);
        stringBuffer.append(city).append(district).append(street).append(villageName);
        tvItemInspectionAddress.setText(mContext.getString(R.string.advertising_address , stringBuffer));

        tvItemOrdersTitle.setTag(item);
    }
}
