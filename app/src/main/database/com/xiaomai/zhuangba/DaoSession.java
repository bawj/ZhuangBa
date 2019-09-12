package com.xiaomai.zhuangba;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.bean.db.SlottingListDB;

import com.xiaomai.zhuangba.OrderServiceItemDao;
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.UserInfoDao;
import com.xiaomai.zhuangba.MaterialsListDBDao;
import com.xiaomai.zhuangba.ShopAuxiliaryMaterialsDBDao;
import com.xiaomai.zhuangba.SlottingListDBDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig orderServiceItemDaoConfig;
    private final DaoConfig pushNotificationDBDaoConfig;
    private final DaoConfig shopCarDataDaoConfig;
    private final DaoConfig userInfoDaoConfig;
    private final DaoConfig materialsListDBDaoConfig;
    private final DaoConfig shopAuxiliaryMaterialsDBDaoConfig;
    private final DaoConfig slottingListDBDaoConfig;

    private final OrderServiceItemDao orderServiceItemDao;
    private final PushNotificationDBDao pushNotificationDBDao;
    private final ShopCarDataDao shopCarDataDao;
    private final UserInfoDao userInfoDao;
    private final MaterialsListDBDao materialsListDBDao;
    private final ShopAuxiliaryMaterialsDBDao shopAuxiliaryMaterialsDBDao;
    private final SlottingListDBDao slottingListDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        orderServiceItemDaoConfig = daoConfigMap.get(OrderServiceItemDao.class).clone();
        orderServiceItemDaoConfig.initIdentityScope(type);

        pushNotificationDBDaoConfig = daoConfigMap.get(PushNotificationDBDao.class).clone();
        pushNotificationDBDaoConfig.initIdentityScope(type);

        shopCarDataDaoConfig = daoConfigMap.get(ShopCarDataDao.class).clone();
        shopCarDataDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        materialsListDBDaoConfig = daoConfigMap.get(MaterialsListDBDao.class).clone();
        materialsListDBDaoConfig.initIdentityScope(type);

        shopAuxiliaryMaterialsDBDaoConfig = daoConfigMap.get(ShopAuxiliaryMaterialsDBDao.class).clone();
        shopAuxiliaryMaterialsDBDaoConfig.initIdentityScope(type);

        slottingListDBDaoConfig = daoConfigMap.get(SlottingListDBDao.class).clone();
        slottingListDBDaoConfig.initIdentityScope(type);

        orderServiceItemDao = new OrderServiceItemDao(orderServiceItemDaoConfig, this);
        pushNotificationDBDao = new PushNotificationDBDao(pushNotificationDBDaoConfig, this);
        shopCarDataDao = new ShopCarDataDao(shopCarDataDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);
        materialsListDBDao = new MaterialsListDBDao(materialsListDBDaoConfig, this);
        shopAuxiliaryMaterialsDBDao = new ShopAuxiliaryMaterialsDBDao(shopAuxiliaryMaterialsDBDaoConfig, this);
        slottingListDBDao = new SlottingListDBDao(slottingListDBDaoConfig, this);

        registerDao(OrderServiceItem.class, orderServiceItemDao);
        registerDao(PushNotificationDB.class, pushNotificationDBDao);
        registerDao(ShopCarData.class, shopCarDataDao);
        registerDao(UserInfo.class, userInfoDao);
        registerDao(MaterialsListDB.class, materialsListDBDao);
        registerDao(ShopAuxiliaryMaterialsDB.class, shopAuxiliaryMaterialsDBDao);
        registerDao(SlottingListDB.class, slottingListDBDao);
    }
    
    public void clear() {
        orderServiceItemDaoConfig.clearIdentityScope();
        pushNotificationDBDaoConfig.clearIdentityScope();
        shopCarDataDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
        materialsListDBDaoConfig.clearIdentityScope();
        shopAuxiliaryMaterialsDBDaoConfig.clearIdentityScope();
        slottingListDBDaoConfig.clearIdentityScope();
    }

    public OrderServiceItemDao getOrderServiceItemDao() {
        return orderServiceItemDao;
    }

    public PushNotificationDBDao getPushNotificationDBDao() {
        return pushNotificationDBDao;
    }

    public ShopCarDataDao getShopCarDataDao() {
        return shopCarDataDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public MaterialsListDBDao getMaterialsListDBDao() {
        return materialsListDBDao;
    }

    public ShopAuxiliaryMaterialsDBDao getShopAuxiliaryMaterialsDBDao() {
        return shopAuxiliaryMaterialsDBDao;
    }

    public SlottingListDBDao getSlottingListDBDao() {
        return slottingListDBDao;
    }

}
