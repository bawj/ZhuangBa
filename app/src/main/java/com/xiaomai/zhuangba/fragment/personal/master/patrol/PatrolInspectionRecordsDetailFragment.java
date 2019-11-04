package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 * <p>
 * 巡查记录详情
 */
public class PatrolInspectionRecordsDetailFragment extends BasePatrolMissionDetailListFragment {

    public static PatrolInspectionRecordsDetailFragment newInstance(String addr, String title) {
        Bundle args = new Bundle();
        args.putString(PatrolMissionDetailListFragment.ADD_R, addr);
        args.putString(PatrolMissionDetailListFragment.TITLE, title);
        PatrolInspectionRecordsDetailFragment fragment = new PatrolInspectionRecordsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> getObservable() {
        return ServiceUrl.getUserApi().selectByDetailNo(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()), getAddR(), StringTypeExplain.PATROL_RECORD.getCode());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PatrolMissionDetailListBean patrolMissionDetailListBean = (PatrolMissionDetailListBean)
                view.findViewById(R.id.tvPatrolMissionEquipmentNumber).getTag();
        int id = patrolMissionDetailListBean.getId();
        String equipmentNo = patrolMissionDetailListBean.getEquipmentNo();
        requestRecordsDetailImg(id , equipmentNo);
    }

    private void requestRecordsDetailImg(int id ,final String equipmentNo) {
        RxUtils.getObservable(ServiceUrl.getUserApi().selectByTaskId(String.valueOf(id)))
                .compose(this.<HttpResult<PatrolInspectionRecordsDetailImgBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<PatrolInspectionRecordsDetailImgBean>(getActivity()) {
                    @Override
                    protected void onSuccess(PatrolInspectionRecordsDetailImgBean patrolInspectionRecordsDetailImgBean) {
                        List<PatrolInspectionRecordsDetailImgBean.TaskPictureListBean> taskPictureList
                                = patrolInspectionRecordsDetailImgBean.getTaskPictureList();
                        startFragment(PatrolInspectionRecordsImgFragment.newInstance(equipmentNo
                                , patrolInspectionRecordsDetailImgBean.getCover(), taskPictureList));
                    }
                });
    }

    @Override
    public String getAddR() {
        if (getArguments() != null) {
            return getArguments().getString(PatrolMissionDetailListFragment.ADD_R);
        }
        return null;
    }

    @Override
    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(PatrolMissionDetailListFragment.TITLE);
        }
        return null;
    }
}
