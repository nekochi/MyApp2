package com.nekomimi.bean;

import java.io.Serializable;

/**
 * Created by hongchi on 2015-8-28.
 */
public class RequestHeader implements Serializable {

    private String mAccept;
    private String mAccept_Encoding;
    private String mAccept_Language;
    private String mCache_Control;
    private String mConnection;
    private String mCookie;
    private String mUpgrade_Insecure_Requests;
    private String mUser_Agent;
}
