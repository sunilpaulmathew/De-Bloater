package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
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

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.sunilpaulmathew.debloater.utils.EditTextInterface;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Restore;
import com.sunilpaulmathew.debloater.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import in.sunilpaulmathew.rootfilepicker.utils.FilePicker;
import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sExecutor;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AppCompatEditText mSearchWord;
    private AppCompatImageButton mMenu;
    private LinearLayout mProgressLayout;
    private MaterialTextView mProgressText;
    private RecyclerView mRecyclerView;
    private InactivePackagesAdapter mRecycleViewAdapter;
    private String mSearchText = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_packages, container, false);

        mSearchWord = mRootView.findViewById(R.id.search_word);
        AppCompatTextView mSummary = mRootView.findViewById(R.id.about_summary);
        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mProgressText = mRootView.findViewById(R.id.progress_text);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        MaterialTextView mTitle = mRootView.findViewById(R.id.page_title);
        AppCompatImageButton mSearchButton = mRootView.findViewById(R.id.search_button);
        mMenu = mRootView.findViewById(R.id.menu_button);

        mTitle.setText(getString(R.string.apps, getString(R.string.inactive)));
        mSummary.setText(getString(R.string.inactive_apps_summary));

        mMenu.setOnClickListener(v -> menuOptions(requireActivity()));

        loadUI(mSearchText);

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

        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadUI(s.toString().toLowerCase());
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mSearchWord.getVisibility() == View.VISIBLE) {
                    if (mSearchText != null) {
                        mSearchText = null;
                        mSearchWord.setText(null);
                    }
                    mSummary.setVisibility(View.VISIBLE);
                    mSearchWord.setVisibility(View.GONE);
                    return;
                }
                Utils.navigateToFragment(requireActivity());
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
                    loadUI(mSearchText);
                    break;
                case 1:
                    Utils.runCommand("svc power reboot");
                    break;
                case 2:
                    if (!PackageTasks.getInactivePackageData(null).isEmpty()) {
                        new EditTextInterface(Build.MODEL.replace(" ","_")
                                .replace("(","_").replace(")","_") + "_" +
                                Build.VERSION.SDK_INT, getString(R.string.backup_list_as), activity) {
                            @SuppressLint("StringFormatInvalid")
                            @Override
                            public void positiveButtonLister(Editable editable) {
                                String name = editable.toString().trim().replace(" ","_")
                                        .replace("(","_").replace(")","_");
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
                                    for (String s : PackageTasks.getInactivePackageData(null)) {
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
                        sCommonUtils.snackBar(mRecyclerView, getString(R.string.backup_list_empty)).show();
                    }
                    break;
                case 3:
                    FilePicker filePicker = new FilePicker(restoreResultLauncher, requireActivity());
                    filePicker.setPath(Environment.getExternalStorageDirectory().toString());
                    filePicker.setExtension("json");
                    filePicker.launch();
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void loadUI(String searchText) {
        new sExecutor() {

            @Override
            public void onPreExecute() {
                mProgressLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mRecyclerView.removeAllViews();
            }

            @Override
            public void doInBackground() {
                mRecycleViewAdapter = new InactivePackagesAdapter(PackageTasks.getInactivePackageData(searchText));
                if (searchText != null) {
                    mSearchText = searchText;
                }
            }

            @Override
            public void onPostExecute() {
                mRecyclerView.setAdapter(mRecycleViewAdapter);
                mProgressLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @SuppressLint("StringFormatInvalid")
    private final ActivityResultLauncher<Intent> restoreResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    File mSelectedFile = FilePicker.getSelectedFile();
                    if (!Restore.isValidBackup(mSelectedFile.getAbsolutePath())) {
                        sCommonUtils.snackBar(mRecyclerView, getString(R.string.restore_error_message)).show();
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
                                            Restore.restoreBackup(mSelectedFile.getAbsolutePath());
                                        }

                                        @Override
                                        public void onPostExecute() {
                                            mProgressLayout.setVisibility(View.GONE);
                                            loadUI(mSearchText);
                                        }
                                    }.execute())
                            .show();
                }
            }
    );

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