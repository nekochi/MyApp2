package com.nekomimi.bean;

import java.io.Serializable;

/**
 * Created by hongchi on 2015-8-25.
 */
public class VolleyResponseStruct implements Serializable{

    private String mResponse;
    private String mHeader;


    public String getmResponse()
    {
        return mResponse;
    }
    public void setmResponse(String response)
    {
        this.mResponse = response;
    }

    public String getmHeader()
    {
        return mHeader;
    }
    public void setmHeader(String header)
    {
        this.mHeader = header;
    }
}
