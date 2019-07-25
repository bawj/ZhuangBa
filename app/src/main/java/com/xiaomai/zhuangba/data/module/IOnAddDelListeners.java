package com.xiaomai.zhuangba.data.module;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public interface IOnAddDelListeners {

    /**
     * 添加成功
     * @param count 数量
     */
    void onAddSuccess(int count);

    /**
     * 减少成功
     * @param count 数量
     */
    void onDelSuccess(int count);

}
