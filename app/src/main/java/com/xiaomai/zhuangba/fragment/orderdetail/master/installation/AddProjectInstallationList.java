package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.fragment.personal.PricingSheetFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Bawj
 * CreateDate: 2020/1/4 0004 13:30
 */
public class AddProjectInstallationList extends SelectServiceFragment {

    public static final String PUBLISHER = "publisher";

    @BindView(R.id.relAddAuxiliaryMaterial)
    RelativeLayout relAddAuxiliaryMaterial;

    public static AddProjectInstallationList newInstance(String serviceId, String serviceText,
                                                         String orderAddressGson,String orderCode , String publisher) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, serviceId);
        args.putString(SERVICE_TEXT, serviceText);
        args.putString(ORDER_ADDRESS_GSON, orderAddressGson);
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(PUBLISHER , publisher);
        AddProjectInstallationList fragment = new AddProjectInstallationList();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.relAddAuxiliaryMaterial})
    public void onViewAddAuxiliaryMaterialClicked(View view) {
        switch (view.getId()) {
            case R.id.relAddAuxiliaryMaterial:
                //新增辅材
                iModule.requestSlottingAndDebug();
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_add_project_installation_list;
    }

    @Override
    public void selectServiceNext() {
        //提交
        ShopAuxiliaryMaterialsDB shopAuxiliaryMaterials = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        boolean isShopAuxiliaryMaterials = shopAuxiliaryMaterials != null &&  shopAuxiliaryMaterials.getMaterialsSlottingId() != 0
                && shopAuxiliaryMaterials.getSlottingSlottingId() != 0 && shopAuxiliaryMaterials.getDebuggingPrice() != 0f;

        Integer totalNumber = ShopCarUtil.getTotalNumber();
        if (isShopAuxiliaryMaterials || totalNumber != 0){
            submitAudit();
        }else {
            ToastUtil.showShort(getString(R.string.please_service));
        }
    }

    @Override
    public void startShopCarFragment() {
        updateUi();
    }

    private void submitAudit() {
        HashMap<String , Object> hashMap = new HashMap<>();
        //订单编号
        hashMap.put("orderCode" , getOrderCode());
        //服务项目
        List<OrderServicesBean> orderServicesBeans = ShopCarUtil.getOrderServicesBean();
        hashMap.put("orderServiceList" , orderServicesBeans);
        //辅材
        ShopCarUtil.setAuxiliaryMaterials(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().initiateAddItem(requestBody))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        ToastUtil.showShort(getString(R.string.commit_success));
                        setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
                        popBackStack();
                    }
                });
    }

    private String getOrderCode(){
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    public String getPhoneNumber() {
        return getPublisher();
    }

    private String getPublisher(){
        if (getArguments() != null){
            return getArguments().getString(PUBLISHER);
        }
        return "";
    }

}
