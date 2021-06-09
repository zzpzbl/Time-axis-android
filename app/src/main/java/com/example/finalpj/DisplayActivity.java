package com.example.finalpj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalpj.entity.Event;
import com.example.finalpj.utils.BitmapToStringUtil;
import com.example.finalpj.utils.DBUtil;
import com.example.finalpj.utils.DateUtil;

import org.apache.commons.lang3.ObjectUtils;

public class DisplayActivity extends AppCompatActivity {

    private TextView eventTitle;
    private TextView eventDate;
    private TextView eventIntro;
    private TextView eventDetails;
    private ImageView eventImage;
    private ImageView editIcon;
    private ImageView deleteIcon;
    private Context context;
    private Event event;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        event = (Event) getIntent().getSerializableExtra("event");
        init();
        if (ObjectUtils.allNotNull(event)) {
            eventTitle.setText(event.getTitle());
            eventDate.setText(DateUtil.timestampToDateStr(event.getDate()));
            eventIntro.setText(event.getIntro());
            eventDetails.setText(event.getDetails());
            eventImage.setImageBitmap(BitmapToStringUtil.convertStringToIcon(event.getImage()));
        }
    }

    private void init() {
        context = DisplayActivity.this;
        eventTitle = findViewById(R.id.event_title);
        eventDate = findViewById(R.id.event_date);
        eventIntro = findViewById(R.id.event_intro);
        eventDetails = findViewById(R.id.event_details);
        eventImage = findViewById(R.id.event_image);
        editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);
        });
        deleteIcon = findViewById(R.id.delete_icon);
        deleteIcon.setOnClickListener(view -> {
            alertDialog = buildAlertDialog();
            alertDialog.show();
        });
    }

    private AlertDialog buildAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("确定删除该事件吗？");
        alertDialogBuilder.setPositiveButton("确定", (dialog, which) -> {
            int deleted = DBUtil.deleteEvent(event);
            Log.d("clickPositiveButton", "deleted: " + deleted);
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            finish();
        });
        alertDialogBuilder.setNegativeButton("取消", (dialog, which) -> {
            alertDialog.dismiss();
        });
        return alertDialogBuilder.create();
    }
}