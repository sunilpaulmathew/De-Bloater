package com.sunilpaulmathew.debloater.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import in.sunilpaulmathew.sCommon.Utils.sExecutor;
import in.sunilpaulmathew.sCommon.Utils.sJSONUtils;
import in.sunilpaulmathew.sCommon.Utils.sPermissionUtils;
import in.sunilpaulmathew.sCommon.Utils.sUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 31, 2020
 */

public class UpdateCheck {

    private static boolean mManualUpdate = false;
    private static int mVersionCode = 0;
    private static JSONObject mJSONObject = null;
    private static String mChangeLog = null, mSHA1 = null, mReleaseURL = null, mVersionName = null;

    public UpdateCheck() {
    }

    private static boolean isManualUpdate() {
        return mManualUpdate;
    }

    /*
     * Based on the ApkSignatureVerifier.java in https://github.com/f-droid/fdroidclient
     * Ref: https://raw.githubusercontent.com/f-droid/fdroidclient/master/app/src/main/java/org/fdroid/fdroid/installer/ApkSignatureVerifier.java
     */
    public static boolean isSignatureMatched(Context context) {
        String mSmartPackKey = "[48, -126, 3, -69, 48, -126, 2, -93, -96, 3, 2, 1, 2, 2, 4, 83, 28, 18, 69, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 48, -127, -115, 49, 14, 48, 12, 6, 3, 85, 4, 6, 19, 5, 73, 110, 100, 105, 97, 49, 15, 48, 13, 6, 3, 85, 4, 8, 19, 6, 75, 101, 114, 97, 108, 97, 49, 14, 48, 12, 6, 3, 85, 4, 7, 19, 5, 75, 111, 99, 104, 105, 49, 18, 48, 16, 6, 3, 85, 4, 10, 19, 9, 83, 109, 97, 114, 116, 80, 97, 99, 107, 49, 33, 48, 31, 6, 3, 85, 4, 11, 19, 24, 83, 109, 97, 114, 116, 80, 97, 99, 107, 45, 75, 101, 114, 110, 101, 108, 32, 109, 97, 110, 97, 103, 101, 114, 49, 35, 48, 33, 6, 3, 85, 4, 3, 19, 26, 83, 117, 110, 105, 32, 80, 97, 117, 108, 32, 77, 97, 116, 104, 101, 119, 32, 77, 101, 110, 97, 99, 104, 101, 114, 121, 48, 30, 23, 13, 49, 55, 49, 48, 50, 54, 49, 49, 49, 57, 50, 48, 90, 23, 13, 52, 50, 49, 48, 50, 48, 49, 49, 49, 57, 50, 48, 90, 48, -127, -115, 49, 14, 48, 12, 6, 3, 85, 4, 6, 19, 5, 73, 110, 100, 105, 97, 49, 15, 48, 13, 6, 3, 85, 4, 8, 19, 6, 75, 101, 114, 97, 108, 97, 49, 14, 48, 12, 6, 3, 85, 4, 7, 19, 5, 75, 111, 99, 104, 105, 49, 18, 48, 16, 6, 3, 85, 4, 10, 19, 9, 83, 109, 97, 114, 116, 80, 97, 99, 107, 49, 33, 48, 31, 6, 3, 85, 4, 11, 19, 24, 83, 109, 97, 114, 116, 80, 97, 99, 107, 45, 75, 101, 114, 110, 101, 108, 32, 109, 97, 110, 97, 103, 101, 114, 49, 35, 48, 33, 6, 3, 85, 4, 3, 19, 26, 83, 117, 110, 105, 32, 80, 97, 117, 108, 32, 77, 97, 116, 104, 101, 119, 32, 77, 101, 110, 97, 99, 104, 101, 114, 121, 48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -93, -67, -12, 34, 23, -76, 100, -49, 117, 10, 42, 53, -19, 54, 110, -24, -109, 107, -128, -75, 80, 58, 56, 97, -65, 3, -98, -69, 111, 104, 23, 13, -40, 1, -42, 54, -1, 77, 125, 93, 85, 14, -118, -35, -71, 5, 123, -69, 23, -102, 9, -40, 52, -38, -24, -5, 85, 101, -112, -98, -71, 97, -84, -66, 76, 52, 86, 78, -55, -113, -40, 108, 110, 32, 106, -69, -107, 91, -18, -7, 59, -94, -37, -68, 97, 70, -5, 48, -22, -8, 113, 107, 96, -124, 127, 13, -61, -122, -45, -89, 3, -55, 41, -7, -89, -61, 11, -36, 9, -11, -111, -105, -5, -7, -115, 41, -67, 68, 55, 107, -19, 115, -92, -74, -116, -64, 11, -112, -75, -104, 95, 79, -106, -105, 16, 2, -79, 87, 70, 115, 73, -126, -15, 127, -92, 123, -83, -23, -107, -24, -36, -68, 6, 99, 107, -105, -1, 16, 99, 113, -82, 95, -47, 6, -95, -8, -18, -40, -104, 22, 21, 104, -26, -103, 97, 97, -19, -93, -103, -63, 61, 71, -103, -92, 95, -42, -118, 2, -99, 37, -15, -120, -84, 1, 69, -65, 6, -82, 70, -62, 86, 34, 19, -127, -109, -49, 89, 7, 46, 3, 123, -116, 127, -73, -77, 89, -22, -76, -63, 40, 123, -48, -124, -87, -93, -127, -68, 16, 43, 2, 59, 52, -63, 36, 111, 68, 119, 96, -10, 86, 7, 80, 35, 29, 28, -125, 95, 112, -101, 82, -117, 56, -45, -75, -97, 95, 2, 3, 1, 0, 1, -93, 33, 48, 31, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, 57, 125, -58, -16, 30, 48, -4, -79, -11, 127, -18, -102, 77, 126, 122, 56, 118, 83, -57, 70, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 3, -126, 1, 1, 0, -123, 24, -8, -90, 49, 117, 25, 86, -40, 42, 93, 126, -104, 89, -117, -76, -118, 92, -56, 111, 105, 65, -106, 102, -9, 110, -46, -67, 15, 0, 99, -10, 127, -113, 82, -65, 21, -71, -89, 104, 126, -113, 95, 25, -97, -12, -9, -64, -2, 83, -84, 100, -21, -84, -92, -97, 61, -17, 67, -98, -66, 79, -91, 12, -15, 106, 9, 11, -22, -112, -28, -60, -75, -73, -72, 69, -118, -5, -45, 10, -25, 86, -65, 113, 76, 112, 59, 39, -87, 5, 57, 117, 102, 82, -43, 72, 83, 50, 52, -26, 49, -120, 15, -58, 13, -91, 76, 114, 93, -36, -46, -64, 23, 85, -70, -97, 124, -75, 12, 71, -5, 44, -81, 80, 40, 50, 126, -21, -127, 127, 116, -120, 97, -55, -121, 37, -111, 102, 83, -17, 9, -108, -37, -99, 20, -3, -39, -74, -15, -91, 25, 98, 36, -116, 118, 22, 116, -114, 60, 15, 49, 105, 123, 94, 29, 114, 12, -20, -61, -62, -71, 104, 48, 91, 63, -110, -54, -18, 94, -45, -11, -51, -111, -123, -75, -8, 36, -58, 88, -15, -116, 12, 4, 95, 16, 92, 61, -22, 12, 56, 5, 37, 44, -47, 123, 104, -98, -111, 21, -114, -121, 127, -64, -58, -9, -93, 63, -116, 83, -31, 61, -99, 34, 6, 19, -94, 69, 47, -106, -61, -115, -47, -50, -97, -28, -39, -54, 41, -48, -125, 120, 93, 84, 109, -84, 9, -83, 110, 119, 68, 72, -76, -53, 63, -52, 75]";
        String mAppKey = Arrays.toString(Objects.requireNonNull(getSignature(context.getPackageName(), context)));
        return mSmartPackKey.equals(mAppKey);
    }

