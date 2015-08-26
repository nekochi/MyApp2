package com.nekomimi.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hongchi on 2015-8-20.
 */

public class HttpConnectNet extends AsyncTask<String,Void,String> {
    private static final String TAG = "HttpConnectNet";
    @Override
    protected String doInBackground(String... url)
    {
        try{
            return downloadFromNet(url[0]);
        }
        catch (IOException e) {
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Log.d(TAG,result);
    }
    private String downloadFromNet(String urlString) throws IOException
    {
        InputStream stream = null;
        String str = "";
        try{
            stream = downloadUrl(urlString);
            str = readIt(stream,10240);
        }
        finally {
            if(stream != null)
            {
                stream.close();
            }
        }
        return str;
    }

    private InputStream downloadUrl(String urlString) throws IOException
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("cookies","2333333");
        conn.setDoInput(true);

        conn.connect();
        InputStream stream = conn.getInputStream();
        return  stream;
    }

    private  String readIt(InputStream stream,int len)throws IOException,UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream,"UTF-8");
        char buffer[] = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
