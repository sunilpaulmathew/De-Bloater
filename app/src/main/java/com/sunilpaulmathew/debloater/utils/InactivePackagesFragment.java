package com.sunilpaulmathew.debloater.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunilpaulmathew.debloater.R;

import java.io.File;
import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 28, 2020
 */

public class InactivePackagesFragment extends Fragment {

    private AsyncTask<Void, Void, Void> mLoader;
    private Handler mHandler = new Handler();
    private LinearLayout mProgressLayout;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_inactivepackages, container, false);

        mProgressLayout = mRootView.findViewById(R.id.progress_layout);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        loadUI();

        return mRootView;
    }

    private void loadUI() {
        if (mLoader == null) {
            mHandler.postDelayed(new Runnable() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void run() {
                    mLoader = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            mProgressLayout.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            mRecycleViewAdapter = new RecycleViewAdapter(PackageTasks.getInactivePackageData());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void recyclerViewItems) {
                            super.onPostExecute(recyclerViewItems);
                            mRecyclerView.setAdapter(mRecycleViewAdapter);
                            mProgressLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mLoader = null;
                        }
                    };
                    mLoader.execute();
                }
            }, 250);
        }
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private List<String> data;

        public RecycleViewAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
            return new RecycleViewAdapter.ViewHolder(rowItem);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
            holder.appName.setText(new File(this.data.get(position)).getName());
            holder.appID.setText(this.data.get(position).replace("/data/adb/modules/De-bloater",""));
            holder.statusMessage.setTextColor(Utils.exist(this.data.get(position)) ? Color.RED : Color.GREEN);
            holder.actionMessage.setTextColor(Utils.exist(this.data.get(position)) ? Color.GREEN : Color.RED);
            holder.actionIcon.setColorFilter(Utils.exist(this.data.get(position)) ? Color.GREEN : Color.RED);
            holder.actionMessage.setText(Utils.exist(this.data.get(position)) ? holder.actionMessage.getContext().getString(R.string.restore) : holder.actionMessage.getContext().getString(R.string.remove));
            holder.actionIcon.setImageDrawable(Utils.exist(this.data.get(position)) ? holder.actionIcon.getContext().getResources().getDrawable(R.drawable.ic_restore) : holder.actionIcon.getContext().getResources().getDrawable(R.drawable.ic_delete));
            holder.statusMessage.setText(Utils.exist(this.data.get(position)) ? null : holder.statusMessage.getContext().getString(R.string.status_message_restore));
            holder.statusMessage.setVisibility(Utils.exist(this.data.get(position)) ? View.GONE : View.VISIBLE);
            holder.actionLayout.setOnClickListener(v -> {
                if (Utils.isPermissionDenied(holder.actionLayout.getContext())) {
                    Utils.snackBar(holder.actionLayout, holder.actionLayout.getContext().getString(R.string.storage_access_denied));
                    return;
                }
                if (Utils.exist(this.data.get(position))) {
                    Utils.delete(this.data.get(position));
                } else {
                    Utils.create("", this.data.get(position));
                }
                try {
                    holder.actionMessage.setText(Utils.exist(this.data.get(position)) ?
                            holder.actionLayout.getContext().getString(R.string.restore) : holder.actionLayout.getContext().getString(R.string.remove));
                    holder.actionIcon.setImageDrawable(Utils.exist(this.data.get(position)) ?
                            holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_restore) : holder.actionLayout.getContext().getResources().getDrawable(R.drawable.ic_delete));
                    holder.statusMessage.setTextColor(Utils.exist(this.data.get(position)) ?
                            Color.RED : Color.GREEN);
                    holder.actionMessage.setTextColor(Utils.exist(this.data.get(position)) ?
                            Color.GREEN : Color.RED);
                    holder.actionIcon.setColorFilter(Utils.exist(this.data.get(position)) ?
                            Color.GREEN : Color.RED);
                    holder.statusMessage.setText(Utils.exist(this.data.get(position)) ?
                            holder.actionLayout.getContext().getString(R.string.status_message_restore) : null);
                    holder.statusMessage.setVisibility(Utils.exist(this.data.get(position)) ? View.GONE : View.VISIBLE);
                } catch (IndexOutOfBoundsException ignored) {}
                notifyDataSetChanged();
            });

        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatImageButton actionIcon;
            private AppCompatTextView appName;
            private AppCompatTextView appID;
            private AppCompatTextView actionMessage;
            private AppCompatTextView statusMessage;
            private FrameLayout actionLayout;

            public ViewHolder(View view) {
                super(view);
                this.actionIcon = view.findViewById(R.id.action_icon);
                this.appName = view.findViewById(R.id.title);
                this.appID = view.findViewById(R.id.description);
                this.actionMessage = view.findViewById(R.id.action_message);
                this.statusMessage = view.findViewById(R.id.status_message);
                this.actionLayout = view.findViewById(R.id.action_layout);
            }
        }
    }
    
}