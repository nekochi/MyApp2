package com.nekomimi.activity;

import java.util.ArrayList;

import com.nekomimi.R;
import com.nekomimi.adapter.MyFragmentPagerAdapter;
import com.nekomimi.base.TestFragment;
import com.nekomimi.fragment.HomeFragment;

import com.nekomimi.view.TopTabView;
import com.nekomimi.view.TopTabView.OnSelectedListener;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public class HomeActivity extends AppCompatActivity {

	private ViewPager mViewPager = null;
	private ArrayList<Fragment> mFragmentList;
	private ActionBarDrawerToggle mActionBarDrawerToggle;
	private Toolbar toolbar;
	private DrawerLayout mDrawerLayout;
	private TabLayout mTabLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mTabLayout = (TabLayout)findViewById(R.id.toptab);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
		mActionBarDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

		LinearLayout mNavTab = (LinearLayout)findViewById(R.id.navtab);
		mNavTab.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				return true;
			}
		});

		//mTopTab = (TopTabView)findViewById(R.id.toptab);
		mViewPager = (ViewPager)findViewById(R.id.vp_pager);
		HomeFragment fragment1 = new HomeFragment();
//		TestFragment fragment1 = new TestFragment("#FF0000");
		TestFragment fragment2 = new TestFragment("#00FF00");
		TestFragment fragment3 = new TestFragment("#0000FF");
		TestFragment fragment4 = new TestFragment("#000000");
		mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(fragment1);
		mFragmentList.add(fragment2);
		mFragmentList.add(fragment3);
		mFragmentList.add(fragment4);
		MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		adapter.addFrag(mFragmentList.get(0),"Home");
		adapter.addFrag(mFragmentList.get(1),"Search");
		adapter.addFrag(mFragmentList.get(2),"Star");
		adapter.addFrag(mFragmentList.get(3),"Me");
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Log.d("where", "onPageSelected");

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(mActionBarDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mActionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}
}
