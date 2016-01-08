package com.nekomimi.base;

import android.os.Handler;
import android.widget.ImageView;

import com.nekomimi.bean.EHentaiMangaInfo;

import java.util.List;

/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public interface AppAction
{
    void login(String name, String password, Handler handler);

    void access(Handler handler, String ...s);

    void gdata(Handler handler, List<EHentaiMangaInfo> list);

    void getMangaInfo(Handler handler);

    void getNews(Handler handler);

    void getNews(Handler handler, String title, String page);

    void getImg( String url, ImageView imageView,AppActionImpl.Callback<Void> callback,int height,int width);
}
