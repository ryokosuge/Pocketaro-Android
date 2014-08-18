/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PostJSONTask extends AsyncTask<String, Void, String> {

    public interface Listener {
        void onCompleted(String response);
    }

    private static final String TAG = PostJSONTask.class.getSimpleName();
    private final PostJSONTask self = this;


    private Listener listener;
    private JSONObject params;

    public PostJSONTask(JSONObject params, Listener listener) {
        this.listener = listener;
        this.params = params;
    }

    @Override
    protected String doInBackground(String... params) {
        return this.post(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        this.listener.onCompleted(s);
    }

    private String post(String urlStr) {
        String response = this.postJSON(urlStr, this.params);
        return response;
    }

    private String postJSON(final String endPoint, final JSONObject params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(endPoint);

        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        httpPost.setHeader("X-Accept", "application/json");
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                // Error
            }
            return responseString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
