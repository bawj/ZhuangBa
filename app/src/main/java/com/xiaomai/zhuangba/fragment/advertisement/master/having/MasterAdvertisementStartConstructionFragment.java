package com.xiaomai.zhuangba.fragment.advertisement.master.having;

import android.os.Bundle;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.QiNiuUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Administrator
 * date 2019/8/28 0028
 * <p>
 * 师傅 广告单 开始施工
 */
public class MasterAdvertisementStartConstructionFragment extends BaseAutographFragment {


    public static MasterAdvertisementStartConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementStartConstructionFragment fragment = new MasterAdvertisementStartConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeInstallation() {
        List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
        } else {
            RxUtils.getObservable(QiNiuUtil.newInstance().getObservable(uriList))
                    .compose(this.<List<String>>bindToLifecycle())
                    .doOnNext(new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> strings) throws Exception {
                        }
                    }).concatMap(new Function<List<String>, ObservableSource<HttpResult<Object>>>() {
                @Override
                public ObservableSource<HttpResult<Object>> apply(List<String> imgUrlList) throws Exception {
                    return RxUtils.getObservable(ServiceUrl.getUserApi()
                            .startTaskAdvertisingOrder(getOrderCode(), new Gson().toJson(imgUrlList)));
                }
            }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                @Override
                protected void onSuccess(Object response) {
                    //跳转到待开工
                    startFragment(MasterWorkerFragment.newInstance());
                }
            });
        }
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_start_construction;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.advertisement_start_construction);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.pre_construction_photos);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
