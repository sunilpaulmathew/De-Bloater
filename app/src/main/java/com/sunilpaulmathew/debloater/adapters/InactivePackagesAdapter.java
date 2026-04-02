package com.sunilpaulmathew.debloater.adapters;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.debloater.R;
import com.sunilpaulmathew.debloater.utils.Common;
import com.sunilpaulmathew.debloater.utils.Utils;

import java.util.List;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;

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
        holder.mStatusIcon.setVisibility(GONE);
        holder.appID.setText(this.data.get(position).replace(Common.getModuleParent(),""));
        holder.appIcon.setImageDrawable(sCommonUtils.getDrawable(R.drawable.ic_android, holder.appIcon.getContext()));
        holder.appIcon.setColorFilter(Utils.exist(this.data.get(position)) ? Color.RED : Color.GREEN);

        holder.actionIcon.setTextColor(Utils.exist(this.data.get(position)) ? Color.BLACK : Color.WHITE);
        holder.actionIcon.setIconTint(ColorStateList.valueOf(Utils.exist(this.data.get(position)) ? Color.BLACK : Color.WHITE));
        holder.actionIcon.setBackgroundTintList(ColorStateList.valueOf(Utils.exist(this.data.get(position)) ? Color.GREEN : Color.RED));
        holder.actionIcon.setText(Utils.exist(this.data.get(position)) ? holder.actionIcon.getContext().getString(R.string.restore) : holder.actionIcon.getContext().getString(R.string.remove));
        holder.actionIcon.setIcon(Utils.exist(this.data.get(position)) ? sCommonUtils.getDrawable(R.drawable.ic_restore,
                holder.actionIcon.getContext()) : sCommonUtils.getDrawable(R.drawable.ic_delete, holder.actionIcon.getContext()));

        holder.statusMessage.setTextColor(Utils.exist(this.data.get(position)) ? Color.RED : Color.GREEN);
        holder.statusMessage.setText(Utils.exist(this.data.get(position)) ? null : holder.statusMessage.getContext().getString(R.string.status_message_restore));
        holder.statusMessage.setVisibility(Utils.exist(this.data.get(position)) ? View.GONE : View.VISIBLE);

        Common.setSlideInAnimation(holder.itemView, position);

        holder.actionIcon.setOnClickListener(v -> {
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
        private final AppCompatImageButton appIcon;
        private final MaterialButton actionIcon, mStatusIcon;
        private final MaterialTextView appName, appID, statusMessage;

        public ViewHolder(View view) {
            super(view);
            this.actionIcon = view.findViewById(R.id.action_button);
            this.appIcon = view.findViewById(R.id.icon);
            this.appName = view.findViewById(R.id.title);
            this.appID = view.findViewById(R.id.description);
            this.statusMessage = view.findViewById(R.id.status_message);
            this.mStatusIcon = view.findViewById(R.id.status_button);
        }
    }

}