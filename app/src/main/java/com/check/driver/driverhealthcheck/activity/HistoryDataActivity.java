package com.check.driver.driverhealthcheck.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.bean.CarOnBean;
import com.check.driver.driverhealthcheck.bean.MessageBean;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvDriveTime;  //驾驶时间
    private TextView tvTemperature;  //车内温度
    private TextView tvHumidity;  //车内湿度
    private TextView tvCOConcentration; //一氧化碳浓度
    private TextView tvHeartRate;   //心率
    private TextView tvBloodPressure;  //血压
    private TextView tvBloodFat;   //血脂
    private ImageView ivBack;
    private TextView tvDateSelect;
    private Calendar calendar;// 用来装日期的
    private int year, month, day;
    private DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        initView();
        initListener();
        init();
    }

    private void init() {
        Calendar ca = Calendar.getInstance();
        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH) + 1;
        day = ca.get(Calendar.DAY_OF_MONTH);
        tvDateSelect.setText(year + "/" + month + "/" + day);
        getData();
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private void getData() {
        try {
            long begin = format.parse(year + "-" + month + "-" + day + " 00:00:00").getTime();
            long end = format.parse(year + "-" + month + "-" + day + " 23:59:59").getTime();
            LitePal.where("time>? and time<?", begin + "", end + "").findAsync(MessageBean.class).listen(listen);
            LitePal.where("endTime>? and endTime<?", begin + "", end + "").findAsync(CarOnBean.class).listen(listen2);
            Log.d(">>>", "1111");
        } catch (ParseException e) {
            Log.d(">>>", "getData: ???");
            e.printStackTrace();
        }

    }

    private FindMultiCallback<CarOnBean> listen2 = new FindMultiCallback<CarOnBean>() {
        @Override
        public void onFinish(List<CarOnBean> list) {
            long time = 0;
            for (int i = 0; i < list.size(); i++) {
                CarOnBean carOnBean = list.get(i);
                time += carOnBean.getEndTime() - carOnBean.getOnTime();
            }
            if (time > 86400000) {
                time = 86400000;
            }
            String times = "";
            time = time / 1000;//秒
            if (time < 60) {
                times = time + "秒";
            } else if (time == 60) {
                times = "1分钟";
            } else if (time < 3600) {
                times = time / 60 + "分" + time % 60 + "秒";
            } else if (time == 3600) {
                times = "1小时";
            } else if (time < 3600 * 24) {
                times = time / 3600 + "小时" + (time % 3600) / 60 + "分" + time % 60 + "秒";
            } else {
                times = "24小时";
            }
            tvDriveTime.setText(times);
        }
    };

    private FindMultiCallback listen = new FindMultiCallback<MessageBean>() {
        @Override
        public void onFinish(List<MessageBean> list) {
            //setData; 温度
            double tem = 0;
            double wet = 0;
            double co = 0;
            double heart = 0;
            double bpH = 0;
            double bpL = 0;
            double bf = 0;
            for (int i = 0; i < list.size(); i++) {
                MessageBean messageBean = list.get(i);
                tem += messageBean.getTem();
                wet += messageBean.getWet();
                co += messageBean.getCo();
                heart += messageBean.getHeart();
                bpH += messageBean.getBp_H();
                bpL += messageBean.getBp_L();
                bf += messageBean.getBf();
            }
            tem = tem / list.size();
            wet = wet / list.size();
            co = co / list.size();
            heart = heart / list.size();
            bpH = bpH / list.size();
            bpL = bpL / list.size();
            bf = bf / list.size();
            tem = (double) Math.round(tem * 100) / 100;
            tem = (double) Math.round(tem * 100) / 100;
            wet = (double) Math.round(wet * 100) / 100;
            co = (double) Math.round(co * 100) / 100;
            heart = (double) Math.round(heart * 100) / 100;
            bpH = (double) Math.round(bpH * 100) / 100;
            bpL = (double) Math.round(bpL * 100) / 100;
            bf = (double) Math.round(bf * 100) / 100;
            tvTemperature.setText(tem + "");
            tvHumidity.setText(wet + ""); //车内湿度
            tvCOConcentration.setText(co + "");//一氧化碳浓度
            tvHeartRate.setText(heart + "");  //心率
            tvBloodPressure.setText(bpH + "/" + bpL); //血压
            tvBloodFat.setText(bf + "");  //血脂
        }
    };

    private void initListener() {
        ivBack.setOnClickListener(this);
        tvDateSelect.setOnClickListener(this);
    }

    private void initView() {
        tvDriveTime = findViewById(R.id.tv_drive_time);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvCOConcentration = findViewById(R.id.tv_CO_concentration);
        tvHeartRate = findViewById(R.id.tv_heart_rate);
        tvBloodPressure = findViewById(R.id.tv_blood_pressure);
        tvBloodFat = findViewById(R.id.tv_blood_fat_note);
        ivBack = findViewById(R.id.iv_back);
        tvDateSelect = findViewById(R.id.tv_date_select);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_date_select:
                if (dialog == null) {
                    dialog = new DatePickerDialog(this, 0, listener, year, month - 1, day);
                }
                dialog.show();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int months, int dayOfMonths) {
            year = years;
            month = months + 1;
            day = dayOfMonths;
            tvDateSelect.setText(year + "/" + month + "/" + day);
            getData();
        }

    };
}
