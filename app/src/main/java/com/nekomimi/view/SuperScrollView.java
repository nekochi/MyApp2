package com.nekomimi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.nekomimi.R;

/**
 * Created by hongchi on 2015-11-6.
 */
public class SuperScrollView extends ScrollView
{

    private ViewGroup mContent = null;
    private int mHeaderHeight = -1;
    private int mContentHeight  = -1;

    public SuperScrollView(Context context)
    {
        super(context);
    }

    public SuperScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(getChildAt(0) instanceof ViewGroup)
            mContent = (ViewGroup)getChildAt(0);

    }

    public SuperScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SuperScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void fling(int velocityY)
    {
        super.fling(velocityY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(mContentHeight<=0) {
            mContentHeight = this.getChildAt(0).findViewById(R.id.ll_content).getHeight();
        }
        return super.onTouchEvent(ev);
    }

    interface onSwipeListener{
        void onSwipe();
    }
}

