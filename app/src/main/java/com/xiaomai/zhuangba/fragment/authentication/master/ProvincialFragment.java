package com.xiaomai.zhuangba.fragment.authentication.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ProvincialAdapter;
import com.xiaomai.zhuangba.data.bean.ProvincialBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 * <p>
 * 服务区域选择
 */
public class ProvincialFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rvProvincial)
    RecyclerView rvProvincial;
    private int parentId = 0;
    private int level = 1;
    private List<String> addressList = new ArrayList<>();
    private ProvincialAdapter provincialAdapter;

    public static ProvincialFragment newInstance() {
        Bundle args = new Bundle();
        ProvincialFragment fragment = new ProvincialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        rvProvincial.setLayoutManager(new LinearLayoutManager(getActivity()));
        provincialAdapter = new ProvincialAdapter();
        rvProvincial.setAdapter(provincialAdapter);
        provincialAdapter.setOnItemClickListener(this);
        request();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProvincialBean provincialBean = (ProvincialBean) view.findViewById(R.id.tvProvincialName).getTag();
        level++;
        parentId = provincialBean.getId();
        addressList.add(provincialBean.getName());
        request();
    }

    private void request() {
        Observable<HttpResult<List<ProvincialBean>>> region = ServiceUrl.getUserApi().getRegion(parentId, level);
        RxUtils.getObservable(region)
                .compose(this.<HttpResult<List<ProvincialBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<ProvincialBean>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<ProvincialBean> provincialBeans) {
                        if (provincialBeans != null && !provincialBeans.isEmpty()) {
                            provincialAdapter.setNewData(provincialBeans);
                        } else {
                            fragmentResultPop();
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        if (level > 0) {
                            level--;
                            if (!addressList.isEmpty()) {
                                addressList.remove(addressList.size() - 1);
                            }
                        }
                    }
                });
    }

    private void fragmentResultPop() {
        Intent bundle = new Intent();
        StringBuilder address = new StringBuilder();
        for (String s : addressList) {
            address.append(s);
        }
        bundle.putExtra(ForResultCode.RESULT_KEY.getExplain(), address.toString());
        setFragmentResult(ForResultCode.RESULT_OK.getCode(), bundle);
        popBackStack();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_provincial;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.service_region);
    }

}
