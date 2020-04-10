package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.data.bean.WalletOrderDetailBean;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/31 0031
 *
 * 提现详情 处理中
 */
public class WalletOrderInProcessingFragment extends WalletOrderDetailFragment{

    @BindView(R.id.tv_title)
    TextView tvTitle;

    public static WalletOrderInProcessingFragment newInstance(WalletDetailBean.ListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", bean);
        WalletOrderInProcessingFragment fragment = new WalletOrderInProcessingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.in_processing));
    }

    @Override
    public List<WalletOrderDetailBean> getList(WalletDetailBean.ListBean bean) {
        tvNumber.setText(String.format(getString(R.string.content_money), String.valueOf( bean.getAmount() == 0 ? bean.getMasterOrderAmount() : bean.getAmount() )));
        List<WalletOrderDetailBean> list = new ArrayList<>();
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), DateUtil.getDate2String(DensityUtils.stringTypeLong(bean.getModifyTime()) , "yyyy-MM-dd HH:mm:ss")));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_account), bean.getWithdrawalsAccount()));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_status), getString(R.string.in_processing)));
        return list;
    }
}
