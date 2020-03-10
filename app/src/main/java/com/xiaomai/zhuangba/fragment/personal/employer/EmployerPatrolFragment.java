package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.DialogUtil;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.EmployerPatrolAdapter;
import com.xiaomai.zhuangba.data.Patrol;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/9/29 0029
 */
public class EmployerPatrolFragment extends BaseListFragment<IBaseModule, EmployerPatrolAdapter> {

    private EmployerPatrolAdapter employerPatrolAdapter;
    public static EmployerPatrolFragment newInstance() {
        Bundle args = new Bundle();
        EmployerPatrolFragment fragment = new EmployerPatrolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestPatrol();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestPatrol();
    }


    private void requestPatrol() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getPatrolOrderList(String.valueOf(getPage()),
                String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<Patrol>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Patrol>() {
                    @Override
                    protected void onSuccess(Patrol patrol) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            employerPatrolAdapter.addData(patrol.getList());
                        } else {
                            //刷新
                            employerPatrolAdapter.setNewData(patrol.getList());
                            finishRefresh();
                        }
                        if (patrol.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommonlyDialog.getInstance()
                .initView(getActivity())
                .isVisibleClose(false)
                .setTvDialogBondTips("")
                .setTvDialogCommonlyContent(getString(R.string.patrol_deatil_tip))
                .setTvDialogCommonlyCloseTextColor(R.color.tool_lib_gray_222222)
                .showDialog();
    }

    @Override
    public EmployerPatrolAdapter getBaseListAdapter() {
        employerPatrolAdapter = new EmployerPatrolAdapter();
        return employerPatrolAdapter;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.inspection_records);
    }
}
