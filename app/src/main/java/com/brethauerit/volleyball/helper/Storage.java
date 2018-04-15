package com.brethauerit.volleyball.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.brethauerit.volleyball.data.Settings;
import com.brethauerit.volleyball.data.Statistic;
import com.google.gson.Gson;

public class Storage {

    private static final String PREFERENCES = "Volleyball_Statistic";
    private static final String KEY_STATISTIC = "STATISTIC";
    private static final String KEY_SETTING = "SETTING";
    private static final String KEY_LAUNCHER = "LAUNCHER_V1.2";


    public static Settings loadSettings(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String statisticsJson = prefs.getString(KEY_SETTING, "");
        Gson gson = new Gson();
        return gson.fromJson(statisticsJson, Settings.class);
    }

    public static void storeSettings(Context context, Settings settings){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String statisticJson = gson.toJson(settings);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SETTING, statisticJson);
        editor.apply();
    }

    public static Statistic loadStatistics(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String statisticsJson = prefs.getString(KEY_STATISTIC, "");
        Gson gson = new Gson();
        return gson.fromJson(statisticsJson, Statistic.class);
    }

    public static void storeStatistics(Context context, Statistic statistic){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String statisticJson = gson.toJson(statistic);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_STATISTIC, statisticJson);
        editor.apply();
    }

    public static boolean isFirstTimeLaunched(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        boolean isOpenedFirst = prefs.getBoolean(KEY_LAUNCHER, true);
        prefs.edit().putBoolean(KEY_LAUNCHER, false).apply();
        return  isOpenedFirst;
    }

}
