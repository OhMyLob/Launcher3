package com.matteolobello.pixellauncher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.graphics.ColorUtils;
import android.view.Menu;
import android.view.View;

import com.android.launcher3.AppInfo;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherCallbacks;
import com.android.launcher3.LauncherExterns;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.android.launcher3.dynamicui.WallpaperColorInfo;
import com.android.launcher3.graphics.DrawableFactory;
import com.android.launcher3.util.ComponentKeyMapper;
import com.android.launcher3.util.Themes;
import com.matteolobello.pixellauncher.oml.OMLSettings;
import com.matteolobello.pixellauncher.search.ItemInfoUpdateReceiver;
import com.matteolobello.pixellauncher.smartspace.SmartspaceController;
import com.matteolobello.pixellauncher.smartspace.SmartspaceView;
import com.google.android.libraries.launcherclient.GoogleNow;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class NexusLauncher {
    private final Launcher mLauncher;
    public final LauncherCallbacks mLauncherCallbacks;
    private boolean fC;
    private final LauncherExterns mLauncherExterns;
    private boolean mRunning;
    com.google.android.libraries.launcherclient.GoogleNow mGoogleNow;
    NexusLauncherOverlay mNexusLauncherOverlay;
    private boolean mStarted;
    private final Bundle mUiInformation = new Bundle();
    private ItemInfoUpdateReceiver mItemInfoUpdateReceiver;

    public NexusLauncher(NexusLauncherActivity activity) {
        mLauncher = activity;
        mLauncherExterns = activity;
        mLauncherCallbacks = new NexusLauncherCallbacks();
        mLauncherExterns.setLauncherCallbacks(mLauncherCallbacks);
    }

    private static GoogleNow.IntegerReference dZ(SharedPreferences sharedPreferences) {
        return new GoogleNow.IntegerReference(
                (sharedPreferences.getBoolean(OMLSettings.ENABLE_MINUS_ONE_PREF, true) ? 1 : 0) | 0x2 | 0x4 | 0x8);
    }

    class NexusLauncherCallbacks implements LauncherCallbacks, SharedPreferences.OnSharedPreferenceChangeListener, WallpaperColorInfo.OnChangeListener {
        private SmartspaceView mSmartspace;

        private ItemInfoUpdateReceiver getUpdateReceiver() {
            if (mItemInfoUpdateReceiver == null) {
                mItemInfoUpdateReceiver = new ItemInfoUpdateReceiver(mLauncher, mLauncherCallbacks);
            }
            return mItemInfoUpdateReceiver;
        }

        public void bindAllApplications(final ArrayList<AppInfo> list) {
            getUpdateReceiver().di();
        }

        public void dump(final String s, final FileDescriptor fileDescriptor, final PrintWriter printWriter, final String[] array) {
            SmartspaceController.get(mLauncher).cX(s, printWriter);
        }

        public void finishBindingItems(final boolean b) {
        }

        public List<ComponentKeyMapper<AppInfo>> getPredictedApps() {
            return ((CustomAppPredictor) mLauncher.getUserEventDispatcher()).getPredictions();
        }

        @Override
        public int getSearchBarHeight() {
            return LauncherCallbacks.SEARCH_BAR_HEIGHT_NORMAL;
        }

        public boolean handleBackPressed() {
            return false;
        }

        public boolean hasCustomContentToLeft() {
            return false;
        }

        public boolean hasSettings() {
            return true;
        }

        public void onActivityResult(final int n, final int n2, final Intent intent) {
        }

        public void onAttachedToWindow() {
            mGoogleNow.onAttachedToWindow();
        }

        public void onCreate(final Bundle bundle) {
            SharedPreferences prefs = Utilities.getPrefs(mLauncher);
            mNexusLauncherOverlay = new NexusLauncherOverlay(mLauncher);
            mGoogleNow = new com.google.android.libraries.launcherclient.GoogleNow(mLauncher, mNexusLauncherOverlay, dZ(prefs));
            mNexusLauncherOverlay.setNowConnection(mGoogleNow);

            prefs.registerOnSharedPreferenceChangeListener(this);

            SmartspaceController.get(mLauncher).cW();
            mSmartspace = mLauncher.findViewById(R.id.search_container_workspace);

            mUiInformation.putInt("system_ui_visibility", mLauncher.getWindow().getDecorView().getSystemUiVisibility());
            WallpaperColorInfo instance = WallpaperColorInfo.getInstance(mLauncher);
            instance.addOnChangeListener(this);
            onExtractedColorsChanged(instance);

            getUpdateReceiver().onCreate();
        }

        public void onDestroy() {
            mGoogleNow.onDestroy();
            Utilities.getPrefs(mLauncher).unregisterOnSharedPreferenceChangeListener(this);

            getUpdateReceiver().onDestroy();
        }

        public void onDetachedFromWindow() {
            mGoogleNow.onDetachedFromWindow();
        }

        public void onHomeIntent() {
            mGoogleNow.closeOverlay(fC);
        }

        public void onInteractionBegin() {
        }

        public void onInteractionEnd() {
        }

        public void onLauncherProviderChange() {
        }

        public void onNewIntent(final Intent intent) {
        }

        public void onPause() {
            mRunning = false;
            mGoogleNow.onPause();

            if (mSmartspace != null) {
                mSmartspace.onPause();
            }
        }

        public void onPostCreate(final Bundle bundle) {
        }

        public boolean onPrepareOptionsMenu(final Menu menu) {
            return false;
        }

        public void onRequestPermissionsResult(final int n, final String[] array, final int[] array2) {
        }

        public void onResume() {
            mRunning = true;
            if (mStarted) {
                fC = true;
            }

            try {
                mGoogleNow.onResume();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (mSmartspace != null) {
                mSmartspace.onResume();
            }
        }

        public void onSaveInstanceState(final Bundle bundle) {
        }

        public void onStart() {
            mStarted = true;
            mGoogleNow.onStart();
        }

        public void onStop() {
            mStarted = false;
            mGoogleNow.onStop();
            if (!mRunning) {
                fC = false;
            }
            mNexusLauncherOverlay.stop();
        }

        public void onTrimMemory(int n) {
        }

        public void onWindowFocusChanged(boolean hasFocus) {
        }

        public void onWorkspaceLockedChanged() {
        }

        public void populateCustomContentContainer() {
        }

        @Override
        public View getQsbBar() {
            return null;
        }

        @Override
        public Bundle getAdditionalSearchWidgetOptions() {
            return null;
        }

        public void preOnCreate() {
            DrawableFactory.get(mLauncher);
        }

        public void preOnResume() {
        }

        public boolean shouldMoveToDefaultScreenOnHomeIntent() {
            return true;
        }

        public boolean startSearch(String s, boolean b, Bundle bundle) {
            View gIcon = mLauncher.findViewById(R.id.g_icon);
            while (gIcon != null && !gIcon.isClickable()) {
                if (gIcon.getParent() instanceof View) {
                    gIcon = (View) gIcon.getParent();
                } else {
                    gIcon = null;
                }
            }
            if (gIcon != null && gIcon.performClick()) {
                mLauncherExterns.clearTypedText();
                return true;
            }
            return false;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (OMLSettings.ENABLE_MINUS_ONE_PREF.equals(key)) {
                mGoogleNow.RB(dZ(sharedPreferences));
            }
        }

        @Override
        public void onExtractedColorsChanged(WallpaperColorInfo wallpaperColorInfo) {
            int alpha = mLauncher.getResources().getInteger(R.integer.extracted_color_gradient_alpha);

            mUiInformation.putInt("background_color_hint", primaryColor(wallpaperColorInfo, mLauncher, alpha));
            mUiInformation.putInt("background_secondary_color_hint", secondaryColor(wallpaperColorInfo, mLauncher, alpha));
            mUiInformation.putBoolean("is_background_dark", Themes.getAttrBoolean(mLauncher, R.attr.isMainColorDark));

            mGoogleNow.redraw(mUiInformation);
        }
    }

    public static int primaryColor(WallpaperColorInfo wallpaperColorInfo, Context context, int alpha) {
        return compositeAllApps(ColorUtils.setAlphaComponent(wallpaperColorInfo.getMainColor(), alpha), context);
    }

    public static int secondaryColor(WallpaperColorInfo wallpaperColorInfo, Context context, int alpha) {
        return compositeAllApps(ColorUtils.setAlphaComponent(wallpaperColorInfo.getSecondaryColor(), alpha), context);
    }

    private static int compositeAllApps(int color, Context context) {
        return ColorUtils.compositeColors(Themes.getAttrColor(context, R.attr.allAppsScrimColor), color);
    }
}
