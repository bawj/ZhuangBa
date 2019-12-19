package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.DryRunOrder;

/**
 * @author Bawj
 * CreateDate:     2019/12/19 0019 11:27
 */
public class OrderFeedBackAdapter extends BaseQuickAdapter<DryRunOrder, BaseViewHolder> {

    public OrderFeedBackAdapter() {
        super(R.layout.item_order_feedback, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DryRunOrder item) {
        //申请人
        TextView tvMemberPhone = helper.getView(R.id.tvMemberPhone);
        //发起时间
        TextView tvTeamDate = helper.getView(R.id.tvTeamDate);
        String initiateUser = item.getInitiateUser();
        tvMemberPhone.setText(mContext.getString(R.string.empty_race_application_ , initiateUser));
        tvTeamDate.setText(item.getInitiateDate());
        tvMemberPhone.setTag(item);
    }
}
