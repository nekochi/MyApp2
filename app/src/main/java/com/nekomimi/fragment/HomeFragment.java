package com.nekomimi.fragment;


import com.nekomimi.R;


import com.nekomimi.activity.HomeActivity;
import com.nekomimi.adapter_listener.MangaItemRcAdapter;
import com.nekomimi.adapter_listener.RecyclerScorllListener;
import com.nekomimi.base.AppConfig;
import com.nekomimi.net.NekoJsonRequest;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.struct.MangaInfo;
import com.nekomimi.util.JsonUtil;
import com.nekomimi.util.Util;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment
{
   // private Button mTestBt = null;
    private RecyclerView mRecycleView = null;
    private List<MangaInfo> mMangaDateList;

    private Handler mHandle = new Handler(){
        public void handleMessage(Message msg)
        {
           switch (msg.what)
           {
               case 0:
                   Log.d("handler",String.valueOf(msg.obj));
                   break;
               case 1:
                    mMangaDateList = JsonUtil.paresMangaInfo((JSONObject)msg.obj);
                   if(mMangaDateList.isEmpty())
                       return;
                   MangaItemRcAdapter adapter = new MangaItemRcAdapter(mMangaDateList);
                   mRecycleView.setAdapter(adapter);
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

    public void getMangaInfo()
    {
        Log.d("TAG", "test");
        Map<String,String> requset = new HashMap<>();
        requset.put("name","");
        requset.put("type","");
        requset.put("skip","");
        requset.put("finish","");
        requset.put("key","e00b1e6d896c4f57ae552ab257186680");
        VolleyConnect.getInstance().connect(NekoJsonRequest.create(Util.makeHtml(AppConfig.MANGAURL,requset,"UTF-8"),mHandle));
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.fragment_home, container , false);
		// mTestBt = (Button)root.findViewById(R.id.test_tv);
//         mTestBt.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Log.d("where", "onTouch");
//                 VolleyConnect.getInstance().connect(NekoStringRequest.create("http://api.hitokoto.us/rand?cat=a", mHandle));
//                 VolleyConnect.getInstance().getImg(mTestIv, "http://img2.myhsw.cn/2015-08-28/k9xkbx59.jpg");

//                 new HttpConnectNet().execute("http://www.baidu.com");
//             }
//         });
//         mTestBt.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View view, MotionEvent motionEvent) {
//
//
//                 return false;
//             }
//         });
         mRecycleView = (RecyclerView)root.findViewById(R.id.mlist_rv);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
         mRecycleView.setLayoutManager(layoutManager);
         mRecycleView.addOnScrollListener(new RecyclerScorllListener() {
             @Override
             public void show() {
                 ((HomeActivity) getActivity()).showFloatBt();
             }

             @Override
             public void hide() {
                 ((HomeActivity) getActivity()).hideFloatBt();
             }
         });
		 return root;
	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
