package com.nekomimi.adapter_listener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by hongchi on 2015-10-27.
 */
public class SuperScrollView extends ScrollView {

    private View mHeader;
    private View mFooter;

    private float mLastTouchY;

    public SuperScrollView(Context context) {
        super(context);
    }

    public SuperScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeader(View view)
    {
        this.mHeader = view;
    }

    public void setFooter(View view)
    {
        this.mFooter = view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_DOWN:
                this.mLastTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x,y,oldx,oldy);
    }




    interface OnScrollListener{

    }
}
