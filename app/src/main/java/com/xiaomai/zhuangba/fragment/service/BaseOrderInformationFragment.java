package com.xiaomai.zhuangba.fragment.service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MultiGraphSelectionAdapter;
import com.xiaomai.zhuangba.data.bean.District;
import com.xiaomai.zhuangba.data.module.orderinformation.IOrderInformationModule;
import com.xiaomai.zhuangba.data.module.orderinformation.IOrderInformationView;
import com.xiaomai.zhuangba.data.module.orderinformation.OrderInformationModule;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.camera.global.Constant;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 * <p>
 * 添加详细信息 或修改信息
 */
public class BaseOrderInformationFragment extends BaseFragment<IOrderInformationModule>
        implements IOrderInformationView, MultiGraphSelectionAdapter.OnMultiGraphClickListener {

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

    @BindView(R.id.recyclerNotes)
    RecyclerView recyclerNotes;
    @BindView(R.id.editInstallationNotes)
    EditText editInstallationNotes;

    /**
     * 拍照图片保存
     */
    public List<String> mediaSelectorFiles = new ArrayList<>();
    private MultiGraphSelectionAdapter multiGraphSelectionAdapter;
    private Uri imageUriFromCamera;
    public Uri resultUri = null;

    private Date selectionDate;
    private Double longitude;
    private Double latitude;
    private String time;

    private List<District> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public String province;
    public String city;
    public String area;

    public static BaseOrderInformationFragment newInstance() {
        Bundle args = new Bundle();
        BaseOrderInformationFragment fragment = new BaseOrderInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        Util.setEditTextInhibitInputSpaChat(editOrderInformationDetailedAddress, 30);
        Util.setEditTextInhibitInputSpaChat(editOrderInformationName, 8);

        mediaSelectorFiles.add(null);
        recyclerNotes.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        multiGraphSelectionAdapter = new MultiGraphSelectionAdapter(getActivity(), mediaSelectorFiles);
        recyclerNotes.setAdapter(multiGraphSelectionAdapter);
        multiGraphSelectionAdapter.setOnMultiGraphClickListener(this);
        recyclerNotes.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));


        Util.parseData(getActivity(), options1Items, options2Items, options3Items);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_order_information;
    }

    @OnClick({R.id.tvOrderInformationClickServiceAddress, R.id.ivOrderInformationLocation
            , R.id.btnOrderInformation, R.id.relOrderInformationTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvOrderInformationClickServiceAddress:
                hideKeyboard(view);
                showPickerView();
                break;
            case R.id.ivOrderInformationLocation:
                hideKeyboard(view);
                showPickerView();
                break;
            case R.id.btnOrderInformation:
                //提交 或 修改信息
                btnOrderInformationClick();
                break;
            case R.id.relOrderInformationTime:
                //点击选择预约时间
                timePickerView();
                break;
            default:
        }
    }

    @Override
    public void addImg() {
        //权限
        RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
            @Override
            public void onSuccess(String obj) {
                //拍照
                imageUriFromCamera = PhotoTool.createImagePathUri(getActivity());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
                startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
            }

            @Override
            public void onFail(Object obj) {
                showToast(getString(R.string.please_open_permissions));
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    resultUri = Uri.parse("file:///" + PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera));
                    //压缩图片
                    Luban.with(getActivity())
                            .load(resultUri)
                            .ignoreBy(100)
                            .setTargetDir(Constant.DIR_ROOT)
                            .filter(new CompressionPredicate() {
                                @Override
                                public boolean apply(String path) {
                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                }
                            })
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    //压缩开始前调用，可以在方法内启动 loading UI
                                    Log.e("开始压缩 时间 = " + System.currentTimeMillis());
                                }

                                @Override
                                public void onSuccess(File file) {
                                    //压缩成功后调用，返回压缩后的图片文件
                                    Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                                    Log.e("压缩图片地址 = " + file.getPath());
                                    mediaSelectorFiles.add(0, "file:///" +file.getPath());
                                    multiGraphSelectionAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //当压缩过程出现问题时调用
                                    Log.e("压缩失败 error " + e);
                                    mediaSelectorFiles.add(0, "file:///" +resultUri.toString());
                                    multiGraphSelectionAdapter.notifyDataSetChanged();
                                }
                            }).launch();

                }
                break;
            default:
                break;
        }
    }


    private void timePickerView() {
        //开始时间 两小时后的时间
        Calendar startDate = DateUtil.getHours(2, "yyyy-MM-dd HH:mm:ss");
        //结束 一个月内的时间
        Calendar endDate = DateUtil.getMonth(1, "yyyy-MM-dd HH:mm:ss");

        Calendar selectionCalendar = Calendar.getInstance();
        if (selectionDate != null) {
            selectionCalendar.setTime(selectionDate);
        } else {
            selectionCalendar = startDate;
        }

        new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中事件回调
                DateTime dateTime = new DateTime(date);
                selectionDate = date;
                int hour = dateTime.getHourOfDay();
                int year = dateTime.getYear();
                int day = dateTime.getDayOfMonth();
                int month = dateTime.getMonthOfYear();
                time = String.valueOf(year)
                        + "-" + String.valueOf(month)
                        + "-" + String.valueOf(day)
                        + " " + String.valueOf(hour)
                        + ":00" + ":00";
                tvOrderInformationDate.setText(time);
            }
        }).setType(new boolean[]{true, true, true, true, false, false})
                //取消按钮文字
                .setCancelText(getString(R.string.close))
                //确认按钮文字
                .setSubmitText(getString(R.string.ok))
                //滚轮文字大小
                .setContentTextSize(18)
                //标题文字大小
                .setTitleSize(14)
                //标题文字
                .setTitleText(getString(R.string.check_time_date))
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setOutSideCancelable(true)
                //是否循环滚动
                .isCyclic(false)
                .setDate(selectionCalendar)
                //确定按钮文字颜色
                .setSubmitColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                //取消按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                //起始终止年月日设定
                .setRangDate(startDate, endDate)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                //是否显示为对话框样式
                .isDialog(false)
                .build().show();

    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options1Items.size() > 0 ?
                        options1Items.get(options1).getName() : "";

                city = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                area = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = province + city + area;
                tvOrderInformationClickServiceAddress.setText(tx);
            }
        })   .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }


    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected IOrderInformationModule initModule() {
        return new OrderInformationModule();
    }

    public void btnOrderInformationClick() {
    }


    /**
     * 大类编号 修改信息使用
     *
     * @return string
     */
    @Override
    public String getLargeClassServiceId() {
        return null;
    }

    /**
     * 大类名称 修改信息使用
     *
     * @return string
     */
    @Override
    public String getLargeServiceText() {
        return null;
    }

    /**
     * 订单编号 修改信息使用
     *
     * @return string
     */
    @Override
    public String getOrderCode() {
        return null;
    }

    /**
     * 订单状态 修改信息使用
     *
     * @return string
     */
    @Override
    public String getOrderStatus() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return editOrderInformationPhone.getText().toString();
    }

    @Override
    public String getUserName() {
        return editOrderInformationName.getText().toString();
    }

    @Override
    public String getAddress() {
        return tvOrderInformationClickServiceAddress.getText().toString();
    }

    @Override
    public String getAddressDetail() {
        return editOrderInformationDetailedAddress.getText().toString();
    }

    @Override
    public String getAppointmentTime() {
        return time;
    }

    @Override
    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    public String getLatitude() {
        return String.valueOf(latitude);
    }

    @Override
    public void placeOrderSuccess(String requestBodyString) {

    }

    @Override
    public void updateOrderSuccess() {

    }

    @Override
    public List<String> getMediaSelectorFiles() {
        return mediaSelectorFiles;
    }

    @Override
    public String getEmployerDescribe() {
        return editInstallationNotes.getText().toString();
    }

}
