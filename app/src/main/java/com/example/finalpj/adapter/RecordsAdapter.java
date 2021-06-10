package com.example.finalpj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalpj.DisplayActivity;
import com.example.finalpj.R;
import com.example.finalpj.entity.Event;
import com.example.finalpj.enums.EventTypeEnum;
import com.example.finalpj.utils.BitmapUtil;
import com.example.finalpj.utils.DateUtil;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private List<Event> events;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView title;
        TextView intro;
        TextView countdown;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_time);
            title = (TextView) itemView.findViewById(R.id.title);
            intro = (TextView) itemView.findViewById(R.id.intro);
            countdown = itemView.findViewById(R.id.countdown);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    public RecordsAdapter(Context context, List<Event> events) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_even, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        Integer eventType = event.getEventType();
        Long eventTime = event.getDate();
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        Date eventDate = new Date(eventTime);
        Date currentDate = new Date(currentTime);
        Long interval = null;
        if (eventType.equals(EventTypeEnum.ORDINARY.getType())) {
            interval = Math.abs(eventTime - new Timestamp(currentTime).getTime()) / (1000 * 3600 * 24);
        } else if (eventType.equals(EventTypeEnum.YEAR.getType())) {
            eventDate.setYear(currentDate.getYear());
            if (eventDate.getTime() < currentTime) {
                eventDate.setYear(currentDate.getYear() + 1);
                interval = Math.abs(eventDate.getTime() - currentTime) / (1000 * 3600 * 24);
            } else {
                interval = Math.abs(eventDate.getTime() - currentTime) / (1000 * 3600 * 24);
            }
        } else if (eventType.equals(EventTypeEnum.MONTH.getType())) {
            eventDate.setYear(currentDate.getYear());
            eventDate.setMonth(currentDate.getMonth());
            if (eventDate.getTime() < currentTime) {
                calendar.setTime(eventDate);
                calendar.add(Calendar.MONTH, 1);
                interval = Math.abs(calendar.getTimeInMillis() - currentTime) / (1000 * 3600 * 24);
            } else {
                interval = Math.abs(eventDate.getTime() - currentTime)/ (1000 * 3600 * 24);
            }
        }
        holder.date.setText(DateUtil.timestampToDateStr(event.getDate()));
        holder.title.setText(event.getTitle());
        holder.intro.setText(event.getIntro());
        holder.countdown.setText(interval.toString());
        holder.imageView.setImageBitmap(BitmapUtil.convertStringToIcon(event.getImage()));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DisplayActivity.class);
            intent.putExtra("event", event);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return events.size();
    }
}
