package com.nekomimi.adapter_listener;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekomimi.R;
import com.nekomimi.base.AppConfig;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.MangaInfo;
import com.nekomimi.net.VolleyConnect;

import java.util.List;

/**
 * Created by hongchi on 2015-9-9.
 */
public class MangaItemRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

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

    public List<MangaInfo> getData()
    {
        return this.mDataList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_mangacard, parent, false);
            MangaItemHolder holder = new MangaItemHolder(view);
            view.setOnClickListener(this);
            return holder;
        }
        else if(viewType == TYPE_FOOTER)
        {
            View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.listview_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MangaItemHolder ) {
            ((MangaItemHolder)holder).itemView.setTag(holder);
            ((MangaItemHolder)holder).mData = mDataList.get(position);
            ((MangaItemHolder)holder).mType_Tv.setText(mDataList.get(position).getType());
            ((MangaItemHolder)holder).mTitle_Tv.setText(mDataList.get(position).getName());
            ((MangaItemHolder)holder).mArea_Tv.setText(mDataList.get(position).getArea());
            VolleyConnect.getInstance().getImg(((MangaItemHolder)holder).mFace_Iv, mDataList.get(position).getCoverImg(), mImgWidth, mImgHeight);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }

    @Override
    public void onClick(View view) {
        MangaItemHolder holder = ((MangaItemHolder) view.getTag());
        holder.mFace_Iv.setDrawingCacheEnabled(true);
        holder.mData.setCoverImgBm((holder.mFace_Iv.getDrawingCache()).copy(Bitmap.Config.RGB_565,false));
        holder.mFace_Iv.setDrawingCacheEnabled(false);
        mOnRecyclerClickListener.onItemClick(view, holder.mData);
    }


    class MangaItemHolder extends RecyclerView.ViewHolder
    {
        private ImageView mFace_Iv;
        private TextView mTitle_Tv;
        private TextView mType_Tv;
        private TextView mArea_Tv;
        private MangaInfo mData;
        public MangaItemHolder(View view)
        {
            super(view);
            mArea_Tv = (TextView)view.findViewById(R.id.tv_mangaarea);
            mFace_Iv = (ImageView)view.findViewById(R.id.iv_face);
            mTitle_Tv = (TextView)view.findViewById(R.id.tv_mangatitle);
            mType_Tv = (TextView)view.findViewById(R.id.tv_mangatype);
            mData = new MangaInfo();
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder
    {
        public FootViewHolder(View view)
        {
            super(view);
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
