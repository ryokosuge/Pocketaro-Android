/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = LoginPresenterImpl.class.getSimpleName();
    private final LoginPresenterImpl self = this;

    private LoginView view;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView view, Context context) {
        this.view = view;
        this.loginInteractor = new LoginInteractorImpl(context);
    }

    @Override
    public void getOAuthURL() {
        this.view.showProgress();
        this.loginInteractor.getOAuthURL(new OnRequestTokenListener() {
            @Override
            public void onCompleted(String oauthURL) {
                Log.e(TAG, "OAuthURL = " + oauthURL);
                self.view.hideProgress();
                self.view.showLoginBrowser(oauthURL);
            }
        });
    }

    @Override
    public void login(Uri resultURL) {
        this.view.showProgress();
        Log.e(TAG, "login()");
        Log.e(TAG, "resultURL = " + resultURL.toString());
        this.loginInteractor.login(new OnLoginListener() {
            @Override
            public void onSuccess(String accessToken, String userName) {
                self.view.hideProgress();
                Log.e(TAG, "accessToken = " + accessToken);
                Log.e(TAG, "userName = " + userName);
                self.view.navigateToHome();
            }
        });
    }
}
