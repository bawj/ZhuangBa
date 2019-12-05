package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.xiaomai.zhuangba.R;

public class MediaPlayerUtil {

    private volatile static MediaPlayer mediaPlayer;
    private volatile static MediaPlayerUtil mediaPlayerUtil;


    private MediaPlayerUtil() {
        mediaPlayer = new MediaPlayer();
    }

    public static MediaPlayerUtil getMediaPlayerUtil() {
        synchronized (MediaPlayerUtil.class) {
            if (mediaPlayerUtil == null) {
                mediaPlayerUtil = new MediaPlayerUtil();
            }
            return mediaPlayerUtil;
        }
    }

    public void switchPlayer(Context context) {
        cancel();
        playMediaWashWelcome(context);
    }

    private void playMediaWashWelcome(Context context) {
        cancel();
        mediaPlayer = MediaPlayer.create(context, R.raw.zhuangbashifu);
        mediaPlayer.start();
    }


    public void cancel() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
