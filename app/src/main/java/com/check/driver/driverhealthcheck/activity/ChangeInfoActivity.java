package com.check.driver.driverhealthcheck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.bean.UserBean;

import org.litepal.LitePal;

public class ChangeInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etPhone;
    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etNewPass2;
    private ImageView ivBack;
    private Button btnRegister;
    private EditText etHeight;
    private EditText etWeight;
    private EditText etAge;
    private String passa;
    private TextView tvDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvDel.setOnClickListener(this);
    }

    private void initView() {
        tvDel=findViewById(R.id.tv_del);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etOldPass = findViewById(R.id.et_pass);
        etNewPass =findViewById(R.id.et_new_pass);
        etNewPass2 = findViewById(R.id.et_pass2);
        ivBack = findViewById(R.id.iv_back);
        btnRegister = findViewById(R.id.btn_register);
        etAge=findViewById(R.id.et_age);
        etHeight=findViewById(R.id.et_height);
        etWeight=findViewById(R.id.et_weight);

        UserBean bean = BaseMessageInit.INSTENCE.getUserBean();;
        String userName = bean.getUserName();
        String phone =bean.getName();
        Integer age =bean.getAge();
        Double height =bean.getHeight();
        Double weight =bean.getWeight();
        passa=bean.getPass();


        etName.setText(userName);
        etPhone.setText(phone);
        etAge.setText(age+"");
        etHeight.setText(height+"");
        etWeight.setText(weight+"");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_register:
                //修改成功，退出登录
                initChange();
                break;
            case R.id.tv_del:
                //删除
                del();
                break;
        }
    }

    private void del() {
        UserBean userBean1 = BaseMessageInit.INSTENCE.getUserBean();
        userBean1.delete();
        goToActivity(LoginActivity.class);

    }

    private void initChange() {
        String name = etName.getText().toString();
        String pass = etOldPass.getText().toString();
        String pass1 =etNewPass.getText().toString();
        String pass2 = etNewPass2.getText().toString();
        String phone = etPhone.getText().toString();
        Double height =Double.parseDouble(etHeight.getText().toString());
        Double weight =Double.parseDouble(etWeight.getText().toString());
        Integer age =Integer.parseInt(etAge.getText().toString());


        if (isEmpty(name)) {
            showToast("姓名不能为空");
            return;
        }
        if (isEmpty(pass1)) {
            showToast("新密码不能为空");
            return;
        }
        if (isEmpty(pass) || isEmpty(pass2)) {
            showToast("原密码不能为空");
            return;
        }
        if (isEmpty(phone)) {
            showToast("电话不能为空");
            return;
        }
        if (!pass.equals(passa)){
            showToast("原密码不正确");
            return;
        }
        if (!pass1.equals(pass2)) {
            showToast("两次密码不一致");
            return;
        }


        UserBean userBeans = BaseMessageInit.INSTENCE.getUserBean();
        userBeans.setName(phone);
        userBeans.setPass(pass2);
        userBeans.setUserName(name);
        userBeans.setAge(age);
        userBeans.setHeight(height);
        userBeans.setWeight(weight);
        userBeans.save();
        goToActivity(LoginActivity.class);
        finish();
    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.equals("");
    }
}
