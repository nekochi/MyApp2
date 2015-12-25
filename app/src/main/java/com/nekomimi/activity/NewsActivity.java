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
import android.widget.ProgressBar;
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
    private List<NewsInfo.News> mNewsList = new ArrayList<>();
    private List<NewsInfo.News> mHeaderDataList = new ArrayList<>();
    private NewsAdapter.SimpleAdapter mSimpleAdapter;


    private String mTitle;
    private int mPage;
    private int mAllPage;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_news);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_news);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_newslist);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount())
                {
                    mAdapter.islastPage(mPage == mAllPage);
                    if(mPage != mAllPage) {
                        getAction().getNews(mHandler, mTitle, String.valueOf(mPage + 1));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });

        handleSearchIntent(getIntent());
        getAction().getNews(mHandler);
        Log.d("NewsActivity", "NewsActivity thread id is " + Thread.currentThread().getId());
    }

    private void handleSearchIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String key = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG,"title:"+key);
            mTitle = key;
            getAction().getNews(mHandler,key,null);
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
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    menuItem.collapseActionView();
                    searchView.setQuery("",false);
                }
            }
        });
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
        mTitle = "";
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                mData = (NewsInfo) msg.obj;
                if(mData.getPagebean().getCurrentPage() == 1){
                    setNews(mData.getPagebean().getContentlist());
                }else {
                    addNews(mData.getPagebean().getContentlist());
                }
                mPage = mData.getPagebean().getCurrentPage();
                mAllPage = mData.getPagebean().getAllPages();
                mAdapter.notifyDataSetChanged();
                mSimpleAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    public void setNews(List<NewsInfo.News> list)
    {
        mNewsList.clear();
        mHeaderDataList.clear();
        addNews(list);
        int i = 0;
        int num  = 0;
        while (num<5 && i <mNewsList.size() )
        {
            if(mNewsList.get(i).getImageurls().size()>0)
            {
                mHeaderDataList.add(mNewsList.get(i));
                num++;
            }
            i++;
        }
    }
    public void addNews(List<NewsInfo.News> list)
    {
        int num = list.size();
        for(int i = 0 ; i < num ; i++)
        {
            mNewsList.add(list.get(i));
        }
    }

    public void checkNewsInfo(String url)
    {
        Intent intent = new Intent(NewsActivity.this,NewsInfoWebActivity.class);
        intent.putExtra("URL",url);
        startActivity(intent);
    }

    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener
    {

        private final int  HEADER = 0;
        private final int  NEWS = 1;
        private final int  FOOTER = 2;

        public NewsAdapter()
        {
            mSimpleAdapter = new SimpleAdapter();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            Log.e("TAG","onCreateView");
            if(viewType == NEWS)
            {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_newscard,parent,false);
                view.setOnClickListener(this);
                return  new NewsHolder(view);
            }
            else if(viewType == HEADER)
            {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_news_header, parent, false);
                return new NewsHeaderHolder(view);
            }else if(viewType == FOOTER)
            {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.listview_footer,parent,false);
                return new NewsFooterHolder(view);
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
                ((NewsHolder) holder).mDate.setText(mNewsList.get(index).getPubDate());
                ((NewsHolder) holder).mSource.setText(mNewsList.get(index).getSource());
                ((NewsHolder) holder).mBody.setText(mNewsList.get(index).getLong_abs()==null?
                        mNewsList.get(index).getDesc():mNewsList.get(index).getLong_abs());
                ((NewsHolder) holder).mTitle.setText(mNewsList.get(index).getTitle());
                ((NewsHolder) holder).setNewsDate(mNewsList.get(index));
            }
            else if (holder instanceof NewsHeaderHolder)
            {
                holder.itemView.setTag(holder);
                ViewPager vp = ((NewsHeaderHolder) holder).mViewPager;

                vp.setAdapter(mSimpleAdapter);
//
                vp.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int arg0) {
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                        if(arg2 != 0)
                        {
                            mSwipeRefreshLayout.setEnabled(false);
                        }else
                        {
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                    }
                });

            }else if (holder instanceof NewsFooterHolder)
            {
                holder.itemView.setTag(holder);
            }

        }



        @Override
        public int getItemViewType(int position)
        {
            if(position == 0) {
                return HEADER;
            }else if(position == getItemCount() - 1){
                return FOOTER;
            }
            return NEWS;
        }

        @Override
        public int getItemCount() {
            if(mNewsList.size()==0){
                return 0;
            }
            return mNewsList.size()+2;
        }

        public void islastPage(boolean flag)
        {
            NewsFooterHolder holder = (NewsFooterHolder)mRecyclerView.findViewHolderForAdapterPosition(getItemCount()-1);
            if(flag) {
            holder.mLoadingBar.setVisibility(View.GONE);
            holder.mLoadMessageTv.setText("再也找不到更多了哦_(:з」∠)_");
            }else
            {
                holder.mLoadingBar.setVisibility(View.VISIBLE);
                holder.mLoadMessageTv.setText("");
            }
        }

        @Override
        public void onClick(View v)
        {
            NewsHolder holder = (NewsHolder) v.getTag();
            checkNewsInfo(holder.getNewsDate().getLink());
        }

        class NewsHolder extends RecyclerView.ViewHolder
        {
            private TextView mTitle;
            private TextView mBody;
            private TextView mSource;
            private TextView mDate;
            private NewsInfo.News mNewsDate;

            public NewsHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.tv_newstitle);
                mBody = (TextView) itemView.findViewById(R.id.tv_newsbody);
                mSource = (TextView) itemView.findViewById(R.id.tv_newssource);
                mDate = (TextView) itemView.findViewById(R.id.tv_newspubdate);
            }

            public void setNewsDate(NewsInfo.News news)
            {
                this.mNewsDate = news;
            }

            public NewsInfo.News getNewsDate()
            {
                return mNewsDate;
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

        class NewsFooterHolder extends RecyclerView.ViewHolder
        {
            private TextView mLoadMessageTv;
            private ProgressBar mLoadingBar;
            public NewsFooterHolder(View itemView) {
                super(itemView);
                mLoadMessageTv = (TextView)itemView.findViewById(R.id.pull_to_refresh_loadmore_text);
                mLoadingBar = (ProgressBar)itemView.findViewById(R.id.pull_to_refresh_load_progress);
            }
        }
        public class SimpleAdapter extends PagerAdapter
        {
            private List<View> mViews;
            public SimpleAdapter()
            {
                Log.e("TAG","PagerAdapter");
                mViews = new ArrayList<>();
            }
            @Override
            public int getCount() {
                return mHeaderDataList.size();
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
            public Object instantiateItem(ViewGroup view, final int position) {
                View root = LayoutInflater.from(getApplication()).inflate(R.layout.view_newsheader,view,false);
                ImageView iv = (ImageView)root.findViewById(R.id.news_img);
                TextView tv = (TextView)root.findViewById(R.id.news_title);
                getAction().getImg(mHeaderDataList.get(position).getImageurls().get(0).getUrl(), iv, null, iv.getMaxHeight(), iv.getMaxWidth());
                tv.setText(mHeaderDataList.get(position).getTitle());
                mViews.add(position, root);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkNewsInfo(mHeaderDataList.get(position).getLink());
                    }
                });
                view.addView(root);
                return root;
            }
        }
    }


}
