package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.adapters.AboutAdapter;
import com.sunilpaulmathew.debloater.utils.RecycleViewItem;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class AboutFragment extends Fragment {

    private ArrayList <RecycleViewItem> mData = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_about, container, false);

        mData.add(new RecycleViewItem(getString(R.string.version), (Utils.isPlayStoreAvailable(requireActivity()) ? "Pro " : "") + BuildConfig.VERSION_NAME, getResources().getDrawable(R.mipmap.ic_launcher_round), null));
        mData.add(new RecycleViewItem(getString(R.string.support), getString(R.string.support_summary), getResources().getDrawable(R.drawable.ic_support), "https://t.me/smartpack_kmanager"));
        mData.add(new RecycleViewItem(getString(R.string.source_code), getString(R.string.source_code_summary), getResources().getDrawable(R.drawable.ic_github), "https://github.com/sunilpaulmathew/De-Bloater"));
        mData.add(new RecycleViewItem(getString(R.string.report_issue), getString(R.string.report_issue_summary), getResources().getDrawable(R.drawable.ic_issue), "https://github.com/sunilpaulmathew/De-Bloater/issues/new"));
        mData.add(new RecycleViewItem(getString(R.string.change_logs), getString(R.string.change_logs_summary), getResources().getDrawable(R.drawable.ic_active), null));
        mData.add(new RecycleViewItem(getString(R.string.more_apps), getString(R.string.more_apps_summary), getResources().getDrawable(R.drawable.ic_playstore), "https://play.google.com/store/apps/dev?id=5836199813143882901"));
        mData.add(new RecycleViewItem(getString(R.string.licence), getString(R.string.licence_summary), getResources().getDrawable(R.drawable.ic_licence), null));
        if (Utils.isPlayStoreAvailable(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.rate_us), getString(R.string.rate_us_Summary), getResources().getDrawable(R.drawable.ic_rate), "https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater"));
        } else if (Utils.isFDroidAvailable(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.fdroid), getString(R.string.fdroid_summary), getResources().getDrawable(R.drawable.ic_fdroid), "https://f-droid.org/packages/com.sunilpaulmathew.debloater"));
        } else {
            mData.add(new RecycleViewItem(getString(R.string.check_update), getString(R.string.check_update_summary), getResources().getDrawable(R.drawable.ic_update), null));
        }
        mData.add(new RecycleViewItem(getString(R.string.invite_friend), getString(R.string.invite_friend_summary), getResources().getDrawable(R.drawable.ic_share), null));
        mData.add(new RecycleViewItem(getString(R.string.translations), getString(R.string.translations_summary), getResources().getDrawable(R.drawable.ic_translate), "https://poeditor.com/join/project?hash=BZS89Ev3WG"));
        if (!Utils.isPlayStoreAvailable(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.donate), getString(R.string.donate_summary), getResources().getDrawable(R.drawable.ic_donate), "https://smartpack.github.io/donation/"));
        }

        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), Utils.getSpanCount(requireActivity())));
        AboutAdapter mRecycleViewAdapter = new AboutAdapter(mData);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        return mRootView;
    }

}