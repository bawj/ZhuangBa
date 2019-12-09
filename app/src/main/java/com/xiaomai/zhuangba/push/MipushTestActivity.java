package com.xiaomai.zhuangba.push;

import android.content.Intent;
import android.os.Bundle;

import com.example.toollib.util.Log;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

/**
 * oppo 推送
 */
public class MipushTestActivity extends UmengNotifyClickActivity {

    private static String TAG = MipushTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //setContentView(R.layout.activity_mipush);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(body);
    }
}
