package com.nekomimi.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.nekomimi.base.AppAction;
import com.nekomimi.base.AppActionImpl;

import java.lang.ref.WeakReference;

/**
 * Created by hongchi on 2015-8-25.
 */
abstract public class BaseActivity extends AppCompatActivity
{
    private AppAction mAppAction = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.mAppAction = new AppActionImpl(this);
        this.mContext = this;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        this.mAppAction = new AppActionImpl(this);
        this.mContext = this;
    }

    public AppAction getAction()
    {
        return this.mAppAction;
    }

    public Context getContext()
    {
        return this.mContext;
    }

    public static class UiHandler extends Handler
    {
        private final WeakReference<BaseActivity> mActivity;

        public UiHandler(BaseActivity activity)
        {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            BaseActivity activity = mActivity.get();
            if (activity != null)
            {
                activity.handleMessage(msg);
            }
        }
    }

    abstract public void handleMessage(Message msg);
}
