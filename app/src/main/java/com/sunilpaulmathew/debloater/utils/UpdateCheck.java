package com.sunilpaulmathew.debloater.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 31, 2020
 */

public class UpdateCheck {

    private static final String LATEST_VERSION_URL = "https://raw.githubusercontent.com/sunilpaulmathew/De-Bloater/master/app/src/main/assets/release.json";
    private static final String LATEST_APK = Environment.getExternalStorageDirectory().toString() + "/app-release.apk";

    private static void getVersionInfo(Context context) {
        if (Utils.isPermissionDenied(context)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return;
        }
        Utils.download(releaseInfo(context), LATEST_VERSION_URL);
    }

    private static void getLatestApp(Context context) {
        if (Utils.isPermissionDenied(context)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return;
        }
        Utils.download(LATEST_APK, getLatestApk(context));
    }

    private static String versionName(Context context) {
        try {
            JSONObject obj = new JSONObject(getReleaseInfo(context));
            return (obj.getString("latestVersion"));
        } catch (JSONException e) {
            return BuildConfig.VERSION_NAME;
        }
    }

    private static int versionCode(Context context) {
        try {
            JSONObject obj = new JSONObject(getReleaseInfo(context));
            return (obj.getInt("latestVersionCode"));
        } catch (JSONException e) {
            return BuildConfig.VERSION_CODE;
        }
    }

    private static String getLatestApk(Context context) {
        try {
            JSONObject obj = new JSONObject(getReleaseInfo(context));
            return (obj.getString("releaseUrl"));
        } catch (JSONException e) {
            return null;
        }
    }

    private static String getChecksum(Context context) {
        try {
            JSONObject obj = new JSONObject(getReleaseInfo(context));
            return (obj.getString("sha1"));
        } catch (JSONException e) {
            return null;
        }
    }

    private static String changelogs(Context context) {
        try {
            JSONObject obj = new JSONObject(getReleaseInfo(context));
            return (obj.getString("releaseNotes"));
        } catch (JSONException e) {
            return null;
        }
    }

    private static String releaseInfo(Context context) {
        return context.getFilesDir().getPath() + "/release";
    }

    private static String getReleaseInfo(Context context) {
        return Utils.read(releaseInfo(context));
    }

    private static boolean hasVersionInfo(Context context) {
        return Utils.exist(releaseInfo(context));
    }

    private static long lastModified(Context context) {
        return new File(releaseInfo(context)).lastModified();
    }

    private static void updateAvailableDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.update_available, versionName(context)))
                .setMessage(context.getString(R.string.change_logs) + "\n" + changelogs(context))
                .setCancelable(false)
                .setNegativeButton(context.getString(R.string.cancel), (dialog, id) -> {
                })
                .setPositiveButton(context.getString(R.string.get_it), (dialog, id) -> {
                    updaterTask(context);
                }).show();
    }

    private static void updaterTask(Context context) {
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog mProgressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage(context.getString(R.string.downloading, versionName(context) + "..."));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                getLatestApp(context);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    mProgressDialog.dismiss();
                } catch (IllegalArgumentException ignored) {}
                if (Utils.exist(LATEST_APK) && Utils.getChecksum(LATEST_APK).contains(Objects.requireNonNull(getChecksum(context)))) {
                    installUpdate(context);
                } else {
                    new MaterialAlertDialogBuilder(context)
                            .setMessage(context.getString(R.string.download_failed))
                            .setNegativeButton(context.getString(R.string.cancel), (dialog, id) -> {
                            }).show();
                }
            }
        }.execute();
    }

    public static void autoUpdateCheck(Context context) {
        if (Utils.isPermissionDenied(context)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return;
        }
        if (Utils.isNetworkUnavailable(context)) {
            return;
        }
        if (!Utils.isDownloadBinaries()) {
            return;
        }
        if (!hasVersionInfo(context) || (lastModified(context) + 3720000L < System.currentTimeMillis())) {
            getVersionInfo(context);
        }
        if (hasVersionInfo(context) && BuildConfig.VERSION_CODE < versionCode(context)) {
            updateAvailableDialog(context);
        }
    }

    public static void manualUpdateCheck(View view, Context context) {
        if (Utils.isPermissionDenied(context)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            Utils.snackBar(view, context.getString(R.string.storage_access_denied));
            return;
        }
        if (Utils.isNetworkUnavailable(context)) {
            Utils.snackBar(view, context.getString(R.string.no_internet));
            return;
        }
        getVersionInfo(context);
        if (hasVersionInfo(context) && BuildConfig.VERSION_CODE < versionCode(context)) {
            updateAvailableDialog(context);
        } else {
            new MaterialAlertDialogBuilder(context)
                    .setMessage(R.string.updated_dialog)
                    .setPositiveButton(context.getString(R.string.cancel), (dialog, id) -> {
                    }).show();
        }
    }

    private static void installUpdate(Context context) {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uriFile;
        uriFile = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",
                new File(LATEST_APK));
        intent.setDataAndType(uriFile, "application/vnd.android.package-archive");
        context.startActivity(Intent.createChooser(intent, ""));
    }

    /*
     * Based on the ApkSignatureVerifier.java in https://github.com/f-droid/fdroidclient
     * Ref: https://raw.githubusercontent.com/f-droid/fdroidclient/master/app/src/main/java/org/fdroid/fdroid/installer/ApkSignatureVerifier.java
     */
    public static boolean isSignatureMatched(Context context) {
        try {
            return !Arrays.equals(getSignature(context.getPackageName(), context), getSignature("org.fdroid.fdroid", context));
        } catch (NullPointerException ignored) {
        }
        return false;
    }

    @SuppressLint("PackageManagerGetSignatures")
    private static byte[] getSignature(String packageid, Context context) {
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(packageid, PackageManager.GET_SIGNATURES);
            return signatureToBytes(pkgInfo.signatures);
        } catch (PackageManager.NameNotFoundException ignored) {}
        return null;
    }

    private static byte[] signatureToBytes(Signature[] signatures) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Signature sig : signatures) {
            try {
                outputStream.write(sig.toByteArray());
            } catch (IOException ignored) {}
        }
        return outputStream.toByteArray();
    }

}