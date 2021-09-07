package com.sunilpaulmathew.debloater.utils;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class PackageItem implements Serializable {

    private final String mAppName, mAPKPath, mPackageName;
    private final Drawable mAppIcon;
    private final boolean mUpdatedSystemApp;

    public PackageItem(String appName, String apkPath, Drawable appIcon, String packageName, boolean updatedSystemApp) {
        this.mAppName = appName;
        this.mAPKPath = apkPath;
        this.mAppIcon = appIcon;
        this.mPackageName = packageName;
        this.mUpdatedSystemApp = updatedSystemApp;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getAPKPath() {
        return mAPKPath;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public boolean isUpdatedSystemApp() {
        return mUpdatedSystemApp;
    }

}