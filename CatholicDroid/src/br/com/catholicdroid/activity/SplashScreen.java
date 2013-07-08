package br.com.catholicdroid.activity;

import com.rafabene.android.lib.twitter.TwitterUtil;

import br.com.catholicdroid.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import static br.com.catholicdroid.Const.LOG;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.splashscreen);
        TextView tv = (TextView) findViewById(R.id.txtSplashScreen);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/CloisterBlack.ttf");
        tv.setTypeface(type);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 0);
        
        startTwitterService();
    }

    private void startTwitterService() {
        if (TwitterUtil.isTwitterConfigured(this)){
            String consumerKey = this.getString(R.string.twitter_consumer_key);
            String consumerSecret = this.getString(R.string.twitter_consumer_secret);
            TwitterUtil twitterUtil = new TwitterUtil(this, consumerKey, consumerSecret);
            String query = "from:" + getString(R.string.twitter_pontifex_profile);
            twitterUtil.startTwitterDownloadService(query);
        }else{
            Log.w(LOG, "Twitter is not configured yet. Waiting for opening TwitterActivity");
        }
        
    }

}
