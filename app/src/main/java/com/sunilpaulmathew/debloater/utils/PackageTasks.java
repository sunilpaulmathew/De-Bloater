/*
 * Copyright (C) 2020-2021 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of Package Manager, a simple, yet powerful application
 * to manage other application installed on an android device.
 *
 */

package com.sunilpaulmathew.debloater.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class PackageTasks {

    static void createModuleParent() {
        Utils.runCommand(Utils.magiskBusyBox() + " mkdir " + Common.getModuleParent());
    }

    public static List<String> getActivePackageData(Context context) {
        List<String> mData = new ArrayList<>();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA);
        if (Utils.getBoolean("sort_name", true, context)) {
            Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(getPackageManager(context)));
        } else {
            Collections.sort(packages, (lhs, rhs) -> String.CASE_INSENSITIVE_ORDER.compare(lhs.packageName, rhs.packageName));
        }
        for (ApplicationInfo packageInfo: packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                    && getSupportedAppsList(packageInfo.sourceDir, context)) {
                if (Common.getSearchText() == null) {
                    mData.add(packageInfo.packageName);
                } else if (getPackageManager(context).getApplicationLabel(packageInfo).toString().toLowerCase().contains(Common.getSearchText())) {
                    mData.add(packageInfo.packageName);
                }
            }
        }
        if (Utils.getBoolean("reverse_order", false, context)) {
            Collections.reverse(mData);
        }
        return mData;
    }

    public static List<String> getInactivePackageData() {
        List<String> mData = new ArrayList<>();
        for (String line : Utils.runAndGetOutput(Utils.magiskBusyBox() + " find " + Common.getModuleParent() + "/system -type f -name *.apk").split("\\r?\\n")) {
            if (line.endsWith(".apk")) {
                mData.add(line);
            }
        }
        return mData;
    }

    private static boolean getSupportedAppsList(String apkPath, Context context) {
        String mStatus = Utils.getString("appTypes", "all", context);
        boolean systemApps = apkPath.startsWith("/system/app") || apkPath.startsWith("/system/priv-app")
                || apkPath.startsWith("/system/product/app") || apkPath.startsWith("/system/product/priv-app")
                || apkPath.startsWith("/system/vendor/app") || apkPath.startsWith("/system/vendor/overlay")
                || apkPath.startsWith("/system/product/overlay") || apkPath.startsWith("/system/system_ext/app")
                || apkPath.startsWith("/system/system_ext/priv-app") || apkPath.startsWith("/system_ext/app")
                || apkPath.startsWith("/system_ext/priv-app");
        boolean vendorApps = apkPath.startsWith("/vendor/overlay") || apkPath.startsWith("/vendor/app");
        boolean productApps = apkPath.startsWith("/product/app") || apkPath.startsWith("/product/priv-app")
                || apkPath.startsWith("/product/overlay");
        switch (mStatus) {
            case "system":
                return systemApps;
            case "product":
                return productApps;
            case "vendor":
                return vendorApps;
            default:
                return systemApps || vendorApps || productApps;
        }
    }

    public static PackageManager getPackageManager(Context context) {
        return context.getApplicationContext().getPackageManager();
    }

    public static ApplicationInfo getAppInfo(String packageName, Context context) {
        try {
            return getPackageManager(context).getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getAppName(String packageName, Context context) {
        return getPackageManager(context).getApplicationLabel(Objects.requireNonNull(getAppInfo(
                packageName, context))).toString();
    }

    public static Drawable getAppIcon(String packageName, Context context) {
        return getPackageManager(context).getApplicationIcon(Objects.requireNonNull(getAppInfo(packageName, context)));
    }

    public static String getAPKPath(String packageName, Context context) {
        return Objects.requireNonNull(getAppInfo(packageName, context)).sourceDir;
    }

    public static String getAdjAPKPath(String packageName, Context context) {
        String apkPath = getAPKPath(packageName, context);
        if (apkPath.startsWith("/product/")) {
            apkPath = apkPath.replace("/product", "/system/product");
        } else if (apkPath.startsWith("/vendor/")) {
            apkPath = apkPath.replace("/vendor", "/system/vendor");
        } else if (apkPath.startsWith("/system_ext/")) {
            apkPath = apkPath.replace("/system_ext", "/system/system_ext");
        }
        return apkPath;
    }

    public static String getStoragePath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getModulePath() {
        return Common.getModuleParent();
    }

    public static void initializeModule() {
        if (!Utils.exist(Common.getModuleParent())) {
            createModuleParent();
            Utils.chmod("755", Common.getModuleParent());
            Utils.create("id=De-bloater\n" +
                            "name=De-bloater\n" +
                            "version=v1.0\n" +
                            "versionCode=1\n" +
                            "author=sunilpaulmathew\n" +
                            "description=De-bloat apps Systemless-ly",
                    Common.getModuleParent() + "/module.prop");
            Utils.chmod("644", Common.getModuleParent() + "/module.prop");
        }
    }

    public static void removeModule(Activity activity) {
        Utils.delete(activity.getFilesDir().getPath() + "/De-bloater");
        Utils.delete(Common.getModuleParent());
        Utils.saveBoolean("tomatot_extreme", false, activity);
        Utils.saveBoolean("tomatot_invisible", false, activity);
        Utils.saveBoolean("tomatot_light", false, activity);
    }

    public static boolean isModuleInitialized() {
        return Utils.exist(Common.getModuleParent()) && Utils.exist(Common.getModuleParent() + "/module.prop");
    }

    public static void setToDelete(String path, String name, Context context) {
        initializeModule();
        new File(context.getFilesDir().getPath() + "/De-bloater" + new File(path).getParentFile()).mkdirs();
        Utils.create(name, context.getFilesDir().getPath() + "/De-bloater" + path);
        Utils.copy(context.getFilesDir().getPath() + "/De-bloater/*", Common.getModuleParent());
        Utils.delete(context.getFilesDir().getPath() + "/De-bloater/*");
    }

    public static void revertDelete(String path) {
        Utils.delete(Common.getModuleParent() + path);
    }

    public static void toggleKeyboard(int mode, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mode == 1) {
            if (Common.getSearchWord().requestFocus()) {
                imm.showSoftInput(Common.getSearchWord(), InputMethodManager.SHOW_IMPLICIT);
            }
        } else {
            imm.hideSoftInputFromWindow(Common.getSearchWord().getWindowToken(), 0);
        }
    }

}