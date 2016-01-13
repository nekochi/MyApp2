package com.nekomimi.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nekomimi.R;
import com.nekomimi.activity.HomeActivity;
import com.nekomimi.adapter_listener.RecyclerScorllListener;
import com.nekomimi.base.AppConfig;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.bean.EHentaiMangaInfo;
import com.nekomimi.bean.MangaDataBuilder;
import com.nekomimi.bean.MangaInfo;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.util.JsonUtil;

import org.json.JSONObject;

import java.util.List;


public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
{
   // private Button mTestBt = null;
    private RecyclerView mRecycleView = null;
    private List<MangaInfo> mMangaDateList;
    private List<EHentaiMangaInfo> mEDataList;
    private MangaItemRcAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mLastVisibleItem;

    private Handler mHandle = new Handler(){
        public void handleMessage(Message msg)
        {
           switch (msg.what)
           {
               case 0:
                  // Log.d("handler", String.valueOf(msg.obj));
                   mEDataList = ( List<EHentaiMangaInfo> ) msg.obj;
                   if(mEDataList.isEmpty()) return;
                   mAdapter.setEData(mEDataList);
                   mRecycleView.setAdapter(mAdapter);
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
//        Map<String,String> request = new HashMap<>();
//        request.put("name","");
//        request.put("type","");
//        request.put("skip","");
//        request.put("finish","");
//        request.put("key", "e00b1e6d896c4f57ae552ab257186680");
//        Util.showProgress(true, mRecycleView, mProgressView);
        MangaDataBuilder mb = MangaDataBuilder.getInstance(this.getContext());
        mb.getMangaList(mHandle,this.getContext());
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
//         mAdapter.setOnItemClickListener(new MangaItemRcAdapter.OnRecyclerClickListener() {
//             @Override
//             public void onItemClick(View view,MangaItemRcAdapter.MangaItemHolder holder)
//             {
                 //TO DO: 漫画卡片点击事件
//                 Toast.makeText(HomeFragment.this.getActivity(),holder.getData().getName(),Toast.LENGTH_SHORT).show();
//                 Intent intent = new Intent(getActivity(), MangaInfoActivity.class);
//                 intent.putExtra("mangainfo",holder.getData());
//                 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
//                 {
//                     MangaInfoActivity.launch((HomeActivity)getActivity(),holder.getImage(),intent);
//                 }
//                 else
//                 {
//                     startActivity(intent);
//                 }
//             }
//         });
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




    /**
     * Created by hongchi on 2015-9-9.
     */
    public class MangaItemRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

        private int mType = 0;

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        private Context mContext;
        private List<MangaInfo> mDataList = null;
        private List<EHentaiMangaInfo> mEDataList = null;
        private int mImgWidth;
        private int mImgHeight;
//        private OnRecyclerClickListener mOnRecyclerClickListener = null;

        public MangaItemRcAdapter() {
            super();
            mImgHeight = AppConfig.mScanHeight / 4;
            mImgWidth = AppConfig.mScanWidth / 4;
        }

        public MangaItemRcAdapter(List<MangaInfo> data) {
            super();
            mDataList = data;
            mImgHeight = AppConfig.mScanHeight / 4;
            mImgWidth = AppConfig.mScanWidth / 4;
        }

        public void setData(List<MangaInfo> datalist) {
            this.mDataList = datalist;
            this.mType = 0;
        }

        public void setEData(List<EHentaiMangaInfo> datalist) {
            this.mEDataList = datalist;
            this.mType = 1;
        }

        public List getData() {
            if (mType == 1)
                return mEDataList;
            else {
                return mDataList;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(NekoApplication.getInstance()).inflate(R.layout.view_mangacard, parent, false);
                MangaItemHolder holder = new MangaItemHolder(view);
                view.setOnClickListener(this);
                return holder;
            } else if (viewType == TYPE_FOOTER) {
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
            if (holder instanceof MangaItemHolder) {
                if (mType == 0) {
                    ((MangaItemHolder) holder).itemView.setTag(holder);
                    ((MangaItemHolder) holder).mData = mDataList.get(position);
                    ((MangaItemHolder) holder).mType_Tv.setText(mDataList.get(position).getType());
                    ((MangaItemHolder) holder).mTitle_Tv.setText(mDataList.get(position).getName());
                    ((MangaItemHolder) holder).mArea_Tv.setText(mDataList.get(position).getArea());
                    VolleyConnect.getInstance().getImg(mDataList.get(position).getCoverImg(), ((MangaItemHolder) holder).mFace_Iv, null, mImgHeight, mImgWidth);
                }else if(mType == 1)
                {
                    ((MangaItemHolder) holder).itemView.setTag(holder);
                    ((MangaItemHolder) holder).mData = mEDataList.get(position);
                    ((MangaItemHolder) holder).mType_Tv.setText(mEDataList.get(position).category);
                    ((MangaItemHolder) holder).mTitle_Tv.setText(mEDataList.get(position).title);
                    ((MangaItemHolder) holder).mArea_Tv.setText(String.valueOf(mEDataList.get(position).rating));
                    VolleyConnect.getInstance().getImg(mEDataList.get(position).thumb, ((MangaItemHolder) holder).mFace_Iv, null, mImgHeight, mImgWidth);
                }

            }
        }

        @Override
        public int getItemCount() {
            if (mType == 1) {
                return mEDataList.size() + 1;
            } else {
                return mDataList.size() + 1;
            }
        }

        @Override
        public void onClick(View view) {
            MangaItemHolder holder = ((MangaItemHolder) view.getTag());
            holder.mFace_Iv.setDrawingCacheEnabled(true);
            //holder.mData.setCoverImgBm((holder.mFace_Iv.getDrawingCache()).copy(Bitmap.Config.RGB_565, false));
            holder.mFace_Iv.setDrawingCacheEnabled(false);
//            mOnRecyclerClickListener.onItemClick(view, holder);
        }


        public class MangaItemHolder extends RecyclerView.ViewHolder {
            private ImageView mFace_Iv;
            private TextView mTitle_Tv;
            private TextView mType_Tv;
            private TextView mArea_Tv;
            private Object mData;

            public MangaItemHolder(View view) {
                super(view);
                mArea_Tv = (TextView) view.findViewById(R.id.tv_mangaarea);
                mFace_Iv = (ImageView) view.findViewById(R.id.iv_face);
                mTitle_Tv = (TextView) view.findViewById(R.id.tv_mangatitle);
                mType_Tv = (TextView) view.findViewById(R.id.tv_mangatype);
                //mData = new MangaInfo();
            }

            public Object getData() {
                return this.mData;
            }

            public ImageView getImage() {
                return this.mFace_Iv;
            }
        }

        class FootViewHolder extends RecyclerView.ViewHolder {
            public FootViewHolder(View view) {
                super(view);
            }
        }

//        public void setOnItemClickListener(OnRecyclerClickListener listener) {
//            this.mOnRecyclerClickListener = listener;
//        }
//
//        public interface OnRecyclerClickListener {
//            void onItemClick(View view, MangaItemHolder holder);
//        }
    }

}
