package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.manager.GlideManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NextLastIssuePhotoAdapter;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.ServiceSampleEntity;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
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
    @BindView(R.id.tvLastPhoto)
    TextView tvLastPhoto;
    /**
     * 下刊
     */
    @BindView(R.id.layNextIssuePhoto)
    LinearLayout layNextIssuePhoto;
    @BindView(R.id.recyclerNextIssuePhoto)
    RecyclerView recyclerNextIssuePhoto;
    @BindView(R.id.tvNextIssuePhotoRemark)
    TextView tvNextIssuePhotoRemark;
    @BindView(R.id.tvNextIssuePhoto)
    TextView tvNextIssuePhoto;
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
    @BindView(R.id.tvNextLastTip)
    TextView tvNextLastTip;
    @BindView(R.id.layNextLastTip)
    LinearLayout layNextLastTip;

    public static final String ORDER_STATUS = "order_status";
    public static BaseAdvertisingBillDetailTabFragment newInstance(String deviceSurfaceInformationString , int status) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        args.putInt(ORDER_STATUS , status);
        BaseAdvertisingBillDetailTabFragment fragment = new BaseAdvertisingBillDetailTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<ServiceSampleEntity> sampleEntityList = new ArrayList<>();
    @Override
    public void initView() {
        DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
        //下刊照片
        String nextIssuePhotos = deviceSurfaceInformation.getNextIssuePhotos();
        if (TextUtils.isEmpty(nextIssuePhotos)){
            tvNextIssuePhoto.setVisibility(View.GONE);
            recyclerNextIssuePhoto.setVisibility(View.GONE);
        }else {
            tvNextIssuePhoto.setVisibility(View.VISIBLE);
            recyclerNextIssuePhoto.setVisibility(View.VISIBLE);
            recyclerNextIssuePhoto.setLayoutManager(new GridLayoutManager(getActivity() , 4));
            recyclerNextIssuePhoto.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
            try {
                sampleEntityList= new Gson().fromJson(nextIssuePhotos, new TypeToken<List<ServiceSampleEntity>>(){}.getType());
            }catch (Exception e){
                e.printStackTrace();
            }
            final NextLastIssuePhotoAdapter nextIssuePhotoAdapter = new NextLastIssuePhotoAdapter();
            recyclerNextIssuePhoto.setAdapter(nextIssuePhotoAdapter);
            nextIssuePhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ArrayList<String> url = new ArrayList<>();
                    List<ServiceSampleEntity> data = nextIssuePhotoAdapter.getData();
                    for (ServiceSampleEntity serviceSampleEntity : data) {
                        url.add(serviceSampleEntity.getPicUrl());
                    }
                    if (url != null) {
                        startFragment(ImgPreviewFragment.newInstance(position, url));
                    }
                }
            });
            nextIssuePhotoAdapter.setNewData(sampleEntityList);
        }
        //下刊备注
        String nextIssueRemark = deviceSurfaceInformation.getNextIssueRemark();
        if (TextUtils.isEmpty(nextIssueRemark)){
            tvNextIssuePhotoRemark.setVisibility(View.GONE);
        }else {
            tvNextIssuePhotoRemark.setVisibility(View.VISIBLE);
            tvNextIssuePhotoRemark.setText(nextIssueRemark);
        }

        //上刊照片
        String publishedPhotos = deviceSurfaceInformation.getPublishedPhotos();
        if (TextUtils.isEmpty(publishedPhotos)){
            tvLastPhoto.setVisibility(View.GONE);
            recyclerLastPhoto.setVisibility(View.GONE);
        }else {
            tvLastPhoto.setVisibility(View.VISIBLE);
            recyclerLastPhoto.setVisibility(View.VISIBLE);
            recyclerLastPhoto.setLayoutManager(new GridLayoutManager(getActivity() , 4));
            recyclerLastPhoto.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
            try {
                sampleEntityList= new Gson().fromJson(publishedPhotos, new TypeToken<List<ServiceSampleEntity>>(){}.getType());
            }catch (Exception e){
                e.printStackTrace();
            }
            final NextLastIssuePhotoAdapter nextIssuePhotoAdapter = new NextLastIssuePhotoAdapter();
            recyclerLastPhoto.setAdapter(nextIssuePhotoAdapter);
            nextIssuePhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    List<ServiceSampleEntity> data = nextIssuePhotoAdapter.getData();
                    ArrayList<String> url = new ArrayList<>();
                    for (ServiceSampleEntity serviceSampleEntity : data) {
                        url.add(serviceSampleEntity.getPicUrl());
                    }
                    if (url != null) {
                        startFragment(ImgPreviewFragment.newInstance(position, url));
                    }
                }
            });
            nextIssuePhotoAdapter.setNewData(sampleEntityList);
        }
        //上刊备注
        String publishedRemark = deviceSurfaceInformation.getPublishedRemark();
        if (TextUtils.isEmpty(publishedRemark)){
            tvLastPhotoRemark.setVisibility(View.GONE);
        }else {
            tvLastPhotoRemark.setVisibility(View.VISIBLE);
            tvLastPhotoRemark.setText(publishedRemark);
        }

        //旧广告
        String oldAdName = deviceSurfaceInformation.getOldAdName();
        tvOldAdvertisementName.setText(getString(R.string.colon, (TextUtils.isEmpty(oldAdName)? getString(R.string.old_advertisement_) : oldAdName)));
        String oldAdUrl = deviceSurfaceInformation.getOldAdUrl();
        GlideManager.loadImage(getActivity() , oldAdUrl , ivOldAdvertisement);
        if (TextUtils.isEmpty(oldAdUrl)){
            ivOldAdvertisement.setVisibility(View.GONE);
            tvOldAdvertisementName.setVisibility(View.GONE);
        }

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

        //上刊或下刊描述
        String nextLastTip = "";
        String type = deviceSurfaceInformation.getType();
        if (type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE_OR_NEXT_NOODLES.getCode()))){
            nextLastTip = getString(R.string.last_report_and_next_report_required);
        }else if (type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_NEXT_ISSUE_NOODLES.getCode()))){
            nextLastTip = getString(R.string.last_report_report_required);
        }else if (type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE_NOODLES.getCode()))){
            nextLastTip = getString(R.string.last_next_report_required);
        }
        if (TextUtils.isEmpty(nextLastTip)){
            layNextLastTip.setVisibility(View.GONE);
        }else {
            tvNextLastTip.setText(nextLastTip);
            layNextLastTip.setVisibility(View.VISIBLE);
        }

        if (getOrderStatus() == AdvertisingEnum.EMPLOYER_ACCEPTANCE.getCode() || getOrderStatus() == AdvertisingEnum.EMPLOYER_CANCELLED.getCode()){
            layNextLastTip.setVisibility(View.GONE);
        }else {
            layNextLastTip.setVisibility(View.VISIBLE);
        }
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

    public int getOrderStatus(){
        if (getArguments() != null){
            return getArguments().getInt(ORDER_STATUS);
        }
        return 0;
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
