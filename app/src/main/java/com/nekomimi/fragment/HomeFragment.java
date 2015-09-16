package com.nekomimi.fragment;


import com.nekomimi.R;


import com.nekomimi.activity.HomeActivity;
import com.nekomimi.activity.MangaInfoActivity;
import com.nekomimi.adapter_listener.MangaItemRcAdapter;
import com.nekomimi.adapter_listener.RecyclerScorllListener;
import com.nekomimi.base.AppConfig;
import com.nekomimi.net.NekoJsonRequest;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.bean.MangaInfo;
import com.nekomimi.util.JsonUtil;
import com.nekomimi.util.Util;


import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment
{
   // private Button mTestBt = null;
    private RecyclerView mRecycleView = null;
    private List<MangaInfo> mMangaDateList;
    private MangaItemRcAdapter mAdapter;
    private View mProgressView;

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
                   mAdapter.setData(mMangaDateList);
                   Util.showProgress(false, mRecycleView, mProgressView);
                   mRecycleView.setAdapter(mAdapter);
                   break;
               case 999999:
                   Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
                   Util.showProgress(false,mRecycleView,mProgressView);
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
        requset.put("key", "e00b1e6d896c4f57ae552ab257186680");
        Util.showProgress(true, mRecycleView, mProgressView);
        VolleyConnect.getInstance().connect(NekoJsonRequest.create(Util.makeHtml(AppConfig.MANGAURL, requset, "UTF-8"), mHandle));
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.fragment_home, container , false);

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
         mAdapter = new MangaItemRcAdapter();
         mAdapter.setOnItemClickListener(new MangaItemRcAdapter.OnRecyclerClickListener() {
             @Override
             public void onItemClick(View view, MangaInfo data) {
                 //TO DO: 漫画卡片点击事件
                 Toast.makeText(HomeFragment.this.getActivity(),data.getName(),Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(getActivity(), MangaInfoActivity.class);
                 intent.putExtra("mangainfo",data);

                 startActivity(intent);
             }
         });
         mProgressView = root.findViewById(R.id.home_progress);
         return root;
	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
