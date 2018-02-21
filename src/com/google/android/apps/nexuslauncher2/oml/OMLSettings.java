package com.google.android.apps.nexuslauncher2.oml;

import android.content.Context;

import com.android.launcher3.Utilities;

public class OMLSettings {

    public final static String ICON_PACK_PREF = "pref_icon_pack";
    public final static String SHOW_PREDICTIONS_PREF = "pref_show_predictions";
    public final static String ENABLE_MINUS_ONE_PREF = "pref_enable_minus_one";
    public final static String SMARTSPACE_PREF = "pref_smartspace";
    public final static String APP_VERSION_PREF = "about_app_version";

    public static final String LEGACY_ICONS_TREATMENT = "pref_legacy_icons_treatment";
    public static final boolean LEGACY_ICONS_TREATMENT_DEFAULT = false;

    public static boolean isLegacyIconsTreatmentEnabled(Context context) {
        return Utilities.getPrefs(context).getBoolean(LEGACY_ICONS_TREATMENT, LEGACY_ICONS_TREATMENT_DEFAULT);
    }
}
