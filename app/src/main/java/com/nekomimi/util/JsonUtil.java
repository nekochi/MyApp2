package com.nekomimi.util;

import com.google.gson.Gson;
import com.nekomimi.api.ApiResponse;
import com.nekomimi.bean.MangaChapterInfo;
import com.nekomimi.bean.MangaImgInfo;
import com.nekomimi.bean.MangaInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2015-9-8.
 */
public class JsonUtil {

    public static List<MangaInfo> parseMangaInfo(JSONObject jsonObject)
    {
        List<MangaInfo> result = new ArrayList<>();
        try {
            JSONObject jb1 = jsonObject.getJSONObject("result");
            JSONArray list = jb1.getJSONArray("bookList");
            for (int i = 0 ; i < list.length() ; i++)
            {
                MangaInfo info = new MangaInfo();
                JSONObject jb = list.getJSONObject(i);
                info.setName(jb.getString("name"));
                info.setArea(jb.getString("area"));
                info.setType(jb.getString("type"));
                info.setDes(jb.getString("des"));
                info.setFinish(jb.getString("finish"));
                info.setLastUpdate(jb.getString("lastUpdate"));
                info.setCoverImg(jb.getString("coverImg"));
                result.add(info);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static List<MangaChapterInfo> praseMangaChapter(JSONObject jsonObject)
    {
        List<MangaChapterInfo> result = new ArrayList<>();
        try{
            JSONObject jb1 = jsonObject.getJSONObject("result");
            JSONArray list = jb1.getJSONArray("chapterList");
            for(int i = 0 ; i < list.length() ; i++)
            {
                JSONObject jb = list.getJSONObject(i);
                MangaChapterInfo info = new MangaChapterInfo();
                info.setChapterId(jb.getString("id"));
                info.setChapterName(jb.getString("name"));
                result.add(info);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static List<MangaImgInfo> parseImgUrlList(JSONObject jsonObject)
    {
        List<MangaImgInfo> result = new ArrayList<>();
        try{
            JSONObject jb = jsonObject.getJSONObject("result");
            JSONArray list = jb.getJSONArray("imageList");
            for(int i = 0 ; i < list.length() ; i++)
            {
                MangaImgInfo imgInfo = new MangaImgInfo();
                JSONObject info = list.getJSONObject(i);
                imgInfo.setId(info.getString("id"));
                imgInfo.setImgUrl(info.getString("imageUrl"));
                result.add(imgInfo);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        return result;
    }

    public  static  ApiResponse toApiRes(JSONObject object,Type type)
    {
        String event = "";
        String msg = "";
        String obj = "";
        try {
            event = object.getString("showapi_res_code");
            msg = object.getString("showapi_res_error");
            obj = object.getJSONObject("showapi_res_body").toString();
            Gson gson = new Gson();
            ApiResponse response = new ApiResponse();
            response.setEvent(event);
            response.setMsg(msg);
            response.setObj(gson.fromJson(obj,type));
            return response;
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
