package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.FrozenAmountBean;
import com.xiaomai.zhuangba.util.DateUtil;

import org.joda.time.DateTime;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 */
public class MasterFrozenAmountAdapter extends BaseQuickAdapter<FrozenAmountBean , BaseViewHolder> {

    public MasterFrozenAmountAdapter() {
        super(R.layout.item_master_frozen_amount, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, FrozenAmountBean item) {
        //订单编号
        TextView tvOrderNumber = helper.getView(R.id.tvOrderNumber);
        tvOrderNumber.setText(mContext.getString(R.string.order_number_symbol , item.getOrderCode()));
        //入账时间
        TextView tvAccountingTime = helper.getView(R.id.tvAccountingTime);
        DateTime dateTime = DateUtil.strToDate(item.getEnterTime(), "yyyy-MM-dd");
        if (dateTime != null){
            int dayOfYear = dateTime.getDayOfMonth();
            tvAccountingTime.setText(mContext.getString(R.string.accounting_time_symbol , String.valueOf(dayOfYear)));
        }
        //入账金额
        TextView tvAmountOfAccount = helper.getView(R.id.tvAmountOfAccount);
        tvAmountOfAccount.setText(mContext.getString(R.string.amount_of_account_symbol , String.valueOf(item.getAmount())));
    }
}
