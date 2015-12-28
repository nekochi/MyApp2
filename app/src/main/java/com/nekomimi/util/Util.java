package com.nekomimi.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.nekomimi.base.AppConfig;
import com.nekomimi.base.NekoApplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongchi on 2015-8-27.
 */
public class Util {

    public static String toResolution(float width,float height,float density)
    {
        StringBuilder builder = new StringBuilder("width:").append(width).append(";height:").append(height).append(";density:").append(density);
        return builder.toString();
    }


    public static float getResolutionFromStr(String str , String key)
    {
        String []strs = str.split(";");
        for(String s : strs)
        {
            String []ss = s.split(":");
            if(ss[0].equals(key))
            {
                return Float.valueOf(ss[1]);
            }
        }
        return -1;
    }

    //将所有已经保存的用户名加上新增用户名,输出格式是字符串
    public static String addAccount(String pre ,String value)
    {
        if(TextUtils.equals(pre, AppConfig.NOT_EXIST))
        {
            return value + ";";
        }
        else if(parseAccount(pre).contains(value))
        {
            return pre;
        }else if(value == null || value.isEmpty())
        {
            return  pre;
        }else
        {
            return pre + value + ";";
        }
    }

    //将本地存储的用户名加载到登录界面的自动填充输入框
    public static List<String> parseAccount(String str)
    {
        List<String> list = new ArrayList<String>();
        if(TextUtils.equals(str, AppConfig.NOT_EXIST))
        {
            list.clear();
            return list;
        }
        else
        {
            String []strs = str.split(";");
            for(int i = 0 ; i < strs.length ; i++)
            {
                list.add(strs[i]);
            }
            return list;
        }
    }

    public static Map<String,String> cookieToMap(String cookie)
    {
        Map<String,String> map = new HashMap<>();
        String []c1 = cookie.split(";");
        for(int i = 0 ; i < c1.length ; i++)
        {
            String []c2 = c1[i].split("=");
            map.put(c2[0],c2[1]);
        }
        return map;
    }
    public static String[] cookieToArray(String cookie)
    {
        String []str = new String[2];
        String []c1 = cookie.split(";");
        String []c2 = c1[0].split("=");
        str[0] = c2[0];
        str[1] = c2[1];
        return str;
    }
    public static String cookieToString(Map<String,String> map)
    {
        String str = "";
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            StringBuffer sb = new StringBuffer().append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            str += sb.toString();
        }
        if(map.size()>0)
            str = str.substring(0,str.length()-1);
        return str;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show ,final View originView ,final View progressView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = NekoApplication.getInstance().getResources().getInteger(android.R.integer.config_shortAnimTime);
            if(originView != null) {
                originView.setVisibility(show ? View.GONE : View.VISIBLE);
                originView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        originView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            if(originView != null) {
                originView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
    }

    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
