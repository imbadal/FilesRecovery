package com.example.imagerecover.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.imagerecover.config.Config;
import com.example.imagerecover.model.ImageDataModel;

import java.io.File;
import java.util.ArrayList;

public class ScannerAsyncTask extends AsyncTask<String, Integer, ArrayList<ImageDataModel>> {

    private static final String TAG = "ScannerAsyncTask_";
    private ArrayList<ImageDataModel> alImageData;
    private Context context;
    private Handler handler;
    private ProgressDialog progressDialog;
    int i = 0;

    public ScannerAsyncTask(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.alImageData = new ArrayList<>();
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ImageDataModel> doInBackground(String... strings) {

        String strArr;
        if (strings[0].equalsIgnoreCase("all")) {
            //if all then scan for complete internal and external storage
            strArr = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            //otherwise only scan items in "RestoredPics" folder (app folder).
            strArr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RestoredPhotos";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("root = ");
        stringBuilder.append(strArr);

        Log.d(TAG, "doInBackground: path: " + strArr);

        checkFileOfDirectory(getFileList(strArr));
        return this.alImageData;

    }

    protected void onProgressUpdate(Integer... numArr) {

        if (this.handler != null) {
            Message message = Message.obtain();
            message.what = Config.UPDATE;
            message.obj = alImageData.size();
            this.handler.sendMessage(message);
        }
    }

    protected void onPostExecute(ArrayList<ImageDataModel> arrayList) {
        if (this.progressDialog != null) {
            this.progressDialog.cancel();
            this.progressDialog = null;
        }
        if (this.handler != null) {
            Message message = Message.obtain();
            message.what = Config.DATA;
            message.obj = arrayList;
            this.handler.sendMessage(message);
        }
        super.onPostExecute(arrayList);
    }

    public void checkFileOfDirectory(File[] fileArr) {

        for (int i = 0; i < fileArr.length; i++) {
            Integer[] numArr = new Integer[1];
            int i2 = this.i;
            this.i = i2 + 1;
            numArr[0] = i2;
            publishProgress(numArr);
            if (fileArr[i].isDirectory()) {
                checkFileOfDirectory(getFileList(fileArr[i].getPath()));
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(fileArr[i].getPath(), options);
                if (!(options.outWidth == -1 || options.outHeight == -1)) {
                    if (fileArr[i].getPath().endsWith(".exo") || fileArr[i].getPath().endsWith(".mp3") || fileArr[i].getPath().endsWith(".mp4")
                            || fileArr[i].getPath().endsWith(".pdf") || fileArr[i].getPath().endsWith(".apk") || fileArr[i].getPath().endsWith(".txt")
                            || fileArr[i].getPath().endsWith(".doc") || fileArr[i].getPath().endsWith(".exi") || fileArr[i].getPath().endsWith(".dat")
                            || fileArr[i].getPath().endsWith(".m4a") || fileArr[i].getPath().endsWith(".json") || fileArr[i].getPath().endsWith(".chck")) {
                        //do nothing, just skip these files
                    } else if (fileArr[i].getPath().endsWith(".jpg") || fileArr[i].getPath().endsWith(".png") || fileArr[i].getPath().endsWith(".gif")
                            || fileArr[i].getPath().endsWith(".raw") || fileArr[i].getPath().endsWith(".tiff")) {
                        //image  files
                        this.alImageData.add(new ImageDataModel(fileArr[i].getPath(), false));
                    } else {
                        //do nothing, just skip these files
                    }
                }
            }
        }
    }

    public File[] getFileList(String str) {
        File file = new File(str);
        if (!file.isDirectory()) {
            return null;
        }
        return file.listFiles();
    }
}
