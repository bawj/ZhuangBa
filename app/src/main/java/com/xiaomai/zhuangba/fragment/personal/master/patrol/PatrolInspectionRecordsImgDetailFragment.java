package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 *
 * 图片
 */
public class PatrolInspectionRecordsImgDetailFragment extends BaseFragment {

    @BindView(R.id.tvImgDetailTitle)
    TextView tvImgDetailTitle;
    @BindView(R.id.ivImgDetailNoodles)
    ImageView ivImgDetailNoodles;

    private static final String IMG_URL = "img_url";
    private static final String TITLE = "title";

    public static PatrolInspectionRecordsImgDetailFragment newInstance(String imgUrl , String title) {
        Bundle args = new Bundle();
        args.putString(IMG_URL , imgUrl);
        args.putString(TITLE , title);
        PatrolInspectionRecordsImgDetailFragment fragment = new PatrolInspectionRecordsImgDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        tvImgDetailTitle.setText(getTitle());
        GlideManager.loadImage(getActivity() , getImgUrl() , ivImgDetailNoodles);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_img_detail;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    public String getTitle(){
        if (getArguments() != null){
            return getArguments().getString(TITLE);
        }
        return "";
    }

    public String getImgUrl(){
        if (getArguments() != null){
            return getArguments().getString(IMG_URL);
        }
        return "";
    }

}
