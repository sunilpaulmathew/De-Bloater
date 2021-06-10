package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
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
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class ActivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private AsyncTask<Void, Void, Void> mLoader;
    private final Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private MaterialCardView mReverse;
    private RecyclerView mRecyclerView;
    private ActivePackagesAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_activepackages, container, false);

        Common.initializeSearchWord(mRootView, R.id.search_word);
        Common.initializeSearchButton(mRootView, R.id.search_button);
        Common.initializeAboutSummary(mRootView, R.id.about_summary);
        MaterialTextView mPageTitle = mRootView.findViewById(R.id.page_title);
        mReverse = mRootView.findViewById(R.id.reverse_button);
        mMenu = mRootView.findViewById(R.id.menu_button);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        TabLayout mTabLayout = mRootView.findViewById(R.id.tab_layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mPageTitle.setText(getString(R.string.apps, getString(R.string.active)));
        mReverse.setElevation(10);
        mReverse.setOnClickListener(v -> {
            Utils.saveBoolean("reverse_order", !Utils.getBoolean("reverse_order", false, requireActivity()), requireActivity());
            reload(requireActivity());
        });

        Common.getSearchButton().setOnClickListener(v -> {
            if (Common.getSearchWord().getVisibility() == View.VISIBLE) {
                if (Common.getSearchText() != null && !Common.getSearchText().isEmpty()) {
                    Common.setSearchText(null);
                    Common.getSearchWord().setText(null);
                }
                Common.getSearchButton().setVisibility(View.VISIBLE);
                Common.getAboutSummary().setVisibility(View.VISIBLE);
                Common.getSearchWord().setVisibility(View.GONE);
                PackageTasks.toggleKeyboard(0, requireActivity());
            } else {
                Common.getAboutSummary().setVisibility(View.GONE);
                Common.getSearchWord().setVisibility(View.VISIBLE);
                PackageTasks.toggleKeyboard(1, requireActivity());
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_all)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_system)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_product)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.apps_vendor)));

        Objects.requireNonNull(mTabLayout.getTabAt(getTabPosition(requireActivity()))).select();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String mStatus = Utils.getString("appTypes", "all", requireActivity());
                switch (tab.getPosition()) {
                    case 0:
                        if (!mStatus.equals("all")) {
                            Utils.saveString("appTypes", "all", requireActivity());
                            loadUI(requireActivity());
                        }
                        break;
                    case 1:
                        if (!mStatus.equals("system")) {
                            Utils.saveString("appTypes", "system", requireActivity());
                            loadUI(requireActivity());
                        }
                        break;
                    case 2:
                        if (!mStatus.equals("product")) {
                            Utils.saveString("appTypes", "product", requireActivity());
                            loadUI(requireActivity());
                        }
                        break;
                    case 3:
                        if (!mStatus.equals("vendor")) {
                            Utils.saveString("appTypes", "vendor", requireActivity());
                            loadUI(requireActivity());
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

        Common.getSearchWord().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Common.setSearchText(s.toString().toLowerCase());
                reload(requireActivity());
            }
        });

        mMenu.setOnClickListener(v -> menuOptions(requireActivity()));

        loadUI(requireActivity());

        return mRootView;
    }

    private int getTabPosition(Activity activity) {
        String mStatus = Utils.getString("appTypes", "all", activity);
        switch (mStatus) {
            case "vendor":
                return 3;
            case "product":
                return 2;
            case "system":
                return 1;
            default:
                return 0;
        }
    }

    private void menuOptions(Activity activity) {
        PopupMenu popupMenu = new PopupMenu(activity, mMenu);
        Menu menu = popupMenu.getMenu();
        if (PackageTasks.isModuleInitialized()) {
            menu.add(Menu.NONE, 1, Menu.NONE, R.string.module_status_reset);
        }
        SubMenu sort = menu.addSubMenu(Menu.NONE, 0, Menu.NONE, getString(R.string.sort_by));
        sort.add(Menu.NONE, 2, Menu.NONE, getString(R.string.name)).setCheckable(true)
                .setChecked(Utils.getBoolean("sort_name", false, activity));
        sort.add(Menu.NONE, 3, Menu.NONE, getString(R.string.package_id)).setCheckable(true)
                .setChecked(Utils.getBoolean("sort_id", true, activity));
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
                    if (!Utils.getBoolean("sort_name", false, activity)) {
                        Utils.saveBoolean("sort_name", true, activity);
                        Utils.saveBoolean("sort_id", false, activity);
                        reload(activity);
                    }
                    break;
                case 3:
                    if (!Utils.getBoolean("sort_id", true, activity)) {
                        Utils.saveBoolean("sort_id", true, activity);
                        Utils.saveBoolean("sort_name", false, activity);
                        reload(activity);
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

    private void loadUI(Activity activity) {
        if (mLoader == null) {
            mHandler.postDelayed(new Runnable() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void run() {
                    mLoader = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            mProgressLayout.setVisibility(View.VISIBLE);
                            mReverse.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            mRecycleViewAdapter = new ActivePackagesAdapter(PackageTasks.getActivePackageData(activity));
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            mRecyclerView.setAdapter(mRecycleViewAdapter);
                            mProgressLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mReverse.setVisibility(View.VISIBLE);
                            mLoader = null;
                        }
                    };
                    mLoader.execute();
                }
            }, 250);
        }
    }

    private void reload(Activity activity) {
        if (mLoader == null) {
            mHandler.postDelayed(new Runnable() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void run() {
                    mLoader = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            mProgressLayout.setVisibility(View.VISIBLE);
                            mReverse.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            mRecyclerView.removeAllViews();
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            mRecycleViewAdapter = new ActivePackagesAdapter(PackageTasks.getActivePackageData(activity));
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            mRecyclerView.setAdapter(mRecycleViewAdapter);
                            mRecycleViewAdapter.notifyDataSetChanged();
                            mProgressLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mReverse.setVisibility(View.VISIBLE);
                            mLoader = null;
                        }
                    };
                    mLoader.execute();
                }
            }, 250);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Common.getSearchText() != null) {
            Common.setSearchText(null);
            Common.getSearchWord().setText(null);
        }
    }
    
}