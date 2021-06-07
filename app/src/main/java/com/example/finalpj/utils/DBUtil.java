package com.example.finalpj.utils;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.finalpj.entity.Event;
import org.litepal.LitePal;
import java.util.List;

public class DBUtil {

    public static SQLiteDatabase sqLiteDatabase;

    static {
        sqLiteDatabase = LitePal.getDatabase();
    }

    public static List<Event> selectAllEvents() {
        return LitePal.findAll(Event.class);
    }

    public static boolean insertEvent(Event event) {
        Log.i("insertEvent", event.toString());
        return event.save();
    }

}
