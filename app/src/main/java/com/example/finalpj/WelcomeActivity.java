package com.example.finalpj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.finalpj.entity.Event;
import com.example.finalpj.enums.EventTypeEnum;
import com.example.finalpj.utils.BitmapUtil;
import com.example.finalpj.utils.DBUtil;
import com.example.finalpj.utils.DateUtil;
import com.loper7.date_time_picker.*;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private DateTimePicker dateTimePicker;
    private SharedPreferences preferences;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_welcome);
        initComponent();
        initSharedPreferences();
    }

    private void initComponent() {
        dateTimePicker = findViewById(R.id.dateTimePicker);
        finish = findViewById(R.id.finish);
        finish.setOnClickListener(this);
    }

    private void initSharedPreferences() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.finish) {
            Long dateTime = DateUtil.getTimeStampFromDateTimePicker(dateTimePicker);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pic06);
            SharedPreferences.Editor editor = null;
            //获取haredPreferences.Editor对象，尝试写数据
            editor = preferences.edit();
            editor.putString("BIRTHDAY", dateTime.toString());
            editor.apply();
            Event birthday = Event.builder().title("来到这个世界")
                    .intro("感觉真不错呀")
                    .details("祝你生日快乐~")
                    .date(dateTime)
                    .eventType(EventTypeEnum.ORDINARY.getType())
                    .image(BitmapUtil.convertBitmapToBase64(bitmapDrawable.getBitmap()))
                    .build();
            DBUtil.insertEvent(birthday);
            Log.v("time", dateTimePicker.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //隐藏键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(WelcomeActivity.this.getCurrentFocus().getWindowToken(), 0);
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
