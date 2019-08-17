package com.mtp.simplecoding;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefUtil {


    public static String getValue(Context context,String key) {
        SharedPreferences prefs = context.getSharedPreferences("NAME",
                Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }


    public static void setValue(Context context,String key, String fullNmae) {
        SharedPreferences preferences = context.getSharedPreferences("NAME",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, fullNmae);
        editor.commit();
    }










}
