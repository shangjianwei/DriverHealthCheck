package com.check.driver.driverhealthcheck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etPhone;
    private EditText etPass;
    private EditText etPass2;
    private ImageView ivBack;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etName=findViewById(R.id.et_name);
        etPhone=findViewById(R.id.et_phone);
        etPass=findViewById(R.id.et_pass);
        etPass2=findViewById(R.id.et_pass2);
        ivBack=findViewById(R.id.iv_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_register:
                //注册成功，跳到登录界面
                break;
        }

    }
}
