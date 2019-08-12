package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 已出发
 */
public class HavingSetOutFragment extends BaseMasterOrderDetailFragment {

    public static HavingSetOutFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        HavingSetOutFragment fragment = new HavingSetOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_having_set_out;
    }

    @OnClick({R.id.btnNewTaskCancelTheOrder, R.id.btnStartConstruction})
    public void onViewHavingSetOutClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNewTaskCancelTheOrder:
                CommonlyDialog.getInstance().initView(getActivity())
                        .setTvDialogCommonlyContent(getString(R.string.cancel_order_tip))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                iModule.requestCancelOrder();
                            }
                        }).showDialog();
                break;
            case R.id.btnStartConstruction:
                //开始施工 签名 拍照
                startFragment(StartConstructionFragment.newInstance(getOrderCode()));
                break;
            default:
        }
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

}
