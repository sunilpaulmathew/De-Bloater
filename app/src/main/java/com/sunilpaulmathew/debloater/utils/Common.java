package com.sunilpaulmathew.debloater.utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on June 10, 2021
 */

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sSerializableItems;

public class Common {

    private static AppCompatEditText mSearchWordActive, mSearchWordInactive;
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

    public static AppCompatEditText getActiveSearchWord() {
        return mSearchWordActive;
    }

    public static AppCompatEditText getInactiveSearchWord() {
        return mSearchWordInactive;
    }

    public static AppCompatTextView getAboutSummary() {
        return mAbout;
    }

    public static boolean isTextMatched(String searchText) {
        if (searchText != null) {
            for (int a = 0; a < searchText.length() - mSearchText.length() + 1; a++) {
                if (mSearchText.equalsIgnoreCase(searchText.substring(a, a + mSearchText.length()))) {
                    return true;
                }
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

    public static List<sSerializableItems> getCredits() {
        List<sSerializableItems> mData = new ArrayList<>();
        mData.add(new sSerializableItems(null, "Willi Ye", "Kernel Adiutor", "https://github.com/Grarak/KernelAdiutor"));
        mData.add(new sSerializableItems(null, "John Wu", "libsu & Magisk", "https://github.com/topjohnwu"));
        mData.add(new sSerializableItems(null, "weishu", "KernelSU", "https://github.com/tiann"));
        mData.add(new sSerializableItems(null, "Nikita", "Russian & Ukrainian Translations", "https://t.me/MONSTER_PC"));
        mData.add(new sSerializableItems(null, "Emre", "Turkish Translations", "https://t.me/xcooLwastaken"));
        mData.add(new sSerializableItems(null, "Firerust96", "Spanish Translations", "https://github.com/Firerust96"));
        mData.add(new sSerializableItems(null, "lay4play", "Italian Translations", null));
        mData.add(new sSerializableItems(null, "Axel Schaab", "German Translations", null));
        mData.add(new sSerializableItems(null, "alex", "Polish Translations", null));
        mData.add(new sSerializableItems(null, "Ktosspl", "Polish Translations", null));
        mData.add(new sSerializableItems(null, "Valdnet", "Polish Translations", "https://github.com/Valdnet"));
        mData.add(new sSerializableItems(null, "Reno", "French Translations", null));
        mData.add(new sSerializableItems(null, "Ebolateam", "French Translations", null));
        mData.add(new sSerializableItems(null, "Hoa Gia Đại Thiếu", "Vietnamese Translations", null));
        mData.add(new sSerializableItems(null, "ひきたり", "Vietnamese Translations", null));
        mData.add(new sSerializableItems(null, "qiaoxin", "Chinese - Hong Kong (Traditional and Simplified) Translations", null));
        mData.add(new sSerializableItems(null, "MMETMA", "Arabic Translations", "https://github.com/MMETMA"));
        mData.add(new sSerializableItems(null, "Guima Teixeira", "French (Belgian)", null));
        mData.add(new sSerializableItems(null, "蔡承佑", "Chinese (Traditional) Translations", null));
        mData.add(new sSerializableItems(null, "Geovanni", "Portuguese (Brazilian) Translations", null));
        mData.add(new sSerializableItems(null, "Chong", "Chinese (Simplified) Translations", null));
        mData.add(new sSerializableItems(null, "Lw201811", "Japanese & Chinese (Simplified) Translations", null));
        mData.add(new sSerializableItems(null, "Hongle", "Chinese - Hong Kong (Traditional and Simplified) Translations", null));
        return mData;
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

    public static void initializeActiveSearchWord(View view, int id) {
        mSearchWordActive = view.findViewById(id);
    }

    public static void initializeInactiveSearchWord(View view, int id) {
        mSearchWordInactive = view.findViewById(id);
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