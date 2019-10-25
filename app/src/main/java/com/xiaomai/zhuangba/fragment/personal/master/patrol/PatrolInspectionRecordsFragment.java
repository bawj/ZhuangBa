package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 *
 * 巡查记录
 */
public class PatrolInspectionRecordsFragment extends BasePatrolMissionFragment {

    public static PatrolInspectionRecordsFragment newInstance() {
        Bundle args = new Bundle();
        PatrolInspectionRecordsFragment fragment = new PatrolInspectionRecordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBasePatrolItemClick(PatrolMissionBean patrolMissionBean) {
        PatrolBean.TasklistBean t = patrolMissionBean.t;
        String villagename = t.getVillagename();
        if (TextUtils.isEmpty(villagename)) {
            villagename = t.getStreet();
        }
        startFragment(PatrolInspectionRecordsDetailFragment.newInstance(t.getDetailNo() , villagename));
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.inspection_records);
    }

    @Override
    public Observable<HttpResult<RefreshBaseList<PatrolBean>>> getObservable() {
        return ServiceUrl.getUserApi().selectByMentor(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()) , StringTypeExplain.PATROL_RECORD.getCode());
    }

}
