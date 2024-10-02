package com.sunilpaulmathew.debloater.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.BuildConfig;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.activities.ChangeLogActivity;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sSerializableItems;
import in.sunilpaulmathew.sCommon.Credits.sCreditsUtils;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;
import in.sunilpaulmathew.sCommon.TranslatorUtils.sTranslatorUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    private final List<sSerializableItems> data;

    public AboutAdapter(List<sSerializableItems> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AboutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_about, parent, false);
        return new AboutAdapter.ViewHolder(rowItem);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AboutAdapter.ViewHolder holder, int position) {
        holder.Title.setText(this.data.get(position).getTextOne());
        if (sThemeUtils.isDarkTheme(holder.Title.getContext())) {
            holder.Title.setTextColor(Utils.getThemeAccentColor(holder.Title.getContext()));
        } else if (position != 0 && !this.data.get(position).getTextOne().equals(holder.Title.getContext()
                .getString(R.string.fdroid)) && !this.data.get(position).getTextTwo().equals(holder.Title
                .getContext().getString(R.string.translations))) {
            holder.mIcon.setColorFilter(Color.BLACK);
        }
        holder.Description.setText(this.data.get(position).getTextTwo());
        holder.mIcon.setImageDrawable(this.data.get(position).getIcon());
        holder.mRVLayout.setOnClickListener(v -> {
            if (this.data.get(position).getTextThree() != null) {
                sCommonUtils.launchUrl(this.data.get(position).getTextThree(), (Activity) holder.mRVLayout.getContext());
            } else if (position == 0) {
                Intent settings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                settings.setData(uri);
                holder.mRVLayout.getContext().startActivity(settings);
            } else if (position == 4) {
                Intent changeLog = new Intent(holder.mRVLayout.getContext(), ChangeLogActivity.class);
                holder.mRVLayout.getContext().startActivity(changeLog);
            } else if (position == 7) {
                UpdateCheck.isManualUpdate(true);
                new UpdateCheck().initialize(0, (Activity) holder.mRVLayout.getContext());
            } else if (position == 8) {
                new sTranslatorUtils(v.getContext().getString(R.string.app_name), "https://poeditor.com/join/project?hash=BZS89Ev3WG",
                        (Activity) v.getContext()).show();
            } else if (position == 9) {
                new sCreditsUtils(Common.getCredits(),
                        sCommonUtils.getDrawable(R.mipmap.ic_launcher, v.getContext()),
                        sCommonUtils.getDrawable(R.drawable.ic_back, v.getContext()),
                        Integer.MIN_VALUE, 20, v.getContext().getString(R.string.app_name),
                        "2024-2025, sunilpaulmathew", BuildConfig.VERSION_NAME)
                        .launchCredits(v.getContext());
            } else if (position == 10) {
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
        private final AppCompatImageButton mIcon;
        private final MaterialTextView Title;
        private final MaterialTextView Description;
        private final LinearLayout mRVLayout;

        public ViewHolder(View view) {
            super(view);
            this.mIcon = view.findViewById(R.id.icon);
            this.Title = view.findViewById(R.id.title);
            this.Description = view.findViewById(R.id.description);
            this.mRVLayout = view.findViewById(R.id.rv_about);
        }
    }

}