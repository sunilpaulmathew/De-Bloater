package com.sunilpaulmathew.debloater.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.activities.TomatotActivity;
import com.sunilpaulmathew.debloater.activities.UADActivity;
import com.sunilpaulmathew.debloater.adapters.ActivePackagesAdapter;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.Objects;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sExecutor;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class ActivePackagesFragment extends Fragment {

    private AppCompatEditText mSearchWord;
    private AppCompatImageButton mMenu;
    private boolean mExit = false;
    private final Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private MaterialCardView mReverse;
    private RecyclerView mRecyclerView;
    private ActivePackagesAdapter mRecycleViewAdapter;
    private String mSearchText = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_packages, container, false);

        mSearchWord = mRootView.findViewById(R.id.search_word);
        AppCompatImageButton mSearchButton = mRootView.findViewById(R.id.search_button);
        AppCompatTextView mSummary = mRootView.findViewById(R.id.about_summary);
        MaterialTextView mPageTitle = mRootView.findViewById(R.id.page_title);
        mReverse = mRootView.findViewById(R.id.reverse_button);
        mMenu = mRootView.findViewById(R.id.menu_button);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        TabLayout mTabLayout = mRootView.findViewById(R.id.tab_layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mPageTitle.setText(getString(R.string.apps, getString(R.string.active)));
        mSummary.setText(getString(R.string.active_app_summary));
        mReverse.setElevation(10);
        mReverse.setOnClickListener(v -> {
            sCommonUtils.saveBoolean("reverse_order", !sCommonUtils.getBoolean("reverse_order", false, requireActivity()), requireActivity());
            loadUI(requireActivity(), mSearchText);
        });

        mSearchButton.setOnClickListener(v -> {
            if (mSearchWord.getVisibility() == View.VISIBLE) {
                if (mSearchText != null && !mSearchText.isEmpty()) {
                    mSearchText = null;
                    mSearchWord.setText(null);
                }
                mSummary.setVisibility(View.VISIBLE);
                mSearchWord.setVisibility(View.GONE);
                PackageTasks.toggleKeyboard(mSearchWord, 0, requireActivity());
            } else {
                mSummary.setVisibility(View.GONE);
                mSearchWord.setVisibility(View.VISIBLE);
                PackageTasks.toggleKeyboard(mSearchWord, 1, requireActivity());
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_all)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_system)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_product)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_vendor)));

        mTabLayout.setVisibility(View.VISIBLE);

        Objects.requireNonNull(mTabLayout.getTabAt(getTabPosition(requireActivity()))).select();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String mStatus = sCommonUtils.getString("appTypes", "all", requireActivity());
                switch (tab.getPosition()) {
                    case 0:
                        if (!mStatus.equals("all")) {
                            sCommonUtils.saveString("appTypes", "all", requireActivity());
                            loadUI(requireActivity(), mSearchText);
                        }
                        break;
                    case 1:
                        if (!mStatus.equals("system")) {
                            sCommonUtils.saveString("appTypes", "system", requireActivity());
                            loadUI(requireActivity(), mSearchText);
                        }
                        break;
                    case 2:
                        if (!mStatus.equals("product")) {
                            sCommonUtils.saveString("appTypes", "product", requireActivity());
                            loadUI(requireActivity(), mSearchText);
                        }
                        break;
                    case 3:
                        if (!mStatus.equals("vendor")) {
                            sCommonUtils.saveString("appTypes", "vendor", requireActivity());
                            loadUI(requireActivity(), mSearchText);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadUI(requireActivity(), s.toString().toLowerCase());
            }
        });

        mMenu.setOnClickListener(v -> menuOptions(requireActivity()));

        loadUI(requireActivity(), mSearchText);

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mSearchWord.getVisibility() == View.VISIBLE) {
                    if (mSearchText != null) {
                        mSearchText = null;
                        mSearchWord.setText(null);
                    }
                    mSearchWord.setVisibility(View.GONE);
                    return;
                }
                if (mExit) {
                    mExit = false;
                    requireActivity().finish();
                } else {
                    sCommonUtils.toast(getString(R.string.press_back_exit), requireActivity()).show();
                    mExit = true;
                    mHandler.postDelayed(() -> mExit = false, 2000);
                }
            }
        });

        return mRootView;
    }

    private int getTabPosition(Activity activity) {
        String mStatus = sCommonUtils.getString("appTypes", "all", activity);
        return switch (mStatus) {
            case "vendor" -> 3;
            case "product" -> 2;
            case "system" -> 1;
            default -> 0;
        };
    }

    private void menuOptions(Activity activity) {
        PopupMenu popupMenu = new PopupMenu(activity, mMenu);
        Menu menu = popupMenu.getMenu();
        if (PackageTasks.isModuleInitialized()) {
            menu.add(Menu.NONE, 1, Menu.NONE, R.string.module_status_reset);
        }
        SubMenu sort = menu.addSubMenu(Menu.NONE, 0, Menu.NONE, getString(R.string.sort_by));
        sort.add(0, 2, Menu.NONE, getString(R.string.name)).setCheckable(true)
                .setChecked(sCommonUtils.getInt("sort_apps", 1, activity) == 0);
        sort.add(0, 3, Menu.NONE, getString(R.string.package_id)).setCheckable(true)
                .setChecked(sCommonUtils.getInt("sort_apps", 1, activity) == 1);
        sort.setGroupCheckable(0, true, true);
        SubMenu customScripts = menu.addSubMenu(Menu.NONE, 0, Menu.NONE, getString(R.string.custom_scripts));
        customScripts.add(Menu.NONE, 4, Menu.NONE, R.string.custom_scripts_tomatot);
        customScripts.add(Menu.NONE, 5, Menu.NONE, R.string.custom_scripts_uad);
        menu.add(Menu.NONE, 6, Menu.NONE, R.string.reboot);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    break;
                case 1:
                    PackageTasks.removeModule(activity);
                    break;
                case 2:
                    if (sCommonUtils.getInt("sort_apps", 1, activity) != 0) {
                        sCommonUtils.saveInt("sort_apps", 0, activity);
                        loadUI(activity, mSearchText);
                    }
                    break;
                case 3:
                    if (sCommonUtils.getInt("sort_apps", 1, activity) != 1) {
                        sCommonUtils.saveInt("sort_apps", 1, activity);
                        loadUI(activity, mSearchText);
                    }
                    break;
                case 4:
                    Intent tomatotScript = new Intent(activity, TomatotActivity.class);
                    startActivity(tomatotScript);
                    break;
                case 5:
                    Intent uadScript = new Intent(activity, UADActivity.class);
                    startActivity(uadScript);
                    break;
                case 6:
                    Utils.runCommand("svc power reboot");
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void loadUI(Activity activity, String searchText) {
        new sExecutor() {

            @Override
            public void onPreExecute() {
                mProgressLayout.setVisibility(View.VISIBLE);
                mReverse.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mRecyclerView.removeAllViews();
            }

            @Override
            public void doInBackground() {
                mRecycleViewAdapter = new ActivePackagesAdapter(PackageTasks.getActivePackageData(activity, searchText), searchText);
                if (searchText != null) {
                    mSearchText = searchText;
                }
            }

            @Override
            public void onPostExecute() {
                mRecyclerView.setAdapter(mRecycleViewAdapter);
                mProgressLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mReverse.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mSearchText != null) {
            mSearchText = null;
            mSearchWord.setText(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSearchText != null) {
            mSearchText = null;
            mSearchWord.setText(null);
        }
    }
    
}