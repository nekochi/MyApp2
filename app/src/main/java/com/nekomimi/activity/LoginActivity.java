package com.nekomimi.activity;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nekomimi.R;
import com.nekomimi.base.AppConfig;
import com.nekomimi.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "LoginActivity";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;
    private final UiHandler mUiHandler = new UiHandler(this);
    // UI references.
    private AutoCompleteTextView mAccountView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mSignInButton;
    private RadioGroup mState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        initView();

    }

    @Override
    public void handleMessage(Message msg)
    {
        handleLogin(msg);
    }

    private void initView()
    {
        mAccountView = (AutoCompleteTextView) findViewById(R.id.account);
        mAccountView.setThreshold(1);
        populateAutoComplete();
        mState = (RadioGroup)findViewById(R.id.rg_website);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
//        getLoaderManager().initLoader(0, null, this);
        AppConfig appConfig = AppConfig.getInstance();
        addAccountToAutoComplete(Util.parseAccount(appConfig.get(AppConfig.ACCOUNT)));
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mUiHandler == null) {
            return;
        }

        if (mState.getCheckedRadioButtonId() == R.id.rb_common)
        {

            login(null,null);
        } else if (mState.getCheckedRadioButtonId() == R.id.rb_exhentai) {
           // login(null,null);
            //getAction().access(null);
        } else {

            // Reset errors.
            mAccountView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String account = mAccountView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;

                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(account) || !isAccountValid(account)) {
                mAccountView.setError(getString(R.string.error_field_required));
                focusView = mAccountView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                login(account,password);
            }
        }
    }

    private boolean isAccountValid(String account) {
        //TODO: Replace this with your own logic
        return account.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void login(String account,String password)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
        Util.showProgress(true, mLoginFormView, mProgressView);
        if(account == null && password == null)
        {
            Message msg = new Message();
            msg.what = 1;
            mUiHandler.sendMessageDelayed(msg,2000);
        }else {
            getAction().login(account, password, mUiHandler);
        }
//        mAuthTask = new UserLoginTask(account, password);
//        mAuthTask.execute((Void) null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new CursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addAccountToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addAccountToAutoComplete(List<String> accountCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, accountCollection);

        mAccountView.setAdapter(adapter);
    }

//    private static class UiHandler extends Handler
//    {
//        private final WeakReference<BaseActivity> mActivity;
//
//        public UiHandler(BaseActivity activity)
//        {
//            mActivity = new WeakReference<BaseActivity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg)
//        {
//            BaseActivity activity = mActivity.get();
//            if (activity != null)
//            {
//                ((LoginActivity)activity).handleLogin(msg);
//            }
//        }
//    }
    private void handleLogin(Message msg)
    {
        if(msg.what == 0)
        {
            Util.showProgress(false, null, mProgressView);
            AppConfig appConfig = AppConfig.getInstance();
            String str = appConfig.get(AppConfig.ACCOUNT);
            appConfig.set(AppConfig.ACCOUNT, Util.addAccount(str, mAccountView.getText().toString()));
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else if(msg.what == 1) {
            Util.showProgress(false, null, mProgressView);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
                Util.showProgress(false, mLoginFormView, mProgressView);
            }
        }

//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mAccount;
//        private final String mPassword;
//
//        UserLoginTask(String account, String password) {
//            mAccount = account;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            Util.showProgress(false,null,mProgressView);
//
//            if (success) {
//                AppConfig appConfig = AppConfig.getInstance();
//                String str = appConfig.get(AppConfig.ACCOUNT);
//                appConfig.set(AppConfig.ACCOUNT, Util.addAccount(str, mAccount));
//                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                startActivity(intent);
//                finish();
//
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            Util.showProgress(false,mLoginFormView,mProgressView);
//        }
//    }
}

