package com.xiaomai.zhuangba.data.module.selectservice;

import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 */
public interface ISelectServiceModule extends IBaseModule<ISelectServiceView> {

    /**
     * 查询 服务项目
     */
    void requestServiceData();

    /**
     * 查询维保
     * @param item content
     */
    void requestMaintenance(ServiceSubcategoryProject item);


    /**
     * 查询开槽、辅材和调试
     */
    void requestSlottingAndDebug();
}
