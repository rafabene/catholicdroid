package br.com.catholicdroid.activity;

import br.com.catholicdroid.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

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
        }, 2000);
    }

}
