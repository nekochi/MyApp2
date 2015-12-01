package com.nekomimi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nekomimi.base.AppAction;
import com.nekomimi.base.AppActionImpl;

/**
 * Created by hongchi on 2015-8-25.
 */
public class BaseActivity extends AppCompatActivity
{
    private AppAction mAppAction = null;
    private Context mContext;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
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
}
