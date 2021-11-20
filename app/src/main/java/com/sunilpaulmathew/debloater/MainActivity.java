package com.sunilpaulmathew.debloater;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.fragments.AboutFragment;
import com.sunilpaulmathew.debloater.fragments.ActivePackagesFragment;
import com.sunilpaulmathew.debloater.fragments.InactivePackagesFragment;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

import in.sunilpaulmathew.sCommon.Adapters.sPagerAdapter;
import in.sunilpaulmathew.sCommon.Utils.sThemeUtils;
import in.sunilpaulmathew.sCommon.Utils.sUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class MainActivity extends AppCompatActivity {

    private boolean mExit;
    private final Handler mHandler = new Handler();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize App Theme
        sThemeUtils.initializeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation);
        MaterialTextView mUnSupported = findViewById(R.id.unsupported);
        ViewPager mViewPager = findViewById(R.id.view_pager);

        if (!Utils.rootAccess()) {
            mUnSupported.setText(R.string.no_root);
            mUnSupported.setVisibility(View.VISIBLE);
            return;
        } else if (!Utils.magiskSupported()) {
            mUnSupported.setText(R.string.no_magisk);
            mUnSupported.setVisibility(View.VISIBLE);
            return;
        }

        sPagerAdapter adapter = new sPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new ActivePackagesFragment(), null);
        adapter.AddFragment(new InactivePackagesFragment(), null);
        adapter.AddFragment(new AboutFragment(), null);

        mViewPager.setAdapter(adapter);

        mBottomNav.setOnItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_active:
                            mViewPager.setCurrentItem(0);
                            break;
                        case R.id.nav_inactive:
                            mViewPager.setCurrentItem(1);
                            break;
                        case R.id.nav_about:
                            mViewPager.setCurrentItem(2);
                            break;
                    }
                    return false;
                }
        );
        mBottomNav.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            mViewPager.setCurrentItem(0);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (Utils.rootAccess() && Utils.magiskSupported() && !Utils.isPlayStoreAvailable(this) && UpdateCheck.isSignatureMatched(this)) {
            new UpdateCheck().initialize(1, this);
        }
    }

    @Override
    public void onBackPressed() {
        if (Common.getSearchWord().getVisibility() == View.VISIBLE) {
            if (Common.getSearchText() != null) {
                Common.setSearchText(null);
                Common.getSearchWord().setText(null);
            }
            Common.getSearchButton().setVisibility(View.VISIBLE);
            Common.getAboutSummary().setVisibility(View.VISIBLE);
            Common.getSearchWord().setVisibility(View.GONE);
            return;
        }
        if (mExit) {
            mExit = false;
            super.onBackPressed();
        } else {
            sUtils.snackBar(findViewById(android.R.id.content), getString(R.string.press_back_exit)).show();
            mExit = true;
            mHandler.postDelayed(() -> mExit = false, 2000);
        }
    }

}