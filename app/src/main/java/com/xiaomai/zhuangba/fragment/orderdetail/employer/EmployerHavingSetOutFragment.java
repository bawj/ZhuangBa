package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.base.BaseEmployerDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 雇主端 已出发
 */
public class EmployerHavingSetOutFragment extends BaseEmployerDetailFragment {

    /**
     * 接单师傅的头像
     */
    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    /**
     * 接单师傅的名称
     */
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;

    public static EmployerHavingSetOutFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        EmployerHavingSetOutFragment fragment = new EmployerHavingSetOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_having_set_out;
    }

    @Override
    public void employerOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.employerOngoingOrdersListSuccess(ongoingOrdersList);
        String bareheadedPhotoUrl = ongoingOrdersList.getBareheadedPhotoUrl();
        //接受订单师傅
        String userText = ongoingOrdersList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        Log.e("师傅头像 " + bareheadedPhotoUrl);
        //接受订单师傅的头像
        GlideManager.loadCircleImage(getActivity(), bareheadedPhotoUrl,
                ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
    }

    @OnClick({R.id.btnCancelTask, R.id.btnNewTaskReceipt})
    public void onViewEmployerHavingSetOutClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelTask:
                CommonlyDialog.getInstance().initView(getActivity())
                        .setTvDialogCommonlyContent(getString(R.string.cancel_order_employer_msg_))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                iModule.obtainCancelTask();
                            }
                        }).showDialog();
                break;
            case R.id.btnNewTaskReceipt:
                //新增订单
                Observable<HttpResult<List<ServiceData>>> serviceCategory = ServiceUrl.getUserApi().getServiceCategory();
                RxUtils.getObservable(serviceCategory)
                        .compose(this.<HttpResult<List<ServiceData>>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<List<ServiceData>>(getActivity()) {
                            @Override
                            protected void onSuccess(List<ServiceData> serviceDataList) {
                                if (serviceDataList != null && !serviceDataList.isEmpty()) {
                                    ServiceData serviceData = serviceDataList.get(0);
                                    startFragment(SelectServiceFragment.newInstance(String.valueOf(serviceData.getServiceId()),
                                            serviceData.getServiceText()));
                                }
                            }
                        });
                break;
            default:
        }
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }

}
