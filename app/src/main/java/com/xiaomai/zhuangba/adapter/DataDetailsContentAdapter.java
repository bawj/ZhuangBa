package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.DataDetailsContentBean;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 */
public class DataDetailsContentAdapter extends BaseQuickAdapter<DataDetailsContentBean, BaseViewHolder> {

    public DataDetailsContentAdapter() {
        super(R.layout.fragment_data_details_content, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataDetailsContentBean item) {
        TextView tvDataDetailsContentNumber = helper.getView(R.id.tvDataDetailsContentNumber);
        TextView tvDataDetailsContentExplain = helper.getView(R.id.tvDataDetailsContentExplain);

        tvDataDetailsContentNumber.setText(item.getExplainContent());
        tvDataDetailsContentExplain.setText(item.getExplain());
    }
}
