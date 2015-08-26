package com.nekomimi.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by hongchi on 2015-8-21.
 */
public class VolleyConnect {

    public static final String HOST = "http://www.baidu.com";


    public static void connect(Context context)
    {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        NekoStringRequest stringRequest = new NekoStringRequest(Request.Method.GET, HOST , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("TAG",s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ERROR",volleyError.getMessage());
            }
        });
        mQueue.add(stringRequest);
    }

    public static void getImg(ImageView imageView,Context context)
    {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
    }
}
