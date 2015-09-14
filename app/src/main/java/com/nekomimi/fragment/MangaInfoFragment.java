package com.nekomimi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekomimi.R;

import java.util.zip.Inflater;

/**
 * Created by hongchi on 2015-9-14.
 */
public class MangaInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mangainfo,container,false);

        return root;
    }
}
