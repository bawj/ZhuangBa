package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ProvincialBean;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class ProvincialAdapter extends BaseQuickAdapter<ProvincialBean, BaseViewHolder> {


    public ProvincialAdapter() {
        super(R.layout.item_provincial, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvincialBean item) {
        TextView tvProvincialName = helper.getView(R.id.tvProvincialName);
        tvProvincialName.setText(item.getName());
        tvProvincialName.setTag(item);
    }
}
