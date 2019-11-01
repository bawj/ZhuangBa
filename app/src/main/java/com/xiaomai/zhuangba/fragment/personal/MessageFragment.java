package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.personal.master.team.TeamMessageFragment;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class MessageFragment extends BaseFragment {

    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvMessageTime)
    TextView tvMessageTime;
    @BindView(R.id.relMessageTeam)
    RelativeLayout relMessageTeam;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        List<PushNotificationDB> list = DBHelper.getInstance().getPushNotificationDBDao().queryBuilder().list();
        if (!list.isEmpty()){
            Collections.reverse(list);
            PushNotificationDB pushNotificationDB = list.get(0);
            tvMessage.setText(pushNotificationDB.getText());
            String time = pushNotificationDB.getTime();
            Long aLong = DensityUtils.stringTypeLong(time);
            if (!TextUtils.isEmpty(time) && aLong != 0){
                tvMessageTime.setText(DateUtil.getDate2String(aLong , "yyyy-MM-dd HH:mm:ss"));
            }
        }

        //雇主不显示团队
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))){
            relMessageTeam.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.relMessageSystem, R.id.relMessageCustomerService , R.id.relMessageTeam})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relMessageSystem:
                //通知消息
                startFragment(NotificationMessageFragment.newInstance());
                break;
            case R.id.relMessageTeam:
                //团队
                startFragment(TeamMessageFragment.newInstance());
                break;
            case R.id.relMessageCustomerService:
                // TODO: 2019/6/3 0003 客服
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.message);
    }
}
