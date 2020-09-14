package com.example.imagerecover.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.imagerecover.utils.FileUtils;

import java.io.File;
import java.io.Serializable;

public class ImageDataModel {

    private String path;
    private String size;
    private long lastModified;
    private boolean isChecked;

    public ImageDataModel() {
    }

    public ImageDataModel(String path, boolean isChecked) {
        this.path = path;
        this.isChecked = isChecked;

        File file = new File(path);
        size = FileUtils.getFileSizeKiloBytes(file);
        lastModified = file.lastModified();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
