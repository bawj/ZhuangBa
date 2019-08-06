package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.base.BaseEmployerDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 雇主端分配中
 */
public class EmployerDistributionFragment extends BaseEmployerDetailFragment {

    @BindView(R.id.tvBaseOrderDetailName)
    TextView tvBaseOrderDetailName;
    @BindView(R.id.tvBaseOrderDetailPhone)
    TextView tvBaseOrderDetailPhone;

    public static EmployerDistributionFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        EmployerDistributionFragment fragment = new EmployerDistributionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_distribution;
    }

    @OnClick({R.id.btnCancelTheOrder, R.id.btnRelaunch})
    public void onViewEmployerDistributionClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelTheOrder:
                //取消任务
                CommonlyDialog.getInstance().initView(getActivity())
                        .setTvDialogCommonlyContent(getString(R.string.cancel_order_employer_msg))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                iModule.obtainCancelTask();
                            }
                        }).showDialog();
                break;
            case R.id.btnRelaunch:
                //更改信息重新发布
                startFragment(OrderInformationFragment.newInstance(getOrderCode(),
                        tvBaseOrderDetailName.getText().toString(),
                        tvBaseOrderDetailPhone.getText().toString()));
                break;
            default:
        }
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
}
