package com.sunilpaulmathew.debloater.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.MainActivity;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.AsyncTasks;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 1, 2020
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        MaterialCardView mStartCard = findViewById(R.id.start_card);
        MaterialTextView mWarning = findViewById(R.id.warning);
        ProgressBar mProgress = findViewById(R.id.progress);

        if (!Utils.getBoolean("warning_message", false, this)) {
            mProgress.setVisibility(View.GONE);
            mWarning.setVisibility(View.VISIBLE);
            mStartCard.setVisibility(View.VISIBLE);
        } else {
            loadUI(StartActivity.this);
        }

        mStartCard.setOnClickListener(v -> {
            mProgress.setVisibility(View.VISIBLE);
            mWarning.setVisibility(View.GONE);
            mStartCard.setVisibility(View.GONE);
            loadUI(StartActivity.this);
        });
    }

    private static void loadUI(Activity activity) {
        new AsyncTasks() {

            @Override
            public void onPreExecute() {
                if (!Utils.getBoolean("warning_message", false, activity)) {
                    Utils.saveBoolean("warning_message", true, activity);
                }
            }

            @Override
            public void doInBackground() {
                // Acquire information about installed apps
                Common.setRawData(PackageTasks.getRawData(activity));
            }

            @Override
            public void onPostExecute() {
                // Launch MainActivity
                Intent mainActivity = new Intent(activity, MainActivity.class);
                activity.startActivity(mainActivity);
                activity.finish();
            }
        }.execute();
    }

}