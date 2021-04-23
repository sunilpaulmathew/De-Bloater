package com.sunilpaulmathew.debloater.utils;

import android.content.Context;

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

    public static JSONArray getAppList(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject main = new JSONObject(json);
                return main.getJSONArray("DeBloater");
            } catch (JSONException ignored) {
            }
        }
        return null;
    }

    public static String getName(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getString("name");
        } catch (JSONException ignored) {
        }
        return null;
    }

    public static String getPath(String string) {
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
        String[] apps = mRestoreData.toArray(new String[0]);
        for (String s : apps) {
            if (Utils.exist(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""))) {
                PackageTasks.setToDelete(Objects.requireNonNull(getPath(s)).replace("/data/adb/modules/De-bloater",""), getName(s), context);
            }
        }
    }

}