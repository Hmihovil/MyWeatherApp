package com.example.prajnashetty.myweatherapp2;

/**
 * Created by prajnashetty on 4/23/15.
 *
 * This class contains methods used to save and retrieve
 * data from Default Shared preference
 */
 import android.content.Context;
 import android.content.SharedPreferences;
 import android.content.SharedPreferences.Editor;
 import android.preference.PreferenceManager;

public class SharedPreference {

    public static final String PREFS_KEY = "LAST_ICAO_CODE";

    public SharedPreference() {
        super();
    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        //using default shared preference
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
        editor.putString(PREFS_KEY, text);
        editor.commit();
    }

    public String getValue(Context context) {
        SharedPreferences settings;
        String text;

        //using default shared preference
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

}
