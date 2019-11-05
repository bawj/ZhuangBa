package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/9 0009
 */
public class BasePatrolMissionAdapter extends BaseSectionQuickAdapter<PatrolMissionBean, BaseViewHolder> {

    public BasePatrolMissionAdapter(int layoutResId, int sectionHeadResId, List<PatrolMissionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }
    @Override
    protected void convertHead(BaseViewHolder helper, PatrolMissionBean item) {
        TextView tvPatrolGroupTitle = helper.getView(R.id.tvPatrolGroupTitle);
        tvPatrolGroupTitle.setText(item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolMissionBean item) {
        PatrolBean.TasklistBean taskListBean = item.t;
        //标题
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        String villagename = taskListBean.getVillagename();
        if (!TextUtils.isEmpty(villagename)) {
            tvItemOrdersTitle.setText(villagename);
        } else {
            String street = taskListBean.getStreet();
            tvItemOrdersTitle.setText(street);
        }
        //数量
        TextView tvPatrolMissionChildNumber = helper.getView(R.id.tvPatrolMissionChildNumber);
        tvPatrolMissionChildNumber.setText(String.valueOf(taskListBean.getCount()));
        //地址
        TextView tvItemInspectionAddress = helper.getView(R.id.tvItemInspectionAddress);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(taskListBean.getProvince())
                .append(taskListBean.getCity()).append(taskListBean.getRegion())
                .append(taskListBean.getStreet()).append(taskListBean.getAddress());
        tvItemInspectionAddress.setText(stringBuilder);
        //3 / 25
        TextView tvPatrolMissionChildTotal = helper.getView(R.id.tvPatrolMissionChildTotal);
        tvPatrolMissionChildTotal.setText(mContext.getString(R.string.task_total
                , String.valueOf(taskListBean.getSuCount()) , String.valueOf(taskListBean.getCount())));
    }
}
