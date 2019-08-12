package com.xiaomai.zhuangba.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiaomai.zhuangba.DaoMaster;
import com.xiaomai.zhuangba.OrderServiceItemDao;
import com.xiaomai.zhuangba.PushNotificationDBDao;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.UserInfoDao;


/**
 * @author Administrator
 * @date 2019/4/25 0025
 * 数据库升级
 */
public class DBDevOpenHelper extends DaoMaster.OpenHelper {

    public DBDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DBDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db , UserInfoDao.class);
            MigrationHelper.migrate(db , PushNotificationDBDao.class);
            MigrationHelper.migrate(db , ShopCarDataDao.class);
            MigrationHelper.migrate(db , OrderServiceItemDao.class);
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }


}
