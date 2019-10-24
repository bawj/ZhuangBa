package com.xiaomai.zhuangba.fragment.advertisement.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/29 0029
 *
 * 广告单 订单池详情
 */
public class MasterAdvertisementOrderPoolAdDetailFragment extends BaseAdvertisementFragment {


    @BindView(R.id.tvBaseOrderDetailItemOrdersType)
    TextView tvBaseOrderDetailItemOrdersType;

    public static MasterAdvertisementOrderPoolAdDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementOrderPoolAdDetailFragment fragment = new MasterAdvertisementOrderPoolAdDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        super.requestOrderDetailSuccess(object);
        tvBaseOrderDetailItemOrdersType.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestAdvertisementOrderPoolOrderDetail();
    }
}
