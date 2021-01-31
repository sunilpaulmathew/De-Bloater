package com.sunilpaulmathew.debloater.fragments;

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
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.Tomatot;
import com.sunilpaulmathew.debloater.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 4, 2020
 */

public class TomatotDebloaterFragment extends Fragment {

    private MaterialTextView mAppsList;
    private boolean mDisabledT = false, mExtremeT = false, mInvisibleT = false, mLightT = false;
    private MaterialCardView mAppListCard;
    private LinearLayout mProgressLayout;

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_tomatot_debloater, container, false);

        AppCompatImageButton mActionIcon = mRootView.findViewById(R.id.action_icon);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        LinearLayout mTitleLayout = mRootView.findViewById(R.id.title_layout);
        MaterialTextView mDisabled = mRootView.findViewById(R.id.disabled);
        MaterialTextView mInvisible = mRootView.findViewById(R.id.invisible);
        MaterialTextView mLight = mRootView.findViewById(R.id.light);
        MaterialTextView mExtreme = mRootView.findViewById(R.id.extreme);
        MaterialTextView mStatus = mRootView.findViewById(R.id.deblaoter_status);
        MaterialTextView mActionMessage = mRootView.findViewById(R.id.action_message);
        mAppsList = mRootView.findViewById(R.id.apps_list);
        MaterialTextView mAppsListTitle = mRootView.findViewById(R.id.apps_list_title);
        mAppListCard = mRootView.findViewById(R.id.apps_list_card);
        FrameLayout mActionLayout = mRootView.findViewById(R.id.action_layout);
        if (Utils.isDarkTheme(requireActivity())) {
            mActionMessage.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mActionIcon.setColorFilter(Utils.getThemeAccentColor(requireActivity()));
            mAppsListTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
        }

        if (Utils.getBoolean("tomatot_extreme", false, requireActivity())) {
            mAppsList.setText(Tomatot.getExtremeList());
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (Utils.getBoolean("tomatot_invisible", false, requireActivity())) {
            mAppsList.setText(Tomatot.getInvisibletList());
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (Utils.getBoolean("tomatot_light", false, requireActivity())) {
            mAppsList.setText(Tomatot.getLightList());
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
                            Tomatot.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            Tomatot.disableTomatotLight(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            Tomatot.disableTomatotExtreme(requireActivity());
                        }
                    } else if (mExtremeT) {
                        if (Utils.getBoolean("tomatot_invisible", true, requireActivity())) {
                            Tomatot.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            Tomatot.disableTomatotLight(requireActivity());
                        }
                        Tomatot.enableTomatotExtreme(requireActivity());
                    } else if (mInvisibleT) {
                        if (Utils.getBoolean("tomatot_light", true, requireActivity())) {
                            Tomatot.disableTomatotLight(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            Tomatot.disableTomatotExtreme(requireActivity());
                        }
                        Tomatot.enableTomatotInvisible(requireActivity());
                    } else if (mLightT) {
                        if (Utils.getBoolean("tomatot_invisible", true, requireActivity())) {
                            Tomatot.disableTomatotInvisible(requireActivity());
                        } else if (Utils.getBoolean("tomatot_extreme", true, requireActivity())) {
                            Tomatot.disableTomatotExtreme(requireActivity());
                        }
                        Tomatot.enableTomatotLight(requireActivity());
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mProgressLayout.setVisibility(View.GONE);
                    new MaterialAlertDialogBuilder(requireActivity())
                            .setMessage(R.string.custom_scripts_applied_message)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.cancel), (dialog, id) -> {
                                requireActivity().finish();
                            }).show();
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
            mAppsList.setText(Tomatot.getExtremeList());
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (mInvisibleT) {
            mAppsList.setText(Tomatot.getInvisibletList());
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (mLightT) {
            mAppsList.setText(Tomatot.getLightList());
            mAppListCard.setVisibility(View.VISIBLE);
        }
    }

}