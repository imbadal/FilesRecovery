package com.example.imagerecover.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.imagerecover.R;

import static com.example.imagerecover.config.Config.APP_PREFERENCE;
import static com.example.imagerecover.config.Config.FILE_TYPE;
import static com.example.imagerecover.config.Config.FILE_TYPE_IMAGE;
import static com.example.imagerecover.config.Config.FILE_TYPE_VIDEO;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView tvRecoveryImage;
    TextView tvRecoveryVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        getSharedPreferences("prefs", MODE_PRIVATE).edit()
                .putBoolean("firstOpen", false).apply();

        tvRecoveryImage = findViewById(R.id.tv_recovery_image);
        tvRecoveryVideo = findViewById(R.id.tv_recovery_video);

        tvRecoveryImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, MediaScannerActivity.class);
            intent.putExtra(FILE_TYPE, FILE_TYPE_IMAGE);
            startActivity(intent);
        });

        tvRecoveryVideo.setOnClickListener(v -> {
            Intent intent = new Intent(context, MediaScannerActivity.class);
            intent.putExtra("file_type", FILE_TYPE_VIDEO);
            startActivity(intent);
        });

    }

    private void startScan() {


    }
}
