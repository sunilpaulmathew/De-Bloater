package com.sunilpaulmathew.debloater.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.fragments.UADFragment;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.UAD;

import in.sunilpaulmathew.sCommon.Utils.sExecutor;
import in.sunilpaulmathew.sCommon.Utils.sUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 26, 2020
 */

public class UADActivity extends AppCompatActivity {

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
                if (sUtils.isDarkTheme(UADActivity.this)) {
                    mLinearLayout.setBackgroundColor(Color.BLACK);
                } else {
                    mLinearLayout.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void doInBackground() {
                UAD.setUADData(UAD.getCarrier(), Common.getCarrier(), UADActivity.this);
                UAD.setUADData(UAD.getHuawei(), Common.getHuawei(), UADActivity.this);
                UAD.setUADData(UAD.getLG(), Common.getLG(), UADActivity.this);
                UAD.setUADData(UAD.getMiscellaneous(), Common.getMisc(), UADActivity.this);
                UAD.setUADData(UAD.getMotorola(), Common.getMoto(), UADActivity.this);
                UAD.setUADData(UAD.getNokia(), Common.getNokia(), UADActivity.this);
                UAD.setUADData(UAD.getOppo(), Common.getOppo(), UADActivity.this);
                UAD.setUADData(UAD.getSamsung(), Common.getSamsung(), UADActivity.this);
                UAD.setUADData(UAD.getSony(), Common.getSony(), UADActivity.this);
                UAD.setUADData(UAD.getXiaomi(), Common.getXiaomi(), UADActivity.this);
                UAD.setUADData(UAD.getZTE(), Common.getZTE(), UADActivity.this);
                UAD.setUADData(UAD.getAOSP(), Common.getAOSP(), UADActivity.this);
                UAD.setUADData(UAD.getGoogle(), Common.getGoogle(), UADActivity.this);
                UAD.setUADData(UAD.getASUS(), Common.getAsus(), UADActivity.this);
                UAD.setUADData(UAD.getOnePlus(), Common.getOnePlus(), UADActivity.this);
            }

            @Override
            public void onPostExecute() {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UADFragment()).commit();
            }
        }.execute();
    }

}