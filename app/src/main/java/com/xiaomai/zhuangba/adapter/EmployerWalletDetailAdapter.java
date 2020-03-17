package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class EmployerWalletDetailAdapter extends BaseQuickAdapter<EmployerWalletDetailBean.ListBean, BaseViewHolder> {

    public EmployerWalletDetailAdapter() {
        super(R.layout.item_base_employer_wallet_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployerWalletDetailBean.ListBean item) {
        TextView tvOrderCode = helper.getView(R.id.tvOrderCode);
        tvOrderCode.setText(item.getAccountNumber());
        TextView tvOrderTime = helper.getView(R.id.tvOrderTime);
        tvOrderTime.setText(item.getTimes());
        TextView tvOrderInfo = helper.getView(R.id.tvOrderInfo);
        int wallerType = item.getWallerType();

        //-  22 23 29  30 31 32 34 36 37  38 39 41 42
        //+ 24 25 26 27 28  40
        if (wallerType == 3 || wallerType == 8 || wallerType == 10 || wallerType == 11 || wallerType == 13
                || wallerType == 22 || wallerType == 23 || wallerType == 29 || wallerType == 30 || wallerType == 31
                || wallerType == 32 || wallerType == 34 || wallerType == 36 || wallerType == 37 || wallerType == 38
                || wallerType == 39|| wallerType == 41|| wallerType == 42) {
            tvOrderInfo.setText(mContext.getString(R.string.expenditure, String.valueOf(item.getAmount())));
            tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        } else if (wallerType == 1 ||wallerType == 4 || wallerType == 7 || wallerType == 9 || wallerType == 14 || wallerType == 19
                || wallerType == 24 || wallerType == 25 || wallerType == 26 || wallerType == 27 || wallerType == 28
                || wallerType == 40) {
            tvOrderInfo.setText(mContext.getString(R.string.income, String.valueOf(item.getAmount())));
            tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_3AB960));
        }
    }
}
