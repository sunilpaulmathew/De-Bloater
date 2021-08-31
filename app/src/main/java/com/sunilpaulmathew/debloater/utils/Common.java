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
    private static List<RecycleViewItem> mRawData;
    private static final List<RecycleViewItem> mAOSP = new ArrayList<>(), mAsus = new ArrayList<>(),
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

    public static List<RecycleViewItem> getAOSP() {
        return mAOSP;
    }

    public static List<RecycleViewItem> getAsus() {
        return mAsus;
    }

    public static List<RecycleViewItem> getCarrier() {
        return mCarrier;
    }

    public static List<RecycleViewItem> getGoogle() {
        return mGoogle;
    }

    public static List<RecycleViewItem> getHuawei() {
        return mHuawei;
    }

    public static List<RecycleViewItem> getLG() {
        return mLG;
    }

    public static List<RecycleViewItem> getMisc() {
        return mMisc;
    }

    public static List<RecycleViewItem> getMoto() {
        return mMoto;
    }

    public static List<RecycleViewItem> getNokia() {
        return mNokia;
    }

    public static List<RecycleViewItem> getOppo() {
        return mOppo;
    }

    public static List<RecycleViewItem> getOnePlus() {
        return mOnePlus;
    }

    public static List<RecycleViewItem> getRawData() {
        return mRawData;
    }

    public static List<RecycleViewItem> getSamsung() {
        return mSamsung;
    }

    public static List<RecycleViewItem> getSony() {
        return mSony;
    }

    public static List<RecycleViewItem> geTInvisible() {
        return mTInvisible;
    }

    public static List<RecycleViewItem> getTLight() {
        return mTLight;
    }

    public static List<RecycleViewItem> getTExtreme() {
        return mTExtreme;
    }

    public static List<RecycleViewItem> getXiaomi() {
        return mXiaomi;
    }

    public static List<RecycleViewItem> getZTE() {
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

    public static void setRawData(List<RecycleViewItem> rawData) {
        mRawData = rawData;
    }

    public static void setSearchText(String searchText) {
        mSearchText = searchText;
    }

}