package com.sunilpaulmathew.debloater.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.sunilpaulmathew.debloater.R;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 4, 2020
 */

public class TomatotDebloaterFragment extends Fragment {

    private AppCompatTextView mAppsList;
    private boolean mDisabledT = false, mExtremeT = false, mInvisibleT = false, mLightT = false;
    private CardView mAppListCard;
    private LinearLayout mProgressLayout;

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_tomatot_debloater, container, false);
        LinearLayout mTitleLayout = mRootView.findViewById(R.id.title_layout);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        AppCompatTextView mDisabled = mRootView.findViewById(R.id.disabled);
        AppCompatTextView mInvisible = mRootView.findViewById(R.id.invisible);
        AppCompatTextView mLight = mRootView.findViewById(R.id.light);
        AppCompatTextView mExtreme = mRootView.findViewById(R.id.extreme);
        AppCompatTextView mStatus = mRootView.findViewById(R.id.deblaoter_status);
        AppCompatTextView mActionMessage = mRootView.findViewById(R.id.action_message);
        mAppsList = mRootView.findViewById(R.id.apps_list);
        AppCompatTextView mAppsListTitle = mRootView.findViewById(R.id.apps_list_title);
        AppCompatImageButton mActionIcon = mRootView.findViewById(R.id.action_icon);
        mAppListCard = mRootView.findViewById(R.id.apps_list_card);
        FrameLayout mActionLayout = mRootView.findViewById(R.id.action_layout);
        if (Utils.isDarkTheme(requireActivity())) {
            mActionMessage.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mActionIcon.setColorFilter(Utils.getThemeAccentColor(requireActivity()));
            mAppsListTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
        }

        if (Utils.getBoolean("tomatot_extreme", false, requireActivity())) {
            mAppsList.setText(CustomScripts.getExtremeList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (Utils.getBoolean("tomatot_invisible", false, requireActivity())) {
            mAppsList.setText(CustomScripts.getInvisibletList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (Utils.getBoolean("tomatot_light", false, requireActivity())) {
            mAppsList.setText(CustomScripts.getLightList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        } else {
            mAppListCard.setVisibility(View.GONE);
        }

        if (Utils.getBoolean("tomatot_extreme", false, requireActivity())) {
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_extreme);
        } else if (Utils.getBoolean("tomatot_light", false, requireActivity())) {
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_light);
        } else if (Utils.getBoolean("tomatot_invisible", false, requireActivity())) {
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_invisible);
        } else {
            mDisabled.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_disabled);
        }
        mTitleLayout.setOnClickListener(v -> Utils.launchUrl(mRootView, "https://forum.xda-developers.com" +
                "/oneplus-6/oneplus-6--6t-cross-device-development/tool-tomatot-debloater-basic-script-to-t3869427",
                requireActivity()));
        mDisabled.setOnClickListener(v -> {
            mDisabledT = true;
            mExtremeT = false;
            mInvisibleT = false;
            mLightT = false;
            mDisabled.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_disabled);
            updateAppList();
        });
        mInvisible.setOnClickListener(v -> {
            mDisabledT = false;
            mExtremeT = false;
            mInvisibleT = true;
            mLightT = false;
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_invisible);
            updateAppList();
        });
        mLight.setOnClickListener(v -> {
            mDisabledT = false;
            mExtremeT = false;
            mInvisibleT = false;
            mLightT = true;
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_light);
            updateAppList();
        });
        mExtreme.setOnClickListener(v -> {
            mDisabledT = false;
            mExtremeT = true;
            mInvisibleT = false;
            mLightT = false;
            mDisabled.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_tomatot_extreme);
            updateAppList();
        });
        mActionLayout.setOnClickListener(v -> {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mProgressLayout.setVisibility(View.VISIBLE);
                    mAppListCard.setVisibility(View.GONE);
                }
                @Override
                protected Void doInBackground(Void... voids) {
                    if (mDisabledT) {
                        if (Utils.getBoolean("tomatot_invisible", true, requireActivity())) {
                            PackageTasks.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            PackageTasks.disableTomatotLight(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            PackageTasks.disableTomatotExtreme(requireActivity());
                        }
                    } else if (mExtremeT) {
                        if (Utils.getBoolean("tomatot_invisible", true, requireActivity())) {
                            PackageTasks.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            PackageTasks.disableTomatotLight(requireActivity());
                        }
                        PackageTasks.enableTomatotExtreme(requireActivity());
                    } else if (mInvisibleT) {
                        if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            PackageTasks.disableTomatotLight(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            PackageTasks.disableTomatotExtreme(requireActivity());
                        }
                        PackageTasks.enableTomatotInvisible(requireActivity());
                    } else if (mLightT) {
                        if (Utils.getBoolean("tomatot_invisible", true, requireActivity())) {
                            PackageTasks.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            PackageTasks.disableTomatotExtreme(requireActivity());
                        }
                        PackageTasks.enableTomatotLight(requireActivity());
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mProgressLayout.setVisibility(View.GONE);
                    requireActivity().onBackPressed();
                }
            }.execute();
        });

        return mRootView;
    }

    @SuppressLint("SetTextI18n")
    private void updateAppList() {
        if (mDisabledT) {
            mAppListCard.setVisibility(View.GONE);
        } else if (mExtremeT) {
            mAppsList.setText(CustomScripts.getExtremeList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (mInvisibleT) {
            mAppsList.setText(CustomScripts.getInvisibletList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (mLightT) {
            mAppsList.setText(CustomScripts.getLightList(requireActivity()));
            mAppListCard.setVisibility(View.VISIBLE);
        }
    }

}