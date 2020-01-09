package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.Claim;

/**
 * @author Bawj
 * CreateDate: 2020/1/6 0006 16:14
 */
public class DefaultMoneyAdapter extends BaseQuickAdapter<Claim, BaseViewHolder> {

    public DefaultMoneyAdapter() {
        super(R.layout.item_default_money, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Claim claim) {
        TextView tvDefaultMoney = baseViewHolder.getView(R.id.tvDefaultMoney);
        TextView tvDefaultDate = baseViewHolder.getView(R.id.tvDefaultDate);
        TextView tvReasonsBreachContract = baseViewHolder.getView(R.id.tvReasonsBreachContract);
        tvDefaultMoney.setText(mContext.getString(R.string.content_money, String.valueOf(claim.getMasterPrice())));
        tvDefaultDate.setText(claim.getCreateTime());
        tvReasonsBreachContract.setText(mContext.getString(R.string.reasons_for_breach_of_contract , claim.getReason()));
    }
}
