package com.nekomimi.adapter_listener;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter 
{
	private final List<Fragment> mFragments = new ArrayList<> ();
	private final List<String> mFragmentTitles = new ArrayList<>();
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public void addFrag(Fragment fragment, String title) {
		mFragments.add(fragment);
		mFragmentTitles.add(title);
	}
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return mFragmentTitles.get(position);
	}
}
