/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInteractorImpl implements LoginInteractor {

    private static final String TAG = LoginInteractorImpl.class.getSimpleName();
    private final LoginInteractorImpl self = this;

    private static final String REQUEST_URL = "https://getpocket.com/v3/oauth/request";
    private static final String OAUTH_URL = "https://getpocket.com/auth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://getpocket.com/v3/oauth/authorize";
    private static final String REQUEST_PARAM_CONSUMER_KEY_KEY = "consumer_key";
    private static final String REQUEST_PARAM_REDIRECT_URI_KEY = "redirect_uri";
    private static final String REQUEST_PARAM_REQUEST_TOKEN_KEY = "request_token";
    private static final String REQUEST_PARAM_CODE_KEY = "code";
    private static final String REDIRECT_URI = "pocketapp31188:authorizationFinished";
    private static final String CONSUMER_KEY = "31188-b1b51664b25d11d46f3a82c7";
    private static final String STORE_REQUEST_TOKEN_KEY = "REQUEST_TOKEN";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_ACCESS_TOKEN_KEY = "access_token";
    private static final String RESPONSE_USER_NAME_KEY = "username";

    Context context;

    public LoginInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getOAuthURL(final OnRequestTokenListener listener) {
        try {
            JSONObject params = this.makeRequestParams();
            PostJSONTask postJSONTask = new PostJSONTask(params, new PostJSONTask.Listener() {
                @Override
                public void onCompleted(String response) {
                    String requestToken = self.getRequestCode(response);
                    Log.e(TAG, "requestToken = " + requestToken);
                    self.storeRequestToken(requestToken);
                    String oauthURL = self.makeOAuthURL(requestToken);
                    listener.onCompleted(oauthURL);
                }
            });
            postJSONTask.execute(REQUEST_URL);
        } catch (JSONException e) {
            Log.e(TAG, "getRequestToken::JSONException", e);
        }
    }

    @Override
    public void login(final OnLoginListener listener) {
        try {
            String requestToken = this.getRequestToken();
            JSONObject params = this.makeOAuthRequestParams(requestToken);
            Log.e(TAG, "params = " + params.toString());
            PostJSONTask postJSONTask = new PostJSONTask(params, new PostJSONTask.Listener() {
                @Override
                public void onCompleted(String response) {
                    Log.e(TAG, "response = " + response);
                    String accessToken = self.getAccessToken(response);
                    String userName = self.getUserName(response);
                    listener.onSuccess(accessToken, userName);
                }
            });
            postJSONTask.execute(ACCESS_TOKEN_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getRequestCode(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString(RESPONSE_CODE_KEY);
        } catch (JSONException e) {
            Log.e(TAG, "getRequestCode::JSONException", e);
            return null;
        }
    }

    private String getAccessToken(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString(RESPONSE_ACCESS_TOKEN_KEY);
        } catch (JSONException e) {
            return null;
        }
    }

    private String getUserName(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString(RESPONSE_USER_NAME_KEY);
        } catch (JSONException e) {
            return null;
        }
    }

    private void storeRequestToken(String requestToken) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        sp.edit().putString(STORE_REQUEST_TOKEN_KEY, requestToken).commit();
    }

    private String getRequestToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        return sp.getString(STORE_REQUEST_TOKEN_KEY, null);
    }

    private JSONObject makeOAuthRequestParams(String requestToken) throws JSONException {
        JSONObject params = new JSONObject();
        params.put(REQUEST_PARAM_CONSUMER_KEY_KEY, CONSUMER_KEY);
        params.put(REQUEST_PARAM_CODE_KEY, requestToken);
        return params;
    }

    private String makeOAuthURL(String requestToken) {
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter(REQUEST_PARAM_REQUEST_TOKEN_KEY, requestToken);
        builder.appendQueryParameter(REQUEST_PARAM_REDIRECT_URI_KEY, REDIRECT_URI);
        String queryStr = builder.build().toString();
        return OAUTH_URL + queryStr;
    }

    private JSONObject makeRequestParams() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(REQUEST_PARAM_CONSUMER_KEY_KEY, CONSUMER_KEY);
        jsonObject.put(REQUEST_PARAM_REDIRECT_URI_KEY, REDIRECT_URI);
        return jsonObject;
    }
}
