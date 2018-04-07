package com.matteolobello.pixellauncher;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.launcher3.AppInfo;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;
import com.matteolobello.pixellauncher.oml.OMLSettings;

import java.util.List;

public class HideAppsActivity extends Activity {

    ProgressBar progressBar;
    RecyclerView recyclerView;

    HideAppsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_apps);
        bindViews();
        query();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
            case android.R.id.home:
                handled = true;
                onBackPressed();
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    }

    private void bindViews() {
        progressBar = findViewById(R.id.activity_hide_apps_progress);
        recyclerView = findViewById(R.id.activity_hide_apps_recycler_view);

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new HideAppsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void query() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        List<AppInfo> apps = LauncherAppState.getInstance(this).getModel().cloneAllAppInfo();
        adapter.setApps(apps);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private class HideAppsAdapter extends RecyclerView.Adapter<HideAppsViewHolder> {

        private List<AppInfo> apps;

        @Override
        public HideAppsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hide_app, viewGroup, false);
            return new HideAppsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HideAppsViewHolder hideAppsViewHolder, int i) {
            hideAppsViewHolder.setAppInfo(apps.get(i));
        }

        @Override
        public int getItemCount() {
            return apps == null ? 0 : apps.size();
        }

        public void setApps(List<AppInfo> apps) {
            this.apps = apps;
            notifyDataSetChanged();
        }
    }

    private class HideAppsViewHolder extends RecyclerView.ViewHolder {

        ViewGroup container;
        ImageView launcherIcon;
        TextView appName;
        TextView className;
        CheckBox isHidden;

        AppInfo appInfo;

        public HideAppsViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.hide_app_container);
            launcherIcon = itemView.findViewById(R.id.hide_app_icon);
            appName = itemView.findViewById(R.id.hide_app_name);
            className = itemView.findViewById(R.id.hide_app_class_name);
            isHidden = itemView.findViewById(R.id.hide_app_checkbox);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleAppVisibility();
                }
            });
        }

        public void setAppInfo(AppInfo appInfo) {
            this.appInfo = appInfo;

            launcherIcon.setImageBitmap(appInfo.iconBitmap);
            appName.setText(appInfo.title);
            className.setText(appInfo.componentName.getClassName());
            isHidden.setChecked(OMLSettings.isComponentHidden(itemView.getContext(), appInfo.componentName));
        }

        private void toggleAppVisibility() {
            boolean currentState = isHidden.isChecked();
            OMLSettings.setComponentHidden(itemView.getContext(), appInfo.componentName, !currentState);
            isHidden.setChecked(!currentState);

            LauncherAppState.getInstance(itemView.getContext()).getModel().forceReload();
        }
    }
}