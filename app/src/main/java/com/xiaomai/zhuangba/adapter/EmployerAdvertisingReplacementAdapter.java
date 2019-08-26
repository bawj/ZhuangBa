package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerAdvertisingReplacement;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class EmployerAdvertisingReplacementAdapter extends BaseQuickAdapter<EmployerAdvertisingReplacement, BaseViewHolder> {

    public EmployerAdvertisingReplacementAdapter() {
        super(R.layout.item_employer_advertising_replacement , null);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployerAdvertisingReplacement item) {
        //标题
        TextView tvItemAdvertisingReplacementTitle = helper.getView(R.id.tvItemAdvertisingReplacementTitle);
        tvItemAdvertisingReplacementTitle.setText(mContext.getString(R.string.advertising_replacement));
        //维护周期
        TextView tvMaintenanceCycle = helper.getView(R.id.tvMaintenanceCycle);
        tvMaintenanceCycle.setText(mContext.getString(R.string.maintenance_cycle_date , item.getOrderTime()));
        //服务备注
        TextView tvServiceRemarks = helper.getView(R.id.tvServiceRemarks);
        tvServiceRemarks.setText(item.getRemark());
        //发布时间
        TextView tvReleaseDate = helper.getView(R.id.tvReleaseDate);
        tvReleaseDate.setText(item.getPublishTime());
        //批量编号
        TextView tvBatchNumber = helper.getView(R.id.tvBatchNumber);
        tvBatchNumber.setText(item.getBatchCode());
        //共x项
        TextView tvNumber = helper.getView(R.id.tvNumber);
        tvNumber.setText(mContext.getString(R.string.several_items , String.valueOf(item.getCount())));
        //总计
        TextView tvAdvertisingMoney = helper.getView(R.id.tvAdvertisingMoney);
        tvAdvertisingMoney.setText(mContext.getString(R.string.content_money , String.valueOf(item.getSumMoney())));
        tvAdvertisingMoney.setTag(item);
    }
}
