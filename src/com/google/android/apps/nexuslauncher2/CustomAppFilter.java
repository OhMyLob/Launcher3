package com.google.android.apps.nexuslauncher2;

import android.content.ComponentName;
import android.content.Context;

import com.android.launcher3.Utilities;
import com.google.android.apps.nexuslauncher2.oml.OMLSettings;

public class CustomAppFilter extends NexusAppFilter {

    private final Context mContext;
    private final boolean hasIconPack;

    public CustomAppFilter(Context context) {
        super(context);
        mContext = context;
        hasIconPack = !Utilities.getPrefs(context).getString(OMLSettings.ICON_PACK_PREF, "").isEmpty();
    }

    @Override
    public boolean shouldShowApp(ComponentName componentName) {
        return super.shouldShowApp(componentName) &&
                (!hasIconPack || !CustomIconUtils.isPackProvider(mContext, componentName.getPackageName()));
    }
}