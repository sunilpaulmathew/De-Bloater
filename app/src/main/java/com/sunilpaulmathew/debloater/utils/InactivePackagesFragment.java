package com.sunilpaulmathew.debloater.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunilpaulmathew.debloater.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private AsyncTask<Void, Void, Void> mLoader;
    private Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_inactivepackages, container, false);

        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        AppCompatTextView mPageTitle = mRootView.findViewById(R.id.page_title);
        mMenu = mRootView.findViewById(R.id.menu_button);

        mPageTitle.setText(getString(R.string.apps, getString(R.string.inactive)));
        mMenu.setOnClickListener(v -> {
            menuOptions(requireActivity());
        });

        loadUI();

        return mRootView;
    }

    private void menuOptions(Activity activity) {
        PopupMenu popupMenu = new PopupMenu(activity, mMenu);
        Menu menu = popupMenu.getMenu();
        if (PackageTasks.isModuleInitialized()) {
            menu.add(Menu.NONE, 0, Menu.NONE, R.string.module_status_reset);
        }
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.reboot);
        menu.add(Menu.NONE, 2, Menu.NONE, R.string.backup);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    PackageTasks.removeModule(activity);
                    break;
                case 1:
                    Utils.runCommand("svc power reboot");
                    break;
                case 2:
                    if (Utils.isPermissionDenied(requireActivity())) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else if (PackageTasks.getInactivePackageData().size() > 0) {
                        try {
                            JSONObject obj = new JSONObject();
                            JSONArray DeBloater = new JSONArray();
                            JSONObject customScript = new JSONObject();
                            JSONArray TE = new JSONArray();
                            JSONArray TI = new JSONArray();
                            JSONArray TL = new JSONArray();
                            String[] apps = PackageTasks.getInactivePackageData().toString().substring(1, PackageTasks.getInactivePackageData().toString().length() - 1).split(", ");
                            for (String s : apps) {
                                JSONObject app = new JSONObject();
                                app.put("name", Utils.read(s));
                                app.put("path", s);
                                DeBloater.put(app);
                                obj.put("DeBloater", DeBloater);
                            }
                            if (Utils.getBoolean("tomatot_extreme", false, requireActivity())) {
                                customScript.put("enabled", true);
                                TE.put(customScript);
                                obj.put("tomatot_extreme", TE);
                            }
                            if (Utils.getBoolean("tomatot_invisible", false, requireActivity())) {
                                customScript.put("enabled", true);
                                TI.put(customScript);
                                obj.put("tomatot_invisible", TI);
                            }
                            if (Utils.getBoolean("tomatot_light", false, requireActivity())) {
                                customScript.put("enabled", true);
                                TL.put(customScript);
                                obj.put("tomatot_light", TL);
                            }
                            Utils.create(obj.toString(), Environment.getExternalStorageDirectory().getPath() + "/de-bloated_list.json");
                            Utils.snackBar(mRecyclerView, getString(R.string.backup_message, Environment.getExternalStorageDirectory().getPath() + "/de-bloater_list.json"));
                        } catch (JSONException ignored) {
                        }
                    } else {
                        Utils.snackBar(mRecyclerView, getString(R.string.backup_list_empty));
                    }
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void loadUI() {
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
                            mRecyclerView.setVisibility(View.GONE);
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            if (!PackageTasks.getInactivePackageData().isEmpty()) {
                                mRecycleViewAdapter = new RecycleViewAdapter(PackageTasks.getInactivePackageData());
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            mRecyclerView.setAdapter(mRecycleViewAdapter);
                            mProgressLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mLoader = null;
                        }
                    };
                    mLoader.execute();
                }
            }, 250);
        }
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private List<String> data;

        public RecycleViewAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
            return new RecycleViewAdapter.ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
            holder.appName.setText(Utils.read(this.data.get(position)));
            if (Utils.isDarkTheme(holder.appName.getContext())) {
                holder.appName.setTextColor(Utils.getThemeAccentColor(holder.appName.getContext()));
            }
            holder.appID.setText(this.data.get(position).replace("/data/adb/modules/De-bloater",""));
            setStatus(holder, this.data.get(position));
            holder.actionLayout.setOnClickListener(v -> {
                if (Utils.isPermissionDenied(holder.actionLayout.getContext())) {
                    Utils.snackBar(holder.actionLayout, holder.actionLayout.getContext().getString(R.string.storage_access_denied));
                    return;
                }
                if (Utils.exist(this.data.get(position))) {
                    Utils.delete(this.data.get(position));
                } else {
                    Utils.create(holder.appName.getText().toString(), this.data.get(position));
                }
                setStatus(holder, this.data.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatImageButton actionIcon;
            private AppCompatImageButton appIcon;
            private AppCompatTextView appName;
            private AppCompatTextView appID;
            private AppCompatTextView actionMessage;
            private AppCompatTextView statusMessage;
            private FrameLayout actionLayout;

            public ViewHolder(View view) {
                super(view);
                this.actionIcon = view.findViewById(R.id.action_icon);
                this.appIcon = view.findViewById(R.id.icon);
                this.appName = view.findViewById(R.id.title);
                this.appID = view.findViewById(R.id.description);
                this.actionMessage = view.findViewById(R.id.action_message);
                this.statusMessage = view.findViewById(R.id.status_message);
                this.actionLayout = view.findViewById(R.id.action_layout);
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private void setStatus(ViewHolder holder, String string) {
            holder.appIcon.setImageDrawable(holder.appIcon.getContext().getResources().getDrawable(R.drawable.ic_android));
            holder.appIcon.setColorFilter(Utils.exist(string) ? Color.RED : Color.GREEN);
            holder.statusMessage.setTextColor(Utils.exist(string) ? Color.RED : Color.GREEN);
            holder.actionMessage.setTextColor(Utils.exist(string) ? Color.GREEN : Color.RED);
            holder.actionIcon.setColorFilter(Utils.exist(string) ? Color.GREEN : Color.RED);
            holder.actionMessage.setText(Utils.exist(string) ? holder.actionMessage.getContext().getString(R.string.restore) : holder.actionMessage.getContext().getString(R.string.remove));
            holder.actionIcon.setImageDrawable(Utils.exist(string) ? holder.actionIcon.getContext().getResources().getDrawable(R.drawable.ic_restore) : holder.actionIcon.getContext().getResources().getDrawable(R.drawable.ic_delete));
            holder.statusMessage.setText(Utils.exist(string) ? null : holder.statusMessage.getContext().getString(R.string.status_message_restore));
            holder.statusMessage.setVisibility(Utils.exist(string) ? View.GONE : View.VISIBLE);
        }
    }
    
}