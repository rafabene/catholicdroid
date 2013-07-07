package br.com.catholicdroid.activity;

import static br.com.catholicdroid.Const.LOG;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
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

    private String consumerKey = null;
    private String consumerSecret = null;
    private String callBackUrl = null;

    private TwitterUtil twitterUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        consumerKey = this.getString(R.string.twitter_consumer_key);
        consumerSecret = this.getString(R.string.twitter_consumer_secret);
        callBackUrl = this.getString(R.string.twitter_catholicdroid_callback_url);
        twitterUtil = TwitterUtil.getInstance(this);

        AccessToken accessToken = twitterUtil.loadAccessToken();
        try {

            /**
             * Handle OAuth Callback
             */
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(callBackUrl)) {
                String denied = uri.getQueryParameter("denied");
                if (denied == null) {
                    String verifier = uri.getQueryParameter("oauth_verifier");
                    accessToken = twitterUtil.getAccessTokenFromOAuthVerifier(consumerKey, consumerSecret, verifier);
                } else {
                    Toast.makeText(this, R.string.access_denied, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            if (accessToken == null) {
                twitterUtil.askOAuth(consumerKey, consumerSecret, callBackUrl);
            } else {
                Twitter twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer(consumerKey, consumerSecret);
                twitter.setOAuthAccessToken(accessToken);
                Query q = new Query("q=Pontifex");
                QueryResult result = twitter.search(q);
                for (Status s : result.getTweets()) {
                    System.out.println(s.getText());
                }
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
                twitterUtil.clearAccessToken();
                Toast.makeText(this, R.string.twitter_remove_auth_success, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
