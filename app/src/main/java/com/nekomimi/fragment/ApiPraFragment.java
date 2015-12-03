package com.nekomimi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekomimi.R;
import com.nekomimi.activity.NewsActivity;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class ApiPraFragment extends BaseFragment implements View.OnClickListener
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
                Intent intent = new Intent(getContext(), NewsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
