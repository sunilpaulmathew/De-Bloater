package com.sunilpaulmathew.debloater.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.fragments.TomatotDebloaterFragment;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 4, 2020
 */

public class TomatotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scripts);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new TomatotDebloaterFragment()).commit();
    }

}