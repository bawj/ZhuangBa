package com.xiaomai.zhuangba.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.QDListSectionAdapter;
import com.xiaomai.zhuangba.data.SectionItem;
import com.xiaomai.zhuangba.data.bean.SearchCondition;
import com.xiaomai.zhuangba.data.bean.SectionHeader;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class ScreenDialog implements View.OnClickListener {

    private Context mContext;
    private Dialog dialog;
    private View inflate;

    private QDListSectionAdapter mAdapter;
    private SearchCondition mSearchCondition;

    /**
     * 公司
     */
    private final int company = 0;
    /**
     * 广告
     */
    private final int advertising = 1;
    /**
     * 尺寸
     */
    private final int size = 2;

    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * 重置
     */
    private TextView tvSearchRest;
    /**
     * 完成
     */
    private TextView tvSearchComplete;

    public static ScreenDialog getInstance() {
        return new ScreenDialog();
    }


    public void showRightDialog(Context context) {
        //自定义dialog显示布局
        inflate = LayoutInflater.from(context).inflate(R.layout.popup_master_screen, null);
        //自定义dialog显示风格
        dialog = new Dialog(context, R.style.DialogRight);
        //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
        dialog.setCanceledOnTouchOutside(true);
        //将布局设置给Dialog
        dialog.setContentView(inflate);

        initView(inflate);

        //获取当前Activity所在的窗体
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.END;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        dialog.show();
    }



    private void initView(View inflate) {
        QMUIStickySectionLayout mSectionLayout = inflate.findViewById(R.id.sectionLayout);
        mLayoutManager = createLayoutManager();
        mSectionLayout.setLayoutManager(mLayoutManager);
        mAdapter = createAdapter();
        mAdapter.setCallback(new QMUIStickySectionAdapter.Callback<SectionHeader, SectionItem>() {
            @Override
            public void loadMore(QMUISection<SectionHeader, SectionItem> section, boolean loadMoreBefore) {

            }

            @Override
            public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                onItemClickUpdate(position);
            }

            @Override
            public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                return false;
            }
        });
        mSectionLayout.setAdapter(mAdapter, true);
        ArrayList<QMUISection<SectionHeader, SectionItem>> list = analysisData();
        mAdapter.setData(list);
        //重置
        tvSearchRest = inflate.findViewById(R.id.tvSearchRest);
        tvSearchRest.setOnClickListener(this);
        //完成
        tvSearchComplete = inflate.findViewById(R.id.tvSearchComplete);
        tvSearchComplete.setOnClickListener(this);
        int num = mSearchCondition.getNum();
        tvSearchComplete.setText(mContext.getString(R.string.search_complete , String.valueOf(num)));
    }

    private void onItemClickUpdate(int position) {
        int sectionIndex = mAdapter.getSectionIndex(position);
        int itemIndex = mAdapter.getItemIndex(position);
        if (itemIndex >= 0) {
            Log.e("第 " + sectionIndex + "组" + "-- 第" + itemIndex + "个");
            mAdapter.setSelect(sectionIndex, itemIndex);
            //公司
            List<String> teamList = new ArrayList<>();
            //尺寸
            List<String> equipmentList = new ArrayList<>();
            //广告
            List<String> batchCodeList = new ArrayList<>();
            //刷新 界面  传入选中的数据
            switch (sectionIndex){
                case company:
                    //公司
                    teamList = mSearchCondition.getTeamList().get(itemIndex).getList();
                    break;
                case advertising:
                    //广告
                    batchCodeList = mSearchCondition.getBatchCodeList().get(itemIndex).getList();
                    break;
                case size:
                    //尺寸
                    equipmentList = mSearchCondition.getEquipmentList().get(itemIndex).getList();
                    break;
                default:
            }
            requesetSearch(teamList , equipmentList , batchCodeList);
        }
    }

    @NonNull
    private ArrayList<QMUISection<SectionHeader, SectionItem>> analysisData() {
        ArrayList<QMUISection<SectionHeader, SectionItem>> list = new ArrayList<>();
        //选择公司
        list.add(createSection(mContext.getString(R.string.select_company), company));
        //选择广告
        list.add(createSection(mContext.getString(R.string.selective_advertising), advertising));
        //选择尺寸
        list.add(createSection(mContext.getString(R.string.selective_size), size));
        return list;
    }

    private QMUISection<SectionHeader, SectionItem> createSection(String headerText, int type) {
        SectionHeader header = new SectionHeader(headerText);
        ArrayList<SectionItem> contents = new ArrayList<>();
        switch (type) {
            case company:
                //公司
                List<SearchCondition.TeamListBean> teamList = mSearchCondition.getTeamList();
                for (SearchCondition.TeamListBean teamListBean : teamList) {
                    contents.add(new SectionItem(teamListBean.getText()));
                }
                break;
            case advertising:
                //广告
                List<SearchCondition.BatchCodeListBean> batchCodeList = mSearchCondition.getBatchCodeList();
                for (SearchCondition.BatchCodeListBean batchCodeListBean : batchCodeList) {
                    contents.add(new SectionItem(batchCodeListBean.getText()));
                }
                break;
            case size:
                //尺寸
                List<SearchCondition.EquipmentListBean> equipmentList = mSearchCondition.getEquipmentList();
                for (SearchCondition.EquipmentListBean equipmentListBean : equipmentList) {
                    contents.add(new SectionItem(equipmentListBean.getText()));
                }
                break;
            default:
        }
        QMUISection<SectionHeader, SectionItem> section = new QMUISection<>(header, contents, false);
        section.setExistAfterDataToLoad(false);
        return section;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSearchRest:
                //重置
                break;
            case R.id.tvSearchComplete:
                //完成
                break;
            default:
        }
    }

    private RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
    }

    private QDListSectionAdapter createAdapter() {
        return new QDListSectionAdapter();
    }

    public Context getContext() {
        return mContext;
    }

    public ScreenDialog setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public ScreenDialog setSearchConditionContent(SearchCondition searchCondition) {
        this.mSearchCondition = searchCondition;
        return this;
    }
    private void requesetSearch(List<String> teamList , List<String> equipmentList , List<String> batchCodeList){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "1");
        hashMap.put("teamList", teamList);
        hashMap.put("equipmentList", equipmentList);
        hashMap.put("batchCodeList", batchCodeList);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getAllSearchCondition(requestBody))
                .subscribe(new BaseHttpRxObserver<SearchCondition>(mContext) {
                    @Override
                    protected void onSuccess(SearchCondition searchCondition) {
                        mSearchCondition = searchCondition;
                        ArrayList<QMUISection<SectionHeader, SectionItem>> list = analysisData();
                        mAdapter.setData(list);
                        int num = mSearchCondition.getNum();
                        tvSearchComplete.setText(mContext.getString(R.string.search_complete , String.valueOf(num)));
                    }
                });
    }

    /**
     * 关闭dialog时调用
     */
    public void close() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
