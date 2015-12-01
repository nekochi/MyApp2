package com.nekomimi.net;

import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nekomimi.base.AppActionImpl;
import com.nekomimi.base.NekoApplication;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hongchi on 2015-11-10.
 */
public class VolleyConnect
{
    public static final int GET = Request.Method.GET;
    public static final int POST = Request.Method.POST;
    public static final int SUCCEED = 0;
    public static final int FALSE = -1;

    private VolleyConnect(){}

    private static VolleyConnect mInstance = null;
    private RequestQueue mRequestQueue;
    public static VolleyConnect getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new VolleyConnect();
            mInstance.mRequestQueue = Volley.newRequestQueue(NekoApplication.getInstance());
        }
        return mInstance;
    }

    public void getStringRequest(Map<String,String> params ,String url,Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
        stringRequest(GET,params ,url,listener,errorListener,null);
    }
    public void postStringRequest(Map<String,String> params ,String url,Response.Listener<String> listener
            ,Response.ErrorListener errorListener,Map<String,String> datas)
    {
        stringRequest(POST,params,url,listener,errorListener,datas);
    }
    public void stringRequest( int method,Map<String,String> params ,String url,Response.Listener<String> listener
            ,Response.ErrorListener errorListener,Map<String,String> datas)
    {
        NekoStringRequest sr = NekoStringRequest.create(method, makeHtml(url, params, "UTF-8"), listener, errorListener,datas);
        mRequestQueue.add(sr);
    }

    public void getJsonRequest(Map<String,String> params ,String url,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener)
    {
        jsonRequest(GET, params, url, listener, errorListener, null);
    }
    public void postJsonRequest(Map<String,String> params ,String url,Response.Listener<JSONObject> listener
            ,Response.ErrorListener errorListener,Map<String,String> datas)
    {
        jsonRequest(POST, params, url, listener, errorListener, datas);
    }
    public void jsonRequest(int method,Map<String,String> params ,String url,Response.Listener<JSONObject> listener
            ,Response.ErrorListener errorListener,Map<String,String> datas)
    {
        NekoJsonRequest jr = NekoJsonRequest.create(method,makeHtml(url, params, "UTF-8"),listener,errorListener,datas);
        mRequestQueue.add(jr);
    }

    public void getImg(final String url,final ImageView view,final AppActionImpl.Callback<Void> callback,int height,int width)
    {
        ImageLoader imageLoader = new ImageLoader(mRequestQueue, NImageCache.getInstance());
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener()
        {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b)
            {
                if(imageContainer.getBitmap()!=null)
                {
                    if(view!=null)
                    {
                        view.setImageBitmap(imageContainer.getBitmap());
                    }
                    if(callback!=null)
                        callback.onResponce(true,null);
                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                if(callback!=null)
                    callback.onResponce(false,null);
            }
        };
        imageLoader.get(url,listener,width,height);
    }



    public  String makeHtml(String url,Map<String,String> params,String encode) {
        if (params == null)
        {
            return url;
        }
        StringBuilder urlResult = new StringBuilder();
        try {
            urlResult.append(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlResult.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode)).append("&");
            }
            urlResult.deleteCharAt(urlResult.length() - 1);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return urlResult.toString();
    }

}
