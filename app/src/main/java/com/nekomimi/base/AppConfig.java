package com.nekomimi.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.nekomimi.util.Util;

import java.util.Map;

/**
 * Created by hongchi on 2015-8-21.
 */
public class AppConfig {
    public static final String MANGA_URL = "http://japi.juhe.cn/comic/book";
    public static final String MANGAINFO_URL = "http://japi.juhe.cn/comic/chapter";
    public static final String MANGACHAPTER_URL = "http://japi.juhe.cn/comic/chapterContent";

    public static final String IPB_MEMBER_ID = "ipb_member_id";
    public static final String IPB_PASS_HASH = "ipb_pass_hash";
    public static final String IPB_SESSION_ID = "ipb_session_id";

    public static final String ISLOGINED = "IsLogined";
    public static final String ERROR = "ERROR";
    public static final String NOT_EXIST = "NOT_EXIST";
    public static final String COOKIE = "Cookie";
    public static final String USERAGENT = "User-Agent";
    public static final String CONF_APP_UNIQUEID = "APP_UNIQUEID";
    public static final String RESOLUTION = "Resolution";
    public static final String ACCOUNT = "Account";

    public static String USERAGENT_VALUE = "";
    private  Context mContext;
    private  SharedPreferences mSharedPreferences;
    private SharedPreferences mCookiesSP;
    private String mUserAgent;
    private static AppConfig mAppConfig;

    public static int mScanWidth;
    public static int mScanHeight;
    public static AppConfig getInstance()
    {
        if(mAppConfig == null)
        {
            mAppConfig = new AppConfig();
            mAppConfig.mContext = NekoApplication.getInstance();
            mAppConfig.mSharedPreferences = mAppConfig.mContext.getSharedPreferences("App_Config",Context.MODE_PRIVATE);
            mAppConfig.mCookiesSP = mAppConfig.mContext.getSharedPreferences("App_Cookies",Context.MODE_PRIVATE);
        }
        return mAppConfig;
    }

    public SharedPreferences getSharedPreferences()
    {
        if(mSharedPreferences == null)
        {
            getInstance();
        }
        return mAppConfig.mSharedPreferences;
    }

    public SharedPreferences getCookiesSp()
    {
        if(mSharedPreferences == null)
        {
            getInstance();
        }
        return mAppConfig.mCookiesSP;
    }

    public void setCookie(String cookies[])
    {
        for(int i = 0 ; i < cookies.length ; i++)
        {
            setCookie(cookies[i]);
        }
    }
    public void setCookie(String cookie)
    {
        String s[] = Util.cookieToArray(cookie);
        if(s.length==2)
        {
            setCookie(s[0],s[1]);
        }
    }
    public void setCookie(String key , String value)
    {
        SharedPreferences sp = getCookiesSp();
        set(key,value,sp);

//        SharedPreferences sp = getSharedPreferences();
//        if(sp == null)
//        {
//            return;
//        }
//        SharedPreferences.Editor editor = sp.edit();
//        String cookieBef = sp.getString(COOKIE,NOT_EXIST);
//        if(TextUtils.equals(cookieBef,NOT_EXIST)){
//            editor.putString(COOKIE,cookie.split(";")[0]);
//            editor.apply();
//        }else
//        {
//            Map<String,String> cookieMap = Util.cookieToMap(cookieBef);
//            String []cookieStr = Util.cookieToArray(cookie);
//            cookieMap.put(cookieStr[0],cookieStr[1]);
//            editor.putString(COOKIE,Util.cookieToString(cookieMap));
//            editor.apply();
//        }
    }

    public String getCookie(String key)
    {
        SharedPreferences sp = getCookiesSp();
        return get(key,sp);
    }

    public String getCookie()
    {
        SharedPreferences sp = getCookiesSp();
        Map cookies = sp.getAll();
        return Util.cookieToString(cookies);
    }
    public String getUserAgent() {
        if(!TextUtils.equals(USERAGENT_VALUE,""))
            return USERAGENT_VALUE;
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            Log.e(ERROR,"sharedpreferences not found");
            return "";
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
            this.set(USERAGENT, ua.toString());
            USERAGENT_VALUE = ua.toString();
            return USERAGENT_VALUE;
        }
        USERAGENT_VALUE = userAgent;
        return userAgent;
    }

    public void set(String key,String val)
    {
        SharedPreferences sp = getSharedPreferences();
        set(key, val , sp);
    }

    public String get(String key)
    {
        SharedPreferences sp = getSharedPreferences();
        return get(key,sp);
    }

    public void set(String key,String val,SharedPreferences sp)
    {
        if(sp == null)
        {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,val);
        editor.apply();
    }

    public String get(String key, SharedPreferences sp)
    {
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

    public void cleanData()
    {
        cleanCookiesSp();
        cleanSharedPreferences();
    }
    public void cleanSharedPreferences()
    {
        SharedPreferences sp = getSharedPreferences();
        cleanSp(sp);
    }
    public void cleanCookiesSp()
    {
        SharedPreferences sp = getCookiesSp();
        cleanSp(sp);
    }
    private void cleanSp(SharedPreferences sp)
    {
        if(sp == null)
        {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
