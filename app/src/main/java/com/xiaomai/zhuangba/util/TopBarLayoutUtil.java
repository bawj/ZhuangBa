package com.xiaomai.zhuangba.util;

import android.graphics.Color;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 */
public class TopBarLayoutUtil {

    /**
     * 设置返回
     * @param topBarLayout topBar
     * @param fragmentActivity activity
     */
    public static void addLeftImageBlackButton(QMUITopBarLayout topBarLayout ,final QMUIFragmentActivity fragmentActivity){
        topBarLayout.addLeftImageButton(com.example.toollib.R.drawable.tool_lib_ic_left_back,
                com.example.toollib.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentActivity.popBackStack();
                    }
                });
    }

    public static void setTopBarTitle(QMUITopBarLayout topBarLayout , String title){
        topBarLayout.setTitle(title).setTextColor(Color.BLACK);
    }

}
