package com.check.driver.driverhealthcheck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;

public class HistoryActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private LineChart chart;

    private RadioGroup radioGroup;
    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        chart = findViewById(R.id.lc_historical_data);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radbtn = (RadioButton) findViewById(checkedId);

        switch (radbtn.getId()){
            case R.id.rb_temp:
                showToast("温度");
                break;
            case R.id.rb_hum:
                showToast("湿度");
                break;
            case R.id.rb_CO:
                showToast("一氧化碳浓度");
                break;
            case R.id.rb_heart_rate:
                showToast("心率");
                break;
            case R.id.rb_blood_pressure:
                showToast("血压");
                break;
            case R.id.rb_blood_fat:
                showToast("血脂");
                break;

        }
    }
}
