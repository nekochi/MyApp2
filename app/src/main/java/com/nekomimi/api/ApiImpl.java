package com.nekomimi.api;

import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.nekomimi.base.AppActionImpl;
import com.nekomimi.bean.EHentaiMangaInfo;
import com.nekomimi.net.VolleyConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public class ApiImpl implements Api
{
    private VolleyConnect mVolleyConnect;

    private static final String HOST = "http://exhentai.org/";
    private static final String API_HOST = "http://exhentai.org/api.php";
//    private static final String HOST = "http://exhentai.org/g/890226/60843bdee2/";

    public ApiImpl()
    {
        mVolleyConnect = VolleyConnect.getInstance();
    }


    @Override
    public void login(String userName,String password,Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
        Map<String,String> map = new HashMap<>();
        map.put("UserName",userName);
        map.put("PassWord",password);
        map.put("CookieDate", "1");
        mVolleyConnect.postStringRequest(null,"https://forums.e-hentai.org/index.php?act=Login&CODE=01",listener,errorListener,map);
//        mVolleyConnect.getStringRequest(map,"https://forums.e-hentai.org/index.php?act=Login&CODE=01",listener,errorListener);
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
        Log.e("access",sb.toString());
         mVolleyConnect.getStringRequest(null,sb.toString(),listener,errorListener);
    }

    @Override
    public void gdata(List<EHentaiMangaInfo> list, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        JSONObject object = new JSONObject();
        try {
            object.put("method","gdata");
            JSONArray gidlist = new JSONArray();
            for(int i = 0; i < list.size() ; i++)
            {
                JSONArray item = new JSONArray();
                item.put(0,Integer.valueOf(list.get(i).gid));
                item.put(1,list.get(i).token);
                gidlist.put(item);
            }
            object.put("gidlist",gidlist);
            mVolleyConnect.postJsonRequest(null,API_HOST,listener,errorListener,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMangaList(String name, String type, String skip, String finish, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("type",type);
        map.put("skip",skip);
        mVolleyConnect.getJsonRequest(map,"",listener,errorListener);
    }

    @Override
    public void getNews(String channelId, String channelName, String title, String page, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        Map<String,String> map = new HashMap<>();
        map.put("channelId",channelId!=null?channelId:"5572a109b3cdc86cf39001db");
        map.put("channelName",channelName!=null?channelName:"国内最新");
        map.put("title",title!=null?title:"");
        map.put("page",page!=null?page:"1");
        mVolleyConnect.getJsonRequest(map,"http://apis.baidu.com/showapi_open_bus/channel_news/search_news",listener,errorListener);
    }

    @Override
    public void getImg(String url, ImageView view, AppActionImpl.Callback<Void> callback, int height, int width)
    {
        mVolleyConnect.getImg(url,view,callback,height,width);
    }
}
