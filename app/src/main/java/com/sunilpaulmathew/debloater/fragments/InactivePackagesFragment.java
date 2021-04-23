package com.sunilpaulmathew.debloater.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.sunilpaulmathew.debloater.adapters.InactivePackagesAdapter;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Restore;
import com.sunilpaulmathew.debloater.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import in.sunilpaulmathew.rootfilepicker.activities.FilePickerActivity;
import in.sunilpaulmathew.rootfilepicker.utils.FilePicker;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private AsyncTask<Void, Void, Void> mLoader;
    private final Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private MaterialTextView mProgressText;
    private RecyclerView mRecyclerView;
    private InactivePackagesAdapter mRecycleViewAdapter;

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
                        ActivityCompat.requestPermissions(requireActivity(), new String[] {
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
                            Utils.create(obj.toString(), PackageTasks.getStoragePath() + "/de-bloated_list.json");
                            Utils.snackBar(mRecyclerView, getString(R.string.backup_message, PackageTasks.getStoragePath() + "/de-bloater_list.json"));
                        } catch (JSONException ignored) {
                        }
                    } else {
                        Utils.snackBar(mRecyclerView, getString(R.string.backup_list_empty));
                    }
                    break;
                case 3:
                    if (Utils.isPermissionDenied(requireActivity())) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[] {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        FilePicker.setPath(Environment.getExternalStorageDirectory().toString());
                        FilePicker.setExtension("json");
                        Intent filePicker = new Intent(getActivity(), FilePickerActivity.class);
                        startActivityForResult(filePicker, 0);
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
                                mRecycleViewAdapter = new InactivePackagesAdapter(PackageTasks.getInactivePackageData());
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
                            mRecycleViewAdapter = new InactivePackagesAdapter(PackageTasks.getInactivePackageData());
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

        if (requestCode == 0 && data != null) {
            File mSelectedFile = FilePicker.getSelectedFile();
            new MaterialAlertDialogBuilder(requireActivity())
                    .setMessage(getString(R.string.restore_question, mSelectedFile.getName()))
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
                                Restore.restoreBackup(mSelectedFile.getAbsolutePath(), requireActivity());
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
    
}