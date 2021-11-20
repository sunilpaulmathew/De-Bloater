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
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import in.sunilpaulmathew.sCommon.Utils.sPackageUtils;
import in.sunilpaulmathew.sCommon.Utils.sUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class Utils {

    static {
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
    }

    public static boolean isPlayStoreAvailable(Context context) {
        return sPackageUtils.isPackageInstalled("com.android.vending", context);
    }

    public static boolean isFDroidAvailable(Context context) {
        return sPackageUtils.isPackageInstalled("org.fdroid.fdroid", context);
    }

    public static String getAppStoreURL(Context context) {
        if (isPlayStoreAvailable(context)) {
            return " Google Play: https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater";
        } else if (isFDroidAvailable(context)) {
            return " F-Droid: https://f-droid.org/packages/com.sunilpaulmathew.debloater/";
        } else {
            return " GitHub: https://github.com/sunilpaulmathew/De-Bloater/releases/latest";
        }
    }

    public static int getSpanCount(Activity activity) {
        return sUtils.isTablet(activity) ? sUtils.getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ?
                4 : 3 : sUtils.getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
    }

    public static boolean rootAccess() {
        return Shell.rootAccess();
    }

    public static void runCommand(String command) {
        Shell.su(command).exec();
    }

    @NonNull
    static String runAndGetOutput(String command) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> outputs = Shell.su(command).exec().getOut();
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
        return Utils.exist("/sbin/.magisk") || Utils.exist("/data/adb/magisk");
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
        return !output.isEmpty() && output.equals("true");
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
             OutputStream output = new FileOutputStream(path)) {
            byte[] data = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
        } catch (Exception ignored) {
        }
    }

    public static void copy(String source, String dest) {
        runCommand(magiskBusyBox() + "cp -rf " + source + " " + dest);
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