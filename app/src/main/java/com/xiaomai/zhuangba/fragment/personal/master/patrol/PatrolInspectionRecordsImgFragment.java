package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgPagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.util.Util;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 * <p>
 * 巡查详情 图片
 */
public class PatrolInspectionRecordsImgFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private static final String TITLE = "title";
    private static final String COVER = "cover";
    private static final String TASK_PICTURE_LISTBEAN_LIST = "taskPictureListBeanList";

    public static PatrolInspectionRecordsImgFragment newInstance(String title, String cover
            , List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> taskPictureListBeanList) {
        Bundle args = new Bundle();
        args.putString(COVER, cover);
        args.putString(TITLE, title);
        args.putParcelableArrayList(TASK_PICTURE_LISTBEAN_LIST, (ArrayList<? extends Parcelable>) taskPictureListBeanList);
        PatrolInspectionRecordsImgFragment fragment = new PatrolInspectionRecordsImgFragment();
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
        if (tabTitle != null) {
            List<PatrolInspectionRecordsImgDetailFragment> patrolInspectionRecordsImgDetailFragmentList = getListFragment(tabTitle);
            mViewPager.setAdapter(new ImgPagerFragmentAdapter(getChildFragmentManager(), patrolInspectionRecordsImgDetailFragmentList, tabTitle));

            CommonNavigator commonNavigator = new CommonNavigator(getActivity());
            commonNavigator.setAdjustMode(true);
            commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
            magicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(magicIndicator, mViewPager);
        }
    }

    private List<PatrolInspectionRecordsImgDetailFragment> getListFragment(String[] tabTitle) {
        List<PatrolInspectionRecordsImgDetailFragment> patrolInspectionRecordsImgDetailFragmentList = new ArrayList<>();
        if (tabTitle != null){
            List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> urlList = getUrlList();
            for (int i = 0; i < tabTitle.length; i++) {
                String url = "";
                if (urlList.size() > i){
                    PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean = urlList.get(i);
                    url = taskPictureListBean.getPic();
                }
                patrolInspectionRecordsImgDetailFragmentList.add(
                        PatrolInspectionRecordsImgDetailFragment.newInstance(url, tabTitle[i]));
            }
        }
        return patrolInspectionRecordsImgDetailFragmentList;
    }

    private String[] getTabTitle() {
        return Util.getNoodle(getCover());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_img;
    }

    @Override
    protected String getActivityTitle() {
        return getTitle();
    }

    public String getCover() {
        if (getArguments() != null) {
            return getArguments().getString(COVER);
        }
        return "";
    }

    public List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> getUrlList() {
        if (getArguments() != null) {
            return getArguments().getParcelableArrayList(TASK_PICTURE_LISTBEAN_LIST);
        }
        return new ArrayList<>();
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

}
