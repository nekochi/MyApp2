package com.nekomimi.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.nekomimi.R;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class NewsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
{
    public static final String TAG = "NewsActivity";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mAdapter;
    private NewsInfo mData = null;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                mSwipeRefreshLayout.setRefreshing(false);
                mData = (NewsInfo) msg.obj;
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_news);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_news);
        setSupportActionBar(mToolbar);
        getAction().getNews(mHandler);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_newslist);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsAdapter();
        handleSearchIntent(getIntent());

        Log.d("NewsActivity", "NewsActivity thread id is " + Thread.currentThread().getId());
    }

    private void handleSearchIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String key = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG,"title:"+key);
            getAction().getNews(mHandler,key);
            mSwipeRefreshLayout.setRefreshing(true);
            mRecyclerView.scrollToPosition(0);
        }
    }



    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        handleSearchIntent(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                break;
            case R.id.action_fresh:
                mRecyclerView.scrollToPosition(0);
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news,menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);

        final SearchView searchView =  (SearchView) menuItem.getActionView();

        SearchManager searchManager= (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(NewsActivity.this, "onExpand", Toast.LENGTH_SHORT).show();
                searchView.setIconified(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(NewsActivity.this, "Collapse", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                menuItem.collapseActionView();
                return false;
            }
        });
        return true;
    }
    @Override
    public void onRefresh()
    {
        getAction().getNews(mHandler);
    }

    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {

        private final int  HEADER = 0;
        private final int  NEWS = 1;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            Log.e("TAG","onCreateView");
            if(viewType == NEWS)
            {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_newscard,parent,false);
                return  new NewsHolder(view);

            }
            else if(viewType == HEADER)
            {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_news_header, parent, false);
                return new NewsHeaderHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            int index = position - 1;
            if(holder instanceof NewsHolder)
            {
                holder.itemView.setTag(holder);
                ((NewsHolder) holder).mDate.setText(mData.getPagebean().getContentlist().get(index).getPubDate());
                ((NewsHolder) holder).mSource.setText(mData.getPagebean().getContentlist().get(index).getSource());
                ((NewsHolder) holder).mBody.setText(mData.getPagebean().getContentlist().get(index).getLong_abs()==null?
                        mData.getPagebean().getContentlist().get(index).getDesc():mData.getPagebean().getContentlist().get(index).getLong_abs());
                ((NewsHolder) holder).mTitle.setText(mData.getPagebean().getContentlist().get(index).getTitle());
            }
            else if (holder instanceof NewsHeaderHolder)
            {
                holder.itemView.setTag(holder);
                ViewPager vp = ((NewsHeaderHolder) holder).mViewPager;
                vp.setAdapter(new SimpleAdapter());
                vp.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

//                vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//                    @Override
//                    public void onPageSelected(int arg0) {
//                    }
//
//                    @Override
//                    public void onPageScrolled(int arg0, float arg1, int arg2) {
//                        vp.getParent().requestDisallowInterceptTouchEvent(true);
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int arg0) {
//                    }
//                });

            }

        }

        @Override
        public int getItemViewType(int position)
        {
            if(position == 0) return HEADER;
            return NEWS;
        }

        @Override
        public int getItemCount() {
            return mData.getPagebean().getContentlist().size()+1;
        }

        class NewsHolder extends RecyclerView.ViewHolder
        {
            private TextView mTitle;
            private TextView mBody;
            private TextView mSource;
            private TextView mDate;

            public NewsHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.tv_newstitle);
                mBody = (TextView) itemView.findViewById(R.id.tv_newsbody);
                mSource = (TextView) itemView.findViewById(R.id.tv_newssource);
                mDate = (TextView) itemView.findViewById(R.id.tv_newspubdate);
            }
        }

        class NewsHeaderHolder extends RecyclerView.ViewHolder
        {
            private ViewPager mViewPager;

            public NewsHeaderHolder(View itemView)
            {
                super(itemView);
                mViewPager = (ViewPager)itemView.findViewById(R.id.vp_news);
            }
        }

        class SimpleAdapter extends PagerAdapter
        {
            private List<NewsInfo.News> mHeaderData;
            private List<View> mViews;
            public SimpleAdapter()
            {
                Log.e("TAG","PagerAdapter");
                mHeaderData = new ArrayList<>();
                mViews = new ArrayList<>();
                int i = 0;
                int num  = 0;
                while (num<5 && i <mData.getPagebean().getContentlist().size() )
                {
                    if(mData.getPagebean().getContentlist().get(i).getImageurls().size()>0)
                    {
                        mHeaderData.add(mData.getPagebean().getContentlist().get(i));
                        num++;
                    }
                    i++;
                }
            }
            @Override
            public int getCount() {
                return mHeaderData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {
                view.removeView(mViews.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                View root = LayoutInflater.from(getApplication()).inflate(R.layout.view_newsheader_item,view,false);
                ImageView iv = (ImageView)root.findViewById(R.id.news_img);
                TextView tv = (TextView)root.findViewById(R.id.news_title);
                getAction().getImg(mHeaderData.get(position).getImageurls().get(0).getUrl(),iv,null,iv.getMaxHeight(),iv.getMaxWidth());
                tv.setText(mHeaderData.get(position).getTitle());
                mViews.add(position, root);
                view.addView(root);
                Log.e("TAG","on!!!!!!!!!!!!!!!!!!!!!!!");
                return root;
            }
        }
    }
}
