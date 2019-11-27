package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.widget.TextView;

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
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.QiNiuUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 验收确认
 */
public class NewSubmitAcceptanceFragment extends BaseAutographFragment {

    /**
     * 定位点
     */
    @BindView(R.id.tvStartConstructionLocation)
    TextView tvStartConstructionLocation;

    public static NewSubmitAcceptanceFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        NewSubmitAcceptanceFragment fragment = new NewSubmitAcceptanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        MapUtils.location(getActivity(), tvStartConstructionLocation);
    }

    @Override
    public void beforeInstallation() {
        List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
        }  else {
            //图片集合
            uploadImg(uriList);
        }
    }

    private void uploadImg(List<String> uriList) {
        RxUtils.getObservable(QiNiuUtil.newInstance().getObservable(uriList))
                .compose(this.<List<String>>bindToLifecycle())
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                    }
                }).concatMap(new Function<List<String>, ObservableSource<HttpResult<Object>>>() {
            @Override
            public ObservableSource<HttpResult<Object>> apply(List<String> imgUrlList) throws Exception {
                HashMap<String, Object> hashMap = new HashMap<>();
                //现场负责人签名图片
                hashMap.put("electronicSignature", tvStartConstructionLocation.getText().toString());
                //订单编号
                hashMap.put("orderCode", getOrderCode());
                //图片地址
                hashMap.put("picturesUrl", new Gson().toJson(imgUrlList));
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                return  RxUtils.getObservable(ServiceUrl.getUserApi().submitValidation(requestBody));
            }
        }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
            @Override
            protected void onSuccess(Object response) {
                //跳转到待开工
                startFragment(MasterWorkerFragment.newInstance());
            }
        });
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    private String getOrderType() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_TYPE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.acceptance_confirmation);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_new_submit_acceptance;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.advertisement_start_construction_complete);
    }

    @Override
    public String getAutographServiceTip() {
        return getString(R.string.agree_to_start_work);
    }

}
