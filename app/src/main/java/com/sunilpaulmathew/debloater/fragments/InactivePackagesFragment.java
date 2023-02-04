package com.sunilpaulmathew.debloater.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.adapters.InactivePackagesAdapter;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.EditTextInterface;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Restore;
import com.sunilpaulmathew.debloater.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import in.sunilpaulmathew.rootfilepicker.activities.FilePickerActivity;
import in.sunilpaulmathew.rootfilepicker.utils.FilePicker;
import in.sunilpaulmathew.sCommon.Utils.sExecutor;
import in.sunilpaulmathew.sCommon.Utils.sUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AppCompatImageButton mMenu;
    private LinearLayout mProgressLayout;
    private MaterialTextView mProgressText;
    private RecyclerView mRecyclerView;
    private InactivePackagesAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_packages, container, false);

        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mProgressText = mRootView.findViewById(R.id.progress_text);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        MaterialTextView mTitle = mRootView.findViewById(R.id.page_title);
        MaterialTextView mSummary = mRootView.findViewById(R.id.about_summary);
        Common.initializeInactiveSearchWord(mRootView, R.id.search_word);
        AppCompatImageButton mSearchButton = mRootView.findViewById(R.id.search_button);
        mMenu = mRootView.findViewById(R.id.menu_button);

        mTitle.setText(getString(R.string.apps, getString(R.string.inactive)));
        mSummary.setText(getString(R.string.inactive_apps_summary));

        mMenu.setOnClickListener(v -> menuOptions(requireActivity()));

        loadUI();

        mSearchButton.setOnClickListener(v -> {
            if (Common.getInactiveSearchWord().getVisibility() == View.VISIBLE) {
                if (Common.getSearchText() != null && !Common.getSearchText().isEmpty()) {
                    Common.setSearchText(null);
                    Common.getInactiveSearchWord().setText(null);
                }
                Common.getAboutSummary().setVisibility(View.VISIBLE);
                Common.getInactiveSearchWord().setVisibility(View.GONE);
                PackageTasks.toggleKeyboard(Common.getInactiveSearchWord(), 0, requireActivity());
            } else {
                Common.getAboutSummary().setVisibility(View.GONE);
                Common.getInactiveSearchWord().setVisibility(View.VISIBLE);
                PackageTasks.toggleKeyboard(Common.getInactiveSearchWord(), 1, requireActivity());
            }
        });

        Common.getInactiveSearchWord().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Common.setSearchText(s.toString().toLowerCase());
                loadUI();
            }
        });

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
                    loadUI();
                    break;
                case 1:
                    Utils.runCommand("svc power reboot");
                    break;
                case 2:
                    if (PackageTasks.getInactivePackageData().size() > 0) {
                        new EditTextInterface(Build.MODEL.replace(" ","_")
                                .replace("(","_").replace(")","_") + "_" +
                                Build.VERSION.SDK_INT, getString(R.string.backup_list_as), activity) {
                            @Override
                            public void positiveButtonLister(Editable editable) {
                                String name = editable.toString().trim().replace(" ","_")
                                        .replace("(","_").replace(")","_") + "_" +
                                        Build.VERSION.SDK_INT;
                                if (!name.endsWith(".json")) {
                                    name = name + ".json";
                                }
                                File jsonFile = new File(PackageTasks.getStoragePath(), name);
                                try {
                                    JSONObject obj = new JSONObject();
                                    JSONObject device = new JSONObject();
                                    JSONArray DeBloater = new JSONArray();
                                    device.put("Manufacturer", Build.MANUFACTURER);
                                    device.put("Brand", Build.BRAND);
                                    device.put("Model", Build.MODEL);
                                    device.put("Version", Build.VERSION.RELEASE);
                                    device.put("SDK", Build.VERSION.SDK_INT);
                                    obj.put("Device", device);
                                    for (String s : PackageTasks.getInactivePackageData()) {
                                        JSONObject app = new JSONObject();
                                        app.put("name", Utils.read(s));
                                        app.put("path", s);
                                        DeBloater.put(app);
                                        obj.put("DeBloater", DeBloater);
                                    }
                                    Utils.create(obj.toString(), jsonFile.getAbsolutePath());
                                    new MaterialAlertDialogBuilder(requireActivity())
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.backup_message, jsonFile.getAbsolutePath()) + "\n\n" + getString(R.string.backup_share_message))
                                            .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                                            })
                                            .setPositiveButton(getString(R.string.share_profile), (dialogInterface, i) -> {
                                                Uri uriFile = FileProvider.getUriForFile(activity,
                                                        BuildConfig.APPLICATION_ID + ".provider", jsonFile);
                                                Intent share = new Intent(Intent.ACTION_SEND);
                                                share.setType("*/*");
                                                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                                share.putExtra(Intent.EXTRA_TEXT, "De-Bloater profile for " + Build.MODEL + " (SDK: " + Build.VERSION.SDK_INT + ").");
                                                share.putExtra(Intent.EXTRA_STREAM, uriFile);
                                                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                Intent shareIntent = Intent.createChooser(share, null);
                                                startActivity(shareIntent);
                                            }).show();
                                } catch (JSONException ignored) {
                                }
                            }
                        }.show();
                    } else {
                        sUtils.snackBar(mRecyclerView, getString(R.string.backup_list_empty)).show();
                    }
                    break;
                case 3:
                    FilePicker.setPath(Environment.getExternalStorageDirectory().toString());
                    FilePicker.setExtension("json");
                    Intent filePicker = new Intent(getActivity(), FilePickerActivity.class);
                    startActivityForResult(filePicker, 0);
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void loadUI() {
        new sExecutor() {

            @Override
            public void onPreExecute() {
                mProgressLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mRecyclerView.removeAllViews();
            }

            @Override
            public void doInBackground() {
                mRecycleViewAdapter = new InactivePackagesAdapter(PackageTasks.getInactivePackageData());
            }

            @Override
            public void onPostExecute() {
                mRecyclerView.setAdapter(mRecycleViewAdapter);
                mProgressLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && data != null) {
            File mSelectedFile = FilePicker.getSelectedFile();
            if (!Restore.isValidBackup(mSelectedFile.getAbsolutePath())) {
               sUtils.snackBar(mRecyclerView, getString(R.string.restore_error_message)).show();
                return;
            }
            new MaterialAlertDialogBuilder(requireActivity())
                    .setMessage(Restore.isJSONMatched(mSelectedFile.getAbsolutePath()) ? getString(R.string.restore_question,
                            mSelectedFile.getName()) : getString(R.string.restore_mismatch_message))
                    .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                    })
                    .setPositiveButton(getString(R.string.restore), (dialogInterface, i) ->
                            new sExecutor() {

                                @Override
                                public void onPreExecute() {
                                    mProgressLayout.setVisibility(View.VISIBLE);
                                    mProgressText.setText(getString(R.string.restoring));
                                }

                                @Override
                                public void doInBackground() {
                                    Restore.restoreBackup(mSelectedFile.getAbsolutePath(), requireActivity());
                                }

                                @Override
                                public void onPostExecute() {
                                    mProgressLayout.setVisibility(View.GONE);
                                    loadUI();
                                }
                            }.execute())
                    .show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Common.getSearchText() != null) {
            Common.setSearchText(null);
            Common.getInactiveSearchWord().setText(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Common.getSearchText() != null) {
            Common.setSearchText(null);
            Common.getInactiveSearchWord().setText(null);
        }
    }
    
}