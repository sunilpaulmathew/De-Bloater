package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import in.sunilpaulmathew.sCommon.APKUtils.sAPKUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.FileUtils.sFileUtils;
import in.sunilpaulmathew.sCommon.PackageUtils.sPackageUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class PackageTasks {

    private static final Set<String> SYSTEM_PATHS = Set.of(
            "/system/app", "/system/priv-app",
            "/system/system_ext/app", "/system/system_ext/priv-app",
            "/system_ext/app", "/system_ext/priv-app"
    );

    private static final Set<String> VENDOR_PATHS = Set.of(
            "/vendor/app", "/vendor/priv-app", "/system/vendor/app"
    );

    private static final Set<String> PRODUCT_PATHS = Set.of(
            "/product/app", "/product/priv-app", "/system/product/app",
            "/system/product/priv-app"
    );

    private static final Set<String> OVERLAY_PATHS = Set.of(
            "/system/vendor/overlay", "/system/product/overlay", "/vendor/overlay",
            "/system_ext/overlay", "/product/overlay"
    );

    private static final Set<String> MISC_PATHS = Set.of(
            "/preload", "/product/preinstall", "/system/preinstall",
            "/system/preload", "/odm/app", "/odm/priv-app",
            "/odm/overlay"
    );

    static void createModuleParent() {
        Utils.runCommand(Utils.magiskBusyBox() + "mkdir " + Common.getModuleParent());
    }

    public static List<PackageItem> getRawData(ProgressBar progressBar, Context context) {
        List<PackageItem> mData = new ArrayList<>();
        List<DebloaterEntry> debloaterEntries = getUADList(context);
        List<ApplicationInfo> packages = getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA);

        if (progressBar != null) {
            progressBar.setMax(packages.size());
        }

        for (ApplicationInfo packageInfo: packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                mData.add(new PackageItem(
                        sPackageUtils.getAppName(packageInfo.packageName, context).toString(),
                        findSystemAPKPath(packageInfo.packageName, context),
                        packageInfo.packageName,
                        debloaterEntries.stream()
                                .filter(entry -> entry.getPackageName().equals(packageInfo.packageName))
                                .map(DebloaterEntry::getRemovalStatus)
                                .findFirst()
                                .orElse(null),
                        sPackageUtils.isUpdatedSystemApp(packageInfo.packageName, context)));

                if (progressBar != null) {
                    if (progressBar.getProgress() < packages.size()) {
                        progressBar.setProgress(progressBar.getProgress() + 1);
                    } else {
                        progressBar.setProgress(0);
                    }
                }
            }
        }
        return mData;
    }

    public static List<PackageItem> getActivePackageData(Context context, String searchText) {
        List<PackageItem> mData = new ArrayList<>();
        for (PackageItem item : Common.getRawData()) {
            if (getSupportedAppsList(item.getAPKPath(), context)) {
                if (searchText == null) {
                    mData.add(item);
                } else if (Common.isTextMatched(item.getAppName(), searchText) || Common.isTextMatched(item.getPackageName(), searchText)) {
                    mData.add(item);
                }
            }
        }
        if (sCommonUtils.getInt("sort_apps", 1, context) == 0) {
            mData.sort((lhs, rhs) -> String.CASE_INSENSITIVE_ORDER.compare(lhs.getAppName(), rhs.getAppName()));
        } else {
            mData.sort((lhs, rhs) -> String.CASE_INSENSITIVE_ORDER.compare(lhs.getPackageName(), rhs.getPackageName()));
        }
        if (sCommonUtils.getBoolean("reverse_order", false, context)) {
            Collections.reverse(mData);
        }
        return mData;
    }

    public static List<DebloaterEntry> getUADList(Context context) {
        List<DebloaterEntry> debloaterEntries = new CopyOnWriteArrayList<>();
        try {
            JSONObject root = new JSONObject(sFileUtils.readAssetFile("uad_lists.json", context));

            Iterator<String> keys = root.keys();
            while (keys.hasNext()) {
                String packageName = keys.next();
                JSONObject packageInfo = root.getJSONObject(packageName);

                String list = packageInfo.getString("list");
                String description = packageInfo.getString("description");
                JSONArray dependencies = packageInfo.getJSONArray("dependencies");
                JSONArray neededBy = packageInfo.getJSONArray("neededBy");
                JSONArray labels = packageInfo.getJSONArray("labels");
                String removal = packageInfo.getString("removal");

                debloaterEntries.add(new DebloaterEntry(packageName, list, description, dependencies, neededBy, labels, removal));
            }

        } catch (Exception ignored) {
        }
        return debloaterEntries;
    }

    public static List<String> getInactivePackageData(String searchText) {
        List<String> mData = new ArrayList<>();
        for (String line : Utils.runAndGetOutput(Utils.magiskBusyBox() + "find " + Common.getModuleParent() + "/system -type f -name *.apk").split("\\r?\\n")) {
            if (line.endsWith(".apk")) {
                if (searchText == null) {
                    mData.add(line);
                } else if (Common.isTextMatched(line, searchText)) {
                    mData.add(line);
                }
            }
        }
        return mData;
    }

    private static boolean isPathInCategory(String apkPath, Set<String> categories) {
        return categories.stream().anyMatch(apkPath::startsWith);
    }

    private static boolean getSupportedAppsList(String apkPath, Context context) {
        int appType = sCommonUtils.getInt("appType", 0, context);

        return switch (appType) {
            case 1 -> isPathInCategory(apkPath, SYSTEM_PATHS);
            case 2 -> isPathInCategory(apkPath, PRODUCT_PATHS);
            case 3 -> isPathInCategory(apkPath, VENDOR_PATHS);
            case 4 -> isPathInCategory(apkPath, OVERLAY_PATHS);
            case 5 -> isPathInCategory(apkPath, MISC_PATHS);
            default -> true;
        };
    }

    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    private static String findSystemAPKPath(String packageName, Context context) {
        String souceDir = sPackageUtils.getSourceDir(packageName, context);
        if (souceDir.startsWith("/data/")) {
            try {
                String mAPKPath = null;
                for (String line : Utils.runAndGetOutput("dumpsys package " + packageName + " | grep resourcePath | grep -v /data/ | grep -v /apex/").replace("resourcePath=", "").split("\\r?\\n")) {
                    mAPKPath = line.replaceAll("\\s+", "");
                    for (File mFile : Objects.requireNonNull(new File(mAPKPath).listFiles())) {
                        if (Objects.equals(sAPKUtils.getPackageName(mFile.getAbsolutePath(), context), packageName)) {
                            mAPKPath = mAPKPath + File.separator + mFile.getName();
                        }
                    }
                }
                if (Utils.exist(mAPKPath)) return mAPKPath;
            } catch (NullPointerException ignored) {
            }
        }
        return souceDir;
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
    }

    public static boolean isModuleInitialized() {
        return Utils.exist(Common.getModuleParent()) && Utils.exist(Common.getModuleParent() + "/module.prop");
    }

    public static void setToDelete(String path, String name) {
        initializeModule();
        Utils.runCommand(Utils.magiskBusyBox() + " mkdir -p " + Common.getModuleParent() + new File(path).getParentFile());
        Utils.create(name, Common.getModuleParent() + path);
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