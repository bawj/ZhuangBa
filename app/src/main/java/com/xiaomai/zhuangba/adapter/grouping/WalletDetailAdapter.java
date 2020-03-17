package com.xiaomai.zhuangba.adapter.grouping;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/30 0030
 */
public class WalletDetailAdapter extends BaseExpandableListAdapter {

    private List<String> groupList;
    private List<List<WalletDetailBean.ListBean>> childrenList;
    private Context context;
    private LayoutInflater inflater;
    private int type;

    public WalletDetailAdapter(Context context, List<String> groupList, List<List<WalletDetailBean.ListBean>> childrenList, int type) {
        this.groupList = groupList;
        this.childrenList = childrenList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrenList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return childrenList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wallet_detail_group, parent, false);
        String title = groupList.get(groupPosition);
        Date date = DateUtil.strToDate_(title, "yyyy-MM");
        String timeTitle = DateUtil.dateToString(date, "yyyy年MM月");

        Long longDate = DateUtil.dateToCurrentTimeMillis(timeTitle, "yyyy年MM月");
        String dayTime = DateUtil.getYear() + "年" + (DateUtil.getMonth() + 1) + "月";
        Long longDayTime = DateUtil.dateToCurrentTimeMillis(dayTime , "yyyy年MM月");
        if (longDate.equals(longDayTime)) {
            timeTitle = timeTitle + "（本月）";
        }
        ((TextView) view.findViewById(R.id.tv_title)).setText(timeTitle);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wallet_detail_children, parent , false);
        TextView tvCode = view.findViewById(R.id.tv_order_code);
        TextView tvTime = view.findViewById(R.id.tv_order_time);
        TextView tvInfo = view.findViewById(R.id.tv_order_info);
        TextView tvType = view.findViewById(R.id.tv_type);

        /**
         * 提现列表与明细列表字段不一样，根据值判断当前界面
         */
        String orderCode = childrenList.get(groupPosition).get(childPosition).getOrderCode();
        String accountNumber = childrenList.get(groupPosition).get(childPosition).getAccountNumber();
        String str = (TextUtils.isEmpty(orderCode) || orderCode == null) ? accountNumber : orderCode;
        tvCode.setText(str);

        tvType.setText(context.getString(R.string.in_processing));
        tvType.setVisibility(View.GONE);
        //1,提现成功 6,提现中
        boolean b = type == WalletOrderTypeEnum.DETAIL_OUT.getCode() && childrenList.get(groupPosition).get(childPosition).getWallerType() == 6;
        tvType.setVisibility(b ? View.VISIBLE : View.GONE);

        String modifyTime = childrenList.get(groupPosition).get(childPosition).getModifyTime();
        String time = childrenList.get(groupPosition).get(childPosition).getTimes();
        tvTime.setText(TextUtils.isEmpty(modifyTime) ? DateUtil.getDate2String(DensityUtils.stringTypeLong(time) , "yyyy-MM-dd HH:mm:ss")
                : DateUtil.getDate2String(DensityUtils.stringTypeLong(modifyTime) , "yyyy-MM-dd HH:mm:ss"));

        //收入：1，支出：2
        String typeStr = "" ;
        ///int streamType = childrenList.get(groupPosition).get(childPosition).getStreamType();
        int wallerType = childrenList.get(groupPosition).get(childPosition).getWallerType();
        if (wallerType == 1 || wallerType == 6 || wallerType == 43){
            typeStr = "-";
            tvInfo.setTextColor(context.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        }else{
            typeStr = "+";
            tvInfo.setTextColor(context.getResources().getColor(R.color.tool_lib_color_3AB960));
        }
        double amount = childrenList.get(groupPosition).get(childPosition).getAmount();
        double orderAmount = childrenList.get(groupPosition).get(childPosition).getOrderAmount();
        double masterOrderAmount = childrenList.get(groupPosition).get(childPosition).getMasterOrderAmount();

        String money = "";
        if (type == WalletOrderTypeEnum.DETAIL_OUT.getCode()) {
            money = "" + amount;
        } else if (type == WalletOrderTypeEnum.DETAIL_INCOME.getCode()) {
            money = "" + masterOrderAmount;
        } else if (type == WalletOrderTypeEnum.DETAIL_ALL.getCode()) {
            if (childrenList.get(groupPosition).get(childPosition).getStreamType() == 1) {
                money = "" + masterOrderAmount;
            } else {
                money = "" + orderAmount;
            }
        }
        tvInfo.setText(typeStr + "¥" + money);
        tvCode.setTag(childrenList.get(groupPosition).get(childPosition).getStreamType());
        WalletDetailBean.ListBean listBean = childrenList.get(groupPosition).get(childPosition);
        tvTime.setTag(listBean);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void notifyDataChanged(List<String> groupList, List<List<WalletDetailBean.ListBean>> childrenList) {
        this.groupList = groupList;
        this.childrenList = childrenList;
        notifyDataSetChanged();
    }

    public void loadNotifyDataChanged(List<String> groupList, List<List<WalletDetailBean.ListBean>> childrenList) {
        this.groupList.addAll(groupList);
        this.childrenList.addAll(childrenList);
        notifyDataSetChanged();
    }
}
