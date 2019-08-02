package com.xiaomai.zhuangba.util;

import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class UserInfoUtil {

    public static String getRole(){
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo != null){
            return userInfo.getRole();
        }else{
            return "";
        }
    }

}
