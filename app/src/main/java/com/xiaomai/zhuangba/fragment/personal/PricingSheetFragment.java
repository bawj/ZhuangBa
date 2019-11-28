package com.xiaomai.zhuangba.fragment.personal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.io.File;

import butterknife.BindView;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * 价目单
 */
public class PricingSheetFragment extends BaseFragment {

    @BindView(R.id.subsamplingScaleImageView)
    SubsamplingScaleImageView subsamplingScaleImageView;

    public static PricingSheetFragment newInstance() {
        Bundle args = new Bundle();
        PricingSheetFragment fragment = new PricingSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        subsamplingScaleImageView.setMinScale(1.0F);
        subsamplingScaleImageView.setMaxScale(10.0F);
        RxUtils.getObservable(ServiceUrl.getUserApi().getEnumerate("PRICELIST"))
                .compose(this.<HttpResult<String>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @SuppressLint("CheckResult")
                    @Override
                    protected void onSuccess(String provincialBeans) {
                        if (getActivity() != null) {
                            Glide.with(getActivity())
                                    .load(provincialBeans)
                                    .downloadOnly(new SimpleTarget<File>() {
                                        @Override
                                        public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                            try {
                                                int sWidth = BitmapFactory.decodeFile(resource.getAbsolutePath()).getWidth();
                                                int sHeight = BitmapFactory.decodeFile(resource.getAbsolutePath()).getHeight();
                                                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                                                if (wm != null) {
                                                    int height = wm.getDefaultDisplay().getHeight();
                                                    if (sHeight >= height
                                                            && sHeight / sWidth >= 3) {
                                                        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                                        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(1.0F, new PointF(0, 0), 0));
                                                    } else {
                                                        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                                                        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
                                                        subsamplingScaleImageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
                                                    }
                                                } else {
                                                    subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                                                    subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
                                                    subsamplingScaleImageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_pricing_sheet;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.price);
    }
}
