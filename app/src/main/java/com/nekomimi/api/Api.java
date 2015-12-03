package com.nekomimi.api;

import android.widget.ImageView;

import com.android.volley.Response;
import com.nekomimi.base.AppActionImpl;

import org.json.JSONObject;

/**
 * Created by hongchi on 2015-11-10.
 */
public interface Api
{
    void login(String userName,String password,Response.Listener<String> listener,Response.ErrorListener errorListener);

    void access( Response.Listener<String> listener,Response.ErrorListener errorListener,String ...s);

    void getMangaList( String name, String type, String skip, String finish,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener );

    void getNews( String channelId, String channelName, String title, String page, Response.Listener<JSONObject> listener,Response.ErrorListener errorListener);

    void getImg(String url,ImageView view,AppActionImpl.Callback<Void> callback,int height,int width);


}
