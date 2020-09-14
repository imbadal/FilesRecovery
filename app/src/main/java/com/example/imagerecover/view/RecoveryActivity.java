package com.example.imagerecover.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.imagerecover.R;
import com.example.imagerecover.adapter.RecoveredImageAdapter;
import com.example.imagerecover.async.RestoreAsyncTask;
import com.example.imagerecover.config.Config;
import com.example.imagerecover.model.ImageDataModel;

import java.io.File;
import java.util.ArrayList;

public class RecoveryActivity extends AppCompatActivity {

    private static final String TAG = "RecoveryActivity_";
    Context context;
    RecyclerView rvRecovered;
    public static ArrayList<String> selectedImages = new ArrayList<>();
    public static ArrayList<ImageDataModel> recoveredImages = new ArrayList<>();
    RecoveredImageAdapter adapter;
    private MyDataHandler myDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        context = this;
        initRecyclerview();
        initAsync();

    }

    private void initAsync() {
        myDataHandler = new MyDataHandler();
        new RestoreAsyncTask(this, this.myDataHandler, recoveredImages).execute("restored");
    }

    private void initRecyclerview() {

        recoveredImages.clear();
        for (String path : selectedImages) {
            recoveredImages.add(new ImageDataModel(path, false));
        }

        rvRecovered = findViewById(R.id.rv_recovered);
        rvRecovered.setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new RecoveredImageAdapter(context, recoveredImages);
        rvRecovered.setAdapter(adapter);

    }

    public class MyDataHandler extends Handler {

        public void handleMessage(Message message) {

            super.handleMessage(message);

            if (message.what == Config.DATA) {

                recoveredImages.clear();
                recoveredImages.addAll((ArrayList) message.obj);
                adapter.notifyDataSetChanged();

            } else if (message.what == Config.REPAIR) {

                //onPostExecute
                adapter.notifyDataSetChanged();

            } else if (message.what == Config.UPDATE) {


            }
        }
    }

    public void deletePictures(ArrayList<ImageDataModel> arrayList) {

        for (int i = 0; i < arrayList.size(); i++) {

            File delFile = new File(arrayList.get(i).getPath());
            if (delFile.delete())
                RecoveryActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(delFile)));
        }

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}