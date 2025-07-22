package com.sunilpaulmathew.debloater.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.adapters.ActivePackagesAdapter;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.Tomatot;
import com.sunilpaulmathew.debloater.utils.Utils;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sExecutor;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 4, 2020
 */

public class TomatotDebloaterFragment extends Fragment {

    private boolean mExtremeT = false, mInvisibleT = false, mLightT = false;
    private MaterialCardView mAppListCard;
    private LinearLayout mProgressLayout;
    private RecyclerView mRecyclerView;
    private ActivePackagesAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_tomatot_debloater, container, false);

        AppCompatImageButton mActionIcon = mRootView.findViewById(R.id.action_icon);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        LinearLayout mTitleLayout = mRootView.findViewById(R.id.title_layout);
        MaterialTextView mInvisible = mRootView.findViewById(R.id.invisible);
        MaterialTextView mLight = mRootView.findViewById(R.id.light);
        MaterialTextView mExtreme = mRootView.findViewById(R.id.extreme);
        MaterialTextView mStatus = mRootView.findViewById(R.id.deblaoter_status);
        MaterialTextView mActionMessage = mRootView.findViewById(R.id.action_message);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        MaterialTextView mAppsListTitle = mRootView.findViewById(R.id.apps_list_title);
        mAppListCard = mRootView.findViewById(R.id.apps_list_card);
        FrameLayout mActionLayout = mRootView.findViewById(R.id.action_layout);
        if (sThemeUtils.isDarkTheme(requireActivity())) {
            mActionMessage.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mActionIcon.setColorFilter(Utils.getThemeAccentColor(requireActivity()));
            mAppsListTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
        }

        if (Tomatot.isScriptEnabled("tomatot_extreme", requireActivity())) {
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_uad_enabled);
            mActionMessage.setText(R.string.restore);
            mExtremeT = true;
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getTExtreme(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
            mAppListCard.setVisibility(View.VISIBLE);
        } else if (Tomatot.isScriptEnabled("tomatot_light", requireActivity())) {
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(R.string.custom_scripts_uad_enabled);
            mActionMessage.setText(R.string.restore);
            mLightT = true;
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getTLight(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
            mAppListCard.setVisibility(View.VISIBLE);
        } else {
            mInvisible.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(sCommonUtils.getBoolean("tomatot_invisible", false, requireActivity()) ?
                    R.string.custom_scripts_uad_enabled : R.string.custom_scripts_tomatot_invisible);
            mActionMessage.setText(sCommonUtils.getBoolean("tomatot_invisible", false, requireActivity()) ?
                    R.string.restore : R.string.apply);
            mInvisibleT = true;
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.geTInvisible(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
            mAppListCard.setVisibility(View.VISIBLE);
        }

        mTitleLayout.setOnClickListener(v -> sCommonUtils.launchUrl("https://forum.xda-developers.com" +
                "/oneplus-6/oneplus-6--6t-cross-device-development/tool-tomatot-debloater-basic-script-to-t3869427",
                requireActivity()));
        mInvisible.setOnClickListener(v -> {
            mExtremeT = false;
            mInvisibleT = true;
            mLightT = false;
            mInvisible.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(sCommonUtils.getBoolean("tomatot_invisible", false, requireActivity()) ?
                    R.string.custom_scripts_uad_enabled : R.string.custom_scripts_tomatot_invisible);
            mActionMessage.setText(sCommonUtils.getBoolean("tomatot_invisible", false, requireActivity()) ?
                    R.string.restore : R.string.apply);
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.geTInvisible(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
        });
        mLight.setOnClickListener(v -> {
            mExtremeT = false;
            mInvisibleT = false;
            mLightT = true;
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mExtreme.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mStatus.setText(sCommonUtils.getBoolean("tomatot_light", false, requireActivity()) ?
                    R.string.custom_scripts_uad_enabled : R.string.custom_scripts_tomatot_light);
            mActionMessage.setText(sCommonUtils.getBoolean("tomatot_light", false, requireActivity()) ?
                    R.string.restore : R.string.apply);
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getTLight(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
        });
        mExtreme.setOnClickListener(v -> {
            mExtremeT = true;
            mInvisibleT = false;
            mLightT = false;
            mInvisible.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mLight.setTextColor(Utils.getPrimaryTextColor(requireActivity()));
            mExtreme.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mStatus.setText(sCommonUtils.getBoolean("tomatot_extreme", false, requireActivity()) ?
                    R.string.custom_scripts_uad_enabled : R.string.custom_scripts_tomatot_extreme);
            mActionMessage.setText(sCommonUtils.getBoolean("tomatot_extreme", false, requireActivity()) ?
                    R.string.restore : R.string.apply);
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getTExtreme(), null);
            mRecyclerView.setAdapter(mRecycleViewAdapter);
        });
        mActionLayout.setOnClickListener(v ->
                new sExecutor() {

                    @Override
                    public void onPreExecute() {
                        mProgressLayout.setVisibility(View.VISIBLE);
                        mAppListCard.setVisibility(View.GONE);
                    }

                    @Override
                    public void doInBackground() {
                        if (mExtremeT) {
                            if (sCommonUtils.getBoolean("tomatot_extreme", false, requireActivity())) {
                                Tomatot.disable("tomatot_extreme", Common.getTExtreme(), requireActivity());
                            } else {
                                if (Tomatot.isScriptEnabled("tomatot_invisible", requireActivity())) {
                                    Tomatot.disable("tomatot_invisible", Common.geTInvisible(), requireActivity());
                                }
                                if (Tomatot.isScriptEnabled("tomatot_light", requireActivity())) {
                                    Tomatot.disable("tomatot_light", Common.getTLight(), requireActivity());
                                }
                                Tomatot.enable("tomatot_extreme", Common.getTExtreme(), requireActivity());
                            }
                        } else if (mInvisibleT) {
                            if (sCommonUtils.getBoolean("tomatot_invisible", false, requireActivity())) {
                                Tomatot.disable("tomatot_invisible", Common.geTInvisible(), requireActivity());
                            } else {
                                if (Tomatot.isScriptEnabled("tomatot_light", requireActivity())) {
                                    Tomatot.disable("tomatot_light", Common.getTLight(), requireActivity());
                                }
                                if (Tomatot.isScriptEnabled("tomatot_extreme", requireActivity())) {
                                    Tomatot.disable("tomatot_extreme", Common.getTExtreme(), requireActivity());
                                }
                                Tomatot.enable("tomatot_invisible", Common.geTInvisible(), requireActivity());
                            }
                        } else if (mLightT) {
                            if (sCommonUtils.getBoolean("tomatot_light", false, requireActivity())) {
                                Tomatot.disable("tomatot_light", Common.getTLight(), requireActivity());
                            } else {
                                if (Tomatot.isScriptEnabled("tomatot_invisible", requireActivity())) {
                                    Tomatot.disable("tomatot_invisible", Common.geTInvisible(), requireActivity());
                                }
                                if (Tomatot.isScriptEnabled("tomatot_extreme", requireActivity())) {
                                    Tomatot.disable("tomatot_extreme", Common.getTExtreme(), requireActivity());
                                }
                                Tomatot.enable("tomatot_light", Common.getTLight(), requireActivity());
                            }
                        }
                    }

                    @Override
                    public void onPostExecute() {
                        mProgressLayout.setVisibility(View.GONE);
                        new MaterialAlertDialogBuilder(requireActivity())
                                .setMessage(R.string.custom_scripts_applied_message)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.cancel), (dialog, id) -> requireActivity().finish()).show();
                    }
                }.execute()
        );

        return mRootView;
    }

}