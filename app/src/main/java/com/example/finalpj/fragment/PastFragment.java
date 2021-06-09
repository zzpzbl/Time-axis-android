package com.example.finalpj.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalpj.R;
import com.example.finalpj.adapter.RecordsAdapter;
import com.example.finalpj.entity.Event;
import com.example.finalpj.utils.DBUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PastFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private Calendar calendar;
    private List<Event> eventList = new ArrayList<>();

    public PastFragment() {}

    public PastFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_past, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        calendar = Calendar.getInstance();
        context = getContext();
        initEvents();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RecordsAdapter(context, eventList));
    }

    private void initEvents() {
        List<Event> events = DBUtil.selectAllEvents();
        List<Event> futureEvents = events.stream()
                .filter(event -> event.getDate() < calendar.getTimeInMillis())
                .sorted(Comparator.comparingLong(Event::getDate).reversed())
                .collect(Collectors.toList());
        this.eventList = futureEvents;
    }

}
