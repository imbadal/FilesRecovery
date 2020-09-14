package com.example.imagerecover.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagerecover.R;

import static com.example.imagerecover.config.Config.APP_PREFERENCE;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView tvRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        getSharedPreferences(APP_PREFERENCE, MODE_PRIVATE).edit()
                .putBoolean("isFirstOpen", false).apply();

        tvRecovery = findViewById(R.id.tv_recovery);

        tvRecovery.setOnClickListener(v -> {
//            Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(context, ScannerActivity.class));
            startActivity(new Intent(context, MediaScannerActivity.class));

        });

    }

    private void startScan() {


    }
}
