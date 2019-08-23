package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;

/**
 * @author Administrator
 * @date 2019/8/22 0022
 */
public class PayDepositAdapter extends BaseQuickAdapter<PayDepositBean , BaseViewHolder> {

    private int masterRankId;
    private IPayDepositInterface iPayDepositInterface;

    public PayDepositAdapter() {
        super(R.layout.item_pay_deposit, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayDepositBean item) {
        TextView tvPayDepositMoney = helper.getView(R.id.tvPayDepositMoney);
        TextView tvDepositTitle = helper.getView(R.id.tvDepositTitle);
        TextView tvPayDepositMsg = helper.getView(R.id.tvPayDepositMsg);
        ImageView ivPayDepositMoney = helper.getView(R.id.ivPayDepositMoney);

        tvPayDepositMoney.setText(String.valueOf(item.getBond()));
        tvDepositTitle.setText(item.getMasterRankName());
        tvPayDepositMsg.setText(item.getExplain());
        if (masterRankId == item.getMasterRankId()){
            ivPayDepositMoney.setBackgroundResource(R.drawable.ic_deposit_money);
            if (iPayDepositInterface != null){
                iPayDepositInterface.callBack(item);
            }
        }else {
            ivPayDepositMoney.setBackgroundResource(R.drawable.checkbox_deposit_nor);
        }
    }

    public void notifyPayDeposit(int masterRankId){
        this.masterRankId = masterRankId;
        notifyDataSetChanged();
    }

    public interface IPayDepositInterface{

        void callBack(PayDepositBean item);

    }

    public void setCallBack(IPayDepositInterface iPayDepositInterface){
        this.iPayDepositInterface = iPayDepositInterface;
    }

}
