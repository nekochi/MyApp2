package com.nekomimi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nekomimi.R;
import com.nekomimi.bean.MangaInfo;

/**
 * Created by hongchi on 2015-9-10.
 */
public class MangaInfoActivity extends AppCompatActivity {

    public static final String TAG = "MangaInfoActivity";

    private Toolbar mToolbar;
    private MangaInfo mMangaInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangainfo);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);


        Intent intent = getIntent();
        mMangaInfo = (MangaInfo)intent.getSerializableExtra("mangainfo");
        Log.d(TAG,mMangaInfo.getName());
        mToolbar.setTitle(mMangaInfo.getName());
        setSupportActionBar(mToolbar);
    }
}
