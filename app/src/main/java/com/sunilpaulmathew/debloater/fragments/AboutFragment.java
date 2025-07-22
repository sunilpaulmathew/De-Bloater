package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.adapters.AboutAdapter;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sSerializableItems;
import in.sunilpaulmathew.sCommon.PackageUtils.sPackageUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class AboutFragment extends Fragment {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_about, container, false);

        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), Utils.getSpanCount(requireActivity())));
        AboutAdapter mRecycleViewAdapter = new AboutAdapter(getData());
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Utils.navigateToFragment(requireActivity());
            }
        });

        return mRootView;
    }

    private List<sSerializableItems> getData() {
        List <sSerializableItems> mData = new ArrayList<>();
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.mipmap.ic_launcher_round, requireActivity()), getString(R.string.version).replace(": %s",""), (sPackageUtils.isPackageInstalled(
                "com.android.vending", requireActivity()) ? "Pro " : "") + BuildConfig.VERSION_NAME, null));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_support, requireActivity()), getString(R.string.support), getString(R.string.support_summary),"https://t.me/smartpack_kmanager"));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_github, requireActivity()), getString(R.string.source_code), getString(R.string.source_code_summary),"https://github.com/sunilpaulmathew/De-Bloater"));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_issue, requireActivity()), getString(R.string.report_issue), getString(R.string.report_issue_summary),"https://github.com/sunilpaulmathew/De-Bloater/issues/new"));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_active, requireActivity()), getString(R.string.change_logs), getString(R.string.change_logs_summary), null));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_playstore, requireActivity()), getString(R.string.more_apps), getString(R.string.more_apps_summary), "https://play.google.com/store/apps/dev?id=5836199813143882901"));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_licence, requireActivity()), getString(R.string.licence), getString(R.string.licence_summary), "https://www.gnu.org/licenses/gpl-3.0-standalone.html"));
        if (sPackageUtils.isPackageInstalled("com.android.vending", requireActivity())) {
            mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_rate, requireActivity()), getString(R.string.rate_us), getString(R.string.rate_us_Summary), "https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater"));
        } else if (sPackageUtils.isPackageInstalled("org.fdroid.fdroid", requireActivity())) {
            mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_fdroid, requireActivity()), getString(R.string.fdroid), getString(R.string.fdroid_summary), "https://f-droid.org/packages/com.sunilpaulmathew.debloater"));
        } else {
            mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_update, requireActivity()),getString(R.string.check_update), getString(R.string.check_update_summary), null));
        }
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_translate, requireActivity()), getString(R.string.translations), getString(R.string.translations_summary), null));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_credits, requireActivity()), getString(R.string.credits), getString(R.string.credits_summary), null));
        mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_share, requireActivity()), getString(R.string.invite_friend), getString(R.string.invite_friend_summary), null));
        if (!sPackageUtils.isPackageInstalled("com.android.vending", requireActivity())) {
            mData.add(new sSerializableItems(sCommonUtils.getDrawable(R.drawable.ic_donate, requireActivity()), getString(R.string.donate), getString(R.string.donate_summary), "https://smartpack.github.io/donation/"));
        }
        return mData;
    }

}