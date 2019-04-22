package com.check.driver.driverhealthcheck.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;

import java.util.Calendar;

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
    }

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
        tvBloodFat = findViewById(R.id.tv_blood_fat);
        ivBack=findViewById(R.id.iv_back);
        tvDateSelect = findViewById(R.id.tv_date_select);
        Calendar ca = Calendar.getInstance();
        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH) + 1;
        day = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_date_select:
                if (dialog == null) {
                    dialog = new DatePickerDialog(this, 0, listener, year, month, day);
                }
                dialog.show();
                break;
        }
    }
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            tvDateSelect.setText(year + "/" + (++month) + "/" + dayOfMonth);
        }

    };
}
