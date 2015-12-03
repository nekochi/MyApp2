package com.nekomimi.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nekomimi.R;
import com.nekomimi.activity.HomeActivity;
import com.nekomimi.activity.MangaInfoActivity;
import com.nekomimi.adapter_listener.MangaItemRcAdapter;
import com.nekomimi.adapter_listener.RecyclerScorllListener;
import com.nekomimi.bean.MangaInfo;
import com.nekomimi.util.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
{
   // private Button mTestBt = null;
    private RecyclerView mRecycleView = null;
    private List<MangaInfo> mMangaDateList;
    private MangaItemRcAdapter mAdapter;
    private View mProgressView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mLastVisibleItem;

    private Handler mHandle = new Handler(){
        public void handleMessage(Message msg)
        {
           switch (msg.what)
           {
               case 0:
                   Log.d("handler",String.valueOf(msg.obj));
                   break;
               case 1:
                    mMangaDateList = JsonUtil.parseMangaInfo((JSONObject)msg.obj);
                   if(mMangaDateList.isEmpty())
                       return;
                   mAdapter.setData(mMangaDateList);
                  // Util.showProgress(false, mRecycleView, mProgressView);
                   mRecycleView.setAdapter(mAdapter);
                   break;
               case 999999:
                   Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
                  // Util.showProgress(false,mRecycleView,mProgressView);
                   break;
               default:
                   break;
           }
            mSwipeRefreshLayout.setRefreshing(false);
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
        Map<String,String> request = new HashMap<>();
        request.put("name","");
        request.put("type","");
        request.put("skip","");
        request.put("finish","");
        request.put("key", "e00b1e6d896c4f57ae552ab257186680");
        //Util.showProgress(true, mRecycleView, mProgressView);
       // VolleyConnect.getInstance().connect(NekoJsonRequest.create(Util.makeHtml(AppConfig.MANGA_URL, request, "UTF-8"), mHandle));
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.fragment_home, container , false);
         mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.srl_refresh);
         mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
         mSwipeRefreshLayout.setOnRefreshListener(this);
         mRecycleView = (RecyclerView)root.findViewById(R.id.mlist_rv);
         final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
         mRecycleView.setLayoutManager(layoutManager);
         mRecycleView.addOnScrollListener(new RecyclerScorllListener()
         {
             @Override
             public void onScrolled(RecyclerView recyclerView, int dx, int dy)
             {
                super.onScrolled(recyclerView,dx,dy);
                 mLastVisibleItem = layoutManager.findLastVisibleItemPosition();
             }

             @Override
             public void show() {
                 ((HomeActivity) getActivity()).showFloatBt();
             }

             @Override
             public void hide() {
                 ((HomeActivity) getActivity()).hideFloatBt();
             }

             @Override
             public void onScrollStateChanged(RecyclerView recyclerView, int newState)
             {
                 super.onScrollStateChanged(recyclerView, newState);
                 if(mAdapter == null || mAdapter.getData() == null)
                 {
                     return;
                 }
                 if (newState == RecyclerView.SCROLL_STATE_IDLE
                         && mLastVisibleItem + 1 == mAdapter.getItemCount())
                 {
                    //todo ：上拉加载
                    Toast.makeText(getActivity(),"up loading",Toast.LENGTH_SHORT).show();
                 }
             }
         });
         mAdapter = new MangaItemRcAdapter();
         mAdapter.setOnItemClickListener(new MangaItemRcAdapter.OnRecyclerClickListener() {
             @Override
             public void onItemClick(View view,MangaItemRcAdapter.MangaItemHolder holder)
             {
                 //TO DO: 漫画卡片点击事件
                 Toast.makeText(HomeFragment.this.getActivity(),holder.getData().getName(),Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(getActivity(), MangaInfoActivity.class);
                 intent.putExtra("mangainfo",holder.getData());
                 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                 {
                     MangaInfoActivity.launch((HomeActivity)getActivity(),holder.getImage(),intent);
                 }
                 else
                 {
                     startActivity(intent);
                 }
             }
         });
        // mProgressView = root.findViewById(R.id.home_progress);
         onRefresh();


         return root;
	 }
	@Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"refresh!",Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(true);
        getMangaInfo();
    }

    public void scrollToTop()
    {
        mRecycleView.smoothScrollToPosition(0);
    }
}
