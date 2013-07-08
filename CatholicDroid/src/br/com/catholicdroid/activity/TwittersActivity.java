package br.com.catholicdroid.activity;

import static br.com.catholicdroid.Const.LOG;
import twitter4j.auth.AccessToken;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import br.com.catholicdroid.R;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.rafabene.android.lib.activity.BaseActivity;
import com.rafabene.android.lib.twitter.TwitterUtil;

public class TwittersActivity extends BaseActivity {

    private String consumerKey;
    private String consumerSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        consumerKey = this.getString(R.string.twitter_consumer_key);
        consumerSecret = this.getString(R.string.twitter_consumer_secret);
        String callBackUrl = this.getString(R.string.twitter_catholicdroid_callback_url);

        AccessToken accessToken = TwitterUtil.loadAccessToken(this);
        try {

            /**
             * Handle OAuth Callback
             */
            TwitterUtil twitterUtil = new TwitterUtil(this, consumerKey, consumerSecret);
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(callBackUrl)) {
                String denied = uri.getQueryParameter("denied");
                if (denied == null) {
                    String verifier = uri.getQueryParameter("oauth_verifier");
                    accessToken = twitterUtil.getAccessTokenFromOAuthVerifier(verifier);

                } else {
                    Toast.makeText(this, R.string.access_denied, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            if (accessToken == null) {
                twitterUtil.askOAuth(callBackUrl);
            } else {
                String query = "from:" + getString(R.string.twitter_pontifex_profile);
                twitterUtil.startTwitterDownloadService(query);
            }
        } catch (Exception e) {
            Log.e(LOG, e.getMessage(), e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getSupportMenuInflater();
        menuInflater.inflate(R.menu.twitter_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        switch (item.getItemId()) {
            case R.id.menuRemoveTwitterAuth:
                TwitterUtil.clearAccessToken(this);
                Toast.makeText(this, R.string.twitter_remove_auth_success, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
