package com.google.android.apps.nexuslauncher2;

import android.content.ComponentName;
import android.content.Context;

import com.android.launcher3.AppFilter;
import com.google.android.apps.nexuslauncher2.oml.OMLSettings;

import java.util.HashSet;

public class NexusAppFilter extends AppFilter {

    private final Context mContext;

    private final HashSet<ComponentName> mHideList = new HashSet<>();

    public NexusAppFilter(Context context) {
        mContext = context;

        //Voice Search
        mHideList.add(ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/.VoiceSearchActivity"));

        //Wallpapers
        mHideList.add(ComponentName.unflattenFromString("com.google.android.apps.wallpaper/.picker.CategoryPickerActivity"));

        //Google Now Launcher
        mHideList.add(ComponentName.unflattenFromString("com.google.android.launcher/com.google.android.launcher.StubApp"));
    }

    @Override
    public boolean shouldShowApp(ComponentName componentName) {
        return !mHideList.contains(componentName) && !OMLSettings.isComponentHidden(mContext, componentName);
    }
}
