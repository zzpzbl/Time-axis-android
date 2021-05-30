package com.example.finalpj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalpj.R;
import com.example.finalpj.entity.Event;
import com.example.finalpj.utils.BitmapToStringUtil;

import java.util.Date;
import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private List<Event> events;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView title;
        TextView details;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.item_time);
            title = (TextView) itemView.findViewById(R.id.title);
            details = (TextView) itemView.findViewById(R.id.details);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    public RecordsAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.date.setText(event.getDate());
        holder.title.setText(event.getTitle());
        holder.details.setText(event.getDetails());
        holder.imageView.setImageBitmap(BitmapToStringUtil.convertStringToIcon(event.getImage()));
    }


    @Override
    public int getItemCount() {
        return events.size();
    }
}
