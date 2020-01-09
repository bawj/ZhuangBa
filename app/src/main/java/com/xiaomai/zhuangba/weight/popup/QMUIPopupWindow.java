package com.xiaomai.zhuangba.weight.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.util.DensityUtil;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.xiaomai.zhuangba.R;

/**
 * @author Bawj
 * CreateDate: 2020/1/4 0004 11:01
 */
public class QMUIPopupWindow implements View.OnClickListener {

    private QMUIPopup qmuiPopup;
    private IQMUIPopupCallBack iqmuiPopupCallBack;

    public static QMUIPopupWindow newInstance() {
        return new QMUIPopupWindow();
    }

    public QMUIPopup initPopup(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_site_replenishment, null);
        qmuiPopup = new QMUIPopup(mContext, QMUIPopup.DIRECTION_TOP);
        qmuiPopup.setPreferredDirection(QMUIPopup.DIRECTION_BOTTOM);
        qmuiPopup.setPositionOffsetYWhenBottom(DensityUtil.dip2px(mContext, -12));
        qmuiPopup.setContentView(view);
        qmuiPopup.generateLayoutParam(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 107));
        qmuiPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);

        TextView tvAddProject = view.findViewById(R.id.tvAddProject);
        TextView tvDeletionItem = view.findViewById(R.id.tvDeletionItem);
        TextView tvCustom = view.findViewById(R.id.tvCustom);

        tvAddProject.setOnClickListener(this);
        tvDeletionItem.setOnClickListener(this);
        tvCustom.setOnClickListener(this);
        return qmuiPopup;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddProject:
                if (iqmuiPopupCallBack != null){
                    iqmuiPopupCallBack.addProject();
                }
                dismiss();
                break;
            case R.id.tvDeletionItem:
                if (iqmuiPopupCallBack != null){
                    iqmuiPopupCallBack.deletionItem();
                }
                dismiss();
                break;
            case R.id.tvCustom:
                if (iqmuiPopupCallBack != null){
                    iqmuiPopupCallBack.custom();
                }
                dismiss();
                break;
            default:
        }
    }

    public void setIQMUIPopupCallBack(IQMUIPopupCallBack iqmuiPopupCallBack) {
        this.iqmuiPopupCallBack = iqmuiPopupCallBack;
    }

    private void dismiss(){
        if (qmuiPopup != null){
            qmuiPopup.dismiss();
        }
    }


    public interface IQMUIPopupCallBack {
        /**
         * 新增项目
         */
        void addProject();

        /**
         * 删减项目
         */
        void deletionItem();

        /**
         * 自定义
         */
        void custom();
    }


}
