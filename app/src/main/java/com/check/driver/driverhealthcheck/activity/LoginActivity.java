package com.check.driver.driverhealthcheck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etPass;
    private Button btnRegister; //注册
    private Button btnLogin;    //登录




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etPhone=findViewById(R.id.et_phone);
        etPass=findViewById(R.id.et_pass);
        btnRegister=findViewById(R.id.btn_register);
        btnLogin=findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                showToast("登录成功，跳转到mainactivity");
                break;
            case R.id.btn_register:
                showToast("注册成功");
                break;
        }

    }
}
