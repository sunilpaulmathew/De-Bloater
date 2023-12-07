package com.sunilpaulmathew.debloater.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.fragments.TomatotDebloaterFragment;
import com.sunilpaulmathew.debloater.utils.Tomatot;

import in.sunilpaulmathew.sCommon.CommonUtils.sExecutor;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 4, 2020
 */

public class TomatotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scripts);

        FrameLayout mFrameLayout = findViewById(R.id.fragment_container);
        LinearLayout mLinearLayout = findViewById(R.id.layout_main);
        ProgressBar mProgressBar = findViewById(R.id.progress);

        new sExecutor() {

            @Override
            public void onPreExecute() {
                if (sThemeUtils.isDarkTheme(TomatotActivity.this)) {
                    mLinearLayout.setBackgroundColor(Color.BLACK);
                } else {
                    mLinearLayout.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void doInBackground() {
                Tomatot.setInvisibleData(TomatotActivity.this);
                Tomatot.setLightData(TomatotActivity.this);
                Tomatot.setExtremeData(TomatotActivity.this);
            }

            @Override
            public void onPostExecute() {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TomatotDebloaterFragment()).commit();
            }
        }.execute();
    }

}