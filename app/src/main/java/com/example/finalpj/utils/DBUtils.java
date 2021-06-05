package com.example.finalpj.utils;

import android.database.sqlite.SQLiteDatabase;
import com.example.finalpj.entity.Event;
import org.litepal.LitePal;
import java.util.List;

public class DBUtils {

    public static SQLiteDatabase sqLiteDatabase;

    static {
        sqLiteDatabase = LitePal.getDatabase();
    }

    public static List<Event> selectAllEvents() {
        return LitePal.findAll(Event.class);
    }

    public static void insertEventById(Event event) {
        event.save();
    }
}
