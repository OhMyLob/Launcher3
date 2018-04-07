package com.matteolobello.pixellauncher.oml;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.launcher3.LauncherFiles;
import com.android.launcher3.Utilities;

public class OMLSettings {

    public final static String ICON_PACK_PREF = "pref_icon_pack";
    public final static String SHOW_PREDICTIONS_PREF = "pref_show_predictions";
    public final static String ENABLE_MINUS_ONE_PREF = "pref_enable_minus_one";
    public final static String SMARTSPACE_PREF = "pref_smartspace";
    public final static String APP_VERSION_PREF = "about_app_version";

    public static final String LEGACY_ICONS_TREATMENT = "pref_legacy_icons_treatment";
    public static final boolean LEGACY_ICONS_TREATMENT_DEFAULT = false;

    public static final String IS_SMARTSMACE_ENABLED_PREF = "pref_is_smartspace_disabled";
    public static final boolean IS_SMARTSMACE_ENABLED_DEFAULT = false;

    public static final String SOLID_UI_PREF = "pref_solid_ui";
    public static final boolean SOLID_UI_DEFAULT = true;

    public static boolean isLegacyIconsTreatmentEnabled(Context context) {
        return Utilities.getPrefs(context).getBoolean(LEGACY_ICONS_TREATMENT, LEGACY_ICONS_TREATMENT_DEFAULT);
    }

    public static boolean isSmartspaceEnabled(Context context) {
        return Utilities.getPrefs(context).getBoolean(IS_SMARTSMACE_ENABLED_PREF, IS_SMARTSMACE_ENABLED_DEFAULT);
    }

    public static boolean hasIconPack(Context context) {
        return !Utilities.getPrefs(context).getString(OMLSettings.ICON_PACK_PREF, "").isEmpty();
    }

    public static boolean isSolidUiEnabled(Context context) {
        return Utilities.getPrefs(context).getBoolean(SOLID_UI_PREF, SOLID_UI_DEFAULT);
    }

    public static boolean isComponentHidden(Context context, ComponentName component) {
        return hiddenAppsPrefs(context).getBoolean(component.getClassName(), false);
    }

    public static void setComponentHidden(Context context, ComponentName component, boolean hidden) {
        hiddenAppsPrefs(context).edit().putBoolean(component.getClassName(), hidden).apply();
    }

    private static SharedPreferences hiddenAppsPrefs(Context context) {
        return context.getSharedPreferences(LauncherFiles.HIDDEN_APPS_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}
