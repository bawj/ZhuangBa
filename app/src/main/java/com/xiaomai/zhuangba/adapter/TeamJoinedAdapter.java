package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.TeamJoinedBean;
import com.xiaomai.zhuangba.util.DateUtil;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 */
public class TeamJoinedAdapter extends BaseQuickAdapter<TeamJoinedBean , BaseViewHolder> {

    public TeamJoinedAdapter() {
        super(R.layout.item_team_joined , null);
    }
    @Override
    protected void convert(BaseViewHolder helper, TeamJoinedBean teamJoinedBean) {
        TextView tvTeamDate = helper.getView(R.id.tvTeamDate);
        TextView tvTeamPhone = helper.getView(R.id.tvTeamPhone);
        TextView tvTeamMemberName = helper.getView(R.id.tvTeamMemberName);

        int id = teamJoinedBean.getId();
        if (id == 1){
            tvTeamMemberName.setText(mContext.getString(R.string.captain , teamJoinedBean.getUserName()));
        }else {
            tvTeamMemberName.setText(teamJoinedBean.getUserName());
        }
        tvTeamDate.setText(DateUtil.dateToFormat(teamJoinedBean.getCreatetime() , "yyyy-MM-dd HH:mm:ss" , "yyyy/MM/dd"));
        tvTeamPhone.setText(teamJoinedBean.getUsernumber());
    }
}
