package com.xiaomai.zhuangba.util;

import android.util.SparseBooleanArray;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 * <p>
 * 全选
 */
public class AllElectionUtil {

    /**
     * 设置所有CheckBox的选中状态
     */
    public static void setStateCheckedMap(SparseBooleanArray stateCheckedMap,boolean isSelectedAll , int size) {
        for (int i = 0; i < size; i++) {
            stateCheckedMap.put(i, isSelectedAll);
        }
    }
}
