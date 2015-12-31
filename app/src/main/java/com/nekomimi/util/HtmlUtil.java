package com.nekomimi.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by hongchi on 2015-12-31.
 * File description :
 */
public class HtmlUtil
{
    public static void parseElement(String html)
    {
        Log.e("Html","start");
        Document doc = Jsoup.parseBodyFragment(html);
        Elements list = doc.getElementsByClass("lt1");
        for(int i = 0 ; i<list.size();i++)
        {
            Log.e("HtmlUtil",list.get(i).toString());
        }
    }


}
