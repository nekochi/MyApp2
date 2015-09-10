package com.nekomimi.bean;

/**
 * Created by hongchi on 2015-9-9.
 */
public class MangaInfo {
    private String mName;
    private String mType;
    private String mArea;
    private String mDes;
    private String mFinish;
    private String mLastUpdate;
    private String mCoverImg;

    public String getName()
    {
        return mName;
    }
    public void setName(String name)
    {
        this.mName = name;
    }
    public String getType()
    {
        return mType;
    }
    public void setType(String type)
    {
        this.mType = type;
    }
    public String getDes()
    {
        return mDes;
    }
    public void setDes(String des)
    {
        this.mDes = des;
    }
    public String getFinish()
    {
        return mFinish;
    }
    public void setFinish(String finish)
    {
        this.mFinish = finish;
    }
    public String getLastUpdate()
    {
        return mLastUpdate;
    }
    public void setLastUpdate(String lastUpdate)
    {
        this.mLastUpdate = lastUpdate;
    }
    public String getCoverImg()
    {
        return mCoverImg;
    }
    public void setCoverImg(String coverImg)
    {
        this.mCoverImg = coverImg;
    }
    public String getArea()
    {
        return mArea;
    }
    public void setArea(String area)
    {
        this.mArea = area;
    }
}
