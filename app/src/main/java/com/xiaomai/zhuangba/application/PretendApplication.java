package com.xiaomai.zhuangba.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.example.toollib.ToolLib;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.bugly.Bugly;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xiaomai.zhuangba.MainActivity;
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.PushNotification;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public class PretendApplication extends Application {

    public static final String BUG_LY_APP_ID = "a1fb987ca2";
    private final static String U_MENG_APP_KEY = "5ce354ba3fc195745e00067e";
    private final static String U_MENG_MESSAGE_SECRET = "8953a06a2bdd1eaf7a3b8ceeb616817a";
    private static PretendApplication pretendApplication;

//   public static final String BASE_URL = "http://192.168.0.110:7966/";
//   public static final String BASE_URL = "http://192.168.0.131:7966/";
//   public static final String BASE_URL = "http://192.168.0.186:7968/";
//   public static final String BASE_URL = "https://zb.hangzhouzhuangba.com/zhuangBas/";
     public static final String BASE_URL = "https://zb.hangzhouzhuangba.com/testZhuangBas/";

    public static PretendApplication getInstance() {
        return pretendApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pretendApplication = this;

        QMUISwipeBackActivityManager.init(this);

        ToolLib.getInstance().init(pretendApplication)
                .initDownLoad(pretendApplication)
                .setBaseUrl(BASE_URL);

        registerActivityLifecycleCallbacks(new LifecycleCallbacks());

        Bugly.init(this, BUG_LY_APP_ID, false);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //友盟初始化
        initUMeng(mPushAgent);
        //自定义通知栏打开
        uMengNotificationClick(mPushAgent);
        //读取消息
        uMengMessageHandler(mPushAgent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void uMengMessageHandler(PushAgent mPushAgent) {
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super则会走通知展示流程，不调用super则不展示通知
                super.dealWithNotificationMessage(context, msg);
                JSONObject raw = msg.getRaw();
                PushNotification.PayloadBean pushNotification = new Gson().fromJson(raw.toString(), PushNotification.PayloadBean.class);
                PushNotificationDB pushNotificationDB = new PushNotificationDB();
                if (pushNotification != null) {
                    pushNotificationDB.setDisplayType(pushNotification.getDisplayType());
                    PushNotification.PayloadBean.ExtraBean extra = pushNotification.getExtra();
                    if (extra != null) {
                        pushNotificationDB.setOrderCode(extra.getCode());
                        pushNotificationDB.setOrderType(extra.getOrderType());
                        pushNotificationDB.setType(extra.getType());
                        pushNotificationDB.setTime(extra.getTime());
                    }
                    PushNotification.PayloadBean.BodyBean body = pushNotification.getBody();
                    if (body != null) {
                        pushNotificationDB.setAfterOpen(body.getAfterOpen());
                        pushNotificationDB.setTicker(body.getTicker());
                        pushNotificationDB.setText(body.getText());
                        pushNotificationDB.setTitle(body.getTitle());
                    }
                }
                UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                if (unique != null) {
                    String phoneNumber = unique.getPhoneNumber();
                    pushNotificationDB.setPhone(phoneNumber);
                    PushNotificationDBDao pushNotificationDBDao = DBHelper.getInstance().getPushNotificationDBDao();
                    pushNotificationDBDao.insert(pushNotificationDB);
                    //通知 通知列表 刷新数据
                    EventBus.getDefault().post(new MessageEvent(EventBusEnum.NOTIFICATION_REFRESH.getCode()));
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    private void uMengNotificationClick(PushAgent instance) {
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                //判断app进程是否存活
                int appAlive = Util.isAppAlive(context, "com.xiaomai.zhuangba");
                if (appAlive == 2 || appAlive == 0) {
                    //如果存活的话， 启动MainActivity。
                    Intent mainIntent = new Intent(context, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mainIntent);
                } else if (appAlive != 1) {
                    Intent launchIntent = context.getPackageManager().
                            getLaunchIntentForPackage("com.xiaomai.zhuangba");
                    if (launchIntent != null) {
                        launchIntent.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        context.startActivity(launchIntent);
                    }
                }
            }
        };
        instance.setNotificationClickHandler(notificationClickHandler);
    }

    private void initUMeng(PushAgent instance) {
        UMConfigure.init(this, U_MENG_APP_KEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, U_MENG_MESSAGE_SECRET);
        instance.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.e("deviceToken = " + deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });
    }
}
