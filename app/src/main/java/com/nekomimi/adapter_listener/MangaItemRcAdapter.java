package com.nekomimi.adapter_listener;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekomimi.R;
import com.nekomimi.base.AppConfig;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.bean.MangaInfo;

import java.util.List;

/**
 * Created by hongchi on 2015-9-9.
 */
public class MangaItemRcAdapter extends RecyclerView.Adapter<MangaItemRcAdapter.MangaItemHolder> implements View.OnClickListener{
    private List<MangaInfo> mDataList = null;
    private int mImgWidth;
    private int mImgHeight;
    private OnRecyclerClickListener mOnRecyclerClickListener = null;

    public MangaItemRcAdapter()
    {
        super();
        mImgHeight = AppConfig.mScanHeight / 4;
        mImgWidth = AppConfig.mScanWidth / 4;
    }
    public MangaItemRcAdapter(List<MangaInfo> data)
    {
        super();
        mDataList = data;
        mImgHeight = AppConfig.mScanHeight / 4;
        mImgWidth = AppConfig.mScanWidth / 4;
    }
    public void setData(List<MangaInfo> datalist)
    {
        this.mDataList = datalist;
    }

    @Override
    public MangaItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_mangacard,parent,false);
        MangaItemHolder holder = new MangaItemHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MangaItemHolder holder, int position) {
        holder.itemView.setTag(mDataList.get(position));
        holder.mType_Tv.setText(mDataList.get(position).getType());
        holder.mTitle_Tv.setText(mDataList.get(position).getName());
        holder.mArea_Tv.setText(mDataList.get(position).getArea());
        VolleyConnect.getInstance().getImg(holder.mFace_Iv,mDataList.get(position).getCoverImg(),mImgWidth,mImgHeight);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onClick(View view) {
        mOnRecyclerClickListener.onItemClick(view,(MangaInfo)view.getTag());
    }


    class MangaItemHolder extends RecyclerView.ViewHolder
    {
        private ImageView mFace_Iv;
        private TextView mTitle_Tv;
        private TextView mType_Tv;
        private TextView mArea_Tv;
        public MangaItemHolder(View view)
        {
            super(view);
            mArea_Tv = (TextView)view.findViewById(R.id.tv_mangaarea);
            mFace_Iv = (ImageView)view.findViewById(R.id.iv_face);
            mTitle_Tv = (TextView)view.findViewById(R.id.tv_mangatitle);
            mType_Tv = (TextView)view.findViewById(R.id.tv_mangatype);
        }
    }

    public void setOnItemClickListener(OnRecyclerClickListener listener)
    {
        this.mOnRecyclerClickListener = listener;
    }

    public static interface OnRecyclerClickListener
    {
        void onItemClick(View view,MangaInfo data);
    }
}
