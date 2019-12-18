package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.Cause;

/**
 * @Author: Bawj
 * @CreateDate: 2019/12/17 0017 11:50
 * 不通过理由
 */
public class DialogNoPassAdapter extends BaseQuickAdapter<Cause, BaseViewHolder> {

    /**
     * 选中状态
     */
    private int position = -1;

    public DialogNoPassAdapter() {
        super(R.layout.item_dialog_no_pass, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Cause item) {
        int adapterPosition = helper.getAdapterPosition();
        TextView tvReason = helper.getView(R.id.tvReason);
        tvReason.setText(item.getCause());
        if (adapterPosition == position) {
            tvReason.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_E74C3C));
            tvReason.setBackground(mContext.getResources().getDrawable(R.drawable.red_bg));
        } else {
            tvReason.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_222222));
            tvReason.setBackground(mContext.getResources().getDrawable(R.drawable.gray_field_description_bg));
        }
        tvReason.setTag(item);
    }

    public void setCheck(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

}
