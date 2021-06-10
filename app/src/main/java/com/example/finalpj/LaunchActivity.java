package com.example.finalpj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private final int SPLASH_DISPLAY_LENGHT = 3000;  //延迟3秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String name = preferences.getString("BIRTHDAY", "empty");
                Intent intent = null;
                if (name.equals("empty")) {
                    intent = new Intent(LaunchActivity.this, WelcomeActivity.class);
                } else {
                    intent = new Intent(LaunchActivity.this, MainActivity.class);
                }
                startActivity(intent);
                destroyActivity();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    private void destroyActivity() {
        finish();
    }
}
