package com.nekomimi.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekomimi.R;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.NewsInfo;

import java.util.List;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class NewsActivity extends BaseActivity
{
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_news);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_newslist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {

        private List<NewsInfo> mNewsList = null;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_newscard,parent,false);
            NewsHolder holder = new NewsHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        class NewsHolder extends RecyclerView.ViewHolder
        {

            public NewsHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
