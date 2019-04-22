package com.check.driver.driverhealthcheck.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.check.driver.driverhealthcheck.MainActivity;
import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.ActivityManager;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.bean.UserBean;
import com.check.driver.driverhealthcheck.service.MyService;
import com.check.driver.driverhealthcheck.utils.SPUtils;

import org.litepal.LitePal;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etPass;
    private Button btnRegister; //注册
    private Button btnLogin;    //登录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clearActivity();
        initCheck();
        initView();
        initListener();
    }
    private void clearActivity() {
        ActivityManager.INSTANCE.clearOtherActivity(this);
    }

    private void initCheck() {
        int isOn = SPUtils.getInt(this, SPUtils.IS_ON, 0);
        if (isOn == 1) {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etPhone = findViewById(R.id.et_phone);
        etPass = findViewById(R.id.et_pass);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                initLogin();
//                goToActivity(MainActivity.class);
                break;
            case R.id.btn_register:
                goToActivity(RegisterActivity.class);
                break;
        }

    }

    private void initLogin() {
        String phone = etPhone.getText().toString();
        String pass = etPass.getText().toString();
        if (isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (isEmpty(pass)) {
            showToast("密码不能为空");
            return;
        }
        UserBean userBean = LitePal.where("name=? and pass= ?", phone, pass).findFirst(UserBean.class);
        if (userBean == null) {
            showToast("用户名或密码不正确");
            return;
        }
        BaseMessageInit.INSTENCE.setUserBean(userBean);
        goToActivity(MainActivity.class);
        finish();

    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.equals("");
    }
}
