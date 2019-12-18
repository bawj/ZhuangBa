package com.xiaomai.zhuangba.data.module.orderinformation;

import com.example.toollib.data.base.IBaseView;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public interface IOrderInformationView extends IBaseView {

    /**
     * 获取大类ID
     *
     * @return string
     */
    String getLargeClassServiceId();

    /**
     * 获取大类名称
     *
     * @return string
     */
    String getLargeServiceText();


    /**
     * 订单编号
     *
     * @return string
     */
    String getOrderCode();


    /**
     * 订单状态
     *
     * @return string
     */
    String getOrderStatus();


    /**
     * 电话
     *
     * @return string
     */
    String getPhoneNumber();

    /**
     * 姓名
     *
     * @return string
     */
    String getUserName();

    /**
     * 地址
     *
     * @return string
     */
    String getAddress();

    /**
     * 详细地址
     *
     * @return string
     */
    String getAddressDetail();

    /**
     * 预约时间
     *
     * @return string
     */
    String getAppointmentTime();

    /**
     * 经度
     *
     * @return string
     */
    String getLongitude();

    /**
     * 纬度
     *
     * @return string
     */
    String getLatitude();

    /**
     * 合同编号
     *
     * @return string
     */
    String getContractNo();

    /**
     * 客户经理
     * @return string
     */
    String getAccountManager();

    /**
     * 项目名称
     * @return string
     */
    String getProjectName();

    /**
     * 项目特点
     * @return string
     */
    String getProjectFeatures();

    /**
     * 店铺名称
     * @return string
     */
    String getShopName();

    /**
     * 第三方订单编号
     * @return string
     */
    String getOrderNumber();

    /**
     * 提交成功
     *
     * @param requestBodyString string 提交json
     */
    void placeOrderSuccess(String requestBodyString);


    /**
     * 修改成功
     */
    void updateOrderSuccess();

    /**
     * 提交安装备注
     *
     * @return list
     */
    List<String> getMediaSelectorFiles();


    /**
     * 雇主描述
     *
     * @return string
     */
    String getEmployerDescribe();
}
