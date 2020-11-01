package com.sunilpaulmathew.debloater;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sunilpaulmathew.debloater.utils.AboutFragment;
import com.sunilpaulmathew.debloater.utils.ActivePackagesFragment;
import com.sunilpaulmathew.debloater.utils.InactivePackagesFragment;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class MainActivity extends AppCompatActivity {

    private boolean mExit;
    private BottomNavigationView mBottomNav;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize App Theme
        Utils.initializeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatTextView mUnSupported = findViewById(R.id.unsupported);

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

        mBottomNav = findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ActivePackagesFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
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

        if (Utils.rootAccess() && Utils.magiskSupported()) {
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
            Utils.snackBar(mBottomNav, getString(R.string.press_back_exit));
            mExit = true;
            mHandler.postDelayed(() -> mExit = false, 2000);
        }
    }

}