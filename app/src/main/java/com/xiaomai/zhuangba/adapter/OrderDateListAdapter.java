package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.util.DateUtil;

/**
 * @author Administrator
 * @date 2019/7/15 0015
 */
public class OrderDateListAdapter extends BaseQuickAdapter<OrderDateList, BaseViewHolder> {

    public OrderDateListAdapter() {
        super(R.layout.item_order_date_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDateList item) {
        TextView tvItemOrderDateListCodeTxt = helper.getView(R.id.tvItemOrderDateListCodeTxt);
        TextView tvItemOrderDateListCode = helper.getView(R.id.tvItemOrderDateListCode);

        tvItemOrderDateListCodeTxt.setText(mContext.getString(R.string.colon,item.getTypeText()));
        if (item.getTypeText().equals(mContext.getString(R.string.order_code))){
            tvItemOrderDateListCode.setText(item.getOrderCode());
        }else{
            tvItemOrderDateListCode.setText(DateUtil.getDate2String(DensityUtils.stringTypeLong(item.getTime()) , "yyyy-MM-dd HH:mm:ss"));
        }
    }
}
