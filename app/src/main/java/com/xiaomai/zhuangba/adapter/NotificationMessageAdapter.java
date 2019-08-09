package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PushNotificationDB;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class NotificationMessageAdapter extends BaseQuickAdapter<PushNotificationDB, BaseViewHolder> {


    public NotificationMessageAdapter() {
        super(R.layout.item_notification_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PushNotificationDB item) {
        TextView tvNotificationTitle = helper.getView(R.id.tvNotificationTitle);
        TextView tvNotificationDate = helper.getView(R.id.tvNotificationDate);
        TextView tvNotificationContent = helper.getView(R.id.tvNotificationContent);

        tvNotificationTitle.setText(TextUtils.isEmpty(item.getTicker()) ? "" : item.getTicker());
        tvNotificationDate.setText(TextUtils.isEmpty(item.getTime()) ? "" : item.getTime());
        tvNotificationContent.setText(TextUtils.isEmpty(item.getText()) ? "" : item.getText());

        tvNotificationContent.setTag(item);
    }
}
