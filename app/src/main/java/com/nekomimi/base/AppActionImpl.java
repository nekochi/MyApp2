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
import com.nekomimi.bean.NewsInfo;
import com.nekomimi.net.VolleyConnect;
import com.nekomimi.util.HtmlUtil;
import com.nekomimi.util.JsonUtil;

import org.json.JSONObject;


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
                    AppConfig.getInstance().set(AppConfig.ISLOGINED, "false");
                    message.what = 1;
                } else {
                    AppConfig.getInstance().set(AppConfig.ISLOGINED, "true");
                    message.what = 0;
                }
                mHandler.sendMessage(message);
                break;
            }
            case ACCESS:
                HtmlUtil.parseElement((String)msg);
                break;
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
            System.out.println(t);
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
