package com.check.driver.driverhealthcheck.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by shang on 2019/4/1.
 */

public class MessageBean extends LitePalSupport {
    private int id;
    private double tem;
    private double wet;
    private double co;
    private int heart;
    private int bp_H;
    private int bp_L;
    private double bf;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTem() {
        return tem;
    }

    public void setTem(double tem) {
        this.tem = tem;
    }

    public double getWet() {
        return wet;
    }

    public void setWet(double wet) {
        this.wet = wet;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getBp_H() {
        return bp_H;
    }

    public void setBp_H(int bp_H) {
        this.bp_H = bp_H;
    }

    public int getBp_L() {
        return bp_L;
    }

    public void setBp_L(int bp_L) {
        this.bp_L = bp_L;
    }

    public double getBf() {
        return bf;
    }

    public void setBf(double bf) {
        this.bf = bf;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
