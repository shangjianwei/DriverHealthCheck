package com.check.driver.driverhealthcheck.base;

import com.check.driver.driverhealthcheck.bean.BaseSetBean;
import com.check.driver.driverhealthcheck.bean.CarOnBean;
import com.check.driver.driverhealthcheck.bean.MessageBean;

/**
 * Created by shang on 2019/4/1.
 */

public enum BaseMessageInit {
    INSTENCE;
    private BaseSetBean baseSetBean;
    private CarOnBean carOnBean;
    private MessageBean messageBean;

    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public CarOnBean getCarOnBean() {
        return carOnBean;
    }

    public void setCarOnBean(CarOnBean carOnBean) {
        this.carOnBean = carOnBean;
    }

    public BaseSetBean getBaseSetBean() {
        return baseSetBean;
    }

    public void setBaseSetBean(BaseSetBean baseSetBean) {
        this.baseSetBean = baseSetBean;
    }
}
