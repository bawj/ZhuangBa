package com.xiaomai.zhuangba.data.observable;

import android.os.Handler;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * 可观察接口，通知事件的发出者
 */
public interface Observable {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(String message , Handler handler);

}
