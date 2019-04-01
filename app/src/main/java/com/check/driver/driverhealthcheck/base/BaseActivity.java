package com.check.driver.driverhealthcheck.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;



/**
 * FieldSamplingSystemStaff
 *
 * @author sjw
 * @date 2018/8/2.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        isCloseSeekBar();
        //将activity加入到AppManager堆栈中

        ActivityManager.INSTANCE.addActivity(this);
    }

    public void showToast(String msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToasttiaoshi(String msg) {
        if (false) {
            return;
        }
        if (msg == null) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.INSTANCE.removeActivity(this);
        int n = ActivityManager.INSTANCE.getActivityNum();
//        if (n == 0) {
//            Intent intent = new Intent(this, ChatConnectService.class);
//            intent.putExtra(ChatConnectService.TYPE_CHAT, ChatConnectService.TYPE_END);
//            startService(intent);
//        }
    }

    public void goToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    public void isCloseSeekBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setSeekBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(color);
        }
    }

//    private SpotsDialog spotsDialog;
//
//    public void showLandingDialog(String msg) {
//        if (spotsDialog == null) {
//            spotsDialog = (SpotsDialog) new SpotsDialog.Builder()
//                    .setContext(this).build();
//        }
//        spotsDialog.setMessage(msg);
//        spotsDialog.show();
//    }
//
//    public void hideDialog() {
//        if (spotsDialog != null) {
//            spotsDialog.dismiss();
//        }
//
//    }

    public void backClick(View view) {
        onBackPressed();
    }
}