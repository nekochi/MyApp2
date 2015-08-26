package com.nekomimi.base;


import com.nekomimi.R;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends Fragment
{
	private int mColor;
	public TestFragment(String color)
	{
		mColor =  Color.parseColor(color);
	}
	public TestFragment()
	{
		super();
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        Bundle args = getArguments();
      //  hello = args != null ? args.getString("hello") : defaultHello;

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
