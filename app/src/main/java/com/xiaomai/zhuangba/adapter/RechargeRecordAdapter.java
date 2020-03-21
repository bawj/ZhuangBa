package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;
import com.xiaomai.zhuangba.util.DateUtil;

/**
 * @author Administrator
 * @date 2019/8/14 0014
 * 充值记录
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<EmployerWalletDetailBean.ListBean, BaseViewHolder> {

    public RechargeRecordAdapter() {
        super(R.layout.item_base_employer_wallet_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployerWalletDetailBean.ListBean item) {
        TextView tvOrderCode = helper.getView(R.id.tvOrderCode);
        tvOrderCode.setText(item.getAccountNumber());
        TextView tvOrderTime = helper.getView(R.id.tvOrderTime);
        tvOrderTime.setText(DateUtil.getDate2String(DensityUtils.stringTypeLong(item.getTimes()), "yyyy-MM-dd"));
        TextView tvOrderInfo = helper.getView(R.id.tvOrderInfo);
        tvOrderInfo.setText(mContext.getString(R.string.income, String.valueOf(item.getAmount())));
        tvOrderInfo.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_3AB960));

    }
}