    private static boolean isUpdateAvailable() {
        return BuildConfig.VERSION_CODE < getVersionCode();
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

    private static int getVersionCode() {
        if (mJSONObject == null) return 0;
        return mVersionCode;
    }

    private static MaterialAlertDialogBuilder updateAvailableDialog(Activity activity) {
        return new MaterialAlertDialogBuilder(activity)
                .setTitle(activity.getString(R.string.update_available, getVersionName()))
                .setMessage(activity.getString(R.string.change_logs) + "\n" + getChangeLogs())
                .setCancelable(false)
                .setNegativeButton(activity.getString(R.string.cancel), (dialog, id) -> {
                })
                .setPositiveButton(activity.getString(R.string.get_it), (dialog, id) -> {
                    if (sPermissionUtils.isPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
                        sPermissionUtils.requestPermission(new String[] {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, activity);
                        sUtils.snackBar(activity.findViewById(android.R.id.content), activity.getString(R.string.storage_access_denied)).show();
                        return;
                    }
                    updaterTask(activity);
                });
    }

    private static String getChangeLogs() {
        if (mJSONObject == null) return "";
        return mChangeLog;
    }

    private static String getChecksum() {
        if (mJSONObject == null) return "";
        return mSHA1;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static String getVersionName() {
        if (mJSONObject == null) return "";
        return mVersionName;
    }

    private static String getUrl() {
        if (mJSONObject == null) return "";
        return mReleaseURL;
    }

    private static void getLatestApp(Context context) {
        if (sPermissionUtils.isPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, context)) {
            sPermissionUtils.requestPermission(new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, (Activity) context);
            return;
        }
        Utils.download(Common.geLatestAPK(context), getUrl());
    }

    public static void isManualUpdate(boolean b) {
        mManualUpdate = b;
    }

    private static void parseJSON(int updateCheckInterval, Activity activity) {
        new sExecutor() {

            private long ucTimeStamp;
            private int interval;
            @Override
            public void onPreExecute() {
                ucTimeStamp = PreferenceManager.getDefaultSharedPreferences(activity).getLong("ucTimeStamp", 0);
                interval = updateCheckInterval * 60 * 60 * 1000;
            }

            @Override
            public void doInBackground() {
                if (System.currentTimeMillis() > ucTimeStamp + interval) {
                    try (InputStream is = new URL(Common.getLatestVersionUrl()).openStream()) {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        String jsonText = readAll(rd);
                        mJSONObject = new JSONObject(jsonText);
                        mChangeLog = sJSONUtils.getString(mJSONObject,"releaseNotes");
                        mReleaseURL = sJSONUtils.getString(mJSONObject,"releaseUrl");
                        mSHA1 = sJSONUtils.getString(mJSONObject,"sha1");
                        mVersionCode = sJSONUtils.getInt(mJSONObject,"latestVersionCode");
                        mVersionName = sJSONUtils.getString(mJSONObject,"latestVersion");
                    } catch (JSONException | IOException ignored) {
                    }
                }
            }

            @Override
            public void onPostExecute() {
                if (isManualUpdate()) {
                    if (mJSONObject == null) {
                        sUtils.snackBar(activity.findViewById(android.R.id.content), activity.getString(R.string.no_internet)).show();
                        return;
                    }
                    if (isUpdateAvailable()) {
                        updateAvailableDialog(activity).show();
                    } else {
                        new MaterialAlertDialogBuilder(activity)
                                .setMessage(R.string.updated_dialog)
                                .setPositiveButton(activity.getString(R.string.cancel), (dialog, id) -> {
                                }).show();
                    }
                } else {
                    if (mJSONObject == null) {
                        return;
                    }
                    if (isUpdateAvailable()) {
                        updateAvailableDialog(activity).show();
                    }
                }

                PreferenceManager.getDefaultSharedPreferences(activity).edit().putLong("ucTimeStamp", System
                        .currentTimeMillis()).apply();
            }
        }.execute();
    }

    private static void updaterTask(Context context) {
        new sExecutor() {
            private String mSid = null;

            private ProgressDialog mProgressDialog;
            @Override
            public void onPreExecute() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage(context.getString(R.string.downloading, getVersionName() + "..."));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void doInBackground() {
                getLatestApp(context);
                mProgressDialog.setMessage("Installing De-Bloater " + getVersionName() + "...");
                Utils.runCommand("sleep 5");
                if (Utils.exist(Common.geLatestAPK(context)) && Utils.getChecksum(Common.geLatestAPK(context))
                        .contains(Objects.requireNonNull(getChecksum()))) {
                    mSid = Utils.runAndGetOutput("pm install-create").replace(
                            "Success: created install session [","").replace("]", "");
                    File mAPK = new File(Common.geLatestAPK(context));
                    Utils.runCommand("pm install-write -S " + mAPK.length() + " " + mSid + " " + mAPK.getName() + " " + mAPK);
                }
            }

            @Override
            public void onPostExecute() {
                try {
                    mProgressDialog.dismiss();
                } catch (IllegalArgumentException ignored) {}
                if (Utils.exist(Common.geLatestAPK(context)) && Utils.getChecksum(Common.geLatestAPK(context))
                        .contains(Objects.requireNonNull(getChecksum()))) {
                    Utils.runCommand("pm install-commit " + mSid);
                } else {
                    new MaterialAlertDialogBuilder(context)
                            .setMessage(context.getString(R.string.download_failed))
                            .setNegativeButton(context.getString(R.string.cancel), (dialog, id) -> {
                            }).show();
                }
            }
        }.execute();
    }

    public void initialize(int updateCheckInterval, Activity activity) {
        parseJSON(updateCheckInterval, activity);
    }

}