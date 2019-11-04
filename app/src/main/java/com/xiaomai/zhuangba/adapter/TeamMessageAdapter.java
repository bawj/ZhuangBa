package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.TeamMessageBean;
import com.xiaomai.zhuangba.enums.StaticExplain;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 */
public class TeamMessageAdapter extends BaseQuickAdapter<TeamMessageBean , BaseViewHolder> {

    public TeamMessageAdapter() {
        super(R.layout.item_team_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamMessageBean teamMessageBean) {
        //邀请人电话
        TextView tvMemberPhone = helper.getView(R.id.tvMemberPhone);
        final String userNumber = teamMessageBean.getUserNumber();
        // 1被邀请 2 请求加入
        if (teamMessageBean.getIsAgree() == 2){
            tvMemberPhone.setText(mContext.getString(R.string.member_phone , userNumber));
        }else if (teamMessageBean.getIsAgree() == 1){
            tvMemberPhone.setText(mContext.getString(R.string.member_phone_ , userNumber));
        }
        //时间
        TextView tvTeamDate = helper.getView(R.id.tvTeamDate);
        tvTeamDate.setText(teamMessageBean.getCreateTime());
        TextView tvAgree = helper.getView(R.id.tvAgree);
        final int adapterPosition = helper.getAdapterPosition();
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    //同意
                    mOnSwipeListener.isAgree(userNumber,String.valueOf(StaticExplain.AGREE.getCode()), adapterPosition);
                }
            }
        });

        Button btnDelete = helper.getView(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    //不同意
                    mOnSwipeListener.isAgree(userNumber,String.valueOf(StaticExplain.REFUSE.getCode()), adapterPosition);
                }
            }
        });
    }

    private IOnSwipeListener mOnSwipeListener;

    public IOnSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(IOnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    public interface IOnSwipeListener {
        /**
         * 师傅加入团队
         * @param userNumber 手机号
         * @param isAgree 3:拒绝;4同意
         * @param pos position
         */
        void isAgree(String userNumber,String isAgree, int pos);
    }
}
