package com.nekomimi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekomimi.R;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class ApiPraFragment extends Fragment implements View.OnClickListener
{

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_apipra,container,false);
        root.findViewById(R.id.news).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.news:

                break;
            default:
                break;
        }
    }
}
