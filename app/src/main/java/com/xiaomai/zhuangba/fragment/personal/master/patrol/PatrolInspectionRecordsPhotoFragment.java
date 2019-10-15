package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PhotoPagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 */
public class PatrolInspectionRecordsPhotoFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<PatrolInspectionRecordsPhotoDetailFragment> photoDetailFragmentList;

    public static final String PATROL_MISSION_DETAIL_LIST_BEAN = "patrol_mission_detail_list_bean";

    public static PatrolInspectionRecordsPhotoFragment newInstance(String patrolMissionDetailListBean) {
        Bundle args = new Bundle();
        args.putString(PATROL_MISSION_DETAIL_LIST_BEAN, patrolMissionDetailListBean);
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
        photoDetailFragmentList = getListFragment(tabTitle);
        mViewPager.setAdapter(new PhotoPagerFragmentAdapter(getChildFragmentManager(), photoDetailFragmentList, tabTitle));

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private List<PatrolInspectionRecordsPhotoDetailFragment> getListFragment(String[] tabTitle) {
        PatrolInspectionRecordsDetailImgBean patrolInspectionRecordsDetailImgBean = getPatrolInspectionRecordsDetailImgBean();
        List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> taskPictureList = patrolInspectionRecordsDetailImgBean.getTaskPictureList();
        List<PatrolInspectionRecordsPhotoDetailFragment> list = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean
                    = new PatrolInspectionRecordsDetailImgBean.TaskPictureListBean();
            if (taskPictureList.size() > i){
                taskPictureListBean = taskPictureList.get(i);
            }
            list.add(PatrolInspectionRecordsPhotoDetailFragment.newInstance(tabTitle[i] , new Gson().toJson(taskPictureListBean)));
        }
        return list;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_photo;
    }

    @Override
    protected String getActivityTitle() {
        PatrolInspectionRecordsDetailImgBean patrolMissionDetailListBean = getPatrolInspectionRecordsDetailImgBean();
        return patrolMissionDetailListBean.getOrderDetailNo();
    }

    private String[] getTabTitle() {
        PatrolInspectionRecordsDetailImgBean patrolMissionDetailListBean = getPatrolInspectionRecordsDetailImgBean();
        String cover = patrolMissionDetailListBean.getCover();
        if (cover.contains(",")) {
            String[] split = cover.split(",");
            String[] title = new String[split.length];
            for (int i = 0; i < split.length; i++) {
                title[i] = (split[i] + "面");
            }
            return title;
        }
        return new String[]{"A面", "B面", "C面", "D面"};
    }

    public PatrolInspectionRecordsDetailImgBean getPatrolInspectionRecordsDetailImgBean() {
        if (getArguments() != null) {
            String string = getArguments().getString(PATROL_MISSION_DETAIL_LIST_BEAN);
            return new Gson().fromJson(string, PatrolInspectionRecordsDetailImgBean.class);
        }
        return new PatrolInspectionRecordsDetailImgBean();
    }


    @Override
    public void rightTitleClick(View v) {
        boolean flag = false;
        //完成
        PatrolInspectionRecordsDetailImgBean patrolInspectionRecordsDetailImgBean = getPatrolInspectionRecordsDetailImgBean();
        List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> taskPictureListBeanList = new ArrayList<>();
        for (PatrolInspectionRecordsPhotoDetailFragment patrolInspectionRecordsPhotoDetailFragment : photoDetailFragmentList) {
            PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean =
                    patrolInspectionRecordsPhotoDetailFragment.getTaskPictureListBean();
            taskPictureListBeanList.add(taskPictureListBean);
            flag = patrolInspectionRecordsPhotoDetailFragment.isFlag();
        }
        if (!flag){
            ToastUtil.showShort(getString(R.string.please_save_the_patrol_pictures));
            return;
        }
        patrolInspectionRecordsDetailImgBean.setTaskPictureList(taskPictureListBeanList);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(taskPictureListBeanList));
        RxUtils.getObservable(ServiceUrl.getUserApi().save(requestBody))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>() {
                    @Override
                    protected void onSuccess(Object response) {
                        Intent intent = new Intent();
                        setFragmentResult(ForResultCode.RESULT_OK.getCode(), intent);
                        popBackStack();
                    }
                });
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.complete);
    }
}
