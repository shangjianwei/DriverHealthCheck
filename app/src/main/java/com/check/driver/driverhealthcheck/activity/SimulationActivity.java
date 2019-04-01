package com.check.driver.driverhealthcheck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.bean.BaseSetBean;

public class SimulationActivity extends BaseActivity {

    private EditText tem_low;
    private EditText tem_up;
    private EditText wet_low;
    private EditText wet_up;
    private EditText co_low;
    private EditText co_up;
    private EditText heart_low;
    private EditText heart_up;
    private EditText bp_L_low;
    private EditText bp_L_up;
    private EditText bp_low;
    private EditText bp_up;
    private EditText bf_low;
    private EditText bf_up;
    private EditText time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        initView();
    }

    private void initView() {
        tem_low = findViewById(R.id.tem_low);
        tem_up = findViewById(R.id.tem_up);
        wet_low = findViewById(R.id.wet_low);
        wet_up = findViewById(R.id.wet_up);
        co_low = findViewById(R.id.co_low);
        co_up = findViewById(R.id.co_up);
        heart_low = findViewById(R.id.heart_low);
        heart_up = findViewById(R.id.heart_up);
        bp_low = findViewById(R.id.bp_low);
        bp_up = findViewById(R.id.bp_up);
        bf_low = findViewById(R.id.bf_low);
        bf_up = findViewById(R.id.bf_up);
        time = findViewById(R.id.time);
        bp_L_low = findViewById(R.id.bp_L_low);
        bp_L_up = findViewById(R.id.bp_L_up);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseSetBean baseSetBean = BaseMessageInit.INSTENCE.getBaseSetBean();
        //将数据展示出来
        tem_low.setText(baseSetBean.getTem_low() + "");
        tem_up.setText(baseSetBean.getTem_up() + "");
        wet_low.setText(baseSetBean.getWet_low() + "");
        wet_up.setText(baseSetBean.getWet_up() + "");
        co_low.setText(baseSetBean.getCo_low() + "");
        co_up.setText(baseSetBean.getCo_up() + "");
        heart_low.setText(baseSetBean.getHeart_low() + "");
        heart_up.setText(baseSetBean.getHeart_up() + "");
        bp_low.setText(baseSetBean.getBp_H_low() + "");
        bp_up.setText(baseSetBean.getBp_H_up() + "");
        bf_low.setText(baseSetBean.getBf_low() + "");
        bf_up.setText(baseSetBean.getBf_up() + "");
        time.setText(baseSetBean.getTime() + "");
        bp_L_low.setText(baseSetBean.getBp_L_low() + "");
        bp_L_up.setText(baseSetBean.getBp_L_up() + "");
    }

    public void onDoneClick(View view) {
        //这里将数据从输入框中取出并保存到数据库中，并且修改BaseMessageInit
        BaseSetBean baseSetBean = BaseMessageInit.INSTENCE.getBaseSetBean();
        //修改参数
        baseSetBean.setTem_low(Double.parseDouble(tem_low.getText().toString()));
        baseSetBean.setTem_up(Double.parseDouble(tem_up.getText().toString()));
        baseSetBean.setWet_low(Double.parseDouble(wet_low.getText().toString()));
        baseSetBean.setWet_up(Double.parseDouble(wet_up.getText().toString()));
        baseSetBean.setCo_low(Double.parseDouble(co_low.getText().toString()));
        baseSetBean.setCo_up(Double.parseDouble(co_up.getText().toString()));
        baseSetBean.setHeart_low(Integer.parseInt(heart_low.getText().toString()));
        baseSetBean.setHeart_up(Integer.parseInt(heart_up.getText().toString()));
        baseSetBean.setBp_L_low(Integer.parseInt(bp_L_low.getText().toString()));
        baseSetBean.setBp_L_up(Integer.parseInt(bp_L_up.getText().toString()));
        baseSetBean.setBp_H_low(Integer.parseInt(bp_low.getText().toString()));
        baseSetBean.setBp_H_up(Integer.parseInt(bp_up.getText().toString()));
        baseSetBean.setBf_low(Double.parseDouble(bf_low.getText().toString()));
        baseSetBean.setBf_up(Double.parseDouble(bf_up.getText().toString()));
        baseSetBean.setTime(Long.parseLong(time.getText().toString()));
        //保存到数据库
        baseSetBean.update(baseSetBean.getId());
        this.finish();
    }

}
