package com.nekomimi.base;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nekomimi.api.Api;
import com.nekomimi.api.ApiImpl;


/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public class AppActionImpl implements AppAction {

    private Context mContext;
    private Api mApi;
    private Handler mHandler;
    private Response.Listener onSuccessListener;
    private Response.ErrorListener onErrorListener;


    private static final String LOGIN = "Login";
    private static final String ACCESS = "Access";
    private static final String GETMANGALIST = "GetMangaList";

    public AppActionImpl(Context context)
    {
        this.mContext = context;
        this.mApi = new ApiImpl();
    }



    @Override
    public void login(String name,String password,Handler handler)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespStrListener(LOGIN);
        this.onErrorListener = new ErrorListener(LOGIN);
        this.mApi.login(name, password, onSuccessListener, onErrorListener);
    }

    @Override
    public void access(Handler handler ,String ...s)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespStrListener(ACCESS);
        this.onErrorListener = new ErrorListener(ACCESS);
        this.mApi.access(onSuccessListener,onErrorListener, s);
    }

    @Override
    public void getMangaInfo(Handler handler)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespStrListener(GETMANGALIST);
        this.onErrorListener = new ErrorListener(GETMANGALIST);
    }

    @Override
    public void getImg( String url, ImageView imageView,Callback<Void> callback,int height,int width)
    {
        this.mApi.getImg(url,imageView,callback,height,width);
    }


    private <T> void handlerMessage(T msg,String tag)
    {
        switch (tag)
        {
            case LOGIN:

                break;
            case ACCESS:

                break;
            default:
                break;
        }

    }



    class RespStrListener implements Response.Listener<String>
    {
        private String mTag;
        public RespStrListener(String tag)
        {
            this.mTag = tag;
        }
        @Override
        public void onResponse(String  t)
        {
            Log.e("TAG", t);
            handlerMessage(t, mTag);

        }
    }

    class ErrorListener implements Response.ErrorListener
    {
        private String mTag;
        public ErrorListener(String tag)
        {
            this.mTag = tag;
        }
        @Override
        public void onErrorResponse(VolleyError volleyError)
        {

        }
    }

    public class Callback<T>
    {
        public void onResponce(boolean event,T arg)
        {

        }
    }
}
