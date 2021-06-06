package com.example.finalpj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String timestampToDateStr(Long timestamp){
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }
}
