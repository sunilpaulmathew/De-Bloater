package com.sunilpaulmathew.debloater;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.fragments.AboutFragment;
import com.sunilpaulmathew.debloater.fragments.ActivePackagesFragment;
import com.sunilpaulmathew.debloater.fragments.InactivePackagesFragment;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class MainActivity extends AppCompatActivity {

    private boolean mExit;
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize App Theme
        Utils.initializeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialTextView mUnSupported = findViewById(R.id.unsupported);

        if (!Utils.rootAccess()) {
            mUnSupported.setText(R.string.no_root);
            mUnSupported.setVisibility(View.VISIBLE);
            return;
        } else if (!Utils.magiskSupported()) {
            mUnSupported.setText(R.string.no_magisk);
            mUnSupported.setVisibility(View.VISIBLE);
            return;
        } else if (Utils.isPermissionDenied(this)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(navListener);
        mBottomNav.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ActivePackagesFragment()).commit();
        }

        if (!Utils.getBoolean("warning_message", false, this)) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.warning_summary)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.cancel), (dialog, id) -> {
                    }).show();
            Utils.saveBoolean("warning_message", true, this);
        }

    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener
            = menuItem -> {
        Fragment selectedFragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_active:
                selectedFragment = new ActivePackagesFragment();
                break;
            case R.id.nav_inactive:
                selectedFragment = new InactivePackagesFragment();
                break;
            case R.id.nav_about:
                selectedFragment = new AboutFragment();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    };

    @Override
    public void onStart() {
        super.onStart();

        if (Utils.rootAccess() && Utils.magiskSupported() && !Utils.isPlayStoreAvailable(this) && UpdateCheck.isSignatureMatched(this)) {
            UpdateCheck.autoUpdateCheck(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (PackageTasks.mSearchWord.getVisibility() == View.VISIBLE) {
            if (PackageTasks.mSearchText != null) {
                PackageTasks.mSearchText = null;
                PackageTasks.mSearchWord.setText(null);
            }
            PackageTasks.mSearchButton.setVisibility(View.VISIBLE);
            PackageTasks.mAbout.setVisibility(View.VISIBLE);
            PackageTasks.mSearchWord.setVisibility(View.GONE);
            return;
        }
        if (mExit) {
            mExit = false;
            super.onBackPressed();
        } else {
            Utils.snackBar(findViewById(android.R.id.content), getString(R.string.press_back_exit));
            mExit = true;
            mHandler.postDelayed(() -> mExit = false, 2000);
        }
    }

}