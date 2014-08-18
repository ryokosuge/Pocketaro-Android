package xyz.ryochin.pocketaro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class LoginActivity extends ActionBarActivity implements LoginView, View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private final LoginActivity self = this;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.findViewById(R.id.activity_login_btn).setOnClickListener(this);
        this.loginPresenter = new LoginPresenterImpl(this, this.getApplicationContext());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent()");
        Uri returnURI = intent.getData();
        this.loginPresenter.login(returnURI);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick()");
        this.loginPresenter.getOAuthURL();
    }

    @Override
    public void showProgress() {
        Log.e(TAG, "showProgress()");
    }

    @Override
    public void hideProgress() {
        Log.e(TAG, "hideProgress");
    }

    @Override
    public void showErrorMessage(String title, String message) {
        Log.e(TAG, "showErrorMessage()");
    }

    @Override
    public void showLoginBrowser(String oauthURL) {
        Log.e(TAG, "OAuthURL = " + oauthURL);
        Uri oauthURI = Uri.parse(oauthURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, oauthURI);
        startActivity(intent);
    }

    @Override
    public void navigateToHome() {
        Log.e(TAG, "navigateTo()");
    }

}
