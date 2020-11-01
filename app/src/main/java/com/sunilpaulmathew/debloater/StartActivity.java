package com.sunilpaulmathew.debloater;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.debloater.utils.PackageTasks;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 1, 2020
 */

public class StartActivity extends AppCompatActivity {

    private AsyncTask<Void, Void, Void> mLoader;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (mLoader == null) {
            mHandler.postDelayed(new Runnable() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void run() {
                    mLoader = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            PackageTasks.getActivePackageData(StartActivity.this);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            // Launch MainActivity
                            Intent mainActivity = new Intent(StartActivity.this, MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                            mLoader = null;
                        }
                    };
                    mLoader.execute();
                }
            }, 1000);
        }
    }

}