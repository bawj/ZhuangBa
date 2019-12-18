package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 已出发
 */
public class HavingSetOutFragment extends BaseMasterOrderDetailFragment {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

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

    @Override
    public void masterOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.masterOngoingOrdersListSuccess(ongoingOrdersList);
        //是否申请了空跑
        setRightTitle(ongoingOrdersList.getFlag());
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
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                setRightTitle(1);
                refreshBaseList.autoRefresh();
            }
        }
    }


    private void setRightTitle(Integer flag) {
        QMUITopBarLayout topBarBase = getTopBarBase();
        topBarBase.removeAllRightViews();
        Button button = topBarBase.addRightTextButton(getString(R.string.empty_race_application), QMUIViewHelper.generateViewId());
        button.setText(getString(R.string.empty_race_application));
        if (flag != null && flag != 0){
            //发起过空跑
            button.setTextColor(getResources().getColor(R.color.tool_lib_gray_B1B1B1));
        }else {
            //没有发起过空跑
            button.setTextColor(getResources().getColor(R.color.tool_lib_gray_222222));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //空跑申请
                    startFragmentForResult(EmptyRaceApplicationFragment.newInstance(getOrderCode()) , ForResultCode.START_FOR_RESULT_CODE.getCode());
                }
            });
        }
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

}
