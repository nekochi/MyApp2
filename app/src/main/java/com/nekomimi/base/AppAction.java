package com.nekomimi.base;

import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public interface AppAction
{
    void login(String name, String password, Handler handler);

    void access(Handler handler, String ...s);

    void getMangaInfo(Handler handler);

    void getNews(Handler handler);

    void getImg( String url, ImageView imageView,AppActionImpl.Callback<Void> callback,int height,int width);
}
