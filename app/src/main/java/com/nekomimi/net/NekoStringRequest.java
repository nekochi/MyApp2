package com.nekomimi.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongchi on 2015-8-24.
 */
public class NekoStringRequest extends StringRequest
{
    private static final String COOKIE = "Cookie";
    Map<String,String> mHeader = new HashMap<String,String>(1);

    public NekoStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
    public NekoStringRequest( String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super( url, listener, errorListener);
    }
    public void setCookie(String cookie)
    {
        mHeader.put(COOKIE, cookie);
    }
    @Override
    public Map<String,String> getHeaders() throws AuthFailureError
    {
        return mHeader;
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Map<String,String> responseHeader = response.headers;
            String m = responseHeader.get("Set-Cookie");
            if(!m.isEmpty())
            {
                Log.d("TAG",m);
            }
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
