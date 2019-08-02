package com.xiaomai.zhuangba.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.SkillList;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class ScopeOfServiceAdapter extends BaseQuickAdapter<SkillList, BaseViewHolder> {

    public ScopeOfServiceAdapter() {
        super(R.layout.item_scope_of_service, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SkillList item) {
        TextView tvItemScopeOfService = helper.getView(R.id.tvItemScopeOfService);
        tvItemScopeOfService.setText(item.getSkillText());
        if (item.getPhoneNumber() != null){
            //选中
            tvItemScopeOfService.setBackgroundResource(R.drawable.red_scope_of_ervice_bg);
            tvItemScopeOfService.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        }else{
            //未选中
            tvItemScopeOfService.setBackgroundResource(R.drawable.gray_scope_of_ervice_bg);
            tvItemScopeOfService.setTextColor(Color.BLACK);
        }
        tvItemScopeOfService.setTag(item);
    }
}
