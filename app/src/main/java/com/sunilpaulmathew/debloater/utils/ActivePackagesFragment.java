package com.sunilpaulmathew.debloater.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;

import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class ActivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private AppCompatImageButton mReverse;
    private AsyncTask<Void, Void, Void> mLoader;
    private Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_activepackages, container, false);

        PackageTasks.mSearchWord = mRootView.findViewById(R.id.search_word);
        PackageTasks.mSearchButton = mRootView.findViewById(R.id.search_button);
        PackageTasks.mAbout = mRootView.findViewById(R.id.about_summary);
        MaterialTextView mPageTitle = mRootView.findViewById(R.id.page_title);
        mReverse = mRootView.findViewById(R.id.reverse_button);
        mMenu = mRootView.findViewById(R.id.menu_button);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mPageTitle.setText(getString(R.string.apps, getString(R.string.active)));
        mReverse.setElevation(10);
        mReverse.setOnClickListener(v -> {
            if (Utils.getBoolean("reverse_order", false, requireActivity())) {
                Utils.saveBoolean("reverse_order", false, requireActivity());
            } else {
                Utils.saveBoolean("reverse_order", true, requireActivity());
            }
            reload(requireActivity());
        });

        PackageTasks.mSearchButton.setOnClickListener(v -> {
            if (PackageTasks.mSearchWord.getVisibility() == View.VISIBLE) {
                if (PackageTasks.mSearchText != null && !PackageTasks.mSearchText.isEmpty()) {
                    PackageTasks.mSearchText = null;
                    PackageTasks.mSearchWord.setText(null);
                }
                PackageTasks.mSearchButton.setVisibility(View.VISIBLE);
                PackageTasks.mAbout.setVisibility(View.VISIBLE);
                PackageTasks.mSearchWord.setVisibility(View.GONE);
            } else {
                PackageTasks.mAbout.setVisibility(View.GONE);
                PackageTasks.mSearchWord.setVisibility(View.VISIBLE);
            }
        });

        PackageTasks.mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                PackageTasks.mSearchText = s.toString().toLowerCase();
                reload(requireActivity());
            }
        });

        mMenu.setOnClickListener(v -> menuOptions(requireActivity()));

        loadUI(requireActivity());

        return mRootView;
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
        SubMenu show = menu.addSubMenu(Menu.NONE, 0, Menu.NONE, getString(R.string.show_from));
        show.add(Menu.NONE, 4, Menu.NONE, getString(R.string.apps_system)).setCheckable(true)
                .setChecked(Utils.getBoolean("apps_system", true, activity));
        show.add(Menu.NONE, 5, Menu.NONE, getString(R.string.apps_vendor)).setCheckable(true)
                .setChecked(Utils.getBoolean("apps_vendor", true, activity));
        show.add(Menu.NONE, 6, Menu.NONE, getString(R.string.apps_product)).setCheckable(true)
                .setChecked(Utils.getBoolean("apps_product", true, activity));
        menu.add(Menu.NONE, 7, Menu.NONE, R.string.custom_scripts);
        menu.add(Menu.NONE, 8, Menu.NONE, R.string.reboot);
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
                    if (Utils.getBoolean("apps_system", true, activity)) {
                        Utils.saveBoolean("apps_system", false, activity);
                    } else {
                        Utils.saveBoolean("apps_system", true, activity);
                    }
                    reload(activity);
                    break;
                case 5:
                    if (Utils.getBoolean("apps_vendor", true, activity)) {
                        Utils.saveBoolean("apps_vendor", false, activity);
                    } else {
                        Utils.saveBoolean("apps_vendor", true, activity);
                    }
                    reload(activity);
                    break;
                case 6:
                    if (Utils.getBoolean("apps_product", true, activity)) {
                        Utils.saveBoolean("apps_product", false, activity);
                    } else {
                        Utils.saveBoolean("apps_product", true, activity);
                    }
                    reload(activity);
                    break;
                case 7:
                    Intent customScript = new Intent(activity, CustomScriptsActivity.class);
                    startActivity(customScript);
                    break;
                case 8:
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
                            mRecycleViewAdapter = new RecycleViewAdapter(PackageTasks.getActivePackageData(activity));
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
                            mRecycleViewAdapter = new RecycleViewAdapter(PackageTasks.getActivePackageData(activity));
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

        if (PackageTasks.mSearchText != null) {
            PackageTasks.mSearchText = null;
            PackageTasks.mSearchWord.setText(null);
        }
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private List<String> data;
        private static final String MODULE_PARENT = "/data/adb/modules/De-bloater";

        public RecycleViewAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
            return new RecycleViewAdapter.ViewHolder(rowItem);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
            try {
                holder.mIcon.setImageDrawable(PackageTasks.getAppIcon(this.data.get(position), holder.mName.getContext()));
                holder.mPath.setText(PackageTasks.getAPKPath(this.data.get(position), holder.mName.getContext()));
                if (PackageTasks.mSearchText != null && PackageTasks.getAppName(this.data.get(position), holder.mName.getContext()).toLowerCase().contains(PackageTasks.mSearchText)) {
                    holder.mName.setText(Utils.fromHtml(PackageTasks.getAppName(this.data.get(position), holder.mName.getContext()).toLowerCase().replace(PackageTasks.mSearchText,
                            "<b><i><font color=\"" + Color.RED + "\">" + PackageTasks.mSearchText + "</font></i></b>")));
                } else {
                    holder.mName.setText(PackageTasks.getAppName(this.data.get(position), holder.mName.getContext()));
                }
                if (Utils.isDarkTheme(holder.mName.getContext())) {
                    holder.mName.setTextColor(Utils.getThemeAccentColor(holder.mName.getContext()));
                }
                if (Utils.exist(MODULE_PARENT + PackageTasks.getAPKPath(this.data.get(position), holder.actionLayout.getContext())) || Utils.exist(MODULE_PARENT + PackageTasks.getAdjAPKPath(this.data.get(position), holder.actionLayout.getContext()))) {
                    holder.actionMessage.setText(holder.actionLayout.getContext().getString(R.string.restore));
                    holder.mActionIcon.setImageDrawable(holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_restore));
                    holder.statusMessage.setTextColor(Color.RED);
                    holder.statusMessage.setVisibility(View.VISIBLE);
                    holder.actionMessage.setTextColor(Color.GREEN);
                    holder.mActionIcon.setColorFilter(Color.GREEN);
                    holder.statusMessage.setText(holder.actionLayout.getContext().getString(R.string.status_message_remove));
                } else {
                    holder.actionMessage.setText(holder.actionLayout.getContext().getString(R.string.remove));
                    holder.mActionIcon.setImageDrawable(holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_delete));
                    holder.statusMessage.setTextColor(Color.GREEN);
                    holder.statusMessage.setVisibility(View.GONE);
                    holder.actionMessage.setTextColor(Color.RED);
                    holder.mActionIcon.setColorFilter(Color.RED);
                    holder.statusMessage.setText(null);
                }
                holder.actionLayout.setOnClickListener(v -> {
                    if (Utils.isPermissionDenied(holder.actionLayout.getContext())) {
                        Utils.snackBar(holder.actionLayout, holder.actionLayout.getContext().getString(R.string.storage_access_denied));
                        return;
                    }
                    if (Utils.exist(MODULE_PARENT + PackageTasks.getAPKPath(this.data.get(position), holder.actionLayout.getContext())) || Utils.exist(MODULE_PARENT + PackageTasks.getAdjAPKPath(this.data.get(position), holder.actionLayout.getContext()))) {
                        PackageTasks.revertDelete(PackageTasks.getAdjAPKPath(this.data.get(position), holder.actionLayout.getContext()));
                    } else {
                        PackageTasks.setToDelete(PackageTasks.getAdjAPKPath(this.data.get(position), holder.actionLayout.getContext()), holder.mName.getText().toString(), holder.actionLayout.getContext());
                    }
                    notifyDataSetChanged();
                });
            } catch (NullPointerException ignored) {}
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatImageButton mActionIcon;
            private AppCompatImageButton mIcon;
            private MaterialTextView mName;
            private MaterialTextView mPath;
            private MaterialTextView actionMessage;
            private MaterialTextView statusMessage;
            private FrameLayout actionLayout;

            public ViewHolder(View view) {
                super(view);
                this.mActionIcon = view.findViewById(R.id.action_icon);
                this.mIcon = view.findViewById(R.id.icon);
                this.mName = view.findViewById(R.id.title);
                this.mPath = view.findViewById(R.id.description);
                this.actionMessage = view.findViewById(R.id.action_message);
                this.statusMessage = view.findViewById(R.id.status_message);
                this.actionLayout = view.findViewById(R.id.action_layout);
            }
        }

    }
    
}