package com.xiaomai.zhuangba.fragment.orderdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public class CompleteFragment extends BaseFragment {

    @BindView(R.id.ivComplete)
    ImageView ivComplete;
    @BindView(R.id.tvCompleteTip)
    TextView tvCompleteTip;

    public static CompleteFragment newInstance(String orderCode , String orderType , String orderStatus) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        args.putString(ConstantUtil.ORDER_TYPE , orderType);
        args.putString(ConstantUtil.ORDER_STATUS , orderStatus);
        CompleteFragment fragment = new CompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        ivComplete.setImageResource(getImageResource());
        tvCompleteTip.setText(getActivityTitle());
    }

    @OnClick({R.id.btnCompleteMissionDetails, R.id.btnCompleteBackHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteMissionDetails:
                //查看已接单详情
                startOrderDetail();
                break;
            case R.id.btnCompleteBackHome:
                //首页
                startFragment(MasterWorkerFragment.newInstance());
                break;
            default:
        }
    }

    public void startOrderDetail() {
        OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() ,getOrderCode()
                , getOrderType() , DensityUtils.stringTypeInteger(getOrderStatus()));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_complete;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.receipt_order_success);
    }

    public int getImageResource() {
        return R.drawable.bg_success;
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }

    public String getOrderCode(){
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    public String getOrderType(){
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_TYPE);
        }
        return "";
    }

    public String getOrderStatus(){
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_STATUS);
        }
        return "";
    }

    @Override
    public boolean isInSwipeBack() {
        return true;
    }
}
