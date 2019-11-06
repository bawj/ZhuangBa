package com.xiaomai.zhuangba.fragment.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NotificationMessageAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class NotificationMessageFragment extends BaseFragment {

    @BindView(R.id.recyclerNotificationMessage)
    RecyclerView recyclerNotificationMessage;
    @BindView(R.id.linSearchEmpty)
    LinearLayout linSearchEmpty;

    private List<PushNotificationDB> pushNotificationDBList;
    private NotificationMessageAdapter notificationMessageAdapter;
    private UserInfo userInfo;

    public static NotificationMessageFragment newInstance() {
        Bundle args = new Bundle();
        NotificationMessageFragment fragment = new NotificationMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        recyclerNotificationMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationMessageAdapter = new NotificationMessageAdapter();
        recyclerNotificationMessage.setAdapter(notificationMessageAdapter);
        userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();

        if (userInfo != null) {
            pushNotificationDBList = DBHelper.getInstance().getPushNotificationDBDao()
                    .queryBuilder()
                    .where(PushNotificationDBDao.Properties.Phone.eq(userInfo.getPhoneNumber()))
                    .list();
            if (!pushNotificationDBList.isEmpty()) {
                Collections.reverse(pushNotificationDBList);
                notificationMessageAdapter.setNewData(pushNotificationDBList);
                linSearchEmpty.setVisibility(View.GONE);
            } else {
                linSearchEmpty.setVisibility(View.VISIBLE);
            }
        }
        notificationMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PushNotificationDB pushNotificationDB = (PushNotificationDB)
                        view.findViewById(R.id.tvNotificationContent).getTag();
                if (userInfo != null) {
                    if (pushNotificationDB.getType().equals(StringTypeExplain.ORDER_STATUS.getCode())) {
                        startOrderDetail(pushNotificationDB);
                    }
                }
            }
        });
        if (getActivity() != null) {
            recyclerNotificationMessage.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        }
    }

    private void startOrderDetail(PushNotificationDB pushNotificationDB) {
        final String role = userInfo.getRole();
        String orderCode = pushNotificationDB.getOrderCode();
        final String orderType = pushNotificationDB.getOrderType();
        //订单详情
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderDetails(orderCode, orderType))
                .compose(this.<HttpResult<OngoingOrdersList>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<OngoingOrdersList>(getActivity()) {
                    @Override
                    protected void onSuccess(OngoingOrdersList ongoingOrdersList) {
                        if (ongoingOrdersList != null) {
                            //师傅端
                            if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                                startMasterOrderDetail(orderType, ongoingOrdersList);
                            } else if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                                startEmployerOrderDetail(orderType, ongoingOrdersList);
                            }
                        }
                    }
                });
    }

    /**
     * 到 师傅详情
     *
     * @param orderType         type
     * @param ongoingOrdersList 订单详情
     */
    private void startMasterOrderDetail(String orderType, OngoingOrdersList ongoingOrdersList) {
        // 安装单
        if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))) {
            OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(), orderType,
                    ongoingOrdersList.getOrderStatus(), ongoingOrdersList.getExpireTime());
            //广告单
        } else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
            AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(), orderType,
                    ongoingOrdersList.getOrderStatus());
            //巡查单
        }else if (orderType.equals(String.valueOf(StaticExplain.PATROL.getCode()))){
            PatrolStatusUtil.startMasterPatrol(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(), orderType,
                    ongoingOrdersList.getOrderStatus());
        }
    }

    /**
     * 到雇主详情
     *
     * @param orderType         type
     * @param ongoingOrdersList 订单详情
     */
    private void startEmployerOrderDetail(String orderType, OngoingOrdersList ongoingOrdersList) {
        // 安装单
        if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))) {
            OrderStatusUtil.startEmployerOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode()
                    , orderType, ongoingOrdersList.getOrderStatus());
        } else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
            //广告单
            AdvertisingStatusUtil.startEmployerAdvertisingBills(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode()
                    , orderType, ongoingOrdersList.getOrderStatus());
            //巡查单
        }else if (orderType.equals(String.valueOf(StaticExplain.PATROL.getCode()))){
            PatrolStatusUtil.startEmployerPatrol(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(), orderType,
                    ongoingOrdersList.getOrderStatus());
        }
    }

    @Override
    public void rightTitleClick(View v) {
        //清空
        if (notificationMessageAdapter != null) {
            UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
            if (unique != null && !pushNotificationDBList.isEmpty()) {
                PushNotificationDBDao pushNotificationDBDao = DBHelper.getInstance().getPushNotificationDBDao();
                pushNotificationDBDao.deleteInTx(pushNotificationDBList);
                pushNotificationDBList = pushNotificationDBDao.queryBuilder()
                        .where(PushNotificationDBDao.Properties.Phone.eq(unique.getPhoneNumber()))
                        .list();
                notificationMessageAdapter.setNewData(pushNotificationDBList);
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_notification_message;
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.empty);
    }

    @Override
    public int getRightTitleColor() {
        return Color.RED;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.notification_message);
    }
}
