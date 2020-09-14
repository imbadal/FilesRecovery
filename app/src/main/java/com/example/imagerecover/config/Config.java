package com.example.imagerecover.config;

import android.os.Environment;

import java.io.File;

public class Config {

    public static final int DATA = 1000;
    public static final int REPAIR = 2000;
    public static final int UPDATE = 3000;
    public static final String IMAGE_RECOVER_DIRECTORY;

    static {

        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStorageDirectory());
        sbDirectory.append(File.separator);
        sbDirectory.append("RecoverMedia_02");
        IMAGE_RECOVER_DIRECTORY = sbDirectory.toString();

    }


    public static final String APP_PREFERENCE = "appPreference";
}