package com.xiaomai.zhuangba.util;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
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
     * @param item  商品
     * @param count 数量
     */
    public static void saveShopCar(ServiceSubcategoryProject item, int count) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        ShopCarData unique = shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(item.getServiceId())).unique();
        if (unique != null || count == 0) {
            shopCarDataDao.delete(unique);
        }
        if (count != 0) {
            ShopCarData shopCarData = new ShopCarData();
            shopCarData.setId(Long.parseLong(String.valueOf(item.getServiceId())));
            shopCarData.setText(item.getServiceText());
            shopCarData.setIcon(item.getIconUrl());
            shopCarData.setMoney(String.valueOf(item.getPrice()));
            shopCarData.setNumber(String.valueOf(count));
            shopCarData.setServiceId(String.valueOf(item.getServiceId()));
            shopCarData.setParentServiceId(String.valueOf(item.getParentServiceId()));
            shopCarDataDao.insert(shopCarData);
        }
    }

    /**
     * 添加到购物车
     * @param shopCar 商品
     */
    public static void saveDialogShopCar(ShopCarData shopCar, int count){
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
            shopCarData.setNumber(String.valueOf(count));
            shopCarData.setServiceId(String.valueOf(shopCar.getServiceId()));
            shopCarData.setParentServiceId(String.valueOf(shopCar.getParentServiceId()));
            shopCarDataDao.insert(shopCarData);
        }
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
                if (i == 0){
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney());
                }else if (i == 1){
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney2());
                }else if (i == 2){
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney3());
                }else{
                    singleMoney += DensityUtils.stringTypeDouble(shopCarData.getMoney3());
                }
            }
            totalMoney = AmountUtil.add(totalMoney, singleMoney, 2);
        }
        return totalMoney;
    }

    public static Double getTotalMoneys(int number , double price , double price1 , double price2){
        Double priceNumber = 0.0;
        for (int i = 0; i < number; i++) {
            if (i == 0){
                priceNumber += price;
            }else if (i == 1){
                priceNumber += price1;
            }else if (i == 2){
                priceNumber += price2;
            }else{
                priceNumber += price2;
            }
        }
        return new BigDecimal(priceNumber).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
