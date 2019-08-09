package com.xiaomai.zhuangba.fragment.personal.employer.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.OrderServiceItemDao;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.BaseContinuedMaintenanceAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.weight.dialog.ShopCarDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 续维保 或者 新增维保 base
 */
public class BaseContinuedMaintenanceFragment extends BaseFragment implements BaseContinuedMaintenanceAdapter.IBaseContinuedAdapter {

    public static final String ORDER_SERVICE_ITEM = "order_service_item";
    @BindView(R.id.rvShopCar)
    RecyclerView rvShopCar;
    @BindView(R.id.tvSelectServiceMonty)
    TextView tvSelectServiceMonty;

    /**
     * 维保总价格
     */
    private Double totalPrice = 0d;

    private BaseContinuedMaintenanceAdapter baseContinuedMaintenanceAdapter;
    public static BaseContinuedMaintenanceFragment newInstance() {
        Bundle args = new Bundle();
        BaseContinuedMaintenanceFragment fragment = new BaseContinuedMaintenanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        List<OrderServiceItem> orderServiceItems = DBHelper.getInstance().getOrderServiceItemDao().queryBuilder().list();
        rvShopCar.setLayoutManager(new LinearLayoutManager(getActivity()));
        baseContinuedMaintenanceAdapter = new BaseContinuedMaintenanceAdapter();
        rvShopCar.setAdapter(baseContinuedMaintenanceAdapter);
        baseContinuedMaintenanceAdapter.setNewData(orderServiceItems);
        baseContinuedMaintenanceAdapter.setLayShopCarMaintenanceClick(this);

        //计算 总价格
        totalPrice(orderServiceItems);
    }

    private void totalPrice(List<OrderServiceItem> orderServiceItems) {
        for (OrderServiceItem orderServiceItem : orderServiceItems) {
            int monthNumber = orderServiceItem.getMonthNumber();
            double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
            totalPrice = totalPrice + (monthNumber * maintenanceAmount);
        }
        tvSelectServiceMonty.setText(getString(R.string.content_money, String.valueOf(totalPrice)));
    }


    @OnClick(R.id.btnConfirmationOfPayment)
    public void onViewClicked() {
        //确认支付
        confirmationOfPayment();
    }

    @Override
    public void layShopCarMaintenanceClick(OrderServiceItem item) {
        int serviceId = item.getServiceId();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMaintenance(String.valueOf(serviceId)))
                .compose(this.<HttpResult<List<Maintenance>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<Maintenance>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<Maintenance> maintenanceList) {
                        showShopCarDialog(maintenanceList);
                    }
                });
    }

    private void showShopCarDialog(List<Maintenance> maintenanceList) {
        ShopCarData shopCarDataList = null;
        if (maintenanceList != null && !maintenanceList.isEmpty()) {
            Maintenance maintenance = maintenanceList.get(0);
            shopCarDataList = findShopCarDataList(maintenance);
        }
        ShopCarDialog.getInstance()
                .initView(shopCarDataList, getActivity())
                .setRvChoosingGoods(getActivity(), maintenanceList)
                .setICallBase(new ShopCarDialog.BaseCallback() {
                    @Override
                    public void sure(Maintenance maintenance) {
                        if (maintenance.getId() != ConstantUtil.DEF_MAINTENANCE) {
                            Integer serviceId = maintenance.getServiceId();
                            OrderServiceItem orderServiceItem = DBHelper.getInstance().getOrderServiceItemDao().queryBuilder()
                                    .where(OrderServiceItemDao.Properties.ServiceId.eq(serviceId))
                                    .unique();
                            if (orderServiceItem != null) {
                                DBHelper.getInstance().getOrderServiceItemDao().delete(orderServiceItem);
                            } else {
                                orderServiceItem = new OrderServiceItem();
                            }
                            orderServiceItem.setMaintenanceId(maintenance.getId());
                            orderServiceItem.setServiceId(maintenance.getServiceId());
                            orderServiceItem.setMonthNumber(maintenance.getNumber());
                            orderServiceItem.setMaintenanceAmount(maintenance.getAmount());
                            DBHelper.getInstance().getOrderServiceItemDao().insert(orderServiceItem);
                        }
                        //计算价格
                        calculateThePrice();
                        baseContinuedMaintenanceAdapter.setNewData(DBHelper.getInstance().getOrderServiceItemDao().queryBuilder().list());
                    }
                }).showDialog();
    }

    private void calculateThePrice() {
        List<OrderServiceItem> maintenanceList = DBHelper.getInstance()
                .getOrderServiceItemDao().queryBuilder().list();
        Double totalPrice = 0d;
        for (OrderServiceItem orderServiceItem : maintenanceList) {
            //服务数量
            int number = orderServiceItem.getNumber();
            //维保金额
            double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
            totalPrice += number * maintenanceAmount;
        }
        tvSelectServiceMonty.setText(getString(R.string.content_money, String.valueOf(totalPrice)));
    }


    private ShopCarData findShopCarDataList(Maintenance maintenance) {
        OrderServiceItem unique = DBHelper.getInstance().getOrderServiceItemDao().queryBuilder()
                .where(OrderServiceItemDao.Properties.ServiceId.eq(maintenance.getServiceId()))
                .unique();
        if (unique != null) {
            return getShopCarData(unique);
        }
        return null;
    }

    private ShopCarData getShopCarData(OrderServiceItem unique) {
        ShopCarData shopCarData = new ShopCarData();
        shopCarData.setServiceId(String.valueOf(unique.getServiceId()));
        shopCarData.setMaintenanceId(unique.getMaintenanceId());
        shopCarData.setMaintenanceTime(String.valueOf(unique.getMonthNumber()));
        shopCarData.setMaintenanceMoney(String.valueOf(unique.getMaintenanceAmount()));
        return shopCarData;
    }

    public void confirmationOfPayment() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_continue_maintenance;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
