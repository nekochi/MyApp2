package com.nekomimi.net;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.nekomimi.base.AppConfig;
import com.nekomimi.bean.ResponseHeader;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongchi on 2015-8-24.
 */
public class NekoStringRequest extends StringRequest
{
    private static final String COOKIE = "Cookie";
    private static Handler mHandler;
    private ResponseHeader mResponseHeader;
    Map<String,String> mHeader = new HashMap<String,String>();
    Map<String,String> mParams = new HashMap<String,String>();

    private static Response.Listener<String> mListener = new Response.Listener<String>(){
        @Override
        public void onResponse(String s) {
            Log.d("onResponse", s);
            Message msg = new Message();
            msg.obj = s;
            msg.what = 0;
            mHandler.sendMessage(msg);
        }
    };
    private static Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };
    public static NekoStringRequest create(String url,Handler handler)
    {
        return create(Method.GET,url,handler,null);
    }
    public static NekoStringRequest create(int method, String url,Handler handler,Map<String,String>map)
    {
        return new NekoStringRequest(method,url,mListener,mErrorListener,handler,map);
    }
    public NekoStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Handler handler,Map<String,String>map) {
        super(method, url, listener, errorListener);
        this.mHandler = handler;
        this.mParams = map;
    }
//    public NekoStringRequest(  String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Handler handler) {
//        super( url ,listener,errorListener);
//        this.mHandler = handler;
//    }

    @Override
    public Map<String,String> getHeaders() throws AuthFailureError
    {
        mHeader.put(AppConfig.USERAGENT,AppConfig.getInstance().getUserAgent());
        mHeader.put(AppConfig.COOKIE,AppConfig.getInstance().getCookie());
        mHeader.put("Connection","keep-alive");

        return mHeader;
    }
   @Override
    public Map<String, String> getParams() throws AuthFailureError
    {
        return mParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Map<String,String> responseHeader = response.headers;
            String m = responseHeader.get("Set-Cookie");

            if(!TextUtils.isEmpty(m))
            {
                AppConfig.getInstance().setCookie(m);
                Log.d("COOKIE",m);
                Log.d("Header", responseHeader.toString());
            }
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
