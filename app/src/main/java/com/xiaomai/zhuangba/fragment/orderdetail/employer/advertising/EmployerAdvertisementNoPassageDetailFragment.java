package com.xiaomai.zhuangba.fragment.orderdetail.employer.advertising;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.weight.dialog.CompensateDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 11:23
 * 广告单验收不通过
 */
public class EmployerAdvertisementNoPassageDetailFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;
    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;
    @BindView(R.id.recyclerNearbyPhoto)
    RecyclerView recyclerNearbyPhoto;

    private List<DeviceSurfaceInformation> list;

    public static EmployerAdvertisementNoPassageDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        EmployerAdvertisementNoPassageDetailFragment fragment = new EmployerAdvertisementNoPassageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        relNewTaskOrderDetailBottom.setVisibility(View.GONE);
        recyclerNearbyPhoto.setVisibility(View.GONE);
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        list = adOrderInformationList.getList();
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
        //师傅名称
        String userText = adOrderInformationList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        String bareheadedPhotoUrl = adOrderInformationList.getBareheadedPhotoUrl();
        GlideManager.loadCircleImage(getActivity() , bareheadedPhotoUrl , ivEmployerDetailMasterHeader , R.drawable.bg_def_head);

        tvBaseAdvertisementMoney.setText(getString(R.string.content_money, String.valueOf(adOrderInformationList.getOrderAmount())));
    }

    @OnClick({R.id.layCompensate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layCompensate:
                //发起索赔
                new CompensateDialog<EmployerAdvertisementNoPassageDetailFragment>()
                        .initView(getActivity(), this)
                        .setCompensate(getActivity(), list)
                        .showDialog();
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_no_passage_detail;
    }

}
