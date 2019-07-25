package com.xiaomai.zhuangba.appilcation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.umeng.message.PushAgent;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        PushAgent.getInstance(activity).onAppStart();
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
