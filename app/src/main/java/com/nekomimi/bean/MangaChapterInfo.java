package com.nekomimi.bean;

import java.io.Serializable;

/**
 * Created by hongchi on 2015-9-17.
 */
public class MangaChapterInfo implements Serializable {
    private String mChapterName;
    private String mChapterId;

    public String getChapterName()
    {
        return this.mChapterName;
    }
    public void setChapterName(String chapterName)
    {
        this.mChapterName = chapterName;
    }
    public String getChapterId()
    {
        return this.mChapterId;
    }
    public void setChapterId(String chapterId)
    {
        this.mChapterId = chapterId;
    }
}
