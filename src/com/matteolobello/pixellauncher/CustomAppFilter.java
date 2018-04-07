package com.matteolobello.pixellauncher;

import android.content.ComponentName;
import android.content.Context;

import com.matteolobello.pixellauncher.oml.OMLSettings;

public class CustomAppFilter extends NexusAppFilter {

    private final Context mContext;
    private final boolean hasIconPack;

    public CustomAppFilter(Context context) {
        super(context);
        mContext = context;
        hasIconPack = OMLSettings.hasIconPack(context);
    }

    @Override
    public boolean shouldShowApp(ComponentName componentName) {
        return super.shouldShowApp(componentName) &&
                (!hasIconPack || !CustomIconUtils.isPackProvider(mContext, componentName.getPackageName()));
    }
}