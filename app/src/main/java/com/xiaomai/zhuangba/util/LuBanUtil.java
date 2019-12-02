package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.camera.global.Constant;
import com.xiaomai.zhuangba.weight.camera.utils.FileUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class LuBanUtil {

    private static LuBanUtil luBanUtil = null;

    public static LuBanUtil getInstance() {
        if (luBanUtil == null) {
            luBanUtil = new LuBanUtil();
        }
        return luBanUtil;
    }

    /**
     * @param mContext           context
     * @param resultUri          需要压缩的图片路径
     * @param stringBaseCallback callback
     */
    public void compress(Context mContext, final Uri resultUri, final BaseCallback<Uri> stringBaseCallback) {
        //判断文件目录是否创建
        boolean orExistsDir = FileUtils.createOrExistsDir(Constant.DIR_ROOT);
        Log.e("文件创建" + (orExistsDir ? "成功" : "失败"));
        //压缩图片
        Luban.with(mContext)
                .load(resultUri)
                .ignoreBy(100)
                .setTargetDir(Constant.DIR_ROOT)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //压缩开始前调用，可以在方法内启动 loading UI
                        Log.e("开始压缩 时间 = " + System.currentTimeMillis());
                    }

                    @Override
                    public void onSuccess(File file) {
                        //压缩成功后调用，返回压缩后的图片文件
                        Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                        Log.e("压缩图片地址 = " + file.getPath());
                        stringBaseCallback.onSuccess( Uri.parse("file:///" + file.getPath()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        //当压缩过程出现问题时调用
                        Log.e("压缩失败 error " + e);
                        stringBaseCallback.onSuccess(resultUri);
                    }
                }).launch();

    }


    /**
     * @param mContext           context
     * @param resultUri          需要压缩的图片路径
     * @param stringBaseCallback callback
     */
    public void compress(Context mContext, final String resultUri, final BaseCallback<String> stringBaseCallback) {
        //判断文件目录是否创建
        boolean orExistsDir = FileUtils.createOrExistsDir(Constant.DIR_ROOT);
        Log.e("文件创建" + (orExistsDir ? "成功" : "失败"));
        //压缩图片
        Luban.with(mContext)
                .load(resultUri)
                .ignoreBy(100)
                .setTargetDir(Constant.DIR_ROOT)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //压缩开始前调用，可以在方法内启动 loading UI
                        Log.e("开始压缩 时间 = " + System.currentTimeMillis());
                    }

                    @Override
                    public void onSuccess(File file) {
                        //压缩成功后调用，返回压缩后的图片文件
                        Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                        Log.e("压缩图片地址 = " + file.getPath());
                        stringBaseCallback.onSuccess(file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //当压缩过程出现问题时调用
                        Log.e("压缩失败 error " + e);
                        stringBaseCallback.onSuccess(resultUri);
                    }
                }).launch();

    }


}
