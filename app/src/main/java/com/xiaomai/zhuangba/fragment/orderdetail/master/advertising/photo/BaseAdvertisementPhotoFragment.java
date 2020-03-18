package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.BaseViewPagerAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment.ORDER_CODES;

public class BaseAdvertisementPhotoFragment<T extends BaseAdvertisementPhotoTabFragment> extends BaseFragment {

    public static final String DEVICE_SURFACE_INFORMATION_LIST_STRING = "device_surface_information_list_string";
    public static final String PREVIOUS_PUBLICATIO_LIST = "previous_publicatio_list";
    public static final String SERVICE_SAMPLE = "service_sample";
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    /**
     * @param serviceId                          serviceId
     * @param serviceSample                      默认图
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     * @return
     */
    public static BaseAdvertisementPhotoFragment newInstance(String orderCodes, String serviceId, String serviceSample,String previousPublicatioList, String deviceSurfaceInformationListString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(PREVIOUS_PUBLICATIO_LIST , previousPublicatioList);
        args.putString(PhotoStyleFragment.SERVICE_ID, serviceId);
        args.putString(SERVICE_SAMPLE, serviceSample);
        args.putString(ORDER_CODES, orderCodes);
        BaseAdvertisementPhotoFragment fragment = new BaseAdvertisementPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        List<DeviceSurfaceInformation> deviceSurfaceInformation1 = getPreviousPublicatio();
        //tab 标题
        String[] tabTitle = new String[deviceSurfaceInformation1.size()];
        //tab 页
        List<T> tabFragmentList = new ArrayList<>();
        for (int i = 0; i < deviceSurfaceInformation1.size(); i++) {
            DeviceSurfaceInformation deviceSurfaceInformation = deviceSurfaceInformation1.get(i);
            String deviceSurface = deviceSurfaceInformation.getDeviceSurface();
            tabTitle[i] = deviceSurface;
            tabFragmentList.add(getBaseAdvertisementPhotoTabFragment(new Gson().toJson(deviceSurfaceInformation)));
        }
        mViewPager.setAdapter(new BaseViewPagerAdapter<>(getChildFragmentManager(), tabFragmentList, tabTitle));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    public T getBaseAdvertisementPhotoTabFragment(String deviceSurfaceInformationString) {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertisement_photo;
    }

    public List<DeviceSurfaceInformation> getDeviceSurfaceInformation() {
        try {
            if (getArguments() != null) {
                String string = getArguments().getString(DEVICE_SURFACE_INFORMATION_LIST_STRING);
                return new Gson().fromJson(string, new TypeToken<List<DeviceSurfaceInformation>>() {
                }.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<DeviceSurfaceInformation> getPreviousPublicatio() {
        try {
            if (getArguments() != null) {
                String string = getArguments().getString(PREVIOUS_PUBLICATIO_LIST);
                return new Gson().fromJson(string, new TypeToken<List<DeviceSurfaceInformation>>() {
                }.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int getRightIcon() {
        return R.drawable.ic_question_mark_gray;
    }

    @Override
    public void rightIconClick(View view) {
        startFragment(PhotoStyleFragment.newInstance(getServiceId()));
    }

    private String getServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(PhotoStyleFragment.SERVICE_ID);
        }
        return "";
    }

    public String getServiceSample() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_SAMPLE);
        }
        return "";
    }

    public String getOrderCodes() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODES);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getTitle();
    }

    public String getTitle() {
        return "";
    }

}
