package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PhotoStyleAdapter;
import com.xiaomai.zhuangba.data.bean.BaseAdvertisementPhotoTabEntity;
import com.xiaomai.zhuangba.data.bean.Rules;
import com.xiaomai.zhuangba.data.bean.StayUrl;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

public class PhotoStyleFragment extends BaseFragment implements OnRefreshListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    @Nullable
    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;
    @BindView(R.id.tvPhotoExplain)
    TextView tvPhotoExplain;


    private PhotoStyleAdapter photoStyleAdapter;
    public static final String SERVICE_ID = "service_id";

    public static PhotoStyleFragment newInstance(String serviceId) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID , serviceId);
        PhotoStyleFragment fragment = new PhotoStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshListener(this);
        rvBaseList.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        photoStyleAdapter = new PhotoStyleAdapter();
        rvBaseList.setAdapter(photoStyleAdapter);
        rvBaseList.addItemDecoration(new GridSpacingItemDecoration(2, 11, false));
        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getPhotoRules(getServiceId()))
                .compose(this.<HttpResult<Rules>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Rules>() {
                    @Override
                    protected void onSuccess(Rules rules) {
                        tvPhotoExplain.setText(rules.getNotice());
                        String pictUrl = rules.getPictUrl();
                        StayUrl stayUrl = new Gson().fromJson(pictUrl , StayUrl.class);
                        List<BaseAdvertisementPhotoTabEntity> photoTabEntityList = new ArrayList<>();
                        photoTabEntityList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderCloseRange() , getString(R.string.panorama)));
                        photoTabEntityList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderProspect() , getString(R.string.with_head_close_up)));
                        photoTabEntityList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderProspect() , getString(R.string.with_head_vision)));
                        photoTabEntityList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getOther() , getString(R.string.other)));
                        photoStyleAdapter.setNewData(photoTabEntityList);
                        refreshLayout.finishRefresh();
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_photo_style;
    }

    private String getServiceId(){
        if (getArguments() != null){
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.photo_style);
    }
}
