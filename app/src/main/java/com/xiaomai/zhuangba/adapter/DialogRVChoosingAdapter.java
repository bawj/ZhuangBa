package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.Maintenance;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 */
public class DialogRVChoosingAdapter extends BaseQuickAdapter<Maintenance, BaseViewHolder> {

    private Integer id = -1;

    public DialogRVChoosingAdapter() {
        super(R.layout.item_dialog_choosing, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Maintenance item) {
        TextView tvItemChoosingMaintenance = helper.getView(R.id.tvItemChoosingMaintenance);
        if (String.valueOf(item.getId()).equals("-1")) {
            tvItemChoosingMaintenance.setText(mContext.getString(R.string.not_choosing_maintenance));
        } else {
            tvItemChoosingMaintenance.setText(mContext.getString(R.string.choosing_maintenance,
                    String.valueOf(item.getNumber()), String.valueOf(item.getAmount())));
        }
        if (String.valueOf(item.getId()).equals(String.valueOf(id))) {
            //选中
            tvItemChoosingMaintenance.setBackgroundResource(R.drawable.red_scope_of_ervice_bg);
            tvItemChoosingMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        } else {
            //未选中
            tvItemChoosingMaintenance.setBackgroundResource(R.drawable.gray_scope_of_ervice_bg);
            tvItemChoosingMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        }
    }

    public void checkItem(Integer id) {
        this.id = id;
        notifyDataSetChanged();
    }
}
