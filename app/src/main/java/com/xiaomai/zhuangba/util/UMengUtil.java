package com.xiaomai.zhuangba.util;

import com.example.toollib.util.Log;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.xiaomai.zhuangba.application.PretendApplication;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class UMengUtil {

    /**
     * 设置别名
     * @param phoneNumber 账号
     */
    public static void alias(String phoneNumber) {
        PushAgent.getInstance(PretendApplication.getInstance()).setAlias(phoneNumber, "zhuangba",
                new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {
                        Log.e("添加别名：-------->  " + isSuccess + "," + message);
                    }
                });
    }

    /**
     * 删除别名
     * @param phoneNumber 账号
     */
    public static void deleteAlias(String phoneNumber){
        PushAgent.getInstance(PretendApplication.getInstance()).deleteAlias(phoneNumber, "zhuangba", new UTrack.ICallBack(){
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.e("删除别名：-------->  " + isSuccess + "," + message);
            }
        });
    }

}
