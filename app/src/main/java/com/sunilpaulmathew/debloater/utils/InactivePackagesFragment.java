package com.sunilpaulmathew.debloater.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private AsyncTask<Void, Void, Void> mLoader;
    private Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private MaterialTextView mProgressText;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;
    private String mPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_inactivepackages, container, false);

        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mProgressText = mRootView.findViewById(R.id.progress_text);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        MaterialTextView mPageTitle = mRootView.findViewById(R.id.page_title);
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
        menu.add(Menu.NONE, 3, Menu.NONE, R.string.restore);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    PackageTasks.removeModule(activity);
                    reload();
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
                            String[] apps = PackageTasks.getInactivePackageData().toString().substring(1, PackageTasks.getInactivePackageData().toString().length() - 1).split(", ");
                            for (String s : apps) {
                                JSONObject app = new JSONObject();
                                app.put("name", Utils.read(s));
                                app.put("path", s);
                                DeBloater.put(app);
                                obj.put("DeBloater", DeBloater);
                            }
                            Utils.create(obj.toString(), Environment.getExternalStorageDirectory().getPath() + "/de-bloated_list.json");
                            Utils.snackBar(mRecyclerView, getString(R.string.backup_message, Environment.getExternalStorageDirectory().getPath() + "/de-bloater_list.json"));
                        } catch (JSONException ignored) {
                        }
                    } else {
                        Utils.snackBar(mRecyclerView, getString(R.string.backup_list_empty));
                    }
                    break;
                case 3:
                    Intent restore = new Intent(Intent.ACTION_GET_CONTENT);
                    restore.setType("*/*");
                    startActivityForResult(restore, 0);
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

    private void reload() {
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
                            mRecyclerView.removeAllViews();
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            mRecycleViewAdapter = new RecycleViewAdapter(PackageTasks.getInactivePackageData());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            mRecyclerView.setAdapter(mRecycleViewAdapter);
                            mRecycleViewAdapter.notifyDataSetChanged();
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            assert uri != null;
            File file = new File(Objects.requireNonNull(uri.getPath()));
            if (Utils.isDocumentsUI(uri)) {
                @SuppressLint("Recycle") Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    mPath = Environment.getExternalStorageDirectory().toString() + "/Download/" +
                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } else {
                mPath = Utils.getPath(file);
            }
            if (!Restore.validBackup(mPath)) {
                Utils.snackBar(mRecyclerView, getString(R.string.restore_error_message));
                return;
            }
            new MaterialAlertDialogBuilder(requireActivity())
                    .setMessage(getString(R.string.restore_question, new File(mPath).getName()))
                    .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                    })
                    .setPositiveButton(getString(R.string.restore), (dialogInterface, i) -> {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                mProgressLayout.setVisibility(View.VISIBLE);
                                mProgressText.setText(getString(R.string.restoring));
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                Restore.restoreBackup(mPath, requireActivity());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void recyclerViewItems) {
                                super.onPostExecute(recyclerViewItems);
                                mProgressLayout.setVisibility(View.GONE);
                                reload();
                            }
                        }.execute();
                    })
                    .show();
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
            private MaterialTextView appName;
            private MaterialTextView appID;
            private MaterialTextView actionMessage;
            private MaterialTextView statusMessage;
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