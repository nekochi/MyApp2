package com.nekomimi.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.nekomimi.util.ArrayData;
import com.nekomimi.R;
import com.nekomimi.fragment.SettingFragment;

/**
 * Created by hongchi on 2015-10-15.
 */
public class SettingActivity extends AppCompatActivity implements SettingFragment.OnSelectedListener {

    private Toolbar mToolbar;
    private SettingFragment mSettingFm1 ;
    private SettingFragment mSettingFm2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);

        mSettingFm1 = SettingFragment.create(ArrayData.SETTING_ITEM_LIST1,0);
        mSettingFm1.setOnSelectedListener(this);
        //mSettingFm2 = SettingFragment.create(ArrayData.SETTING_ITEM_LIST2,1);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.rl_content,mSettingFm1).commit();

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

    @Override
    public void onSelected(View view, int flag) {

    }
}
