package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.base.BaseEmployerDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 已接单
 */
public class AcceptedOrdersFragment extends BaseEmployerDetailFragment {

    /**
     * 接单师傅的头像
     */
    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    /**
     * 接单师傅的名称
     */
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;

    public static AcceptedOrdersFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        AcceptedOrdersFragment fragment = new AcceptedOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_accepted_orders;
    }

    @Override
    public void employerOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.employerOngoingOrdersListSuccess(ongoingOrdersList);
        String bareheadedPhotoUrl = ongoingOrdersList.getBareheadedPhotoUrl();
        //接受订单师傅
        String userText = ongoingOrdersList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        Log.e("师傅头像 " + bareheadedPhotoUrl);
        //接受订单师傅的头像
        GlideManager.loadCircleImage(getActivity(), bareheadedPhotoUrl,
                ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
    }

    @OnClick(R.id.btnCancelTask)
    public void onViewClicked() {
        CommonlyDialog.getInstance().initView(getActivity())
                .setTvDialogCommonlyContent(getString(R.string.cancel_order_employer_msg))
                .setICallBase(new CommonlyDialog.BaseCallback() {
                    @Override
                    public void sure() {
                        iModule.obtainCancelTask();
                    }
                }).showDialog();
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
}
