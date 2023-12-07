package com.sunilpaulmathew.debloater.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class InactivePackagesAdapter extends RecyclerView.Adapter<InactivePackagesAdapter.ViewHolder> {

    private final List<String> data;

    public InactivePackagesAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public InactivePackagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
        return new InactivePackagesAdapter.ViewHolder(rowItem);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull InactivePackagesAdapter.ViewHolder holder, int position) {
        holder.appName.setText(Utils.read(this.data.get(position)));
        if (sThemeUtils.isDarkTheme(holder.appName.getContext())) {
            holder.appName.setTextColor(Utils.getThemeAccentColor(holder.appName.getContext()));
        }
        holder.appID.setText(this.data.get(position).replace(Common.getModuleParent(),""));
        holder.appIcon.setImageDrawable(sCommonUtils.getDrawable(R.drawable.ic_android, holder.appIcon.getContext()));
        holder.appIcon.setColorFilter(Utils.exist(this.data.get(position)) ? Color.RED : Color.GREEN);
        holder.statusMessage.setTextColor(Utils.exist(this.data.get(position)) ? Color.RED : Color.GREEN);
        holder.actionMessage.setTextColor(Utils.exist(this.data.get(position)) ? Color.GREEN : Color.RED);
        holder.actionIcon.setColorFilter(Utils.exist(this.data.get(position)) ? Color.GREEN : Color.RED);
        holder.actionMessage.setText(Utils.exist(this.data.get(position)) ? holder.actionMessage.getContext().getString(R.string.restore) : holder.actionMessage.getContext().getString(R.string.remove));
        holder.actionIcon.setImageDrawable(Utils.exist(this.data.get(position)) ? sCommonUtils.getDrawable(R.drawable.ic_restore,
                holder.actionMessage.getContext()) : sCommonUtils.getDrawable(R.drawable.ic_delete, holder.actionMessage.getContext()));
        holder.statusMessage.setText(Utils.exist(this.data.get(position)) ? null : holder.statusMessage.getContext().getString(R.string.status_message_restore));
        holder.statusMessage.setVisibility(Utils.exist(this.data.get(position)) ? View.GONE : View.VISIBLE);
        holder.actionLayout.setOnClickListener(v -> {
            if (Utils.exist(this.data.get(position))) {
                Utils.delete(this.data.get(position));
            } else {
                Utils.create(holder.appName.getText().toString(), this.data.get(position));
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageButton actionIcon;
        private final AppCompatImageButton appIcon;
        private final MaterialTextView appName;
        private final MaterialTextView appID;
        private final MaterialTextView actionMessage;
        private final MaterialTextView statusMessage;
        private final FrameLayout actionLayout;

        public ViewHolder(View view) {
            super(view);
            this.actionIcon = view.findViewById(R.id.action_icon);
            this.appIcon = view.findViewById(R.id.icon);
            this.appName = view.findViewById(R.id.title);
            this.appID = view.findViewById(R.id.description);
            this.actionMessage = view.findViewById(R.id.action_message);
            this.statusMessage = view.findViewById(R.id.status_message);
            this.actionLayout = view.findViewById(R.id.action_layout);
        }
    }

}