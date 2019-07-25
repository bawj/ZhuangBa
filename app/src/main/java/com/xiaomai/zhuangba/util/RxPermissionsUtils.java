package com.xiaomai.zhuangba.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.example.toollib.data.base.BaseCallback;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 * 权限
 */
public class RxPermissionsUtils {


    @SuppressLint("CheckResult")
    public static void applyPermission(Context mContext, String permission, final BaseCallback<String> baseCallback) {
        RxPermissions rxPermission = new RxPermissions((Activity) mContext);
        rxPermission.requestEach(permission)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            //用户已经同意该权限
                            baseCallback.onSuccess("");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            baseCallback.onFail("");
                        } else {
                            baseCallback.onFail("");
                            //用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void applyPermission(Context mContext, final BaseCallback<String> baseCallback, String... permissions) {
        new RxPermissions((Activity) mContext)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            baseCallback.onSuccess("");
                        } else {
                            baseCallback.onFail("");
                        }
                    }
                });
    }
}
