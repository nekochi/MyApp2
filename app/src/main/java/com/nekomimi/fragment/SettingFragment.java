package com.nekomimi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nekomimi.R;

/**
 * Created by hongchi on 2015-10-15.
 */
public class SettingFragment extends Fragment {


    private String[] mDataString;


    private RecyclerView mSettingRv;
    public static SettingFragment create(String[] str)
    {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("data",str);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    public SettingFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            mDataString = getArguments().getStringArray("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting,container,false);
        mSettingRv = (RecyclerView)root.findViewById(R.id.rv_setting);
        mSettingRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSettingRv.setAdapter(new SettingItemAdapter(mDataString));


        return root;
    }

    class SettingItemAdapter extends RecyclerView.Adapter<SettingItemAdapter.SettingItemHolder>
    {
        String[] mData ;

        SettingItemAdapter(String[] str)
        {
            mData = str;
        }

        @Override
        public SettingItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            SettingItemHolder settingItemHolder = new SettingItemHolder(LayoutInflater.from(getActivity()).inflate(R.layout.view_settingitem,parent,false));
            return settingItemHolder;
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }

        @Override
        public void onBindViewHolder(SettingItemHolder holder, int position) {
            holder.itemView.setTag(holder);
            holder.mSettingItemTv.setText(mData[position]);
        }

        class SettingItemHolder extends RecyclerView.ViewHolder
        {
            TextView mSettingItemTv;
            public SettingItemHolder(View view)
            {
                super(view);
                mSettingItemTv = (TextView)view.findViewById(R.id.tv_settingitem);
            }
        }

    }
}
