package com.nekomimi.util;

import android.text.TextUtils;

import com.nekomimi.base.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongchi on 2015-8-27.
 */
public class Util {

    public static String toResolution(float width,float height,float density)
    {
        StringBuilder builder = new StringBuilder("width:").append(width).append(";height:").append(height).append(";density:").append(density);
        return builder.toString();
    }


    public static float getResolutionFromStr(String str , String key)
    {
        String []strs = str.split(";");
        for(String s : strs)
        {
            String []ss = s.split(":");
            if(ss[0].equals(key))
            {
                return Float.valueOf(ss[1]);
            }
        }
        return -1;
    }

    //将所有已经保存的用户名加上新增用户名,输出格式是字符串
    public static String addAccount(String pre ,String value)
    {
        if(TextUtils.equals(pre, AppConfig.NOT_EXIST))
        {
            return value + ";";
        }
        else if(parseAccount(pre).contains(value))
        {
            return pre;
        }
        {
            return pre + value + ";";
        }
    }

    //将本地存储的用户名加载到登录界面的自动填充输入框
    public static List<String> parseAccount(String str)
    {
        List<String> list = new ArrayList<String>();
        if(TextUtils.equals(str, AppConfig.NOT_EXIST))
        {
            list.clear();
            return list;
        }
        else
        {
            String []strs = str.split(";");
            for(int i = 0 ; i < strs.length ; i++)
            {
                list.add(strs[i]);
            }
            return list;
        }
    }

    public static Map<String,String> cookieToMap(String cookie)
    {
        Map<String,String> map = new HashMap<>();
        String []c1 = cookie.split(";");
        for(int i = 0 ; i < c1.length ; i++)
        {
            String []c2 = c1[i].split("=");
            map.put(c2[0],c2[1]);
        }
        return map;
    }
    public static String[] cookieToArray(String cookie)
    {
        String []str = new String[2];
        String []c1 = cookie.split(";");
        String []c2 = c1[0].split("=");
        str[0] = c2[0];
        str[1] = c2[1];
        return str;
    }
    public static String cookieToString(Map<String,String> map)
    {
        String str = "";
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            StringBuffer sb = new StringBuffer().append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            str += sb.toString();
        }
        return str;
    }
}
