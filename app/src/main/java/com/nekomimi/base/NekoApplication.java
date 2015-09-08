package com.nekomimi.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.UUID;

///**
// * Created by hongchi on 2015-8-20.
// */
public class NekoApplication extends Application {

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    private static NekoApplication mNekoApplication = null;

    public static NekoApplication getInstance()
    {
        return mNekoApplication;
    }

    public void onCreate()
    {
        super.onCreate();
        mNekoApplication = this;
    }
    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni!=null&&ni.isConnectedOrConnecting();
    }


    public int getNetworkType()
    {
        int netType = 0;
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni == null)
        {
            return netType;
        }
        int nType = ni.getType();
        if(nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = ni.getExtraInfo();
            if (!extraInfo.isEmpty()) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        }
        else if (nType == ConnectivityManager.TYPE_WIFI)
        {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public String getAppId() {
        String uniqueID = AppConfig.getInstance().get(AppConfig.CONF_APP_UNIQUEID);
        if (TextUtils.equals(uniqueID, AppConfig.NOT_EXIST)) {
            uniqueID = UUID.randomUUID().toString();
            AppConfig.getInstance().set(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }
}
