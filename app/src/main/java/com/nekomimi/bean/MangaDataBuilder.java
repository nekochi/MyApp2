package com.nekomimi.bean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.nekomimi.base.AppAction;
import com.nekomimi.base.AppActionImpl;
import com.nekomimi.db.MangaDatabaseHelper;

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
public class MangaDataBuilder
{
    private static final String TAG = "MangaDataBuilder";
    private static final String GALLERY_KEY = "it5";
    private static MangaDataBuilder mMangaDataBuilder = null;
    private Context mContext;
    private AppAction mAppAction;

    private List<EHentaiMangaInfo> mResultList;
    private List<EHentaiMangaInfo> mDBtList;
    private List<EHentaiMangaInfo> mHtmlList;
    public static MangaDataBuilder getInstance(Context context)
    {
        if(mMangaDataBuilder == null)
        {
            mMangaDataBuilder = new MangaDataBuilder(context);
        }
        return mMangaDataBuilder;
    }
    private MangaDataBuilder(Context context){
        this.mContext = context;
        this.mAppAction = new AppActionImpl(context);
    }

    private Handler mUiHandler = null;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what != 0)
                return;
            switch (msg.arg1)
            {
                case AppActionImpl.CODE_ACCESS: {
                    List<EHentaiMangaInfo> t = (List<EHentaiMangaInfo>) msg.obj;
                    if(t.size() == 0) {return; }
                    MangaDatabaseHelper mangaDatabaseHelper = new MangaDatabaseHelper(mContext);
                    boolean flag = true;
                    for (int i = 0; i < t.size(); i++)
                    {
                        List<EHentaiMangaInfo> infos = mangaDatabaseHelper.queryByGid(t.get(i).gid);

                        if (infos == null || infos.size() == 0)
                        {
                            flag = false;
                            mHtmlList.add(t.get(i));
                            //mResultList.add(i,t.get(i));
                        } else {
                            mDBtList.add(infos.get(0));
                            mResultList.add(infos.get(0));
                        }
                    }
                    if (flag) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = mResultList;
                        mUiHandler.sendMessage(message);
                    } else {
                        mAppAction.gdata(mHandler, mHtmlList);
                    }
                    break;
                }
                case AppActionImpl.CODE_GDATA: {
                    List<EHentaiMangaInfo> t = (List<EHentaiMangaInfo>) msg.obj;
                    MangaDatabaseHelper mangaDatabaseHelper = new MangaDatabaseHelper(mContext);
                    for(EHentaiMangaInfo info : t)
                    {
                        for(int i = 0 ; i < mResultList.size() ; i++)
                        {
                            if(info.gid == mResultList.get(i).gid)
                            {
                                mResultList.set(i,info);
                            }
                        }
                        mResultList.add(info);
                        List<EHentaiMangaInfo> temp = mangaDatabaseHelper.queryByGid(info.gid);
                        if( temp != null && temp.size() > 0 )
                        {
                            mangaDatabaseHelper.update(info);
                        }else
                        {
                            mangaDatabaseHelper.insert(info);
                        }
                    }
                    Message message = new Message();
                    message.what = 0;
                    message.obj = mResultList;
                    mUiHandler.sendMessage(message);
                    break;
                }
                default:
                    break;
            }
        }
    };
    public  ArrayList<EHentaiMangaInfo> parseMangaList(String html)
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
    public  void getMangaList(final Handler handler, final Context context)
    {
        mResultList = new ArrayList<>();
        mHtmlList =  new ArrayList<>();
        mDBtList = new ArrayList<>();
        mUiHandler = handler;
        mAppAction.access(this.mHandler);
    }

    public static void getMangaListByHtml(final Handler handler,Context context)
    {

    }

    public static void getMangaListByDb(final Handler handler,Context context)
    {

    }
}
