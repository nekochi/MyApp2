package com.nekomimi.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.nekomimi.R;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.NewsInfo;

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

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_newscard,parent,false);
            NewsHolder holder = new NewsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            if(holder instanceof NewsHolder)
            {
                holder.itemView.setTag(holder);
                ((NewsHolder) holder).mData = mData.getPagebean().getContentlist().get(position);
                ((NewsHolder) holder).mDate.setText(mData.getPagebean().getContentlist().get(position).getPubDate());
                ((NewsHolder) holder).mSource.setText(mData.getPagebean().getContentlist().get(position).getSource());
                ((NewsHolder) holder).mBody.setText(mData.getPagebean().getContentlist().get(position).getLong_abs());
                ((NewsHolder) holder).mTitle.setText(mData.getPagebean().getContentlist().get(position).getTitle());
            }

        }

        @Override
        public int getItemCount() {
            return mData.getPagebean().getContentlist().size();
        }

        class NewsHolder extends RecyclerView.ViewHolder
        {
            private TextView mTitle;
            private TextView mBody;
            private TextView mSource;
            private TextView mDate;
            private NewsInfo.News mData;

            public NewsHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.tv_newstitle);
                mBody = (TextView) itemView.findViewById(R.id.tv_newsbody);
                mSource = (TextView) itemView.findViewById(R.id.tv_newssource);
                mDate = (TextView) itemView.findViewById(R.id.tv_newspubdate);
            }
        }
    }
}
