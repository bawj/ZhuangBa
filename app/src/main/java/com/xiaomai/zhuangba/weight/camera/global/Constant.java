package com.xiaomai.zhuangba.weight.camera.global;


import com.xiaomai.zhuangba.weight.camera.utils.FileUtils;

import java.io.File;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/10
 * Desc	        ${常量}
 */
public class Constant {
    /** app名称 */
    public static final String APP_NAME = "ZhuangBa";
    /** ZhuangBa/ */
    public static final String BASE_DIR = APP_NAME + File.separator;
    /** 文件夹根目录 /storage/emulated/0/WildmaIDCardCamera/ */
    public static final String DIR_ROOT = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;
}