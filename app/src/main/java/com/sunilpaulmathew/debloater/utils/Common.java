package com.sunilpaulmathew.debloater.utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on June 10, 2021
 */

import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.sunilpaulmathew.debloater.R;

import java.util.List;

public class Common {

    private static List<PackageItem> mRawData;

    public static boolean isTextMatched(String searchText, String searchWord) {
        for (int a = 0; a < searchText.length() - searchWord.length() + 1; a++) {
            if (searchWord.equalsIgnoreCase(searchText.substring(a, a + searchWord.length()))) {
                return true;
            }
        }
        return false;
    }

    public static int getPriorityIcon(String label) {
        return switch (label) {
            case "Recommended" -> R.drawable.ic_delete;
            case "Advanced", "System" -> R.drawable.ic_settings_round;
            default -> R.drawable.ic_warning;
        };
    }

    public static int getPriorityColor(String label) {
        return switch (label) {
            case "Recommended" -> R.color.removal_recommended;
            case "Advanced" -> R.color.removal_advanced;
            case "Expert" -> R.color.removal_expert;
            default -> R.color.removal_unsafe;
        };
    }

    public static int getPriorityTextColor(String label) {
        return switch (label) {
            case "Recommended", "Expert", "Advanced" -> R.color.colorBlack;
            default -> R.color.colorWhite;
        };
    }

    public static String getPriorityText(String label) {
        return switch (label) {
            case "Recommended" -> "Safe to remove";
            case "Advanced" -> "Likely safe";
            case "Expert" -> "Likely dangerous";
            default -> "Dangerous to remove";
        };
    }

    public static List<PackageItem> getRawData() {
        return mRawData;
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

    public static void setRawData(List<PackageItem> rawData) {
        mRawData = rawData;
    }

    public static void setSlideInAnimation(final View viewToAnimate, int position) {
        // Only animate items appearing for the first time
        if (position > -1) {
            viewToAnimate.setTranslationY(50f);
            viewToAnimate.setAlpha(0f);

            viewToAnimate.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setDuration(150)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        } else {
            // Reset properties to ensure recycled views are displayed correctly
            viewToAnimate.setTranslationY(0f);
            viewToAnimate.setAlpha(1f);
        }
    }

}