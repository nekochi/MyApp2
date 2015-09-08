package com.nekomimi.fragment;


import com.android.volley.Request;

import com.nekomimi.R;


import com.nekomimi.net.NekoStringRequest;
import com.nekomimi.net.VolleyConnect;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class HomeFragment extends Fragment
{
    private TextView mTestTv = null;
	private ImageView mTestIv = null;

    private Handler mHandle = new Handler(){
        public void handleMessage(Message msg)
        {
           switch (msg.what)
           {
               case 0:
                   Log.d("handler",String.valueOf(msg.obj));
                   break;
               default:
                   break;
           }
        }
    };
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
                 Log.d("where", "onTouch");
                 VolleyConnect.getInstance().connect(NekoStringRequest.create("http://api.hitokoto.us/rand?cat=a", mHandle));
                 VolleyConnect.getInstance().getImg(mTestIv, "http://img2.myhsw.cn/2015-08-28/k9xkbx59.jpg");

//                 new HttpConnectNet().execute("http://www.baidu.com");

                 return false;
             }
         });
         mTestIv = (ImageView)root.findViewById(R.id.iv_face);

		 return root;
	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
