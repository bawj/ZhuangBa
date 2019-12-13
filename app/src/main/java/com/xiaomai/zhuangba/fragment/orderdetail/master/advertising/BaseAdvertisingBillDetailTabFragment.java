package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.manager.GlideManager;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.OrderDateList;

import java.util.List;

import butterknife.BindView;

public class BaseAdvertisingBillDetailTabFragment extends BaseFragment {

    public static final String DEVICE_SURFACE_INFORMATION = "device_Surface_Information";

    /**
     * 上刊
     */
    @BindView(R.id.layLastPhoto)
    LinearLayout layLastPhoto;
    @BindView(R.id.recyclerLastPhoto)
    RecyclerView recyclerLastPhoto;
    @BindView(R.id.tvLastPhotoRemark)
    TextView tvLastPhotoRemark;
    /**
     * 下刊
     */
    @BindView(R.id.layNextIssuePhoto)
    LinearLayout layNextIssuePhoto;
    @BindView(R.id.recyclerNextIssuePhoto)
    RecyclerView recyclerNextIssuePhoto;
    @BindView(R.id.tvNextIssuePhotoRemark)
    TextView tvNextIssuePhotoRemark;
    /**
     * 旧广告
     */
    @BindView(R.id.tvOldAdvertisementName)
    TextView tvOldAdvertisementName;
    @BindView(R.id.ivOldAdvertisement)
    ImageView ivOldAdvertisement;
    /**
     * 新广告
     */
    @BindView(R.id.tvNewAdvertisementName)
    TextView tvNewAdvertisementName;
    @BindView(R.id.ivNewAdvertisement)
    ImageView ivNewAdvertisement;
    /**
     * 订单信息
     */
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;

    public static BaseAdvertisingBillDetailTabFragment newInstance(String deviceSurfaceInformationString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        BaseAdvertisingBillDetailTabFragment fragment = new BaseAdvertisingBillDetailTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
        // TODO: 2019/12/11 0011  上刊照片
        layLastPhoto.setVisibility(View.GONE);
        // TODO: 2019/12/11 0011 下刊照片
        layNextIssuePhoto.setVisibility(View.GONE);
        //旧广告
        String oldAdName = deviceSurfaceInformation.getOldAdName();
        tvOldAdvertisementName.setText(getString(R.string.colon, oldAdName));
        String oldAdUrl = deviceSurfaceInformation.getOldAdUrl();
        GlideManager.loadImage(getActivity() , oldAdUrl , ivOldAdvertisement);
        //新广告
        String newAdName = deviceSurfaceInformation.getNewAdName();
        tvNewAdvertisementName.setText(getString(R.string.colon, newAdName));
        String newAdUrl = deviceSurfaceInformation.getNewAdUrl();
        GlideManager.loadImage(getActivity() , newAdUrl , ivNewAdvertisement);
        //订单信息
        String orderCode = deviceSurfaceInformation.getOrderCode();
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderDateListAdapter orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);
        //订单时间信息
        List<OrderDateList> orderDateList = deviceSurfaceInformation.getOrderDateList();
        orderDateList.add(0, new OrderDateList(orderCode, "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateList);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertising_bill_detail_tab;
    }

    private DeviceSurfaceInformation getDeviceSurfaceInformation() {
        if (getArguments() != null) {
            String string = getArguments().getString(DEVICE_SURFACE_INFORMATION);
            return new Gson().fromJson(string, DeviceSurfaceInformation.class);
        }
        return new DeviceSurfaceInformation();
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
