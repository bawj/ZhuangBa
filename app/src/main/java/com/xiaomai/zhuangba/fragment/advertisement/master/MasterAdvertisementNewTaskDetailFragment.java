package com.xiaomai.zhuangba.fragment.advertisement.master;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 * <p>
 * 师傅 广告单 新任务详情
 */
public class MasterAdvertisementNewTaskDetailFragment extends BaseAdvertisementFragment {

    @BindView(R.id.btnCancelTask)
    Button btnCancelTask;

    public static MasterAdvertisementNewTaskDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementNewTaskDetailFragment fragment = new MasterAdvertisementNewTaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_new_detail;
    }

    @OnClick({R.id.btnCancelTask, R.id.btnTaskAcceptance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelTask:
                //取消任务
                cancelTask();
                break;
            case R.id.btnTaskAcceptance:
                //接任务
                taskAcceptance();
                break;
            default:
        }
    }

    @Override
    public void setIsCancelVisibility(double orderAmount) {
        btnCancelTask.setVisibility(orderAmount == 0 ? View.GONE : View.VISIBLE);
    }

    private void taskAcceptance() {
        RxUtils.getObservable(ServiceUrl.getUserApi().acceptAdvertisingOrder(getOrderCode()))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        startFragment(MasterWorkerFragment.newInstance());
                    }
                });
    }

    private void cancelTask() {
        RxUtils.getObservable(ServiceUrl.getUserApi().masterCancelAdvertisingOrder(getOrderCode()))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        startFragment(MasterWorkerFragment.newInstance());
                    }
                });
    }
}
