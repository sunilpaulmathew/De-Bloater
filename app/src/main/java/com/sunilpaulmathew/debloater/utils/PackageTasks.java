package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import in.sunilpaulmathew.sCommon.APKUtils.sAPKUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.FileUtils.sFileUtils;
import in.sunilpaulmathew.sCommon.PackageUtils.sPackageUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class PackageTasks {

    static void createModuleParent() {
        Utils.runCommand(Utils.magiskBusyBox() + "mkdir " + Common.getModuleParent());
    }

    public static List<PackageItem> getRawData(Context context) {
        List<PackageItem> mData = new ArrayList<>();
        List<ApplicationInfo> packages = getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo: packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                mData.add(new PackageItem(
                        sPackageUtils.getAppName(packageInfo.packageName, context).toString(),
                        sPackageUtils.isUpdatedSystemApp(packageInfo.packageName, context) ? findSystemAPKPath(packageInfo.packageName,
                                context) : sPackageUtils.getSourceDir(packageInfo.packageName, context),
                        sPackageUtils.getAppIcon(packageInfo.packageName, context),
                        packageInfo.packageName,
                        sPackageUtils.isUpdatedSystemApp(packageInfo.packageName, context)));
            }
        }
        return mData;
    }

    public static List<PackageItem> getActivePackageData(Context context) {
        List<PackageItem> mData = new ArrayList<>();
        for (PackageItem item : Common.getRawData()) {
            if (getSupportedAppsList(item.getAPKPath(), context)) {
                if (Common.getSearchText() == null) {
                    mData.add(item);
                } else if (Common.isTextMatched(item.getAppName()) || Common.isTextMatched(item.getPackageName())) {
                    mData.add(item);
                }
            }
        }
        if (sCommonUtils.getBoolean("sort_name", true, context)) {
            Collections.sort(mData, (lhs, rhs) -> String.CASE_INSENSITIVE_ORDER.compare(lhs.getAppName(), rhs.getAppName()));
        } else {
            Collections.sort(mData, (lhs, rhs) -> String.CASE_INSENSITIVE_ORDER.compare(lhs.getPackageName(), rhs.getPackageName()));
        }
        if (sCommonUtils.getBoolean("reverse_order", false, context)) {
            Collections.reverse(mData);
        }
        return mData;
    }

    public static List<String> getInactivePackageData() {
        List<String> mData = new ArrayList<>();
        for (String line : Utils.runAndGetOutput(Utils.magiskBusyBox() + "find " + Common.getModuleParent() + "/system -type f -name *.apk").split("\\r?\\n")) {
            if (line.endsWith(".apk")) {
                if (Common.getSearchText() == null) {
                    mData.add(line);
                } else if (Common.isTextMatched(line)) {
                    mData.add(line);
                }
            }
        }
        return mData;
    }

    private static boolean getSupportedAppsList(String apkPath, Context context) {
        String mStatus = sCommonUtils.getString("appTypes", "all", context);
        boolean systemApps = apkPath.startsWith("/system/app") || apkPath.startsWith("/system/priv-app")
                || apkPath.startsWith("/system/product/app") || apkPath.startsWith("/system/product/priv-app")
                || apkPath.startsWith("/system/vendor/app") || apkPath.startsWith("/system/vendor/overlay")
                || apkPath.startsWith("/system/product/overlay") || apkPath.startsWith("/system/system_ext/app")
                || apkPath.startsWith("/system/system_ext/priv-app") || apkPath.startsWith("/system_ext/app")
                || apkPath.startsWith("/system_ext/priv-app") || apkPath.startsWith("/system/preload");
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
                return true;
        }
    }

    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    public static String findSystemAPKPath(String packageName, Context context) {
        try {
            String mAPKPath = null;
            for (String line : Utils.runAndGetOutput("dumpsys package " + packageName + " | grep resourcePath").replace("resourcePath=", "").split("\\r?\\n")) {
                if (!line.startsWith("/data/")) {
                    mAPKPath = line.replaceAll("\\s+", "");
                    for (File mFile : Objects.requireNonNull(new File(mAPKPath).listFiles())) {
                        if (Objects.equals(sAPKUtils.getPackageName(mFile.getAbsolutePath(), context), packageName)) {
                            mAPKPath = mAPKPath + File.separator + mFile.getName();
                        }
                    }
                }
            }
            if (Utils.exist(mAPKPath)) return mAPKPath;
        } catch (NullPointerException ignored) {}
        return sPackageUtils.getSourceDir(packageName, context);
    }

    public static String getAdjAPKPath(String apkPath) {
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
        sCommonUtils.saveBoolean("tomatot_extreme", false, activity);
        sCommonUtils.saveBoolean("tomatot_invisible", false, activity);
        sCommonUtils.saveBoolean("tomatot_light", false, activity);
    }

    public static boolean isModuleInitialized() {
        return Utils.exist(Common.getModuleParent()) && Utils.exist(Common.getModuleParent() + "/module.prop");
    }

    public static void setToDelete(String path, String name, Context context) {
        initializeModule();
        sFileUtils.mkdir(new File(context.getFilesDir().getPath() + "/De-bloater" + new File(path).getParentFile()));
        Utils.create(name, context.getFilesDir().getPath() + "/De-bloater" + path);
        Utils.copy(context.getFilesDir().getPath() + "/De-bloater/*", Common.getModuleParent());
        Utils.delete(context.getFilesDir().getPath() + "/De-bloater/*");
    }

    public static void revertDelete(String path) {
        Utils.delete(Common.getModuleParent() + path);
    }

    public static void toggleKeyboard(AppCompatEditText editText, int mode, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mode == 1) {
            if (editText.requestFocus()) {
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        } else {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

}