package com.example.finalpj.utils;

import android.view.View;

import com.example.finalpj.R;
import com.loper7.date_time_picker.DateTimePicker;
import com.loper7.date_time_picker.number_picker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.SneakyThrows;

public class DateUtil {

    public static String timestampToDateStr(Long timestamp){
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    @SneakyThrows
    public static Long getTimeStampFromDateTimePicker(DateTimePicker dateTimePicker) {
        NumberPicker yearPicker = dateTimePicker.findViewById(R.id.np_datetime_year);
        NumberPicker monthPicker = dateTimePicker.findViewById(R.id.np_datetime_month);
        NumberPicker dayPicker = dateTimePicker.findViewById(R.id.np_datetime_day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(yearPicker.getValue() + "-" + monthPicker.getValue() + "-" + dayPicker.getValue());
        return date.getTime();
    }
}
