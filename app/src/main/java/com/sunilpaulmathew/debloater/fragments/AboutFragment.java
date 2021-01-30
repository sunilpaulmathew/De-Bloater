package com.sunilpaulmathew.debloater.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.activities.ChangeLogActivity;
import com.sunilpaulmathew.debloater.activities.LicenceActivity;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.io.Serializable;
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

        mData.add(new RecycleViewItem(getString(R.string.version), (Utils.isProUser(requireActivity()) ? "Pro " : "") + BuildConfig.VERSION_NAME, getResources().getDrawable(R.mipmap.ic_launcher_round), null));
        mData.add(new RecycleViewItem(getString(R.string.support), getString(R.string.support_summary), getResources().getDrawable(R.drawable.ic_support), "https://t.me/smartpack_kmanager"));
        mData.add(new RecycleViewItem(getString(R.string.source_code), getString(R.string.source_code_summary), getResources().getDrawable(R.drawable.ic_github), "https://github.com/sunilpaulmathew/De-Bloater"));
        mData.add(new RecycleViewItem(getString(R.string.report_issue), getString(R.string.report_issue_summary), getResources().getDrawable(R.drawable.ic_issue), "https://github.com/sunilpaulmathew/De-Bloater/issues/new"));
        mData.add(new RecycleViewItem(getString(R.string.change_logs), getString(R.string.change_logs_summary), getResources().getDrawable(R.drawable.ic_active), null));
        mData.add(new RecycleViewItem(getString(R.string.more_apps), getString(R.string.more_apps_summary), getResources().getDrawable(R.drawable.ic_playstore), "https://play.google.com/store/apps/dev?id=5836199813143882901"));
        mData.add(new RecycleViewItem(getString(R.string.licence), getString(R.string.licence_summary), getResources().getDrawable(R.drawable.ic_licence), null));
        if (Utils.isProUser(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.rate_us), getString(R.string.rate_us_Summary), getResources().getDrawable(R.drawable.ic_rate), "https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater"));
        } else if (UpdateCheck.isSignatureMatched(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.check_update), getString(R.string.check_update_summary), getResources().getDrawable(R.drawable.ic_update), null));
        } else {
            mData.add(new RecycleViewItem(getString(R.string.fdroid), getString(R.string.fdroid_summary), getResources().getDrawable(R.drawable.ic_fdroid), "https://f-droid.org/packages/com.sunilpaulmathew.debloater"));
        }
        mData.add(new RecycleViewItem(getString(R.string.invite_friend), getString(R.string.invite_friend_summary), getResources().getDrawable(R.drawable.ic_share), null));
        mData.add(new RecycleViewItem(getString(R.string.translations), getString(R.string.translations_summary), getResources().getDrawable(R.drawable.ic_translate), "https://github.com/sunilpaulmathew/De-Bloater/blob/master/app/src/main/res/values/strings.xml"));
        if (!Utils.isProUser(requireActivity())) {
            mData.add(new RecycleViewItem(getString(R.string.donate), getString(R.string.donate_summary), getResources().getDrawable(R.drawable.ic_donate), "https://smartpack.github.io/donation/"));
        }

        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), Utils.getSpanCount(requireActivity())));
        RecycleViewAdapter mRecycleViewAdapter = new RecycleViewAdapter(mData);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        return mRootView;
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private ArrayList<RecycleViewItem> data;

        public RecycleViewAdapter(ArrayList<RecycleViewItem> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_about, parent, false);
            return new RecycleViewAdapter.ViewHolder(rowItem);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
            holder.Title.setText(this.data.get(position).getTitle());
            if (Utils.isDarkTheme(holder.Title.getContext())) {
                holder.Title.setTextColor(Utils.getThemeAccentColor(holder.Title.getContext()));
            } else if (position != 0 && !this.data.get(position).getTitle().equals(holder.Title.getContext().getString(R.string.fdroid))){
                holder.mIcon.setColorFilter(Color.BLACK);
            }
            holder.Description.setText(this.data.get(position).getDescription());
            holder.mIcon.setImageDrawable(this.data.get(position).getIcon());
            holder.mRVLayout.setOnClickListener(v -> {
                if (this.data.get(position).getURL() != null) {
                    Utils.launchUrl(holder.mRVLayout, this.data.get(position).getURL(), holder.mRVLayout.getContext());
                } else if (position == 0) {
                    Intent settings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                    settings.setData(uri);
                    holder.mRVLayout.getContext().startActivity(settings);
                } else if (position == 4) {
                    Intent changeLog = new Intent(holder.mRVLayout.getContext(), ChangeLogActivity.class);
                    holder.mRVLayout.getContext().startActivity(changeLog);
                } else if (position == 6) {
                    Intent licence = new Intent(holder.mRVLayout.getContext(), LicenceActivity.class);
                    holder.mRVLayout.getContext().startActivity(licence);
                } else if (position == 7) {
                    UpdateCheck.manualUpdateCheck(holder.mRVLayout, holder.mRVLayout.getContext());
                } else if (position == 8) {
                    Intent shareapp = new Intent();
                    shareapp.setAction(Intent.ACTION_SEND);
                    shareapp.putExtra(Intent.EXTRA_SUBJECT, holder.mRVLayout.getContext().getString(R.string.app_name));
                    shareapp.putExtra(Intent.EXTRA_TEXT, holder.mRVLayout.getContext().getString(R.string.share_app_message, BuildConfig.VERSION_NAME) + Utils.getAppStoreURL(holder.mRVLayout.getContext()));
                    shareapp.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(shareapp, null);
                    holder.mRVLayout.getContext().startActivity(shareIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatImageButton mIcon;
            private MaterialTextView Title;
            private MaterialTextView Description;
            private LinearLayout mRVLayout;

            public ViewHolder(View view) {
                super(view);
                this.mIcon = view.findViewById(R.id.icon);
                this.Title = view.findViewById(R.id.title);
                this.Description = view.findViewById(R.id.description);
                this.mRVLayout = view.findViewById(R.id.rv_about);
            }
        }
    }

    private static class RecycleViewItem implements Serializable {
        private String mTitle;
        private String mDescription;
        private Drawable mIcon;
        private String mURL;

        public RecycleViewItem(String title, String description, Drawable icon, String url) {
            this.mTitle = title;
            this.mDescription = description;
            this.mIcon = icon;
            this.mURL = url;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getDescription() {
            return mDescription;
        }

        public Drawable getIcon() {
            return mIcon;
        }

        public String getURL() {
            return mURL;
        }
    }

}