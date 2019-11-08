package com.xiaomai.zhuangba.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUIDefaultStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.SectionItem;
import com.xiaomai.zhuangba.data.bean.SectionHeader;
import com.xiaomai.zhuangba.weight.QDLoadingItemView;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class QDListSectionAdapter extends QMUIDefaultStickySectionAdapter<SectionHeader, SectionItem>  {

    private Context mContext;
    private int sectionIndex = -100;
    private int itemIndex = -100;

    @NonNull
    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_section_header , null);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionItemViewHolder(@NonNull ViewGroup viewGroup) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_section , null);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionLoadingViewHolder(@NonNull ViewGroup viewGroup) {
        return new ViewHolder(new QDLoadingItemView(viewGroup.getContext()));
    }

    @Override
    protected void onBindSectionHeader(final ViewHolder holder, final int position, QMUISection<SectionHeader, SectionItem> section) {
        TextView tvItemListSectionHeader = holder.itemView.findViewById(R.id.tvItemListSectionHeader);
        tvItemListSectionHeader.setText(section.getHeader().getText());
        ImageView ivArrowView = holder.itemView.findViewById(R.id.ivArrowView);
        ivArrowView.setRotation(section.isFold() ? 90f : 180f);
        ivArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.isForStickyHeader ? position : holder.getAdapterPosition();
                toggleFold(pos, false);
            }
        });
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<SectionHeader, SectionItem> section, int itemIndex) {
        ImageView ivSection = holder.itemView.findViewById(R.id.ivSection);
        TextView tvListSectionText = holder.itemView.findViewById(R.id.tvListSectionText);
        tvListSectionText.setText(section.getItemAt(itemIndex).getText());


        int sectionIndex = getSectionIndex(position);
        int itemIndexM = getItemIndex(position);

        if (this.sectionIndex == sectionIndex && this.itemIndex == itemIndexM){
            ivSection.setVisibility(View.VISIBLE);
            tvListSectionText.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        }else {
            ivSection.setVisibility(View.GONE);
            tvListSectionText.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        }
    }

    /**
     * 刷新数据
     * @param sectionIndex 第 n 组数据
     * @param itemIndex 第 n 组的 第 m 个
     */
    public void setSelect(int sectionIndex,int itemIndex) {
        this.sectionIndex = sectionIndex;
        this.itemIndex = itemIndex;
        notifyDataSetChanged();
    }
}
