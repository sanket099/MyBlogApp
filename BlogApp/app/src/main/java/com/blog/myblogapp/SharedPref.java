package com.blog.myblogapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.logging.SocketHandler;

public class SharedPref {

    private static final String SharedPreference_name = "my_SharedPreference";

    private static SharedPref instance;
    private Context context;

    SharedPref(Context context) {
        this.context = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);

        }
        return instance;
    }

    public void save_flag(long flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("flag", flag);
        editor.apply();

    }
    public long get_flag(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference_name, Context.MODE_PRIVATE);

        return sharedPreferences.getLong("flag", -1);
    }

    public void del_flag(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("flag");
        editor.apply();


    }

}
