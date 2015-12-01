package com.nekomimi.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekomimi.R;

public class TestFragment extends Fragment
{
	private int mColor;
	public static TestFragment newInstance(String color)
	{
		TestFragment t = new TestFragment();
		Bundle bundle = new Bundle();
		bundle.putString("color",color);
		t.setArguments(bundle);
		return t;
	}

	public TestFragment()
	{
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        Bundle args = getArguments();
		this.mColor = args != null ? Color.parseColor(args.getString("color")) : Color.BLACK ;

    }
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.fragment_test, container , false);
		 
		 root.findViewById(R.id.fortest).setBackgroundColor(mColor);
		 return root;

	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
