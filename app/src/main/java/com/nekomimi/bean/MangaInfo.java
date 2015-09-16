package com.nekomimi.bean;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by hongchi on 2015-9-9.
 */
public class MangaInfo implements Parcelable{
    private String mName;
    private String mType;
    private String mArea;
    private String mDes;
    private String mFinish;
    private String mLastUpdate;
    private String mCoverImgSt;

    private Bitmap mCoverImgBm = null;

    public MangaInfo(){}

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
        return mCoverImgSt;
    }
    public void setCoverImg(String coverImg)
    {
        this.mCoverImgSt = coverImg;
    }
    public String getArea()
    {
        return mArea;
    }
    public void setArea(String area)
    {
        this.mArea = area;
    }
    public Bitmap getCoverImgBm()
    {
        return this.mCoverImgBm;
    }
    public void setCoverImgBm(Bitmap coverImg)
    {
        this.mCoverImgBm = coverImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mArea);
        parcel.writeString(mCoverImgSt);
        parcel.writeString(mDes);
        parcel.writeString(mFinish);
        parcel.writeString(mLastUpdate);
        parcel.writeString(mType);
        parcel.writeParcelable(mCoverImgBm, i);
    }

    public static final Parcelable.Creator<MangaInfo> CREATOR = new Parcelable.Creator<MangaInfo>()
    {
        public MangaInfo createFromParcel(Parcel in)
        {
            return new MangaInfo(in);
        }

        public MangaInfo[] newArray(int size)
        {
            return new MangaInfo[size];
        }
    };

    @SuppressWarnings("unchecked")
    public MangaInfo(Parcel in) {
        // TODO Auto-generated constructor stub
        mName = in.readString();
        mArea = in.readString();
        mCoverImgSt = in.readString();
        mDes = in.readString();



        mFinish = in.readString();
        mLastUpdate = in.readString();

        mType = in.readString();
        mCoverImgBm = in.readParcelable(null);
    }
}
