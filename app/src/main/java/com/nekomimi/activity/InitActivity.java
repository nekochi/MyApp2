package com.nekomimi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.nekomimi.R;

/**
 * Created by hongchi on 2015-8-26.
 */
public class InitActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        InitTask task = new InitTask();
        task.execute((Void)null);
    }


    public class InitTask extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                return false;
            }
            //在这里做初始化工作
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success )
        {
            Intent intent = new Intent(InitActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
