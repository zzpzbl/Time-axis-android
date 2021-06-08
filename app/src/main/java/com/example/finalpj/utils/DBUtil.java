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

    public static Event selectEventById(Integer id) {
        Event event = LitePal.find(Event.class, id);
        Log.i("selectEventById", event.toString());
        return event;
    }

    public static List<Event> selectAllEvents() {
        List<Event> events = LitePal.findAll(Event.class);
        Log.i("selectAllEvents", events.toString());
        return events;
    }

    public static List<Event> selectEventFuzzily(String titlePart) {
        List<Event> events = LitePal.where("title like %?%", titlePart).find(Event.class);
        Log.i("selectEventFuzzily", events.toString());
        return events;
    }

    public static boolean insertEvent(Event event) {
        Log.i("insertEvent", event.toString());
        return event.save();
    }
}
