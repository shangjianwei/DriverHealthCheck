package com.check.driver.driverhealthcheck;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.driver.driverhealthcheck.activity.HistoryActivity;
import com.check.driver.driverhealthcheck.activity.SimulationActivity;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.bean.BaseSetBean;
import com.check.driver.driverhealthcheck.bean.CarOnBean;
import com.check.driver.driverhealthcheck.bean.MessageBean;
import com.check.driver.driverhealthcheck.service.MyService;
import com.check.driver.driverhealthcheck.utils.SPUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView ivMenu;  //菜单按钮
    private TextView tvDriveTime;  //驾驶时间
    private TextView tvDriveNote;  //驾驶建议
    private TextView tvTemperature;  //车内温度
    private TextView tvTempNote;  //温度建议
    private TextView tvHumidity;  //车内湿度
    private TextView tvHumidityNote; //湿度建议
    private TextView tvCOConcentration; //一氧化碳浓度
    private TextView tvCONote; //CO浓度建议
    private TextView tvHeartRate;   //心率
    private TextView tvHeartRateNote; //心率建议
    private TextView tvBloodPressure;  //血压
    private TextView tvBloodPressureNote;// 血压建议
    private TextView tvBloodFat;   //血脂
    private TextView tvBloodFatNote; //血脂建议



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGetItem();
        initView();
        initListener();
        initData();
    }

    private void initGetItem() {
        //判断汽车是不是在启动状态

        BaseSetBean albumToUpdate = LitePal.find(BaseSetBean.class, 1);
        if (albumToUpdate == null) {
            //第一次安装没有数据
            albumToUpdate = new BaseSetBean();
            albumToUpdate.setFirst();
            //设置默认数据
            albumToUpdate.save();
        }
        BaseMessageInit.INSTENCE.setBaseSetBean(albumToUpdate);
    }

    private void initData() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navItem1:
                        goToActivity(HistoryActivity.class);
                        break;
                    case R.id.navItem2:
                        goToActivity(SimulationActivity.class);
                        break;
                    case R.id.navItem3:
                        //退出
                        showToast("3");
                        break;

                }
                return false;
            }
        });
    }

    private void initListener() {
        ivMenu.setOnClickListener(this);
    }

    private void initView() {
        ivMenu = findViewById(R.id.iv_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        tvDriveTime = findViewById(R.id.tv_drive_time);
        tvDriveNote=findViewById(R.id.tv_drive_note);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvTempNote=findViewById(R.id.tv_temp_note);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvHumidityNote=findViewById(R.id.tv_humidity_note);
        tvCOConcentration = findViewById(R.id.tv_CO_concentration);
        tvCONote=findViewById(R.id.tv_CO_note);
        tvHeartRate = findViewById(R.id.tv_heart_rate);
        tvHeartRateNote=findViewById(R.id.tv_heart_rate_note);
        tvBloodPressure = findViewById(R.id.tv_blood_pressure);
        tvBloodPressureNote=findViewById(R.id.tv_blood_pressure_note);
        tvBloodFat = findViewById(R.id.tv_blood_fat);
        tvBloodFatNote=findViewById(R.id.tv_blood_fat_note);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_menu:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)
                        ) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;


        }
    }
}
