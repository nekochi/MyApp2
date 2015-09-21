package com.nekomimi.net;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nekomimi.R;
import com.nekomimi.base.NImageCache;
import com.nekomimi.base.NekoApplication;




/**
 * Created by hongchi on 2015-8-21.
 */
public class VolleyConnect {
    public static final String TAG = "VolleyConnect";
    public static final String HOST = "http://www.baidu.com";

    private Context mContext;
    private RequestQueue mQueue;
    private static VolleyConnect mVolleyConnect;
    public static VolleyConnect getInstance() {
        if (mVolleyConnect == null)
        {
            mVolleyConnect = new VolleyConnect();
            mVolleyConnect.mContext = NekoApplication.getInstance();
            mVolleyConnect.mQueue = Volley.newRequestQueue(mVolleyConnect.mContext);
        }
        return mVolleyConnect;
    }

    public  void connect()
    {
//        connect(HOST);
    }
    public  void connect(Request request)
    {
//        NekoStringRequest stringRequest = new NekoStringRequest(Request.Method.GET, url ,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//              System.out.println(s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("ERROR",volleyError.getMessage(),volleyError);
//
//            }
//        });
//        stringRequest.setRequestProperty("apikey","31079c31653c3d102a92cebdda04c267");
        Log.d(TAG,request.getUrl());
        mQueue.add(request);
    }

    public  void getImg(ImageView imageView,String url)
    {
        getImg(imageView,url,0,0);
    }
    public void getImg(ImageView imageView,String url,int width,int height)
    {
        ImageLoader imageLoader = new ImageLoader(mQueue, NImageCache.getInstance());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,0,R.drawable.ic_launcher);
        imageLoader.get(url,listener,width,height);
    }
}
