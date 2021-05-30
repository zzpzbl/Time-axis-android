package com.example.finalpj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalpj.adapter.RecordsAdapter;
import com.example.finalpj.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvents();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecordsAdapter recordsAdapter = new RecordsAdapter(eventList);
        recyclerView.setAdapter(recordsAdapter);
    }

    private void initEvents() {
        Event event = new Event(1, "2020-05-31", "pkmdso", "pkmkmp");
        for (int i = 0; i < 10; ++i) {
            eventList.add(event);
        }
    }
}
