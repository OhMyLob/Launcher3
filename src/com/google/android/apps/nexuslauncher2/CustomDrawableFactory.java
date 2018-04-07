package com.google.android.apps.nexuslauncher2;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.launcher3.FastBitmapDrawable;
import com.android.launcher3.ItemInfo;
import com.google.android.apps.nexuslauncher2.oml.OMLSettings;

public class CustomDrawableFactory extends DynamicDrawableFactory {

    private final boolean hasIconPack;

    public CustomDrawableFactory(Context context) {
        super(context);
        hasIconPack = OMLSettings.hasIconPack(context);
    }

    @Override
    public FastBitmapDrawable newIcon(Bitmap icon, ItemInfo info) {
        if (hasIconPack) {
            return new FastBitmapDrawable(icon);
        }
        return super.newIcon(icon, info);
    }
}