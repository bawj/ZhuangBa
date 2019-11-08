package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

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

    public static final String TITLE = "title";
    public static final String PATROL_MISSION_DETAIL_LIST_BEAN = "patrol_mission_detail_list_bean";

    public static PatrolInspectionRecordsPhotoFragment newInstance(String title, String patrolMissionDetailListBean) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
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
            PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean = new PatrolInspectionRecordsDetailImgBean.TaskPictureListBean();
            for (PatrolInspectionRecordsDetailImgBean.TaskPictureListBean pictureListBean : taskPictureList) {
                if (pictureListBean.getSide().equals(tabTitle[i])){
                    taskPictureListBean = pictureListBean;
                    break;
                }
            }
            taskPictureListBean.setSide(tabTitle[i]);
            list.add(PatrolInspectionRecordsPhotoDetailFragment.newInstance(tabTitle[i], new Gson().toJson(taskPictureListBean)));
        }
        return list;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_photo;
    }

    @Override
    protected String getActivityTitle() {
        if (!TextUtils.isEmpty(getTitle())) {
            return getTitle();
        } else {
            PatrolInspectionRecordsDetailImgBean patrolMissionDetailListBean = getPatrolInspectionRecordsDetailImgBean();
            return patrolMissionDetailListBean.getOrderDetailNo();
        }
    }

    private String[] getTabTitle() {
        PatrolInspectionRecordsDetailImgBean patrolMissionDetailListBean = getPatrolInspectionRecordsDetailImgBean();
        String cover = patrolMissionDetailListBean.getCover();
        if (cover.contains(",")) {
            return cover.split(",");
        } else if (!TextUtils.isEmpty(cover)) {
            return new String[]{cover};
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
        //判断是否有图片 没有上传
        boolean isTip = false;
        //完成
        PatrolInspectionRecordsDetailImgBean patrolInspectionRecordsDetailImgBean = getPatrolInspectionRecordsDetailImgBean();
        //默认设置已巡查
        patrolInspectionRecordsDetailImgBean.setStatus(StringTypeExplain.PROCESSED.getCode());
        //默认图 可能没有
        List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> taskPictureVOList = patrolInspectionRecordsDetailImgBean.getTaskPictureList();
        for (int i = 0; i < photoDetailFragmentList.size(); i++) {
            //新图片
            PatrolInspectionRecordsPhotoDetailFragment patrolInspectionRecordsPhotoDetailFragment = photoDetailFragmentList.get(i);
            PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean = patrolInspectionRecordsPhotoDetailFragment.getTaskPictureListBean();
            //新图片
            String newPic = taskPictureListBean.getPic();
            String newSide = taskPictureListBean.getSide();
            //如果有 则替换  没有 则添加
            PatrolInspectionRecordsDetailImgBean.TaskPictureListBean primaryTaskPictureListBean = null;
            for (PatrolInspectionRecordsDetailImgBean.TaskPictureListBean pictureListBean : taskPictureVOList) {
                String side = pictureListBean.getSide();
                if (side.equals(newSide)){
                    primaryTaskPictureListBean = pictureListBean;
                    break;
                }
            }
            if (primaryTaskPictureListBean != null && !TextUtils.isEmpty(newPic)){
                primaryTaskPictureListBean.setPic(newPic);
            }else if (!TextUtils.isEmpty(newPic)){
                taskPictureVOList.add(taskPictureListBean);
            }
            boolean shot = photoDetailFragmentList.get(i).isShot();
            boolean upload = photoDetailFragmentList.get(i).isUpload();
            //拍照了 但是没有上传
            if (shot && !upload){
                isTip = true;
            }
        }

        if (taskPictureVOList.size() <= 0){
            ToastUtil.showShort(getString(R.string.please_shot_img));
            return;
        }
        if (isTip){
            ToastUtil.showShort(getString(R.string.please_save_shot_img));
            return;
        }

        if (taskPictureVOList.size() != photoDetailFragmentList.size()){
            //有一个面 没有上传图片  则 未巡查
            patrolInspectionRecordsDetailImgBean.setStatus(StringTypeExplain.PROCESSING.getCode());
        }
        PatrolInspectionRecordsDetailImgBean detailImgBean = new PatrolInspectionRecordsDetailImgBean();
        detailImgBean.setId(patrolInspectionRecordsDetailImgBean.getId());
        detailImgBean.setCover(patrolInspectionRecordsDetailImgBean.getCover());
        detailImgBean.setOrderDetailNo(patrolInspectionRecordsDetailImgBean.getOrderDetailNo());
        detailImgBean.setStatus(patrolInspectionRecordsDetailImgBean.getStatus());
        detailImgBean.setTaskPictureVOList(taskPictureVOList);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(detailImgBean));
        RxUtils.getObservable(ServiceUrl.getUserApi().save(requestBody))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        Intent intent = new Intent();
                        setFragmentResult(ForResultCode.RESULT_OK.getCode(), intent);
                        popBackStack();
                    }
                });
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.complete);
    }
}
