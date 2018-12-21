package com.nyzc.gdm.currencyratio.uipacakage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.R;

public class SplashActivity extends AppCompatActivity {


    boolean is_first_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }

}
