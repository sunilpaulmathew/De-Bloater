package com.sunilpaulmathew.debloater.utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on June 10, 2021
 */

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

public class Common {

    private static AppCompatEditText mSearchWord;
    private static AppCompatImageButton mSearchButton;
    private static AppCompatTextView mAbout;
    private static String mSearchText;

    public static AppCompatEditText getSearchWord() {
        return mSearchWord;
    }

    public static AppCompatImageButton getSearchButton() {
        return mSearchButton;
    }

    public static AppCompatTextView getAboutSummary() {
        return mAbout;
    }

    public static String getModuleParent() {
        return "/data/adb/modules/De-bloater";
    }

    public static String geLatestAPK(Context context) {
        return context.getExternalFilesDir("") + "/app-release.apk";
    }

    public static String getLatestVersionUrl() {
        return "https://raw.githubusercontent.com/sunilpaulmathew/De-Bloater/master/app/src/main/assets/release.json";
    }

    public static String getSearchText() {
        return mSearchText;
    }

    public static void initializeSearchButton(View view, int id) {
        mSearchButton = view.findViewById(id);
    }

    public static void initializeSearchWord(View view, int id) {
        mSearchWord = view.findViewById(id);
    }

    public static void initializeAboutSummary(View view, int id) {
        mAbout = view.findViewById(id);
    }

    public static void setSearchText(String searchText) {
        mSearchText = searchText;
    }

}