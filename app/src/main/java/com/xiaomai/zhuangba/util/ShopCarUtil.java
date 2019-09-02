package com.xiaomai.zhuangba.util;

import android.text.TextUtils;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.ShopAuxiliaryMaterialsDBDao;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.bean.Debugging;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.bean.db.SlottingListDB;
import com.xiaomai.zhuangba.data.db.DBHelper;

import java.math.BigDecimal;
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
        return new BigDecimal(totalMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

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
}
