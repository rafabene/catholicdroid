package com.rafabene.android.lib.twitter;

import static android.content.Context.MODE_PRIVATE;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.widget.Toast;

import com.rafabene.android.lib.R;

public class TwitterUtil {

    private static final String TWITTER_PREF = "TwitterPreference";

    private Context context;

    private TwitterUtil(Context context) {
        this.context = context;
    }

    public static TwitterUtil getInstance(Context context) {
        return new TwitterUtil(context);
    }

    /**
     * Get Access Token using a previous RequestToken from {@link #askOAuth(String, String, String)} method
     * 
     * @param consumerKey
     * @param consumerSecret
     * @param verifier
     * @return
     * @throws TwitterException
     */
    public AccessToken getAccessToken(String consumerKey, String consumerSecret, String verifier) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        SharedPreferences sharedPreferences = context.getSharedPreferences(TWITTER_PREF, MODE_PRIVATE);
        String requestToken = sharedPreferences.getString("requestToken", null);
        String requestTokenSecret = sharedPreferences.getString("requestTokenSecret", null);

        if (requestToken != null && requestTokenSecret != null) {
            RequestToken tr = new RequestToken(requestToken, requestTokenSecret);
            return twitter.getOAuthAccessToken(tr, verifier);
        } else {
            throw new IllegalStateException(context.getString(R.string.twitter_illegalState));
        }
    }

    /**
     * Convenience method to load {@link AccessToken} from file system
     * 
     * @return
     */
    public AccessToken loadAccessToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TWITTER_PREF, MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", null);
        String accessTokenSecret = sharedPreferences.getString("accessTokenSecret", null);
        if (accessToken != null && accessTokenSecret != null) {
            return new AccessToken(accessToken, accessTokenSecret);
        } else {
            return null;
        }
    }

    /**
     * Convenience method to store the {@link AccessToken} on file system
     * 
     * @param accessToken
     * @param accessTokenSecret
     */
    public void storeAccessToken(String accessToken, String accessTokenSecret) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TWITTER_PREF, MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("accessTokenSecret", accessTokenSecret);
        editor.commit();
    }

    /**
     * Open a browser to ask to authorize app to use Twitter.
     * 
     * The {@link RequestToken} is stored so it can be used again to complete the process on
     * {@link #getAccessToken(String, String, String)} method
     * 
     * @param consumerKey
     * @param consumerSecret
     * @param callBackUrl
     * @throws TwitterException
     */
    public void askOAuth(String consumerKey, String consumerSecret, String callBackUrl) throws TwitterException {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(consumerKey);
        configurationBuilder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = configurationBuilder.build();
        Twitter twitter = new TwitterFactory(configuration).getInstance();

        RequestToken requestToken = twitter.getOAuthRequestToken(callBackUrl);
        SharedPreferences sharedPreferences = context.getSharedPreferences(TWITTER_PREF, MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("requestToken", requestToken.getToken());
        editor.putString("requestTokenSecret", requestToken.getTokenSecret());
        editor.commit();
        Toast.makeText(context, R.string.twitter_authorize, Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
    }
}
