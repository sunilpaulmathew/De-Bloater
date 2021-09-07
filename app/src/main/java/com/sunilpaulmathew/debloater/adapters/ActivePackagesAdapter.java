package com.sunilpaulmathew.debloater.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.PackageItem;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class ActivePackagesAdapter extends RecyclerView.Adapter<ActivePackagesAdapter.ViewHolder> {

    private final List<PackageItem> data;

    public ActivePackagesAdapter(List<PackageItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ActivePackagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
        return new ActivePackagesAdapter.ViewHolder(rowItem);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ActivePackagesAdapter.ViewHolder holder, int position) {
        try {
            holder.mIcon.setImageDrawable(this.data.get(position).getAppIcon());
            holder.mPath.setText(this.data.get(position).getAPKPath());
            if (Common.getSearchText() != null && Common.isTextMatched(this.data.get(position).getAppName())) {
                holder.mName.setText(Utils.fromHtml(this.data.get(position).getAppName().replace(Common.getSearchText(),
                        "<b><i><font color=\"" + Color.RED + "\">" + Common.getSearchText() + "</font></i></b>")));
            } else {
                holder.mName.setText(this.data.get(position).getAppName());
            }
            if (Utils.isDarkTheme(holder.mName.getContext())) {
                holder.mName.setTextColor(Utils.getThemeAccentColor(holder.mName.getContext()));
            }
            if (Utils.exist(PackageTasks.getModulePath() + this.data.get(position).getAPKPath()) || Utils.exist(PackageTasks.getModulePath() + PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()))) {
                holder.actionMessage.setText(holder.actionLayout.getContext().getString(R.string.restore));
                holder.mActionIcon.setImageDrawable(holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_restore));
                holder.statusMessage.setTextColor(Color.RED);
                holder.statusMessage.setVisibility(View.VISIBLE);
                holder.actionMessage.setTextColor(Color.GREEN);
                holder.mActionIcon.setColorFilter(Color.GREEN);
                holder.statusMessage.setText(holder.actionLayout.getContext().getString(R.string.status_message_remove));
            } else {
                holder.actionMessage.setText(holder.actionLayout.getContext().getString(R.string.remove));
                holder.mActionIcon.setImageDrawable(holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_delete));
                holder.statusMessage.setTextColor(Color.GREEN);
                holder.statusMessage.setVisibility(View.GONE);
                holder.actionMessage.setTextColor(Color.RED);
                holder.mActionIcon.setColorFilter(Color.RED);
                holder.statusMessage.setText(null);
            }
            holder.actionLayout.setOnClickListener(v -> {
                if (Utils.exist(PackageTasks.getModulePath() + this.data.get(position).getAPKPath()) || Utils.exist(PackageTasks.getModulePath() + PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()))) {
                    PackageTasks.revertDelete(PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()));
                } else {
                    if (data.get(position).isUpdatedSystemApp()) {
                        new MaterialAlertDialogBuilder(v.getContext())
                                .setIcon(data.get(position).getAppIcon())
                                .setTitle(data.get(position).getAppName())
                                .setMessage(v.getContext().getString(R.string.updated_system_app_warning, data.get(position).getAppName()))
                                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                                })
                                .setPositiveButton(R.string.uninstall, (dialog, id) -> {
                                    Intent remove = new Intent(Intent.ACTION_DELETE);
                                    remove.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                                    remove.setData(Uri.parse("package:" + data.get(position).getPackageName()));
                                    v.getContext().startActivity(remove);
                                }).show();
                    }
                    PackageTasks.setToDelete(PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()), holder.mName.getText().toString(), holder.actionLayout.getContext());
                }
                notifyItemChanged(position);
            });
        } catch (NullPointerException ignored) {}
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageButton mActionIcon;
        private final AppCompatImageButton mIcon;
        private final MaterialTextView mName;
        private final MaterialTextView mPath;
        private final MaterialTextView actionMessage;
        private final MaterialTextView statusMessage;
        private final FrameLayout actionLayout;

        public ViewHolder(View view) {
            super(view);
            this.mActionIcon = view.findViewById(R.id.action_icon);
            this.mIcon = view.findViewById(R.id.icon);
            this.mName = view.findViewById(R.id.title);
            this.mPath = view.findViewById(R.id.description);
            this.actionMessage = view.findViewById(R.id.action_message);
            this.statusMessage = view.findViewById(R.id.status_message);
            this.actionLayout = view.findViewById(R.id.action_layout);
        }
    }

}