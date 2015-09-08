package com.nekomimi.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.nekomimi.util.Util;

import java.util.Map;

/**
 * Created by hongchi on 2015-8-21.
 */
public class AppConfig {

    public static final String ERROR = "ERROR";
    public static final String NOT_EXIST = "NOT_EXIST";
    public static final String COOKIE = "Cookie";
    public static final String USERAGENT = "User-Agent";
    public static final String CONF_APP_UNIQUEID = "APP_UNIQUEID";
    public static final String RESOLUTION = "Resolution";
    public static final String ACCOUNT = "Account";
    private  Context mContext;
    private  SharedPreferences mSharedPreferences;
    private String mUserAgent;
    private static AppConfig mAppConfig;

    public static AppConfig getInstance()
    {
        if(mAppConfig == null)
        {
            mAppConfig = new AppConfig();
            mAppConfig.mContext = NekoApplication.getInstance();
            mAppConfig.mSharedPreferences = mAppConfig.mContext.getSharedPreferences("App_Config",Context.MODE_PRIVATE);
        }
        return mAppConfig;
    }

    private SharedPreferences getSharedPreferences()
    {
        if(mAppConfig == null)
        {
            getInstance();
        }
        return mAppConfig.mSharedPreferences;

    }

    public void setCookie(String cookie)
    {
        SharedPreferences sp = getSharedPreferences();
        if(sp == null)
        {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        String cookieBef = sp.getString(COOKIE,NOT_EXIST);
        if(TextUtils.equals(cookieBef,NOT_EXIST)){
            editor.putString(COOKIE,cookie.split(";")[0]);
            editor.apply();
        }else
        {
            Map<String,String> cookieMap = Util.cookieToMap(cookieBef);
            String []cookieStr = Util.cookieToArray(cookie);
            cookieMap.put(cookieStr[0],cookieStr[1]);
            editor.putString(COOKIE,Util.cookieToString(cookieMap));
            editor.apply();
        }

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

    public String getUserAgent() {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return ERROR;
        }
        String userAgent = sp.getString(USERAGENT, NOT_EXIST);
        if (TextUtils.equals(userAgent, NOT_EXIST)) {
            StringBuffer ua = new StringBuffer("APP");
            ua.append('/' + ((NekoApplication) mContext).getPackageInfo().versionName + '_'
                    +((NekoApplication) mContext).getPackageInfo().versionCode);
            ua.append("/Android");
            ua.append("/" + android.os.Build.VERSION.RELEASE);
            ua.append("/" + android.os.Build.MODEL);
            ua.append("/" + ((NekoApplication) mContext).getAppId());
            this.set(USERAGENT,ua.toString());
            return ua.toString();
        }
        return userAgent;
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
