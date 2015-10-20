package com.nekomimi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nekomimi.R;
import com.nekomimi.activity.MangaReaderActivity;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.MangaChapterInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2015-9-14.
 */
public class MangaInfoFragment extends Fragment {
    private RecyclerView mChapterRv;
    private List<MangaChapterInfo> mDatas;
    private String mMangaName;


//    private Handler mHandler = new Handler(){
//        public void handleMessage(Message msg)
//        {
//            switch (msg.what)
//            {
//                case 1:
//                    List<MangaImgInfo> object = JsonUtil.parseImgUrlList((JSONObject)msg.obj);
//                    Intent intent = new Intent(getActivity(), MangaReaderActivity.class);
//                    intent.putExtra("img", (Serializable)object);
//                    startActivity(intent);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    public static MangaInfoFragment create(List<MangaChapterInfo> data,String name)
    {
        MangaInfoFragment fragment = new MangaInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Name",name);
        bundle.putSerializable("Data", (Serializable) data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if(arg != null)
        {
            mMangaName = arg.getString("Name");
            mDatas = (ArrayList<MangaChapterInfo>) arg.getSerializable("Data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mangainfo, container, false);
        mChapterRv = (RecyclerView) root.findViewById(R.id.rv_chapter);
        mChapterRv.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        MangaChapterAdapter adapter = new MangaChapterAdapter(mDatas);
        adapter.setRecyclerListener(new OnChapterClickListener() {
            @Override
            public void onChapterClick(View view, MangaChapterInfo data) {
                Intent intent = new Intent(getActivity(),MangaReaderActivity.class);
                intent.putExtra("mangaName",mMangaName);
                intent.putExtra("id",data.getChapterId());
                startActivity(intent);
//                Map<String,String> request = new HashMap<String, String>();
//                request.put("comicName",mMangaName);
//                request.put("id",data.getChapterId());
//                request.put("key", "e00b1e6d896c4f57ae552ab257186680");
//                VolleyConnect.getInstance().connect(NekoJsonRequest.create(Util.makeHtml(AppConfig.MANGACHAPTER_URL, request, "UTF-8"), mHandler));
            }
        });
        mChapterRv.setAdapter(adapter);
        return root;
    }

    class MangaChapterAdapter extends RecyclerView.Adapter<MangaChapterAdapter.MangaChapterHolder> implements View.OnClickListener
    {
        private List<MangaChapterInfo> mDatas;
        private OnChapterClickListener mListener;

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
        public void onBindViewHolder(final MangaChapterHolder holder, int position) {
            holder.itemView.setTag(holder);
            holder.mData = this.mDatas.get(position);
            holder.mChapterBt.setTag(holder);
            holder.mChapterBt.setText(mDatas.get(position).getChapterName());
            holder.mChapterBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null)
                    mListener.onChapterClick(view,((MangaChapterHolder)view.getTag()).mData);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public void onClick(View view) {
            MangaChapterHolder holder = (MangaChapterHolder)view.getTag();
            mListener.onChapterClick(view,holder.mData);
        }

        public void setRecyclerListener(OnChapterClickListener listener)
        {
            this.mListener = listener;
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
    public static interface OnChapterClickListener
    {
        void onChapterClick(View view ,MangaChapterInfo data);
    }

}
