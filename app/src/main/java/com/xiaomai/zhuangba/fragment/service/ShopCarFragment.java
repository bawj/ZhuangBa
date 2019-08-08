package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.widget.QMUIAnimationListView;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ShopCarAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.dialog.ShopCarDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_ID;
import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_TEXT;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 * <p>
 * 购物车
 */
public class ShopCarFragment extends BaseFragment implements ShopCarAdapter.IShopCarAdapterCallBack {

    @BindView(R.id.rvShopCar)
    QMUIAnimationListView rvShopCar;
    @BindView(R.id.tvSelectServiceMonty)
    TextView tvSelectServiceMonty;

    private List<ShopCarData> shopCarDataList;
    private ShopCarAdapter shopCarAdapter;

    public static ShopCarFragment newInstance(String largeClassServiceId, String serviceText) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, largeClassServiceId);
        args.putString(SERVICE_TEXT, serviceText);
        ShopCarFragment fragment = new ShopCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();
        shopCarAdapter = new ShopCarAdapter(getActivity(), shopCarDataList);
        rvShopCar.setAdapter(shopCarAdapter);
        shopCarAdapter.setIShopCarAdapterCallBack(this);

        setSelectServiceMoney();
    }

    private void setSelectServiceMoney() {
        tvSelectServiceMonty.setText(getString(R.string.content_money, String.valueOf(ShopCarUtil.getTotalMoney())));
    }

    @OnClick(R.id.btnSelectServiceNext)
    public void onViewClicked() {
        Integer totalNumber = ShopCarUtil.getTotalNumber();
        if (totalNumber > 0){
            startFragment(SubmitOrderInformationFragment.newInstance(getServiceId(), getServiceText()));
        }else {
            // TODO: 2019/8/7 0007 可以 Toast
        }
    }

    @Override
    public void updateShopCarFragment(ShopCarData shopCarData, final int count, final int position) {
        ShopCarUtil.saveDialogShopCar(shopCarData, count);
        shopCarAdapter.setNewData();
        //总计
        setSelectServiceMoney();
    }

    @Override
    public void showMaintenanceDialog(final ShopCarData shopCarData) {
        //显示dialog
        String serviceId = shopCarData.getServiceId();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMaintenance(serviceId))
                .compose(this.<HttpResult<List<Maintenance>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<Maintenance>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<Maintenance> maintenanceList) {
                        showShopCarDialog(shopCarData, maintenanceList);
                    }
                });
    }

    private void showShopCarDialog(final ShopCarData shopCarData, List<Maintenance> maintenanceList) {
        ShopCarDialog.getInstance()
                .initView(shopCarData, getActivity())
                .setRvChoosingGoods(getActivity(), maintenanceList)
                .setICallBase(new ShopCarDialog.BaseCallback() {
                    @Override
                    public void sure(Maintenance maintenance) {
                        ShopCarUtil.updateShopCarDialog(shopCarData, maintenance);
                        shopCarAdapter.setNewData();
                        setSelectServiceMoney();
                    }
                }).showDialog();
    }

    private String getServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

    private String getServiceText() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_TEXT);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
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
