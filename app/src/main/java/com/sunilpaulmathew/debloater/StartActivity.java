package com.sunilpaulmathew.debloater;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.sunilpaulmathew.debloater.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 1, 2020
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        AppCompatTextView mMagiskCredit = findViewById(R.id.magisk_credit);
        if (Utils.isDarkTheme(this)) {
            mMagiskCredit.setTextColor(Utils.getThemeAccentColor(this));
        }

        new Handler().postDelayed(() -> {
            // Launch MainActivity
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }, 1000);
    }

}