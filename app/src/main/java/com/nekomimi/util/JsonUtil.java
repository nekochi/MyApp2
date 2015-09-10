package com.nekomimi.util;

import com.nekomimi.bean.MangaInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2015-9-8.
 */
public class JsonUtil {

    public static List<MangaInfo> paresMangaInfo(JSONObject jsonObject)
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
}
