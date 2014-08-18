/**
 * =====================================================
 * ENCODE : UTF-8
 * CREATED AT 14/08/18.
 * CREATED BY kosuge.
 * ===================================================== 
 */

package xyz.ryochin.pocketaro;

public interface LoginInteractor {
    public void getOAuthURL(OnRequestTokenListener listener);
    public void login(OnLoginListener listener);
}
