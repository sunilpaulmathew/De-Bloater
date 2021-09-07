package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.snackbar.Snackbar;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.topjohnwu.superuser.Shell;
import com.topjohnwu.superuser.ShellUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class Utils {

    static {
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
    }

    public static boolean isPackageInstalled(String packageID, Context context) {
        try {
            context.getPackageManager().getApplicationInfo(packageID, 0);
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }
    }

    public static boolean isPlayStoreAvailable(Context context) {
        return isPackageInstalled("com.android.vending", context);
    }

    public static boolean isFDroidAvailable(Context context) {
        return isPackageInstalled("org.fdroid.fdroid", context);
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

    public static boolean isDarkTheme(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static void initializeAppTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private static int getOrientation(Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity.isInMultiWindowMode() ?
                Configuration.ORIENTATION_PORTRAIT : activity.getResources().getConfiguration().orientation;
    }

    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getSpanCount(Activity activity) {
        return isTablet(activity) ? getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ?
                4 : 3 : getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
    }

    public static void launchUrl(String url, Activity activity) {
        if (url == null) return;
        if (isNetworkUnavailable(activity)) {
            snackBar(activity.findViewById(android.R.id.content), activity.getString(R.string.no_internet));
        } else {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    static boolean isNetworkUnavailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return (cm.getActiveNetworkInfo() == null) || !cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static boolean isPermissionDenied(Context context) {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        return (context.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
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

    @NonNull
    private static String runAndGetError(String command) {
        StringBuilder sb = new StringBuilder();
        List<String> outputs = new ArrayList<>();
        List<String> stderr = new ArrayList<>();
        try {
            Shell.su(command).to(outputs, stderr).exec();
            outputs.addAll(stderr);
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

    public static boolean getBoolean(String name, boolean defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(name, defaults);
    }

    public static void saveBoolean(String name, boolean value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(name, value).apply();
    }

    public static String getString(String name, String defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(name, defaults);
    }

    public static void saveString(String name, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(name, value).apply();
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

    public static void snackBar(View view, String message) {
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackBar.setAction(R.string.dismiss, v -> snackBar.dismiss());
        snackBar.show();
    }

    public static CharSequence fromHtml(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static String readAssetFile(Context context, String file) {
        InputStream input = null;
        BufferedReader buf = null;
        try {
            StringBuilder s = new StringBuilder();
            input = context.getAssets().open(file);
            buf = new BufferedReader(new InputStreamReader(input));

            String str;
            while ((str = buf.readLine()) != null) {
                s.append(str).append("\n");
            }
            return s.toString().trim();
        } catch (IOException ignored) {
        } finally {
            try {
                if (input != null) input.close();
                if (buf != null) buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}