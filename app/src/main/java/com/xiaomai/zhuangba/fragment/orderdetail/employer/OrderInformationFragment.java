package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.service.BaseOrderInformationFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class OrderInformationFragment extends BaseOrderInformationFragment {

    @BindView(R.id.editOrderInformationName)
    EditText editOrderInformationName;
    @BindView(R.id.editOrderInformationPhone)
    EditText editOrderInformationPhone;
    @BindView(R.id.editOrderInformationDetailedAddress)
    EditText editOrderInformationDetailedAddress;
    @BindView(R.id.btnOrderInformation)
    Button btnOrderInformation;
    @BindView(R.id.relOrderInformationTime)
    RelativeLayout relOrderInformationTime;
    @BindView(R.id.tvOrderInformationClickServiceAddress)
    TextView tvOrderInformationClickServiceAddress;
    @BindView(R.id.tvOrderInformationDate)
    TextView tvOrderInformationDate;
    @BindView(R.id.tvOrderInformationInstallationNotes)
    TextView tvOrderInformationInstallationNotes;
    @BindView(R.id.editInstallationNotes)
    EditText editInstallationNotes;
    @BindView(R.id.recyclerNotes)
    RecyclerView recyclerNotes;
    @BindView(R.id.editContractNumber)
    EditText editContractNumber;
    @BindView(R.id.editAccountManager)
    EditText editAccountManager;
    @BindView(R.id.editEntryName)
    EditText editEntryName;
    @BindView(R.id.editProjectCharacteristics)
    EditText editProjectCharacteristics;
    @BindView(R.id.editShopName)
    EditText editShopName;
    @BindView(R.id.editOrderCode)
    EditText editOrderCode;

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ONGOING_ORDERS_LIST = "ongoing_orders_list";
    public static OrderInformationFragment newInstance(String orderCode, String name, String phone , String ongoingOrdersList) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(NAME, name);
        args.putString(PHONE, phone);
        args.putString(ONGOING_ORDERS_LIST , ongoingOrdersList);
        OrderInformationFragment fragment = new OrderInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        editOrderInformationName.setText(getName());
        editOrderInformationPhone.setText(getPhone());

        tvOrderInformationInstallationNotes.setVisibility(View.GONE);
        editInstallationNotes.setVisibility(View.GONE);
        recyclerNotes.setVisibility(View.GONE);

        OngoingOrdersList ongoingOrdersList = getOngoingOrdersList();
        editContractNumber.setText(ongoingOrdersList.getContractNo());
        editAccountManager.setText(ongoingOrdersList.getAccountManager());
        editEntryName.setText(ongoingOrdersList.getProjectName());
        editProjectCharacteristics.setText(ongoingOrdersList.getProjectFeatures());
        editShopName.setText(ongoingOrdersList.getShopName());
        editOrderCode.setText(ongoingOrdersList.getOrderNumber());
    }

    @Override
    public void btnOrderInformationClick() {
        iModule.requestUpdateOrder();
    }

    @Override
    public void updateOrderSuccess() {
        //修改成功
        startFragment(EmployerFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_information;
    }

    @Override
    public String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.update_information);
    }

    private String getName() {
        if (getArguments() != null) {
            return getArguments().getString(NAME);
        }
        return "";
    }

    private OngoingOrdersList getOngoingOrdersList(){
        if (getArguments() != null){
            return new Gson().fromJson(getArguments().getString(ONGOING_ORDERS_LIST) , OngoingOrdersList.class);
        }
        return new OngoingOrdersList();
    }

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

}
