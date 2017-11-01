package topsint.myfriendsfinder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    final static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                boolean getLoginCheck = PrefUtil.getbooleanPref(PrefUtil.PRE_LOGINCHECK, getApplicationContext());
                Log.e(String.valueOf(getLoginCheck),"ok true che");
                if (getLoginCheck) {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
               /* Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();*/
            }
        }, SPLASH_TIME_OUT);
    } catch (Exception e){
            e.printStackTrace();
        }
    }
}
