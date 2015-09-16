package com.nekomimi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;


import com.nekomimi.R;
import com.nekomimi.adapter_listener.MyFragmentPagerAdapter;
import com.nekomimi.base.TestFragment;
import com.nekomimi.bean.MangaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2015-9-10.
 */
public class MangaInfoActivity extends AppCompatActivity {

    public static final String TAG = "MangaInfoActivity";

    private Toolbar mToolbar;
    private MangaInfo mMangaInfo;
    private ImageView mCoverIv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangainfo);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);


        Intent intent = getIntent();
        mMangaInfo = (MangaInfo)intent.getParcelableExtra("mangainfo");
        Log.d(TAG, mMangaInfo.getName());
        mToolbar.setTitle(mMangaInfo.getName());
        setSupportActionBar(mToolbar);
        mCoverIv = (ImageView)findViewById(R.id.iv_mangacover);
        mCoverIv.setImageBitmap(mMangaInfo.getCoverImgBm());
        mTabLayout = (TabLayout)findViewById(R.id.tl_mangainfo);
        mViewPager = (ViewPager)findViewById(R.id.vp_mangainfo);
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mFragments = new ArrayList<>();
        mFragments.add(new TestFragment("#026719"));
        mFragments.add(new TestFragment("#acf233"));
        adapter.addFrag(mFragments.get(0), "info");
        adapter.addFrag(mFragments.get(1), "comment");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
