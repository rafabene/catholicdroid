package br.com.catholicdroid.activity;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import br.com.catholicdroid.R;

import com.rafabene.android.lib.activity.BaseActivity;
import com.rafabene.android.lib.domain.TaskCaller;
import com.rafabene.android.lib.domain.TwitterResult;
import com.rafabene.android.lib.twitter.TwitterUtil;

public class TwittersActivity extends BaseActivity implements TaskCaller<List<TwitterResult>> {

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
                String verifier = uri.getQueryParameter("oauth_verifier");
                accessToken = twitterUtil.getAccessToken(consumerKey, consumerSecret, verifier);
                twitterUtil.storeAccessToken(accessToken.getToken(), accessToken.getTokenSecret());
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void success(List<TwitterResult> result) {
        // TODO Auto-generated method stub

    }

}
