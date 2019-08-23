package com.xiaomai.zhuangba.data.module.selectservice;

import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.Slotting;
import com.xiaomai.zhuangba.data.module.base.IBaseListView;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 */
public interface ISelectServiceView extends IBaseListView {

    /**
     * serviceId
     * @return string
     */
    String getServiceId();

    /**
     * 请求成功
     * @param serviceSubcategories list
     */
    void requestServiceDataSuccess(List<ServiceSubcategory> serviceSubcategories);

    /**
     * 维保服务
     * @param serviceSubcategoryProject item
     * @param maintenanceList list
     */
    void requestMaintenance(ServiceSubcategoryProject serviceSubcategoryProject,List<Maintenance> maintenanceList);


    /**
     * 开槽 辅材
     * @param slotting 必选项
     */
    void slottingAndDebugSuccess(Slotting slotting);
}
