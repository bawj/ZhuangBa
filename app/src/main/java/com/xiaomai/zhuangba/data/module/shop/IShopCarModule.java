package com.xiaomai.zhuangba.data.module.shop;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/9/27 0027
 */
public interface IShopCarModule extends IBaseModule<IShopCarView> {

    /**
     * 提交
     */
    void submitOrder();

}
