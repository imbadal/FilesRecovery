package com.example.imagerecover.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.imagerecover.R;
import com.example.imagerecover.utils.Const;
import com.example.imagerecover.utils.PermissionManager;

import static com.example.imagerecover.config.Config.APP_PREFERENCE;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    boolean firstStart;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstStart = prefs.getBoolean("firstOpen", true);

        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PermissionManager.permissionCheck(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //permission granted

                    moveForeword();

                } else {
                    //permission not granted


                }

            }
        }, SPLASH_TIME_OUT);

    }

    private void moveForeword() {

        Intent intent;
        if (firstStart) {
            intent = new Intent(SplashActivity.this, UserAgreementActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Const.READ_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveForeword();
                } else {
                    Toast.makeText(context, "Please open settings and allow the permission.", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}