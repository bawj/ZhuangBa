package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.BaseViewPagerAdapter;
import com.xiaomai.zhuangba.adapter.NearbyPhotoAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.EquipmentSurfaceRules;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.orderdetail.master.EquipmentSurfaceRulesFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.dialog.EquipmentSurfaceDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class BaseAdvertisingBillDetailFragment extends BaseFragment implements OnRefreshListener {

    public static final String ORDER_CODES = "order_codes";

    @BindView(R.id.layOrderInfo)
    LinearLayout layOrderInfo;
    @BindView(R.id.tvBaseOrderDetailItemOrdersTitle)
    TextView tvBaseOrderDetailItemOrdersTitle;
    @BindView(R.id.tvBaseEquipmentNumber)
    TextView tvBaseEquipmentNumber;
    @BindView(R.id.tvBaseAdvertisementMoney)
    TextView tvBaseAdvertisementMoney;
    @BindView(R.id.tvBaseAdvertisementChangePlaces)
    TextView tvBaseAdvertisementChangePlaces;
    @BindView(R.id.tvBaseAdvertisementBatchQueryNumber)
    TextView tvBaseAdvertisementBatchQueryNumber;
    @BindView(R.id.tvBaseAdvertisementDateOfAppointment)
    TextView tvBaseAdvertisementDateOfAppointment;
    @BindView(R.id.tvBaseAdvertisementServiceCycle)
    TextView tvBaseAdvertisementServiceCycle;
    @BindView(R.id.tvBaseAdvertisementNotes)
    TextView tvBaseAdvertisementNotes;
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    @BindView(R.id.tvBaseOrderDetailItemOrdersType)
    TextView tvBaseOrderDetailItemOrdersType;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.recyclerNearbyPhoto)
    RecyclerView recyclerNearbyPhoto;

    private AdOrderInformation adOrderInformationList;

    public static BaseAdvertisingBillDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        BaseAdvertisingBillDetailFragment fragment = new BaseAdvertisingBillDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        layOrderInfo.setVisibility(View.GONE);
        refreshBaseList.setOnRefreshListener(this);
        recyclerNearbyPhoto.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerNearbyPhoto.addItemDecoration(new GridSpacingItemDecoration(3, 32, false));
        //默认刷新
        refreshBaseList.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        String orderCodes = getOrderCodes();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdEquipmentInformation(orderCodes))
                .compose(this.<HttpResult<AdOrderInformation>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<AdOrderInformation>() {
                    @Override
                    protected void onSuccess(AdOrderInformation adOrderInformationList) {
                        setViewData(adOrderInformationList);
                        refreshBaseList.finishRefresh();
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        refreshBaseList.finishRefresh();
                    }
                });
    }

    public void setViewData(AdOrderInformation adOrderInformationList) {
        this.adOrderInformationList = adOrderInformationList;
        layOrderInfo.setVisibility(View.VISIBLE);
        setNearbyPhoto(adOrderInformationList);
        //订单状态
        AdvertisingStatusUtil.masterStatus(getContext(), adOrderInformationList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        //设备编号 标题
        tvBaseOrderDetailItemOrdersTitle.setText(adOrderInformationList.getEquipmentNum());
        tvBaseEquipmentNumber.setText(adOrderInformationList.getEquipmentNum());
        //金额
        tvBaseAdvertisementMoney.setText(getString(R.string.content_money, String.valueOf(adOrderInformationList.getMasterOrderAmount())));
        //更换位置
        tvBaseAdvertisementChangePlaces.setText(adOrderInformationList.getEquipmentSurface());
        //批量查询号
        tvBaseAdvertisementBatchQueryNumber.setText(adOrderInformationList.getBatchCode());
        //预约时间
        tvBaseAdvertisementDateOfAppointment.setText(adOrderInformationList.getReservation());
        //服务周期
        tvBaseAdvertisementServiceCycle.setText(adOrderInformationList.getServiceCycle());
        //备注说明
        tvBaseAdvertisementNotes.setText(adOrderInformationList.getRemark());
        //地址
        tvBaseOrderDetailLocation.setText(adOrderInformationList.getAddress());
        //生成 ‘面’的tab 页
        List<DeviceSurfaceInformation> list = adOrderInformationList.getList();
        initTab(list);

        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String role = userInfo.getRole();
        //已选择角色
        if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端 查询设备面规则
            queryEquipmentSurfaceRules(adOrderInformationList);
        }
    }

    private void queryEquipmentSurfaceRules(final AdOrderInformation adOrderInformationList) {
        //获取设备规则
        Integer serviceId = adOrderInformationList.getServiceId();
        RxUtils.getObservableZip(ServiceUrl.getUserApi().getDeviceSurfaceRules(String.valueOf(serviceId), 1))
                .compose(this.<HttpResult<EquipmentSurfaceRules>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<EquipmentSurfaceRules>(getActivity()) {
                    @Override
                    protected void onSuccess(EquipmentSurfaceRules equipmentSurfaceRules) {
                        //opupFlag 是否是弹框0：否；1：是
                        int popupFlag = equipmentSurfaceRules.getPopupFlag();
                        if (popupFlag == StaticExplain.POPUP_FLAG.getCode()) {
                            new EquipmentSurfaceDialog<BaseAdvertisingBillDetailFragment>()
                                    .setContext(getActivity(), BaseAdvertisingBillDetailFragment.this, String.valueOf(adOrderInformationList.getServiceId()))
                                    .initView(equipmentSurfaceRules)
                                    .show();
                        }
                    }
                });
    }

    private void setNearbyPhoto(AdOrderInformation adOrderInformationList) {
        String addressUrl = adOrderInformationList.getAddressUrl();
        final List<String> urlList = Util.getUrl(addressUrl);
        NearbyPhotoAdapter nearbyPhotoAdapter = new NearbyPhotoAdapter();
        recyclerNearbyPhoto.setAdapter(nearbyPhotoAdapter);
        nearbyPhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<String> url = new ArrayList<>(urlList);
                if (url != null) {
                    startFragment(ImgPreviewFragment.newInstance(position, url));
                }
            }
        });
        nearbyPhotoAdapter.setNewData(urlList);
    }

    private void initTab(List<DeviceSurfaceInformation> list) {
        //tab 标题
        String[] tabTitle = new String[list.size()];
        //tab 页
        List<BaseAdvertisingBillDetailTabFragment> tabFragmentList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DeviceSurfaceInformation deviceSurfaceInformation = list.get(i);
            String deviceSurfaceInformationString = new Gson().toJson(deviceSurfaceInformation);
            String deviceSurface = deviceSurfaceInformation.getDeviceSurface();
            if (!TextUtils.isEmpty(deviceSurface)) {
                tabTitle[i] = deviceSurface;
                tabFragmentList.add(getAdvertisingBillDetailTab(deviceSurfaceInformationString));
            }
        }
        mViewPager.setAdapter(new BaseViewPagerAdapter<>(getChildFragmentManager(), tabFragmentList, tabTitle));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    public BaseAdvertisingBillDetailTabFragment getAdvertisingBillDetailTab(String deviceSurfaceInformationString) {
        return BaseAdvertisingBillDetailTabFragment.newInstance(deviceSurfaceInformationString , adOrderInformationList.getOrderStatus());
    }

    @OnClick({R.id.tvBaseOrderDetailLocation})
    public void onViewBaseClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBaseOrderDetailLocation:
                //定位
                startMap();
                break;
            default:
        }
    }

    public void startMap() {
        String address = tvBaseOrderDetailLocation.getText().toString();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String role = userInfo.getRole();
        if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
            //雇主端
            MapUtils.mapNavigation(getActivity(), address);
        } else if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端
            MapUtils.mapMasterNavigation(getActivity(), address, adOrderInformationList.getEquipmentId());
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertising_bill_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public String getRightTitle() {
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String role = userInfo.getRole();
        if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            return getString(R.string.equipment_surface_rules);
        }
        return null;
    }

    @Override
    public void rightTitleClick(View v) {
        //设备面规则
        startFragment(EquipmentSurfaceRulesFragment.newInstance(String.valueOf(adOrderInformationList.getServiceId())));
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    public String getOrderCodes() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODES);
        }
        return "";
    }
}
