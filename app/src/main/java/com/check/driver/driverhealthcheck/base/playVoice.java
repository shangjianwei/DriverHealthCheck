package com.check.driver.driverhealthcheck.base;


import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.utils.SPUtils;

public enum playVoice {
    INSTANCE;
    private SoundPool soundPool;

    public static final int CO = 1;
    public static final int DEIVETIME = 2;
    public static final int HEART = 3;
    public static final int TEMP = 4;
    public static final int XUEYA = 5;
    public static final String SP_CO = "1SPSET";
    public static final String SP_DEIVETIME = "2SPSET";
    public static final String SP_HEART = "3SPSET";
    public static final String SP_TEMP = "4SPSET";
    public static final String SP_XUEYA = "5SPSET";
    private int co, drivetime, heart, temp, xueya;
    private static final int TIME_CO = 5000;
    private static final int TIME_DEIVETIME = 5000;
    private static final int TIME_HEART = 4000;
    private static final int TIME_TEMP = 4000;
    private static final int TIME_XUEYA = 4000;
        private static final int WAITTIME = 300000;
//    private static final int WAITTIME = 3000;
    private long needWait = -1;
    private long laskCheck = 0;
    private Handler handler;
    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (soundPool == null) {
                return false;
            }
//            soundPool.play(xueya, 0.5f, 0.5f, 1, 1, 1);
            switch (msg.what) {
                case 1:
                    addItem(msg);
                    break;
                case 2:
                    playItem(msg);
                    break;

            }

            return false;
        }
    };

    private void playItem(Message msg) {
        soundPool.play(msg.arg1, 0.5f, 0.5f, 1, 0, 1);
    }

    private void addItem(Message msg) {

        long now = System.currentTimeMillis();
        long hasgo = now - laskCheck;
        Log.e("??????", needWait + "<>"+hasgo);
        laskCheck = now;
        if (hasgo > needWait) {
            needWait = msg.arg2;
            soundPool.play(msg.arg1, 0.5f, 0.5f, 1, 0, 1);
        } else {
            needWait = needWait - hasgo;
            Message message = Message.obtain();
            message.what = 2;
            message.arg1 = msg.arg1;
            handler.sendMessageDelayed(message, needWait);
            needWait = needWait + msg.arg2;
        }
    }
//    private

    public void create(Context context) {
        if (soundPool != null) {
            return;
        }
        //设置描述音频流信息的属性
        AudioAttributes abs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)   //设置允许同时播放的流的最大值
                .setAudioAttributes(abs)   //完全可以设置为null
                .build();
        co = soundPool.load(context, R.raw.co, 1);
        drivetime = soundPool.load(context, R.raw.drivetime, 1);
        heart = soundPool.load(context, R.raw.heart, 1);
        temp = soundPool.load(context, R.raw.temp, 1);
        xueya = soundPool.load(context, R.raw.xueya, 1);
        handler = new Handler(callback);
        laskCheck = System.currentTimeMillis();
    }

    public void play(int p, Context context) {
        switch (p) {
            case CO: {
                long lastTime = SPUtils.getLong(context, SP_CO, 0);
                if (System.currentTimeMillis() - lastTime > WAITTIME) {
                    SPUtils.putLong(context, SP_CO, System.currentTimeMillis());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = p;
                    message.arg2 = TIME_CO;
                    handler.sendMessage(message);
                }
            }
            break;
            case DEIVETIME: {
                long lastTime = SPUtils.getLong(context, SP_DEIVETIME, 0);
                if (System.currentTimeMillis() - lastTime > WAITTIME) {
                    SPUtils.putLong(context, SP_DEIVETIME, System.currentTimeMillis());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = p;
                    message.arg2 = TIME_DEIVETIME;
                    handler.sendMessage(message);
                }

            }

            break;
            case HEART: {
                long lastTime = SPUtils.getLong(context, SP_HEART, 0);
                if (System.currentTimeMillis() - lastTime > WAITTIME) {
                    SPUtils.putLong(context, SP_HEART, System.currentTimeMillis());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = p;
                    message.arg2 = TIME_HEART;
                    handler.sendMessage(message);

                }

            }

            break;
            case TEMP: {
                long lastTime = SPUtils.getLong(context, SP_TEMP, 0);
                if (System.currentTimeMillis() - lastTime > WAITTIME) {
                    SPUtils.putLong(context, SP_TEMP, System.currentTimeMillis());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = p;
                    message.arg2 = TIME_TEMP;
                    handler.sendMessage(message);
                }

            }

            break;
            case XUEYA: {
                long lastTime = SPUtils.getLong(context, SP_XUEYA, 0);
                if (System.currentTimeMillis() - lastTime > WAITTIME) {
                    SPUtils.putLong(context, SP_XUEYA, System.currentTimeMillis());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = p;
                    message.arg2 = TIME_XUEYA;
                    handler.sendMessage(message);

                }

            }

            break;
        }

    }

    public void releaseSoundPool() {
        if (soundPool != null) {
            soundPool.autoPause();
            soundPool.unload(co);
            soundPool.unload(drivetime);
            soundPool.unload(heart);
            soundPool.unload(temp);
            soundPool.unload(xueya);
            soundPool.release();
            soundPool = null;
        }
    }
}
