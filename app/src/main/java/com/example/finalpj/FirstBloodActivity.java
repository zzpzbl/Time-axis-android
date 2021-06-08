package com.example.finalpj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.loper7.date_time_picker.*;
import com.loper7.date_time_picker.number_picker.NumberPicker;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Logger;

public class FirstBloodActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private DateTimePicker dateTimePicker;
    private SharedPreferences preferences;
    private Button finish;
    private Button articleBtn;
    private NumberPicker numberPickerOfYear;
    private NumberPicker numberPickerOfMonth;
    private NumberPicker numberPickerOfDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.first_blood);
        initComponent();
        initSharedPreferences();
    }

    private void initComponent() {
        name = findViewById(R.id.name);
        dateTimePicker = findViewById(R.id.dateTimePicker);
        numberPickerOfYear = dateTimePicker.findViewById(R.id.np_datetime_year);
        numberPickerOfMonth = dateTimePicker.findViewById(R.id.np_datetime_month);
        numberPickerOfDay = dateTimePicker.findViewById(R.id.np_datetime_day);
        finish = findViewById(R.id.finish);
        finish.setOnClickListener(this);

        articleBtn = findViewById(R.id.jmpArticle);
        articleBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FirstBloodActivity.this, DisplayActivity.class);
            startActivity(intent);
        });

    }

    private void initSharedPreferences() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.finish) {
            SharedPreferences.Editor editor = null;
            //获取haredPreferences.Editor对象，尝试写数据
            editor = preferences.edit();
            editor.putString("USERNAME", name.toString());
            editor.putString("BIRTHDAY", dateTimePicker.toString());
            editor.apply();
            Log.v("name ", name.toString());
            Log.v("time", dateTimePicker.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //隐藏键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(FirstBloodActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }



//    private boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }

}
