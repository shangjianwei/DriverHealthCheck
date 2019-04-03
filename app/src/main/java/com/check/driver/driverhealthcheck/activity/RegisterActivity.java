package com.check.driver.driverhealthcheck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.bean.UserBean;

import org.litepal.LitePal;

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
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etPass = findViewById(R.id.et_pass);
        etPass2 = findViewById(R.id.et_pass2);
        ivBack = findViewById(R.id.iv_back);
        btnRegister = findViewById(R.id.btn_register);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_register:
                //注册成功，跳到登录界面
                initRegist();
                break;
        }

    }

    private void initRegist() {
        String name = etName.getText().toString();
        String pass = etPass.getText().toString();
        String pass2 = etPass2.getText().toString();
        String phone = etPhone.getText().toString();
        if (isEmpty(name)) {
            showToast("姓名不能为空");
            return;
        }
        if (isEmpty(pass) || isEmpty(pass2)) {
            showToast("密码不能为空");
            return;
        }
        if (isEmpty(phone)) {
            showToast("电话不能为空");
            return;
        }
        if (!pass.equals(pass2)) {
            showToast("手机号不能为空");
            return;
        }
        UserBean userBean = LitePal.where("name=?", phone).findFirst(UserBean.class);
        if (userBean != null) {
            showToast("该手机号已注册");
            return;
        }

        UserBean userBeans = new UserBean();
        userBeans.setName(phone);
        userBeans.setPass(pass);
        userBeans.setUserName(name);
        userBeans.save();
        showToast("注册成功");
        finish();
    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.endsWith("");
    }

}
