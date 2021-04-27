package com.programabit.mediguard.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class GuardCountPreference {

    private final String KEY_COUNT = "guardCount";

    private final SharedPreferences preferences;

    public GuardCountPreference(Context context) {
        this.preferences = context.getSharedPreferences(KEY_COUNT, Context.MODE_PRIVATE);
    }

    public void setCount(int count) {
        this.preferences.edit().putInt(KEY_COUNT,count).apply();
    }

    public int getCount() {
       return this.preferences.getInt(KEY_COUNT,0);
    }

}
