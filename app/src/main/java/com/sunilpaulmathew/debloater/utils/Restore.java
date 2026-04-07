package com.sunilpaulmathew.debloater.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.sunilpaulmathew.sCommon.JsonUtils.sJSONUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 17, 2020
 */

public class Restore {

    private static JSONArray getAppList(String json) {
        return sJSONUtils.getJSONArray(sJSONUtils.getJSONObject(json), "DeBloater");
    }

    private static JSONObject getDeviceInfo(String json) {
        return sJSONUtils.getJSONObject(sJSONUtils.getString(sJSONUtils.getJSONObject(json), "Device"));
    }

    private static String getName(String string) {
        return sJSONUtils.getString(sJSONUtils.getJSONObject(string), "name");
    }

    private static String getModel(String string) {
        return sJSONUtils.getString(getDeviceInfo(string), "Model");
    }

    private static int getSDK(String string) {
        return sJSONUtils.getInt(getDeviceInfo(string), "SDK");
    }

    private static String getPath(String string) {
        return sJSONUtils.getString(sJSONUtils.getJSONObject(string), "path");
    }

    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = Objects.requireNonNull(result).lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static boolean isValidBackup(String jsonString) {
        return getAppList(jsonString) != null;
    }

    public static boolean isJSONMatched(String jsonString) {
        if (getDeviceInfo(jsonString) == null) return true;
        return Objects.equals(getModel(jsonString), Build.MODEL) && getSDK(jsonString) == Build.VERSION.SDK_INT;
    }

    public static void restoreBackup(String jsonString) {
        List<String> mRestoreData = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(getAppList(jsonString)).length(); i++) {
            try {
                mRestoreData.add(Objects.requireNonNull(getAppList(jsonString)).getJSONObject(i).toString());
            } catch (JSONException ignored) {
            }
        }
        for (String s : mRestoreData) {
            if (Utils.exist(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""))) {
                PackageTasks.setToDelete(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""), getName(s));
            }
        }
    }

}