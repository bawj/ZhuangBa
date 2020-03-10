package com.xiaomai.zhuangba.util;

import android.text.TextUtils;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.ShopAuxiliaryMaterialsDBDao;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.Enumerate;
import com.xiaomai.zhuangba.data.bean.Debugging;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.bean.db.SlottingListDB;
import com.xiaomai.zhuangba.data.db.DBHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 */
public class ShopCarUtil {

    /**
     * 购物车单个商品
     *
     * @param item 商品
     * @return 购物车单个商品
     */
    public static ShopCarData getShopCarData(ServiceSubcategoryProject item) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        return shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(item.getServiceId())).unique();
    }

    /**
     * 添加到购物车
     *
     * @param item        商品
     * @param maintenance 维保价格
     * @param count       数量
     */
    public static void saveShopCar(ServiceSubcategoryProject item, Maintenance maintenance, int count) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        ShopCarData unique = shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(item.getServiceId())).unique();
        if (unique != null) {
            shopCarDataDao.delete(unique);
        }
        if (count != 0) {
            ShopCarData shopCarData = new ShopCarData();
            shopCarData.setId(Long.parseLong(String.valueOf(item.getServiceId())));
            shopCarData.setText(item.getServiceText());
            shopCarData.setIcon(item.getIconUrl());
            shopCarData.setMoney(String.valueOf(item.getPrice()));
            shopCarData.setMoney2(String.valueOf(item.getPrice2()));
            shopCarData.setMoney3(String.valueOf(item.getPrice3()));
            if (maintenance != null) {
                //维保价格  没有选择维保 为null
                shopCarData.setMaintenanceMoney(String.valueOf(maintenance.getAmount()));
                shopCarData.setMaintenanceId(maintenance.getId());
                shopCarData.setMaintenanceTime(String.valueOf(maintenance.getNumber()));
            }
            shopCarData.setNumber(String.valueOf(count));
            shopCarData.setServiceId(String.valueOf(item.getServiceId()));
            shopCarData.setParentServiceId(String.valueOf(item.getParentServiceId()));
            shopCarDataDao.insert(shopCarData);
        }
    }

    /**
     * 添加到购物车
     *
     * @param shopCar 商品
     */
    public static void saveDialogShopCar(ShopCarData shopCar, int count) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        ShopCarData unique = shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(shopCar.getServiceId())).unique();
        if (unique != null || count == 0) {
            shopCarDataDao.delete(unique);
        }
        if (count != 0) {
            ShopCarData shopCarData = new ShopCarData();
            shopCarData.setId(Long.parseLong(String.valueOf(shopCar.getServiceId())));
            shopCarData.setText(shopCar.getText());
            shopCarData.setIcon(shopCar.getIcon());
            shopCarData.setMoney(String.valueOf(shopCar.getMoney()));
            shopCarData.setMoney2(String.valueOf(shopCar.getMoney2()));
            shopCarData.setMoney3(String.valueOf(shopCar.getMoney3()));
            shopCarData.setMaintenanceMoney(String.valueOf(shopCar.getMaintenanceMoney()));
            shopCarData.setMaintenanceId(shopCar.getMaintenanceId());
            shopCarData.setMaintenanceTime(String.valueOf(shopCar.getMaintenanceTime()));
            shopCarData.setNumber(String.valueOf(count));
            shopCarData.setServiceId(String.valueOf(shopCar.getServiceId()));
            shopCarData.setParentServiceId(String.valueOf(shopCar.getParentServiceId()));
            shopCarDataDao.insert(shopCarData);
        }
    }


    /**
     * 修改维保
     *
     * @param shopCar     商品
     * @param maintenance 维保
     */
    public static void updateShopCarDialog(ShopCarData shopCar, Maintenance maintenance) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        ShopCarData unique = shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(shopCar.getServiceId())).unique();
        unique.setMaintenanceId(maintenance.getId());
        unique.setMaintenanceTime(String.valueOf(maintenance.getNumber()));
        unique.setMaintenanceMoney(String.valueOf(maintenance.getAmount()));
        shopCarDataDao.update(unique);
    }

    /**
     * 修改选择的开槽
     *
     * @param item 开槽长度
     */
    public static void updateSelectionSlotLength(SlottingListDB item) {
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();
        if (unique == null) {
            unique = new ShopAuxiliaryMaterialsDB();
            unique.setSlottingId(item.getId());
            unique.setSlottingSlottingId(item.getSlottingId());
            unique.setSlottingStartLength(item.getStartLength());
            unique.setSlottingEndLength(item.getEndLength());
            unique.setSlottingSlottingPrice(item.getSlottingPrice());
            shopAuxiliaryMaterialsDBDao.insert(unique);
        } else {
            unique.setSlottingId(item.getId());
            unique.setSlottingSlottingId(item.getSlottingId());
            unique.setSlottingStartLength(item.getStartLength());
            unique.setSlottingEndLength(item.getEndLength());
            unique.setSlottingSlottingPrice(item.getSlottingPrice());
            shopAuxiliaryMaterialsDBDao.update(unique);
        }
    }
    public static SlottingListDB getSelectionSlotLength() {
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();

        SlottingListDB slottingListDB = new SlottingListDB();
        slottingListDB.setId(unique.getSlottingId());
        slottingListDB.setStartLength(unique.getSlottingStartLength());
        slottingListDB.setEndLength(unique.getSlottingEndLength());
        slottingListDB.setSlottingPrice(unique.getSlottingSlottingPrice());

        return slottingListDB;
    }

    /**
     * 修改是否需要调试
     *
     * @param id    id
     * @param price 调试价格
     */
    public static void updateSelectDebugging(int id,Double price) {
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();
        if (unique == null) {
            unique = new ShopAuxiliaryMaterialsDB();
            unique.setDebuggingPrice(price);
            unique.setDebuggingId(id);
            shopAuxiliaryMaterialsDBDao.insert(unique);
        } else {
            unique.setDebuggingPrice(price);
            unique.setDebuggingId(id);
            shopAuxiliaryMaterialsDBDao.update(unique);
        }
    }

    public static Debugging getSelectDebugging() {
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();
        Debugging debugging = new Debugging();
        debugging.setId(unique.getDebuggingId());
        debugging.setPrice(unique.getDebuggingPrice());
        return debugging;
    }

    /**
     * 修改选择的辅材
     *
     * @param materialsListDB 开槽长度
     */
    public static void updateAuxiliaryMaterials(MaterialsListDB materialsListDB) {
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();
        if (unique == null) {
            unique = new ShopAuxiliaryMaterialsDB();
            unique.setMaterialsId(materialsListDB.getId());
            unique.setMaterialsSlottingId(materialsListDB.getSlottingId());
            unique.setMaterialsStartLength(materialsListDB.getStartLength());
            unique.setMaterialsEndLength(materialsListDB.getEndLength());
            unique.setMaterialsSlottingPrice(materialsListDB.getSlottingPrice());
            shopAuxiliaryMaterialsDBDao.insert(unique);
        } else {
            unique.setMaterialsId(materialsListDB.getId());
            unique.setMaterialsSlottingId(materialsListDB.getSlottingId());
            unique.setMaterialsStartLength(materialsListDB.getStartLength());
            unique.setMaterialsEndLength(materialsListDB.getEndLength());
            unique.setMaterialsSlottingPrice(materialsListDB.getSlottingPrice());
            shopAuxiliaryMaterialsDBDao.update(unique);
        }
    }

    public static MaterialsListDB getAuxiliaryMaterials(){
        ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao();
        ShopAuxiliaryMaterialsDB unique = shopAuxiliaryMaterialsDBDao.queryBuilder().unique();
        MaterialsListDB materialsListDB = new MaterialsListDB();
        materialsListDB.setId(unique.getMaterialsId());
        materialsListDB.setSlottingId(unique.getMaterialsSlottingId());
        materialsListDB.setStartLength(unique.getMaterialsStartLength());
        materialsListDB.setEndLength(unique.getMaterialsEndLength());
        materialsListDB.setSlottingPrice(unique.getMaterialsSlottingPrice());
        return materialsListDB;
    }

    /**
     * 计算 必选项 辅材 等的价格
     *
     * @return price
     */
    public static double getAuxiliaryMaterialsPrice() {
        ShopAuxiliaryMaterialsDB unique = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        if (unique != null) {
            //开槽价格
            double slottingSlottingPrice = unique.getSlottingSlottingPrice();
            //辅材价格
            double materialsSlottingPrice = unique.getMaterialsSlottingPrice();
            //调试价格
            double debuggingPrice = unique.getDebuggingPrice();
            return slottingSlottingPrice + materialsSlottingPrice + debuggingPrice;
        }
        return 0d;
    }


    /**
     * 计算任务数量
     *
     * @return int
     */
    public static Integer getTotalNumber() {
        Integer totalNumber = 0;
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        List<ShopCarData> list = shopCarDataDao.queryBuilder().list();
        for (ShopCarData shopCarData : list) {
            totalNumber = DensityUtils.stringTypeInteger(shopCarData.getNumber()) + totalNumber;
        }
        return totalNumber;
    }

    /**
     * 计算总金额
     *
     * @return int
     */
    public static Double getTotalMoney() {
        Double totalMoney = 0.0;
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        List<ShopCarData> list = shopCarDataDao.queryBuilder().list();
        for (ShopCarData shopCarData : list) {
            //商品数量
            String number = shopCarData.getNumber();
            //单个商品总价
            double singleMoney = 0.0;
            for (int i = 0; i < DensityUtils.stringTypeInteger(number); i++) {
                if (i == 0) {
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney());
                } else if (i == 1) {
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney2());
                } else if (i == 2) {
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney3());
                } else {
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney3());
                }
            }
            totalMoney = AmountUtil.add(totalMoney, singleMoney, 2);
            //维保价格
            String maintenanceMoney = shopCarData.getMaintenanceMoney();
            if (!TextUtils.isEmpty(maintenanceMoney)) {
                totalMoney += DensityUtils.stringTypeDouble(maintenanceMoney) * DensityUtils.stringTypeInteger(number);
            }
        }
        //计算辅材价格
        double price = ShopCarUtil.getAuxiliaryMaterialsPrice();
        totalMoney = totalMoney + price;
        //计算加急单价格
        totalMoney = totalMoney + getEnumeratePrice();
        return new BigDecimal(totalMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static Double getEnumeratePrice(){
        Enumerate enumerate = DBHelper.getInstance().getEnumerateDao().queryBuilder().unique();
        if (enumerate != null){
            return enumerate.getUrgentPrice();
        }
        return 0d;
    }

    /**
     * @param number 数量
     * @param price 价格1
     * @param price1 价格2
     * @param price2 价格3
     * @param maintenancePrice 维保金额
     * @return 服务项目带维保金额的价格
     */
    public static Double getTotalMoneys(int number, double price, double price1, double price2, double maintenancePrice) {
        Double priceNumber = 0.0;
        for (int i = 0; i < number; i++) {
            if (i == 0) {
                priceNumber += price;
            } else if (i == 1) {
                priceNumber += price1;
            } else if (i == 2) {
                priceNumber += price2;
            } else {
                priceNumber += price2;
            }
        }
        priceNumber += number * maintenancePrice;
        return new BigDecimal(priceNumber).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * @param number 数量
     * @param price 价格1
     * @param price1 价格2
     * @param price2 价格3
     * @return 服务项目不带维保金额的价格
     */
    public static Double getTotalMoney(int number, double price, double price1, double price2) {
        Double priceNumber = 0.0;
        for (int i = 0; i < number; i++) {
            if (i == 0) {
                priceNumber += price;
            } else if (i == 1) {
                priceNumber += price1;
            } else if (i == 2) {
                priceNumber += price2;
            } else {
                priceNumber += price2;
            }
        }
        return new BigDecimal(priceNumber).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static List<OrderServicesBean> getOrderServicesBean() {
        //服务项目{ 小类服务ID 数量 和 总价格}
        List<ShopCarData> shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();
        List<OrderServicesBean> orderServicesBeans = new ArrayList<>();
        for (ShopCarData shopCarData : shopCarDataList) {
            //大类服务ID
            //任务总数量
            int number = DensityUtils.stringTypeInteger(shopCarData.getNumber());
            //订单总金额
///            double orderAmount = ShopCarUtil.getTotalMoneys(number, DensityUtils.stringTypeDouble(shopCarData.getMoney())
//                    , DensityUtils.stringTypeDouble(shopCarData.getMoney2()), DensityUtils.stringTypeDouble(shopCarData.getMoney3())
//                    , DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
            double orderAmount = ShopCarUtil.getTotalMoney(number, DensityUtils.stringTypeDouble(shopCarData.getMoney())
                    , DensityUtils.stringTypeDouble(shopCarData.getMoney2()), DensityUtils.stringTypeDouble(shopCarData.getMoney3()));
            //订单服务项目
            OrderServicesBean orderServicesBean = new OrderServicesBean();
            //服务名称
            orderServicesBean.setServiceText(shopCarData.getText());
            //服务项目ID
            orderServicesBean.setServiceId(shopCarData.getServiceId());
            //服务项目数量
            orderServicesBean.setNumber(number);
            //服务项目总金额
            orderServicesBean.setAmount(orderAmount);
            //第一个价格
            orderServicesBean.setMoney(shopCarData.getMoney());
            //第二个价格
            orderServicesBean.setMoney2(shopCarData.getMoney2());
            //第三个价格
            orderServicesBean.setMoney3(shopCarData.getMoney3());
            //如果有维保 则ID != -1
            if (shopCarData.getMaintenanceId() != ConstantUtil.DEF_MAINTENANCE) {
                //维保时间 单位（月）
                orderServicesBean.setMonthNumber(DensityUtils.stringTypeInteger(shopCarData.getMaintenanceTime()));
                //维保 金额 * 项目数量
                double maintenanceAmount = DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()) * DensityUtils.stringTypeInteger(shopCarData.getNumber());
                orderServicesBean.setMaintenanceAmount(maintenanceAmount);
            }
            orderServicesBeans.add(orderServicesBean);
        }
        return orderServicesBeans;
    }


    /**
     * 辅材
     * @param hashMap map
     */
    public static void setAuxiliaryMaterials(HashMap<String, Object> hashMap) {
        //没有 添加图片 和 备注
        ShopAuxiliaryMaterialsDB unique = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        if (unique != null){
            //开槽
            hashMap.put("slottingStartLength", unique.getSlottingStartLength());
            hashMap.put("slottingEndLength", unique.getSlottingEndLength());
            hashMap.put("slottingPrice", unique.getSlottingSlottingPrice());
            //是否调试 0 调试 1 不调试
            hashMap.put("debugging", unique.getDebuggingPrice() == 0 ? 0 : 1);
            hashMap.put("debugPrice", unique.getDebuggingPrice());
            //辅材
            hashMap.put("materialsStartLength", unique.getMaterialsStartLength());
            hashMap.put("materialsEndLength", unique.getMaterialsEndLength());
            hashMap.put("materialsPrice", unique.getMaterialsSlottingPrice());
        }
    }
}
