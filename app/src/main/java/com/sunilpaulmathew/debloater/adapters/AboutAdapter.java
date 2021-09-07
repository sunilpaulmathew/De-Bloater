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
import com.sunilpaulmathew.debloater.utils.AboutItems;
import com.sunilpaulmathew.debloater.utils.UpdateCheck;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    private final ArrayList<AboutItems> data;

    public AboutAdapter(ArrayList<AboutItems> data) {
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
        holder.Title.setText(this.data.get(position).getTitle());
        if (Utils.isDarkTheme(holder.Title.getContext())) {
            holder.Title.setTextColor(Utils.getThemeAccentColor(holder.Title.getContext()));
        } else if (position != 0 && !this.data.get(position).getTitle().equals(holder.Title.getContext()
                .getString(R.string.fdroid)) && !this.data.get(position).getTitle().equals(holder.Title
                .getContext().getString(R.string.translations))) {
            holder.mIcon.setColorFilter(Color.BLACK);
        }
        holder.Description.setText(this.data.get(position).getDescription());
        holder.mIcon.setImageDrawable(this.data.get(position).getIcon());
        holder.mRVLayout.setOnClickListener(v -> {
            if (this.data.get(position).getURL() != null) {
                Utils.launchUrl(this.data.get(position).getURL(), (Activity) holder.mRVLayout.getContext());
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