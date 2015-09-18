package com.nekomimi.bean;

import java.io.Serializable;

/**
 * Created by hongchi on 2015-9-18.
 */
public class MangaImgInfo implements Serializable {
    private String mImgUrl;
    private String mId;

    public String getImgUrl()
    {
        return this.mImgUrl;
    }
    public void setImgUrl(String imgUrl)
    {
        this.mImgUrl = imgUrl;
    }
    public String getId()
    {
        return this.mId;
    }
    public void setId(String id)
    {
        this.mId = id;
    }
}
