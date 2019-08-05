package com.xiaomai.zhuangba.data.observable;

import android.os.Handler;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * 观察者
 */
public interface Observer {

    /**
     * 通知更新
     * @param message msg
     * @param address 地址
     * @param handler handler
     */
    void update(String message ,String address, Handler handler);

}
