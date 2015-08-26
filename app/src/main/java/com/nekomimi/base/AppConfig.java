package com.nekomimi.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hongchi on 2015-8-21.
 */
public class AppConfig {

    private static final String ERROR = "ERROR";
    private static final String NOT_EXIST = "NOT_EXIST";
    private static final String COOKIE = "Cookie";

    private  Context mContext;
    private  SharedPreferences mSharedPreferences;
    private static AppConfig mAppConfig;

    public static AppConfig getInstance(Context context)
    {
        if(mAppConfig == null)
        {
            mAppConfig = new AppConfig();
            mAppConfig.mContext = context;
            mAppConfig.mSharedPreferences = mAppConfig.mContext.getSharedPreferences("App_Config",Context.MODE_PRIVATE);
        }
        return mAppConfig;
    }

    public  SharedPreferences getSharedPreferences()
    {
        if(mAppConfig == null)
        {
            return null;
        }else {
            return mAppConfig.mSharedPreferences;
        }
    }

    public void setCookie(String cookie)
    {
        SharedPreferences sp = getSharedPreferences();
        if(sp == null)
        {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(COOKIE,cookie);
        editor.apply();
    }

    public String getCookie()
    {
        SharedPreferences sp = getSharedPreferences();
        if(sp == null)
        {
            return ERROR;
        }
        return sp.getString(COOKIE,NOT_EXIST);
    }

    public void set(String key,String val)
    {
        SharedPreferences sp = getSharedPreferences();
        if(sp == null)
        {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,val);
        editor.apply();
    }

    public String get(String key)
    {
        SharedPreferences sp = getSharedPreferences();
        if(sp==null)
        {
            return ERROR;
        }
        return sp.getString(key,NOT_EXIST);
    }
    public void setSharedPreferences(SharedPreferences sp)
    {
        if(mAppConfig == null)
        {
            return;
        }
        mAppConfig.mSharedPreferences = sp;
    }
}
