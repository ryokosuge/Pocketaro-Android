/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

public interface LoginView {

    public void showProgress();

    public void hideProgress();

    public void showErrorMessage(String title, String message);

    public void showLoginBrowser(String oauthURL);

    public void navigateTo();

}
