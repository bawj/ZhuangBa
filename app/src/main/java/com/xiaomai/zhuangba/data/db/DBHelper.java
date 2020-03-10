package com.xiaomai.zhuangba.data.db;


import com.xiaomai.zhuangba.DaoSession;
import com.xiaomai.zhuangba.EnumerateDao;
import com.xiaomai.zhuangba.OrderServiceItemDao;
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.ShopAuxiliaryMaterialsDBDao;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.UserInfoDao;

/**
 * DB帮助类
 *
 * @author Administrator
 * @date 2019/4/25
 */
public class DBHelper {

    private DaoSession daoSession;
    private static volatile DBHelper mInstance;

    private DBHelper() {
        daoSession = DBManager.getInstance().getDaoSession();
    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用户表
     *
     * @return userInfoDao
     */
    public UserInfoDao getUserInfoDao() {
        return daoSession.getUserInfoDao();
    }

    /**
     * 通知消息
     *
     * @return PushNotificationDBDao
     */
    public PushNotificationDBDao getPushNotificationDBDao() {
        return daoSession.getPushNotificationDBDao();
    }

    /**
     * 购物车
     */
    public ShopCarDataDao getShopCarDataDao() {
        return daoSession.getShopCarDataDao();
    }

    /**
     * 加急单
     */
    public EnumerateDao getEnumerateDao() {
        return daoSession.getEnumerateDao();
    }

    /**
     * 续维保
     */
    public OrderServiceItemDao getOrderServiceItemDao() {
        return daoSession.getOrderServiceItemDao();
    }

    /**
     * 购物车 辅材 开槽 等必选项
     * @return dao
     */
    public ShopAuxiliaryMaterialsDBDao getShopAuxiliaryMaterialsDBDao(){
        return daoSession.getShopAuxiliaryMaterialsDBDao();
    }
}
