package com.nekomimi.view;


import com.nekomimi.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopTabView extends RelativeLayout implements View.OnClickListener{

	private static final String TAG = "DEBUG";
	
	private Context mContext;
	private TopTabView mTopTabView;
	private TextView mHomeTab = null;
	private TextView mMarkTab = null;
	private TextView mSearchTab = null;
	private TextView mMeTab = null;
	private View mBottomView = null;
	private TextView mSelectedTab = null;
	private int mSelected;
	private OnSelectedListener mListener;
	
	private int mWidth;
	private int mPosition1;
	private int mPosition2;
	private int mPosition3;
	private int mPosition4;
	
	public TopTabView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public TopTabView(Context context,AttributeSet attr) {
		super(context,attr);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
//		View view = inflater.inflate(R.layout.view_toptab, this ,true);
		mTopTabView = (TopTabView)inflater.inflate(R.layout.view_toptab, this ,true);
		initAllView();
		initBottomView();
		initSelect();
	}
	
	private void initAllView()
	{
		if(mTopTabView == null)
		{
			return;
		}
		mHomeTab = (TextView)mTopTabView.findViewById(R.id.tv_hometab);
		mMarkTab = (TextView)mTopTabView.findViewById(R.id.tv_marktab);
		mSearchTab = (TextView)mTopTabView.findViewById(R.id.tv_searchtab);
		mMeTab = (TextView)mTopTabView.findViewById(R.id.tv_metab);
		mBottomView = mTopTabView.findViewById(R.id.v_bottom);
		mHomeTab.setOnClickListener(this);
		mMarkTab.setOnClickListener(this);
		mSearchTab.setOnClickListener(this);
		mMeTab.setOnClickListener(this);
	}
	
	private void initBottomView()
	{
		if(mBottomView == null)
		{
			return;
		}
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        mWidth = screenW/4;
        LayoutParams lp = (LayoutParams) mBottomView.getLayoutParams();
        lp.width = mWidth;
        mBottomView.setLayoutParams(lp);
        mPosition1 = 0;
        mPosition2 = mWidth;
        mPosition3 = mWidth*2;
        mPosition4 = mWidth*3;
        Log.d(TAG,"mPosition2="+mPosition2+"mPosition3="+mPosition3+"mPosition4="+mPosition4);
	}
	public void initSelect()
	{
		mSelectedTab = mHomeTab;
		mSelected = 1;
		mSelectedTab.setTextColor(Color.WHITE);
	}
	
	public void setSelect(int position)
	{
		if(position<1 || position>4)
		{
			return;
		}
		if(position == mSelected)
		{
			return;
		}
		if(mSelectedTab != null)
		{
			mSelectedTab.setTextColor(Color.parseColor("#DDDDDD"));
			Log.d(TAG, "mSelectedTab.setTextColor(Color.TRANSPARENT)");
		}
		Animation animation = null;
//		float mPosition = mBottomView.getTranslationX();
//		Log.d("mPosition",mPosition+"");
		switch(position)
		{
		case 1:
			mSelectedTab = mHomeTab;
//			animation = new TranslateAnimation(mPosition,mPosition1,0,0);
			if(mSelected == 2)
			{
				animation = new TranslateAnimation(mPosition2,mPosition1,0,0);
			}
			else if(mSelected == 3)
			{
				animation = new TranslateAnimation(mPosition3,mPosition1,0,0);
			}
			else if(mSelected == 4)
			{
				animation = new TranslateAnimation(mPosition4,mPosition1,0,0);
			}
			break;
		case 2:
			mSelectedTab = mMarkTab;
//			animation = new TranslateAnimation(mPosition,mPosition2,0,0);
			if(mSelected == 1)
			{
				animation = new TranslateAnimation(mPosition1,mPosition2,0,0);
			}
			else if(mSelected == 3)
			{
				animation = new TranslateAnimation(mPosition3,mPosition2,0,0);
			}
			else if(mSelected == 4)
			{
				animation = new TranslateAnimation(mPosition4,mPosition2,0,0);
			}
			break;
		case 3:
			mSelectedTab = mSearchTab;
//			animation = new TranslateAnimation(mPosition,mPosition3,0,0);
			if(mSelected == 1)
			{
				animation = new TranslateAnimation(mPosition1,mPosition3,0,0);
			}
			else if(mSelected == 2)
			{
				animation = new TranslateAnimation(mPosition2,mPosition3,0,0);
			}
			else if(mSelected == 4)
			{
				animation = new TranslateAnimation(mPosition4,mPosition3,0,0);
			}
			break;
		case 4:
			mSelectedTab = mMeTab;
//			animation = new TranslateAnimation(mPosition,mPosition4,0,0);
			if(mSelected == 1)
			{
				animation = new TranslateAnimation(mPosition1,mPosition4,0,0);
			}
			else if(mSelected == 2)
			{
				animation = new TranslateAnimation(mPosition2,mPosition4,0,0);
			}
			else if(mSelected == 3)
			{
				animation = new TranslateAnimation(mPosition3,mPosition4,0,0);
			}
			break;
		default:
			break;		
		}
		if(animation == null)
		{
			Log.d(TAG, "animation == null");
			return;
		}
		mSelectedTab.setTextColor(Color.WHITE);
		mSelected = position;
		animation.setFillAfter(true);
        animation.setDuration(300);
        mBottomView.startAnimation(animation);
        mListener.onSelectedChange(mSelected);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.tv_hometab:
			setSelect(1);
			break;
		case R.id.tv_marktab:
			setSelect(2);
			break;
		case R.id.tv_searchtab:
			setSelect(3);
			break;
		case R.id.tv_metab:
			setSelect(4);
			break;
		default:
			break;
		}
	}
	public void setOnSelectedListener(OnSelectedListener listener)
	{
		mListener = listener;
	}
	
	public interface OnSelectedListener{
		public void onSelectedChange(int arg0);
	}
}
