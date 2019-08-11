package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.DataDetailsBean;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 */
public class TabDataDetailsTitleAdapter extends BaseQuickAdapter<DataDetailsBean, BaseViewHolder> {

    private String check = "";

    public TabDataDetailsTitleAdapter() {
        super(R.layout.item_tab_data_details_title, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataDetailsBean item) {
        TextView tvTabDataDetail = helper.getView(R.id.tvTabDataDetail);
        tvTabDataDetail.setText(item.getData());
        tvTabDataDetail.setTag(item);
        if (check.equals(item.getData())){
            tvTabDataDetail.setEnabled(true);
            tvTabDataDetail.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_222222));
        }else {
            tvTabDataDetail.setEnabled(false);
            tvTabDataDetail.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_BBBCC1));
        }
    }

    public void setCheck(String check){
        this.check = check;
        notifyDataSetChanged();
    }
}
