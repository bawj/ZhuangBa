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
public class EmployerWalletDetailAdapter extends BaseQuickAdapter<EmployerWalletDetailBean.ListBean , BaseViewHolder> {

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

        if (item.getWallerType() == 3 || item.getWallerType() == 8 || item.getWallerType() == 10 || item.getWallerType() == 11){
            tvOrderInfo.setText(mContext.getString(R.string.expenditure , String.valueOf(item.getAmount())));
            tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        }else if (item.getWallerType() == 4 || item.getWallerType() == 7 || item.getWallerType() == 9){
            tvOrderInfo.setText(mContext.getString(R.string.income , String.valueOf(item.getAmount())));
            tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_3AB960));
        }
    }
}
