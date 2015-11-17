package com.nekomimi.api;

import android.widget.ImageView;

import com.android.volley.Response;
import com.nekomimi.base.AppActionImpl;
import com.nekomimi.net.VolleyConnect;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public class ApiImpl implements Api
{
    private VolleyConnect mVolleyConnect;

    private static final String HOST = "http://blog.csdn.net";


    public ApiImpl()
    {
        mVolleyConnect = VolleyConnect.getInstance();
    }

    @Override
    public void login(String userName,String password,Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
        Map<String,String> map = new HashMap<>();
        map.put("userName",userName);
        mVolleyConnect.getStringRequest(map,"",listener,errorListener);
    }

    @Override
    public void access(  Response.Listener<String> listener, Response.ErrorListener errorListener ,String ...s)
    {
        StringBuilder sb = new StringBuilder(HOST);
        try {
            for (int i = 0 ; i < s.length ; i++) {
                sb.append("/").append(URLEncoder.encode(s[i], "UTF-8"));
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
         mVolleyConnect.getStringRequest(null,sb.toString(),listener,errorListener);
    }

    @Override
    public void getMangaList(String name, String type, String skip, String finish, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        Map<String,String> map = new HashMap<>();
        mVolleyConnect.getJsonRequest(map,HOST,listener,errorListener);
    }

    @Override
    public void getImg(String url, ImageView view, AppActionImpl.Callback<Void> callback, int height, int width)
    {
        mVolleyConnect.getImg(url,view,callback,height,width);
    }
}
