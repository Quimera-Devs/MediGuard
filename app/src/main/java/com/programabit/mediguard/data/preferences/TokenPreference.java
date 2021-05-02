package com.programabit.mediguard.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenPreference                                            {

    private final String KEY_TOKEN = TokenPreference.class.getSimpleName() + "token";

    private final SharedPreferences preferences;

    public TokenPreference(Context context) {
        this.preferences = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        this.preferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return this.preferences.getString(KEY_TOKEN, "");
    }
}
