package com.example.imagerecover.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imagerecover.R;
import com.example.imagerecover.async.ScannerAsyncTask;
import com.example.imagerecover.config.Config;
import com.example.imagerecover.model.ImageDataModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.example.imagerecover.config.Config.DATA;
import static com.example.imagerecover.config.Config.REPAIR;
import static com.example.imagerecover.config.Config.UPDATE;

public class MediaScannerActivity extends AppCompatActivity {

    private static final String TAG = "MediaScannerActivity_";

    public static ArrayList<ImageDataModel> scannedImages = new ArrayList<>();
    Context context;
    PulsatorLayout pulsatorLayout;
    private MyDataHandler myDataHandler;
    private ArrayList<ImageDataModel> alImageData = new ArrayList();

    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_scanner);

        context = this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);

        initScanUi();
        initBottomSheetDialog();
        initAsync();


    }

    private void initAsync() {
        myDataHandler = new MyDataHandler();
        new ScannerAsyncTask(this, this.myDataHandler).execute("all");
    }

    private void initScanUi() {
        pulsatorLayout = findViewById(R.id.pulsator);
        pulsatorLayout.start();
    }

    private void initBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_dialog_scan);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);

        LinearLayout llDialog = bottomSheetDialog.findViewById(R.id.ll_dialog);
        LinearLayout llCancel = bottomSheetDialog.findViewById(R.id.ll_cancel);

        llDialog.setVisibility(View.VISIBLE);
        llCancel.setVisibility(View.GONE);

        tvCount = bottomSheetDialog.findViewById(R.id.tv_count);
        TextView tvCancel = bottomSheetDialog.findViewById(R.id.tv_cancel);
        TextView tvNo = bottomSheetDialog.findViewById(R.id.tv_no);
        TextView tvYes = bottomSheetDialog.findViewById(R.id.tv_yes);

        tvCancel.setOnClickListener(v -> {
            llCancel.setVisibility(View.VISIBLE);
            llDialog.setVisibility(View.GONE);
        });

        tvYes.setOnClickListener(v -> {
            onBackPressed();
        });

        tvNo.setOnClickListener(v -> {
            llCancel.setVisibility(View.GONE);
            llDialog.setVisibility(View.VISIBLE);
        });

        bottomSheetDialog.show();

    }


    public class MyDataHandler extends Handler {


        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == Config.DATA) {

                alImageData.clear();
                alImageData.addAll((ArrayList) message.obj);

                Log.d(TAG, "handleMessage: " + DATA);

                next();

            } else if (message.what == Config.REPAIR) {

                Log.d(TAG, "handleMessage: " + Config.REPAIR);

            } else if (message.what == Config.UPDATE) {
                Log.d(TAG, "handleMessage: " + UPDATE);
                TextView tvScannedItems = tvCount;
                tvScannedItems.setText(message.obj.toString());
            }
        }
    }

    public void next() {

        PreRecoveryActivity.scannedImages = alImageData;
        Intent intent = new Intent(context, PreRecoveryActivity.class);
        startActivity(intent);
        finish();

    }


}
