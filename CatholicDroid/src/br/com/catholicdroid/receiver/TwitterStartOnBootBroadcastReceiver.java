package br.com.catholicdroid.receiver;

import static com.rafabene.android.lib.Constant.LOG;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.com.catholicdroid.R;

import com.rafabene.android.lib.twitter.TwitterUtil;

;

public class TwitterStartOnBootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TwitterUtil.isTwitterConfigured(context)) {
            String consumerKey = context.getString(R.string.twitter_consumer_key);
            String consumerSecret = context.getString(R.string.twitter_consumer_secret);
            TwitterUtil twitterUtil = new TwitterUtil(context, consumerKey, consumerSecret);
            String query = "from:" + context.getString(R.string.twitter_pontifex_profile);
            twitterUtil.startTwitterDownloadService(query);
        } else {
            Log.w(LOG, "TwitterStartOnBootBroadcastReceiver not started because it is not configured");
        }

    }
}
