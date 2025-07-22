package com.sunilpaulmathew.debloater.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.adapters.ActivePackagesAdapter;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.UAD;
import com.sunilpaulmathew.debloater.utils.Utils;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sExecutor;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 26, 2021
 */

public class UADFragment extends Fragment {

    private AppCompatImageButton mSelectIcon;
    private MaterialCardView mAppListCard;
    private MaterialTextView mActionMessage, mScriptTitle, mScriptStatus;
    private LinearLayout mProgressLayout;
    private RecyclerView mRecyclerView;
    private ActivePackagesAdapter mRecycleViewAdapter;
    private String mScriptPath;
    private String mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_uad, container, false);

        AppCompatImageButton mActionIcon = mRootView.findViewById(R.id.action_icon);
        mSelectIcon = mRootView.findViewById(R.id.select_icon);
        FrameLayout mActionLayout = mRootView.findViewById(R.id.action_layout);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        LinearLayout mTitleLayout = mRootView.findViewById(R.id.title_layout);
        mAppListCard = mRootView.findViewById(R.id.apps_list_card);
        mActionMessage = mRootView.findViewById(R.id.action_message);
        MaterialTextView mAppsListTitle = mRootView.findViewById(R.id.apps_list_title);
        mScriptTitle = mRootView.findViewById(R.id.script_title);
        mScriptStatus = mRootView.findViewById(R.id.deblaoter_status);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        if (sThemeUtils.isDarkTheme(requireActivity())) {
            mActionMessage.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mActionIcon.setColorFilter(Utils.getThemeAccentColor(requireActivity()));
            mAppsListTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mScriptTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
        }

        if (Build.BRAND.equalsIgnoreCase("oneplus")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_oneplus";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getOnePlus(), null);
            mTitle = getString(R.string.oneplus);
        } else if (Build.BRAND.equalsIgnoreCase("asus")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_asus";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getAsus(), null);
            mTitle = getString(R.string.asus);
        } else if (Build.BRAND.equalsIgnoreCase("motorola")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_motorola";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getMoto(), null);
            mTitle = getString(R.string.motorola);
        } else if (Build.BRAND.equalsIgnoreCase("huawei")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_huawei";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getHuawei(), null);
            mTitle = getString(R.string.huawei);
        } else if (Build.BRAND.equalsIgnoreCase("lg")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_lg";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getLG(), null);
            mTitle = getString(R.string.lg);
        } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_samsung";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getSamsung(), null);
            mTitle = getString(R.string.samsung);
        } else if (Build.BRAND.equalsIgnoreCase("nokia")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_nokia";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getNokia(), null);
            mTitle = getString(R.string.nokia);
        } else if (Build.BRAND.equalsIgnoreCase("oppo")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_oppo";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getOppo(), null);
            mTitle = getString(R.string.oppo);
        } else if (Build.BRAND.equalsIgnoreCase("sony")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_sony";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getSony(), null);
            mTitle = getString(R.string.sony);
        } else if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_xiaomi";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getXiaomi(), null);
            mTitle = getString(R.string.xiaomi);
        } else if (Build.BRAND.equalsIgnoreCase("zte")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_zte";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getZTE(), null);
            mTitle = getString(R.string.zte);
        } else {
            mScriptPath = PackageTasks.getModulePath() + "/uad_google";
            mRecycleViewAdapter = new ActivePackagesAdapter(Common.getGoogle(), null);
            mTitle = getString(R.string.google);
        }

        setStatus();

        mTitleLayout.setOnClickListener(v -> sCommonUtils.launchUrl( "https://gitlab.com/W1nst0n/universal-android-debloater",
                requireActivity()));

        mActionLayout.setOnClickListener(v ->
                new sExecutor() {

                    @Override
                    public void onPreExecute() {
                        mProgressLayout.setVisibility(View.VISIBLE);
                        mAppListCard.setVisibility(View.GONE);
                    }

                    @Override
                    public void doInBackground() {
                        UAD.applyScript(mScriptPath, requireActivity());
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

        mSelectIcon.setOnClickListener(v -> selectionMenu(requireActivity()));

        return mRootView;
    }

    private void selectionMenu(Activity activity) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), mSelectIcon);
        Menu menu = popupMenu.getMenu();
        if (!Common.getAOSP().isEmpty() || Utils.exist("uad_aosp")) {
            menu.add(Menu.NONE, 0, Menu.NONE, R.string.aosp);
        }
        if (!Common.getGoogle().isEmpty() || Utils.exist("uad_google")) {
            menu.add(Menu.NONE, 1, Menu.NONE, R.string.google);
        }
        if (!Common.getOnePlus().isEmpty() || Utils.exist("uad_oneplus")) {
            menu.add(Menu.NONE, 2, Menu.NONE, R.string.oneplus);
        }
        if (!Common.getAsus().isEmpty() || Utils.exist("uad_asus")) {
            menu.add(Menu.NONE, 3, Menu.NONE, R.string.asus);
        }
        if (!Common.getHuawei().isEmpty() || Utils.exist("uad_huawei")) {
            menu.add(Menu.NONE, 4, Menu.NONE, R.string.huawei);
        }
        if (!Common.getLG().isEmpty() || Utils.exist("uad_lg")) {
            menu.add(Menu.NONE, 5, Menu.NONE, R.string.lg);
        }
        if (!Common.getSamsung().isEmpty() || Utils.exist("uad_samsung")) {
            menu.add(Menu.NONE, 6, Menu.NONE, R.string.samsung);
        }
        if (!Common.getMoto().isEmpty() || Utils.exist("uad_motorola")) {
            menu.add(Menu.NONE, 7, Menu.NONE, R.string.motorola);
        }
        if (!Common.getNokia().isEmpty() || Utils.exist("uad_nokia")) {
            menu.add(Menu.NONE, 8, Menu.NONE, R.string.nokia);
        }
        if (!Common.getOppo().isEmpty() || Utils.exist("uad_oppo")) {
            menu.add(Menu.NONE, 9, Menu.NONE, R.string.oppo);
        }
        if (!Common.getSony().isEmpty() || Utils.exist("uad_sony")) {
            menu.add(Menu.NONE, 10, Menu.NONE, R.string.sony);
        }
        if (!Common.getXiaomi().isEmpty() || Utils.exist("uad_xiaomi")) {
            menu.add(Menu.NONE, 11, Menu.NONE, R.string.xiaomi);
        }
        if (!Common.getZTE().isEmpty() || Utils.exist("uad_zte")) {
            menu.add(Menu.NONE, 12, Menu.NONE, R.string.zte);
        }
        if (!Common.getCarrier().isEmpty() || Utils.exist("uad_carrier")) {
            menu.add(Menu.NONE, 13, Menu.NONE, R.string.carrier);
        }
        if (!Common.getMisc().isEmpty() || Utils.exist("uad_misc")) {
            menu.add(Menu.NONE, 14, Menu.NONE, R.string.miscellaneous);
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    sCommonUtils.saveString("setDefault", "aosp", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_aosp";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getAOSP(), null);
                    mTitle = getString(R.string.aosp);
                    setStatus();
                    break;
                case 1:
                    sCommonUtils.saveString("setDefault", "google", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_google";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getGoogle(), null);
                    mTitle = getString(R.string.google);
                    setStatus();
                    break;
                case 2:
                    sCommonUtils.saveString("setDefault", "oneplus", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_oneplus";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getOnePlus(), null);
                    mTitle = getString(R.string.oneplus);
                    setStatus();
                    break;
                case 3:
                    sCommonUtils.saveString("setDefault", "asus", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_aosp";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getAsus(), null);
                    mTitle = getString(R.string.asus);
                    setStatus();
                    break;
                case 4:
                    sCommonUtils.saveString("setDefault", "huawei", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_huawei";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getHuawei(), null);
                    mTitle = getString(R.string.huawei);
                    setStatus();
                    break;
                case 5:
                    sCommonUtils.saveString("setDefault", "lg", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_lg";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getLG(), null);
                    mTitle = getString(R.string.lg);
                    setStatus();
                    break;
                case 6:
                    sCommonUtils.saveString("setDefault", "samsung", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_samsung";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getSamsung(), null);
                    mTitle = getString(R.string.samsung);
                    setStatus();
                    break;
                case 7:
                    sCommonUtils.saveString("setDefault", "motorola", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_motorola";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getMoto(), null);
                    mTitle = getString(R.string.motorola);
                    setStatus();
                    break;
                case 8:
                    sCommonUtils.saveString("setDefault", "nokia", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_nokia";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getNokia(), null);
                    mTitle = getString(R.string.nokia);
                    setStatus();
                    break;
                case 9:
                    sCommonUtils.saveString("setDefault", "oppo", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_oppo";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getOppo(), null);
                    mTitle = getString(R.string.oppo);
                    setStatus();
                    break;
                case 10:
                    sCommonUtils.saveString("setDefault", "sony", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_sony";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getSony(), null);
                    mTitle = getString(R.string.sony);
                    setStatus();
                    break;
                case 11:
                    sCommonUtils.saveString("setDefault", "xiaomi", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_xiaomi";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getXiaomi(), null);
                    mTitle = getString(R.string.xiaomi);
                    setStatus();
                    break;
                case 12:
                    sCommonUtils.saveString("setDefault", "zte", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_zte";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getZTE(), null);
                    mTitle = getString(R.string.zte);
                    setStatus();
                    break;
                case 13:
                    sCommonUtils.saveString("setDefault", "carrier", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_carrier";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getCarrier(), null);
                    mTitle = getString(R.string.carrier);
                    setStatus();
                    break;
                case 14:
                    sCommonUtils.saveString("setDefault", "misc", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_misc";
                    mRecycleViewAdapter = new ActivePackagesAdapter(Common.getMisc(), null);
                    mTitle = getString(R.string.miscellaneous);
                    setStatus();
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void setStatus() {
        if (Utils.exist(mScriptPath)) {
            mScriptStatus.setText(getString(R.string.custom_scripts_uad_enabled));
        } else {
            mScriptStatus.setText(getString(R.string.custom_scripts_uad_disabled));
        }
        mActionMessage.setText(Utils.exist(mScriptPath) ? getString(R.string.restore) : getString(R.string.apply));
        mScriptTitle.setText(mTitle);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

}