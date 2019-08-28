package com.xiaomai.zhuangba.fragment.advertisement.employer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 *
 * 已取消
 */
public class EmployerAdvertisementCancelledFragment extends BaseAdvertisementFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;

    public static EmployerAdvertisementCancelledFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerAdvertisementCancelledFragment fragment = new EmployerAdvertisementCancelledFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setOngoingOrder(OngoingOrdersList ongoingOrdersList, List<OrderDateList> orderDateLists) {
        super.setOngoingOrder(ongoingOrdersList, orderDateLists);
        GlideManager.loadImage(getActivity() , ongoingOrdersList.getBareheadedPhotoUrl() , ivEmployerDetailMasterHeader , R.drawable.bg_def_head);
        tvEmployerDetailMasterName.setText(ongoingOrdersList.getUserText());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_cancelled;
    }
}
