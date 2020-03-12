package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.util.DateUtil;

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
        if (item.getOrderType().equals(StringTypeExplain.ADD_PROJECT.getCode())){
            //新增项目
            tvMemberPhone.setText(mContext.getString(R.string.add_project_application_ , initiateUser));
        }else if (item.getOrderType().equals(StringTypeExplain.CUSTOM_PROJECT.getCode())){
            //自定义项
            tvMemberPhone.setText(mContext.getString(R.string.custom_project_application_ , initiateUser));
        }else if (item.getOrderType().equals(StringTypeExplain.DRY_RUN_PROJECT.getCode())){
            //空跑
            tvMemberPhone.setText(mContext.getString(R.string.empty_race_application_ , initiateUser));
        }
        tvTeamDate.setText(DateUtil.getDate2String(DensityUtils.stringTypeLong(item.getInitiateDate()) , "yyyy-MM-dd HH:mm:ss"));
        tvMemberPhone.setTag(item);
    }
}
