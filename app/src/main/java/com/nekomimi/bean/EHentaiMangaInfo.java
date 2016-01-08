package com.nekomimi.bean;

/**
 * Created by hongchi on 2016-1-4.
 * File description :
 */
public class EHentaiMangaInfo
{
    public String mUrl;
    public int gid;
    public String token;
    public String archiver_key;
    public String title;
    public String title_jpn;
    public String  category;
    public String  thumb;
    public String  uploader;
    public String  posted;
    public String  filecount;
    public String filesize;
    public boolean expunged;
    public String  rating;
    public String torrentcount;
    public String tags[];


    public EHentaiMangaInfo(String Url)
    {
        this.mUrl = Url;
    }
}


