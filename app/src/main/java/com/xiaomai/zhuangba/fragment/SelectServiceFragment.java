package com.xiaomai.zhuangba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.Log;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ServiceContentAdapter;
import com.xiaomai.zhuangba.adapter.ServiceTitleAdapter;
import com.xiaomai.zhuangba.adapter.SheetBehaviorAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.selectservice.ISelectServiceModule;
import com.xiaomai.zhuangba.data.module.selectservice.ISelectServiceView;
import com.xiaomai.zhuangba.data.module.selectservice.SelectServiceModule;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.service.ServiceDetailFragment;
import com.xiaomai.zhuangba.fragment.service.ShopCarFragment;
import com.xiaomai.zhuangba.util.SheetBehaviorUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.util.TopBarLayoutUtil;
import com.xiaomai.zhuangba.weight.dialog.ChoosingGoodsDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * <p>
 * 选择服务
 */
public class SelectServiceFragment extends BaseListFragment<ISelectServiceModule, ServiceTitleAdapter>
        implements ServiceContentAdapter.IServiceContentOnAddDelListener, BaseQuickAdapter.OnItemClickListener,
        SheetBehaviorAdapter.ISheetBehaviorListener, ISelectServiceView {

    /**
     * 大类ID
     */
    public static final String SERVICE_ID = "service_id";
    /**
     * 大类名称
     */
    public static final String SERVICE_TEXT = "service_text";

    /**
     * 服务类小类名称
     */
    @BindView(R.id.rvSelectServiceTitle)
    RecyclerView rvSelectServiceTitle;
    /**
     * 服务小类 内容
     */
    @BindView(R.id.rvSelectServiceContent)
    RecyclerView rvSelectServiceContent;
    /**
     * 服务类小类名称
     */
    @BindView(R.id.tvServiceContentTitle)
    TextView tvServiceContentTitle;
    @BindView(R.id.laySelectService)
    LinearLayout laySelectService;
    @BindView(R.id.tvSelectServiceTaskNumber)
    TextView tvSelectServiceTaskNumber;
    @BindView(R.id.tvSelectServiceMonty)
    TextView tvSelectServiceMonty;
    @BindView(R.id.itemSelectServiceLayoutFor)
    QMUILinearLayout itemSelectServiceLayoutFor;
    @BindView(R.id.layBottomSheetDialog)
    LinearLayout layBottomSheetDialog;
    @BindView(R.id.topBarSelectService)
    QMUITopBarLayout topBarSelectService;
    @BindView(R.id.viewSelectServiceBlack)
    View viewSelectServiceBlack;
    @BindView(R.id.rvBottomSheetShop)
    RecyclerView rvBottomSheetShop;
    @BindView(R.id.tvShopCarEmpty)
    TextView tvShopCarEmpty;
    /**
     * 服务类名称
     */
    private ServiceTitleAdapter serviceTitleAdapter;
    /**
     * 选中的 标记
     */
    private String serviceText;
    private int serviceTitlePosition = 0;
    /**
     * 服务 内容
     */
    private ServiceContentAdapter serviceContentAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private SheetBehaviorAdapter sheetBehaviorAdapter;

    public static SelectServiceFragment newInstance(String serviceId, String serviceText) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, serviceId);
        args.putString(SERVICE_TEXT, serviceText);
        SelectServiceFragment fragment = new SelectServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        //类目
        rvSelectServiceTitle.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceTitleAdapter = new ServiceTitleAdapter();
        rvSelectServiceTitle.setAdapter(serviceTitleAdapter);
        serviceTitleAdapter.setOnItemClickListener(this);
        //内容
        rvSelectServiceContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceContentAdapter = new ServiceContentAdapter();
        rvSelectServiceContent.setAdapter(serviceContentAdapter);
        serviceContentAdapter.setOnItemClickListener(this);
        serviceContentAdapter.setOnAddDelListener(this);

        //弹出已选择的服务
        bottomSheetBehavior = BottomSheetBehavior.from(layBottomSheetDialog);
        SheetBehaviorUtil.setBehavior(bottomSheetBehavior, layBottomSheetDialog, viewSelectServiceBlack);
        rvBottomSheetShop.setLayoutManager(new LinearLayoutManager(getActivity()));
        sheetBehaviorAdapter = new SheetBehaviorAdapter();
        rvBottomSheetShop.setAdapter(sheetBehaviorAdapter);
        sheetBehaviorAdapter.setOnAddDelListener(this);

        //设置标题
        TopBarLayoutUtil.setTopBarTitle(topBarSelectService, getString(R.string.select_service));
        //设置返回
        TopBarLayoutUtil.addLeftImageBlackButton(topBarSelectService, getBaseFragmentActivity());

        //默认清除购物车(也可以不清除)
        DBHelper.getInstance().getShopCarDataDao().deleteAll();
        updateUi();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        iModule.requestServiceData();
    }

    @Override
    public void onAddOrDelSuccess(ServiceSubcategoryProject item) {
        //选择规格  查询 维保类型
        iModule.requestMaintenance(item);
    }

    @Override
    public void requestMaintenance(final ServiceSubcategoryProject serviceSubcategoryProject, List<Maintenance> maintenanceList) {
        ChoosingGoodsDialog
                .getInstance()
                .initView(serviceSubcategoryProject,getActivity())
                .setTvChoosingGoodsName(serviceSubcategoryProject.getServiceText())
                .setRvChoosingGoods(getActivity(),serviceSubcategoryProject,maintenanceList)
                .setICallBase(new ChoosingGoodsDialog.BaseCallback() {
                    @Override
                    public void sure(Maintenance maintenance,int count) {
                        Log.e("添加数量 = " + count);
                        //确定 添加
                        if (count > 0) {
                            ShopCarUtil.saveShopCar(serviceSubcategoryProject,maintenance, count);
                            sheetBehaviorUpdate();
                        }
                    }
                })
                .showDialog();
    }

    @Override
    public void sheetBehaviorUpdate() {
        serviceContentAdapter.notifyDataSetChanged();
        sheetBehaviorAdapter.setNewData(DBHelper.getInstance().getShopCarDataDao().queryBuilder().list());
        updateUi();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == serviceTitleAdapter) {
            ServiceSubcategory serviceSubcategory = (ServiceSubcategory)
                    view.findViewById(R.id.tvServiceTitleAdapterName).getTag();
            serviceText = serviceSubcategory.getServiceText();
            serviceTitleAdapter.setItemChecked(serviceText);
            serviceContentAdapter.setNewData(serviceSubcategory.getServicePoolList());
            serviceTitlePosition = position;
            tvServiceContentTitle.setText(serviceText);
        } else if (adapter == serviceContentAdapter) {
            ServiceSubcategoryProject serviceSubcategoryProject = (ServiceSubcategoryProject)
                    view.findViewById(R.id.tvServiceContentMoney).getTag();
            startFragment(ServiceDetailFragment.newInstance(serviceSubcategoryProject.getServiceText(),
                    serviceSubcategoryProject.getServiceStandard()));
        }
    }

    @OnClick({R.id.itemSelectServiceLayoutFor, R.id.btnSelectServiceNext, R.id.tvShopCarEmpty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemSelectServiceLayoutFor:
                //弹出 bottomSheetBehavior 根据状态不同显示隐藏
                int state = bottomSheetBehavior.getState();
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //显示 更新 bottomSheetBehavior
                    sheetBehaviorAdapter.setNewData(DBHelper.getInstance().getShopCarDataDao().queryBuilder().list());
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.btnSelectServiceNext:
                Log.e("btnSelectServiceNext 点击下一步");
                Integer totalNumber = ShopCarUtil.getTotalNumber();
                if (totalNumber != 0) {
                    startFragmentForResult(ShopCarFragment.newInstance(getServiceId() , getServiceText()) , ForResultCode.START_FOR_RESULT_CODE_.getCode());
                }
                break;
            case R.id.tvShopCarEmpty:
                //清空
                DBHelper.getInstance().getShopCarDataDao().deleteAll();
                sheetBehaviorUpdate();
                break;
            default:
        }
    }

    @Override
    public void requestServiceDataSuccess(List<ServiceSubcategory> serviceSubcategories) {
        serviceTitleAdapter.setNewData(serviceSubcategories);
        if (serviceSubcategories != null && !serviceSubcategories.isEmpty()) {
            ServiceSubcategory serviceSubcategory = serviceSubcategories.get(serviceTitlePosition);
            if (TextUtils.isEmpty(serviceText)) {
                serviceText = serviceSubcategory.getServiceText();
            }
            tvServiceContentTitle.setText(serviceText);
            serviceTitleAdapter.setItemChecked(serviceText);
            serviceContentAdapter.setNewData(serviceSubcategory.getServicePoolList());
        }
        laySelectService.setVisibility(View.VISIBLE);
        itemSelectServiceLayoutFor.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        sheetBehaviorUpdate();
    }

    /**
     * 更新 任务数量 和 总价格
     */
    public void updateUi() {
        int totalNumber = ShopCarUtil.getTotalNumber();
        double totalMoney = ShopCarUtil.getTotalMoney();
        tvSelectServiceTaskNumber.setText(getString(R.string.new_task_number, String.valueOf(totalNumber)));
        tvSelectServiceMonty.setText(getString(R.string.content_money, String.valueOf(totalMoney)));
    }

    @Override
    public String getServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

    public String getServiceText() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_TEXT);
        }
        return "";
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected ISelectServiceModule initModule() {
        return new SelectServiceModule();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_select_service;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.select_service);
    }

}