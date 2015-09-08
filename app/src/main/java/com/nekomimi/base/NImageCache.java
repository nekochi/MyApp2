package com.nekomimi.base;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by hongchi on 2015-8-27.
 */
public class NImageCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> mCache;
    private static NImageCache mNImageCache;

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
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String,Bitmap>(maxSize)
        {
            @Override
            protected int sizeOf(String key,Bitmap bitmap)
            {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mCache.put(s,bitmap);
    }
}
