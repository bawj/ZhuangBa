package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.Util;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 */
public class ServiceDetailFragment extends BaseFragment {

    @BindView(R.id.tvServiceDetailTitle)
    TextView tvServiceDetailTitle;
    @BindView(R.id.tvServiceDetailContent)
    TextView tvServiceDetailContent;
    @BindView(R.id.videoView)
    JCVideoPlayerStandard jcVideoPlayerStandard;

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String VIDEO = "video";
    public static final String VIDEO_IMG = "video_img";

    public static ServiceDetailFragment newInstance(String title, String content, String video, String videoImg) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        args.putString(VIDEO, video);
        args.putString(VIDEO_IMG, videoImg);
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        tvServiceDetailTitle.setText(getContentTitle());
        String content = getContent();
        if (getActivity() != null && !TextUtils.isEmpty(content)) {
            Util.setParagraphSpacing(getActivity(), tvServiceDetailContent, content, 24, 3);
        }
        String videoUrl = getVideoUrl();
        if (TextUtils.isEmpty(videoUrl)){
            jcVideoPlayerStandard.setVisibility(View.GONE);
        }else {
            Log.e("videoUrl = " + videoUrl);
            jcVideoPlayerStandard.setVisibility(View.VISIBLE);
            jcVideoPlayerStandard.setUp(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            GlideManager.loadImage(getActivity(), getVideoImg(), jcVideoPlayerStandard.thumbImageView);
        }
    }

    private String getContentTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

    private String getContent() {
        if (getArguments() != null) {
            return getArguments().getString(CONTENT);
        }
        return "";
    }

    private String getVideoUrl() {
        if (getArguments() != null) {
            return getArguments().getString(VIDEO);
        }
        return "";
    }

    private String getVideoImg() {
        if (getArguments() != null) {
            return getArguments().getString(VIDEO_IMG);
        }
        return "";
    }

    @Override
    protected void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_service_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getContentTitle();
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
