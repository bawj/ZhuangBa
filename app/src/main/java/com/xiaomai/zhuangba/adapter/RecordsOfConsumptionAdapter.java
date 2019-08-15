package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;

/**
 * @author Administrator
 * @date 2019/8/14 0014
 *
 * 消费记录
 */
public class RecordsOfConsumptionAdapter extends BaseQuickAdapter<EmployerWalletDetailBean.ListBean, BaseViewHolder> {

    public RecordsOfConsumptionAdapter() {
        super(R.layout.item_base_employer_wallet_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployerWalletDetailBean.ListBean item) {
        TextView tvOrderCode = helper.getView(R.id.tvOrderCode);
        tvOrderCode.setText(item.getAccountNumber());
        TextView tvOrderTime = helper.getView(R.id.tvOrderTime);
        tvOrderTime.setText(item.getTimes());
        TextView tvOrderInfo = helper.getView(R.id.tvOrderInfo);
        tvOrderInfo.setText(mContext.getString(R.string.expenditure, String.valueOf(item.getAmount())));
        tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
    }
}
