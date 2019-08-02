package com.xiaomai.zhuangba.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AliPayAccountBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class AliPayAccountAdapter extends BaseQuickAdapter<AliPayAccountBean, BaseViewHolder> {

    public AliPayAccountAdapter(@Nullable List<AliPayAccountBean> data) {
        super(R.layout.item_alipay_account, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AliPayAccountBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_account, item.getWithdrawalsAccount());
    }
}