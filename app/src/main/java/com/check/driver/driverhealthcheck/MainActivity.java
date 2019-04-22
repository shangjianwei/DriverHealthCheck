package com.check.driver.driverhealthcheck;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.driver.driverhealthcheck.activity.ChangeInfoActivity;
import com.check.driver.driverhealthcheck.activity.HistoryActivity;
import com.check.driver.driverhealthcheck.activity.HistoryDataActivity;
import com.check.driver.driverhealthcheck.activity.SimulationActivity;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.base.BaseMessageInit;
import com.check.driver.driverhealthcheck.base.playVoice;
import com.check.driver.driverhealthcheck.bean.BaseSetBean;
import com.check.driver.driverhealthcheck.bean.CarOnBean;
import com.check.driver.driverhealthcheck.bean.MessageBean;
import com.check.driver.driverhealthcheck.bean.UserBean;
import com.check.driver.driverhealthcheck.service.MyService;
import com.check.driver.driverhealthcheck.utils.SPUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    private Button btnStart;   //车辆启动
    private Button btnEnd;      //车辆熄火
    private TextView tv_head;  //人名


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGetItem();
        initView();
        initName();
        initListener();
        initData();
    }

    private void initName() {
        if (navigationView != null) {
            tv_head = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_head);
        }
        UserBean bean =BaseMessageInit.INSTENCE.getUserBean();;
        String name = bean.getUserName();
        tv_head.setText(name);
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


        playVoice.INSTANCE.create(this);
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
                        onBackPressed();
                        break;
                    case R.id.navItem4:
                        goToActivity(ChangeInfoActivity.class);
                        break;
                    case R.id.navItem5:
                        goToActivity(HistoryDataActivity.class);
                        break;

                }
                return false;
            }
        });
    }

    private void initListener() {
        ivMenu.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    private void initView() {
        ivMenu = findViewById(R.id.iv_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        tvDriveTime = findViewById(R.id.tv_drive_time);
        tvDriveNote = findViewById(R.id.tv_drive_note);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvTempNote = findViewById(R.id.tv_temp_note);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvHumidityNote = findViewById(R.id.tv_humidity_note);
        tvCOConcentration = findViewById(R.id.tv_CO_concentration);
        tvCONote = findViewById(R.id.tv_CO_note);
        tvHeartRate = findViewById(R.id.tv_heart_rate);
        tvHeartRateNote = findViewById(R.id.tv_heart_rate_note);
        tvBloodPressure = findViewById(R.id.tv_blood_pressure);
        tvBloodPressureNote = findViewById(R.id.tv_blood_pressure_note);
        tvBloodFat = findViewById(R.id.tv_blood_fat);
        tvBloodFatNote = findViewById(R.id.tv_blood_fat_note);
        btnStart = findViewById(R.id.btn_start);
        btnEnd = findViewById(R.id.btn_end);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_menu:

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.btn_start:
                //开始  用的intent
                Intent intent = new Intent(this, MyService.class);
                intent.putExtra(MyService.type, 1);
                startService(intent);
                break;
            case R.id.btn_end:
                //结束  用的intent1
                Intent intent1 = new Intent(this, MyService.class);
                intent1.putExtra(MyService.type, 2);
                startService(intent1);
                break;
        }
    }

    private boolean isSHow = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (isSHow) {
                changeMessage(BaseMessageInit.INSTENCE.getMessageBean());
                handler.sendEmptyMessageDelayed(1, 1000);
            }
            return false;
        }
    });

    private void changeMessage(MessageBean messageBean) {
        int isOn = SPUtils.getInt(this, SPUtils.IS_ON, 0);
        if (isOn == 1) {
            String time = "";
            long beginTime = SPUtils.getLong(this, SPUtils.BEGIN_TIME, 0);
            if (beginTime == 0) {
                beginTime = System.currentTimeMillis();
            }
            long cha = System.currentTimeMillis() - beginTime;
            cha = cha / 1000;//秒
            if (cha < 60) {
                time = cha + "秒";
            } else if (cha == 60) {
                time = "1分钟";
            } else if (cha < 3600) {
                time = cha / 60 + "分" + cha % 60 + "秒";
            } else if (cha == 3600) {
                time = "1小时";
            } else if (cha < 3600 * 24) {
                time = cha / 3600 + "小时" + (cha % 3600) / 60 + "分" + cha % 60 + "秒";
            } else {
                int day = (int) (cha / (3600 * 24));
                int hour = (int) ((cha - day * 3600 * 24) / 3600);
                time = day + "天" + hour + "小时";
            }
            tvDriveTime.setText(time);
            if (cha > 3600 * 4) {
                tvDriveNote.setText("您已属于疲劳驾驶，请及时休息");
                playVoice.INSTANCE.play(playVoice.DEIVETIME, this);
                tvDriveNote.setTextColor(Color.parseColor("#E22018"));

            } else {
                tvDriveNote.setText("驾驶时间正常");
                tvDriveNote.setTextColor(Color.parseColor("#CCCDCF"));
            }

            if (messageBean == null) {
                return;
            }
            //温度
            String tem = getTemp(messageBean.getTem());
            tvTemperature.setText(tem);
            if (messageBean.getTem() > 28 || messageBean.getTem() < 10) {
                tvTempNote.setText("车内温度异常，请注意！");
                playVoice.INSTANCE.play(playVoice.TEMP, this);
                tvTempNote.setTextColor(Color.parseColor("#E22018"));

            } else {
                tvTempNote.setText("车内温度正常");
                tvTempNote.setTextColor(Color.parseColor("#CCCDCF"));
            }
            //一氧化碳浓度
            String co = decimalFormat2.format(messageBean.getCo()) + "mg/m²";
            tvCOConcentration.setText(co);
            if (messageBean.getCo() > 30) {
                playVoice.INSTANCE.play(playVoice.CO, this);
                tvCONote.setText("车内一氧化碳浓度过高，请注意");
                tvCONote.setTextColor(Color.parseColor("#E22018"));

            } else {
                tvCONote.setText("一氧化碳浓度正常");
                tvCONote.setTextColor(Color.parseColor("#CCCDCF"));
            }
            //心率
            String heart = messageBean.getHeart() + "";
            tvHeartRate.setText(heart);
            if (messageBean.getHeart() > 100) {
                playVoice.INSTANCE.play(playVoice.HEART, this);
                tvHeartRateNote.setText("您的心率过高，请注意");
                tvHeartRateNote.setTextColor(Color.parseColor("#E22018"));

            } else {
                tvHeartRateNote.setText("心率正常");
                tvHeartRateNote.setTextColor(Color.parseColor("#CCCDCF"));
            }
            String bp = messageBean.getBp_H() + "/" + messageBean.getBp_L();
            tvBloodPressure.setText(bp);
            if (messageBean.getBp_H() > 140 || messageBean.getBp_L() > 90) {
                tvBloodPressureNote.setText("您的血压过高，请注意");
                playVoice.INSTANCE.play(playVoice.XUEYA, this);
                tvBloodPressureNote.setTextColor(Color.parseColor("#E22018"));

            } else {
                tvBloodPressureNote.setText("血压正常");
                tvBloodPressureNote.setTextColor(Color.parseColor("#CCCDCF"));
            }

            String bf = decimalFormat2.format(messageBean.getBf());
            tvBloodFat.setText(bf);
        }

    }

    private DecimalFormat decimalFormat = new DecimalFormat("#.0");
    private DecimalFormat decimalFormat2 = new DecimalFormat("#0.00");

    private String getTemp(double d) {
        return decimalFormat.format(d) + "℃";
    }

    @Override
    protected void onStart() {
        super.onStart();

        isSHow = true;
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isSHow = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playVoice.INSTANCE.releaseSoundPool();
    }
}
