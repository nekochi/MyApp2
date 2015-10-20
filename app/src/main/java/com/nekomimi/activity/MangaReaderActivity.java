package com.nekomimi.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.nekomimi.R;
import com.nekomimi.base.AppConfig;
import com.nekomimi.bean.MangaImgInfo;
import com.nekomimi.fragment.MangaReaderFragment;
import com.nekomimi.net.NekoJsonRequest;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.util.JsonUtil;
import com.nekomimi.util.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongchi on 2015-9-18.
 */
public class MangaReaderActivity extends AppCompatActivity implements MangaReaderFragment.OnFragmentInteractionListener{

   // private GestureImageView mGImageView
    private String mMangaName;
    private String mMangaChapterId;
    private List<MangaImgInfo> mImageInfoList;
    private ViewPager mImageVp;
    private MangaImgAdapter mAdapter;
    private View mProgressBar;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mImageInfoList = JsonUtil.parseImgUrlList((JSONObject) msg.obj);
                    mImageVp = (ViewPager)findViewById(R.id.vp_mangareader);
                    mAdapter = new MangaImgAdapter(getSupportFragmentManager(),mImageInfoList);
                    mImageVp.setAdapter(mAdapter);
                    Util.showProgress(false,mImageVp,mProgressBar);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangareader);
        Intent intent = getIntent();
        mProgressBar = findViewById(R.id.mangareader_progress);
        mMangaName = intent.getStringExtra("mangaName");
        mMangaChapterId = intent.getStringExtra("id");
        Map<String,String> request = new HashMap<String, String>();
        request.put("comicName", mMangaName);
        request.put("id", mMangaChapterId);
        request.put("key", "e00b1e6d896c4f57ae552ab257186680");
        VolleyConnect.getInstance().connect(NekoJsonRequest.create(Util.makeHtml(AppConfig.MANGACHAPTER_URL, request, "UTF-8"), mHandler));


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.hide();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class MangaImgAdapter extends FragmentPagerAdapter
    {
        private List<MangaImgInfo> mDataList;
        MangaImgAdapter(FragmentManager manager , List<MangaImgInfo> datas)
        {
            super(manager);
            this.mDataList = datas;
        }
        @Override
        public Fragment getItem(int position) {
            return MangaReaderFragment.newInstance(mDataList.get(position).getImgUrl());
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }
    }
}
