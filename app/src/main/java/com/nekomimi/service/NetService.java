package com.nekomimi.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by hongchi on 2015-11-2.
 */
public class NetService extends Service
{
    public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    NetworkInfo.State mWifiState = null;
    NetworkInfo.State mMobileState = null;

    private BroadcastReceiver mNetBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(ACTION.equals(intent.getAction()))
            {
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                mMobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                mWifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

                if(mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED!=mWifiState && NetworkInfo.State.CONNECTED == mMobileState)
                {
                    Toast.makeText(context, "正在使用流量哦~", Toast.LENGTH_LONG).show();
                }
                else if (mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED==mWifiState && NetworkInfo.State.CONNECTED != mMobileState)
                {
                    Toast.makeText(context,"正在使用Wifi哦~",Toast.LENGTH_LONG).show();
                }else if (mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED!=mWifiState && NetworkInfo.State.CONNECTED != mMobileState)
                {
                    Toast.makeText(context,"目前手机没有任何网络/(ㄒoㄒ)/",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetBroadcastReceiver,filter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mNetBroadcastReceiver);
    }
}
