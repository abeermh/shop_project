package com.abeer_m.shop_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by abeer_m on 1/22/2018.
 */

public class splash extends AppCompatActivity {
    private final int splash_display_time=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this,login.class);
                splash.this.startActivity(intent);
                splash.this.finish();
            }
        },splash_display_time);
    }
}
