package com.nekomimi.fragment;


import com.nekomimi.R;

import com.nekomimi.net.HttpConnectNet;
import com.nekomimi.net.VolleyConnect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment
{
    private TextView mTestTv = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        Bundle args = getArguments();
      //  hello = args != null ? args.getString("hello") : defaultHello;

    }
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.fragment_home, container , false);
		 mTestTv = (TextView)root.findViewById(R.id.test_tv);
         mTestTv.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 Log.d("where","onTouch");
                 VolleyConnect.connect(getActivity());
//                 new HttpConnectNet().execute("http://www.baidu.com");
                 return false;
             }
         });
		 return root;
	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
