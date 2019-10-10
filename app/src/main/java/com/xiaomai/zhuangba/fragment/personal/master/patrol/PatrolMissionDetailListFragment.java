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
 */
public class PatrolMissionDetailListFragment extends BasePatrolMissionDetailListFragment{

    /**
     * 子订单编号
     */
    public static final String DETAIL_NO = "detail_no";
    public static final String TITLE = "title";

    public static PatrolMissionDetailListFragment newInstance(String detailNo , String title) {
        Bundle args = new Bundle();
        args.putString(DETAIL_NO, detailNo);
        args.putString(TITLE , title);
        PatrolMissionDetailListFragment fragment = new PatrolMissionDetailListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> getObservable() {
        return ServiceUrl.getUserApi().selectByDetailNo(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()), getDetailNo(),StringTypeExplain.CURRENT.getCode());
    }

    @Override
    public String getDetailNo() {
        if (getArguments() != null) {
            return getArguments().getString(DETAIL_NO);
        }
        return null;
    }

    @Override
    public String getTitle(){
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return null;
    }


}
