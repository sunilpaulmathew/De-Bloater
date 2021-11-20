package com.sunilpaulmathew.debloater.utils;

import android.content.Context;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.sunilpaulmathew.sCommon.Utils.sJSONUtils;

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
        return sJSONUtils.getString(sJSONUtils.getJSONObject(string), "Model");
    }

    private static int getSDK(String string) {
        return sJSONUtils.getInt(sJSONUtils.getJSONObject(string), "SDK");
    }

    private static String getPath(String string) {
        return sJSONUtils.getString(sJSONUtils.getJSONObject(string), "path");
    }

    public static boolean isValidBackup(String path) {
        return getAppList(Utils.read(path)) != null;
    }

    public static boolean isJSONMatched(String path) {
        if (getDeviceInfo(Utils.read(path)) == null) return true;
        return Objects.equals(getModel(Utils.read(path)), Build.MODEL) && getSDK(Utils.read(path)) == Build.VERSION.SDK_INT;
    }

    public static void restoreBackup(String path, Context context) {
        List<String> mRestoreData = new ArrayList<>();
        if (Utils.exist(path)) {
            for (int i = 0; i < Objects.requireNonNull(getAppList(Utils.read(path))).length(); i++) {
                try {
                    mRestoreData.add(Objects.requireNonNull(getAppList(Utils.read(path))).getJSONObject(i).toString());
                } catch (JSONException ignored) {
                }
            }
        }
        for (String s : mRestoreData) {
            if (Utils.exist(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""))) {
                PackageTasks.setToDelete(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""), getName(s), context);
            }
        }
    }

}