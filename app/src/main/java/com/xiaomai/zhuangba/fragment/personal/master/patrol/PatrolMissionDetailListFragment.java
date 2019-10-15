package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 */
public class PatrolMissionDetailListFragment extends BasePatrolMissionDetailListFragment {

    /**
     * 子订单编号
     */
    public static final String DETAIL_NO = "detail_no";
    public static final String TITLE = "title";

    public static PatrolMissionDetailListFragment newInstance(String detailNo, String title) {
        Bundle args = new Bundle();
        args.putString(DETAIL_NO, detailNo);
        args.putString(TITLE, title);
        PatrolMissionDetailListFragment fragment = new PatrolMissionDetailListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> getObservable() {
        return ServiceUrl.getUserApi().selectByDetailNo(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()), getDetailNo(), StringTypeExplain.CURRENT.getCode());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final PatrolMissionDetailListBean patrolMissionDetailListBean = (PatrolMissionDetailListBean)
                view.findViewById(R.id.tvPatrolMissionEquipmentNumber).getTag();
        int id = patrolMissionDetailListBean.getId();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectByTaskId(String.valueOf(id)))
                .compose(this.<HttpResult<PatrolInspectionRecordsDetailImgBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<PatrolInspectionRecordsDetailImgBean>(getActivity()) {
                    @Override
                    protected void onSuccess(PatrolInspectionRecordsDetailImgBean patrolInspectionRecordsDetailImgBean) {
                        startFragmentForResult(PatrolInspectionRecordsPhotoFragment.newInstance(new Gson().toJson(patrolInspectionRecordsDetailImgBean))
                                , ForResultCode.START_FOR_RESULT_CODE.getCode());
                    }
                });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()){
            if (resultCode == ForResultCode.RESULT_OK.getCode()){
                refresh();
            }
        }
    }

    @Override
    public String getDetailNo() {
        if (getArguments() != null) {
            return getArguments().getString(DETAIL_NO);
        }
        return null;
    }

    @Override
    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return null;
    }


}
