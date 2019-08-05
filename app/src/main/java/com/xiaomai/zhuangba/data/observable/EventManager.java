package com.xiaomai.zhuangba.data.observable;

import android.os.Handler;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * 事件处理
 */
public class EventManager implements Observable{

    private List<Observer> observers;

    public EventManager(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i > 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(String message ,String address, Handler handler) {
        for (Observer o : observers) {
            o.update(message ,address, handler);
        }
    }
}
