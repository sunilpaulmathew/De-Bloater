package com.sunilpaulmathew.debloater.utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on June 10, 2021
 */

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class Common {

    private static AppCompatEditText mSearchWord;
    private static AppCompatImageButton mSearchButton;
    private static AppCompatTextView mAbout;
    private static List<PackageItem> mRawData;
    private static final List<PackageItem> mAOSP = new ArrayList<>(), mAsus = new ArrayList<>(),
            mCarrier = new ArrayList<>(), mGoogle = new ArrayList<>(), mHuawei = new ArrayList<>(),
            mLG = new ArrayList<>(), mSamsung = new ArrayList<>(), mMisc = new ArrayList<>(),
            mMoto = new ArrayList<>(), mNokia = new ArrayList<>(), mOnePlus = new ArrayList<>(),
            mOppo = new ArrayList<>(), mSony = new ArrayList<>(), mTInvisible = new ArrayList<>(),
            mTLight = new ArrayList<>(), mTExtreme = new ArrayList<>(), mXiaomi = new ArrayList<>(),
            mZTE = new ArrayList<>();
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

    public static boolean isTextMatched(String searchText) {
        for (int a = 0; a < searchText.length() - mSearchText.length() + 1; a++) {
            if (mSearchText.equalsIgnoreCase(searchText.substring(a, a + mSearchText.length()))) {
                return true;
            }
        }
        return false;
    }

    public static List<PackageItem> getAOSP() {
        return mAOSP;
    }

    public static List<PackageItem> getAsus() {
        return mAsus;
    }

    public static List<PackageItem> getCarrier() {
        return mCarrier;
    }

    public static List<PackageItem> getGoogle() {
        return mGoogle;
    }

    public static List<PackageItem> getHuawei() {
        return mHuawei;
    }

    public static List<PackageItem> getLG() {
        return mLG;
    }

    public static List<PackageItem> getMisc() {
        return mMisc;
    }

    public static List<PackageItem> getMoto() {
        return mMoto;
    }

    public static List<PackageItem> getNokia() {
        return mNokia;
    }

    public static List<PackageItem> getOppo() {
        return mOppo;
    }

    public static List<PackageItem> getOnePlus() {
        return mOnePlus;
    }

    public static List<PackageItem> getRawData() {
        return mRawData;
    }

    public static List<PackageItem> getSamsung() {
        return mSamsung;
    }

    public static List<PackageItem> getSony() {
        return mSony;
    }

    public static List<PackageItem> geTInvisible() {
        return mTInvisible;
    }

    public static List<PackageItem> getTLight() {
        return mTLight;
    }

    public static List<PackageItem> getTExtreme() {
        return mTExtreme;
    }

    public static List<PackageItem> getXiaomi() {
        return mXiaomi;
    }

    public static List<PackageItem> getZTE() {
        return mZTE;
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

    public static void setRawData(List<PackageItem> rawData) {
        mRawData = rawData;
    }

    public static void setSearchText(String searchText) {
        mSearchText = searchText;
    }

}