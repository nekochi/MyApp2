package com.nekomimi.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.nekomimi.api.Api;
import com.nekomimi.api.ApiImpl;
import com.nekomimi.api.ApiResponse;
import com.nekomimi.bean.EHentaiMangaInfo;
import com.nekomimi.bean.MangaDataBuilder;
import com.nekomimi.bean.NewsInfo;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
    private static final String NEWSLIST = "NewsList";
    private static final String GDATA = "Gdata";

    public static final int CODE_LOGIN = 100000;
    public static final int CODE_ACCESS = 100001;
    public static final int CODE_GETMANGALIST = 100002;
    public static final int CODE_NEWSLIST = 100003;
    public static final int CODE_GDATA = 100004;

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
    public void gdata(Handler handler, List<EHentaiMangaInfo> list)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespJsonListener(GDATA);
        this.onErrorListener = new ErrorListener(GDATA);
        this.mApi.gdata(list,onSuccessListener,onErrorListener);
    }

    @Override
    public void getMangaInfo(Handler handler)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespStrListener(GETMANGALIST);
        this.onErrorListener = new ErrorListener(GETMANGALIST);
    }

    @Override
    public void getNews(Handler handler)
    {
        getNews(handler,null,null);
    }

    @Override
    public void getNews(Handler handler, String title , String page)
    {
        this.mHandler = handler;
        this.onSuccessListener = new RespJsonListener(NEWSLIST);
        this.onErrorListener = new ErrorListener(NEWSLIST);
        this.mApi.getNews(null,"",title,page,onSuccessListener,onErrorListener);
    }

    @Override
    public void getImg( String url, ImageView imageView,Callback<Void> callback,int height,int width)
    {
        VolleyConnect.getInstance().getImg(url,imageView,callback,height,width);
//        this.mApi.getImg(url,imageView,callback,height,width);
    }


    private <T> void handlerMessage(T msg,String tag)
    {
        Log.d("AppAction", "AppAction thread id is " + Thread.currentThread().getId());
        switch (tag)
        {
            case NEWSLIST:
            {
                ApiResponse<NewsInfo> response = JsonUtil.toApiRes((JSONObject) msg, new TypeToken<NewsInfo>() {}.getType());
                if(response == null) return;
                NewsInfo info = response.getObj();
                Message message = new Message();
                message.what = Integer.valueOf(response.getEvent());
                message.arg1 = info.getPagebean().getCurrentPage();
                message.obj = info;
                mHandler.sendMessage(message);
                break;
            }
            case LOGIN:
            {
                Message message = new Message();
                if (AppConfig.getInstance().getCookie(AppConfig.IPB_MEMBER_ID).equals(AppConfig.NOT_EXIST) ||
                        AppConfig.getInstance().getCookie(AppConfig.IPB_PASS_HASH).equals(AppConfig.NOT_EXIST) ||
                        AppConfig.getInstance().getCookie(AppConfig.IPB_SESSION_ID).equals(AppConfig.NOT_EXIST)) {
                    Toast.makeText(mContext, "Login Failed! Please check your password or account", Toast.LENGTH_LONG).show();
//                    AppConfig.getInstance().set(AppConfig.ISLOGINED, "false");
                    AppConfig.getInstance().setIslogined(false);
                    message.what = 1;
                } else {
//                    AppConfig.getInstance().set(AppConfig.ISLOGINED, "true");
                    AppConfig.getInstance().setIslogined(true);
                    message.what = 0;
                }
                mHandler.sendMessage(message);
                break;
            }
            case ACCESS:
            {
//                ArrayList<EHentaiMangaInfo> result = MangaDataBuilder.parseMangaList((String) msg);
                MangaDataBuilder mangaDataBuilder = MangaDataBuilder.getInstance(mContext);
                ArrayList<EHentaiMangaInfo> result = mangaDataBuilder.parseMangaList((String) msg);
                Message message = new Message();
                message.what = 0;
                message.arg1 = CODE_ACCESS;
                message.obj = result;
                mHandler.sendMessage(message);
                break;
            }
            case GDATA:
            {
                ApiResponse<NewsInfo> response = JsonUtil.toEHApiRes((JSONObject) msg, new TypeToken<List<EHentaiMangaInfo>>() {}.getType());
                if(response == null) return;
                Message message = new Message();
                message.what = 0;
                message.arg1 = CODE_GDATA;
                message.obj = response.getObj();
                mHandler.sendMessage(message);
                break;
            }
            default:
                break;
        }

    }


    class RespJsonListener implements Response.Listener<JSONObject>
    {
        private String mTag;
        public RespJsonListener(String tag)
        {
            this.mTag = tag;
        }
        @Override
        public void onResponse(JSONObject jsonObject) {
            Log.e(mTag, jsonObject.toString());
            handlerMessage(jsonObject, mTag);
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
//            int maxLogSize = 1000;
//
//            for(int i = 0; i <= t.length() / maxLogSize; i++) {
//
//                int start = i * maxLogSize;
//
//                int end = (i+1) * maxLogSize;
//
//                end = end > t.length() ? t.length() : end;
//
//                Log.v(mTag, t.substring(start, end));
//
//            }
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

    public  interface  Callback<T>
    {
        public  void onResponce(boolean event,T arg);
    }
}
