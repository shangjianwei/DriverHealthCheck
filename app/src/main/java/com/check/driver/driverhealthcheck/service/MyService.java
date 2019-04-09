package com.check.driver.driverhealthcheck.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.bean.BaseSetBean;
import com.check.driver.driverhealthcheck.bean.CarOnBean;
import com.check.driver.driverhealthcheck.bean.MessageBean;
import com.check.driver.driverhealthcheck.utils.SPUtils;

import java.util.Random;

//开启服务
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static final String type = "TYPE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = super.onStartCommand(intent, flags, startId);
        int t = intent.getIntExtra(type, -1);
        if (t == 1) {
            //启动
            initOn();
        } else if (t == 2) {
            //结束
            initOFF();
        }
        return i;

    }

    private void initOFF() {
        int isOn = SPUtils.getInt(this, SPUtils.IS_ON, 0);
        if (isOn == 0) {
            //表明汽车已关闭
            isOnCar = false;
            return;
        }
        isOnCar = false;
        SPUtils.putInt(this, SPUtils.IS_ON, 0);
        long time = SPUtils.getLong(this, SPUtils.BEGIN_TIME, 0);
        CarOnBean carOnBean = new CarOnBean();
        carOnBean.setOnTime(time);
        carOnBean.setEndTime(System.currentTimeMillis());
    }

    private boolean isOnCar;

    private void initOn() {
        int isOn = SPUtils.getInt(this, SPUtils.IS_ON, 0);
        if (isOn == 1) {
            //表明汽车已开启
            return;
        }
        //记录状态为开启
        SPUtils.putInt(this, SPUtils.IS_ON, 1);
        //标记开始时间
        SPUtils.putLong(this, SPUtils.BEGIN_TIME, System.currentTimeMillis());
        //启动汽车,需要把当前时间存储起来
        isOnCar = true;
        handler.sendEmptyMessage(1);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isOnCar) {
                        randomNewMsg();
                        long time = BaseMessageInit.INSTENCE.getBaseSetBean().getTime();
                        handler.sendEmptyMessageDelayed(1, time * 1000);
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * 生成数据
     */
    private void randomNewMsg() {
        Random random = new Random();
        MessageBean messageBean = new MessageBean();
        BaseSetBean baseSetBean = BaseMessageInit.INSTENCE.getBaseSetBean();
        messageBean.setTime(System.currentTimeMillis());
        {
            double ran = random.nextDouble();
            double low = baseSetBean.getTem_low();
            double up = baseSetBean.getTem_up();
            double end = (up - low) * ran + low;
            messageBean.setTem(end);
        }
        {
            double ran = random.nextDouble();
            double low = baseSetBean.getWet_low();
            double up = baseSetBean.getWet_up();
            double end = (up - low) * ran + low;
            messageBean.setWet(end);
        }
        {
            double ran = random.nextDouble();
            double low = baseSetBean.getCo_low();
            double up = baseSetBean.getCo_up();
            double end = (up - low) * ran + low;
            messageBean.setCo(end);
        }
        {
            double ran = random.nextDouble();
            int low = baseSetBean.getHeart_low();
            int up = baseSetBean.getHeart_up();
            int end = (int) ((up - low) * ran + low);
            messageBean.setHeart(end);
        }
        {
            double ran = random.nextDouble();
            int low = baseSetBean.getBp_H_low();
            int up = baseSetBean.getBp_H_up();
            int end = (int) ((up - low) * ran + low);
            messageBean.setBp_H(end);
        }
        {
            double ran = random.nextDouble();
            int low = baseSetBean.getBp_L_low();
            int up = baseSetBean.getBp_L_up();
            int end = (int) ((up - low) * ran + low);
            messageBean.setBp_L(end);
        }
        {
            double ran = random.nextDouble();
            double low = baseSetBean.getBf_low();
            double up = baseSetBean.getBf_up();
            double end = ((up - low) * ran + low);
            messageBean.setBf(end);
        }
        messageBean.save();
        BaseMessageInit.INSTENCE.setMessageBean(messageBean);

    }
}
