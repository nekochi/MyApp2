package com.nekomimi.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
        getNews();
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_newslist);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsAdapter();

    }

    public void getNews()
    {
        getAction().getNews(mHandler);
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
                Toast.makeText(this,"search",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_fresh:
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
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(NewsActivity.this, "onExpand", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(NewsActivity.this, "Collapse", Toast.LENGTH_LONG).show();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onRefresh()
    {
        getNews();
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
