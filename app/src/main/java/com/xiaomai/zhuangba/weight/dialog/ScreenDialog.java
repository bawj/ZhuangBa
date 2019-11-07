package com.xiaomai.zhuangba.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class ScreenDialog {

    private Dialog dialog;
    private View inflate;

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
        //获取当前Activity所在的窗体
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.END;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        dialog.show();
    }

    /** 关闭dialog时调用 */
    public void close() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
