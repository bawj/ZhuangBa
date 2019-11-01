package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.data.bean.WalletOrderDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/31 0031
 *
 * 收入详情
 */
public class WalletOrderIncomeDetailsFragment extends WalletOrderDetailFragment{

    @BindView(R.id.tv_title)
    TextView tvTitle;

    public static WalletOrderIncomeDetailsFragment newInstance(WalletDetailBean.ListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", bean);
        WalletOrderIncomeDetailsFragment fragment = new WalletOrderIncomeDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.wallet_income_number));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public List<WalletOrderDetailBean> getList(WalletDetailBean.ListBean bean) {
        List<WalletOrderDetailBean> list = new ArrayList<>();
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_code), bean.getOrderCode()));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_order_money), String.format(getString(R.string.content_money), bean.getOrderAmount())));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_income_scale), getScale(bean.getOrderAmount(), bean.getMasterOrderAmount())));
        return list;
    }
}
