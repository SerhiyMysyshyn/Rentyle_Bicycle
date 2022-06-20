package com.aventador.bicyclerental.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static String MAP_MODE = "1";
    public static String RENTAL_COINS_COUNT = "0.0";

    public static int RENTAL_COIN_RATE = 5;

    public static SharedPreferences getSharedPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("rentalBikes", Context.MODE_PRIVATE);
        }
        return preferences;
    }











    public static void saveMapMode(String mode) {
        editor = preferences.edit();
        editor.putString(MAP_MODE, mode);
        editor.apply();
    }

    public static String getMapMode() {
        return preferences.getString(MAP_MODE, "1");
    }
}
