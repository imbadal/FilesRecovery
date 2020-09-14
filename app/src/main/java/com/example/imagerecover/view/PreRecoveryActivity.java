package com.example.imagerecover.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagerecover.R;
import com.example.imagerecover.adapter.ScannerAdapter;
import com.example.imagerecover.model.ImageDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PreRecoveryActivity extends AppCompatActivity {

    private static final String TAG = "PreRecoveryActivity_";
    public static ArrayList<ImageDataModel> scannedImages = new ArrayList<>();

    Context context;
    RecyclerView rvScannedImage;
    ScannerAdapter adapter;
    ArrayList<String> selectedImages = new ArrayList<>();
    TextView tvProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_recovery);

        context = this;
        tvProceed = findViewById(R.id.tv_proceed);
        initRecyclerview();

        tvProceed.setOnClickListener(v -> {
            if (selectedImages.size() == 0) {
                Toast.makeText(context, "Please select at least one.", Toast.LENGTH_SHORT).show();
                return;
            }
            RecoveryActivity.selectedImages = selectedImages;
            Intent intent = new Intent(context, RecoveryActivity.class);
            startActivity(intent);
        });

    }

    private void initRecyclerview() {

        sortByLastModified();

        rvScannedImage = findViewById(R.id.rv_scanned_images);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        rvScannedImage.setLayoutManager(layoutManager);
        adapter = new ScannerAdapter(context, scannedImages);
        rvScannedImage.setAdapter(adapter);

    }

    public void setSelectedImages(String path, boolean isSelected) {

        if (isSelected) {
            selectedImages.add(path);
        } else {
            selectedImages.remove(path);
        }

    }

    public void sortByLastModified() {

        Collections.sort(scannedImages, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ImageDataModel i1 = (ImageDataModel) o1;
                ImageDataModel i2 = (ImageDataModel) o2;
                return Long.compare(i2.getLastModified(), i1.getLastModified());
            }
        });

    }
}