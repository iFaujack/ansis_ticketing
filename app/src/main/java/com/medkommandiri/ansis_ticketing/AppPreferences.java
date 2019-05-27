package com.medkommandiri.ansis_ticketing;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    public String idUser(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("xnticket", context.MODE_PRIVATE);
        String returnresult = sharedPreferences.getString("idUser",null);
        return returnresult;
    }
    public String realname(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("xnticket", context.MODE_PRIVATE);
        String returnresult = sharedPreferences.getString("realname",null);
        return returnresult;
    }

    public String idRole(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("xnticket", context.MODE_PRIVATE);
        String returnresult = sharedPreferences.getString("idRole",null);
        return returnresult;
    }
}
