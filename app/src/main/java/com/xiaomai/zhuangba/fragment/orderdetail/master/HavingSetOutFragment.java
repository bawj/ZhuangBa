package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.fragment.service.LocationFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 已出发
 */
public class HavingSetOutFragment extends BaseMasterOrderDetailFragment {

    public static HavingSetOutFragment newInstance(String orderCode , String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
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
                RxPermissionsUtils.applyPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        startFragment(StartConstructionFragment.newInstance(getOrderCode(),getOrderType()));
                    }
                    @Override
                    public void onFail(Object obj) {
                        super.onFail(obj);
                        showToast(getString(R.string.positioning_authority_tip));
                    }
                });
                break;
            default:
        }
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

}
