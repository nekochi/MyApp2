package com.nekomimi.bean;

import java.io.Serializable;

/**
 * Created by hongchi on 2015-8-25.
 */
public class ResponseHeader implements Serializable {
    private String mCache_Control;
    private String mConnection;
    private String mContent_Encoding;
    private String mConten_Type;
    private String mDate;
    private String mExpires;
    private String mServer;
    private String  mTransfer_Encoding;
    private String  mX_Cache;
}
