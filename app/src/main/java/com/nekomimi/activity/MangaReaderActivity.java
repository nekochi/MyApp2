package com.nekomimi.activity;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nekomimi.R;
import com.nekomimi.bean.MangaImgInfo;
import com.nekomimi.fragment.MangaReaderFragment;

import java.util.List;

/**
 * Created by hongchi on 2015-9-18.
 */
public class MangaReaderActivity extends AppCompatActivity implements MangaReaderFragment.OnFragmentInteractionListener{

   // private GestureImageView mGImageView;
    private List<MangaImgInfo> mImageInfoList;
    private ViewPager mImageVp;
    private MangaImgAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangareader);
        mImageInfoList = (List<MangaImgInfo>)getIntent().getSerializableExtra("img");
        mImageVp = (ViewPager)findViewById(R.id.vp_mangareader);
        mAdapter = new MangaImgAdapter(getSupportFragmentManager(),mImageInfoList);
        mImageVp.setAdapter(mAdapter);
        //mGImageView = (GestureImageView)findViewById(R.id.image);
        //VolleyConnect.getInstance().getImg(mGImageView,getIntent().getStringExtra("img"));
//        ImageRequest request = new ImageRequest(getIntent().getStringExtra("img"), new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                mGImageView.setImageBitmap(bitmap);
//            }
//        }, 0, 0, null,
//                new Response.ErrorListener() {
//                    public void onErrorResponse(VolleyError error) {
//                        mGImageView.setImageResource(R.drawable.ic_launcher);
//                    }
//                });
//        VolleyConnect.getInstance().connect(request);
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
