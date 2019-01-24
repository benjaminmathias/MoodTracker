package com.openclassrooms.bmathias.moodtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    //SharedPreferences file used to store user's note and mood/background
    static SharedPreferences mPreferences;

    //String and Integer used for user note and image/background
    private String noteText = "";
    private int moodIndex = -1;

    //String key for SharedPreferences
    private final String MOOD_KEY = "mood";
    private final String BACKGROUND_KEY = "background";
    private final String NOTE_KEY = "note";

    @Override
    public void onReceive(Context context, Intent intent) {

        mPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        Log.d("AlarmReceiver", "Alarm fired");

        // Create a new int and String where the mood and note of the day will be stored
        int moodI;
        String noteI;

        // If the user opened and left the app during the day, since BACKGROUND_KEY is automatically
        // created, we get those keys. If those keys don't exists we set moodIndex to -1 (which
        // correspond to the default_background). WIP !
        if (mPreferences.contains(BACKGROUND_KEY)){
            moodI = mPreferences.getInt(BACKGROUND_KEY, moodIndex);
            noteI = mPreferences.getString(NOTE_KEY, noteText);
        } else {
            moodI = mPreferences.getInt(BACKGROUND_KEY, -1);
            noteI = mPreferences.getString(NOTE_KEY, noteText);
        }

        // Create a new int and String where the mood and note already saved will be stored
        int moodT;
        String noteT;

        for (int i = 0; i < 7; i++) {
            moodT = mPreferences.getInt(BACKGROUND_KEY + i, moodIndex);
            noteT = mPreferences.getString(NOTE_KEY + i, noteText);
            preferencesEditor.putInt(BACKGROUND_KEY + i, moodI);
            preferencesEditor.putString(NOTE_KEY + i, noteI);
            moodI = moodT;
            noteI = noteT;
        }

        // After we remove these keys, when the user will open his app after midnight, the initial
        // mood/background will be display (moodIndex = 3) and note deleted
        preferencesEditor.remove(MOOD_KEY);
        preferencesEditor.remove(BACKGROUND_KEY);
        preferencesEditor.remove(NOTE_KEY);
        preferencesEditor.apply();
    }
}