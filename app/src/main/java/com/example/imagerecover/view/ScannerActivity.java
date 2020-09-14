package com.example.imagerecover.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagerecover.R;
import com.example.imagerecover.config.Config;
import com.example.imagerecover.helper.MediaScanner;
import com.example.imagerecover.model.ImageDataModel;
import com.example.imagerecover.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScannerActivity extends AppCompatActivity {

    private static final String TAG = "ScannerActivity_";
    public Context context;

    LinearLayout llImages;
    private ArrayList<ImageDataModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        context = this;

        llImages = findViewById(R.id.ll_images);

        new ScanImages().execute("all");
    }


    private final class ScanImages extends AsyncTask<String, Integer, ArrayList<ImageDataModel>> {

        int counter = 0;

        @Override
        protected ArrayList<ImageDataModel> doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: ");
            String path = "";

            if (strings[0].equalsIgnoreCase("all")) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RestoredPhotos";
            }

            Log.d(TAG, "doInBackground: path: " + path);

            checkFileOfDirectory(getFileList(path));

            return imageList;
        }

        @Override
        protected void onPostExecute(ArrayList<ImageDataModel> imageDataModels) {
            super.onPostExecute(imageDataModels);

            Log.d(TAG, "onPostExecute: ");

            Toast.makeText(context, "Post Execute", Toast.LENGTH_SHORT).show();

            for (ImageDataModel model : imageDataModels) {

//                ImageView imageView = new ImageView(context);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//                imageView.setLayoutParams(layoutParams);
//                Glide.with(context).load(model.getPath()).placeholder(R.drawable.ic_broken_image_24dp).centerCrop().into(imageView);
//                llImages.addView(imageView);

                String path = model.getPath();
                getImageInfo(path);

                Log.e(TAG, "path: " + model.getPath());

            }

            restoreImage(imageDataModels.get(0).getPath());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "onProgressUpdate: count: " + values[0]);
        }

        private File[] getFileList(String path) {
            File file = new File(path);
            if (!file.isDirectory()) {
                Log.d(TAG, "getFileList: null");
                return null;
            }
            Log.d(TAG, "getFileList: not null");
            return file.listFiles();
        }

        private void checkFileOfDirectory(File[] fileArr) {
            for (int i = 0; i < fileArr.length; i++) {
                Integer[] numArr = new Integer[1];
                int i2 = counter;
                counter = i2 + 1;
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
                        } else {
                            imageList.add(new ImageDataModel(fileArr[i].getPath(), false));
                        }
                    }
                }
            }
        }

    }

    public void getImageInfo(String path) {


        File file = new File(path);
        long fileDate = file.lastModified();
        String fileName = file.getName();
        String fileSize = FileUtils.getFileSizeKiloBytes(file);

        Date fileData = new Date(fileDate);
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd MMM yyyy");
        String stringDate = simpleDate.format(fileData);



        Log.d("image_info_", "getImageInfo: !! fileDate: " + stringDate+" !! fileSize: " + fileSize+ " !! fileName: "+ fileName);

        if (true) {
            return;
        }

//        try {
//            ExifInterface exif = new ExifInterface(path);
//
//            Log.e("image_info_", "TAG_DATETIME: " + exif.getAttribute(ExifInterface.TAG_DATETIME));
//            Log.e("image_info_", "TAG_GPS_LATITUDE: " + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
//            Log.e("image_info_", "TAG_GPS_LATITUDE_REF: " + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
//            Log.e("image_info_", "TAG_GPS_LONGITUDE: " + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
//            Log.e("image_info_", "TAG_GPS_LONGITUDE_REF: " + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
//            Log.e("image_info_", "TAG_DATETIME_ORIGINAL: " + exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
//            Log.e("image_info_", "TAG_DATETIME_ORIGINAL: " + exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
//            Log.e("image_info_", "TAG_FLASH: " + exif.getAttribute(ExifInterface.TAG_FLASH));
//            Log.e("image_info_", "TAG_FOCAL_LENGTH: " + exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
//            Log.e("image_info_", "TAG_GPS_PROCESSING_METHOD: " + exif.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));
//            Log.e("image_info_", "TAG_GPS_TIMESTAMP: " + exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));
//            Log.e("image_info_", "TAG_IMAGE_LENGTH: " + exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
//            Log.e("image_info_", "TAG_IMAGE_WIDTH: " + exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
//            Log.e("image_info_", "TAG_MAKE: " + exif.getAttribute(ExifInterface.TAG_MAKE));
//            Log.e("image_info_", "TAG_MODEL: " + exif.getAttribute(ExifInterface.TAG_MODEL));
//            Log.e("image_info_", "TAG_ORIENTATION: " + exif.getAttribute(ExifInterface.TAG_ORIENTATION));
//            Log.e("image_info_", "TAG_WHITE_BALANCE: " + exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE));
//            Log.e("image_info_", "TAG_COPYRIGHT: " + exif.getAttribute(ExifInterface.TAG_COPYRIGHT));
//            Log.e("image_info_", "TAG_ARTIST: " + exif.getAttribute(ExifInterface.TAG_ARTIST));
//            Log.e("image_info_", "TAG_FOCAL_LENGTH: " + exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
//            Log.e("image_info_", "TAG_SHARPNESS: " + exif.getAttribute(ExifInterface.TAG_SHARPNESS));
//            Log.e("image_info_", "---------------------------------------------------------------------------------------------\n\n");
//
//        } catch (IOException e) {
//            Log.e("image_info_", "Catch: " + e.getMessage());
//            e.printStackTrace();
//        }

    }

    public void restoreImage(String path) {

        Log.d(TAG, "restoreImage: ");
        File sourceFile = new File(path);
        File fileDirectory = new File(Config.IMAGE_RECOVER_DIRECTORY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Config.IMAGE_RECOVER_DIRECTORY);
        stringBuilder.append(File.separator);
        stringBuilder.append("recoveredimage.png");
        File destinationFile = new File(stringBuilder.toString());
        try {
            Log.d(TAG, "restoreImage: TRY");
            if (!destinationFile.exists()) {
                fileDirectory.mkdirs();
            }

            FileUtils.copy(sourceFile, destinationFile);

            if (Build.VERSION.SDK_INT >= 19) {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(destinationFile));
                context.sendBroadcast(intent);
            }
            new MediaScanner(context, destinationFile);
            getImageInfo(stringBuilder.toString());

        } catch (IOException e) {
            Log.d(TAG, "restoreImage: IOException");
            e.printStackTrace();
        }

    }


}
