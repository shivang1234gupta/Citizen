package com.myapp.android.citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_DISPLAY_TIME=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, PageViewActivity.class);

                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();

                // transition from splash to main menu
                //overridePendingTransition(R.animate.activityfadein,
                       // R.animate.splashfadeout);

            }
        }, SPLASH_DISPLAY_TIME);
    }
}
