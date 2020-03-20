package com.xiaomai.zhuangba.fragment.orderdetail.employer.advertising;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.Cause;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.CompensateDialog;
import com.xiaomai.zhuangba.weight.dialog.EditTextDialogNoPassBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:59
 * 雇主 广告单 验收中
 */
public class EmployerAdvertisementAcceptanceDetailFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;
    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;
    @BindView(R.id.recyclerNearbyPhoto)
    RecyclerView recyclerNearbyPhoto;

    private List<DeviceSurfaceInformation> list;

    public static EmployerAdvertisementAcceptanceDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        EmployerAdvertisementAcceptanceDetailFragment fragment = new EmployerAdvertisementAcceptanceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        super.initView();
        relNewTaskOrderDetailBottom.setVisibility(View.GONE);
        recyclerNearbyPhoto.setVisibility(View.GONE);
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        list = adOrderInformationList.getList();
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
        //师傅名称
        String userText = adOrderInformationList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        String bareheadedPhotoUrl = adOrderInformationList.getBareheadedPhotoUrl();
        GlideManager.loadCircleImage(getActivity() , bareheadedPhotoUrl , ivEmployerDetailMasterHeader , R.drawable.bg_def_head);
        tvBaseAdvertisementMoney.setText(getString(R.string.content_money, String.valueOf(adOrderInformationList.getOrderAmount())));
    }

    @OnClick({R.id.btnAdvertisementNoPassage, R.id.btnAcceptanceApproval,R.id.layCompensate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdvertisementNoPassage:
                //验收不通过 查询验收不通过理由
                RxUtils.getObservable(ServiceUrl.getUserApi().getNotPassReasons())
                        .compose(this.<HttpResult<List<Cause>>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<List<Cause>>(getActivity()) {
                            @Override
                            protected void onSuccess(List<Cause> response) {
                                showNotPassReasonsDialog(response);
                            }
                        });
                break;
            case R.id.btnAcceptanceApproval:
                //验收通过
                RxUtils.getObservable(ServiceUrl.getUserApi().adPassedAdvertisingOrder(getOrderCodes()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                startFragmentAndDestroyCurrent(EmployerFragment.newInstance());
                            }
                        });
                break;
            case R.id.layCompensate:
                //发起索赔
                new CompensateDialog<EmployerAdvertisementAcceptanceDetailFragment>()
                        .initView(getActivity(), this)
                        .setCompensate(getActivity(), list)
                        .showDialog();
                break;
            default:
        }
    }

    private void showNotPassReasonsDialog(List<Cause> causeList) {
        EditTextDialogNoPassBuilder.getInstance().initView(getActivity())
                .setNoPassAdapter(causeList)
                .setICallBase(new EditTextDialogNoPassBuilder.BaseCallback() {
                    @Override
                    public void ok(Cause cause, String content) {
                        obtainNotPassedOrder(cause,content);
                    }
                }).showDialog();
    }

    private void obtainNotPassedOrder(Cause mCause,String content) {
        if (TextUtils.isEmpty(content) && mCause == null){
            ToastUtil.showShort(getString(R.string.please_select_or_fill_in_the_reason_for_failure));
        }
        String cause = "";
        if (mCause != null){
            mCause.getCause();
        }
        RxUtils.getObservable(ServiceUrl.getUserApi().notPassedAdvertisingOrder(getOrderCodes(),cause ,content))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        startFragment(EmployerFragment.newInstance());
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_acceptance_detail;
    }
}
