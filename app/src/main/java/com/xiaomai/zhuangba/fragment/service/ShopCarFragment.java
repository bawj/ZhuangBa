package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ShopCarAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.OrderAddress;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.Slotting;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.shop.IShopCarModule;
import com.xiaomai.zhuangba.data.module.shop.IShopCarView;
import com.xiaomai.zhuangba.data.module.shop.ShopCarModule;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.dialog.ShopCarDialog;
import com.xiaomai.zhuangba.weight.dialog.SlottingAndDebugDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.ORDER_ADDRESS_GSON;
import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_ID;
import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_TEXT;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 * <p>
 * 购物车
 */
public class ShopCarFragment extends BaseFragment<IShopCarModule> implements ShopCarAdapter.IShopCarAdapterCallBack, View.OnClickListener, IShopCarView {

    @BindView(R.id.rvShopCar)
    RecyclerView rvShopCar;
    @BindView(R.id.tvSelectServiceMonty)
    TextView tvSelectServiceMonty;

    private ShopCarAdapter shopCarAdapter;
    private View headView;

    public static ShopCarFragment newInstance(String largeClassServiceId, String serviceText, String orderAddressGson) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, largeClassServiceId);
        args.putString(SERVICE_TEXT, serviceText);
        args.putString(ORDER_ADDRESS_GSON, orderAddressGson);
        ShopCarFragment fragment = new ShopCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        rvShopCar.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ShopCarData> shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();

        shopCarAdapter = new ShopCarAdapter();
        shopCarAdapter.setNewData(shopCarDataList);
        headView = getShopCarHeaderView();
        shopCarAdapter.addHeaderView(headView);
        rvShopCar.setAdapter(shopCarAdapter);
        shopCarAdapter.setIShopCarAdapterCallBack(this);

        setSelectServiceMoney();
    }

    private View getShopCarHeaderView() {
        ShopAuxiliaryMaterialsDB shopAuxiliaryMaterialsDB = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();

        View view = View.inflate(getActivity(), R.layout.item_shop_car_header, null);
        RelativeLayout layCablingSlotting = view.findViewById(R.id.layCablingSlotting);
        layCablingSlotting.setOnClickListener(this);
        TextView tvCablingSlotting = view.findViewById(R.id.tvCablingSlotting);
        tvCablingSlotting.setText(getString(R.string.slotting_start_end_length, shopAuxiliaryMaterialsDB.getSlottingStartLength()
                , shopAuxiliaryMaterialsDB.getSlottingEndLength()));

        RelativeLayout layDebugging = view.findViewById(R.id.layDebugging);
        TextView tvShopCarMaintenance = view.findViewById(R.id.tvShopCarMaintenance);
        layDebugging.setOnClickListener(this);
        tvShopCarMaintenance.setText(shopAuxiliaryMaterialsDB.getDebuggingPrice() == StaticExplain.DEBUGGING.getCode() ?
                getString(R.string.no_debugging_required) : getString(R.string.need_debugging));

        RelativeLayout layAuxiliaryMaterials = view.findViewById(R.id.layAuxiliaryMaterials);
        TextView tvShopAuxiliaryMaterials = view.findViewById(R.id.tvShopAuxiliaryMaterials);
        layAuxiliaryMaterials.setOnClickListener(this);
        tvShopAuxiliaryMaterials.setText(getString(R.string.slotting_start_end_length, shopAuxiliaryMaterialsDB.getMaterialsStartLength()
                , shopAuxiliaryMaterialsDB.getMaterialsEndLength()));

        TextView tvAuxiliaryMaterialsMoney = view.findViewById(R.id.tvAuxiliaryMaterialsMoney);
        double price = ShopCarUtil.getAuxiliaryMaterialsPrice();
        tvAuxiliaryMaterialsMoney.setText(getString(R.string.content_money, String.valueOf(price)));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layCablingSlotting:
                requestSlottingAndDebug();
                break;
            case R.id.layDebugging:
                requestSlottingAndDebug();
                break;
            case R.id.layAuxiliaryMaterials:
                requestSlottingAndDebug();
                break;
            default:
        }
    }

    private void requestSlottingAndDebug() {
        String orderAddressGson = getOrderAddressGson();
        OrderAddress orderAddress = new Gson().fromJson(orderAddressGson, OrderAddress.class);
        RxUtils.getObservable(ServiceUrl.getUserApi().getSlottingAndDebug(orderAddress.getProvince() , orderAddress.getCity()))
                .compose(this.<HttpResult<Slotting>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Slotting>(getActivity()) {
                    @Override
                    protected void onSuccess(Slotting slotting) {
                        slottingAndDebugSuccess(slotting);
                    }
                });
    }

    public void slottingAndDebugSuccess(Slotting slotting) {
        SlottingAndDebugDialog.getInstance().initView(getActivity(), slotting)
                .setICallBase(new SlottingAndDebugDialog.BaseCallback() {
                    @Override
                    public void ok() {
                        List<ShopCarData> shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();
                        shopCarAdapter = new ShopCarAdapter();
                        shopCarAdapter.setNewData(shopCarDataList);
                        headView = getShopCarHeaderView();
                        shopCarAdapter.addHeaderView(headView);
                        rvShopCar.setAdapter(shopCarAdapter);
                        shopCarAdapter.setIShopCarAdapterCallBack(ShopCarFragment.this);
                        setSelectServiceMoney();
                    }

                    @Override
                    public void calculationPrice() {
                    }
                })
                .showDialog();
    }

    private void setSelectServiceMoney() {
        tvSelectServiceMonty.setText(getString(R.string.content_money, String.valueOf(ShopCarUtil.getTotalMoney())));
    }

    @OnClick(R.id.btnSelectServiceNext)
    public void onViewClicked() {
        Integer totalNumber = ShopCarUtil.getTotalNumber();
        if (totalNumber > 0) {
            //提交
            iModule.submitOrder();
        } else {
            ToastUtil.showShort(getString(R.string.please_select_the_service_items));
        }
    }

    @Override
    public void updateShopCarFragment(ShopCarData shopCarData, final int count) {
        ShopCarUtil.saveDialogShopCar(shopCarData, count);
        shopCarAdapter.setNewData(DBHelper.getInstance().getShopCarDataDao().queryBuilder().list());
        //总计
        setSelectServiceMoney();
    }

    @Override
    public void showMaintenanceDialog(final ShopCarData shopCarData) {
        //显示dialog
        String serviceId = shopCarData.getServiceId();
        OrderAddress orderAddress = new Gson().fromJson(getOrderAddressGson(), OrderAddress.class);
        if (orderAddress != null) {
            RxUtils.getObservable(ServiceUrl.getUserApi().getMaintenance(serviceId, orderAddress.getProvince(), orderAddress.getCity()))
                    .compose(this.<HttpResult<List<Maintenance>>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<List<Maintenance>>(getActivity()) {
                        @Override
                        protected void onSuccess(List<Maintenance> maintenanceList) {
                            showShopCarDialog(shopCarData, maintenanceList);
                        }
                    });
        }

    }

    private void showShopCarDialog(final ShopCarData shopCarData, List<Maintenance> maintenanceList) {
        ShopCarDialog.getInstance()
                .initView(shopCarData, getActivity())
                .setRvChoosingGoods(getActivity(), maintenanceList)
                .setICallBase(new ShopCarDialog.BaseCallback() {
                    @Override
                    public void sure(Maintenance maintenance) {
                        ShopCarUtil.updateShopCarDialog(shopCarData, maintenance);
                        shopCarAdapter.setNewData(DBHelper.getInstance().getShopCarDataDao().queryBuilder().list());
                        setSelectServiceMoney();
                    }
                }).showDialog();
    }

    @Override
    public void placeOrderSuccess(String requestBodyString) {
        //提交成功
        startFragment(PaymentDetailsFragment.newInstance(requestBodyString));
    }

    @Override
    public String getServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

    @Override
    public String getServiceText() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_TEXT);
        }
        return "";
    }

    @Override
    public String getOrderAddressGson() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_ADDRESS_GSON);
        }
        return null;
    }

    @Override
    protected IShopCarModule initModule() {
        return new ShopCarModule();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_shop_car;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.selected_services);
    }
}
