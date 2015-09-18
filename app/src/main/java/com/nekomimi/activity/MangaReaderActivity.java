package com.nekomimi.activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nekomimi.R;
import com.nekomimi.net.VolleyConnect;
import com.polites.android.GestureImageView;

/**
 * Created by hongchi on 2015-9-18.
 */
public class MangaReaderActivity extends AppCompatActivity {

    private GestureImageView mGImageView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangareader);

        mGImageView = (GestureImageView)findViewById(R.id.image);
        VolleyConnect.getInstance().getImg(mGImageView,getIntent().getStringExtra("img"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.hide();
        }
    }
}
