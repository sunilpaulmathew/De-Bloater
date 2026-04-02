package com.sunilpaulmathew.debloater.adapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.DebloaterEntry;
import com.sunilpaulmathew.debloater.utils.PackageItem;
import com.sunilpaulmathew.debloater.utils.PackageTasks;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 03, 2021
 */

public class ActivePackagesAdapter extends RecyclerView.Adapter<ActivePackagesAdapter.ViewHolder> {

    private final List<PackageItem> data;
    private final String searchText;
    private static List<DebloaterEntry> debloaterEntries = null;

    public ActivePackagesAdapter(List<PackageItem> data, String searchText, Context context) {
        this.data = data;
        this.searchText = searchText;
        if (debloaterEntries == null) {
            debloaterEntries = PackageTasks.getUADList(context);
        }
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
            if (this.data.get(position).getPackageName() != null) {
                holder.mPackageName.setVisibility(VISIBLE);
                holder.mPackageName.setTextColor(Color.CYAN);
            } else {
                holder.mPackageName.setVisibility(GONE);
            }

            this.data.get(position).loadPackages(holder.mIcon, holder.mName, holder.mPackageName);

            holder.mPath.setText(this.data.get(position).getAPKPath());

            if (searchText != null && Common.isTextMatched(this.data.get(position).getAppName(), searchText)) {
                holder.mName.setText(Utils.fromHtml(this.data.get(position).getAppName().replace(searchText,
                        "<b><i><font color=\"" + R.color.removal_unsafe + "\">" + searchText + "</font></i></b>")));
            } else {
                holder.mName.setText(this.data.get(position).getAppName());
            }

            if (this.data.get(position).getRemovalRec() != null) {
                holder.mStatusIcon.setText(Common.getPriorityText(this.data.get(position).getRemovalRec()));
                holder.mStatusIcon.setTextColor(sCommonUtils.getColor(Common.getPriorityTextColor(this.data.get(position).getRemovalRec()), holder.mStatusIcon.getContext()));
                holder.mStatusIcon.setIconTint(ColorStateList.valueOf(sCommonUtils.getColor(Common.getPriorityTextColor(this.data.get(position).getRemovalRec()), holder.mStatusIcon.getContext())));
                holder.mStatusIcon.setIcon(sCommonUtils.getDrawable(Common.getPriorityIcon(this.data.get(position).getRemovalRec()), holder.mStatusIcon.getContext()));
                holder.mStatusIcon.setBackgroundTintList(ColorStateList.valueOf(sCommonUtils.getColor(Common.getPriorityColor(this.data.get(position).getRemovalRec()), holder.mStatusIcon.getContext())));
                holder.mStatusIcon.setVisibility(VISIBLE);
            } else {
                holder.mStatusIcon.setVisibility(GONE);
            }

            if (Utils.exist(PackageTasks.getModulePath() + this.data.get(position).getAPKPath()) || Utils.exist(PackageTasks.getModulePath() + PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()))) {
                holder.mActionIcon.setText(holder.mActionIcon.getContext().getString(R.string.restore));
                holder.mActionIcon.setIcon(sCommonUtils.getDrawable(R.drawable.ic_restore, holder.mActionIcon.getContext()));
                holder.mActionIcon.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                holder.statusMessage.setText(holder.statusMessage.getContext().getString(R.string.status_message_remove));
                holder.mActionIcon.setTextColor(Color.BLACK);
                holder.mActionIcon.setIconTint(ColorStateList.valueOf(Color.BLACK));
                holder.statusMessage.setTextColor(Color.RED);
                holder.statusMessage.setVisibility(View.VISIBLE);
            } else {
                holder.mActionIcon.setText(holder.mActionIcon.getContext().getString(R.string.remove));
                holder.mActionIcon.setIcon(sCommonUtils.getDrawable(R.drawable.ic_delete, holder.mActionIcon.getContext()));
                holder.mActionIcon.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                holder.mActionIcon.setTextColor(Color.WHITE);
                holder.mActionIcon.setIconTint(ColorStateList.valueOf(Color.WHITE));
                holder.statusMessage.setText(null);
                holder.statusMessage.setTextColor(Color.GREEN);
                holder.statusMessage.setVisibility(View.GONE);
            }

            holder.mStatusIcon.setOnClickListener(v -> new MaterialAlertDialogBuilder(v.getContext())
                    .setIcon(this.data.get(position).getAppIcon())
                    .setTitle(this.data.get(position).getAppName())
                    .setMessage(debloaterEntries.stream()
                            .filter(entry -> entry.getPackageName().equals(this.data.get(position).getPackageName()))
                            .map(DebloaterEntry::getDescription)
                            .findFirst()
                            .orElse(null))
                    .setPositiveButton(R.string.cancel, (dialog, id) -> {
                            }
                    ).show()
            );

            Common.setSlideInAnimation(holder.itemView, position);

            holder.mActionIcon.setOnClickListener(v -> {
                if (Utils.exist(PackageTasks.getModulePath() + this.data.get(position).getAPKPath()) || Utils.exist(PackageTasks.getModulePath() + PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()))) {
                    PackageTasks.revertDelete(PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()));
                } else {
                    if (this.data.get(position).isUpdatedSystemApp()) {
                        new MaterialAlertDialogBuilder(v.getContext())
                                .setIcon(this.data.get(position).getAppIcon())
                                .setTitle(this.data.get(position).getAppName())
                                .setMessage(v.getContext().getString(R.string.updated_system_app_warning, this.data.get(position).getAppName()))
                                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                                })
                                .setPositiveButton(R.string.uninstall, (dialog, id) -> {
                                    Intent remove = new Intent(Intent.ACTION_DELETE);
                                    remove.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                                    remove.setData(Uri.parse("package:" + this.data.get(position).getPackageName()));
                                    v.getContext().startActivity(remove);
                                }).show();
                    }
                    PackageTasks.setToDelete(PackageTasks.getAdjAPKPath(this.data.get(position).getAPKPath()), holder.mName.getText().toString());
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
        private final AppCompatImageButton mIcon;
        private final MaterialButton mActionIcon, mStatusIcon;
        private final MaterialTextView mName, mPackageName, mPath, statusMessage;

        public ViewHolder(View view) {
            super(view);
            this.mActionIcon = view.findViewById(R.id.action_button);
            this.mIcon = view.findViewById(R.id.icon);
            this.mName = view.findViewById(R.id.title);
            this.mPackageName = view.findViewById(R.id.packageName);
            this.mPath = view.findViewById(R.id.description);
            this.statusMessage = view.findViewById(R.id.status_message);
            this.mStatusIcon = view.findViewById(R.id.status_button);
        }
    }

}