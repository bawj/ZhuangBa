package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 *
 * 巡查记录详情
 */
public class PatrolInspectionRecordsDetailFragment extends BasePatrolMissionDetailListFragment {

    public static PatrolInspectionRecordsDetailFragment newInstance(String detailNo , String title) {
        Bundle args = new Bundle();
        args.putString(PatrolMissionDetailListFragment.DETAIL_NO, detailNo);
        args.putString(PatrolMissionDetailListFragment.TITLE , title);
        PatrolInspectionRecordsDetailFragment fragment = new PatrolInspectionRecordsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> getObservable() {
        return ServiceUrl.getUserApi().selectByDetailNo(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()), getDetailNo(),StringTypeExplain.PATROL_RECORD.getCode());
    }

    @Override
    public String getDetailNo() {
        if (getArguments() != null) {
            return getArguments().getString(PatrolMissionDetailListFragment.DETAIL_NO);
        }
        return null;
    }

    @Override
    public String getTitle(){
        if (getArguments() != null) {
            return getArguments().getString(PatrolMissionDetailListFragment.TITLE);
        }
        return null;
    }
}
