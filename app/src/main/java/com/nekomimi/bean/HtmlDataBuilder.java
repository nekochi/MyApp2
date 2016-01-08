package com.nekomimi.bean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.nekomimi.base.AppActionImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2016-1-4.
 * File description :
 */
public class HtmlDataBuilder
{
    private static final String TAG = "HtmlDataBuilder";
    private static final String GALLERY_KEY = "it5";

    public static ArrayList<EHentaiMangaInfo> parseMangaList(String html)
    {
        ArrayList<EHentaiMangaInfo> result = new ArrayList<>();

        Document doc = Jsoup.parseBodyFragment(html);
        Elements list = doc.getElementsByClass(GALLERY_KEY);
        for(Element node:list)
        {
            EHentaiMangaInfo item = new EHentaiMangaInfo(node.child(0).attr("href"));
            String temp[] = item.mUrl.split("/");    //example: url = http://exhentai.org/g/890254/c021f77bf6/
            item.gid = Integer.valueOf(temp[4]);
            item.token = temp[5];
            result.add(item);
        }


        return result;
    }
    public static void getMangaList(final Handler handler,Context context)
    {
        final List<EHentaiMangaInfo> list = new ArrayList<>();
        final AppActionImpl appAction = new AppActionImpl(context);
        appAction.access(new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        List<EHentaiMangaInfo> t = (List<EHentaiMangaInfo>) msg.obj;
                        for(int i = 0 ;i < t.size() ; i++)
                        {
                            list.add(t.get(i));
                        }
                        appAction.gdata(handler,list);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
