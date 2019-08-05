package com.xiaomai.zhuangba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class MultiGraphSelectionAdapter extends RecyclerView.Adapter<MultiGraphSelectionAdapter.ViewHolder> {

    private Context mContext;
    private List<Uri> stringList;

    /**
     * 最多能添加3张图
     */
    private static final int MAX_IMG = 4;

    public MultiGraphSelectionAdapter(Context context, List<Uri> stringList) {
        this.mContext = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_multi_graph_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiGraphSelectionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ImageView ivItemMultiGraphMedia = holder.ivItemMultiGraphMedia;
        ImageView ivItemMultiGraphMediaAdd = holder.ivItemMultiGraphMediaAdd;
        ImageView ivItemMultiGraphSelectionDelete = holder.ivItemMultiGraphSelectionDelete;
        RelativeLayout relItemMultiGraph = holder.relItemMultiGraph;
        Uri uri = stringList.get(position);
        if (uri == null) {
            ivItemMultiGraphMediaAdd.setVisibility(View.VISIBLE);
            ivItemMultiGraphMedia.setVisibility(View.GONE);
            ivItemMultiGraphSelectionDelete.setVisibility(View.GONE);
            relItemMultiGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMultiGraphClickListener != null) {
                        onMultiGraphClickListener.addImg();
                    }
                }
            });
            relItemMultiGraph.setBackgroundResource(R.drawable.item_multi_graph_selection_bg);
        } else {
            relItemMultiGraph.setOnClickListener(null);
            relItemMultiGraph.setBackgroundResource(0);
            ivItemMultiGraphSelectionDelete.setVisibility(View.VISIBLE);
            ivItemMultiGraphSelectionDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stringList.remove(position);
                    notifyDataSetChanged();
                }
            });
            GlideManager.loadUriImage(mContext, uri, ivItemMultiGraphMedia);
            ivItemMultiGraphMediaAdd.setVisibility(View.GONE);
            ivItemMultiGraphMedia.setVisibility(View.VISIBLE);
            ivItemMultiGraphSelectionDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return stringList.size() > MAX_IMG ? MAX_IMG : stringList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivItemMultiGraphMedia)
        ImageView ivItemMultiGraphMedia;
        @BindView(R.id.ivItemMultiGraphMediaAdd)
        ImageView ivItemMultiGraphMediaAdd;
        @BindView(R.id.ivItemMultiGraphSelectionDelete)
        ImageView ivItemMultiGraphSelectionDelete;
        @BindView(R.id.relItemMultiGraph)
        RelativeLayout relItemMultiGraph;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnMultiGraphClickListener onMultiGraphClickListener;

    public void setOnMultiGraphClickListener(OnMultiGraphClickListener onRecyclerItemClickListener) {
        this.onMultiGraphClickListener = onRecyclerItemClickListener;
    }

    public interface OnMultiGraphClickListener {
        /**
         * 添加
         */
        void addImg();
    }

}
