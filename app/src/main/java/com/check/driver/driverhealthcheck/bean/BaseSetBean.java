package com.check.driver.driverhealthcheck.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by shang on 2019/4/1.
 */

public class BaseSetBean extends LitePalSupport {
    private int id;
    private double tem_low;
    private double tem_up;
    private double wet_low;
    private double wet_up;
    private double co_low;
    private double co_up;
    private int heart_low;
    private int heart_up;
    private int bp_H_low;
    private int bp_H_up;
    private int bp_L_low;
    private int bp_L_up;
    private double bf_low;
    private double bf_up;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTem_low() {
        return tem_low;
    }

    public void setTem_low(double tem_low) {
        this.tem_low = tem_low;
    }

    public double getTem_up() {
        return tem_up;
    }

    public void setTem_up(double tem_up) {
        this.tem_up = tem_up;
    }

    public double getWet_low() {
        return wet_low;
    }

    public void setWet_low(double wet_low) {
        this.wet_low = wet_low;
    }

    public double getWet_up() {
        return wet_up;
    }

    public void setWet_up(double wet_up) {
        this.wet_up = wet_up;
    }

    public double getCo_low() {
        return co_low;
    }

    public void setCo_low(double co_low) {
        this.co_low = co_low;
    }

    public double getCo_up() {
        return co_up;
    }

    public void setCo_up(double co_up) {
        this.co_up = co_up;
    }

    public int getHeart_low() {
        return heart_low;
    }

    public void setHeart_low(int heart_low) {
        this.heart_low = heart_low;
    }

    public int getHeart_up() {
        return heart_up;
    }

    public void setHeart_up(int heart_up) {
        this.heart_up = heart_up;
    }

    public int getBp_H_low() {
        return bp_H_low;
    }

    public void setBp_H_low(int bp_H_low) {
        this.bp_H_low = bp_H_low;
    }

    public int getBp_H_up() {
        return bp_H_up;
    }

    public void setBp_H_up(int bp_H_up) {
        this.bp_H_up = bp_H_up;
    }

    public int getBp_L_low() {
        return bp_L_low;
    }

    public void setBp_L_low(int bp_L_low) {
        this.bp_L_low = bp_L_low;
    }

    public int getBp_L_up() {
        return bp_L_up;
    }

    public void setBp_L_up(int bp_L_up) {
        this.bp_L_up = bp_L_up;
    }

    public double getBf_low() {
        return bf_low;
    }

    public void setBf_low(double bf_low) {
        this.bf_low = bf_low;
    }

    public double getBf_up() {
        return bf_up;
    }

    public void setBf_up(double bf_up) {
        this.bf_up = bf_up;
    }

    public void setFirst() {
        tem_up = 30;
        tem_low = 20;
        wet_up = 30;
        wet_low = 20;
        co_up = 30;
        co_low = 0;
        heart_low = 80;
        heart_up = 160;
        bp_H_low = 120;
        bp_H_up = 180;
        bp_L_low = 80;
        bp_L_up = 120;
        bf_low = 0;
        bf_up = 1;
        time = 5;
    }
}
