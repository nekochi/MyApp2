package com.nekomimi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nekomimi.base.AppAction;
import com.nekomimi.base.AppActionImpl;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class BaseFragment extends Fragment
{
    private AppAction mAction;
    private Context mContext;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.mContext = getActivity();
        this.mAction = new AppActionImpl(mContext);
    }

    public AppAction getAction()
    {
        return this.mAction;
    }

    public Context getContext()
    {
        return this.mContext;
    }
}
