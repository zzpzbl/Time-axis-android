package com.example.finalpj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalpj.entity.Event;
import com.example.finalpj.utils.DBUtil;
import com.example.finalpj.utils.DateUtil;
import com.example.finalpj.utils.FileUtil;
import com.loper7.date_time_picker.DateTimePicker;

import org.apache.commons.lang3.StringUtils;

import static org.litepal.LitePalApplication.getContext;

public class EditActivity extends AppCompatActivity {

    private Button saveEventButton;
    private EditText eventTitleEditText;
    private EditText eventIntroEditText;
    private EditText eventDetailsEditText;
    private ImageView eventImageView;
    private DateTimePicker eventDateTimePicker;
    private Context context;
    private String imagePath = null;
    private Long eventDateTime;
    public static final int CHOOSE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initComponent();
        context = getContext();
    }

    private void initComponent() {
        findViewById(R.id.layout_edit_form).getBackground().setAlpha(220);
        saveEventButton = findViewById(R.id.save_event_button);
        eventTitleEditText = findViewById(R.id.event_title_edit_text);
        eventIntroEditText = findViewById(R.id.event_intro_edit_text);
        eventDetailsEditText = findViewById(R.id.event_details_edit_text);
        eventDateTimePicker = findViewById(R.id.event_date_time_picker);
        eventImageView = findViewById(R.id.event_image_view);
        saveEventButton.setOnClickListener(view -> saveEvent());
        eventImageView.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
            } else {
                openAlbum();
            }
        });
    }

    private void saveEvent() {
        String eventTitle = eventTitleEditText.getText().toString();
        String eventIntro = eventIntroEditText.getText().toString();
        String eventDetails = eventDetailsEditText.getText().toString();
        Long eventDate = DateUtil.getTimeStampFromDateTimePicker(eventDateTimePicker);
        String eventImage = FileUtil.imageToBase64(imagePath);
        if (StringUtils.isNotBlank(eventTitle)) {
            Event event = Event.builder()
                    .title(eventTitle)
                    .intro(eventIntro)
                    .details(eventDetails)
                    .date(eventDate)
                    .image(eventImage).build();
            boolean isSuccess = DBUtil.insertEvent(event);
            if (isSuccess) {
                Log.i("saveInsert", "event insertion succeed");
                Toast.makeText(EditActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("saveInsert", "event insertion failed");
                Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    // Camera and Album
    private void openAlbum()
    {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case CHOOSE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(context, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //frontTextLayout.setVisibility(View.GONE);
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)
    {
        Uri uri = data.getData();
        //LogUtil.e("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection)
    {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath)
    {
        Log.i("displayImage", "imagePath:" + imagePath);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //Glide.with(this).load(imagePath).into(photo1);
            eventImageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "打开图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}