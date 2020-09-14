package com.example.imagerecover.async;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.imagerecover.config.Config;
import com.example.imagerecover.helper.MediaScanner;
import com.example.imagerecover.model.ImageDataModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RestoreAsyncTask extends AsyncTask<String, String, String> {

    private static final String TAG = "RestoreAsyncTask_";
    private Context context;
    private Handler handler;
    private ArrayList<ImageDataModel> alImageData;


    public RestoreAsyncTask(Context context, Handler handler, ArrayList<ImageDataModel> alImageData) {
        this.context = context;
        this.handler = handler;
        this.alImageData = alImageData;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        for (int strArr = 0; strArr < this.alImageData.size(); strArr++) {
            File sourceFile = new File((this.alImageData.get(strArr)).getPath());
            File fileDirectory = new File(Config.IMAGE_RECOVER_DIRECTORY);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Config.IMAGE_RECOVER_DIRECTORY);
            stringBuilder.append(File.separator);
            stringBuilder.append(getFileName(strArr));
            File destinationFile = new File(stringBuilder.toString());
            try {
                if (!destinationFile.exists()) {
                    fileDirectory.mkdirs();
                }
                copy(sourceFile, destinationFile);

                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(destinationFile));
                this.context.sendBroadcast(intent);
                new MediaScanner(this.context, destinationFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    protected void onPostExecute(String str) {

        Toast.makeText(context, "Restored successfully", Toast.LENGTH_SHORT).show();

        if (this.handler != null) {
            Message obtain = Message.obtain();
            obtain.what = Config.REPAIR;
            obtain.obj = str;
            this.handler.sendMessage(obtain);
        }
        super.onPostExecute(str);
    }


    public void copy(File file, File file2) throws IOException {

        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(file2).getChannel();

        source.transferTo(0, source.size(), destination);
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }


    public String getFileName(int i) {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.US);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(simpleDateFormat.format(date));
        stringBuilder.append(i);
        stringBuilder.append(".png");
        return stringBuilder.toString();

    }


}
