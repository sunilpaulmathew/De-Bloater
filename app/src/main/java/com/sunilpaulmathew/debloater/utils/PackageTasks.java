/*
 * Copyright (C) 2020-2021 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of Package Manager, a simple, yet powerful application
 * to manage other application installed on an android device.
 *
 */

package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class PackageTasks {

    private static final String MODULE_PARENT = "/data/adb/modules/De-bloater";
    public static String mSearchText;
    public static AppCompatEditText mSearchWord;
    public static AppCompatImageButton mSearchButton;
    public static AppCompatTextView mAbout;

    static void createModuleParent() {
        Utils.runCommand("mkdir " + MODULE_PARENT);
    }

    public static List<String> getActivePackageData(Context context) {
        List<String> mData = new ArrayList<>();
        List<ApplicationInfo> packages = getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA);
        if (Utils.getBoolean("sort_name", true, context)) {
            Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(getPackageManager(context)));
        }
        for (ApplicationInfo packageInfo: packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                    && getSupportedAppsList(packageInfo.sourceDir, context)) {
                if (mSearchText == null) {
                    mData.add(packageInfo.packageName);
                } else if (getPackageManager(context).getApplicationLabel(packageInfo).toString().toLowerCase().contains(mSearchText)) {
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
        for (String line : Utils.runAndGetOutput("find " + MODULE_PARENT + "/system -type f -name *.apk").split("\\r?\\n")) {
            if (line.endsWith(".apk")) {
                mData.add(line);
            }
        }
        return mData;
    }

    private static boolean getSupportedAppsList(String apkPath, Context context) {
        boolean system = Utils.getBoolean("apps_system", true, context);
        boolean vendor = Utils.getBoolean("apps_vendor", true, context);
        boolean product = Utils.getBoolean("apps_product", true, context);
        boolean systemApps = apkPath.startsWith("/system/app") || apkPath.startsWith("/system/priv-app")
                || apkPath.startsWith("/system/product/app") || apkPath.startsWith("/system/vendor/app")
                || apkPath.startsWith("/system/vendor/overlay") || apkPath.startsWith("/system/product/overlay");
        boolean vendorApps = apkPath.startsWith("/vendor/overlay") || apkPath.startsWith("/vendor/app");
        boolean productApps = apkPath.startsWith("/product/app") || apkPath.startsWith("/product/overlay");
        if (system && vendor && product) {
            return systemApps || vendorApps || productApps;
        } else if (system && vendor) {
            return systemApps || vendorApps;
        } else if (system && product) {
            return systemApps || productApps;
        } else if (vendor && product) {
            return vendorApps || productApps;
        } else if (system) {
            return systemApps;
        } else if (vendor) {
            return vendorApps;
        } else if (product) {
            return productApps;
        } else {
            return false;
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
        }
        return apkPath;
    }

    public static void initializeModule() {
        if (!Utils.exist(MODULE_PARENT)) {
            createModuleParent();
            Utils.chmod("755", MODULE_PARENT);
            Utils.create("id=De-bloater\n" +
                            "name=De-bloater\n" +
                            "version=v1.0\n" +
                            "versionCode=1\n" +
                            "author=sunilpaulmathew\n" +
                            "description=De-bloat apps Systemless-ly",
                    MODULE_PARENT + "/module.prop");
            Utils.chmod("644", MODULE_PARENT + "/module.prop");
        }
    }

    public static void removeModule(Activity activity) {
        Utils.delete(activity.getFilesDir().getPath() + "/De-bloater");
        Utils.delete(MODULE_PARENT);
        Utils.saveBoolean("tomatot_extreme", false, activity);
        Utils.saveBoolean("tomatot_invisible", false, activity);
        Utils.saveBoolean("tomatot_light", false, activity);
        Utils.restartApp(activity);
    }

    public static boolean isModuleInitialized() {
        return Utils.exist(MODULE_PARENT) && Utils.exist(MODULE_PARENT + "/module.prop");
    }

    public static void enableTomatotInvisible(Context context) {
        initializeModule();
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        Utils.saveBoolean("tomatot_invisible", true, context);
    }

    public static void disableTomatotInvisible(Context context) {
        initializeModule();
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        Utils.saveBoolean("tomatot_invisible", false, context);
    }

    public static void enableTomatotLight(Context context) {
        initializeModule();
        String[] light = CustomScripts.getTomatotLight().toString().substring(1, CustomScripts
                .getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        Utils.saveBoolean("tomatot_light", true, context);
    }

    public static void disableTomatotLight(Context context) {
        initializeModule();
        String[] light = CustomScripts.getTomatotLight().toString().substring(1, CustomScripts
                .getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        Utils.saveBoolean("tomatot_light", false, context);
    }

    public static void enableTomatotExtreme(Context context) {
        initializeModule();
        String[] extreme = CustomScripts.getTomatotExtreme().toString().substring(1, CustomScripts
                .getTomatotExtreme().toString().length() - 1).split(", ");
        for (String s : extreme) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        String[] light = CustomScripts.getTomatotLight().toString().substring(1, CustomScripts
                .getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                setToDelete(s, new File(s).getName(), context);
            }
        }
        Utils.saveBoolean("tomatot_extreme", true, context);
    }

    public static void disableTomatotExtreme(Context context) {
        initializeModule();
        String[] extreme = CustomScripts.getTomatotExtreme().toString().substring(1, CustomScripts
                .getTomatotExtreme().toString().length() - 1).split(", ");
        for (String s : extreme) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        String[] light = CustomScripts.getTomatotLight().toString().substring(1, CustomScripts
                .getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        String[] invisible = CustomScripts.getTomatotInvisible().toString().substring(1, CustomScripts
                .getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(MODULE_PARENT + s)) {
                revertDelete(s);
            }
        }
        Utils.saveBoolean("tomatot_extreme", false, context);
    }

    static void setToDelete(String path, String name, Context context) {
        initializeModule();
        new File(context.getFilesDir().getPath() + "/De-bloater" + new File(path).getParentFile()).mkdirs();
        Utils.create(name, context.getFilesDir().getPath() + "/De-bloater" + path);
        Utils.copy(context.getFilesDir().getPath() + "/De-bloater/*", MODULE_PARENT);
        Utils.delete(context.getFilesDir().getPath() + "/De-bloater/*");
    }

    static void revertDelete(String path) {
        Utils.delete(MODULE_PARENT + path);
    }

}