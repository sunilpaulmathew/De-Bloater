package com.sunilpaulmathew.debloater.utils;

import android.content.Context;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 17, 2020
 */

public class Restore {

    private static JSONArray getAppList(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject main = new JSONObject(json);
                return main.getJSONArray("DeBloater");
            } catch (JSONException ignored) {
            }
        }
        return null;
    }

    private static JSONObject getDeviceInfo(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject baseJSON = new JSONObject(json);
                return new JSONObject(baseJSON.getString("Device"));
            } catch (JSONException ignored) {
            }
        }
        return null;
    }

    private static String getName(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getString("name");
        } catch (JSONException ignored) {
        }
        return null;
    }

    private static String getModel(String string) {
        if (getDeviceInfo(string) == null) return null;
        try {
            return getDeviceInfo(string).getString("Model");
        } catch (JSONException ignored) {
        }
        return null;
    }

    private static int getSDK(String string) {
        if (getDeviceInfo(string) == null) return 0;
        try {
            return getDeviceInfo(string).getInt("SDK");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private static String getPath(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getString("path");
        } catch (JSONException ignored) {
        }
        return null;
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