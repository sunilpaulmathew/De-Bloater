package com.sunilpaulmathew.debloater;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.fragments.AboutFragment;
import com.sunilpaulmathew.debloater.fragments.ActivePackagesFragment;
import com.sunilpaulmathew.debloater.fragments.InactivePackagesFragment;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

import in.sunilpaulmathew.sCommon.Adapters.sPagerAdapter;
import in.sunilpaulmathew.sCommon.PackageUtils.sPackageUtils;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 27, 2020
 */

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize App Theme
        sThemeUtils.initializeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation);
        MaterialTextView mUnSupported = findViewById(R.id.unsupported);

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

        mBottomNav.setOnItemSelectedListener(
                menuItem -> {
                    if (menuItem.getItemId() == R.id.nav_active) {
                        mFragment = new ActivePackagesFragment();
                    } else if (menuItem.getItemId() == R.id.nav_inactive) {
                        mFragment = new InactivePackagesFragment();
                    } else if (menuItem.getItemId() == R.id.nav_about) {
                        mFragment = new AboutFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            mFragment).commit();
                    return true;
                }
        );
        mBottomNav.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ActivePackagesFragment()).commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (Utils.rootAccess() && Utils.magiskSupported() && !sPackageUtils.isPackageInstalled(
                "com.android.vending", this) && UpdateCheck.isSignatureMatched(this)) {
            new UpdateCheck().initialize(1, this);
        }
    }

}