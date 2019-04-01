package com.check.driver.driverhealthcheck.bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * Created by shang on 2019/4/1.
 */

public class CarOnBean extends LitePalSupport {
    private int id;
    private long onTime;
    private long endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOnTime() {
        return onTime;
    }

    public void setOnTime(long onTime) {
        this.onTime = onTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
