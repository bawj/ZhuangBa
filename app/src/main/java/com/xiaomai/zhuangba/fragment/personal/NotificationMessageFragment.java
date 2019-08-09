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
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NotificationMessageAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

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

    private NotificationMessageAdapter notificationMessageAdapter;
    private List<PushNotificationDB> pushNotificationDBList;

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
        final UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique != null) {
            pushNotificationDBList = DBHelper.getInstance().getPushNotificationDBDao()
                    .queryBuilder()
                    .where(PushNotificationDBDao.Properties.Phone.eq(unique.getPhoneNumber()))
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
                if (unique != null) {
                    if (pushNotificationDB.getOrderType().equals(StringTypeExplain.ORDER_STATUS.getCode())) {
                        OngoingOrdersList ongoingOrdersList = new OngoingOrdersList();
                        ongoingOrdersList.setOrderCode(pushNotificationDB.getOrderCode());
                        String role = unique.getRole();

                        String orderCode = ongoingOrdersList.getOrderCode();
                        int orderStatus = ongoingOrdersList.getOrderStatus();
                        String expireTime = ongoingOrdersList.getExpireTime();
                        if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                            OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), orderCode, orderStatus, expireTime);
                        } else if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                            OrderStatusUtil.startEmployerOrderDetail(getBaseFragmentActivity(), orderCode, orderStatus);
                        }
                    }
                }
            }
        });
        if (getActivity() != null) {
            recyclerNotificationMessage.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
