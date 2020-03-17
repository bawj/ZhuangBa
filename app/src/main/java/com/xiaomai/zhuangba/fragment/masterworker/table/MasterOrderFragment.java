package com.xiaomai.zhuangba.fragment.masterworker.table;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MasterOrderAdapter;
import com.xiaomai.zhuangba.adapter.TabCommonNavigator;
import com.xiaomai.zhuangba.data.bean.SearchCondition;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.AdvertisingBillsFragment;
import com.xiaomai.zhuangba.fragment.masterworker.GuaranteeFragment;
import com.xiaomai.zhuangba.fragment.masterworker.InspectionSheetFragment;
import com.xiaomai.zhuangba.fragment.masterworker.NeedDealWithFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.ScreenDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/11/6 0006
 */
public class MasterOrderFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.layScreen)
    LinearLayout relScreen;

    /**
     * 筛选条件
     */
    private List<String> teamList = new ArrayList<>();
    /**
     * 筛选条件
     */
    private List<String> equipmentList = new ArrayList<>();
    /**
     * 筛选条件
     */
    private List<String> batchCodeList = new ArrayList<>();
    private List<BaseListFragment> fragmentList;
    public static MasterOrderFragment newInstance() {
        Bundle args = new Bundle();
        MasterOrderFragment fragment = new MasterOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        //安装单
        fragmentList.add(NeedDealWithFragment.newInstance());
        //维保单
        fragmentList.add(GuaranteeFragment.newInstance());
        //广告单
        fragmentList.add(AdvertisingBillsFragment.newInstance());
        //巡查单
        fragmentList.add(InspectionSheetFragment.newInstance());
        //titlegetAdvertisingList
        String[] strTitle = getTabTitle();
        mViewPager.setAdapter(new MasterOrderAdapter(getChildFragmentManager(), fragmentList, strTitle));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new TabCommonNavigator(strTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.addOnPageChangeListener(this);

        relScreen.setVisibility(View.GONE);
    }

    public String[] getTabTitle() {
        return new String[]{getString(R.string.need_to_be_dealt_with), getString(R.string.maintenance_policy_title)
                , getString(R.string.advertising_bills), getString(R.string.inspection_sheet)};
    }

    @Override
    public void onPageSelected(int i) {
        if (/*i == 1 ||*/ i == 2) {
            relScreen.setVisibility(View.VISIBLE);
        } else {
            relScreen.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.layScreen)
    public void onViewClicked() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "1");

        //hashMap.put("teamList", getTeamList()); 放开 和 完成 之前 展示的信息一样
        //hashMap.put("equipmentList", getEquipmentList());
        //hashMap.put("batchCodeList", getBatchCodeList());

        hashMap.put("teams", "");
        hashMap.put("equipments", "");
        hashMap.put("batchCodes", "");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getAllSearchCondition(requestBody))
                .compose(this.<HttpResult<SearchCondition>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<SearchCondition>(getActivity()) {
                    @Override
                    protected void onSuccess(SearchCondition searchCondition) {
                        //筛选
                        ScreenDialog.getInstance()
                                .setContext(getActivity())
                                .setSearchConditionContent(searchCondition)
                                .setScreenDialogCallBack(new ScreenDialog.ScreenDialogCallBack() {
                                    @Override
                                    public void callBack(List<String> teamListSearch, List<String> equipmentListSearch, List<String> batchCodeListSearch) {
                                        teamList = teamListSearch;
                                        equipmentList = equipmentListSearch;
                                        batchCodeList = batchCodeListSearch;
                                        AdvertisingBillsFragment advertisingBillsFragment = (AdvertisingBillsFragment) fragmentList.get(2);
                                        advertisingBillsFragment.setTeamList(teamListSearch);
                                        advertisingBillsFragment.setEquipmentList(equipmentListSearch);
                                        advertisingBillsFragment.setBatchCodeList(batchCodeListSearch);
                                        advertisingBillsFragment.refrshAdvertisingBills();
                                    }
                                })
                                .showRightDialog(getActivity());
                    }
                });
    }

    public List<String> getTeamList() {
        if (teamList == null) {
            return new ArrayList<>();
        }
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
        this.teamList = teamList;
    }

    public List<String> getEquipmentList() {
        if (equipmentList == null) {
            return new ArrayList<>();
        }
        return equipmentList;
    }

    public void setEquipmentList(List<String> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<String> getBatchCodeList() {
        if (batchCodeList == null) {
            return new ArrayList<>();
        }
        return batchCodeList;
    }

    public void setBatchCodeList(List<String> batchCodeList) {
        this.batchCodeList = batchCodeList;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_order;
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
