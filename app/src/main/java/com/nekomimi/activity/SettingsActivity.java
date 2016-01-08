package com.nekomimi.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.nekomimi.R;
import com.nekomimi.util.DataCleanManager;

/**
 * Created by hongchi on 2015-10-15.
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        this.setSupportActionBar(mToolbar);


        getFragmentManager().beginTransaction().replace(R.id.fl_content,new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            findPreference("cleanCache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
//                    Log.e(TAG, "onPreferenceClick");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Really???").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{


                                Log.e(TAG,DataCleanManager.getTotalCacheSize(getActivity()));
                                DataCleanManager.clearAllCache(getActivity());
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return false;
                }
            });
        }
    }


//    public class NumberPickerPreference extends DialogPreference {
//        public NumberPickerPreference(Context context, AttributeSet attrs) {
//            super(context, attrs);
//
//            //setDialogLayoutResource(R.layout.numberpicker_dialog);
//            setPositiveButtonText(android.R.string.ok);
//            setNegativeButtonText(android.R.string.cancel);
//
//            setDialogIcon(null);
//        }
//
//    }
}
