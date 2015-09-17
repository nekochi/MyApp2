package com.nekomimi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.nekomimi.R;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.MangaChapterInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hongchi on 2015-9-14.
 */
public class MangaInfoFragment extends Fragment {
    private RecyclerView mChapterRv;
    private List<MangaChapterInfo> mDatas;

    public static MangaInfoFragment create(List<MangaChapterInfo> data)
    {
        MangaInfoFragment fragment = new MangaInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Data", (Serializable) data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if(arg != null)
            mDatas = (ArrayList<MangaChapterInfo>) arg.getSerializable("Data");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mangainfo, container, false);
        mChapterRv = (RecyclerView) root.findViewById(R.id.rv_chapter);
        mChapterRv.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        mChapterRv.setAdapter(new MangaChapterAdapter(mDatas));
        return root;
    }

    class MangaChapterAdapter extends RecyclerView.Adapter<MangaChapterAdapter.MangaChapterHolder> implements View.OnClickListener{
        private List<MangaChapterInfo> mDatas;

        public MangaChapterAdapter(List<MangaChapterInfo> data)
        {
            this.mDatas = data;
        }

        @Override
        public MangaChapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_mangachapter,parent,false);
            MangaChapterHolder holder = new MangaChapterHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(MangaChapterHolder holder, int position) {
            holder.itemView.setTag(holder);
            holder.mData = this.mDatas.get(position);
            holder.mChapterBt.setText(mDatas.get(position).getChapterName());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public void onClick(View view) {

        }

        class MangaChapterHolder extends RecyclerView.ViewHolder{
            private Button mChapterBt;
            private MangaChapterInfo mData;
            public MangaChapterHolder(View view)
            {
                super(view);
                this.mChapterBt = (Button)view.findViewById(R.id.bt_mangachapter);
                this.mData  = new MangaChapterInfo();
            }
        }
    }
}
