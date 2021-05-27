package com.example.finalpj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class EnablementActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private final int SPLASH_DISPLAY_LENGHT = 3000;  //延迟3秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enablement);
        SharedPreferences.Editor editor = null;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String name = preferences.getString("USERNAME", "empty");
                Intent intent = null;
                if (name.equals("empty")) {
                    intent = new Intent(EnablementActivity.this, FirstBloodActivity.class);
                } else {
                    intent = new Intent(EnablementActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGHT);


    }
}
