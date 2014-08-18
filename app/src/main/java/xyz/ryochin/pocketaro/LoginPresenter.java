/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

import android.net.Uri;

public interface LoginPresenter {

    public void getOAuthURL();

    public void login(Uri resultURI);
}
