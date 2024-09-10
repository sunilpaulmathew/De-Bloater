package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.Html;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.topjohnwu.superuser.Shell;
import com.topjohnwu.superuser.ShellUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.PackageUtils.sPackageUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class Utils {

    static {
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
    }

    public static String getAppStoreURL(Context context) {
        if (sPackageUtils.isPackageInstalled("com.android.vending", context)) {
            return " Google Play: https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater";
        } else if (sPackageUtils.isPackageInstalled("org.fdroid.fdroid", context)) {
            return " F-Droid: https://f-droid.org/packages/com.sunilpaulmathew.debloater/";
        } else {
            return " GitHub: https://github.com/sunilpaulmathew/De-Bloater/releases/latest";
        }
    }

    public static int getSpanCount(Activity activity) {
        return sCommonUtils.isTablet(activity) ? sCommonUtils.getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ?
                4 : 3 : sCommonUtils.getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
    }

    public static boolean rootAccess() {
        return Shell.rootAccess();
    }

    public static void runCommand(String command) {
        Shell.cmd(command).exec();
    }

    @NonNull
    static String runAndGetOutput(String command) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> outputs = Shell.cmd(command).exec().getOut();
            if (ShellUtils.isValidOutput(outputs)) {
                for (String output : outputs) {
                    sb.append(output).append("\n");
                }
            }
            return removeSuffix(sb.toString()).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean magiskSupported() {
        return Utils.exist("/sbin/.magisk") || Utils.exist("/data/adb/magisk") || isKSUSupported();
    }

    public static int getThemeAccentColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }

    public static int getPrimaryTextColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    private static String removeSuffix(@Nullable String s) {
        if (s != null && s.endsWith("\n")) {
            return s.substring(0, s.length() - "\n".length());
        }
        return s;
    }

    public static boolean exist(String file) {
        String output = runAndGetOutput("[ -e " + file + " ] && echo true");
        return output.equals("true");
    }

    public static boolean isKSUSupported() {
        return Utils.exist("/data/adb/ksu/bin/busybox");
    }

    public static void delete(String path) {
        runCommand(magiskBusyBox() + "rm -r " + path);
    }

    public static void create(String text, String path) {
        Utils.runCommand(magiskBusyBox() + "echo '" + text + "' > " + path);
    }

    public static String read(String path) {
        return Utils.runAndGetOutput("cat " + path);
    }

    static void download(String path, String url) {
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(path)) {
            byte[] data = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
        } catch (Exception ignored) {
        }
    }

    static void chmod(String permission, String path) {
        runCommand(magiskBusyBox() + "chmod " + permission + " " + path);
    }

    public static String getChecksum(String path) {
        return runAndGetOutput("sha1sum " + path);
    }

    public static String magiskBusyBox() {
        if (Utils.exist("/data/adb/magisk/busybox")) {
            return "/data/adb/magisk/busybox ";
        } else if (isKSUSupported()) {
            return "/data/adb/ksu/bin/busybox ";
        } else {
            return "";
        }
    }

    public static CharSequence fromHtml(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

}