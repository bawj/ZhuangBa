package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.WalletDetailBean;
import com.xiaomai.zhuangba.data.WalletOrderDetailBean;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/30 0030
 */
public class WalletOrderDetailFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private int type;
    private WalletDetailBean.ListBean bean;

    public static WalletOrderDetailFragment newInstance(WalletDetailBean.ListBean bean, int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", bean);
        bundle.putInt("type", type);
        WalletOrderDetailFragment fragment = new WalletOrderDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getArguments() != null){
            bean = (WalletDetailBean.ListBean) getArguments().getSerializable("date");
           if (bean != null){
               tvTitle.setText(getTitle(bean));
               tvNumber.setText(String.format(getString(R.string.content_money), getAmount(bean)));
               recyclerView.setAdapter(new DetailAdapter(getList(bean)));
           }
        }
    }

    @SuppressLint("StringFormatMatches")
    private List<WalletOrderDetailBean> getList(WalletDetailBean.ListBean bean) {
        List<WalletOrderDetailBean> list = new ArrayList<>();

        if (type == WalletOrderTypeEnum.DETAIL_OUT.getCode() || bean.getStreamType() == 2) {
            //从明细进入详情与从提现进入详情，取值字段不一样
            if (type == WalletOrderTypeEnum.DETAIL_OUT.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), bean.getTimes()));
            } else if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), bean.getModifyTime()));
            }
            list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_account), bean.getWithdrawalsAccount()));

            //提现中
            if (bean.getWallerType() == 6) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_status), getString(R.string.in_processing)));
                return list;
            } else {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_status), getString(R.string.wallet_out_success)));
            }

            if (type == WalletOrderTypeEnum.DETAIL_OUT.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_code), bean.getAccountNumber()));
            } else if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_code), bean.getOrderCode()));
            }

            if (type == WalletOrderTypeEnum.DETAIL_OUT.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), bean.getTimes()));
            } else if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
                list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), bean.getModifyTime()));
            }
        } else if (type == WalletOrderTypeEnum.DETAIL_INCOME.getCode() || bean.getStreamType() == 1) {
            list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_code), bean.getOrderCode()));
            list.add(new WalletOrderDetailBean(getString(R.string.wallet_order_money), String.format(getString(R.string.content_money), bean.getOrderAmount())));
            list.add(new WalletOrderDetailBean(getString(R.string.wallet_income_scale), getScale(bean.getOrderAmount(), bean.getMasterOrderAmount())));
        }
        return list;
    }

    /**
     * 获取标题
     *
     * @param bean
     * @return
     */
    private String getTitle(WalletDetailBean.ListBean bean) {
        String str = "";
        if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
            str = bean.getStreamType() == 2 ? getString(R.string.wallet_detail_out) : getString(R.string.wallet_income_number);
        } else {
            str = type == WalletOrderTypeEnum.DETAIL_OUT.getCode() ? getString(R.string.wallet_detail_out) : getString(R.string.wallet_income_number);
        }
        return str;
    }

    private String getAmount(WalletDetailBean.ListBean bean) {
        String str = "";
        if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
            str = bean.getStreamType() == 2 ? "" + bean.getOrderAmount() : "" + bean.getMasterOrderAmount();
        } else {
            str = type == WalletOrderTypeEnum.DETAIL_OUT.getCode() ? "" + bean.getAmount() : "" + bean.getMasterOrderAmount();
        }
        return str;
    }

    class DetailAdapter extends BaseQuickAdapter<WalletOrderDetailBean, BaseViewHolder> {
        public DetailAdapter(List<WalletOrderDetailBean> list) {
            super(R.layout.item_wallet_order_detail, list);
        }
        @Override
        protected void convert(BaseViewHolder helper, WalletOrderDetailBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_content, item.getContent());
        }
    }

    /**
     * 分成比例
     */
    private String getScale(double amount, double masterOrderAmount) {
        double scale = 1;
        if (amount != 0){
            scale = masterOrderAmount / amount;
            BigDecimal b = new BigDecimal(scale);
            scale = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return scale * 100 + "%";
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_wallet_order_detail;
    }

    @Override
    protected String getActivityTitle() {
        type = getArguments().getInt("type");
        if (bean != null) {
            int streamType = bean.getStreamType();
            type = streamType != 2 ?  WalletOrderTypeEnum.DETAIL_INCOME.getCode() : type;
        }
        return type == WalletOrderTypeEnum.DETAIL_INCOME.getCode() ? getString(R.string.wallet_income_detail) : getString(R.string.wallet_withdraw_detail);
    }
}
