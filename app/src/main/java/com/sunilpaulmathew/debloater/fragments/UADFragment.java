package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
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
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.UAD;
import com.sunilpaulmathew.debloater.utils.Utils;

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

    @SuppressLint("StaticFieldLeak")
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

        if (Utils.isDarkTheme(requireActivity())) {
            mActionMessage.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mActionIcon.setColorFilter(Utils.getThemeAccentColor(requireActivity()));
            mAppsListTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
            mScriptTitle.setTextColor(Utils.getThemeAccentColor(requireActivity()));
        }

        if (Build.BRAND.equalsIgnoreCase("oneplus")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_oneplus";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getOnePlusList(requireActivity()));
            mTitle = getString(R.string.oneplus);
        } else if (Build.BRAND.equalsIgnoreCase("asus")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_asus";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getASUSList(requireActivity()));
            mTitle = getString(R.string.asus);
        } else if (Build.BRAND.equalsIgnoreCase("motorola")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_motorola";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getMotorolaList(requireActivity()));
            mTitle = getString(R.string.motorola);
        } else if (Build.BRAND.equalsIgnoreCase("huawei")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_huawei";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getHuaweiList(requireActivity()));
            mTitle = getString(R.string.huawei);
        } else if (Build.BRAND.equalsIgnoreCase("lg")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_lg";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getLGList(requireActivity()));
            mTitle = getString(R.string.lg);
        } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_samsung";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getSamsungList(requireActivity()));
            mTitle = getString(R.string.samsung);
        } else if (Build.BRAND.equalsIgnoreCase("nokia")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_nokia";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getNokiaList(requireActivity()));
            mTitle = getString(R.string.nokia);
        } else if (Build.BRAND.equalsIgnoreCase("oppo")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_oppo";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getOppoList(requireActivity()));
            mTitle = getString(R.string.oppo);
        } else if (Build.BRAND.equalsIgnoreCase("sony")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_sony";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getSonyList(requireActivity()));
            mTitle = getString(R.string.sony);
        } else if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_xiaomi";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getXiaomiList(requireActivity()));
            mTitle = getString(R.string.xiaomi);
        } else if (Build.BRAND.equalsIgnoreCase("zte")) {
            mScriptPath = PackageTasks.getModulePath() + "/uad_zte";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getZTEList(requireActivity()));
            mTitle = getString(R.string.zte);
        } else {
            mScriptPath = PackageTasks.getModulePath() + "/uad_google";
            mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getGoogleList(requireActivity()));
            mTitle = getString(R.string.google);
        }

        setStatus();

        mTitleLayout.setOnClickListener(v -> Utils.launchUrl( "https://gitlab.com/W1nst0n/universal-android-debloater",
                requireActivity()));

        mActionLayout.setOnClickListener(v -> new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressLayout.setVisibility(View.VISIBLE);
                mAppListCard.setVisibility(View.GONE);
            }
            @Override
            protected Void doInBackground(Void... voids) {
                UAD.applyScript(mScriptPath, requireActivity());
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
        }.execute());

        mSelectIcon.setOnClickListener(v -> selectionMenu(requireActivity()));

        return mRootView;
    }

    private void selectionMenu(Activity activity) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), mSelectIcon);
        Menu menu = popupMenu.getMenu();
        if (UAD.getAOSPList(activity).size() > 0 || Utils.exist("uad_aosp")) {
            menu.add(Menu.NONE, 0, Menu.NONE, R.string.aosp);
        }
        if (UAD.getGoogleList(activity).size() > 0 || Utils.exist("uad_google")) {
            menu.add(Menu.NONE, 1, Menu.NONE, R.string.google);
        }
        if (UAD.getOnePlusList(activity).size() > 0 || Utils.exist("uad_oneplus")) {
            menu.add(Menu.NONE, 2, Menu.NONE, R.string.oneplus);
        }
        if (UAD.getASUSList(activity).size() > 0 || Utils.exist("uad_asus")) {
            menu.add(Menu.NONE, 3, Menu.NONE, R.string.asus);
        }
        if (UAD.getHuaweiList(activity).size() > 0 || Utils.exist("uad_huawei")) {
            menu.add(Menu.NONE, 4, Menu.NONE, R.string.huawei);
        }
        if (UAD.getLGList(activity).size() > 0 || Utils.exist("uad_lg")) {
            menu.add(Menu.NONE, 5, Menu.NONE, R.string.lg);
        }
        if (UAD.getSamsungList(activity).size() > 0 || Utils.exist("uad_samsung")) {
            menu.add(Menu.NONE, 6, Menu.NONE, R.string.samsung);
        }
        if (UAD.getMotorolaList(activity).size() > 0 || Utils.exist("uad_motorola")) {
            menu.add(Menu.NONE, 7, Menu.NONE, R.string.motorola);
        }
        if (UAD.getNokiaList(activity).size() > 0 || Utils.exist("uad_nokia")) {
            menu.add(Menu.NONE, 8, Menu.NONE, R.string.nokia);
        }
        if (UAD.getOppoList(activity).size() > 0 || Utils.exist("uad_oppo")) {
            menu.add(Menu.NONE, 9, Menu.NONE, R.string.oppo);
        }
        if (UAD.getSonyList(activity).size() > 0 || Utils.exist("uad_sony")) {
            menu.add(Menu.NONE, 10, Menu.NONE, R.string.sony);
        }
        if (UAD.getXiaomiList(activity).size() > 0 || Utils.exist("uad_xiaomi")) {
            menu.add(Menu.NONE, 11, Menu.NONE, R.string.xiaomi);
        }
        if (UAD.getZTEList(activity).size() > 0 || Utils.exist("uad_zte")) {
            menu.add(Menu.NONE, 12, Menu.NONE, R.string.zte);
        }
        if (UAD.getCarrierList(activity).size() > 0 || Utils.exist("uad_carrier")) {
            menu.add(Menu.NONE, 13, Menu.NONE, R.string.carrier);
        }
        if (UAD.getMiscellaneousList(activity).size() > 0 || Utils.exist("uad_misc")) {
            menu.add(Menu.NONE, 14, Menu.NONE, R.string.miscellaneous);
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    Utils.saveString("setDefault", "aosp", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_aosp";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getAOSPList(activity));
                    mTitle = getString(R.string.aosp);
                    setStatus();
                    break;
                case 1:
                    Utils.saveString("setDefault", "google", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_google";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getGoogleList(activity));
                    mTitle = getString(R.string.google);
                    setStatus();
                    break;
                case 2:
                    Utils.saveString("setDefault", "oneplus", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_oneplus";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getOnePlusList(activity));
                    mTitle = getString(R.string.oneplus);
                    setStatus();
                    break;
                case 3:
                    Utils.saveString("setDefault", "asus", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_aosp";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getASUSList(activity));
                    mTitle = getString(R.string.asus);
                    setStatus();
                    break;
                case 4:
                    Utils.saveString("setDefault", "huawei", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_huawei";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getHuaweiList(activity));
                    mTitle = getString(R.string.huawei);
                    setStatus();
                    break;
                case 5:
                    Utils.saveString("setDefault", "lg", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_lg";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getLGList(activity));
                    mTitle = getString(R.string.lg);
                    setStatus();
                    break;
                case 6:
                    Utils.saveString("setDefault", "samsung", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_samsung";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getSamsungList(activity));
                    mTitle = getString(R.string.samsung);
                    setStatus();
                    break;
                case 7:
                    Utils.saveString("setDefault", "motorola", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_motorola";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getMotorolaList(activity));
                    mTitle = getString(R.string.motorola);
                    setStatus();
                    break;
                case 8:
                    Utils.saveString("setDefault", "nokia", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_nokia";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getNokiaList(activity));
                    mTitle = getString(R.string.nokia);
                    setStatus();
                    break;
                case 9:
                    Utils.saveString("setDefault", "oppo", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_oppo";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getOppoList(activity));
                    mTitle = getString(R.string.oppo);
                    setStatus();
                    break;
                case 10:
                    Utils.saveString("setDefault", "sony", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_sony";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getSonyList(activity));
                    mTitle = getString(R.string.sony);
                    setStatus();
                    break;
                case 11:
                    Utils.saveString("setDefault", "xiaomi", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_xiaomi";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getXiaomiList(activity));
                    mTitle = getString(R.string.xiaomi);
                    setStatus();
                    break;
                case 12:
                    Utils.saveString("setDefault", "zte", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_zte";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getZTEList(activity));
                    mTitle = getString(R.string.zte);
                    setStatus();
                    break;
                case 13:
                    Utils.saveString("setDefault", "carrier", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_carrier";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getCarrierList(activity));
                    mTitle = getString(R.string.carrier);
                    setStatus();
                    break;
                case 14:
                    Utils.saveString("setDefault", "misc", activity);
                    mScriptPath = PackageTasks.getModulePath() + "/uad_misc";
                    mRecycleViewAdapter = new ActivePackagesAdapter(UAD.getMiscellaneousList(activity));
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