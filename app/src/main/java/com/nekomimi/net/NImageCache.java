package com.nekomimi.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.nekomimi.base.NekoApplication;
import com.nekomimi.util.Util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hongchi on 2015-8-27.
 */
public class NImageCache implements ImageLoader.ImageCache {

    private DiskLruCache mDiskLruCache;
    private LruCache<String,Bitmap> mCache;
    private static NImageCache mNImageCache;

    private static final int MAXSIZE = 10 * 1024 * 1024;
    public static NImageCache getInstance()
    {
        if(mNImageCache == null)
        {
            mNImageCache = new NImageCache();
        }
        return mNImageCache;
    }
    private NImageCache()
    {
        mCache = new LruCache<String,Bitmap>(MAXSIZE)
        {
            @Override
            protected int sizeOf(String key,Bitmap bitmap)
            {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        try{
            mDiskLruCache = DiskLruCache.open(NekoApplication.getInstance().getImgDiskCacheDir()
                    ,NekoApplication.getInstance().getPackageInfo().versionCode,1,MAXSIZE);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public Bitmap getBitmap(String s)
    {
        if(mCache.get(s)!=null)
        {
            return mCache.get(s);
        }
        else
        {
            String key = Util.md5(s);
            try{
                if(mDiskLruCache.get(key)!=null)
                {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    Bitmap bitmap = null;
                    if (snapshot != null)
                    {
                        bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                        mCache.put(s, bitmap);
                    }
                    return bitmap;
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap)
    {
        mCache.put(s, bitmap);
        // 判断是否存在DiskLruCache缓存，若没有存入
        String key = Util.md5(s);
        try {
            if (mDiskLruCache.get(key) == null)
            {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null)
                {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream))
                    {
                        editor.commit();
                    }
                    else
                    {
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
