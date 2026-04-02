package com.sunilpaulmathew.debloater.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */
public class PackageItem implements Serializable {

    private final String mAppName, mAPKPath, mPackageName, mRemRec;
    private final boolean mUpdatedSystemApp;

    private Drawable mAppIcon;

    public PackageItem(String appName, String apkPath, String packageName, String remRec, boolean updatedSystemApp) {
        this.mAppName = appName;
        this.mAPKPath = apkPath;
        this.mPackageName = packageName;
        this.mRemRec = remRec;
        this.mUpdatedSystemApp = updatedSystemApp;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getAPKPath() {
        return mAPKPath;
    }

    public String getRemovalRec() {
        return mRemRec;
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

    public void loadPackages(ImageButton icon, TextView name, TextView packageName) {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                PackageManager pm = icon.getContext().getPackageManager();
                try {
                    ApplicationInfo ai = pm.getApplicationInfo(mPackageName, 0);
                    mAppIcon = pm.getApplicationIcon(ai);
                } catch (PackageManager.NameNotFoundException ignored) {
                }

                handler.post(() -> {
                    name.setText(mAppName);
                    packageName.setText(mPackageName);
                    icon.setImageDrawable(mAppIcon);
                });
            });
        }
    }

}