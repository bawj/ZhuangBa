package com.xiaomai.zhuangba.weight.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.toollib.util.DensityUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.download.DownLoadUtil;

import java.io.File;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class UpdateVersionDialog {

    private AlertDialog mAlertDialog;
    public static UpdateVersionDialog getInstance() {
        return new UpdateVersionDialog();
    }

    @SuppressLint("ClickableViewAccessibility")
    public UpdateVersionDialog initView(final Context mContext, final String downLoadUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialog);
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_vesion, null);
        final SeekBar seekBar = dialogView.findViewById(R.id.seekBar);
        final TextView tvSeek = dialogView.findViewById(R.id.tvSeek);
        final TextView tvDownLoadTip = dialogView.findViewById(R.id.tvDownLoadTip);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                //设置文本显示
                tvSeek.setText((String.valueOf(progress) + "%"));
                //获取文本宽度
                float textWidth = tvSeek.getWidth();
                //获取seekbar最左端的x位置
                float left = seekBar.getLeft();
                //进度条的刻度值
                float max = Math.abs(seekBar.getMax());
                float thumb = DensityUtil.dp2px(15.0F);
                float average = (((float) seekBar.getWidth()) - 2 * thumb) / max;
                float pox = left - textWidth / 2 + thumb + average * progress;
                tvSeek.setX(pox);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        DownLoadUtil.downLoad(mContext, downLoadUrl, new DownLoadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDownLoadTip.setText(mContext.getString(R.string.down_load_complete));
                    }
                });
            }

            @Override
            public void onDownloading(int progress) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
            }
        });
        mAlertDialog.setContentView(dialogView);
        return this;
    }
    public void showDialog(Context mContext) {
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            //解决无法弹出输入法的问题
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
        //计算屏幕宽高
        int width;
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            width = dm.widthPixels;
            height = dm.heightPixels;
        } else {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            width = metrics.widthPixels;
            height = metrics.heightPixels;
        }
        if (window != null) {
            window.setGravity(Gravity.TOP);
            window.setLayout(width, height);
            WindowManager.LayoutParams lp = window.getAttributes();
            ((Activity)mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                    WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            window.setAttributes(lp);
            lp.alpha = 0.5f;
            window.setAttributes(lp);
        }
    }
}
