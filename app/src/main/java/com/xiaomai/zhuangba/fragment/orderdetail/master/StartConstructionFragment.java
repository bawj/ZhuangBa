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
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 开始施工
 */
public class StartConstructionFragment extends BaseAutographFragment {

    /**
     * 定位点
     */
    @BindView(R.id.tvStartConstructionLocation)
    TextView tvStartConstructionLocation;


    public static StartConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        StartConstructionFragment fragment = new StartConstructionFragment();
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
        } else {
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
                return RxUtils.getObservable(ServiceUrl.getUserApi().startTaskOrder(getOrderCode(),
                        new Gson().toJson(imgUrlList), tvStartConstructionLocation.getText().toString()));
            }
        }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
            @Override
            protected void onSuccess(Object response) {
                //跳转到待开工
                startFragment(MasterWorkerFragment.newInstance());
            }
        });
    }

    /* @Override
    public void beforeInstallation() {
        //提交
        List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
        } else {
            try {
                //安装前 图片集合
                List<MultipartBody.Part> parts = new ArrayList<>();
                for (int i = 0; i < uriList.size(); i++) {
                    Uri compressPath = Uri.parse(uriList.get(i));
                    File file = new File(new URI(compressPath.toString()));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                    parts.add(body);
                }
                Observable<HttpResult<Object>> responseBodyObservable = RxUtils.getObservableZip(
                        ServiceUrl.getUserApi().uploadFiles(parts).subscribeOn(Schedulers.io()));
                RxUtils.getObservable(responseBodyObservable)
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .doOnNext(new BaseHttpConsumer<Object>() {
                            @Override
                            public void httpConsumerAccept(HttpResult<Object> httpResult) {
                            }
                        })
                        .concatMap(new Function<HttpResult<Object>, ObservableSource<HttpResult<Object>>>() {
                            @Override
                            public ObservableSource<HttpResult<Object>> apply(HttpResult<Object> httpResult) throws Exception {
                                return RxUtils.getObservable(ServiceUrl.getUserApi().startTaskOrder(getOrderCode(),
                                        httpResult.getData().toString(), tvStartConstructionLocation.getText().toString()));
                            }
                        })
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object object) {
                                //跳转到待开工
                                startFragment(MasterWorkerFragment.newInstance());
                            }
                        });

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }*/

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
        return getString(R.string.startup_confirmation);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_start_construction;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.job_site_font_installation_img);
    }

    @Override
    public String getAutographServiceTip() {
        return getString(R.string.agree_to_start_work);
    }


    @Override
    public void onStop() {
        super.onStop();
        MapUtils.stopLocation();
    }
}
