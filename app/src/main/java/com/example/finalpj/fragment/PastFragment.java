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
import java.util.List;

public class PastFragment extends Fragment {
    private List<Event> eventList = new ArrayList<>();
    private Context context;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_past, container, false);
        context = getContext();
        initEvents();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecordsAdapter recordsAdapter = new RecordsAdapter(eventList);
        recyclerView.setAdapter(recordsAdapter);
        return view;
    }

    private void initEvents() {
        eventList = DBUtil.selectAllEvents();
    }

}
