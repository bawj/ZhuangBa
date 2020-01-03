package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.BusinessNeeds;
import com.xiaomai.zhuangba.enums.StaticExplain;

/**
 * @author Bawj
 * CreateDate:     2020/1/2 0002 13:29
 */
public class EmployerRealNameAuthenticationAdapter extends BaseQuickAdapter<BusinessNeeds , BaseViewHolder> {


    public EmployerRealNameAuthenticationAdapter() {
        super(R.layout.item_employer_real_name_authentication, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessNeeds item) {
        TextView tvEmployerReal = helper.getView(R.id.tvEmployerReal);
        tvEmployerReal.setText(item.getName());
        int flag = item.getFlag();
        if (flag == StaticExplain.EMPLOYER_NOT_CHECK.getCode()){
            tvEmployerReal.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gray));
            tvEmployerReal.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        }else if (flag == StaticExplain.EMPLOYER_IS_CHECK.getCode()){
            tvEmployerReal.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red));
            tvEmployerReal.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_E74C3C));
        }
    }
}
