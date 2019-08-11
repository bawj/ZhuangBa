package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.base.BaseEmployerDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 *
 * 已取消
 */
public class EmployerCancelledFragment extends BaseEmployerDetailFragment {

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

    public static EmployerCancelledFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        EmployerCancelledFragment fragment = new EmployerCancelledFragment();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_cancelled;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
    @Override
    public void leftBackClick() {
        statusBarWhite();
        super.leftBackClick();
    }
}
