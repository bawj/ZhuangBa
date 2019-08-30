package com.xiaomai.zhuangba.fragment.masterworker.advertising;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingBillDetailPagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.observable.EventManager;
import com.xiaomai.zhuangba.data.observable.Observer;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.util.ConstantUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 */
public class AdvertisingBillDetailFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<WholeAdvertisingFragment> listFragment;
    private SparseArray isRefreshFragment;
    public EventManager eventManager;
    private AdvertisingBillsBean advertisingBillsBean;

    public static AdvertisingBillDetailFragment newInstance(AdvertisingBillsBean advertisingBillsBean) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantUtil.ADVERTISING_BILLS, advertisingBillsBean);
        AdvertisingBillDetailFragment fragment = new AdvertisingBillDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        isRefreshFragment = new SparseArray<>();
        String[] tabTitle = getTabTitle();
        listFragment = getListFragment();

        mViewPager.setAdapter(new AdvertisingBillDetailPagerFragmentAdapter(getChildFragmentManager(), listFragment, tabTitle));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.addOnPageChangeListener(this);

        //注册观察者
        eventManager = new EventManager(new ArrayList<Observer>());
        for (WholeAdvertisingFragment baseFragment : listFragment) {
            eventManager.registerObserver(baseFragment);
        }

        refresh(0);
    }

    private String[] getTabTitle() {
        return new String[]{getString(R.string.whole), getString(R.string.new_task)
                , getString(R.string.received_orders), getString(R.string.having_set_out), getString(R.string.be_under_construction)};
    }

    private List<WholeAdvertisingFragment> getListFragment() {
        List<WholeAdvertisingFragment> list = new ArrayList<>();
        list.add(WholeAdvertisingFragment.newInstance(getAdvertisingBillsBean(), ""));
        list.add(WholeAdvertisingFragment.newInstance(getAdvertisingBillsBean(), String.valueOf(AdvertisingEnum.MASTER_NEW_TASK.getCode())));
        list.add(WholeAdvertisingFragment.newInstance(getAdvertisingBillsBean(), String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL.getCode())));
        list.add(WholeAdvertisingFragment.newInstance(getAdvertisingBillsBean(), String.valueOf(AdvertisingEnum.MASTER_IN_PROCESSING.getCode())));
        list.add(WholeAdvertisingFragment.newInstance(getAdvertisingBillsBean(), String.valueOf(AdvertisingEnum.MASTER_BE_UNDER_CONSTRUCTION.getCode())));
        return list;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        refresh(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_advertising_bill_detail;
    }

    private void refresh(int i) {
        if (isRefreshFragment.get(i) == null) {
            eventManager.notifyObservers(String.valueOf(i), "", handler);
            isRefreshFragment.put(i, i);
        }
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("handleMessage = " + msg);
            return false;
        }
    });

    @Override
    protected String getActivityTitle() {
        advertisingBillsBean = getAdvertisingBillsBean();
        String street = advertisingBillsBean.getStreet();
        String villageName = advertisingBillsBean.getVillageName();
        return TextUtils.isEmpty(street) ? villageName : street;
    }

    private AdvertisingBillsBean getAdvertisingBillsBean() {
        if (getArguments() != null) {
            return (AdvertisingBillsBean) getArguments().getSerializable(ConstantUtil.ADVERTISING_BILLS);
        }
        return new AdvertisingBillsBean();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (WholeAdvertisingFragment wholeAdvertisingFragment : listFragment) {
            eventManager.removeObserver(wholeAdvertisingFragment);
        }
        isRefreshFragment.clear();
    }
}
