package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PhotoPagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 */
public class PatrolInspectionRecordsPhotoFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static final String PATROL_MISSION_DETAIL_LIST_BEAN = "patrol_mission_detail_list_bean";

    public static PatrolInspectionRecordsPhotoFragment newInstance(String patrolMissionDetailListBean) {
        Bundle args = new Bundle();
        args.putString(PATROL_MISSION_DETAIL_LIST_BEAN , patrolMissionDetailListBean);
        PatrolInspectionRecordsPhotoFragment fragment = new PatrolInspectionRecordsPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        String[] tabTitle = getTabTitle();
        List<PatrolInspectionRecordsPhotoDetailFragment> photoDetailFragmentList = getListFragment(tabTitle);
        mViewPager.setAdapter(new PhotoPagerFragmentAdapter(getChildFragmentManager(), photoDetailFragmentList, tabTitle));

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private List<PatrolInspectionRecordsPhotoDetailFragment> getListFragment( String[] tabTitle) {
        List<PatrolInspectionRecordsPhotoDetailFragment> list = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            list.add(PatrolInspectionRecordsPhotoDetailFragment.newInstance());
        }
        return list;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_photo;
    }

    @Override
    protected String getActivityTitle() {
        PatrolMissionDetailListBean patrolMissionDetailListBean = getPatrolMissionDetailListBean();
        return patrolMissionDetailListBean.getDetailNo();
    }

    private String[] getTabTitle() {
        PatrolMissionDetailListBean patrolMissionDetailListBean = getPatrolMissionDetailListBean();
        String cover = patrolMissionDetailListBean.getCover();
        if (cover.contains(",")){
            String[] split = cover.split(",");
            String[] title = new String[split.length];
            for (int i = 0; i < split.length; i++) {
                title[i] = (split[i] + "面");
            }
            return title;
        }
        return new String[]{"A面", "B面", "C面", "D面"};
    }


    public PatrolMissionDetailListBean getPatrolMissionDetailListBean(){
        if (getArguments() != null){
            String string = getArguments().getString(PATROL_MISSION_DETAIL_LIST_BEAN);
            return new Gson().fromJson(string , PatrolMissionDetailListBean.class);
        }
        return new PatrolMissionDetailListBean();
    }

}
