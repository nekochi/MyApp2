package com.nekomimi.net;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
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
    private ResponseHeader mResponseHeader;
    Map<String,String> mHeader = new HashMap<>();
    Map<String,String> mParams = new HashMap<>();



    public static NekoStringRequest create(int method, String url,Response.Listener listener,Response.ErrorListener errorListener,Map<String,String>map)
    {
        return new NekoStringRequest(method,url,listener,errorListener,map);
    }
    public NekoStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Map<String,String>map) {
        super(method, url, listener, errorListener);
        this.mParams = map;
    }
//    public NekoStringRequest(  String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Handler handler) {
//        super( url ,listener,errorListener);
//        this.mHandler = handler;
//    }

    @Override
    public Map<String,String> getHeaders() throws AuthFailureError
    {
//        mHeader.put(AppConfig.USERAGENT,AppConfig.getInstance().getUserAgent());
        mHeader.put(AppConfig.COOKIE,AppConfig.getInstance().getCookie());
//        mHeader.put("Connection","keep-alive");
//        mHeader.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        mHeader.put("Accept-Encoding","gzip, deflate");
//        mHeader.put("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
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
//
            if(!TextUtils.isEmpty(m))
            {
                AppConfig.getInstance().setCookie(m.split(";"));
                Log.e("request-cookie", mHeader.get(AppConfig.COOKIE));
                Log.e("COOKIE",m);
                Log.e("Header", responseHeader.toString());
            }
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
