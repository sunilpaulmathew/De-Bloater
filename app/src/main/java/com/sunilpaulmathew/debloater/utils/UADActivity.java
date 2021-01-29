package com.sunilpaulmathew.debloater.utils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.debloater.R;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 26, 2020
 */

public class UADActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scripts);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new UADFragment()).commit();
    }

}